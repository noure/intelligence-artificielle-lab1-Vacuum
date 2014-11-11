package ai.lab1.core.grid;

import ai.lab1.core.Agent;
import ai.lab1.core.grid.GridEnvironment.Orientation;

public abstract class GridAgent extends Agent {

	boolean collision = false;
	
	Orientation orientaion;
	
	public GridAgent(){
		orientaion =  Orientation.UP;
	}

	public Orientation getOriontation() {
 		return  orientaion;
	}

	public void setOrientation(Orientation orientation) {
		 this.orientaion =  orientation;
	}

	public boolean isCollision() {
		return collision;
	}
	
	public void setCollision(boolean value){
		this.collision = value;
	}

}
