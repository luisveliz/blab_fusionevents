package lmFit;


public class AnomalousDiffusion implements LMfunc
{
	static final int D = 0;
	static final int alfa = 1;
	//static final int C = 2;
	
	
	double[] initial;
	boolean weighted;
	double[] weights;
	
	public AnomalousDiffusion(double[] initial, boolean weighted, double[] weights)
	{
		this.initial = initial;
		this.weighted = weighted;		
		this.weights = weights;
	}
	
	public double val(double[] x, double[] a)
	{
		//return 4*a[D]*Math.pow(x[0], a[alfa]) + a[C];//4D(t^alfa)+C
		return 4*a[D]*Math.pow(x[0], a[alfa]);
	} //val
	
	public double grad(double[] x, double[] a, int a_k)
	{
		if (a_k == D)
			return 4*Math.pow(x[0], a[alfa]);
		else if(a_k == alfa)
			if(x[0]!=0)
				return (4*a[D]*Math.pow(x[0], a[alfa]))*Math.log(x[0]);
			else
				return (4*a[D]*Math.pow(0.000001, a[alfa]))*Math.log(0.000001);
		/*else if(a_k == C)
		{
			return 1;
		}*/
		else 
		{
			assert false;
			return 0.;
		}
	} //grad
	public double[] initial()
	{
//		double[] a = new double[2];
//		a[D] = 0.8;
//		a[alfa] = 0.5;
//		//a[C] = 0.0;
//		return a;
		return initial;
	} //initial
	
	public Object[] testdata(int npts) 
	{
		double[] a = new double[2];
		a[D] = initial[D]+0.02;
		a[alfa] = initial[alfa]+0.02;
		//a[C] = 0.0;
	
		//int npts = 100;
		double[][] x = new double[npts][1];
		double[] y = new double[npts];
		double[] s = new double[npts];
		for( int i = 0; i < npts; i++ ) 
		{				
			x[i][0] = (double)i;
			y[i] = val(x[i], a);
			if(weighted) s[i] = weights[i];
			else s[i] = 1.0;
		}
		Object[] o = new Object[4];
		o[0] = x;
		o[1] = a;
		o[2] = y;
		o[3] = s;
		
		return o;
	} //test
}
