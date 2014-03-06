package lmFit;


public class DirectedMotionWithOffset implements LMfunc
{
	static final int D = 0;
	static final int V = 1;	
	static final int C = 2;
	private double[] initial;
	boolean weighted;
	double[] weights;
	public DirectedMotionWithOffset(double[] initial, boolean weighted, double[] weights)
	{
		this.initial=initial;
		this.weighted = weighted;
		this.weights = weights;
	}
	public double[] initial()
	{
		/*double[] a = new double[2];
		a[D] = 1.5;
		a[V] = 0.3;*/
		return initial;
	} //initial
	
	public double val(double[] x, double[] a)
	{
		return 4*a[D]*x[0]+Math.pow(a[V],2)*Math.pow(x[0],2)+a[C];//4Dt+v^2*t^2+c
	} //val
	
	public double grad(double[] x, double[] a, int a_k)
	{
		if (a_k == D)
			return 4*x[0]+Math.pow(a[V],2)*Math.pow(x[0],2);			
		else if(a_k == V)
			return 4*a[D]*x[0]+2*a[V]*Math.pow(x[0],2);		
		else if(a_k == C)
			return 1;//4Dt+v^2*t^2+c
		else 
		{
			assert false;
			return 0.;
		}
	} //grad
	
	public Object[] testdata(int npts) 
	{
		double[] a = new double[3];
		a[D] = 1.5;
		a[V] = 0.5;
		a[C] = 0.01;
	
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