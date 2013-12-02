package lmFit;

public class Exponetial implements LMfunc
{
	static final int A = 0;
	static final int K = 1;
	
	public double val(double[] x, double[] a)
	{
		return a[A]*Math.exp(a[K]*x[0]);
	} //val
	
	public double grad(double[] x, double[] a, int a_k)
	{
		if (a_k == K)
			return a[A]*x[0]*Math.exp(a[K]*x[0]);
		else 
		{
			assert false;
			return 0.;
		}
	} //grad
	public double[] initial()
	{
		double[] a = new double[2];
		a[A] = 100;
		a[K] = 1;
		return a;
	} //initial
	
	public Object[] testdata(int npts) 
	{
		double[] a = new double[2];
		a[A] = 99;
		a[K] = 0.9;
	
		//int npts = 100;
		double[][] x = new double[npts][1];
		double[] y = new double[npts];
		double[] s = new double[npts];
		for( int i = 0; i < npts; i++ ) 
		{				
			x[i][0] = (double)i;
			y[i] = val(x[i], a);
			//s[i] = 1.0-(i/npts);
			s[i] = 1.0;
		}
	
		Object[] o = new Object[4];
		o[0] = x;
		o[1] = a;
		o[2] = y;
		o[3] = s;
		
		return o;
	} //test
}