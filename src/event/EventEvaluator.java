package event;

import java.awt.Color;

import bTools.BMaths;
import data.Trajectory;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Plot;
import ij.gui.PlotWindow;
import ij.measure.CurveFitter;
import ij.process.ImageProcessor;

public class EventEvaluator{
	
	private ImagePlus imp;
	private int limitRadius;
	private int deltaWindow;
	
	private int impEndFrame; //Actual length of the movie.
	
	public EventEvaluator(ImagePlus imp, int limitRadius, int deltaWindow){
		this.imp=imp;
		this.limitRadius=limitRadius;
		this.deltaWindow=deltaWindow;
		
		
		//Checking which is the biggest one, NSlices or NFrames.
		if(imp.getNFrames() > 1){
			impEndFrame = imp.getNFrames();
		}else if(imp.getNSlices() > 1){
			impEndFrame = imp.getNSlices();
		}else{
			System.out.println("No es una película!!");
		}
	}
	
	public Event evaluate(Trajectory traj){
		int max=0;
		int maxIndex=-1;
		
		
		if (traj.getRadio() > limitRadius){
			return null;
		}
		double x=traj.getCentroide_Y();
		double y=traj.getCentroide_X();
		int xEvent=(int)x;
		int yEvent=(int)y;
		
		int startTraj=traj.getMovieFrame();//Get the first frame where the trajectory is detected
		int startFrame = startTraj-deltaWindow;
		if (startFrame<1){
			startFrame=1;
		}
		
		int endFrame=startTraj+deltaWindow;//Verify that the number of the frames don't exceed the limits of the movie
		if (endFrame > impEndFrame){
			endFrame = impEndFrame;
		}
		int leftInterval= startTraj-startFrame;
		int rightInterval= endFrame-startTraj;
		int Intensities[]=new int[leftInterval+rightInterval+1];
		//System.out.println("Array size:"+Intensities.length);
		System.out.println("Inicio ventana:"+startFrame+" , centro ventana:"+startTraj+" , fin ventana:"+endFrame);
		
		ImageStack is=imp.getStack();
		ImageProcessor ip;
		
		//Mean model for vesicle intensities
		
		int xmin,xmax,ymin,ymax;
		
		xmin=xEvent-1;
		xmax=xEvent+1;
		ymin=yEvent-1;
		ymax=yEvent+1;
		
		if (xmin<0) xmin=0;
		if (ymin<0) ymin=0;
		if (xmax>imp.getWidth()) xmax=imp.getWidth()-1;
		if (ymax>imp.getHeight()) ymax=imp.getHeight()-1;
		System.out.println("y max="+imp.getHeight()+" x max="+imp.getWidth());
		
		int intensitiesSum;
		int numPixels=(xmax-xmin+1)*(ymax-ymin+1);
		
		
		for (int i=startFrame;i<=endFrame;i++){
			ip=is.getProcessor(i);
			intensitiesSum=0;
			for (int xi=xmin;xi<=xmax;xi++){
				for (int yi=ymin;yi<=ymax;yi++){
					intensitiesSum=intensitiesSum+ip.getPixel(xi, yi);
				}
			}
			
			Intensities[i-startFrame]=intensitiesSum/numPixels;
			
			//Detecting maximum intensity
			if (max<Intensities[i-startFrame]){
				max=Intensities[i-startFrame];
				maxIndex=i-startFrame;
			}
			
			
			System.out.println("i: "+i+" (x,y)=("+xEvent+","+yEvent+"): "+Intensities[i-startFrame]);
		}
		System.out.println("Max intensity: "+max+", array position: "+maxIndex);
		System.out.println("");
		Object[] resultado=verifyExponential(xEvent,yEvent,maxIndex,Intensities.length,startFrame,Intensities);
		
		return null;
	}
	
	
	public Object[] verifyExponential(int pixelX, int pixelY, int startPos,int endPos, int startFrame, int[] intensitiesWindow){
		double x[]=new double[endPos-startPos];
		double y[]=new double[endPos-startPos];
		double gof=0;
		double expParams[] = new double[2];
		for (int i=startPos;i<endPos;i++){
			x[i-startPos]=i+startFrame;
			y[i-startPos]=intensitiesWindow[i];
		}
		
		//Code for calculate the mean intensity before (background) the maximum intensity
		
		ImageStack is=imp.getImageStack();
		ImageProcessor ip;
		double background=0.0;
		int bgFrames=0;
		if (startFrame!=1){
			int minLimit=startFrame-10;
			if (minLimit<0) minLimit=0;
			for (int f=startFrame-10;f<startFrame;f++){
				ip=is.getProcessor(f);
				background=background+ip.getPixel(pixelX, pixelY);
				bgFrames++;
			}
			background=background/bgFrames;
		}else{//if the event candidate doesn't have previous frames to calculate background we use the minimum intensity post event
			double min = y[0];
			for(int i=0;i<y.length;i++){
				if(y[i]<min)
					min=y[i];
			}
			background=min;
			System.out.print("Minimum intensity: "+min);
		}
		//end background code
		
		if (endPos-startPos>2){
			CurveFitter checker = new CurveFitter(x, y);

			checker.doCustomFit( "y=a*exp(b*(x-"+startFrame+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
			gof = checker.getFitGoodness();
			expParams = checker.getParams();
			double fit[][] = new double[2][x.length];
			for(int i=0;i<x.length;i++)
			{
				fit[0][i] = x[i];
				fit[1][i] = checker.f(expParams, x[i]);
				System.out.println("Exp: x->"+fit[0][i]+" y->"+fit[1][i]);
			}
			if (gof>0.5){
				
				//Code for proving if exponential adjust is working or not (plotting events' intensities)
				PlotWindow.noGridLines = false; // draw grid lines
		        Plot plot = new Plot("X= "+String.valueOf(pixelX)+" Y= "+String.valueOf(pixelY),"X Axis","Y Axis",x,y);
		        plot.setLimits(0, impEndFrame, 0, 255);
		        plot.setLineWidth(1);
		        double yprueba[]=new double[x.length];
		        for(int i=0;i<x.length;i++)
				{
					yprueba[i]=fit[1][i];
				}
		        plot.setColor(Color.red);
		        plot.addPoints(x,yprueba,PlotWindow.X);
		        plot.addPoints(x,yprueba,PlotWindow.LINE);
		        plot.show();
		        //end of plotting code
			}
			//if(gof>0.3){
				System.out.println(checker.getResultString());
				System.out.println("gof "+gof);
				return new Object[]{true,fit,expParams,gof};
		}
		return null;
	}
}