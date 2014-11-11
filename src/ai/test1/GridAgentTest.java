package ai.test1;

import ai.lab1.core.Action;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAction;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.core.grid.GridEnvironment;
import ai.lab1.core.grid.GridEnvironment.Orientation;

public class GridAgentTest extends GridAgent{

	private int nextActionIndex;
	
	private GridAction[] actionSequence; 

	public GridAgentTest(GridAction[] sequence) {
		this.setOrientation(Orientation.UP); 
		this.nextActionIndex  = -1;
		this.actionSequence = sequence;
 	}

	@Override
	public Action chooseAction(Percept percept) {
		 this.nextActionIndex++;
		 if (this.nextActionIndex < this.actionSequence.length){
			 return this.actionSequence[this.nextActionIndex];
		 }
		 else{
			 return GridEnvironment.STOP;
		 }
	}

	@Override
	public void updateModel(Action action, Percept percept) {
		//does noting
	}

}
