package lmFit;

public class Gauss2dIsotropic implements LMfunc{

	    public double amplitude; 	// max int
	    public int x0;
	    public int y0;
	    public double sigma;
	    public double[][] xy;
	    public double[] z;
	    public double b;
	    public double[] limits;
	    
	    public Gauss2dIsotropic(double[][] xy, double[] z,double amplitude, int x0, int y0, double sig, double b){
	    	this.xy=xy;
	    	this.z=z;
	    	this.amplitude=amplitude;
	    	this.x0=x0;
	    	this.y0=y0;
	    	this.sigma=sig;
	    	this.b=b;

	    }
	    
	    public double val(double[] x, double[] a)
	    {
	      double sig=a[0];
	      double x0fit=a[1];
	      double y0fit=a[2];
	      double bfit=a[3];
	      double ampfit=a[4];
	      double xcuad=(x[0]-x0fit)*(x[0]-x0fit);
	      double ycuad=(x[1]-y0fit)*(x[1]-y0fit);
	      return (ampfit*Math.exp(-(xcuad+ycuad)/(2*sig*sig)))+bfit;
	    } //val



	    public double grad(double[] x, double[] a, int a_k)
	    {
	      assert x.length == 2;
	      assert a.length == 5;
	      
	      double sig=a[0];
		  double x0fit=a[1];
	      double y0fit=a[2];
		  double ampfit=a[4];
		  double xcuad=(x[0]-x0fit)*(x[0]-x0fit);
		  double ycuad=(x[1]-y0fit)*(x[1]-y0fit);
		  double sigcuad,sigtres;
	      if (a_k==0){//sigma
	    	sigcuad=sig*sig;
	    	sigtres=sig*sigcuad;
	    	return (ampfit/sigtres)*(xcuad+ycuad)*Math.exp(-((xcuad+ycuad)/(2*sigcuad)));
	      }
	      else{//a_k==1 mu_x
	    	if (a_k==1){
		    	sigcuad=sig*sig;
		    	return (ampfit/sigcuad)*(x[0]-x0fit)*Math.exp(-((xcuad+ycuad)/(2*sigcuad)));
		    	
	    	}else{
	    		if (a_k==2){//a_k==2 mu_y
	    			sigcuad=sig*sig;
			    	return (ampfit/sigcuad)*(x[1]-y0fit)*Math.exp(-((xcuad+ycuad)/(2*sigcuad)));
	    		}else{
	    			if (a_k==3){//a_k==3 b
	    				return 1;
	    			}else{//a_k==4 amp
	    				sigcuad=sig*sig;
	    				return Math.exp(-((xcuad+ycuad)/(2*sigcuad)));
	    			}
	    		}
	    	}

	      }
	    }

	      

	    //grad


	    public double[] initial()
	    {
	      double[] a = new double[5];
	      a[0] = sigma;
	      a[1] = x0;
	      a[2] = y0;
	      a[3] = b;
	      a[4] = amplitude;
	      
	      return a;
	    } //initial


	    public Object[] testdata(int npts)
	    {
	      Object[] o = new Object[4];
	      double[][] x = new double[npts][2];
	      double[] y = new double[npts];
	      double[] s = new double[npts];
	      double[] a = new double[5];

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
