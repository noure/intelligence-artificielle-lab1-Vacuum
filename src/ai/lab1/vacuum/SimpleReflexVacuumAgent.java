package ai.lab1.vacuum;

import java.util.Random;

import ai.lab1.core.Action;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.core.grid.GridEnvironment;

public class SimpleReflexVacuumAgent extends GridAgent {

	Random rand = new Random();

	public SimpleReflexVacuumAgent() {
		super();
	}

	@Override
	public Action chooseAction(Percept percept) {
		// A completer--- fait

		VacuumPercept vacuumPercept = (VacuumPercept) percept;

		if (vacuumPercept.getLocationSatate().equals(
				VacuumEnvironment.LocationState.Dirty)) {
			return VacuumEnvironment.STUCK;
		} else {
			/** trois action sont possibile 
			 * 
			 * 
			 */
			int i = rand.nextInt(3);
			
			return GridEnvironment.POSSIBLE_ACTIONS[i];
		}
	}

	@Override
	public void updateModel(Action action, Percept percept) {
		// Noting
	}

}
