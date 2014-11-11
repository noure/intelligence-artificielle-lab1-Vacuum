package ai.lab1.core;

/**
 * Class to represent any environment object. My be agent, obstacle...
 * @author Redouane
 *
 */
public abstract class EnvironmentObject {

	static int counter = 0;

	int id;
	 

	public EnvironmentObject(){
		this.id = counter++;
	}
	
	public int getId() {
		return id;
	}
	 

}
