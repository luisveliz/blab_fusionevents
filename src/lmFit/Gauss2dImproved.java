package lmFit;

public class Gauss2dImproved implements LMfunc{

	    public double amplitude; 	// max int
	    public int x0;
	    public int y0;
	    public double sigmax;
	    public double sigmay;
	    public double[][] xy;
	    public double[] z;
	    public double b;
	    public double[] limits;
	    
	    public Gauss2dImproved(double[][] xy, double[] z,double amplitude, int x0, int y0, double sigx,double sigy,double b){
	    	this.xy=xy;
	    	this.z=z;
	    	this.amplitude=amplitude;
	    	this.x0=x0;
	    	this.y0=y0;
	    	this.sigmax=sigx;
	    	this.sigmay=sigy;
	    	this.b=b;

	    }
	    
	    public double val(double[] x, double[] a)
	    {
	      double sigx=a[0];
	      double sigy=a[1];
	      double x0fit=a[2];
	      double y0fit=a[3];
	      double bfit=a[4];
	      double ampfit=a[5];
	      double xcuad=(x[0]-x0fit)*(x[0]-x0fit);
	      double ycuad=(x[1]-y0fit)*(x[1]-y0fit);
	      return (ampfit*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy)))))+bfit;
	    } //val



	    public double grad(double[] x, double[] a, int a_k)
	    {
	      assert x.length == 2;
	      assert a.length == 6;
	      
	      double sigx=a[0];
		  double sigy=a[1];
		  double x0fit=a[2];
	      double y0fit=a[3];
		  double ampfit=a[5];
		  double xcuad=(x[0]-x0fit)*(x[0]-x0fit);
		  double ycuad=(x[1]-y0fit)*(x[1]-y0fit);
		  double sigxcuad,sigxtres,sigycuad,sigytres;
	      if (a_k==0){//sigma_x
	    	sigxcuad=sigx*sigx;
	    	sigxtres=sigx*sigxcuad;
	    	return (ampfit/sigxtres)*(xcuad)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	      }
	      else{//a_k==1 sigma_y
	    	if (a_k==1){
		    	sigycuad=sigy*sigy;
		    	sigytres=sigy*sigycuad;
		    	return (ampfit/sigytres)*(ycuad)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	    	}else{
	    		if (a_k==2){
	    			sigxcuad=sigx*sigx;
	    			return (ampfit/sigxcuad)*(x[0]-x0fit)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	    		}else{
	    			if (a_k==3){
	    				sigycuad=sigy*sigy;
	    				return (ampfit/sigycuad)*(x[1]-y0fit)*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	    			}else{
	    				if (a_k==4){
	    					return 1;
	    				}else{
	    					return Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy))));
	    				}
	    			}
	    		}
	    	}

	      }
	    }

	      

	    //grad


	    public double[] initial()
	    {
	      double[] a = new double[6];
	      a[0] = sigmax;
	      a[1] = sigmay;
	      a[2] = x0;
	      a[3] = y0;
	      a[4] = b;
	      a[5] = amplitude;
	      
	      return a;
	    } //initial


	    public Object[] testdata(int npts)
	    {
	      Object[] o = new Object[4];
	      double[][] x = new double[npts][2];
	      double[] y = new double[npts];
	      double[] s = new double[npts];
	      double[] a = new double[6];

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
