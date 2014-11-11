package ai.test1;

import ai.lab1.core.Agent;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridAction;
import ai.lab1.core.grid.GridEnvironment;
import ai.lab1.core.grid.GridObstacle;
import ai.lab1.core.grid.XYLocation;
import ai.lab1.vacuum.Dirt;
import ai.lab1.vacuum.SimpleVacuumInterface;

public class GridEnvironmentTest extends GridEnvironment{

	public class PerceptTest  implements Percept{

		private XYLocation location;

		public PerceptTest(XYLocation location){
			this.location = location;
		}

		public XYLocation getLocation() {
			return location;
		}
	}

	public GridEnvironmentTest(int width, int height) {
		super(width, height);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) { 
		return new PerceptTest(this.environmentState.getLocation(anAgent)) ;
	}

	public static void main(String args[]){
        
		GridEnvironmentTest env = new  GridEnvironmentTest(5,5);
		SimpleVacuumInterface i = 	new SimpleVacuumInterface(5,5);
	    
	    
		GridAction[] sequence = {
				TURN_90_LEFT, TURN_90_LEFT,TURN_90_LEFT, FORWARD ,
				TURN_90_LEFT, FORWARD, FORWARD,  TURN_90_LEFT,
				FORWARD, FORWARD,TURN_90_RIGTH,  FORWARD,
				FORWARD, TURN_90_RIGTH, FORWARD, FORWARD, ERROR_OP};

		GridAgentTest a = new  GridAgentTest(sequence );
		 

	
		env.addEnvironmentView(i);
		env.addEnvironmentObject(new GridObstacle(), new XYLocation(1,1));
		env.addEnvironmentObject(new GridObstacle(),new XYLocation(1,2));
		env.addEnvironmentObject(new Dirt(),new XYLocation(3,3));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,0));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,4));

		env.addEnvironmentObject(a,new XYLocation(3,3));

		i.show();
		env.stepUntilDone();
		i.show();
 
	}

}
