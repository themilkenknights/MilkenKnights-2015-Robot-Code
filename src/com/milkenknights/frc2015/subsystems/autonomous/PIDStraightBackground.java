package com.milkenknights.frc2015.subsystems.autonomous;

import com.milkenknights.common.AutonomousAction;
import com.milkenknights.frc2015.subsystems.DriveSubsystem;
import com.milkenknights.frc2015.subsystems.Subsystems;
import com.milkenknights.frc2015.subsystems.DriveSubsystem.DriveMode;

/**
 * The same as PIDStraightAction, but backgrounds immediately after starting
 * the PID loop.
 */
public class PIDStraightBackground extends AutonomousAction {
    DriveSubsystem driveSubsystem;
    double setpoint;
    double speedLimit;
    double tolerance;
    
    boolean firstLoop;
    
    /**
     * Make a new PIDStraightBackground
     * @param driveSubsystem the DriveSubsystem instance to use
     * @param setpoint the distance to travel
     * @param speedLimit the speed limit
     * @param tolerance how close to the desired distance we need to be
     */
    public PIDStraightBackground(Subsystems subsystems, double setpoint, double speedLimit,
            double tolerance) {
        this.driveSubsystem = subsystems.drive();
        this.setpoint = setpoint;
        this.speedLimit = speedLimit;
        this.tolerance = tolerance;
    }
    
    /**
     * Make a new PIDStraightBackground
     * @param driveSubsystem the DriveSubsystem instance to use
     * @param setpoint the distance to travel
     * @param tolerance how close to the desired distance we need to be
     */
    public PIDStraightBackground(Subsystems subsystems, double setpoint, double tolerance) {
        this(subsystems, setpoint, 1, tolerance);
    }
    
    @Override
    protected void startCode() {
        driveSubsystem.setStraightPIDSetpoint(setpoint, speedLimit);
        driveSubsystem.setDriveMode(DriveMode.PIDSTRAIGHT);
        firstLoop = true;
    }

    @Override
    protected EndState periodicCode() {
        if (driveSubsystem.pidOnTarget(tolerance)) {
            return EndState.END;
        } else if (firstLoop) {
            firstLoop = false;
            return EndState.BACKGROUND;
        } else {
            return EndState.CONTINUE;
        }
    }

}
