package lmFit;

public class NormalDiff implements LMfunc 
{
	static final int D = 0;
	boolean weighted;
	double[] weights;
	
	public NormalDiff(boolean weighted, double[] weights)
	{
		this.weighted = weighted;
		this.weights = weights;
	}
	
	public double[] initial()
	{
		double[] a = new double[1];
		a[D] = 1.;
		return a;
	} //initial

	public double val(double[] x, double[] a)
	{
		return 4*a[D]*x[0];
	} //val
	
	public double grad(double[] x, double[] a, int a_k)
	{
		if (a_k == D)
			return 4*x[0];
		else 
		{
			assert false;
			return 0.;
		}
	} //grad
	
	public Object[] testdata(int npts) 
	{
		double[] a = new double[1];
		a[D] = 1.0;
	
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