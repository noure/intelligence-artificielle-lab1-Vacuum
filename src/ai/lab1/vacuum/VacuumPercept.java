package ai.lab1.vacuum;

import ai.lab1.core.Percept;
import ai.lab1.core.grid.XYLocation;
import ai.lab1.vacuum.VacuumEnvironment.LocationState;

public class VacuumPercept implements Percept {

	LocationState locationState;
	
	XYLocation agentLocation;
	
	Boolean collisionInTheLastMove = false;

	public VacuumPercept(LocationState locationState, XYLocation agentLocation,
			Boolean collisionInTheLastMove) {
		super();
		this.locationState = locationState;
		this.agentLocation = agentLocation;
		this.collisionInTheLastMove = collisionInTheLastMove;
	}
 
	public   LocationState  getLocationSatate() {
 		return locationState;
	}
	
	 public String toString(){
		 return "[Cell" + agentLocation+ " is in " + locationState  + 
				 " state, and bump in last move : "+collisionInTheLastMove +"]";
	 }

	public XYLocation getAgentLocation() {
 		return this.agentLocation;
	}

}
