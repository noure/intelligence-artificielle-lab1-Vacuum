package ai.lab1.vacuum;

import java.util.HashMap;
import java.util.LinkedList;

import ai.lab1.core.Action;
import ai.lab1.core.Agent;
import ai.lab1.core.EnvironmentObject;
import ai.lab1.core.EnvironmentState;
import ai.lab1.core.EnvironmentView;
import ai.lab1.core.Obstacle;
import ai.lab1.core.grid.GridAgent;
import ai.lab1.core.grid.GridEnvironmentState;
import ai.lab1.core.grid.XYLocation;


public class SimpleVacuumInterface   implements EnvironmentView{

	private int width;

	private int height;

	private HashMap<XYLocation, LinkedList<EnvironmentObject>> content;

	private HashMap<Integer, XYLocation> things ;

	static final String [][] OBJECT_VIEW = {
		{"       ", "   *   ", "       "},//Dirty (Clean : "       ", "       ", "       ")
		{"*******", "*******", "*******"},//Obstacle 
		{"   ~   ", " ~ A ~ ", "   ~   "},//Collision
		{"   ^   ", "   A   ", "       "},//up               enum Oriontation {UP, RIGTH, DOWN, LEFT, NONE}
		{"       ", "   A > ", "       "},//rigth
		{"       ", "   A   ", "   v   "},//down
		{"       ", " < A   ", "       "},//left
		{"   ^   ", " < A > ", "   v   "}//Error
	};

	public SimpleVacuumInterface(int width, int height) {
		this.width = width;
		this.height = height;
		this.content = new HashMap<XYLocation,LinkedList<EnvironmentObject>>();
		for(int i = 0; i< this.width; i++){
			for(int j=0; j< this.height; j++){
				XYLocation location = new XYLocation(i, j);
				this.content.put(location, new LinkedList<EnvironmentObject>());
			}
		}
		this.things = new HashMap<Integer, XYLocation>();
	}

	public void clear(){
		this.content.clear();
	}

	public void show(){
		printLine();
		for(int row=0; row < height; row ++){
			this.printCells(row);
			this.printLine();
		}
	}

	private void printCells(int row) {
		for (int i=0; i < 3; i++){
			System.out.println("|" + this.cellsRow(i, row, 0)); 
		}
	}

	private String cellsRow(int i, int row, int col) {
		if (col ==  width){
			return "";
		}
		else{
			LinkedList<EnvironmentObject> objectAtPos = this.content.get(new XYLocation(col, row));
			String segment = "       ";
			if(!objectAtPos.isEmpty() ){
				
				EnvironmentObject last = objectAtPos.peekLast();
				segment = image(last, i);
			} 
			return segment + "|" +  cellsRow(i, row, col+1);
		}
	}

	private String image(EnvironmentObject o, int i) {
		if(o instanceof Dirt ){
			return OBJECT_VIEW[0][i];
		}
		if(o instanceof Obstacle){
			return OBJECT_VIEW[1][i];
		}
		GridAgent agent = (GridAgent) o;
		if(agent.getOriontation() == null){
			return  OBJECT_VIEW[7][i];
		}
		if(agent.isCollision()){
			return  OBJECT_VIEW[2][i];
		} 
		return OBJECT_VIEW[agent.getOriontation().ordinal() + 3][i]; 
	}

	private void printLine() {
		System.out.println(" " + this.repeat("------- ", this.width)); 
	}

	private String repeat(String s, int n) { 
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		for(int i=n; i > 1; i--)
			sb.append(s); 
		return sb.toString();
	}
 
	@Override
	public void notify(String msg) { 
		System.out.println(msg);
	}

	@Override
	public void agentAdded(Agent agent, EnvironmentState resultingState) {
		GridEnvironmentState state = (GridEnvironmentState) resultingState;
		XYLocation location = state.getLocation(agent);
//		if(!this.content.containsKey(location)){
//			this.content.put(location, new LinkedList<EnvironmentObject>());
//		}
		this.content.get(location).add(agent);
		this.things.put(agent.getId(), location);
		show();
	}

	@Override
	public void agentRemoved(Agent agent, EnvironmentState currentState) {
		XYLocation location =this.things.get(agent.getId());
//		if(this.content.containsKey(location)){ 
			this.content.get(location).remove(agent);
//			if(this.content.get(location).isEmpty()){
//				this.content.remove(location);
//			}
//		} 
		this.things.remove(agent.getId()); 
		show();
	}

	@Override
	public void thingAdded(EnvironmentObject eo, EnvironmentState resultingState) {
		GridEnvironmentState state = (GridEnvironmentState) resultingState;
		XYLocation location = state.getLocation(eo);
//		if(!this.content.containsKey(location)){
//			this.content.put(location, new LinkedList<EnvironmentObject>());
//		}
		this.content.get(location).add(eo);
		this.things.put(eo.getId(), location);
		show();
	}

	@Override
	public void thingRemoved(EnvironmentObject eo, EnvironmentState resultingState) {
		XYLocation location = this.things.get(eo.getId()); 
//		if(this.content.containsKey(location)){ 
			this.content.get(location).remove(eo); 
//			if(this.content.get(location).isEmpty()){
//				this.content.remove(location);
//			}
//		}
		this.things.remove(eo.getId());
		show();
	}

	@Override
	public void agentActed(Agent agent, Action action, EnvironmentState resultingState) {
		GridEnvironmentState state = (GridEnvironmentState) resultingState;
		XYLocation  location = state.getLocation(agent);
		XYLocation  currentLocation = this.things.get(agent.getId());
		if(!location.equals(currentLocation)){
			this.content.get(location).add(agent);
			this.content.get(currentLocation).remove(agent);
			this.things.put(agent.getId(), location); 
		}
		this.show() ;
	}
}