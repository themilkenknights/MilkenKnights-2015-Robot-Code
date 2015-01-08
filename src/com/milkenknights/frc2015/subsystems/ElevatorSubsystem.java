package com.milkenknights.frc2015.subsystems;

import edu.wpi.first.wpilibj.CANTalon;

/*
 * @author Jake
 */

public class ElevatorSubsystem {
	public enum Positions {GROUND, SCORINGPLATFORM, STEP, FIRSTTOTE, SECONDTOTE, THIRDTOTE};
	Positions elevatorPosition;
	
	CANTalon elevatorTalon;
	
	
	public ElevatorSubsystem() {
		elevatorTalon = new CANTalon(0);
		
		elevatorTalon.changeControlMode(CANTalon.ControlMode.Position);
	}
	
	public void setPosition(Positions position) {
		elevatorPosition = position;
	}
	
	public void update(){
		switch (elevatorPosition) {
		case GROUND:
			elevatorTalon.set(0);
		case SCORINGPLATFORM:
			elevatorTalon.set(1);
		case STEP:
			elevatorTalon.set(2);
		case FIRSTTOTE:
			elevatorTalon.set(3);
		case SECONDTOTE:
			elevatorTalon.set(4);
		case THIRDTOTE:
			elevatorTalon.set(5);
		}
	}
}
