package typeOfMotion;

public class Evaluator 
{
	public static double[] funcNormal(double[] x, double d)
	{
		double[] m = new double[x.length];
		for(int i=0;i<x.length;i++)
			m[i]=4*d*x[i];
		return m;		
	}	
	public static double[] funcNormalWO(double[] x, double d, double c)
	{
		double[] m = new double[x.length];
		for(int i=0;i<x.length;i++)
			m[i]=4*d*x[i]+c;
		return m;		
	}	
	public static double[] funcCorralled(double[] x, double d, double L)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
//			m[i]=(L*L/3)*(1-Math.exp(-12*d*x[i]/(L*L)));
//			m[i]=(L*L)*(1-Math.exp(-4*d*x[i]/(L*L)));
			m[i]=(L*L)*(1-0.99*Math.exp(-4*0.85*d*x[i]/(L*L)));
		}
		return m;		
	}
	public static double[] funcCorralledWO(double[] x, double d, double L, double c)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
//			m[i]=(L*L/3)*(1-Math.exp(-12*d*x[i]/(L*L)))+c;
			m[i]=(L*L)*(1-Math.exp(-4*d*x[i]/(L*L)))+c;
		}
		return m;		
	}
	public static double[] funcAnomalous(double[] x, double d, double alfa)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
			m[i]=4*d*Math.pow(x[i],alfa);
		}
		return m;		
	}
	public static double[] funcAnomalousWO(double[] x, double d, double alfa, double c)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
			m[i]=4*d*Math.pow(x[i],alfa)+c;
		}
		return m;		
	}
	
	public static double[] funcDirected(double[] x, double d, double v)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
			m[i]=4*d*x[i]+Math.pow(v*x[i], 2);
		}
		return m;		
	}
	public static double[] funcDirectedWO(double[] x, double d, double v, double c)
	{
		double[] m= new double[x.length];
		for(int i=0;i<x.length;i++)
		{
			m[i]=4*d*x[i]+Math.pow(v*x[i], 2)+c;
		}
		return m;		
	}

}
