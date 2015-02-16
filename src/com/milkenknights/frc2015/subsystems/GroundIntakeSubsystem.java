package com.milkenknights.frc2015.subsystems;

import com.milkenknights.common.MSubsystem;
import com.milkenknights.common.RestrictedSolenoidPair;
import com.milkenknights.frc2015.Constants;

import edu.wpi.first.wpilibj.CANTalon;

public class GroundIntakeSubsystem extends MSubsystem {

    CANTalon rightTalon;
    CANTalon leftTalon;
    RestrictedSolenoidPair actuators;
    
    boolean wheelsSpinning;
    
    public GroundIntakeSubsystem() {
        leftTalon = new CANTalon(Constants.groundIntakeLeftTalonDeviceNumber);
        rightTalon = new CANTalon(Constants.groundIntakeRightTalonDeviceNumber);
        actuators = new RestrictedSolenoidPair(
                Constants.groundIntakeFirstActuatorDeviceNumber,
                Constants.groundIntakeSecondActuatorDeviceNumber,
                false, 0, 0);
    }
    
    /**
     * Start spinning the wheels.
     */
    public void spinWheels() {
        wheelsSpinning = true;
    }
    
    /**
     * Opens the actuators and stops the wheels.
     */
    public void open() {
        wheelsSpinning = false;
        actuators.set(false);
    }
    
    /**
     * Closes the ground intake and starts spinning the wheels.
     */
    public void close() {
        wheelsSpinning = true;
        actuators.set(true);
    }
    
    public void update() {
        if (wheelsSpinning) {
            leftTalon.set(Constants.groundIntakeTalonSpeed);
            rightTalon.set(Constants.groundIntakeTalonSpeed);
        } else {
            leftTalon.set(0);
            rightTalon.set(0);
        }
    }
}