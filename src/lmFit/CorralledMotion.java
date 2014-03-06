package lmFit;


public class CorralledMotion implements LMfunc 
{	
	static final int D = 0;
	static final int L = 1;
	//static final int A1 = 1;
	//static final int A2 = 2;
	//static final int D = 3;
	
//	static final int C = 2;

	private double[] initial;
	boolean weighted;
	double[] weights;
	
	public CorralledMotion(double[] initial, boolean weighted, double[] weights)
	{
		this.initial = initial;
		this.weighted = weighted;
		this.weights = weights;
	}
	public double[] initial()
	{
		//double[] a = new double[4];
		/*double[] a = new double[2];
		a[D]  = 0.35;
		a[l] = 4.0;*/
		//a[A1] = 1;
		//a[A2] = 1;
		
		//return a;
		return initial;
	}
	//public double[] initial(double input_rc, double input_A1, double input_A2, double input_D)
	
	public double val(double[] x, double[] a)
	{
		//return a[rc]*(1-a[A1]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc]));
		//return a[rc]*(1-Math.exp(-4*a[D]*x[0]/a[rc]));
//		return a[l] - a[l]*Math.exp(-4*a[D]*x[0]/a[l]);
//		return a[L]*a[L]/3 - a[L]*a[L]*Math.exp(-12*a[D]*x[0]/(a[L]*a[L]))/3;
		return a[L]*a[L] - a[L]*a[L]*Math.exp(-4*a[D]*x[0]/(a[L]*a[L]));
	} //val
	
	public double grad(double[] x, double[] a, int a_k)
	{
		if (a_k == L)
		{
			//return (1-a[A1]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc]))-a[A1]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc])*4*a[A2]*a[D]*x[0]/a[rc];
			//return (1-Math.exp(-4*a[D]*x[0]/a[rc]))-Math.exp(-4*a[D]*x[0]/a[rc])*4*a[D]*x[0]/a[rc];
//			return 1 - Math.exp(-4*a[D]*x[0]/a[L]) - Math.exp(-4*a[D]*x[0]/a[L])*4*a[D]*x[0]/a[L];
//			return a[L]*(2/3 - 2/3*Math.exp(-12*a[D]*x[0]/(a[L]*a[L])))-(8*a[D]*x[0]/a[L])*Math.exp(-12*a[D]*x[0]/(a[L]*a[L]));
			return 2*a[L] - (2*a[L]+ 8*a[D]*x[0]/a[L])*Math.exp(-4*a[D]*x[0]/(a[L]*a[L]));
			
			//return (1-a[A1]*Math.pow(10,-4*a[A2]*a[D]*x[0]/a[rc]))-a[A1]*Math.pow(10,-4*a[A2]*a[D]*x[0]/a[rc])*Math.log(10)*4*a[A2]*a[D]*x[0]/a[rc];
		}
	/*	else if(a_k == A1)
			return -a[rc]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc]);
			
		else if(a_k == A2)
			return a[rc]*a[A1]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc])*4*a[D]*x[0]/a[rc];
			*/
			
		else if(a_k == D)
		{
			//return a[rc]*a[A1]*Math.exp(-4*a[A2]*a[D]*x[0]/a[rc])*4*a[A2]*x[0]/a[rc];
//			return a[L]*Math.exp(-4*a[D]*x[0]/a[L])*4*x[0]/a[L];
//			return (a[L]*a[L]/3)*Math.exp(-12*a[D]*x[0]/(a[L]*a[L]))*(-12*x[0]/(a[L]*a[L]));
			return 4*x[0]*Math.exp(-4*a[D]*x[0]/(a[L]*a[L]));
		}		
		else 
		{
			assert false;
			return 0.;
		}
	} //grad
	
	public Object[] testdata(int npts) 
	{
		//double[] a = new double[4];
		double[] a = new double[2];
		a[D]  = initial[D]+0.02;
		a[L] = initial[L]+0.02;
		//a[A1] = 1.0;
		//a[A2] = 1.0;
		
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
