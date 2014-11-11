package ai.lab1.vacuum;

import java.util.Random;

import ai.lab1.core.Action;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.vacuum.VacuumEnvironment.LocationState;


/**
 *  An agent that keeps track of:
          - executions of 'Forward' action that resulted in bump
          - executions of 'Forward' action that would lead to an already visited location
 * @author Redouane
 *
 */
public class ModelBasedVacuumAgent extends GridAgent {

	VacuumModel model;

	Random rand = new Random();


	public ModelBasedVacuumAgent() {
		this.model = new VacuumModel(this); 
	}


	@Override
	public Action chooseAction(Percept percept) { 
	    //	A complete. vous pouvez utilis� cet algo:<---------------- done
		
		VacuumPercept vacuumPercept = (VacuumPercept) percept;
		
	   /**
	    * if location-state is Dirty
	    * 		return STUCK
	    * else 
	    * 	action <-- randomly choose an action
	    *   if action is FORWARD and not (can-go-forward(model, percept.location)
	    * 	    return randomly choose an action from {TURN_90_LEFT, TURN_90_RIGTH}
	    * 	return action
	    * 	
	    */
		
		
		 
	}

	@Override
	public void updateModel(Action action, Percept percept) {
		//  A completer
	}

}
