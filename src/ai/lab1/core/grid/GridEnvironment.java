package ai.lab1.core.grid;

import java.util.Set;

import ai.lab1.core.Action;
import ai.lab1.core.Agent;
import ai.lab1.core.Environment;
import ai.lab1.core.EnvironmentObject;
import ai.lab1.core.EnvironmentState;
import ai.lab1.core.Obstacle;
import ai.lab1.core.grid.XYLocation.Direction;



/**
 *  This class is for environments represented by a 2D grid. At any time
  a thing is located in one cell of the grid. There cannot be more than 
  one thing at the same location. 

  Location is represented by a pair (row, col), where rows and columns are
  counted starting from top left corner:

     ------- ------- ------- 
    |       |       |       |
    | (0,0) | (0,1) | (0,2) | ....
    |       |       |       |
     ------- ------- ------- 
    |       |       |       | 
    | (1,0) | (1,1) | (1,2) | ... 
    |       |       |       | 
     ------- ------- ------- 
    |       |       |       |
    | (2,0) | (2,1) | (2,2) | ...
    |       |       |       |
     ------- ------- ------- 
        :       :       :
        :       :       :


  Agents only perceive things included in their current position  

  We may add some observers, for example an interface that will display
  state changes.
 * @author Redouane
 *
 */
public abstract class GridEnvironment extends Environment {

	public enum Orientation{ 
		UP, RIGTH, DOWN, LEFT 
	}

	public static final int NUM_POSSIBLE_ACTIONS = 3; 

	public static final GridAction TURN_90_RIGTH = new GridAction("Turn_90_Rigth");

	public static final GridAction TURN_90_LEFT = new GridAction("Turn_90_Left");

	public static final GridAction FORWARD = new GridAction("Forward");

	public static final GridAction STOP = new GridAction("STOP");

	public static final GridAction ERROR_OP =  new GridAction("Error"){
		@Override
		public boolean isNoOp() {
			return true;
		}
	};

	public static final GridAction NO_OP = new GridAction("NoOp"){
		@Override
		public boolean isNoOp() {
			return true;
		}
	};
	public static final GridAction[] POSSIBLE_ACTIONS ={TURN_90_RIGTH, TURN_90_LEFT, FORWARD};

	public static double FORWARD_MEASURE       =  -2; 
	public static double COLLISION_MEASURE     =  -3; 
	public static double TURN_90_RIGTH_MEASURE =  -1;
	public static double TURN_90_LEFT_MEASURE  =  -1;


	int width; 

	int height; 

	protected GridEnvironmentState environmentState = new GridEnvironmentState();

	public GridEnvironment(int width ,  int height ) {
		this.width = width;
		this.height = height;
	}

	public EnvironmentState executeAction(Agent ag, Action action){
		GridAgent agent = (GridAgent) ag;

		// Reset collision value
		agent.setCollision(false);
		if (action.equals(TURN_90_RIGTH)){
			this.turnHeading(agent, 1); 
			updatePerformanceMeasure(agent, TURN_90_RIGTH_MEASURE); 
		} else if(action.equals(TURN_90_LEFT) ){
			this.turnHeading(agent,-1);
			updatePerformanceMeasure(agent, -1); 
		}
		else if (action.equals(FORWARD) ){
			Direction  direction = null;
			if( agent.getOriontation() ==  Orientation.UP)
				direction = Direction.North; 
			else if (agent.getOriontation() ==  Orientation.RIGTH)
				direction = Direction.East; 
			else if (agent.getOriontation() ==  Orientation.DOWN)
				direction = Direction.South; 
			else if (agent.getOriontation() ==  Orientation.LEFT)
				direction = Direction.West; 
			this.moveForward(agent, direction) ;
		}
		else if (action.equals(STOP)  ){
			agent.setAlive(false);
			environmentState.changedState(agent); 
		}
		else if (action.equals(ERROR_OP)){
			agent.setOrientation(null) ;
		}
		this.notifyViews("Agent-"+ag.getId()+ " performs action "+   action.toString());
		return this.environmentState; 

	}

	@Override
	public EnvironmentState getCurrentState() {
		return environmentState;
	}


	public Set<EnvironmentObject> getThingsAt(XYLocation location) {
		return this.environmentState.getThingsAt(location);
	}


	private void moveForward(GridAgent agent, Direction direction) {
		XYLocation oldLocation = ((XYLocation) this.environmentState.getLocation(agent));
		XYLocation newLocation =	oldLocation.locationAt(direction);
		if(this.possible(newLocation)){ 
			updatePerformanceMeasure(agent, FORWARD_MEASURE); 
			agent.setCollision(false);
			this.environmentState.put(agent, newLocation);
		} else{
			updatePerformanceMeasure(agent, COLLISION_MEASURE); 
			agent.setCollision(true);
			environmentState.changedState(agent);
		} 
	}

	private boolean possible(XYLocation newLocation) {
		return  newLocation.getXCoOrdinate()>=0 
				&& newLocation.getYCoOrdinate()>=0
				&& newLocation.getXCoOrdinate() < this.width 
				&& newLocation.getYCoOrdinate() < this.height
				&& !obstacleInLocation(newLocation);
	}


	private boolean obstacleInLocation(XYLocation location) {
		Set<EnvironmentObject> set = this.getThingsAt(location);
		for( EnvironmentObject o : set){
			if(o instanceof Obstacle)
				return true;
		}
		return false;
	}

	private void turnHeading(GridAgent agent, int incr) {  
		int index = agent.getOriontation().ordinal();
		agent.setOrientation(Orientation.values()[(index + incr + 4 ) % 4])  ;
		environmentState.changedState(agent); 
	}

	public void addEnvironmentObject(EnvironmentObject eo, XYLocation location) {
		this.environmentState.put(eo, location); 
		super.addEnvironmentObject(eo);
	}

	public void removeEnvironmentObject(EnvironmentObject eo) {
		this.environmentState.remove(eo); 
		super.removeEnvironmentObject(eo);
	}

	public void addAgent(Agent  agent, XYLocation location) {
		this.addEnvironmentObject(agent, location);
	}
}
