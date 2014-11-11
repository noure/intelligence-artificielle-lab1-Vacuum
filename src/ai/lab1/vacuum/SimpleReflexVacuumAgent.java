package ai.lab1.vacuum;

import java.util.Random;

import ai.lab1.core.Action;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.vacuum.VacuumEnvironment.LocationState;


public class SimpleReflexVacuumAgent extends GridAgent {

	Random rand = new Random();
	
 
	public SimpleReflexVacuumAgent() {
		super();
 	}

	@Override
	public Action chooseAction(Percept percept) {
		 // A completer
	}

	@Override
	public void updateModel(Action action, Percept percept) { 
		//Noting
	}

}
