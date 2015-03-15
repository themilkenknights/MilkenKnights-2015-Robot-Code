package com.milkenknights.frc2015.subsystems;

import com.kauailabs.nav6.frc.IMU;
import com.milkenknights.common.MSubsystem;
import com.milkenknights.frc2015.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem that manages the robot's wheels.
 * 
 */
public class DriveSubsystem extends MSubsystem {
    RobotDrive drive;

    Encoder encLeft;
    Encoder encRight;

    IMU gyro;

    double leftSpeed;
    double rightSpeed;

    double PIDStraightSetpoint;
    double PIDPivotSetpoint;

    public enum DriveMode {
        TANK, PIDSTRAIGHT, PIDPIVOT
    }

    DriveMode driveMode;

    public DriveSubsystem() {
        CANTalon leftTalonA = new CANTalon(Constants.leftTalonDeviceNumberA);
        CANTalon leftTalonB = new CANTalon(Constants.leftTalonDeviceNumberB);

        CANTalon rightTalonA = new CANTalon(Constants.rightTalonDeviceNumberA);
        CANTalon rightTalonB = new CANTalon(Constants.rightTalonDeviceNumberB);

        drive = new RobotDrive(leftTalonA, rightTalonA);

        encLeft = new Encoder(Constants.driveLeftEncoderDeviceNumberA,
                Constants.driveLeftEncoderDeviceNumberB);
        encRight = new Encoder(Constants.driveRightEncoderDeviceNumberA,
                Constants.driveRightEncoderDeviceNumberB);

        gyro = new IMU(new SerialPort(Constants.imuBaudRate,
                SerialPort.Port.kMXP));

        leftTalonB.changeControlMode(ControlMode.Follower);
        rightTalonB.changeControlMode(ControlMode.Follower);

        leftTalonB.set(leftTalonA.getDeviceID());
        rightTalonB.set(rightTalonA.getDeviceID());

        encLeft.setDistancePerPulse(-Constants.driveInchesPerPulse);
        encRight.setDistancePerPulse(Constants.driveInchesPerPulse);

        encLeft.setReverseDirection(true);

        driveMode = DriveMode.TANK;
    }

    /**
     * Change the current drive mode
     * 
     * @param mode
     *            The mode to change to
     */
    public void setDriveMode(DriveMode mode) {
        driveMode = mode;
    }

    /**
     * Set the speeds of the left and right side of the robot.
     * 
     * @param left
     *            The desired speed of the robot's left side.
     * @param right
     *            The desired speed of the robot's right side.
     */
    public void tankDrive(double left, double right) {
        leftSpeed = left;
        rightSpeed = right;
    }

    /**
     * Set the setpoint for PID straight driving mode. This should be how far
     * forward you want the robot to move.
     *
     * @param setpoint
     *            The desired PID straight setpoint.
     */
    public void setStraightPIDSetpoint(double setpoint) {
        PIDStraightSetpoint = setpoint;
    }

    /**
     * Get whatever we set the straight drive setpoint to be.
     *
     * @return The last set PID straight setpoint.
     */
    public double getStraightPIDSetpoint() {
        return PIDStraightSetpoint;
    }

    /**
     * Set the setpoint for PID pivot mode. This should be the angle you want
     * the robot to be facing.
     *
     * @param setpoint
     *            The desired PID angle setpoint, between -180 and 180.
     */
    public void setPivotPIDSetpoint(double setpoint) {
        PIDPivotSetpoint = setpoint;
    }

    /**
     * Get whatever we set the pivot setpoint to be.
     *
     * @return The last set PID pivot setpoint.
     */
    public double getPivotPIDSetpoint() {
        return PIDPivotSetpoint;
    }

    /**
     * "Zero out" our position. If the robot has moved forward or rotated, this
     * will reset the position back to zero.
     */
    public void resetStraightPIDPosition() {
        encLeft.reset();
        encRight.reset();
    }

    /**
     * Find out if we have reached our straight PID target.
     *
     * @param threshold
     *            How close/precise we want to be
     * @return true if we have reached the target. false if we are not in a PID
     *         mode at all
     */
    public boolean pidOnTarget(double threshold) {
        if (driveMode == DriveMode.PIDPIVOT) {
            return Math.abs(getPivotPIDSetpoint() - gyro.getYaw()) <= threshold;
        } else if (driveMode == DriveMode.PIDSTRAIGHT) {
            return Math.abs(getStraightPIDSetpoint() - encLeft.pidGet()) <= threshold
                    || Math.abs(getStraightPIDSetpoint() - encLeft.pidGet()) <= threshold;
        }
        return false;
    }
    
    double m_error;
    public double pivotPIDError() {
        m_error = PIDPivotSetpoint - gyro.pidGet();

        if (Math.abs(m_error) > (Constants.gyroMaximumInput - Constants.gyroMinimumInput) / 2) {
            if (m_error > 0) {
                m_error = m_error - Constants.gyroMaximumInput
                        + Constants.gyroMinimumInput;
            } else {
                m_error = m_error + Constants.gyroMaximumInput
                        - Constants.gyroMinimumInput;
            }
        }
        
        return m_error;
    }
    
    public double getEncPosition() {
        return encLeft.getDistance();
    }
    
    private double limit(double val, double lim) {
        if (Math.abs(val) <= lim) {
            return val;
        } else if (val > 0) {
            return lim;
        } else if (val < 0) {
            return -lim;
        } else {
            return 0;
        }
    }

    /**
     * Updates wheel speeds depending on driveMode (which should be set to the
     * desired mode with setDriveMode(). This method should be called during
     * every loop no matter what.
     */
    public void update() {
        switch (driveMode) {
        case TANK:
            drive.tankDrive(leftSpeed, rightSpeed, true);
            break;
        case PIDSTRAIGHT:
            double outputMagnitude = (getStraightPIDSetpoint() - encLeft.pidGet()) * Constants.driveStraightP;
            double curve = (PIDPivotSetpoint - gyro.pidGet()) * Constants.drivePivotP;
            
            drive.drive(outputMagnitude, curve);
            break;
        case PIDPIVOT:
            double m_result = Constants.drivePivotP * pivotPIDError();

            if (m_result > 1) {
                m_result = 1;
            } else if (m_result < -1) {
                m_result = -1;
            }

            drive.tankDrive(m_result, -m_result);
            break;
        }

        SmartDashboard.putNumber("l dist", encLeft.pidGet());
        SmartDashboard.putNumber("r dist", encRight.pidGet());
        SmartDashboard.putNumber("gyro", gyro.pidGet());
        SmartDashboard.putNumber("pivot setppoint", PIDPivotSetpoint);
        SmartDashboard.putString("Drive Mode", driveMode.toString());
        System.out.println(gyro.pidGet() + " " + PIDPivotSetpoint);
    }
}
