package typeOfMotion;

import bTools.BMaths;
import ij.IJ;
import ij.measure.CurveFitter;
import lmFit.AnomalousDiffusion;
import lmFit.AnomalousDiffusionWithOffset;
import lmFit.CorralledMotion;
import lmFit.CorralledMotionWithOffset;
import lmFit.DirectedMotion;
import lmFit.DirectedMotionWithOffset;
import lmFit.LM;
import lmFit.NormalDiff;
import lmFit.NormalDiffWithOffset;

public class Fitter 
{
	public static double[] normalFitIJ(double[] x, double[] y, boolean withOffset)
	{
		CurveFitter cv = new CurveFitter(x, y);
		double[] params;
		if(withOffset)cv.doFit(CurveFitter.STRAIGHT_LINE);
		else cv.doCustomFit("y=4*a*x", new double[]{1}, false);
		params = cv.getParams();
		IJ.log("normalFitIJ");
		IJ.log("fit goodness"+cv.getFitGoodness());
		IJ.log(cv.getResultString());
		if(withOffset)
			return new double[]{params[1]*0.25,params[0]};
		else 
			return params;
	}
	public static double[] normalFitLM(double[] x, double[] y, boolean withOffset, boolean weighted, double[] weights)
	{
		double[][] xx = new double[x.length][1];
		for(int i=0;i<xx.length;i++) xx[i][0] = x[i];
		
		NormalDiff nd = new NormalDiff(weighted, weights);
		NormalDiffWithOffset ndwo = new NormalDiffWithOffset(weighted, weights);
		
		double[] params,s;
		Object[] test;
		boolean[] vary;
		
		if(withOffset)
		{
			params = ndwo.initial();
			test = ndwo.testdata(y.length);
			vary = new boolean[params.length];
			vary[1]=true;
		}
		else
		{
			params = nd.initial();
			test = nd.testdata(y.length);
			vary = new boolean[params.length];
		}
		s = (double[])test[3];
		vary[0]=true;
					
		try
		{
			if(withOffset)LM.solve( xx, params, y, s, vary, ndwo, 0.001, 0.001, 1000, 0);
			else LM.solve( xx, params, y, s, vary, nd, 0.001, 0.001, 1000, 0);
		}
		catch(Exception ex) {
			System.err.println("NormalFit Exception caught: " + ex.getMessage());
			IJ.log("NormalFit Exception caught: " + ex.getMessage());
		}			
		return params;
	}

	public static double[] anomalousFitIJ(double[] x, double[] y, double[] initial, boolean withOffset)
	{
		CurveFitter cv = new CurveFitter(x, y);
		if(withOffset)cv.doCustomFit("y=4*a*(x^b)+c", initial, false);
		else cv.doCustomFit("y=4*a*(x^b)", initial, false);
		IJ.log("anomalousFit");
		IJ.log("fit goodness"+cv.getFitGoodness());
		IJ.log(cv.getResultString());
		return cv.getParams();
	}
	public static double[] anomalousFitLM(double[] x, double[] y, double[] initial, boolean withOffset, boolean weighted, double[] weights)
	{
		double[][] xx = new double[x.length][1];
		for(int i=0;i<xx.length;i++) xx[i][0] = x[i];
		
		AnomalousDiffusion ad = new AnomalousDiffusion(initial, weighted, weights);
		AnomalousDiffusionWithOffset adwo = new AnomalousDiffusionWithOffset(initial, weighted, weights);
		double[] aguess,s;
		Object[] test;
		boolean[] vary;
		
		if(withOffset)
		{
			aguess = adwo.initial();
			test = adwo.testdata(y.length);
			vary = new boolean[aguess.length];
			vary[2]=true;
		}
		else
		{
			aguess = ad.initial();
			test = ad.testdata(y.length);
			vary = new boolean[aguess.length];
		}
		s = (double[])test[3];
		vary[0]=true;
		vary[1]=true;
					
		try
		{
			if(weighted)LM.solve( xx, aguess, y, s, vary, adwo, 0.001, 0.001, 1000, 0);
			else LM.solve( xx, aguess, y, s, vary, ad, 0.001, 0.001, 1000, 0);
		}
		catch(Exception ex) 
		{
			System.err.println("Anomalous Fit Exception caught: " + ex.getMessage());
			IJ.log("Anomalous Fit Exception caught: " + ex.getMessage());
		}			
		return aguess;
	}
	
	
	public static double[] directedFitIJ(double[] x, double[] y, double[] initial, boolean withoffset)
	{
		CurveFitter cv = new CurveFitter(x, y);
		if(withoffset)cv.doCustomFit("y=4*a*x+b*b*x*x+c", initial, false);
		else cv.doCustomFit("y=4*a*x+b*b*x*x", initial, false);
		IJ.log("directedFit");
		IJ.log("fit goodness"+cv.getFitGoodness());
		IJ.log(cv.getResultString());
		return cv.getParams();
	}
	public static double[] directedFitLM(double[] x, double[] y, double[] initial, boolean withOffset, boolean weighted, double[] weights)
	{
		double[][] xx = new double[x.length][1];
		for(int i=0;i<xx.length;i++) xx[i][0] = x[i];
		
		DirectedMotion dm = new DirectedMotion(initial, weighted, weights);
		DirectedMotionWithOffset dmwo = new DirectedMotionWithOffset(new double[]{initial[0],initial[1],0}, weighted, weights);
		double[] aguess,s;
		Object[] test;
		boolean[] vary;
		
		if(withOffset)
		{
			aguess = dmwo.initial();
			test = dmwo.testdata(y.length);
			vary = new boolean[aguess.length];
			vary[2]=true;
		}
		else
		{
			aguess = dm.initial();
			test = dm.testdata(y.length);
			vary = new boolean[aguess.length];
		}
		s = (double[])test[3];
		vary[0]=true;
		vary[1]=true;
					
		try{
			if(withOffset)LM.solve( xx, aguess, y, s, vary, dmwo, 0.001, 0.001, 1000, 0);
			else LM.solve( xx, aguess, y, s, vary, dm, 0.001, 0.001, 1000, 0);
		}
		catch(Exception ex) {
			System.err.println("Directed Fit Exception caught: " + ex.getMessage());
			IJ.log("DirectedFit Exception caught: " + ex.getMessage());
		}			
		return aguess;
	}
	
	public static double[] corralledFitIJ(double[] x, double[] y, double[] initial, boolean withOffset)
	{
		CurveFitter cv = new CurveFitter(x, y);
//		if(withOffset)cv.doCustomFit("y=(b*b/3)*(1-exp(-12*a*x/(b*b)))+c", initial, false);
//		else cv.doCustomFit("y=(b*b/3)*(1-exp(-12*a*x/(b*b)))", initial, false);
//		if(withOffset)cv.doCustomFit("y=(b*b)*(1-exp(-4*a*x/(b*b)))+c", initial, false);
//		else cv.doCustomFit("y=(b*b)*(1-exp(-4*a*x/(b*b)))", initial, false);
		if(withOffset)cv.doCustomFit("y=(b*b)*(1-0.99*exp(-4*0.85*a*x/(b*b)))+c", initial, false);
		else cv.doCustomFit("y=(b*b)*(1-0.99*exp(-4*0.85*a*x/(b*b)))", initial, false);
		IJ.log("corralledFit");
		IJ.log("fit goodness"+cv.getFitGoodness());
		IJ.log(cv.getResultString());
		return cv.getParams();
	}
	public static double[] corralledFitLM(double[] x, double[] y, double[] initial, boolean withOffset, boolean weighted, double[] weights)
	{
		double[][] xx = new double[x.length][1];
		for(int i=0;i<xx.length;i++) xx[i][0] = x[i];
		
		CorralledMotion cm = new CorralledMotion(initial, weighted, weights);
		CorralledMotionWithOffset cmwo = new CorralledMotionWithOffset(initial, weighted, weights);
		
		double[] aguess,s;
		Object[] test;
		boolean[] vary;
		
		if(withOffset)
		{
			aguess = cmwo.initial();
			test = cmwo.testdata(y.length);
			vary = new boolean[aguess.length];
			vary[2]=true;
		}
		else
		{
			aguess = cm.initial();
			test = cm.testdata(y.length);
			vary = new boolean[aguess.length];
		}
		s = (double[])test[3];
		vary[0]=true;
		vary[1]=true;
					
		try {
			if(withOffset)LM.solve( xx, aguess, y, s, vary, cmwo, 0.001, 0.001, 1000, 1);
			else LM.solve( xx, aguess, y, s, vary, cm, 0.001, 0.001, 1000, 1);
		}
		catch(Exception ex) 
		{
			System.err.println("CorralledFit Exception caught: " + ex.getMessage());
			IJ.log("CorralledFit Exception caught: " + ex.getMessage());
		}			
		return aguess;
	}
	
	public static Object[] automaticBestFit(double[] timeLag, double[] msd, boolean fitWithOffset, boolean weighted, double[] weights)
	{
		double[] time12 = BMaths.getSubArray(timeLag, 0, 1);
		double[] msd12 = BMaths.getSubArray(msd, 0, 1);

		double[] normalFit = normalFitIJ(timeLag, msd, fitWithOffset);
		double[] normalFit12 = normalFitIJ(time12, msd12, fitWithOffset);
		double[] anomalous = anomalousFitIJ(timeLag, msd,new double[]{normalFit12[0],1.0,0.0}, fitWithOffset); 
		double[] corralled = corralledFitIJ(timeLag, msd, new double[]{normalFit12[0],BMaths.avg(msd)},fitWithOffset);
		double[] directed = directedFitIJ(timeLag, msd,new double[]{anomalous[0],1.0},fitWithOffset);		
		
		int c=0;
		if (fitWithOffset) c=1;
		
		double[] gof = new double[]{BMaths.getFitGoodness(msd,Evaluator.funcNormal(timeLag, normalFit[0]),1),
									BMaths.getFitGoodness(msd, Evaluator.funcAnomalous(timeLag, anomalous[0], anomalous[1]),2+c),
									BMaths.getFitGoodness(msd, Evaluator.funcCorralled(timeLag, corralled[0], corralled[1]),2+c),
									BMaths.getFitGoodness(msd, Evaluator.funcDirected(timeLag, directed[0], directed[1]),2+c)};
		
		int type = TypeOfMotionAnalysis.NORMAL;
		double goff = gof[0];
		double[] params = normalFit;
		if(gof[1]>goff)
		{
			type = TypeOfMotionAnalysis.ANOMALOUS;
			params = anomalous;
			goff = gof[1]; 
		}
		if(gof[2]>goff)
		{
			type = TypeOfMotionAnalysis.CORRALLED;
			params = corralled;
			goff = gof[2];
		}
		if(gof[3]>goff)
		{
			type = TypeOfMotionAnalysis.DIRECTED;
			params = directed;
			goff = gof[3];
		}
		
		if(type==TypeOfMotionAnalysis.ANOMALOUS)
		{
			if(anomalous[1]==1)
			{
				type = TypeOfMotionAnalysis.NORMAL;
				params = normalFit;
				goff = gof[0];
			}
			else if(anomalous[1]>1)
			{
				type = TypeOfMotionAnalysis.DIRECTED;
				params = directed;
				goff = gof[3];
			}
		}
		return new Object[]{type, params, goff};		
	}
}
