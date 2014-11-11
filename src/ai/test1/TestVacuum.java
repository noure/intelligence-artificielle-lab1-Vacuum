package ai.test1;

import org.junit.Test;

import ai.lab1.core.Environment;
import ai.lab1.core.grid.GridEnvironment;
import ai.lab1.core.grid.GridObstacle;
import ai.lab1.core.grid.XYLocation;
import ai.lab1.vacuum.Dirt;
import ai.lab1.vacuum.ModelBasedVacuumAgent;
import ai.lab1.vacuum.SimpleReflexVacuumAgent;
import ai.lab1.vacuum.SimpleVacuumInterface;
import ai.lab1.vacuum.VacuumEnvironment;


public class TestVacuum {



	@Test
	public void testReflexAgentOnSimpleEnvironment(){ 
		GridEnvironment env = (GridEnvironment) getEnv1();
		env.notifyViews("Execution of reflex agent on simple environment ");
		env.addAgent(new SimpleReflexVacuumAgent(), new XYLocation(1,0));
		env.stepUntilDone();
	}

	@Test
	public void testReflexAgentOnComplexEnvironment(){ 
		GridEnvironment env = (GridEnvironment) getEnv2();
		env.notifyViews("Execution of reflex agent on complex environment "); 
		env.addAgent(new SimpleReflexVacuumAgent(), new XYLocation(1,0));
		env.stepUntilDone();
	}
	@Test
	public void testModelBasedAgentOnSimpleEnvironment(){ 
		GridEnvironment env = (GridEnvironment) getEnv1();
		env.notifyViews("Execution of model based agent on simple environment ");  
		env.addAgent(new ModelBasedVacuumAgent(), new XYLocation(1,0));
		env.stepByStepUntilDone();
	}
	@Test
	public void testModelBasedAgentOnComplexEnvironment(){ 
		GridEnvironment env = (GridEnvironment) getEnv1();
		env.notifyViews("Execution of model based agent on complex environment ");  
		env.addAgent(new ModelBasedVacuumAgent(), new XYLocation(1,0));
		env.stepUntilDone();
	}

	@Test
	public void performanceOfSimpleReflexAgentOnComplexEnvironment(){ 
		double performance = 0.0;
		for(int i=0; i<1000; i++){
			GridEnvironment env = (GridEnvironment) getEnv3();
			env.notifyViews("Execution performance of model based agent on complex environment ");  
			SimpleReflexVacuumAgent agent = new SimpleReflexVacuumAgent();
			env.addAgent(agent, new XYLocation(1,0));
			env.step(1000);
			performance+=env.getPerformanceMeasure(agent);
		}
		System.out.println("The performance of Reflex Agent is "+ performance/1000);
	}
	@Test
	public void performanceOfModelBasedAgentOnComplexEnvironment(){ 
		double performance = 0.0;
		for(int i=0; i<1000; i++){
			GridEnvironment env = (GridEnvironment) getEnv3();
			env.notifyViews("Execution performance of model based agent on complex environment ");  
			ModelBasedVacuumAgent agent = new ModelBasedVacuumAgent();
			env.addAgent(agent, new XYLocation(1,0));
			env.step(1000);
			performance+=env.getPerformanceMeasure(agent);
		}
		System.out.println("The performance of Based Model Agent is "+ performance/1000);

	}



	private VacuumEnvironment getEnv(int width, int height){
		SimpleVacuumInterface out = new SimpleVacuumInterface(width,height); 
		VacuumEnvironment env = new VacuumEnvironment(width,height);
		env.addEnvironmentView(out);
		return env;
	}

	/**
	 *
	 * @return   A simple  environment
	 */

	public Environment getEnv1(){ 
		VacuumEnvironment env = getEnv(2,2);

		env.addEnvironmentObject(new GridObstacle(), new XYLocation(0,0));

		env.addEnvironmentObject(new Dirt(),new XYLocation(0,1));
		env.addEnvironmentObject(new Dirt(),new XYLocation(1,0));
		env.addEnvironmentObject(new Dirt(),new XYLocation(1,1));
		return env;
	}

	/**
	 * 
	 * @return More complex environment
	 */
	public Environment getEnv2(){
		VacuumEnvironment env = getEnv(5,5);

		env.addEnvironmentObject(new GridObstacle(), new XYLocation(1,1));
		env.addEnvironmentObject(new GridObstacle(), new XYLocation(1,2));

		env.addEnvironmentObject(new Dirt(),new XYLocation(3,3));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,0));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,4));
		return env;
	}

	public Environment getEnv3(){
		VacuumEnvironment env = new VacuumEnvironment(5,5);  

		env.addEnvironmentObject(new GridObstacle(), new XYLocation(1,1));
		env.addEnvironmentObject(new GridObstacle(), new XYLocation(1,2));

		env.addEnvironmentObject(new Dirt(),new XYLocation(3,3));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,0));
		env.addEnvironmentObject(new Dirt(),new XYLocation(0,4));
		return env; 
	}
}
