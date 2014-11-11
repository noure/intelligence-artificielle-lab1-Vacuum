package ai.lab1.core.grid;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ai.lab1.core.Agent;
import ai.lab1.core.EnvironmentObject;
import ai.lab1.core.EnvironmentState;


public class GridEnvironmentState implements EnvironmentState {
	 
	Map<EnvironmentObject, XYLocation> locations = new HashMap<EnvironmentObject, XYLocation>();
	
	Set<EnvironmentObject> objectsThatChangedStateLately   = new LinkedHashSet<EnvironmentObject>();
	
	public GridEnvironmentState( ) {
 	} 

	public XYLocation getLocation(EnvironmentObject eo) {
 		return this.locations.get(eo);
	}

	public void put(EnvironmentObject eo, XYLocation location) {
		 if(!this.locations.containsKey(eo) || !locations.get(eo).equals(location)){
			 this.locations.put(eo, location);
		 }
	}

	public void changedState(EnvironmentObject eo) {
		this.objectsThatChangedStateLately.add(eo);
	}

	public void remove(EnvironmentObject eo) {
		 this.locations.remove(eo);
 	}

	@Override
	public void reset() {
		this.objectsThatChangedStateLately.clear(); 
	}

	public Set<EnvironmentObject> getThingsAt(XYLocation location) {
		Set<EnvironmentObject>  set = new LinkedHashSet<EnvironmentObject> ();
		for(  Entry<EnvironmentObject, XYLocation> es : this.locations.entrySet()){
			if(es.getValue().equals(location) && !(es.getKey() instanceof Agent) ){
				set.add(es.getKey());
			}
		}
		return set;
		
	} 
 

}
