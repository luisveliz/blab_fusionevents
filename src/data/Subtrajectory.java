package data;


public class Subtrajectory extends Trajectory 
{	
	int begin;
	int end;
	int subIndex;
	
	public Subtrajectory(int index, Particle[] particles, int[] frames, Metrics metrics) 
	{
		//TODO arreglar lo del 0
		super(index, particles, frames, metrics);
	}

	public int getStartFrame() {
		return begin;
	}

	public int getEndFrame() {
		return end;
	}
	public int getSubId()
	{
		return subIndex;
	}
	public void setSubIndex(int subIndex)
	{
		this.subIndex = subIndex;
	}
	public void setBeginFrame(int beginFrame)
	{
		this.begin = beginFrame;
	}
	public void setEndFrame(int endFrame)
	{
		this.end = endFrame;
	}
	

}
