package com.milkenknights.frc2015.controls;

import com.milkenknights.frc2015.subsystems.*;

public class ControlSystemSelect {
    public static ControlSystem controlSystem(DriveSubsystem drive,
            ElevatorSubsystem elevator) {
        return new TripleATKControl(drive, elevator);
    }
}