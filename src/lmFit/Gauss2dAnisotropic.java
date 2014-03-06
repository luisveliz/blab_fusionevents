package lmFit;


public class Gauss2dAnisotropic implements LMfunc{
    public double amplitude; 	// max int
    public int x0;
    public int y0;
    public double sigmax;
    public double sigmay;
    public double[][] xy;
    public double[] z;
    
    public Gauss2dAnisotropic(double[][] xy, double[] z,double amplitude, int x0, int y0, double sigx,double sigy){
    	this.xy=xy;
    	this.z=z;
    	this.amplitude=amplitude;
    	this.x0=x0;
    	this.y0=y0;
    	this.sigmax=sigx;
    	this.sigmay=sigy;
    }
    
    public double val(double[] x, double[] a)
    {
      double sigx=a[0];
      double sigy=a[1];
      double xcuad=(x[0]-x0)*(x[0]-x0);
      double ycuad=(x[1]-y0)*(x[1]-y0);
      return amplitude*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
    } //val



    public double grad(double[] x, double[] a, int a_k)
    {
      assert x.length == 2;
      assert a.length == 2;
      double xcuad=(x[0]-x0)*(x[0]-x0);
	      double ycuad=(x[1]-y0)*(x[1]-y0);
	      double sigx=a[0];
	      double sigy=a[1];
	      if (a_k==0){//sigma_x
	    	double sigxcuad=sigx*sigx;
	    	double sigxtres=sigx*sigxcuad;
	    	return (amplitude/sigxtres)*(xcuad)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	      }
	      else{//a_k==1 sigma_y
	    	double sigycuad=sigy*sigy;
	    	double sigytres=sigy*sigycuad;
	    	return (amplitude/sigytres)*(ycuad)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	      }
    }

      

    //grad


    public double[] initial()
    {
      double[] a = new double[2];
      a[0] = sigmax;
      a[1] = sigmay;
      
      return a;
    } //initial


    public Object[] testdata(int npts)
    {
      Object[] o = new Object[4];
      double[][] x = new double[npts][2];
      double[] y = new double[npts];
      double[] s = new double[npts];
      double[] a = new double[2];
      //Initializing both sigma values (x and y) on 1.0
      a[0] = 1.0;	
      a[1] = 1.0;
      xy=x;
      z=y;
      for( int i = 0; i < npts; i++ ) {
    	  s[i] =1.0;
      }

      o[0] = x;
      o[1] = a;
      o[2] = y;
      o[3] = s;

      return o;
    } //testdata

    //LMGaussTest

}




