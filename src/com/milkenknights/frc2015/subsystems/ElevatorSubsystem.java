package com.milkenknights.frc2015.subsystems;

import com.milkenknights.common.MSubsystem;
import com.milkenknights.frc2015.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

/**
 * The subsystem that controls the elevator.
 * @author Jake Reiner
 */
public class ElevatorSubsystem extends MSubsystem {
    boolean positionMode;

    double elevatorSpeed;
    boolean resetPosition;

    public enum Positions {
        GROUND(0),
        SCORINGPLATFORM(1),
        STEP(2),
        FIRSTTOTE(3),
        SECONDTOTE(4),
        THIRDTOTE(30);

        public final double position;
        private Positions(double p) {
            position = p;
        }
    }

    public Positions elevatorPosition;

    CANTalon elevatorTalonRight;
    CANTalon elevatorTalonLeft;

    DigitalInput hallEffectSensorLeft;
    DigitalInput hallEffectSensorRight;
    
    Encoder enc_l;
    Encoder enc_r;
    
    PIDController pid_l;
    PIDController pid_r;

    public ElevatorSubsystem() {
        hallEffectSensorLeft = new DigitalInput(
                Constants.hallEffectSensorLeftDeviceNumber);
        hallEffectSensorRight = new DigitalInput(
                Constants.hallEffectSensorRightDeviceNumber);
        
        elevatorTalonLeft = new CANTalon(
                Constants.leftElevatorTalonDeviceNumber);
        elevatorTalonRight = new CANTalon(
                Constants.rightElevatorTalonDeviceNumber);
        
        resetPosition = false;
        positionMode = false;
        
        enc_l = new Encoder(Constants.elevatorLeftEncoderDeviceNumberA,
                Constants.elevatorLeftEncoderDeviceNumberB);
        enc_r = new Encoder(Constants.elevatorRightEncoderDeviceNumberA,
                Constants.elevatorRightEncoderDeviceNumberB);
        
        enc_l.setDistancePerPulse(Constants.elevatorInchesPerPulse);
        enc_r.setDistancePerPulse(-Constants.elevatorInchesPerPulse);
        
        pid_l = new PIDController(0,0,0, enc_l, elevatorTalonLeft);
        pid_r = new PIDController(0,0,0, enc_r, elevatorTalonRight);
    }

    /**
     * Change the mode for us controlling the elevator.
     * 
     * @param mode True changes mode to position mode. False changes mode to
     * manual speed control mode.
     */
    public void changeMode(boolean mode) {
        if (mode != positionMode) {
            if (mode) {
                pid_l.enable();
                pid_r.enable();
            } else {
                if (pid_l.isEnable()) {
                    pid_l.disable();
                }
                if (pid_r.isEnable()) {
                    pid_r.disable();
                }
            }
        }
        positionMode = mode;
    }
    
    /** Returns true if we are in position mode. */
    public boolean inPositionMode() {
        return positionMode;
    }
    
    /**
     * Tell the elevator to move to a predetermined height. Only works when we
     * are in position mode (otherwise, does nothing).
     * @param position The desired elevator position.
     */
    public void setPosition(Positions position) {
        elevatorPosition = position;
        pid_l.setSetpoint(elevatorPosition.position);
        pid_r.setSetpoint(-elevatorPosition.position);
    }

    /**
     * Manually set the speed of the elevator. Only works when we are in manual
     * speed control mode (otherwise, does nothing).
     * 
     * A positive value will move the elevator up.
     * @param speed The desired speed.
     */
    public void setSpeed(double speed) {
        elevatorSpeed = speed;
    }
    
    /**
     * Return the speed that we told to elevator to move at.
     * @return The last set value from setSpeed.
     */
    public double getSpeed() {
        return elevatorSpeed;
    }

    /**
     * Triggers the robot to go in reset mode. In reset mode, the elevator will
     * descend at a slow speed until the bottom hall effect sensors are hit.
     * When the robot is in reset mode, it will not react to any other controls
     * until it is either finished, or if the reset is manually halted by
     * calling abortReset().
     */
    public void resetPosition() {
        resetPosition = true;
    }
    
    /**
     * If the robot is in reset mode, this will prematurely end the reset.
     */
    public void abortReset() {
        resetPosition = false;
    }

    /**
     * Get the average between the elevator encoder positions.
     * @return the average between the elevator encoder positions.
     */
    public double getPosition() {
        return (enc_l.getDistance() - enc_r.getDistance())/2;
    }

    /**
     * Set PID gains for both sides of the elevator.
     */
    public void setPID(double p, double i, double d) {
        pid_l.setPID(p, i, d);
        pid_r.setPID(p, i, d);
    }

    public void update(){
        if (resetPosition) {
            boolean leftDone = hallEffectSensorLeft.get();
            boolean rightDone = hallEffectSensorRight.get();
            if (leftDone) {
                elevatorTalonLeft.set(0);
                elevatorTalonLeft.setPosition(0);
            } else {
                elevatorTalonLeft.set(0.1);
            }
            
            if (rightDone) {
                elevatorTalonRight.set(0);
                elevatorTalonRight.setPosition(0);
            } else {
                elevatorTalonRight.set(-0.1);
            }
            
            // once both sides have been reset, leave reset mode
            if (leftDone && rightDone) {
                resetPosition = false;
            }
        } else {
            if (positionMode) {
                //pid_l.setSetpoint(elevatorPosition.position);
                //pid_r.setSetpoint(-elevatorPosition.position);
            } else {
                elevatorTalonLeft.set(elevatorSpeed);
                elevatorTalonRight.set(-elevatorSpeed);
            }
        }
    }
}
