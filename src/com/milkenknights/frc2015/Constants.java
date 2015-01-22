package com.milkenknights.frc2015;

/** A listing of constants/settings used throughout the robot code. */
public class Constants {
    // CAN Device Numbers
    public static final int leftTalonDeviceNumberA = 3; // CIM
    public static final int leftTalonDeviceNumberB = 4; // CIM
    public static final int leftTalonDeviceNumberC = 5; // 550
    public static final int rightTalonDeviceNumberA = 8; // CIM
    public static final int rightTalonDeviceNumberB = 7; // CIM
    public static final int rightTalonDeviceNumberC = 6; // 550
    
    public static final int elevatorTalonDeviceNumber = 0;
    
    // DIO Ports
    public static final int pressureTransducerChannel = 0;
    
    // Pressure Transducer
    public static final double transducerScaleFactor = 50;
    public static final double transducerOffset = -25;
}
