package com.milkenknights.frc2015;

/** A listing of constants/settings used throughout the robot code. */
public class Constants {
    public class DEBUG_LOGGER {
        public static final boolean ENABLED = true; //Enables and disables the debug log
        public static final int     DEFAULT_LOGLEVEL = 5; //Controls the standard logging level for the DebugLog
        public static final int     INFO_DISPLAYFREQ = 100; //Controls how often the DebugLog displays diagnostic information
        public static final boolean INTRO_MESSAGE = true; //Whether to display the welcome message
    }
    
    public class CAN {
        public static final int     GROUNDINTAKE_LEFT_TALON = 1;
        public static final int     ELEVATOR_LEFT_TALON = 2;
        public static final int     DRIVE_LEFT_A_TALON = 3;
        public static final int     DRIVE_LEFT_B_TALON = 4;        
        public static final int     DRIVE_RIGHT_B_TALON = 7;
        public static final int     DRIVE_RIGHT_A_TALON = 8;
        public static final int     ELEVATOR_RIGHT_TALON = 9;
        public static final int     GROUNDINTAKE_RIGHT_TALON = 10;
    }
    
    public class ANALOG {
        public static final int     PRESSURE_TRANSDUCER = 0;
    }
    
    public class DIO {
        public static final int     DRIVE_LEFT_ENCODER_A = 0;
        public static final int     DRIVE_LEFT_ENCODER_B = 1;
        public static final int     DRIVE_RIGHT_ENCODER_A = 2;
        public static final int     DRIVE_RIGHT_ENCODER_B = 3;
        public static final int     ELEVATOR_LEFT_ENCODER_A = 4;
        public static final int     ELEVATOR_LEFT_ENCODER_B = 5;
        public static final int     ELEVATOR_RIGHT_ENCODER_A = 6;
        public static final int     ELEVATOR_RIGHT_ENCODER_B = 7;
        public static final int     ELEVATOR_BANNER_BLACK = 8;
        public static final int     ELEVATOR_HALL_EFFECT = 9;
    }
    
    public class SOLENOID {
        public static final int     GROUNDINTAKE_LEFT = 0;
        public static final int     GROUNDINTAKE_RIGHT = 1;
        public static final int     ELEVATOR_FLAPS = 2;
    }
    
    public class PRESSURE_TRANSDUCER {
        public static final double  SCALE_FACTOR = 50;
        public static final double  OFFSET = -25;
    }
    
    public class GYRO {
        public static final int     IMU_BAUD_RATE = 57600;
        public static final double  MAXIMUM_INPUT = 180;
        public static final double  MINIMUM_INPUT = -180;
    }

    public class DRIVE {
        public static final double  INCHES_PER_PULSE = 4 * Math.PI / 360;
        public static final double  STRAIGHT_P = 0.015;
        public static final double  STRAIGHT_F = 0.18;
        public static final double  PIVOT_P = 0.041;
    }
    
    public class ELEVATOR {
        public static final double  INCHES_PER_PULSE = 1.25 * Math.PI / 360;
        public static final double  P = 0.277;
        public static final double  STEERING_P = 0.1;
        public static final double  F = 0.12;
        
        public static final double  RESET_DISTANCE = 0.2;
        public static final double  ACCURACY_THRESHOLD = 0.5;
        
        public class HEIGHTS {
            public static final double  MIN = 0;
            public static final double  MAX = 36;
            public static final double  SCORING_PLATFORM = 3;
            public static final double  READY_TO_INTAKE = 21;
            public static final double  STEP = 12;
            public static final double  ONE_TOTE = 14;
            public static final double  TWO_TOTE = 28.65;
        }
    }

    public class GROUND_INTAKE {
        public static final double  INTAKE_SPEED = 1;
        public static final double  INTAKE_SLOW_SPEED = 0.5;
        
    }
}
