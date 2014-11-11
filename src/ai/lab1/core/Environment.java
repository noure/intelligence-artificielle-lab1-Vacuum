package ai.lab1.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 

/**
 * Abstract class representing an Environment.  'Real' Environment classes
    inherit from this. Your Environment will typically need to implement:
        percept:          Define the percept that an agent sees.
        executeAction:    Define the effects of executing an action.
                          Also update the agent.performance slot.
    The environment keeps a list of things and agents (which is a subset
    of things). Each agent has a performance slot, initialized to 0.
    Each thing has a location slot, even though some environments may not
    need this.

 * @author Redouane
 *
 */
public abstract class Environment implements EnvironmentViewNotifier {

	// Note: Use LinkedHashSet's in order to ensure order is respected as
	// provide
	// access to these elements via List interface.
	protected Set<EnvironmentObject> envObjects = new LinkedHashSet<EnvironmentObject>();

	protected Set<Agent> agents = new LinkedHashSet<Agent>();

	protected Set<EnvironmentView> views = new LinkedHashSet<EnvironmentView>();

	protected Map<Agent, Double> performanceMeasures = new LinkedHashMap<Agent, Double>();

	//
	// PRUBLIC METHODS
	//

	//
	// Methods to be implemented by subclasses.
	public abstract EnvironmentState getCurrentState();

	public abstract EnvironmentState executeAction(Agent agent, Action action);

	public abstract Percept getPerceptSeenBy(Agent anAgent);
 
	//
	// START-Environment
	public List<Agent> getAgents() {
		// Return as a List but also ensures the caller cannot modify
		return new ArrayList<Agent>(agents);
	}

	public void addAgent(Agent a) {
		addEnvironmentObject(a);
	}

	public void removeAgent(Agent a) {
		removeEnvironmentObject(a);
	}

	public List<EnvironmentObject> getEnvironmentObjects() {
		// Return as a List but also ensures the caller cannot modify
		return new ArrayList<EnvironmentObject>(envObjects);
	}

	public void addEnvironmentObject(EnvironmentObject eo) {
		envObjects.add(eo);
		if (eo instanceof Agent) {
			Agent a = (Agent) eo;
			if (!agents.contains(a)) {
				agents.add(a); 
				this.updateEnvironmentViewsAgentAdded(a); 
			}
		}
		else{ 
			this.updateEnvironmentViewsThingAdded(eo); 
		}
	}

	
	public void removeEnvironmentObject(EnvironmentObject eo) {
		envObjects.remove(eo);
		if (eo instanceof Agent) {
			agents.remove(eo);
			this.updateEnvironmentViewsAgentRemoved((Agent) eo); 
		}
		else{
			this.updateEnvironmentViewsThingRemoved(eo); 
		}
		
	}

	

	

	/**
	 * Central template method for controlling agent simulation. The concrete
	 * behavior is determined by the primitive operations
	 * {@link #getPerceptSeenBy(Agent)}, {@link #executeAction(Agent, Action)} 
	 */
	public void step() {
		for (Agent agent : agents) {
			if (agent.isAlive()) {
				Percept percept = getPerceptSeenBy(agent);
				this.notifyViews("Agent-"+agent.getId()+ " percept : "+percept.toString());
				Action anAction = agent.chooseAction(percept);
				EnvironmentState es = executeAction(agent, anAction);
				agent.updateModel(anAction, percept);
				updateEnvironmentViewsAgentActed(agent, anAction, es);
				es.reset();
			}
		} 
	}

	public void step(int n) {
		for (int i = 0; i < n; i++) {
			step();
		}
	}

	public void stepUntilDone() {
		while (!isDone()) {
			step();
		}
	}

	public void stepByStepUntilDone() { 
		while (!isDone() ) {
			step();
			try {
				System.in.read();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
	}
	
	public boolean isDone() {
		for (Agent agent : agents) {
			if (agent.isAlive()) {
				return false;
			}
		}
		return true;
	}

	
	public double getPerformanceMeasure(Agent forAgent) {
		Double pm = performanceMeasures.get(forAgent);
		if (null == pm) {
			pm = new Double(0);
			performanceMeasures.put(forAgent, pm);
		}

		return pm;
	}
	

	public void addEnvironmentView(EnvironmentView ev) {
		views.add(ev);
	}

	public void removeEnvironmentView(EnvironmentView ev) {
		views.remove(ev);
	}

	public void removeAllEnvironmentView() {
		views.clear();
	}
	
	public void notifyViews(String msg) {
		for (EnvironmentView ev : views) {
			ev.notify(msg);
		}
	}

	// END-Environment
	// 
	////////////////////////////////////////////////////////////////////////////////
	//
	// PROTECTED METHODS
	//

	protected void updatePerformanceMeasure(Agent forAgent, double addTo) {
		Double old = getPerformanceMeasure(forAgent);
		if(old==null) old=0.0;
		performanceMeasures.put(forAgent,  + addTo);
	}

	protected void updateEnvironmentViewsAgentAdded(Agent agent) {
		for (EnvironmentView view : views) {
			view.agentAdded(agent, getCurrentState());
		}
	}
	private void updateEnvironmentViewsAgentRemoved(Agent agent) {
		for (EnvironmentView view : views) {
			view.agentRemoved(agent, getCurrentState());
		}
	}
	protected void updateEnvironmentViewsThingAdded(EnvironmentObject eo) {
		for (EnvironmentView view : views) {
			view.thingAdded(eo, getCurrentState());
		}
	}

	private void updateEnvironmentViewsThingRemoved(EnvironmentObject eo) {
		for (EnvironmentView view : views) {
			view.thingRemoved(eo, getCurrentState());
		}
	}

	
	protected void updateEnvironmentViewsAgentActed(Agent agent, Action action, EnvironmentState state) {
		for (EnvironmentView view : views) {
			view.agentActed(agent, action, state);
		}
	}
	
}