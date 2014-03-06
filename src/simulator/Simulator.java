package simulator;

import java.util.Random;

import bTools.BMaths;

import main.Thinker;
import Jama.util.Maths;
import data.Metrics;
import data.Particle;
import data.TrajSet;
import data.Trajectory;

public class Simulator 
{
	Thinker thinker;
	GUI gui;
	Metrics metrics;
	
	public Simulator(Thinker thinker)
	{
		this.thinker = thinker;
		gui = new GUI(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void close()
	{
		gui.dispose();
		gui = null;
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void radioButton_stateChanged()
	{
		gui.jTextfield_setEnabled(gui.radioButton_TwoState_isSelected());
	}
	
	public void jButton_Simulate_clicked()
	{
		double timeStep = gui.get_TimeStep();
		double distanceFactor = 1;
		this.metrics = new Metrics(timeStep, distanceFactor, Metrics.SECONDS, Metrics.MICROMETERS);
		
		int nsteps = gui.get_nPoints()-1;
		int ntrajs = gui.get_nTrajs();
		double d1 = gui.get_D1();
		double d2 = gui.get_D2();
		double p12 = gui.get_P12();
		double p21 = gui.get_P21();
		/*if(!gui.radioButton_TwoState_isSelected())
		{
			d2 = 0;
			p12 = 0;
			p21 = 1;
		}*/
		double dc = gui.get_DC();
		double l = gui.get_L();
		double dd = gui.get_DD();
		double v = gui.get_V();
		
//		int dim = 2;
		int[] frames = new int[nsteps+1];
		double[] time = new double[nsteps+1];
		for(int i=0;i<nsteps+1;i++)
		{
			frames[i] = i+1;
			time[i] = i*timeStep; 
		}

		Trajectory[] trajs = new Trajectory[ntrajs];
		if(gui.jCheckBox_Normal_isSelected())
		{
			if(gui.radioButton_TwoState_isSelected())
			{
				for(int i=0;i<ntrajs;i++)
				{
					System.out.println("sim normal 2 state traj "+i);
					trajs[i] =  sim2stateTraj(i+1, 0, 0, nsteps, d1, d2, p12, p21, frames, metrics);
				}
			}
			else
			{
				for(int i=0;i<ntrajs;i++)
				{
					System.out.println("sim normal traj "+i);
					trajs[i] =  simNormalTraj(i+1, 0, 0, nsteps, d1, frames, metrics);
				}
			}
			thinker.addTrajSet(new TrajSet(TrajSet.SIMULATED, "sim","simN D"+d1+"_"+(nsteps+1)+"_"+ntrajs, trajs, metrics));
		}
		if(gui.jCheckBox_Constrained_isSelected())
		{
			for(int i=0;i<ntrajs;i++)
			{
				System.out.println("sim constrained traj "+i);
				trajs[i] =  simConstrainedTraj(i+1, 0, 0, nsteps, dc, l, frames, metrics);
			}
			thinker.addTrajSet(new TrajSet(TrajSet.SIMULATED, "sim","simC D"+dc+"_L"+l+"_"+(nsteps+1)+"_"+ntrajs, trajs, metrics));
		}
		if(gui.jCheckBox_Directed_isSelected())
		{
			for(int i=0;i<ntrajs;i++)
			{
				System.out.println("sim directed traj "+i);
				trajs[i] =  simDirectedTraj(i+1, 0, 0, nsteps, d1, v, frames, metrics);
			}
			thinker.addTrajSet(new TrajSet(TrajSet.SIMULATED, "sim","simD D"+dd+"_V"+v+"_"+(nsteps+1)+"_"+ntrajs, trajs, metrics));
		}
		
		thinker.updateMain();				
		thinker.updateAnalysis();
		gui.setVisible(false);
	}
		
	
	public static Trajectory sim2stateTraj(int index, double initialX, double initialY, int no_of_steps, double d1, double d2, double p12, double p21, int[] frames, Metrics metrics)
	{
		double tau = metrics.getFrameStep();
		double p1 = p21/(p12+p21);
		double p2 = 1-p1;
//		System.out.println("p1:"+p1+" p2:"+p2+" p12:"+p12+" p21:"+p21);
		
		double[] x = new double[no_of_steps+1];
		double[] y = new double[no_of_steps+1];
		double[] dx = new double[no_of_steps];
		double[] dy = new double[no_of_steps];
//		double[] sq_dis = new double[no_of_steps];
//		double[] ssd = new double[no_of_steps+1];
		int[] state = new int[no_of_steps];
//		int[] gState = new int[no_of_steps];
		
		double[] toss = new double[no_of_steps];
		Random random = new Random();
		for(int i=0;i<toss.length;i++)
			toss[i] = random.nextDouble(); 
		
		double stddev1 =  Math.sqrt(2*d1*tau);
		double stddev2 =  Math.sqrt(2*d2*tau);
		
		//Determine initial state
		if(toss[0]>p1)
		{
			state[0]=2;
			dx[0] = 0 + stddev2*random.nextGaussian();
			dy[0] = 0 + stddev2*random.nextGaussian();
		}
		else
		{
			state[0]=1;
			dx[0] = 0 + stddev1*random.nextGaussian();
			dy[0] = 0 + stddev1*random.nextGaussian();
		}
//		dx[0] = Math.sqrt(2*d1*tau)+0.5*random.nextGaussian();
//		dy[0] = Math.sqrt(2*d1*tau)+0.5*random.nextGaussian();
		
		
		x[0] = initialX + stddev1*random.nextGaussian();
		y[0] = initialY + stddev1*random.nextGaussian();
		
		
		
//		sq_dis[0]= x[0]*x[0]+y[0]*y[0];
//		ssd[0]=0;
		
		
		//Determine successive states with appropriate transition probabilities.
		for(int i=1;i<no_of_steps;i++)
		{
			if(state[i-1]==1 && toss[i]<=p12)
			{
				state[i] = 2;
				dx[i] = 0 + stddev2*random.nextGaussian();
				dy[i] = 0 + stddev2*random.nextGaussian();
			}
			else if(state[i-1]==2 && toss[i]<=p21)
			{
				state[i]=1;
				dx[i] = 0 + stddev1*random.nextGaussian();
				dy[i] = 0 + stddev1*random.nextGaussian();
			}
			else
			{
				state[i]=state[i-1];
				if(state[i]==1)
				{
					dx[i] = 0 + stddev1*random.nextGaussian();
					dy[i] = 0 + stddev1*random.nextGaussian();
				}
				else
				{
					dx[i] = 0 + stddev2*random.nextGaussian();
					dy[i] = 0 + stddev2*random.nextGaussian();
				}
			}
//			dx[i] = Math.sqrt(2*d1*tau)+0.5*random.nextGaussian();
//			dy[i] = Math.sqrt(2*d1*tau)+0.5*random.nextGaussian();
		}
		Particle[] particles = new Particle[no_of_steps+1];
		particles[0] = new Particle(x[0],y[0]);
		for(int i=1;i<no_of_steps+1;i++)
		{
//			x[i]+=x[i-1]+dx[i-1];
//			y[i]+=y[i-1]+dy[i-1];
			x[i]=x[i-1]+dx[i-1];
			y[i]=y[i-1]+dy[i-1];
			particles[i] = new Particle(x[i], y[i]);
//			sq_dis[i-1]= dx[i-1]*dx[i-1]+dy[i-1]*dy[i-1];
//			ssd[i] = ssd[i-1]+sq_dis[i-1];  
		}
		Trajectory traj = new Trajectory(index, particles, frames, metrics);
		traj.setState(state);
		return traj;
	}
	
	public static Trajectory simNormalTraj(int index, double initialX, double initialY, int no_of_steps, double d, int[] frames, Metrics metrics)
	{
		double tau = metrics.getFrameStep();
		double[] x = new double[no_of_steps+1];
		double[] y = new double[no_of_steps+1];
		
		Random random = new Random();
		double stddev =  Math.sqrt(2*d*tau);
		
		//Determine initial state
		x[0] = initialX + stddev*random.nextGaussian();
		y[0] = initialY + stddev*random.nextGaussian();
		
		Particle[] particles = new Particle[no_of_steps+1];
		particles[0] = new Particle(x[0],y[0]);
		for(int i=1;i<no_of_steps+1;i++)
		{
			x[i]=x[i-1]+stddev*random.nextGaussian();
			y[i]=y[i-1]+stddev*random.nextGaussian();
//			System.out.println("x:"+x[i]+" y:"+y[i]+" x0:"+x[0]+" y[0]:"+y[0]);
						
			particles[i] = new Particle(x[i], y[i]);
		}
		Trajectory traj = new Trajectory(index, particles, frames, metrics);
		return traj;
	}
	
	public static Trajectory simConstrainedTraj(int index, double initialX, double initialY, int no_of_steps, double d, double l, int[] frames, Metrics metrics)
	{
		double tau = metrics.getFrameStep();
		double[] x = new double[no_of_steps+1];
		double[] y = new double[no_of_steps+1];
		
		Random random = new Random();
		double stddev =  Math.sqrt(2*d*tau);
		
		//Determine initial state
		x[0] = initialX + stddev*random.nextGaussian();
		y[0] = initialY + stddev*random.nextGaussian();
		
		//Determine successive states with appropriate transition probabilities.
		Particle[] particles = new Particle[no_of_steps+1];
		particles[0] = new Particle(x[0],y[0]);
		for(int i=1;i<no_of_steps+1;i++)
		{
			do
			{
				x[i]=x[i-1]+stddev*random.nextGaussian();
				y[i]=y[i-1]+stddev*random.nextGaussian();
//				System.out.println("x:"+x[i]+" y:"+y[i]+" x0:"+x[0]+" y[0]:"+y[0]);
			}
//			while(x[i]>x[0]+l || x[i]<x[0]-l || y[i]>y[0]+l || y[i]<y[0]-l); //Corral cuadrado
			while(BMaths.dist(x[0], y[0], x[i], y[i])>l); //Corral circular
						
			particles[i] = new Particle(x[i], y[i]);
		}
		Trajectory traj = new Trajectory(index, particles, frames, metrics);
		return traj;
	}
	public static Trajectory simDirectedTraj(int index, double initialX, double initialY, int no_of_steps, double d, double v, int[] frames, Metrics metrics)
	{
		double xv = Math.cos(Math.PI*0.125)*v;
		double yv = Math.sin(Math.PI*0.125)*v;
		double tau = metrics.getFrameStep();
		double[] x = new double[no_of_steps+1];
		double[] y = new double[no_of_steps+1];
		
		Random random = new Random();
		double stddev =  Math.sqrt(2*d*tau);
		
		//Determine initial state
		x[0] = initialX + stddev*random.nextGaussian();
		y[0] = initialY + stddev*random.nextGaussian();
		
		//Determine successive states with appropriate transition probabilities.
		Particle[] particles = new Particle[no_of_steps+1];
		particles[0] = new Particle(x[0],y[0]);
		for(int i=1;i<no_of_steps+1;i++)
		{
			x[i]=x[i-1]+stddev*random.nextGaussian() + xv;
			y[i]=y[i-1]+stddev*random.nextGaussian() + yv;
			particles[i] = new Particle(x[i], y[i]);
		}
		Trajectory traj = new Trajectory(index, particles, frames, metrics);
		return traj;
	}
	
	
	
}
