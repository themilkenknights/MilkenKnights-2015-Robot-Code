package com.milkenknights.frc2015.controls;

import com.milkenknights.common.AutonomousAction.CurrentState;
import com.milkenknights.frc2015.subsystems.Subsystems;
import com.milkenknights.frc2015.subsystems.autonomous.PIDTrapezoidal;

public class Move50Auto extends ControlSystem {
    PIDTrapezoidal trapezoid;
    public Move50Auto(Subsystems subsystems) {
        super(subsystems);
        
        trapezoid = new PIDTrapezoidal(subsystems, 0.25, 0.25, 1, 40, 1);
    }
    
    @Override
    public void init() {
        trapezoid.start();
    }
    
    @Override
    public void periodic() {
        if(trapezoid.getCurrentState() != CurrentState.ENDED) {
            trapezoid.periodicRun();
        }
    }
}
