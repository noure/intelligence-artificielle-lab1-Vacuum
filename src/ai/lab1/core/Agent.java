package ai.lab1.core;

public abstract class Agent  extends EnvironmentObject{

	private boolean alive;
  
	public Agent() {
		super();
		this.alive = true; 
 	}
	// Every agent must implement the two following method
	public abstract Action chooseAction(Percept percept);
	        
	    // If the agent memorizes some model, this method should redefined
	    // In the case of reflex agent, this function shouldn't be redefined
	public abstract void  updateModel(Action action, Percept percept); 
	 
	/**
	 * Life-cycle indicator as to the liveness of an Agent.
	 * 
	 * @return true if the Agent is to be considered alive, false otherwise.
	 */
	boolean isAlive(){
		return alive;
		
	}

	/**
	 * Set the current liveness of the Agent.
	 * 
	 * @param alive
	 *            set to true if the Agent is to be considered alive, false
	 *            otherwise.
	 */
	public void setAlive(boolean alive){
		this.alive = alive;  
	}

}
