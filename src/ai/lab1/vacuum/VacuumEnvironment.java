package ai.lab1.vacuum;

import java.util.Set;

import ai.lab1.core.Action;
import ai.lab1.core.Agent;
import ai.lab1.core.EnvironmentObject;
import ai.lab1.core.EnvironmentState;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAction;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.core.grid.GridEnvironment;
import ai.lab1.core.grid.XYLocation;



public class VacuumEnvironment extends GridEnvironment {

	public static final GridAction STUCK = new GridAction("Stuck");

	public static double SUCK_MEASURE = 5;


	public enum LocationState {
		Clean, Dirty
	};


	public VacuumEnvironment(int width, int height) {
		super(width, height);
	}


	@Override
	public Percept getPerceptSeenBy(Agent anAgent) { 
		XYLocation aglocation = this.environmentState.getLocation(anAgent);
		Set<EnvironmentObject> set = this.getThingsAt(aglocation); 
		LocationState locationState =  LocationState.Clean;
		for(EnvironmentObject eo :set) {
			if(eo instanceof Dirt){
				locationState = LocationState.Dirty ;
				break; 
			}
		}
		if (anAgent instanceof SimpleReflexVacuumAgent) {
			// reflex Agent can not percept its location
			// only the state of cell is percepted 
			return  new VacuumPercept(locationState, null,   null);
		}
		if (anAgent instanceof ModelBasedVacuumAgent) {
			return new VacuumPercept(locationState, aglocation,  ((GridAgent)anAgent).isCollision());
		}
		return null;
	}

	/**
	 * 
	 */
	public EnvironmentState executeAction(Agent ag, Action action){
		if(!action.equals(STUCK)){
			super.executeAction(ag, action);
		}
		else{
			Set<EnvironmentObject> set = this.getThingsAt(this.environmentState.getLocation(ag));
			for( EnvironmentObject e: set){
				if(e instanceof Dirt){				
					this.notifyViews("Agent-"+ag.getId()+ " performs action "+   action.toString()); 
					this.removeEnvironmentObject(e);
					this.updatePerformanceMeasure(ag, SUCK_MEASURE);

				}
			}
		}
		return environmentState;
	}


	@Override
	public boolean isDone() { 
		for(EnvironmentObject o : this.envObjects){
			if(o instanceof Dirt){
				return false;
			}
		}
		return true;

	}

}
