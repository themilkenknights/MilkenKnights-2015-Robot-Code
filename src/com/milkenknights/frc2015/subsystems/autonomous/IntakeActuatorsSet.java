package com.milkenknights.frc2015.subsystems.autonomous;

import com.milkenknights.common.AutonomousAction;
import com.milkenknights.frc2015.subsystems.GroundIntakeSubsystem;

/**
 * A quick AutonomousAction that calls the setActuators method and immediately
 * ends.
 * 
 * @see GroundIntakeSubsystem#setActuators(ActuatorsState)
 */
public class IntakeActuatorsSet extends AutonomousAction {
    GroundIntakeSubsystem groundIntakeSubsystem;

    GroundIntakeSubsystem.ActuatorsState s;

    public IntakeActuatorsSet(GroundIntakeSubsystem groundIntakeSubsystem,
            GroundIntakeSubsystem.ActuatorsState s) {
        this.groundIntakeSubsystem = groundIntakeSubsystem;

        this.s = s;
    }

    @Override
    public void startCode() {
        groundIntakeSubsystem.setActuators(s);
    }

    @Override
    public EndState periodicCode() {
        return EndState.END;
    }

}
