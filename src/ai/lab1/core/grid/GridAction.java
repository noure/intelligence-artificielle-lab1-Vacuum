package ai.lab1.core.grid;

import ai.lab1.core.Action;
/**
 * A class that represents an action in the 2D grid environment
 * 
 * @author Redouane EZZAHIR
 *
 */
public class GridAction implements Action {

	private String name;
	
	public GridAction(String name){
		this.name = name;
	}
	/**
	 * Returns the value of the name attribute.
	 * 
	 * @return the value of the name attribute.
	 */
	public String getName() {
		return name;		
	}
	
	public String toString() {
		return name;		
	}
	
	@Override
	public boolean isNoOp() {
 		return false;
	}

}
