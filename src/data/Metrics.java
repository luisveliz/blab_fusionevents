package data;



import java.text.NumberFormat;

/**
 * @author Luis
 *
 */
public class Metrics 
{
	public final static int SECONDS=0;
	public final static int MILISECONDS=1;
	public final static int MICROSECONDS=2;
		
	public final static int MILIMETERS=3;
	public final static int MICROMETERS=4;
	public final static int NANOMETERS=5;
	
	public final static int FRAME=6;
	public final static int PX=7;
	
	private double distanceFactor;
	private double frameStep;
	private int distanceUnit;
	private int timeUnit;
	
	public Metrics()
	{
		distanceFactor = 1;
		frameStep = 1;
		distanceUnit = PX;
		timeUnit = FRAME;
	}
	
	public Metrics(double frameStep, double distanceFactor, int timeUnit, int distanceUnit)
	{
		this.frameStep = frameStep;
		this.distanceFactor = distanceFactor;
		this.timeUnit = timeUnit;
		this.distanceUnit = distanceUnit;
	}
	
	public static String unitToString(int unit)
	{
		switch(unit)
		{
			case 0:
				return "seconds";
			case 1:
				return "miliseconds";
			case 2:
				return "microseconds";
			case 3:
				return "milimeters";
			case 4:
				return "micrometers";
			case 5:
				return "nanometers";
			case 6:
				return "frame";
			case 7:
				return "px";				
			default:
				return "default";
		}		
	}
	public double[] framesToTime(int[] frames)
	{
		double[] time = new double[frames.length];
		time[0]=0;
		for(int i=1;i<frames.length;i++)
			time[i]=(frames[i]-1)*frameStep;			
		return time;
	}
	public int getDistUnit()
	{
		return distanceUnit;
	}
	
	public String getDistUnittoString()
	{
		switch(distanceUnit)
		{
			case MILIMETERS:
				return "mm";
			case MICROMETERS:
				return "um";				
			case NANOMETERS:
				return "nm";
			case PX:
				return "px";
		default:
				return "default!!";
		}
	}
	public int getTimeUnit()
	{
		return timeUnit;
	}
	public String getTimeUnittoString()
	{
		switch(timeUnit)
		{
			case SECONDS:
				return "sec";
			case MILISECONDS:
				return "msec";				
			case MICROSECONDS:
				return "usec";
			case FRAME:
				return "frame";
		default:
				return "default!!";
		}
	}
	public String getVelUnit()
	{
		return getDistUnittoString()+"/"+getTimeUnittoString();
	}
	public String getDifUnit()
	{
		return getDistUnittoString()+"^2/"+getTimeUnittoString();		
	}

	
	public double getDistanceFactor() {
		return distanceFactor;
	}

	public double getFrameStep() {
		return frameStep;
	}

}
