package ai.lab1.vacuum;

import java.util.LinkedHashSet;
import java.util.Set;

import ai.lab1.core.Action;
import ai.lab1.core.Model;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.XYLocation;
import ai.lab1.core.grid.GridEnvironment.Orientation;
import ai.lab1.core.grid.XYLocation.Direction;


public class VacuumModel implements Model {

	Set<XYLocation> visitedCellsTrack = new LinkedHashSet<XYLocation>();

	Set<XYLocation> obstacleLocationTrack = new LinkedHashSet<XYLocation>();

	ModelBasedVacuumAgent agent;

	private XYLocation currentLocation;

	private Action previousAction;


	public VacuumModel(ModelBasedVacuumAgent agent) {
		super();
		this.agent = agent;
	}

	public boolean canGoForward(XYLocation agentLocation) { 
		
		/**
		 * s’assure de ne pas foncer à nouveau sur un mur ou un obstacle
		 *  lorsqu’il revient à une position qu’il a déjà visitée
		 *   (ex : mémorisation de la topologie de l’environnement).
		 *     Aussi, il essaie de ne pas revenir à une position 
		 *     déjà visitée. Plus particulièrement, s’il s’apprête à se déplacer
		 *      à une position déjà visitée, il s’en empêchera si au moins une des
		 *      trois autres positions adjacentes n’a pas été visitée
		 *      mais accessible (ce n’est ni un mur, ni un obstacle).
		 *      
		 *    
		 *     - 1. calculer la nouvelle posision 
		 *     - 2. si obstacle retourner false
		 *     - 3  si pas-visité retourner true
		 *     - 4. sinon
		 *             si au moins une cellule n'ai pas visité 
		 *     			   return false;
		 *      -     sinon return true 
		 *     
		 */
		 

	} 
 
	public void update(Action action, Percept percept ) {
		
		/*
		 * A Completer 
		 * mettre a jours 
		 *    obstacleLocationTrack: if previousAction==FORWARD && currentLocation!=AgentLocation 
		 *    							then obstacle in currentLocation  
		 *    visitedCellsTrack:    if previousAction==FORWARD && urrentLocation==AgentLocation 
		 *   							then  currentLocation is visited
		 *    previousAction, is the given action
		 *    currentLocation, to compute. it depends to agent orientation and FORWARD action
		 */
		 
		  
	}



}
