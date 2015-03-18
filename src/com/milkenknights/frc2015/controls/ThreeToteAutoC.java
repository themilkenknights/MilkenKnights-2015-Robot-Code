package com.milkenknights.frc2015.controls;

import java.util.Iterator;
import java.util.LinkedList;

import com.milkenknights.common.AutonomousAction;
import com.milkenknights.frc2015.Constants;
import com.milkenknights.frc2015.subsystems.DriveSubsystem;
import com.milkenknights.frc2015.subsystems.ElevatorSubsystem;
import com.milkenknights.frc2015.subsystems.GroundIntakeSubsystem;
import com.milkenknights.frc2015.subsystems.GroundIntakeSubsystem.ActuatorsState;
import com.milkenknights.frc2015.subsystems.GroundIntakeSubsystem.WheelsState;
import com.milkenknights.frc2015.subsystems.autonomous.AutonWait;
import com.milkenknights.frc2015.subsystems.autonomous.ElevatorMoveAction;
import com.milkenknights.frc2015.subsystems.autonomous.FakeUltrasonic;
import com.milkenknights.frc2015.subsystems.autonomous.FlapsAction;
import com.milkenknights.frc2015.subsystems.autonomous.IntakeActuatorsSet;
import com.milkenknights.frc2015.subsystems.autonomous.IntakeWheelsSet;
import com.milkenknights.frc2015.subsystems.autonomous.PIDPivotAction;
import com.milkenknights.frc2015.subsystems.autonomous.PIDStraightAction;
import com.milkenknights.frc2015.subsystems.autonomous.PIDStraightBackground;
import com.milkenknights.frc2015.subsystems.autonomous.PrintAction;
import com.milkenknights.frc2015.subsystems.autonomous.WaitForAndLoadTote;
import com.milkenknights.frc2015.subsystems.autonomous.WaitForDriveDistance;
import com.milkenknights.frc2015.subsystems.autonomous.WaitForToteLoad;

/**
 * A three tote autonomous that attempts to grab knocked down bins and toss them
 * to the side.  Does not use AutonomousList.
 */
public class ThreeToteAutoC extends AutonomousList {
    LinkedList<AutonomousAction> actions;
    
    public ThreeToteAutoC(DriveSubsystem sDrive, ElevatorSubsystem sElevator,
            GroundIntakeSubsystem sGroundIntake) {
        super(sDrive, sElevator, sGroundIntake);
        for (int i = 0; i < 1; i++) {
            actions.add(new FlapsAction(elevatorSub, true));
            actions.add(new IntakeActuatorsSet(groundIntakeSub,
                    ActuatorsState.OPEN));

            actions.add(new ElevatorMoveAction(elevatorSub,
                    Constants.elevatorReadyToIntakeHeight,
                    Constants.elevatorThreshold));
            
            actions.add(new PIDStraightAction(driveSub, 35, 1.3));
            
            actions.add(new IntakeWheelsSet(groundIntakeSub,
                    WheelsState.SLOW_INTAKE));
            actions.add(new IntakeActuatorsSet(groundIntakeSub,
                    ActuatorsState.CLOSED));
            
            actions.add(new PIDPivotAction(driveSub, 90, 5));
            
            actions.add(new IntakeWheelsSet(groundIntakeSub,
                    WheelsState.OUTPUT));
            actions.add(new AutonWait(0.3));
            
            actions.add(new IntakeWheelsSet(groundIntakeSub,
                    WheelsState.STOPPED));
            actions.add(new IntakeActuatorsSet(groundIntakeSub,
                    ActuatorsState.OPEN));
            actions.add(new PIDPivotAction(driveSub, 0, 5));
        }
        
        /*
        actions.add(new ElevatorMoveAction(elevatorSub,
                Constants.elevatorReadyToIntakeHeight+1.5,
                Constants.elevatorThreshold));
        
        actions.add(new IntakeWheelsSet(groundIntakeSub,
                GroundIntakeSubsystem.WheelsState.STOPPED));
        
        actions.add(new PIDPivotAction(driveSub, 90, 0.35));
        
        actions.add(new PIDStraightAction(driveSub, 50, 0.35));
        
        actions.add(new PIDPivotAction(driveSub, 90, 0.35));
        
        actions.add(new ElevatorMoveAction(elevatorSub,
                0, Constants.elevatorThreshold));
        
        actions.add(new FlapsAction(elevatorSub, false));
        
        actions.add(new PIDStraightAction(driveSub, 30, 0.35));
        */
    }

    @Override
    protected Iterator<AutonomousAction> getAutonomousIterator() {
        return actions.iterator();
    }
}
