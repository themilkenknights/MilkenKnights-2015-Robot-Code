package com.milkenknights.frc2015;

import com.milkenknights.common.PIDGains;

/** A listing of constants/settings used throughout the robot code. */
public class Constants {
    // CAN Device Numbers
    public static final int groundIntakeLeftTalonDeviceNumber = 1;
    public static final int leftElevatorTalonDeviceNumber = 2;
    public static final int leftTalonDeviceNumberA = 3; // CIM
    public static final int leftTalonDeviceNumberB = 4; // CIM
    public static final int leftTalonDeviceNumberC = 5; // 550
    public static final int rightTalonDeviceNumberC = 6; // 550
    public static final int rightTalonDeviceNumberB = 7; // CIM
    public static final int rightTalonDeviceNumberA = 8; // CIM
    public static final int rightElevatorTalonDeviceNumber = 9;
    public static final int groundIntakeRightTalonDeviceNumber = 10;

    
    // these correspond to index numbers, so A->0, B->1, and C->2
    // these are the wheels that should be reversed
    public static final int[] reversedLeftTalons = {2};
    public static final int[] reversedRightTalons = {0,1};
    
    // Analog Ports
    public static final int pressureTransducerChannel = 0;
    
    // DIO Ports
    public static final int hallEffectSensorDeviceNumber = 9;
    public static final int elevatorLeftEncoderDeviceNumberA = 6;
    public static final int elevatorLeftEncoderDeviceNumberB = 7;
    public static final int elevatorRightEncoderDeviceNumberA = 4;
    public static final int elevatorRightEncoderDeviceNumberB = 5;
    public static final int driveLeftEncoderDeviceNumberA = 0;
    public static final int driveLeftEncoderDeviceNumberB = 1;
    public static final int driveRightEncoderDeviceNumberA = 2;
    public static final int driveRightEncoderDeviceNumberB = 3;
    public static final int bannerSensorWhiteDeviceNumber = 10;
    public static final int bannerSensorBlackDeviceNumber = 11;
    
    // solenoid ports
    public static final int groundIntakeFirstActuatorDeviceNumber = 0;
    public static final int groundIntakeSecondActuatorDeviceNumber = 1;

    // Pressure Transducer
    public static final double transducerScaleFactor = 50;
    public static final double transducerOffset = -25;
    
    public static final int imuBaudRate = 57600;
    
    // 4 inch wheel diameter. encoder does 360 pulses per revolution
    // This is equal to 4pi/360
    public static final double driveInchesPerPulse = 0.03490658503;
    
    // INCORRECT VALUE. FIX THIS
    public static final double elevatorInchesPerPulse = 0.01114794;
    
    // PID constants for driving straight
    public static final PIDGains driveStraightPID =
            new PIDGains(0.2, 0, 0.005);
    public static final PIDGains drivePivotPID =
            new PIDGains(0.2, 0, 0.005);
    
    public static final double minimumWheelSpeed = 0.0069;

    //GroundIntake talon speed
    public static final double groundIntakeTalonSpeed = 1;
    public static final double groundIntakeTalonSlowdownSpeed = .01;
    
    public static final double elevatorResetDistance = 0.25;
    public static final double elevatorMaxDistance = 34;
    public static final double elevatorMinDistance = 0.25;

    //Elevator PID constants
    public static final PIDGains elevatorPID = new PIDGains(0.195, 0, 0);
    
    public static final double elevatorScoringPlatformHeight = 3;
    public static final double elevatorReadyToIntakeHeight = 24;
    public static final double elevatorStepHeight = 12;
    public static final double elevatorTote1Height = 14;
    public static final double elevatorTote2Height = 28;
}
