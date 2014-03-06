package twoStateDiffusion;

import java.util.Random;

public class HMMTraj
{
	public double[] x;//position in x
	public double[] y;//position in y
	public double[] dx;//displacement in x
	public double[] dy;//displacement in y
	public double[] sq_dis;//displacements r²
	public double[] ssd;
	public int[] state;//state 0 or 1 //only for simulated trajs
	public int[] gState;
	
	//Dodo's TwoStateSPT()
	public HMMTraj(int no_of_steps, int dim, double tau, double d1, double d2, double p12, double p21)
	{
		double p1 = p21/(p12+p21);
		double p2 = 1-p1;
		System.out.println("p1:"+p1+" p2:"+p2+" p12:"+p12+" p21:"+p21);
		x = new double[no_of_steps+1];
		y = new double[no_of_steps+1];
		dx = new double[no_of_steps];
		dy = new double[no_of_steps];
		sq_dis = new double[no_of_steps];
		ssd = new double[no_of_steps+1];
		state = new int[no_of_steps];
		gState = new int[no_of_steps];
		
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
		x[0] = 0;
		y[0] = 0;
		sq_dis[0]= x[0]*x[0]+y[0]*y[0];
		ssd[0]=0;
		
		
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
		}
		for(int i=1;i<no_of_steps+1;i++)
		{
			x[i]+=x[i-1]+dx[i-1];
			y[i]+=y[i-1]+dy[i-1];
			sq_dis[i-1] = dx[i-1]*dx[i-1] + dy[i-1]*dy[i-1];
			ssd[i] = ssd[i-1] + sq_dis[i-1];  
		}
	}
	public HMMTraj(double[] x, double[] y)
	{
		this.x = x;
		this.y = y;
		dx = new double[x.length-1];
		dy =  new double[y.length-1];
		sq_dis = new double[x.length-1];
		//state = new int[x.length-1];
		gState = new int[x.length-1];
		ssd = new double[x.length];
		
		//sq_dis[0]= dx[0]*x[0]+y[0]*y[0];
		ssd[0]=0;
		//state[0]=1;
		
		for(int i=1;i<x.length;i++)
		{
			System.out.println("i"+i);
			dx[i-1]=x[i]-x[i-1];
			dy[i-1]=y[i]-y[i-1];
			sq_dis[i-1]= dx[i-1]*dx[i-1]+dy[i-1]*dy[i-1];
			ssd[i] = ssd[i-1]+sq_dis[i-1];
			gState[i-1]=1;
		}
	}
	public HMMTraj(double[] sq_dis)
	{
		this.sq_dis=sq_dis;
	}
	public double[] getX() {
		return x;
	}
	public double[] getY() {
		return y;
	}
	public double[] getDx() {
		return dx;
	}
	public double[] getDy() {
		return dy;
	}
	public double[] getSq_dis() {
		return sq_dis;
	}
	public int[] getState() {
		return state;
	}
	public void setState(int[] state)
	{
		this.state = state;
	}
	public int[] getGState() {
		return gState;
	}
	public void setGState(int[] state)
	{
		this.gState = state;
	}
}
