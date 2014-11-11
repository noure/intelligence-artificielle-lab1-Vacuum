package ai.lab1.vacuum;

import java.util.LinkedHashSet;
import java.util.Set;

import ai.lab1.core.Action;
import ai.lab1.core.Model;
import ai.lab1.core.Percept;
import ai.lab1.core.grid.GridEnvironment;
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
		 * Done !
		 */

		Direction direction = directionFromOrientation(agent.getOriontation());

		XYLocation loc = agentLocation.locationAt(direction);

		if (obstacleLocationTrack.contains(loc)) {
			return false;
		}

		if (!visitedCellsTrack.contains(loc)) {
			return true;
		}

		XYLocation curLocation = agentLocation.west();

		if (!curLocation.equals(loc)
				&& !visitedCellsTrack.contains(curLocation)) {
			return false;

		}

		curLocation = agentLocation.up();
		if (!curLocation.equals(loc)
				&& !visitedCellsTrack.contains(curLocation)) {
			return false;
		}

		curLocation = agentLocation.east();
		if (!curLocation.equals(loc)
				&& !visitedCellsTrack.contains(curLocation)) {
			return false;
		}

		curLocation = agentLocation.down();
		if (!curLocation.equals(loc)
				&& !visitedCellsTrack.contains(curLocation)) {
			return false;
		}

		return true;

		/**
		 * s�assure de ne pas foncer � nouveau sur un mur ou un obstacle
		 * lorsqu�il revient � une position qu�il a d�j� visit�e (ex :
		 * m�morisation de la topologie de l�environnement). Aussi, il essaie de
		 * ne pas revenir � une position d�j� visit�e. Plus particuli�rement,
		 * s�il s�appr�te � se d�placer � une position d�j� visit�e, il s�en
		 * emp�chera si au moins une des trois autres positions adjacentes n�a
		 * pas �t� visit�e mais accessible (ce n�est ni un mur, ni un obstacle).
		 * 
		 * 
		 * - 1. calculer la nouvelle posision - 2. si obstacle retourner false -
		 * 3 si pas-visit� retourner true - 4. sinon si au moins une cellule
		 * n'ai pas visit� return false; - sinon return true
		 * 
		 */

	}

	public void update(Action action, Percept percept) {

		VacuumPercept vacuumPercept = (VacuumPercept) percept;

		if (GridEnvironment.FORWARD.equals(previousAction)
				&& vacuumPercept.getAgentLocation().equals(currentLocation)) {
			visitedCellsTrack.add(currentLocation);
		}

		if (GridEnvironment.FORWARD.equals(previousAction)
				&& !vacuumPercept.getAgentLocation().equals(currentLocation)) {
			obstacleLocationTrack.add(currentLocation);
		}

		if (action.equals(GridEnvironment.FORWARD)) {
			Direction direction = directionFromOrientation(agent
					.getOriontation());
			currentLocation = vacuumPercept.getAgentLocation().locationAt(
					direction);
		}
		previousAction = action;

		/** done aussi
		 * 
		 * A Completer mettre a jours obstacleLocationTrack: if
		 * previousAction==FORWARD && currentLocation!=AgentLocation then
		 * obstacle in currentLocation visitedCellsTrack: if
		 * previousAction==FORWARD && urrentLocation==AgentLocation then
		 * currentLocation is visited previousAction, is the given action
		 * currentLocation, to compute. it depends to agent orientation and
		 * FORWARD action
		 */

	}

	private Direction directionFromOrientation(Orientation oriontation) {
		// Orientation up = Orientation.UP;
		switch (oriontation) {
		case UP:
			return Direction.North;

		case DOWN:
			return Direction.South;

		case RIGTH:
			return Direction.East;

		case LEFT:
			return Direction.West;

		default:
			return null;
		}

	}
}
