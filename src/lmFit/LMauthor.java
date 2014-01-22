package lmFit;

// levenberg-marquardt in java 
//
// To use this, implement the functions in the LMfunc interface.
//
// This library uses simple matrix routines from the JAMA java matrix package,
// which is in the public domain.  Reference:
//    http://math.nist.gov/javanumerics/jama/
// (JAMA has a matrix object class.  An earlier library JNL, which is no longer
// available, represented matrices as low-level arrays.  Several years 
// ago the performance of JNL matrix code was better than that of JAMA,
// though improvements in java compilers may have fixed this by now.)
//
// One further recommendation would be to use an inverse based
// on Choleski decomposition, which is easy to implement and
// suitable for the symmetric inverse required here.  There is a choleski
// routine at idiom.com/~zilla.
//
// If you make an improved version, please consider adding your
// name to it ("modified by ...") and send it back to me
// (and put it on the web).
//
// ----------------------------------------------------------------
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Library General Public
// License as published by the Free Software Foundation; either
// version 2 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Library General Public License for more details.
// 
// You should have received a copy of the GNU Library General Public
// License along with this library; if not, write to the
// Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA  02111-1307, USA.
//
// initial author contact info:  
// jplewis  www.idiom.com/~zilla  zilla # computer.org,   #=at
//
// Improvements by:
// dscherba  www.ncsa.uiuc.edu/~dscherba  
// Jonathan Jackson   j.jackson # ucl.ac.uk


//package ZS.Solve;

// see comment above


import Jama.Matrix;



/**
 * Levenberg-Marquardt, implemented from the general description
 * in Numerical Recipes (NR), then tweaked slightly to mostly
 * match the results of their code.
 * Use for nonlinear least squares assuming Gaussian errors.
 *
 * TODO this holds some parameters fixed by simply not updating them.
 * this may be ok if the number if fixed parameters is small,
 * but if the number of varying parameters is larger it would
 * be more efficient to make a smaller hessian involving only
 * the variables.
 *
 * The NR code assumes a statistical context, e.g. returns
 * covariance of parameter errors; we do not do this.
 */
public final class LMauthor
{
	/**
	 * calculate the current sum-squared-error
	 * (Chi-squared is the distribution of squared Gaussian errors,
	 * thus the name)
	 */
	public static double chiSquared(double[][] x, double[] a, double[] y, double[] s, LMfunc f)
	{
		int npts = y.length;
		double sum = 0.;

		for( int i = 0; i < npts; i++ ) 
		{
			double d = y[i] - f.val(x[i], a);
			d = d / s[i];
			sum = sum + (d*d);
		}
		return sum;
	} //chiSquared


	/**
	 * Minimize E = sum {(y[k] - f(x[k],a)) / s[k]}^2
	 * The individual errors are optionally scaled by s[k].
	 * Note that LMfunc implements the value and gradient of f(x,a),
	 * NOT the value and gradient of E with respect to a!
	 * 
	 * @param x array of domain points, each may be multidimensional
	 * @param y corresponding array of values
	 * @param a the parameters/state of the model
	 * @param vary false to indicate the corresponding a[k] is to be held fixed
	 * @param s2 sigma^2 for point i
	 * @param lambda blend between steepest descent (lambda high) and
	 *	jump to bottom of quadratic (lambda zero).
	 * 	Start with 0.001.
	 * @param termepsilon termination accuracy (0.01)
	 * @param maxiter	stop and return after this many iterations if not done
	 * @param verbose	set to zero (no prints), 1, 2
	 *
	 * @return the new lambda for future iterations.
	 *  Can use this and maxiter to interleave the LM descent with some other
	 *  task, setting maxiter to something small.
	 */
	public static double solve(double[][] x, double[] a, double[] y, double[] s, 
							   boolean[] vary, LMfunc f, double lambda, double termepsilon, 
							   int maxiter, int verbose) throws Exception
							   {
		int npts = y.length;
		int nparm = a.length;
		assert s.length == npts;
		assert x.length == npts;
		if (verbose > 0) 
		{
			System.out.print("solve x["+x.length+"]["+x[0].length+"]" );
			System.out.print(" a["+a.length+"]");
			System.out.println(" y["+y.length+"]");
		}
//		for( int i = 0; i < nparm; i++ ) 
//			System.out.print("initial:"+i+":"+a[i]+" ");	    				
//		System.out.println();
		double e0 = chiSquared(x, a, y, s, f);
		
		//double lambda = 0.001;
		boolean done = false;

		// g = gradient, H = hessian, d = step to minimum
		// H d = -g, solve for d
	    double[][] H = new double[nparm][nparm];
	    double[] g = new double[nparm];
	    //double[] d = new double[nparm];
	
	    double[] oos2 = new double[s.length];
	    for( int i = 0; i < npts; i++ )  oos2[i] = 1./(s[i]*s[i]);
	
	    int iter = 0;
	    int term = 0;	// termination count test

	    do 
	    {
	    	++iter;
//			for( int i = 0; i < nparm; i++ ) 
//				System.out.print("aaaaaaaaaaa:"+i+":"+a[i]+" ");	    				
//			System.out.println();
	    	
	    	// hessian approximation
	    	for( int r = 0; r < nparm; r++ ) 
	    	{
	    		for( int c = 0; c < nparm; c++ ) 
	    		{
	    			for( int i = 0; i < npts; i++ ) 
	    			{
	    				if (i == 0) H[r][c] = 0.;
	    				double[] xi = x[i];
	    				H[r][c] += (oos2[i] * f.grad(xi, a, r) * f.grad(xi, a, c));
	    			}  //npts
	    		} //c
	    	} //r

	    	// boost diagonal towards gradient descent
	    	for( int r = 0; r < nparm; r++ )
	    		H[r][r] *= (1. + lambda);

	    	// gradient
	    	for( int r = 0; r < nparm; r++ ) 
	    	{
	    		for( int i = 0; i < npts; i++ ) 
	    		{
	    			if (i == 0) g[r] = 0.;
	    			double[] xi = x[i];
	    			g[r] += (oos2[i] * (y[i]-f.val(xi,a)) * f.grad(xi, a, r));
    			}
    		} //npts
	    	
	    	// scale (for consistency with NR, not necessary)
	    	if (false) 
	    	{
	    		for( int r = 0; r < nparm; r++ ) 
	    		{
	    			g[r] = -0.5 * g[r];
	    			for( int c = 0; c < nparm; c++ ) 
	    			{
	    				H[r][c] *= 0.5;
    				}
    			}
	    	}

	    	// solve H d = -g, evaluate error at new location
	    	//double[] d = DoubleMatrix.solve(H, g);

	    	double[] d = (new Matrix(H)).lu().solve(new Matrix(g, nparm)).getRowPackedCopy();
	    	//double[] na = DoubleVector.add(a, d);
	    	double[] na = (new Matrix(a, nparm)).plus(new Matrix(d, nparm)).getRowPackedCopy();
	    	double e1 = chiSquared(x, na, y, s, f);

	    	if (verbose > 0) 
	    	{
	    		System.out.println("\n\niteration "+iter+" lambda = "+lambda);
	    		System.out.print("a = ");
	    		(new Matrix(a, nparm)).print(10, 2);
	    		if (verbose > 1) 
	    		{
	    			System.out.print("H = ");
	    			(new Matrix(H)).print(10, 2);
	    			System.out.print("g = ");
	    			(new Matrix(g, nparm)).print(10, 2);
	    			System.out.print("d = ");
	    			(new Matrix(d, nparm)).print(10, 2);
    			}
	    		System.out.print("e0 = " + e0 + ": ");
	    		System.out.print("moved from ");
	    		(new Matrix(a, nparm)).print(10, 2);
	    		System.out.print("e1 = " + e1 + ": ");
	    		if (e1 < e0) 
	    		{
	    			System.out.print("to ");
	    			(new Matrix(na, nparm)).print(10, 2);
    			}
	    		else 
	    		{
	    			System.out.println("move rejected");
    			}
    		}
	    	
	    	// termination test (slightly different than NR)
	    	if (Math.abs(e1-e0) > termepsilon) 
	    	{
	    		term = 0;
    		}      
	    	else 
	    	{
	    		term++;
	    		if (term == 4)
//	    		if (term == 20) 
	    		{
	    			//System.out.println("terminating after " + iter + " iterations");
	    			done = true;
    			}
    		}
	    	if (iter >= maxiter) done = true;
	    	// in the C++ version, found that changing this to e1 >= e0
	    	// was not a good idea.  See comment there.
	    	//
	    	if (e1 > e0 || Double.isNaN(e1)) 
	    	{
	    		// new location worse than before
	    		lambda *= 10.;
    		}
	    	else 
	    	{
	    		// new location better, accept new parameters
	    		lambda *= 0.1;
	    		e0 = e1;
	    		// simply assigning a = na will not get results copied back to caller
	    		for( int i = 0; i < nparm; i++ ) 
	    		{
	    			if (vary[i])
    				{
	    				a[i] = na[i];
//	    				System.out.print(i+":"+a[i]+" ");	    				
    				}
    			}
//	    		System.out.println();
    		}
    	}
	    while(!done);
	    return lambda;
    } //solve	
 //LM

/*public static void main(String[] args)
{
  double x[][]= new double[49][2];
  int c=0;
  /*for (int i=59;i<=65;i++){
	  for (int j=68;j<=74;j++){
		  x[c][0]=i;
		  x[c][1]=j;
		  c++;
	  }
  }
  double y[]={65,69,53,53,53,53,57,53,77,65,73,73,53,61,81,81,81,102,85,69,53,77,106,134,150,110,89,65,65,102,154,179,126,89,61,69,77,138,146,138,77,61,49,61,85,73,85,73,69};
  
  
  //LMfunc f = new LMSineTest();	// works
  //LMfunc f = new LMGaussTest();	// works
  //double y[]=new double[49];
  for (int i=0;i<7;i++){
	  for (int j=0;j<7;j++){
		  x[c][0]=i;
		  x[c][1]=j;
		  c++;
	  }
  }
  //double y[] = { 61.8141 ,  88.5429 , 105.9204 ,115.6491 , 109.8331 ,  89.1406  , 61.4733  , 86.1500 , 124.9884 , 150.8333 , 160.1324, 150.8130 , 122.6276 ,  88.1146 , 106.0479 , 149.3675 , 183.7095 , 196.8641 , 183.8153 , 152.2545  ,108.6003 , 114.4897, 161.4655 , 197.7650 , 212.1116  ,198.0714,  164.4452 , 116.5824 , 107.5389 , 150.2530 , 186.9951 , 197.2656 , 183.1098, 150.9533 , 110.1653 ,  88.9859 , 122.9588 , 151.7639 , 162.1616,  149.7593 , 124.9051   ,90.3404 ,  65.1993 ,  87.7524, 
//		  110.0802 , 117.4536 , 106.9683 ,  86.1833  , 63.2299};
  double sigx=2.13;
  double sigy=3.25;
  double y[]={48.279796135323686,59.528510898520025,70.09426920958975,75.08272025417718,72.16210450720743,62.965001805987384,51.739693467371715,85.04719603753225,106.15409515106674,119.94759535678446,128.45153045883086,120.41937979551356,107.34980778448387,81.5409324764983,116.26688510283272,146.0200658026412,166.79543659384765,176.74186132346784,166.33528712356213,143.68550939586063,114.68305594068718,127.03916929490646,164.15909916915788,187.1134417459824,197.5397415451724,187.47234513683904,162.48332541824746,126.33016040598056,117.1581242104287,147.9032002798175,169.09028759604487,176.83065657109518,167.3053748668062,143.60373903745995,113.29473971523089,82.42352489060013,106.9339599154831,121.74035593360102,125.46879568470825,120.3144104378294,103.77489203155118,85.22706939776849,50.24110368856409,60.61030679320402,71.44304566592895,76.35340900503418,68.89855340294045,60.55095788473169,47.600058971534445};
  double xcuad,ycuad,rnd;
  /*for (int k=0;k<49;k++){
	  xcuad=(x[k][0]-3)*(x[k][0]-3);
	  ycuad=(x[k][1]-3)*(x[k][1]-3);
	  rnd=Math.random()*5;
	  System.out.println("rnd: "+rnd);
	  y[k]=193*Math.exp(-((xcuad/(2*sigx*sigx))+(ycuad/(2*sigy*sigy)))) + rnd;
  };
  System.out.println("y: ");
  for (int l=0;l<49;l++){
	  System.out.print(y[l]+",");
  }
  
  LMfunc f = new Gauss2dAnisotropic(x,y,193,3,3,1,1);
  
  double[] aguess = f.initial();
  Object[] test = f.testdata(49);
  double[]areal=(double[]) test[1];
  double[] s= (double[]) test[3];//Matriz de pesos
  boolean[] vary = new boolean[aguess.length];
  for( int i = 0; i < aguess.length; i++ ) vary[i] = true;
  assert aguess.length == areal.length;
  

  try {
    solve( x, aguess, y, s, vary, f, 0.001, 0.01, 1000, 2);
  }
  catch(Exception ex) {
    System.err.println("Exception caught: " + ex.getMessage());
    System.exit(1); 
  }

  System.out.print("desired solution "); 
  (new Matrix(aguess, aguess.length)).print(10, 4);

  System.exit(0);
}*/ //main

} //LM
