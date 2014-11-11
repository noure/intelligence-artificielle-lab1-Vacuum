package ai.lab1.core;

/**
 * Allows external applications/logic to view the interaction of Agent(s) with
 * an Environment.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public interface EnvironmentView {
	/**
	 * A simple notification message from an object in the Environment.
	 * 
	 * @param msg
	 *            the message received.
	 */
	void notify(String msg);

	/**
	 * Indicates an Agent has been added to the environment and what it
	 * perceives initially.
	 * 
	 * @param agent
	 *            the Agent just added to the Environment.
	 * @param resultingState
	 *            the EnvironmentState that resulted from the Agent being added
	 *            to the Environment.
	 */
	void agentAdded(Agent agent, EnvironmentState resultingState);
	
	
	void agentRemoved(Agent agent, EnvironmentState currentState);
	/**
	 * Indicates an environment object has been added to the environment 
	 * 
	 * @param eo
	 *            the environment object just added to the Environment.
	 * @param resultingState
	 *            the EnvironmentState that resulted from the environment object being added
	 *            to the Environment.
	 */
	void thingAdded(EnvironmentObject eo, EnvironmentState resultingState);
	
	void thingRemoved(EnvironmentObject eo, EnvironmentState resultingState);
	
	/**
	 * Indicates the Environment has changed as a result of an Agent's action.
	 * 
	 * @param agent
	 *            the Agent that performed the Action.
	 * @param action
	 *            the Action the Agent performed.
	 * @param resultingState
	 *            the EnvironmentState that resulted from the Agent's Action on
	 *            the Environment.
	 */
	void agentActed(Agent agent, Action action, EnvironmentState resultingState);

}
