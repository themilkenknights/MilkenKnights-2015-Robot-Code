package com.milkenknights.frc2015;

import java.util.LinkedList;

import com.milkenknights.common.MSubsystem;
import com.milkenknights.common.RestrictedSolenoid;
import com.milkenknights.frc2015.controls.DoNothing;
import com.milkenknights.frc2015.controls.Move50Auto;
import com.milkenknights.frc2015.controls.ControlSystem;
import com.milkenknights.frc2015.controls.ThreeToteAutoB;
import com.milkenknights.frc2015.controls.ThreeToteAutoC;
import com.milkenknights.frc2015.controls.TripleATKControl;
import com.milkenknights.frc2015.subsystems.DriveSubsystem;
import com.milkenknights.frc2015.subsystems.ElevatorSubsystem;
import com.milkenknights.frc2015.subsystems.GroundIntakeSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    LinkedList<MSubsystem> subsystems;
    
    DriveSubsystem driveSubsystem;
    ElevatorSubsystem elevatorSubsystem;
    GroundIntakeSubsystem groundIntakeSubsystem;

    ControlSystem teleControlSystem;
    ControlSystem autoControlSystem;
    
    SendableChooser autoChooser;

    public void robotInit() {
        RestrictedSolenoid.initPressureSensor(Constants.pressureTransducerChannel, 
                Constants.transducerScaleFactor, Constants.transducerOffset);
        
        driveSubsystem = new DriveSubsystem();
        elevatorSubsystem = new ElevatorSubsystem();
        groundIntakeSubsystem = new GroundIntakeSubsystem();

        teleControlSystem = new TripleATKControl(driveSubsystem,
                elevatorSubsystem,
                groundIntakeSubsystem);
        
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing",
                new DoNothing(driveSubsystem,
                        elevatorSubsystem,
                        groundIntakeSubsystem));
        autoChooser.addObject("Drive Forward 50\"",
                new Move50Auto(driveSubsystem,
                        elevatorSubsystem,
                        groundIntakeSubsystem));
        autoChooser.addObject("Three Tote Auto",
                new ThreeToteAutoB(driveSubsystem,
                        elevatorSubsystem,
                        groundIntakeSubsystem));
        autoChooser.addObject("Rag 3 Tote",
                new ThreeToteAutoC(driveSubsystem,
                        elevatorSubsystem,
                        groundIntakeSubsystem));
        SmartDashboard.putData("Autonomous Selector", autoChooser);

        subsystems = new LinkedList<MSubsystem>();
        subsystems.add(driveSubsystem);
        subsystems.add(elevatorSubsystem);
        subsystems.add(groundIntakeSubsystem);
    }

    public void autonomousInit() {
        autoControlSystem = (ControlSystem) autoChooser.getSelected();
        
        autoControlSystem.init();
        
        subsystems.stream().forEach(s -> s.update());
    }

    public void autonomousPeriodic() {
        autoControlSystem.periodic();
        
        subsystems.stream().forEach(s -> s.update());
    }

    public void teleopInit() {
        teleControlSystem.init();
        
        subsystems.stream().forEach(s -> s.teleopInit());
    }

    public void teleopPeriodic() {
        teleControlSystem.periodic();
        
        subsystems.stream().forEach(s -> s.update());
    }
    
    public void disabledPeriodic() {
        subsystems.stream().forEach(s -> s.update());
    }

    public void testPeriodic() {

    }
}
