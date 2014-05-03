package event;

import java.awt.Color;

import lmFit.Gauss2dAnisotropic;
import lmFit.Gauss2dImproved;
import lmFit.Gauss2dIsotropic;
import lmFit.LMIsotropic2dGaussian;
import lmFit.LMauthor;
import lmFit.LMfunc;
import particleTracker.ParticleDetector;
import visualization.PreviewCanvas;
import bTools.BMaths; 
import data.Frame;
import data.Particle;
import data.Trajectory;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Plot;
import ij.gui.PlotWindow;
import ij.gui.StackWindow;
import ij.measure.CurveFitter;
import ij.process.ImageProcessor;

import java.io.*;
import java.util.ArrayList;

public class EventEvaluator{
	
	private ImagePlus imp;
	private int limitRadius;
	private int deltaWindow;
	private int areaRadius;
	private int fitPatchSize;
	private double timeFrames;
	private double minIntensityIncrease;
	private ArrayList<Double> nonFusionedIntArray;
	private double maxIntThreshold;//Value that represent the intensity of the vesicles that don't fuse with the membrane
	
	private double dockingSlopeTolerance=0.1;
	
	private int impEndFrame; //Actual length of the movie.
	
	private StackWindow stack_window;
	private PreviewCanvas pc;
	
	public EventEvaluator(ImagePlus imp, int limitRadius, int deltaWindow,int areaRadius,int fitPatchSize,double time, double minIntInc, ArrayList<Double> nonFusionedVesicles){
		this.imp=imp;
		this.limitRadius=limitRadius;
		this.deltaWindow=deltaWindow;
		this.areaRadius=areaRadius;
		this.fitPatchSize=(fitPatchSize-1)/2;
		this.timeFrames=time;
		this.minIntensityIncrease=minIntInc;
		this.nonFusionedIntArray=nonFusionedVesicles;
		
		
		//Checking which is the biggest one, NSlices or NFrames.
		if(imp.getNFrames() > 1){
			impEndFrame = imp.getNFrames();
		}else if(imp.getNSlices() > 1){
			impEndFrame = imp.getNSlices();
		}else{
			System.out.println("No es una película!!");
		}
		setMaxIntThreshold();
	
		/*/Prueba graficación
		double magnification = imp.getWindow().getCanvas().getMagnification();
		ImageStack stack=imp.getStack();
		Frame preview_frame = new Frame(stack.getProcessor(50), 50);
		pc = new PreviewCanvas(imp, preview_frame, magnification, ParticleDetector.radius);		
		// display the image and canvas in a stackWindowtu 
		stack_window = new StackWindow(imp, pc);
		//fin prueba graficación*/
	}
	
	public void setWindowAnalysisSize(int size){
		fitPatchSize=(size-1)/2;
	}
	
	public void setTimeBetweenFrames(int time){
		timeFrames=time;
	}
	
	public int getImpEndFrame(){
		return impEndFrame;
	}
	
	
	public void setMaxIntThreshold(){
		double avgInt=0.;
		double stdInt=0.;
		int nNFVesicles=nonFusionedIntArray.size();
		double [] vesiclesIntensities=new double[nNFVesicles];
		for (int i=0;i<nNFVesicles;i++){
			vesiclesIntensities[i]=nonFusionedIntArray.get(i);
		}
		double []avgStd=BMaths.avgstd(vesiclesIntensities);
		avgInt=avgStd[0];
		stdInt=avgStd[1];
		this.maxIntThreshold=avgInt-(0.5*stdInt);//+2*stdInt;
	}
	
	/*public Event evaluate(Trajectory traj){
		int max=0;
		int maxIndex=-1;
		
		
		/*if (traj.getRadio() > limitRadius){
			return null;
		}
		System.out.println("Trajectory id: "+traj.getId());
		double x=traj.getCentroide_Y();
		double y=traj.getCentroide_X();
		
		int xEvent=(int)x;
		int yEvent=(int)y;
		
		int startTraj=traj.getMovieFrame();//Get the first frame where the trajectory is detected
		
		//Code for testing if checking every frame on the trajectory gives better results
		int startFrame=startTraj;
		int endFrame=startTraj+traj.getLength()-1;
		System.out.println("Trajectoria empieza en "+startFrame+" y termina en "+endFrame);
		int Intensities[]=new int[endFrame-startFrame+1];
		//end of testing code
		
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
		if (xmax>=imp.getWidth()){
			xmax=imp.getWidth()-1;
			System.out.println("Extremoo x");
		}
		if (ymax>=imp.getHeight()){
			ymax=imp.getHeight()-1;
			System.out.println("Extremoo y");
		}
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
			//Intensities[i-startFrame]=ip.getPixel(xEvent, yEvent);
			
			//Detecting maximum intensity
			if (max<Intensities[i-startFrame]){
				max=Intensities[i-startFrame];
				maxIndex=i-startFrame;
			}
			
			
			System.out.println("i: "+i+" (x,y)=("+xEvent+","+yEvent+"): "+Intensities[i-startFrame]);
		}
		System.out.println("Max intensity: "+max+", array position: "+maxIndex);
		System.out.println("");
		int endAnalysis=Intensities.length;
		if (endAnalysis-maxIndex>deltaWindow) endAnalysis=maxIndex+deltaWindow;
		
		Event ev=verifyExponential(xEvent,yEvent,maxIndex,endAnalysis,startFrame,Intensities);
		
		return ev;
	}
	
	
	public Event verifyExponential(int pixelX, int pixelY, int startPos,int endPos, int startFrame, int[] intensitiesWindow){
		double x[]=new double[endPos-startPos];
		double y[]=new double[endPos-startPos];
		double gof=0;
		double expParams[] = new double[2];
		for (int i=startPos;i<endPos;i++){
			x[i-startPos]=i+startFrame;
			y[i-startPos]=intensitiesWindow[i];
		}
		
		//Objects needed to get intensities
		ImageStack is=imp.getImageStack();
		ImageProcessor ip;
		
		//Test code to plot every intensity along the movie
		double completeX[]=new double[impEndFrame];
		double completeY[]=new double[impEndFrame];
		
		int xmin,xmax,ymin,ymax;
		
		xmin=pixelX-1;
		xmax=pixelX+1;
		ymin=pixelY-1;
		ymax=pixelY+1;
		
		if (xmin<0) xmin=0;
		if (ymin<0) ymin=0;
		if (xmax>imp.getWidth()) xmax=imp.getWidth()-1;
		if (ymax>imp.getHeight()) ymax=imp.getHeight()-1;
		System.out.println("y max="+imp.getHeight()+" x max="+imp.getWidth());
		
		int intensitiesSum;
		int numPixels=(xmax-xmin+1)*(ymax-ymin+1);
		
		
		for (int c=0;c<impEndFrame;c++){
			ip=is.getProcessor(c+1);
			intensitiesSum=0;
			for (int xi=xmin;xi<=xmax;xi++){
				for (int yi=ymin;yi<=ymax;yi++){
					intensitiesSum=intensitiesSum+ip.getPixel(xi, yi);
				}
			}
			completeX[c]=c+1;
			completeY[c]=intensitiesSum/numPixels;
		}
		
		/*for (int c=0;c<impEndFrame;c++){
			ip=is.getProcessor(c+1);
			completeX[c]=c+1;
			completeY[c]=ip.getPixel(pixelX, pixelY);
		}
		
		//end of test code
		
		//Code for calculate the mean intensity before (background) the maximum intensity
		
		double background=0.0;
		int bgFrames=0;
		if (startFrame!=1){//if the event has previous frames, get the average of the 5 previous frames' intensities
			int minLimit=startFrame-5;
			if (minLimit<1) minLimit=1;
			for (int f=minLimit;f<startFrame;f++){
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
		
		if (endPos-startPos>2){//Filter the shortest trajectories
			
			CurveFitter checker = new CurveFitter(x, y);
			checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+startFrame)+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
			gof = checker.getFitGoodness();
			expParams = checker.getParams();
			double fit[][] = new double[2][x.length];
			for(int i=0;i<x.length;i++)
			{
				fit[0][i] = x[i];
				fit[1][i] = checker.f(expParams, x[i]);
				System.out.println("Exp: x->"+fit[0][i]+" y->"+fit[1][i]);
			}
			double tau=-1/expParams[1];
			double amplitude=expParams[0];
			if (gof>0.5 && tau<5 && amplitude>(0.4*background)){//Filter according to 3 parameters of the exp adjust: gof, tau and amplitude
				
				//Code for testing graphically if the filtering is working
				PlotWindow.noGridLines = false;
			    Plot plot = new Plot("X= "+String.valueOf(pixelX)+" Y= "+String.valueOf(pixelY),"X Axis","Y Axis",completeX,completeY);
			    plot.setLimits(0, impEndFrame, 0, 255);
			    plot.setLineWidth(1);
			    double yprueba[]=new double[x.length];
			    for(int i=0;i<x.length;i++){
						yprueba[i]=fit[1][i];
				}
			    plot.setColor(Color.red);
			    plot.addPoints(x,yprueba,PlotWindow.X);
			    plot.addPoints(x,yprueba,PlotWindow.LINE);
			    plot.show();
			    //end of testing code
			    
			    System.out.println(checker.getResultString());
			    System.out.println("gof "+gof);
				
			    //To do: eventRadius and T mean (actually they are initialized in 0)

				Event eventTested=new Event(0,0,0,0,0,startPos+startFrame,endPos+startFrame,expParams[0],expParams[1],0,new double[2]);

				return eventTested;
					
		    }
			return null;
		}
		return null;
	}*/
	
	public Event evaluateImproved(Trajectory traj){
		
		int trajLength=traj.getLength();
		int startFrame=traj.getMovieFrame();
		int endFrame=startFrame+trajLength-1;
		System.out.println("Evaluating trajectory "+traj.getId()+" that starts in frame "+startFrame+" and ends in frame "+endFrame);
		
		Particle trajParticles[]=traj.getParticlesPro();
		
		double intensities[]=new double[impEndFrame];
		getEventIntensities(intensities,startFrame,endFrame,trajParticles);
		
		/*int xStartEvent=(int)trajParticles[0].y;
		int yStartEvent=(int)trajParticles[0].x;
		int xEndEvent=(int)trajParticles[trajLength-1].y;
		int yEndEvent=(int)trajParticles[trajLength-1].x;
		getBackgroundIntensities(intensities,xStartEvent,yStartEvent,startFrame,xEndEvent,yEndEvent,endFrame);*/
		
		
		int maxIndex=getMaxIntensityIndex(intensities,startFrame,endFrame);
		
		int xEvent=(int)(trajParticles[maxIndex+1-startFrame].y+0.5);
		int yEvent=(int)(trajParticles[maxIndex+1-startFrame].x+0.5);
		
		/*/Código que ajusta el centro de la vesicula en el punto de la máxima intensidad
		int x1Patch=xEvent-fitPatchSize;
	    int x2Patch=xEvent+fitPatchSize;
	    int y1Patch=yEvent-fitPatchSize;
	    int y2Patch=yEvent+fitPatchSize;
	    
	    if (x1Patch<0) x1Patch=0;
		if (y1Patch<0) y1Patch=0;
		if (x2Patch>=imp.getWidth()){
			x2Patch=imp.getWidth()-1;
		}
		if (y2Patch>=imp.getHeight()){
			y2Patch=imp.getHeight()-1;
		}
		double []limits=new double[4];
		limits[0]=x1Patch;
		limits[1]=x2Patch;
		limits[2]=y1Patch;
		limits[3]=y2Patch;
		
		int npts=(x2Patch-x1Patch+1)*(y2Patch-y1Patch+1);
		double intensitiesPatch[]=new double [npts];
		double patchXY[][]=new double[npts][2];
		ImageProcessor ip=imp.getImageStack().getProcessor(maxIndex+1);
		int c=0;
	    for (int xi=x1Patch;xi<=x2Patch;xi++){
	    	for (int yi=y1Patch;yi<=y2Patch;yi++){
	    		intensitiesPatch[c]=ip.getPixel(xi, yi);
	    		patchXY[c][0]=xi;
	    		patchXY[c][1]=yi;
	    		c++;
	    	}
	    }
	    
	    System.out.println("Intensidades patch frame: "+(maxIndex+1)+" centerX:"+xEvent+" centerY:"+yEvent);
	    for (int u=0;u<intensitiesPatch.length;u++){
	    	System.out.print(intensitiesPatch[u]+",");
	    }
	    
	    int amp=ip.getPixel(xEvent,yEvent);
	    LMfunc f=new Gauss2dImproved(patchXY,intensitiesPatch,amp-50,xEvent,yEvent,1.0,1.0,50);
	    double []aguess=new double[6];
	    aguess = f.initial();
	    Object[] test = f.testdata(npts);
	    double[] s= (double[]) test[3];//Weights' matrix
	    boolean[] vary = new boolean[aguess.length];
	    for( int i = 0; i < aguess.length; i++ ) vary[i] = true;
	    
	    
	    
	    try {
	      LMauthor.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 1000, 2,limits);
	    }
	    catch(Exception ex) {
	      System.err.println("Exception caught: " + ex.getMessage());
	      System.exit(1);
	    }
		//Fin de código de prueba
		int xCenter=(int)(aguess[2]+0.5);
		int yCenter=(int)(aguess[3]+0.5);
		
		updateAllEventIntensities(intensities,xCenter,yCenter);
		*/
		double background=getAvgEventBackground(intensities,startFrame,endFrame);
		int endAnalysis=maxIndex+deltaWindow;
		if (endAnalysis>=impEndFrame) endAnalysis=impEndFrame-1;
		
		Event ev=verifyExponentialFinal(traj,xEvent,yEvent,maxIndex,endFrame,startFrame,intensities,background,fitPatchSize);
		return ev;
	}
	
	public Event verifyExponentialImproved(Trajectory traj, int centerX, int centerY, int startPos,int endPos, int startFrame, double[] intensities, double background){
		/*Code to improve the exponential fitting
		double originalBg=background;
		int bgThresholdIndex=endPos;
		boolean thresholdReached=false;
		int index=startPos;
		double threshold=background;//+(0.25*background);
		while(index<impEndFrame && !thresholdReached){
			if (intensities[index]<=threshold){
				thresholdReached=true;
				bgThresholdIndex=index;
			}else{
				index++;
			}
		}
		endPos=bgThresholdIndex;
		*/
		double x[]=new double[endPos-startPos+1];
		double y[]=new double[endPos-startPos+1];
		double gof=0;
		double expParams[] = new double[2];
		for (int i=startPos;i<=endPos;i++){
			x[i-startPos]=i+1;
			y[i-startPos]=intensities[i];
		}
		
		
		//Test code to plot every intensity along the movie
		double completeX[]=new double[impEndFrame];
		for(int i=0;i<impEndFrame;i++){
			completeX[i]=i+1;
		}
		
		double completeY[]=intensities;
		
		double slopeX[]=new double[impEndFrame-1];
		double slopeY[]=new double[impEndFrame-1];
		
		for (int i=0;i<impEndFrame-1;i++){
			slopeX[i]=i+1;
			slopeY[i]=(completeY[i+1]-completeY[i])/(completeX[i+1]-completeX[i]);
		}
		
		double accelX[]=new double[impEndFrame-2];
		double accelY[]=new double[impEndFrame-2];
		
		for (int j=0;j<impEndFrame-2;j++){
			accelX[j]=j+1;
			accelY[j]=(slopeY[j+1]-slopeY[j])/(slopeX[j+1]-slopeX[j]);
		}
		
		double smoothSlopeX[]=new double[impEndFrame-2];
		double smoothSlopeY[]=new double[impEndFrame-2];
		
		for (int k=2;k<impEndFrame-4;k++){
			smoothSlopeX[k]=k+1;
			smoothSlopeY[k]=(slopeY[k-2]+2*slopeY[k-1]+3*slopeY[k]+2*slopeY[k+1]+slopeY[k+2])/9;
		}
		
		//end of test code
		
		if (endPos-startPos>2){//Filter the shortest trajectories
			
			CurveFitter checker = new CurveFitter(x, y);
			//checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+1)+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
			checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+1)+"))+"+Double.toString(background), new double[]{1.0,1.0}, false);
			gof = checker.getFitGoodness();
			expParams = checker.getParams();
			double fit[][] = new double[2][x.length];
			for(int i=0;i<x.length;i++)
			{
				fit[0][i] = x[i];
				fit[1][i] = checker.f(expParams, x[i]);
				//System.out.println("Exp: x->"+fit[0][i]+" y->"+fit[1][i]);
			}
			double tau=-1/expParams[1];
			double amplitude=expParams[0];
			//Added code for fitting exp bg
			//background=expParams[2];
			//
			
			System.out.println(checker.getResultString());
		    System.out.println("gof "+gof);
		    
			if (gof>0.5 && amplitude>(0.5*background)){//Filter according to 2 parameters of the exp adjust: gof and amplitude
				
				int empiricLimit=startPos+(int)tau;
				if (empiricLimit>=smoothSlopeY.length) empiricLimit=smoothSlopeY.length-1;
				boolean negSpeed=false;
				int negativeChanges=0;
				int positiveChanges=0;
				int startTest=startPos-1;
				if (startTest<1) startTest=1;
				for (int i=startTest;i<empiricLimit;i++){
					if (smoothSlopeY[i-1]>0 && smoothSlopeY[i]<0){
						negSpeed=true;
						negativeChanges++;
						System.out.println("Cambio negativo");
					}
					if (smoothSlopeY[i-1]<0 && smoothSlopeY[i]>0){
						negSpeed=false;
						positiveChanges++;
						System.out.println("Cambio positivo");
					}
				}
				if (negativeChanges<=1 && positiveChanges<=1){
					double yprueba[]=new double[x.length];
				    for(int i=0;i<x.length;i++){
							yprueba[i]=fit[1][i];
					}
					
				    
	
				    int patchSize=6;//Customizable variable by the user
				    
				    
				    boolean eventGauss=true;
					ImageStack is=imp.getImageStack();
					double [] aguess=new double[6];
					try{
						FileWriter fstream = new FileWriter("out.txt",true);
						PrintWriter out = new PrintWriter(fstream);
						//Codigo de prueba para ver como arreglar lo de "Matrix is singular" en el ajuste
						int testLimit=startPos+(int)(tau+0.5);
						if (testLimit>=smoothSlopeY.length) testLimit=smoothSlopeY.length-1;
						//fin codigo
						double gaussAmp=256.;
						for (int aux=startPos+1;aux<=testLimit;aux++){
							ImageProcessor ip=is.getProcessor(aux);
							
							int x1Patch=centerX-patchSize;
						    int x2Patch=centerX+patchSize;
						    int y1Patch=centerY-patchSize;
						    int y2Patch=centerY+patchSize;
						    
						    if (x1Patch<0) x1Patch=0;
							if (y1Patch<0) y1Patch=0;
							if (x2Patch>=imp.getWidth()){
								x2Patch=imp.getWidth()-1;
							}
							if (y2Patch>=imp.getHeight()){
								y2Patch=imp.getHeight()-1;
							}
							double []limits=new double[4];
							limits[0]=x1Patch;
							limits[1]=x2Patch;
							limits[2]=y1Patch;
							limits[3]=y2Patch;
							
							int npts=(x2Patch-x1Patch+1)*(y2Patch-y1Patch+1);
							double intensitiesPatch[]=new double [npts];
							double patchXY[][]=new double[npts][2];
							
							int c=0;
						    for (int xi=x1Patch;xi<=x2Patch;xi++){
						    	for (int yi=y1Patch;yi<=y2Patch;yi++){
						    		intensitiesPatch[c]=ip.getPixel(xi, yi);
						    		patchXY[c][0]=xi;
						    		patchXY[c][1]=yi;
						    		c++;
						    	}
						    }
						    
						    System.out.println("Intensidades patch frame: "+aux+" centerX:"+centerX+" centerY:"+centerY);
						    for (int u=0;u<intensitiesPatch.length;u++){
						    	System.out.print(intensitiesPatch[u]+",");
						    }
						    
						    int amp=ip.getPixel(centerX,centerY);
						    LMfunc f=new Gauss2dImproved(patchXY,intensitiesPatch,amp-background,centerX,centerY,1.0,1.0,background);
						    
						    aguess = f.initial();
						    Object[] test = f.testdata(npts);
						    double[] s= (double[]) test[3];//Weights' matrix
						    boolean[] vary = new boolean[aguess.length];
						    for( int i = 0; i < aguess.length; i++ ) vary[i] = true;
						    
						    
						    
						    try {
						      LMauthor.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 100, 2,limits);
						    }
						    catch(Exception ex) {
						      System.err.println("Exception caught: " + ex.getMessage());
						      System.exit(1);
						    }
						    if (aguess[5]<gaussAmp){
						    	if (aguess[5]<0) eventGauss=false;
						    	else gaussAmp=aguess[5];
						    }
						    else{
						    	eventGauss=false;
						    	System.out.println("NO ES EVENTO");
						    }
						    System.out.println("Frame aux: "+aux+" Sigmax: "+aguess[0]+" Sigmay: "+aguess[1]+" x0: "+(int)(aguess[2]+0.5)+" y0: "+(int)(aguess[3]+0.5)+" b: "+aguess[4]+" amp: "+aguess[5]);
						    out.println(" Traj "+traj.getId()+"Frame aux: "+aux+" Sigmax: "+aguess[0]+" Sigmay: "+aguess[1]+" x0: "+(int)(aguess[2]+0.5)+" y0: "+(int)(aguess[3]+0.5)+" b: "+aguess[4]+" amp: "+aguess[5]);
						    out.println("Chi square: "+LMauthor.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
						    out.println("R2: "+LMauthor.rSquared(patchXY, aguess, intensitiesPatch, s, f));
						    centerX=(int)(aguess[2]+0.5);
						    centerY=(int)(aguess[3]+0.5);
						    //amp=(int)aguess[5];
						}
						out.println(checker.getResultString());
						out.close();
					}catch(Exception e){
						System.err.println("Error: " + e.getMessage());
					}
					
				  //Code for testing graphically if the filtering is working
					if (eventGauss){
					/*PlotWindow.noGridLines = false;
				    //Plot plot = new Plot("Trajectory: "+traj.getId(),"X Axis","Y Axis",completeX,completeY);
					Plot plot = new Plot("Neg: "+negativeChanges+" Pos: "+positiveChanges+" Tau: "+tau+" Trajectory: "+traj.getId()+" x: "+(int)(aguess[2]+0.5)+" y: "+(int)(aguess[3]+0.5),"X Axis","Y Axis",smoothSlopeX,smoothSlopeY);
				    plot.setLimits(0, impEndFrame, -255, 255);
				    plot.setLineWidth(1);
				    plot.setColor(Color.red);
				    plot.addPoints(x,yprueba,PlotWindow.X);
				    plot.addPoints(x,yprueba,PlotWindow.LINE);
				    plot.drawLine(1, background, impEndFrame, background);
				    /*plot.addPoints(completeX,completeY,PlotWindow.X);
				    plot.addPoints(completeX,completeY,PlotWindow.LINE);
				    plot.show();
				    Plot plot2 = new Plot("Neg: "+negativeChanges+" Pos: "+positiveChanges+" Tau: "+tau+" Trajectory: "+traj.getId()+" x: "+(int)traj.getParticlesPro()[startPos+1-startFrame].y+" y: "+(int)traj.getParticlesPro()[startPos+1-startFrame].x,"X Axis","Y Axis",slopeX,slopeY);
				    plot2.setLimits(0, impEndFrame, -255, 255);
				    plot2.setLineWidth(1);
				    plot2.setColor(Color.green);
				    plot2.addPoints(x,yprueba,PlotWindow.X);
				    plot2.addPoints(x,yprueba,PlotWindow.LINE);
				    plot2.drawLine(1, background, impEndFrame, background);;
				    plot2.show();*/
				    //end of testing code
				    //System.out.println("Sigmax: "+aguess[0]+" Sigmay: "+aguess[1]+" x0: "+(int)(aguess[2]+0.5)+" y0: "+(int)(aguess[3]+0.5)+" b: "+aguess[4]+" amp: "+aguess[5]);
				
					
				    //To do: eventRadius and T mean (actually they are initialized in 0)
				    
					Event eventTested=new Event(traj.getId(),1.387*aguess[0],1.387*aguess[1],(int)(aguess[2]+0.5),(int)(aguess[3]+0.5),startPos+1,endPos+1,amplitude,tau,null,intensities,null,null);
					return eventTested;
					}
					else{
						return null;
					}
				}else{
					return null;
				}		
		    }
			return null;
		}
		return null;
	}

	void getEventIntensities(double[] intensities,int startFrame, int endFrame, Particle[] trajParticles){
		Particle particle;
		double x,y;
		int xEvent,yEvent;
		int lastX=0;
		int lastY=0;
		int xmin,xmax,ymin,ymax;
		int numPixels;
		
		ImageStack is=imp.getImageStack();
		
		for (int i=startFrame;i<=endFrame;i++){
			particle=trajParticles[i-startFrame];
			x=particle.y;
			y=particle.x;
			
			xEvent=(int) (x+0.5);
			yEvent=(int) (y+0.5);
			
			if (x==-1 && y==-1){
				xEvent=lastX;
				yEvent=lastY;
			}else{
				lastX=xEvent;
				lastY=yEvent;
			}
			
			xmin=xEvent-areaRadius;
			xmax=xEvent+areaRadius;
			ymin=yEvent-areaRadius;
			ymax=yEvent+areaRadius;
			
			if (xmin<0) xmin=0;
			if (ymin<0) ymin=0;
			if (xmax>=imp.getWidth()){
				xmax=imp.getWidth()-1;
			}
			if (ymax>=imp.getHeight()){
				ymax=imp.getHeight()-1;
			}
			
			numPixels=getAreaSize(xmin,xmax,ymin,ymax);
			System.out.println("Obtendré intensidad del frame: "+i);
			intensities[i-1]=getAreaIntensity(is,i,xmin,xmax,ymin,ymax,numPixels);
			//System.out.println("i: "+i+" (x,y)=("+xEvent+","+yEvent+"): "+intensities[i-1]);
		}	
	}
	
	void getBackgroundIntensities(double[] intensities, int xStart, int yStart, int startFrame, int xEnd, int yEnd, int endFrame){

		ImageStack is=imp.getImageStack();
		
		int xsmin,xsmax,ysmin,ysmax,xemin,xemax,yemin,yemax;
		
		xsmin=xStart-areaRadius;
		xsmax=xStart+areaRadius;
		ysmin=yStart-areaRadius;
		ysmax=yStart+areaRadius;
		xemin=xEnd-areaRadius;
		xemax=xEnd+areaRadius;
		yemin=yEnd-areaRadius;
		yemax=yEnd+areaRadius;
		
		if (xsmin<0) xsmin=0;
		if (ysmin<0) ysmin=0;
		if (xemin<0) xemin=0;
		if (yemin<0) yemin=0;
		if (xsmax>imp.getWidth()) xsmax=imp.getWidth()-1;
		if (ysmax>imp.getHeight()) ysmax=imp.getHeight()-1;
		if (xemax>imp.getWidth()) xemax=imp.getWidth()-1;
		if (yemax>imp.getHeight()) yemax=imp.getHeight()-1;
		
		int numPixelsStart=getAreaSize(xsmin,xsmax,ysmin,ysmax);
		int numPixelsEnd=getAreaSize(xemin,xemax,yemin,yemax);
		
		for (int i=1;i<startFrame;i++){
			intensities[i-1]=getAreaIntensity(is,i,xsmin,xsmax,ysmin,ysmax,numPixelsStart);
			//System.out.println("bg i: "+i+" (x,y)=("+xStart+","+yStart+"): "+intensities[i-1]);
		}
		for (int j=endFrame+1;j<=impEndFrame;j++){
			intensities[j-1]=getAreaIntensity(is,j,xemin,xemax,yemin,yemax,numPixelsEnd);
			//System.out.println("bg i: "+j+" (x,y)=("+xEnd+","+yEnd+"): "+intensities[j-1]);
		}
	}
	
	double getAreaIntensity(ImageStack is, int frame, int xmin, int xmax, int ymin, int ymax,int numPixels){
		ImageProcessor ip=is.getProcessor(frame);
		double intensity=0.0;
		for (int xi=xmin;xi<=xmax;xi++){
			for(int yi=ymin;yi<=ymax;yi++){
				intensity=intensity+ip.getPixel(xi, yi);
			}
		}
		intensity=intensity/numPixels;
		return intensity;
	}
	
	int getAreaSize(int xmin,int xmax,int ymin,int ymax){
		return (xmax-xmin+1)*(ymax-ymin+1);
	}
	
	double getAvgEventBackground(double[] intensities,int startFrame, int endFrame){
		double background=0.0;
		int nFrames=0;
		for (int i=0;i<startFrame-1;i++){
			background=background+intensities[i];
			nFrames++;
		}
		for (int j=endFrame;j<impEndFrame;j++){
			background=background+intensities[j];
			nFrames++;
		}
		if (nFrames==0) return background;
		return background/nFrames;
	}
	
	int getMaxIntensityIndex(double[] intensities, int startFrame, int endFrame){
		double maxIntensity=-1.0;
		int maxIndex=-1;
		for (int i=startFrame-1;i<endFrame;i++){
			//Before it was >= the sign of the comparision
			if (intensities[i]>maxIntensity){
				maxIntensity=intensities[i];
				maxIndex=i;
			}
		}
		System.out.println("Max Intensity: "+maxIntensity+" frame: "+(maxIndex+1));
		return maxIndex;
	}
	
	//Fit all the intensities to the maximum intensity zone (event candidate)
	void updateAllEventIntensities(double[] intensities,int xEvent,int yEvent){
		ImageStack is=imp.getImageStack();
		ImageProcessor ip;
		
		int xmin,xmax,ymin,ymax;
		xmin=xEvent-areaRadius;
		xmax=xEvent+areaRadius;
		ymin=yEvent-areaRadius;
		ymax=yEvent+areaRadius;
		
		if (xmin<0) xmin=0;
		if (ymin<0) ymin=0;
		if (xmax>=imp.getWidth()){
			xmax=imp.getWidth()-1;
		}
		if (ymax>=imp.getHeight()){
			ymax=imp.getHeight()-1;
		}
		
		int numPixels=getAreaSize(xmin,xmax,ymin,ymax);
		
		for (int i=1;i<=impEndFrame;i++){
			intensities[i-1]=getAreaIntensity(is,i,xmin,xmax,ymin,ymax,numPixels);
		}
		System.out.println("Actualicé centro del evento a x: "+xEvent+" y: "+yEvent);
	}
	
	public int[] getMaxIncrease(double [] intensities, double background,  int frameStart, int nPreviousFrames){
		double maxIncrease=-1;
		int startIndex=frameStart-nPreviousFrames-1;
		if (startIndex<1) startIndex=1;
		int startCurrentInc=startIndex;
		int startIncrease=startIndex;
		int endIncrease=startIndex;
		double currentIncrease=0.;
		double delta=0.;
		double theoricalIncrease=intensities[frameStart-1]-background;
		double maxIntensity=intensities[startIndex];
		double maxIntIndex=startIndex;
		//double subitDelta=theoricalIncrease/4;
		//it was 0.5 originally
		double theoricalRatioInc=0.5;
		//boolean subitIncrease=false;
		for (int i=startIndex;i<(frameStart-1);i++){
			//if (intensities[i]<=1.25*background) theoricalStart=true;
			//if (theoricalStart){
				if (intensities[i+1]>maxIntensity){
					maxIntensity=intensities[i+1];
					maxIntIndex=i+1;
				}
				delta=intensities[i+1]-intensities[i];
				//if (delta>-1.0*theoricalRatioInc*theoricalIncrease){
				if (delta>0.){
					currentIncrease=currentIncrease+delta;
					if (currentIncrease>=maxIncrease){
						//if (delta>=subitDelta) subitIncrease=true;
						maxIncrease=currentIncrease;
						startIncrease=startCurrentInc;
						endIncrease=i+1;
					}
				}else{
					startCurrentInc=i+1;
					currentIncrease=0.;
				}
			//}
		}
		if (maxIntIndex<2){//a 0,0 pair means that the movie in that area starts with its maximum intensity, and require another kind of treatment
			return new int[]{0,0};
		}
		
		int frameDiff=endIncrease-startIncrease;
		
		if (frameDiff==0) return new int[] {-1,-1};
		//if (!subitIncrease) return -1.0;
		double increase=(intensities[endIncrease]-intensities[startIncrease]);
		
		try{
			FileWriter fstream = new FileWriter("test.txt",true);
			PrintWriter out = new PrintWriter(fstream);
			out.println("Frame start: "+ frameStart+" Inicio incremento: "+startIncrease+" final incremento: "+endIncrease+"incremento "+increase);
			out.close();
		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		if (increase<theoricalRatioInc*theoricalIncrease)  {
			startIncrease=-1;
			endIncrease=-1;
		}
		return new int[] {startIncrease,endIncrease};
	}
	
	public Event evaluateSelectedArea(int x1, int y1, int x2, int y2){
		double[] intensities=new double[impEndFrame];
		ImageStack is=imp.getImageStack();
		int xCenter=((x2-x1)/2)+x1;
		int yCenter=((y2-y1)/2)+y1;
		ImageProcessor ip;
		int d;
		for (int i=1;i<impEndFrame;i++){
			ip=is.getProcessor(i);
			d=0;
			for (int xi=xCenter-1;xi<=xCenter+1;xi++){
				for (int yi=yCenter-1;yi<=yCenter+1;yi++){
					d++;
					intensities[i-1]=intensities[i-1]+ip.getPixel(xi, yi);
				}
			}
			intensities[i-1]=intensities[i-1]/d;
		}
		int maxIndex=getMaxIntensityIndex(intensities,1,impEndFrame-1);
		int areaSize=(x2-x1+1)*(y2-y1+1);
		double [][] patchXY=new double[areaSize][2];
		int c=0;
		for(int j=x1;j<=x2;j++){
			for (int k=y1;k<=y2;k++){
				patchXY[c][0]=j;
				patchXY[c][1]=k;
				c++;
			}
		}
		boolean testGauss=true;
		int centerX=xCenter;
		int centerY=yCenter;
		int aux=maxIndex+1;
		double lastGaussAmp=300.;
		double lastStdX=0.;
		double lastStdY=0.;
		double std1=1.;
		double std2=1.;
		double backgroundAvg=0.;
		int firstCenterX=0;
		int firstCenterY=0;
		ArrayList<Double> stdXArray=new ArrayList<Double>();
		ArrayList<Double> stdYArray=new ArrayList<Double>();
		
		int maxAmpIndex=-1;
		double maxAmp=0.;
		int minStdIndex=-1;
		double minStd=0.;
		
		double avgR2=0.0;
		Event event=null;
		while(aux<=impEndFrame && testGauss){
			double []intensitiesPatch= new double[areaSize];
			c=0;
			ip=is.getProcessor(aux);
			for (int xi=x1;xi<=x2;xi++){
				for (int yi=y1;yi<=y2;yi++){
					intensitiesPatch[c]=ip.getPixel(xi, yi);
					c++;
				}
			}
			
			for (int h=0;h<intensitiesPatch.length;h++) System.out.print(intensitiesPatch[h]+",");
			int endPos=maxIndex+15;
			if (endPos<impEndFrame) endPos=impEndFrame-1;
			double background=getAvgEventBackground(intensities,maxIndex,endPos);
			int amp=ip.getPixel(centerX,centerY);
			LMfunc f=new Gauss2dImproved(patchXY,intensitiesPatch,amp-background,centerX,centerY,1.0,1.0,background);
		    //LMfunc f=new Gauss2dIsotropic(patchXY,intensitiesPatch,amp-background,centerX,centerY,1.0,background);
		    //double[] aguess=new double[5];
		    double[] aguess=new double[6];
			aguess = f.initial();
		    Object[] test = f.testdata(areaSize);
		    double[] s= (double[]) test[3];//Weights' matrix
		    boolean[] vary = new boolean[aguess.length];
		    for( int i = 0; i < aguess.length; i++ ) vary[i] = true;
		    double []limits=new double[4];
		    limits[0]=x1;
		    limits[1]=x2;
		    limits[2]=y1;
		    limits[3]=y2;
		    
		    
		    try {
		    	//LMIsotropic2dGaussian.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 1000, 2,limits);
		      LMauthor.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 500, 2,limits);
		    }
		    catch(Exception ex) {
		      System.err.println("Exception caught: " + ex.getMessage());
		      System.exit(1);
		    }
		    //centerX=(int)(aguess[1]+0.5);
		    //centerY=(int)(aguess[2]+0.5);
		    centerX=(int)(aguess[2]+0.5);
		    centerY=(int)(aguess[3]+0.5);
		    //double r2=LMIsotropic2dGaussian.rSquared(patchXY, aguess, intensitiesPatch, s, f);
		    double r2=LMauthor.rSquared(patchXY, aguess, intensitiesPatch, s, f);
		    System.out.println("R2: "+r2);
		    //System.out.println("Desvest: "+aguess[0]+"Centro x: "+aguess[1]+" Centro y: "+aguess[2]+" amplitude: "+aguess[4]);
		    System.out.println("Desvestx: "+aguess[0]+"Desvesty: "+aguess[1]+"Centro x: "+aguess[2]+" Centro y: "+aguess[3]+" amplitude: "+aguess[5]);
		    if (aux==maxIndex+1){//The first 
		    	//if (r2<0.7) return null; //if the first vesicle, the max intensity, doesnt fit very well, we discard that vesicle
			    //firstCenterX=(int)(aguess[1]+0.5);
			    //firstCenterY=(int)(aguess[2]+0.5);
		    	firstCenterX=(int)(aguess[2]+0.5);
		    	firstCenterY=(int)(aguess[3]+0.5);
			    std1=aguess[0]*1.5;
			    std2=aguess[0]*3.;
		    	minStd=aguess[0];
			    minStdIndex=aux;
			    updateAllEventIntensities(intensities,firstCenterX,firstCenterY);
		    }else{
		    	if (aguess[0]<minStd){//We let a little difference possible due to docking vesicles that keep its size cuasi constant for a few frames
		    		minStd=aguess[0];//IMPORTANT: Maybe this filter doesn't work really well, candidate to removing
		    		minStdIndex=aux;
		    	}
		    }
		    /*if (aguess[4]>maxAmp){
	    		maxAmp=aguess[4];
	    		maxAmpIndex=aux;
	    	}*/
		    if (aguess[5]>maxAmp){
	    		maxAmp=aguess[5];
	    		maxAmpIndex=aux;
	    	}
		    try{
		    	FileWriter fstream = new FileWriter("test.txt",true);
				PrintWriter out = new PrintWriter(fstream);
		    	//out.println("Traj: "+id+" Frame aux: "+aux+" Sigma: "+aguess[0]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[3]+" amp: "+aguess[4]);
			    //out.println("Chi square: "+LMIsotropic2dGaussian.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
				out.println(" Frame aux: "+aux+" Sigmax: "+aguess[0]+"Sigmay: "+aguess[1]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[4]+" amp: "+aguess[5]);
			    out.println("Chi square: "+LMauthor.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
				out.println("R2: "+r2);
			    out.close();
		    }catch(Exception e){
				System.err.println("Error: " + e.getMessage());
		    }
		    /*if (r2>=0.5 && (lastGaussAmp-aguess[4])>-(lastGaussAmp*0.1) && (aguess[0]-lastStd)>-(lastStd*0.25)){//change for dynamic threshold values depending on the specific situation!!!!!
		    	lastGaussAmp=aguess[4];
		    	lastStd=aguess[0];
		    	backgroundAvg=backgroundAvg+aguess[3];
		    	stdArray.add(aguess[0]);
		    	aux++;
		    }else{
		    	testGauss=false;
		    }*/
		    if (r2>=0.5 && (lastGaussAmp-aguess[5])>-(lastGaussAmp*0.1) && (aguess[0]-lastStdX)>-(lastStdX*0.25) && (aguess[1]-lastStdY)>-(lastStdY*0.25)){//change for dynamic threshold values depending on the specific situation!!!!!
		    	lastGaussAmp=aguess[5];
		    	lastStdX=aguess[0];
		    	lastStdY=aguess[1];
		    	backgroundAvg=backgroundAvg+aguess[4];
		    	stdXArray.add(aguess[0]);
		    	stdYArray.add(aguess[1]);
		    	avgR2=avgR2+r2;
		    	aux++;
		    }else{
		    	testGauss=false;
		    }
		}
		if (maxAmpIndex!=(maxIndex+1)){// || minStdIndex!=(maxIndex+1)){
			return null;
		}else{
		backgroundAvg=backgroundAvg/(aux-(maxIndex+1));
	    //int []maxIncreaseIndexes=getMaxIncreaseIndexes(intensities,maxIndex+1);
	    //Plotting intensities code
		int [] increaseData=new int[2];
		increaseData=getMaxIncrease(intensities,backgroundAvg,maxIndex+1,maxIndex);
		double increase=increaseData[0];
	    double increaseRatio=increaseData[1];
		//System.out.println("start max, endmax: "+maxIncreaseIndexes[0]+" "+maxIncreaseIndexes[1]);
	    
	    avgR2=avgR2/(aux-(maxIndex+1));
	    
	    
	    double[] x=new double[impEndFrame];
	    for (int i=1;i<=impEndFrame;i++){
	    	x[i-1]=i;
	    }
	    
	    
	    /*new section of code
	    boolean nearToBg=false;
	    while (aux!=impEndFrame && nearToBg){
	    	aux++;
	    	if (intensities[aux-1]<=1.2*backgroundAvg){
	    		nearToBg=false;
	    	}
	    }
	    int framesFitted=aux-(maxIndex+1);
	    //new section of code*/
	    int framesFitted=aux-(maxIndex+1)+10;//the original and probed line of code

	    int maxLimit=maxIndex+framesFitted;
	    if (maxLimit>=impEndFrame){
	    	maxLimit=impEndFrame-1;
	    	framesFitted=impEndFrame-(maxIndex+1);
	    	}
	    double[] xFitter=new double[framesFitted];
	    double[] yFitter=new double[framesFitted];
	    

	    for (int i=maxIndex;i<maxLimit;i++){
	    	xFitter[i-maxIndex]=i+1;
	    	yFitter[i-maxIndex]=intensities[i];
	    }
	    
	    double slopeStdXFit=lineFitting(stdXArray,maxIndex+1);
	    double slopeStdYFit=lineFitting(stdYArray,maxIndex+1);
	    double maxIntensity=intensities[maxIndex];
	    
	    double theoricalMaxInc=intensities[maxIndex]-backgroundAvg;
	    //Ajuste exponencial
	    if(aux-(maxIndex+1)>1 && (slopeStdXFit>0 || slopeStdYFit>0) && (maxIndex==0 || increaseRatio>2) && (maxIntensity>=1.5*backgroundAvg)){
		    CurveFitter checker = new CurveFitter(xFitter, yFitter);
			//checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+1)+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
			checker.doCustomFit( "y=a*exp(b*(x-"+(maxIndex+1)+"))+"+backgroundAvg, new double[]{1.0,1.0}, false);
			double gof = checker.getFitGoodness();
			double[] expParams=new double[2];
			expParams = checker.getParams();
			double fit[][] = new double[xFitter.length][2];
			for(int i=0;i<xFitter.length;i++)
			{
				fit[i][0] = xFitter[i];
				fit[i][1] = checker.f(expParams, xFitter[i]);
				//System.out.println("Exp: x->"+fit[0][i]+" y->"+fit[1][i]);
			}
			double tau=-1/expParams[1];
			double amplitude=expParams[0];
			try{
				FileWriter fstream = new FileWriter("test.txt",true);
				PrintWriter out = new PrintWriter(fstream);
				out.println("Ajuste desde: "+maxIndex+" hasta "+maxLimit);
				out.println(checker.getResultString());
				out.println("gof "+gof);
				out.close();
			}
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}
			//int startMaxIncrease=maxIncreaseIndexes[0];
		    //int endMaxIncrease=maxIncreaseIndexes[1];
		    //double increaseRatio=-1;
		    //if (maxIndex!=0)increaseRatio=(intensities[endMaxIncrease]-intensities[startMaxIncrease])/(endMaxIncrease-startMaxIncrease);
		    if ((maxIndex==0 && gof>=0.85) || (maxIndex!=0 && gof>=0.75)){// && framesFitted<=tau){// && (maxIndex==0 || increaseRatio>10)){
		    	System.out.println("Esto hipotéticamente es un evento de fusión :), tau: "+tau+" amp: "+amplitude);
		    	
		    	event=new Event(0,lastStdX,lastStdY,firstCenterX,firstCenterY,maxIndex,maxIndex+(int)(tau+0.5),amplitude,tau,null,intensities,fit,increaseData);
		    	
			    //this code was originally on line 1220
			    int r1=(int)(std1+0.5);
			    int r2=(int)(std2+0.5);
			    double [] intensities2=new double[impEndFrame];
			    double [] vesicleIntensities=new double[impEndFrame];
			    int x2_1=centerX-r2;
			    int x2_2=centerX+r2;
			    int y2_1=centerY-r2;
			    int y2_2=centerY+r2;
			    double r2cuad=r2*r2;
			    double r1cuad=r1*r1;
			    double xaux,yaux;
			    double sumcuad;
			    int npixels;
			    int vesiclenpix;
			    for (int f=1;f<impEndFrame;f++){
			    	npixels=0;
			    	vesiclenpix=0;
			    	ip=is.getProcessor(f);
				    for (int xi=x2_1;xi<=x2_2;xi++){
				    	for (int yi=y2_1;yi<=y2_2;yi++){
				    		xaux=xi-centerX;
				    		yaux=yi-centerY;
				    		sumcuad=(xaux*xaux)+(yaux*yaux);
				    		if (sumcuad>r1cuad && sumcuad<r2cuad ){
				    			intensities2[f-1]=intensities2[f-1]+ip.getPixel(xi, yi);
				    			npixels++;
				    			//System.out.println("Pixel2 "+"xi: "+xi+" yi: "+yi);
				    		}else{
				    			if (sumcuad<=r1cuad){
				    				vesicleIntensities[f-1]=vesicleIntensities[f-1]+ip.getPixel(xi, yi);
				    				vesiclenpix++;
				    			}
				    		}
				    	}
				    }
				    intensities2[f-1]=intensities2[f-1]/npixels;
				    vesicleIntensities[f-1]=vesicleIntensities[f-1]/vesiclenpix;
			    }
			    //
			    
			    PlotWindow.noGridLines = false;
			    //Plot plot = new Plot("Trajectory: "+traj.getId(),"X Axis","Y Axis",completeX,completeY);
				Plot plot = new Plot(" prob: "+(avgR2*gof)+" r2: "+avgR2+" gof: "+gof+" x: "+centerX+" y: "+centerY+" pendiente inc: "+increaseRatio,"X Axis","Y Axis",x,intensities);
			    //Plot plot = new Plot(" x: "+centerX+" y: "+centerY,"X Axis","Y Axis",x,intensities);
			    plot.setLimits(0, impEndFrame, 0, 255);
			    plot.setLineWidth(1);
			    plot.setColor(Color.red);
			    //plot.addPoints(xFitter,yFit,PlotWindow.LINE);
			    plot.drawLine(1, backgroundAvg, impEndFrame, backgroundAvg);
			    plot.show();
			    //End plotting code
		    }
		    
		    
	    }  
		    
	
	return event;
	}			
		
	}
	
	double lineFitting(ArrayList<Double> inputArray, int startX){
		int length=inputArray.size();
		double[] x=new double[length];
		double[] y=new double[length];
		for (int i=0;i<length;i++){
			x[i]=i+startX;
			y[i]=inputArray.get(i);
		}
		CurveFitter checker=new CurveFitter(x,y);
		checker.doCustomFit("y=a*x+b", new double[]{1.0,0.0}, false);
		double gof = checker.getFitGoodness();
		double[] expParams=new double[2];
		expParams = checker.getParams();
		System.out.println(checker.getResultString());
	    System.out.println("gof recta "+gof);
		return expParams[0];
	}
	boolean compareNearLineFit(ArrayList<Double> inputArray, int startX, double r2Allowed, double teorethicalSlope, double ratioTolerance){
		int length=inputArray.size();
		double[] x=new double[length];
		double[] y=new double[length];
		for (int i=0;i<length;i++){
			x[i]=i+startX;
			y[i]=inputArray.get(i);
		}
		CurveFitter checker=new CurveFitter(x,y);
		checker.doCustomFit("y=a*x+b", new double[]{1.0,0.0}, false);
		double r2 = checker.getRSquared();
		double[] expParams=new double[2];
		expParams = checker.getParams();
		System.out.println(checker.getResultString());
		double slope=expParams[0];
		try{
			FileWriter fstream = new FileWriter("test.txt",true);
			PrintWriter out = new PrintWriter(fstream);
			out.println("Pendiente resultante: "+slope);
			out.println("R2: "+r2);
			out.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		if (r2<r2Allowed) return false;
		if (slope<teorethicalSlope*-1.0*(1+ratioTolerance) || slope>teorethicalSlope*(1+ratioTolerance)) return false;
		return true;
	}
	
	boolean compareExactLineFit(ArrayList<Double> inputArray, int startX, double r2Allowed, double teorethicalSlope, double ratioTolerance){
		int length=inputArray.size();
		double[] x=new double[length];
		double[] y=new double[length];
		for (int i=0;i<length;i++){
			x[i]=i+startX;
			y[i]=inputArray.get(i);
		}
		CurveFitter checker=new CurveFitter(x,y);
		checker.doCustomFit("y=a*x+b", new double[]{1.0,0.0}, false);
		double r2 = checker.getRSquared();
		double[] expParams=new double[2];
		expParams = checker.getParams();
		System.out.println(checker.getResultString());
		double slope=expParams[0];
		try{
			FileWriter fstream = new FileWriter("test.txt",true);
			PrintWriter out = new PrintWriter(fstream);
			out.println("Pendiente resultante: "+slope);
			out.println("R2: "+r2);
			out.close();
		}
		catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		if (r2<r2Allowed) return false;
		if (slope<teorethicalSlope*(1-ratioTolerance) || slope>teorethicalSlope*(1+ratioTolerance)) return false;
		return true;
	}
	
	public Event verifyExponentialFinal(Trajectory traj, int centerX, int centerY, int startPos,int endPos, int startFrame, double[] intensities, double background,int patchSize){

									
		int x1Patch=centerX-patchSize;
		int x2Patch=centerX+patchSize;
		int y1Patch=centerY-patchSize;
		int y2Patch=centerY+patchSize;
						    
		if (x1Patch<0) x1Patch=0;
		if (y1Patch<0) y1Patch=0;
		if (x2Patch>=imp.getWidth()){
			x2Patch=imp.getWidth()-1;
		}
		if (y2Patch>=imp.getHeight()){
			y2Patch=imp.getHeight()-1;
		}
		Event event=evaluateSelectedTraj(traj.getId(), startPos, endPos, intensities,centerX,centerY,x1Patch,y1Patch,x2Patch,y2Patch);
		return event;		
	}
	
	public Event evaluateSelectedTraj(int id, int startFrame, int endPos, double [] intensities,int xCenter, int yCenter, int x1, int y1, int x2, int y2){
		int maxIndex=startFrame;
		int areaSize=(x2-x1+1)*(y2-y1+1);
		double [][] patchXY=new double[areaSize][2];
		int c=0;
		for(int j=x1;j<=x2;j++){
			for (int k=y1;k<=y2;k++){
				patchXY[c][0]=j;
				patchXY[c][1]=k;
				c++;
			}
		}
		boolean testGauss=true;
		int centerX=xCenter;
		int centerY=yCenter;
		int aux=maxIndex+1;
		double lastGaussAmp=300.;
		double lastStdX=0.;
		double lastStdY=0.;
		double std1=1.;
		double std2=1.;
		double backgroundAvg=0.;
		int firstCenterX=0;
		int firstCenterY=0;
		ArrayList<Double> stdXArray=new ArrayList<Double>();
		ArrayList<Double> stdYArray=new ArrayList<Double>();
		ArrayList<Double> ampArray=new ArrayList<Double>();
		ImageStack is=imp.getImageStack();
		ImageProcessor ip;
		
		int maxAmpIndex=-1;
		double maxAmp=0.;
		int minStdIndex=-1;
		double minStd=0.;
		
		double avgR2=0.0;
		Event event=null;
		
		double vesicleDisplacement=0.;
		double displacementAccepted=0.;
		
		int lastXPos=-1;
		int lastYPos=-1;
		
		double distance;
		
		while(aux<=impEndFrame && testGauss){
			double []intensitiesPatch= new double[areaSize];
			c=0;
			ip=is.getProcessor(aux);
			for (int xi=x1;xi<=x2;xi++){
				for (int yi=y1;yi<=y2;yi++){
					intensitiesPatch[c]=ip.getPixel(xi, yi);
					c++;
				}
			}
			
			double background=getAvgEventBackground(intensities,maxIndex, endPos);
			int amp=ip.getPixel(centerX,centerY);
			LMfunc f=new Gauss2dImproved(patchXY,intensitiesPatch,amp-background,centerX,centerY,1.5,1.5,background);
		    //LMfunc f=new Gauss2dIsotropic(patchXY,intensitiesPatch,amp-background,centerX,centerY,1.0,background);
		    //double[] aguess=new double[5];
		    double[] aguess=new double[6];
			aguess = f.initial();
		    Object[] test = f.testdata(areaSize);
		    double[] s= (double[]) test[3];//Weights' matrix
		    boolean[] vary = new boolean[aguess.length];
		    for( int i = 0; i < aguess.length; i++ ) vary[i] = true;
		    double []limits=new double[4];
		    limits[0]=x1;
		    limits[1]=x2;
		    limits[2]=y1;
		    limits[3]=y2;
		    
		    
		    try {
		    	//LMIsotropic2dGaussian.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 1000, 2,limits);
		      LMauthor.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 1000, 2,limits);
		    }
		    catch(Exception ex) {
		      System.err.println("Exception caught: " + ex.getMessage());
		      System.exit(1);
		    }
		    centerX=(int)(aguess[2]+0.5);
		    centerY=(int)(aguess[3]+0.5);
		    
		    //double r2=LMIsotropic2dGaussian.rSquared(patchXY, aguess, intensitiesPatch, s, f);
		    double r2=LMauthor.rSquared(patchXY, aguess, intensitiesPatch, s, f);
		    
		    if (aux==maxIndex+1){//The first 
		    	//if (r2<0.7) return null; //if the first vesicle, the max intensity, doesnt fit very well, we discard that vesicle
		    	firstCenterX=(int)(aguess[2]+0.5);
		    	firstCenterY=(int)(aguess[3]+0.5);
			    std1=aguess[0]*1.5;
			    std2=aguess[0]*3.;
		    	minStd=aguess[0];
			    minStdIndex=aux;
			    updateAllEventIntensities(intensities,firstCenterX,firstCenterY);
		    }else{
		    	if (aguess[0]<minStd){//We let a little difference possible due to docking vesicles that keep its size cuasi constant for a few frames
		    		minStd=aguess[0];//IMPORTANT: Maybe this filter doesn't work really well, candidate to removing
		    		minStdIndex=aux;
		    	}
		    }
		    /*if (aguess[4]>maxAmp){
	    		maxAmp=aguess[4];
	    		maxAmpIndex=aux;
	    	}*/
		    //Official code, remove the comments and remove the next if
		    /*if (aguess[5]>maxAmp){
	    		maxAmp=aguess[5];
	    		maxAmpIndex=aux;
	    	}*/
		    if (intensities[aux-1]>maxAmp){
		    	maxAmp=intensities[aux-1];
		    	maxAmpIndex=aux;
		    }
		    try{
		    	FileWriter fstream = new FileWriter("test.txt",true);
				PrintWriter out = new PrintWriter(fstream);
		    	//out.println("Traj: "+id+" Frame aux: "+aux+" Sigma: "+aguess[0]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[3]+" amp: "+aguess[4]);
			    //out.println("Chi square: "+LMIsotropic2dGaussian.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
				out.println("Traj: "+id+" Frame aux: "+aux+" Sigmax: "+aguess[0]+"Sigmay: "+aguess[1]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[4]+" amp: "+intensities[aux-1]);
			    out.println("Chi square: "+LMauthor.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
				out.println("R2: "+r2);
			    out.close();
		    }catch(Exception e){
				System.err.println("Error: " + e.getMessage());
		    }
		    /*if (r2>=0.5 && (lastGaussAmp-aguess[4])>-(lastGaussAmp*0.1) && (aguess[0]-lastStd)>-(lastStd*0.25)){//change for dynamic threshold values depending on the specific situation!!!!!
		    	lastGaussAmp=aguess[4];
		    	lastStd=aguess[0];
		    	backgroundAvg=backgroundAvg+aguess[3];
		    	stdArray.add(aguess[0]);
		    	aux++;
		    }else{
		    	testGauss=false;
		    }*/
		    
		    //this is the official piece of code
		    if (r2>=0.4){// && (lastGaussAmp-aguess[5])>-(lastGaussAmp*0.1) && (aguess[0]-lastStdX)>-(lastStdX*0.25) && (aguess[1]-lastStdY)>-(lastStdY*0.25)){//change for dynamic threshold values depending on the specific situation!!!!!
		    	//Next line is the original, remove the comments
		    	//lastGaussAmp=aguess[5];
		    	
		    	if (lastXPos!=-1 && lastYPos!=-1){
		    		distance=Math.sqrt(Math.pow((centerX-lastXPos), 2)+Math.pow((centerY-lastYPos), 2));
			    	if (distance>2) return null;
		    		vesicleDisplacement+=distance;
			    	displacementAccepted+=1;
			    	try{
				    	FileWriter fstream = new FileWriter("test.txt",true);
						PrintWriter out = new PrintWriter(fstream);
				    	//out.println("Traj: "+id+" Frame aux: "+aux+" Sigma: "+aguess[0]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[3]+" amp: "+aguess[4]);
					    //out.println("Chi square: "+LMIsotropic2dGaussian.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
						out.println("Distance between current point and the last is: "+ distance);
					    out.close();
				    }catch(Exception e){
						System.err.println("Error: " + e.getMessage());
				    }
			    }
		    	
		    	
		    	lastGaussAmp=intensities[aux-1];
		    	lastStdX=aguess[0];
		    	lastStdY=aguess[1];
		    	lastXPos=centerX;
		    	lastYPos=centerY;
		    	backgroundAvg=backgroundAvg+aguess[4];
		    	stdXArray.add(aguess[0]);
		    	stdYArray.add(aguess[1]);
		    	ampArray.add(aguess[5]);
		    	avgR2=avgR2+r2;
		    	aux++;
		    }else{
		    	testGauss=false;
		    }
		}
		/*if (maxAmpIndex!=(maxIndex+1)){// || minStdIndex!=(maxIndex+1)){//Original piece of code 
			return null;*/
		//}else{
		
		int analysisWindowSize=fitPatchSize*2+1;
		
		if (analysisWindowSize>7 && vesicleDisplacement>=displacementAccepted){//Math.sqrt(2)){//Filter the vesicles that are moving so much and can be a transient moving vesicle and not a fusion event
			return null;
		}
		
		avgR2=avgR2/(aux-(maxIndex+1));
	    
	    if (avgR2<0.5){//The gaussian fit can't be poor for all the frames of the event in the movie
	    	return null;
	    }
		
		backgroundAvg=backgroundAvg/(aux-(maxIndex+1));
	    //int []maxIncreaseIndexes=getMaxIncreaseIndexes(intensities,maxIndex+1);
	    //Plotting intensities code
		int [] increaseData=getMaxIncrease(intensities,backgroundAvg,maxIndex+1,maxIndex);
		int startIncrease=increaseData[0];
		int endIncrease=increaseData[1];
		System.out.println("start: "+startIncrease);
		System.out.println("end: "+endIncrease);
		
	    
		//System.out.println("start max, endmax: "+maxIncreaseIndexes[0]+" "+maxIncreaseIndexes[1]);
	    
	    
	    
	    double[] x=new double[impEndFrame];
	    for (int i=1;i<=impEndFrame;i++){
	    	x[i-1]=i;
	    }
	    
	    
	    /*new section of code
	    boolean nearToBg=false;
	    while (aux!=impEndFrame && nearToBg){
	    	aux++;
	    	if (intensities[aux-1]<=1.2*backgroundAvg){
	    		nearToBg=false;
	    	}
	    }
	    int framesFitted=aux-(maxIndex+1);
	    //new section of code*/
	    int framesFitted=aux-(maxIndex+1)+15;//the original and probed line of code

	    int maxLimit=maxIndex+framesFitted;
	    if (maxLimit>=impEndFrame){
	    	maxLimit=impEndFrame-1;
	    	framesFitted=impEndFrame-(maxIndex+1);
	    }
	    double[] xFitter=new double[framesFitted];
	    double[] yFitter=new double[framesFitted];
	    

	    for (int i=maxIndex;i<maxLimit;i++){
	    	xFitter[i-maxIndex]=i+1;
	    	yFitter[i-maxIndex]=intensities[i];
	    }
	    
	    double slopeStdXFit=lineFitting(stdXArray,maxIndex+1);
	    double slopeStdYFit=lineFitting(stdYArray,maxIndex+1);
	    double maxIntensity=intensities[maxIndex];
	    
	    double theoricalMaxInc=intensities[maxIndex]-backgroundAvg;
	    
	    
	    
	    //Ajuste exponencial
	    if(aux-(maxIndex+1)>1 && startIncrease!=-1 && endIncrease!=-1){// && (maxIndex==0 || increaseRatio>0)){//&& (slopeStdXFit>0 || slopeStdYFit>0) && (maxIntensity>=1.5*backgroundAvg)){
	    	
	    	
	    	
	    	
	    	double increase=0.;
	    	double increaseRatio=0.;
	    	if (startIncrease!=0 && endIncrease!=0){
				increase=intensities[endIncrease]-intensities[startIncrease];
				increaseRatio=increase/(endIncrease-startIncrease);
			}else{
	    		increase=intensities[0]-backgroundAvg;
	    		increaseRatio=increase;
	    	}
	    	
	    	
	    	//code added for tests
		    try{
		    	FileWriter fstream = new FileWriter("test.txt",true);
				PrintWriter out = new PrintWriter(fstream);
				out.println("Increase: "+Double.toString(increaseRatio)+" number of frames: "+(aux-(maxIndex+1))+" frames hacia atrás: "+maxIndex);
				out.close();
		    }catch(Exception e){
				System.err.println("Error: " + e.getMessage());
		    }
		    
		    //end added code
	    	
	    	boolean isDocking=isDocking(xFitter,yFitter,maxIndex,stdXArray.size(),slopeStdXFit,slopeStdYFit);
		    if (isDocking && increaseRatio>1){
		    	try{
			    	FileWriter fstream = new FileWriter("test.txt",true);
					PrintWriter out = new PrintWriter(fstream);
					out.println("ES DOCKING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					out.close();
			    }catch(Exception e){
					System.err.println("Error: " + e.getMessage());
			    }
		    	if (endIncrease<maxIndex){
		    		ArrayList <Double> mediumIntArray=new ArrayList<Double>();
		    		int newMaxIndex=(int)(endIncrease+0.1*(maxIndex-endIncrease)+1);
		    		
		    		mediumIntArray.add(intensities[newMaxIndex-1]);
		    		for (int i=newMaxIndex;i<(maxIndex-1+stdXArray.size());i++){
		    			mediumIntArray.add((intensities[i-1]+2*intensities[i]+intensities[i+1])/4);//smoothing the intensities
		    		}
		    		mediumIntArray.add(intensities[maxIndex-1]);
		    		
		    		boolean dockingConfirmation=compareNearLineFit(mediumIntArray,newMaxIndex-1,0.2,dockingSlopeTolerance,0);
		    		/*double slopeMediumIntDocking=lineFitting(mediumIntArray,newMaxIndex);
		    		if(slopeMediumIntDocking>dockingSlopeTolerance || slopeMediumIntDocking<-1.*dockingSlopeTolerance){
		    			return null;
		    		}*/
		    		if (!dockingConfirmation) return null;
				    startFrame=newMaxIndex;
		    	}
		    	double convertedAmplitude=BMaths.roundDouble(intensities[maxIndex]/backgroundAvg, 5);
		    	if (convertedAmplitude>=minIntensityIncrease){
		    		event=new Event(id,lastStdX,lastStdY,firstCenterX,firstCenterY,startFrame,impEndFrame,convertedAmplitude,0,null,intensities,null,increaseData);
		    		return event;
		    	}else{
		    		return null;
		    	}
		    }else{
	    	int isNormalFE=isNormalFE(maxIndex,stdXArray,stdYArray,ampArray);
	    	int isUnknownFE=isUnknownFE(maxIndex,stdXArray,stdYArray,ampArray);
	    	if (isNormalFE>0 || isUnknownFE>0 && increaseRatio>1){
	    		try{
			    	FileWriter fstream = new FileWriter("test.txt",true);
					PrintWriter out = new PrintWriter(fstream);
					out.println("Frames de puff: "+isNormalFE);
					out.close();
			    }catch(Exception e){
					System.err.println("Error: " + e.getMessage());
			    }
	    		CurveFitter checker;
		    	if (isNormalFE==yFitter.length){
		    		checker = new CurveFitter(xFitter, yFitter);
		    	}
		    	else{
		    		framesFitted=isNormalFE+15;
		    		maxLimit=maxIndex+isNormalFE+15;
		    		if (maxLimit>=impEndFrame){
		    	    	maxLimit=impEndFrame-1;
		    	    	framesFitted=impEndFrame-(maxIndex+1);
		    	    	}
		    		double[] xFitterCut=new double[framesFitted];
		    		for(int i=0;i<framesFitted;i++){
		    			xFitterCut[i]=xFitter[i];
		    		}
		    		double[] yFitterCut=new double[framesFitted];
		    		for(int j=0;j<framesFitted;j++){
		    			yFitterCut[j]=yFitter[j];
		    		}
		    		
		    		checker = new CurveFitter(xFitterCut, yFitterCut);
		    	}
		    	if (maxIndex-endIncrease>1){
		    		double teorethicalMidSlope=(intensities[maxIndex]-intensities[endIncrease])/(maxIndex-endIncrease);
		    		
		    		ArrayList<Double> midIntensities=new ArrayList<Double>();
		    		for (int i=endIncrease;i<=maxIndex;i++){
		    			midIntensities.add(intensities[i]);
		    		}
		    		boolean midCorrectBehaviour=compareExactLineFit(midIntensities,endIncrease,0.1,teorethicalMidSlope,0.3);
		    		try{
						FileWriter fstream = new FileWriter("test.txt",true);
						PrintWriter out = new PrintWriter(fstream);
						out.println("Mid test desde: "+endIncrease+" hasta "+maxIndex);
						out.println("Pendiente esperada: "+teorethicalMidSlope+" resultado: "+midCorrectBehaviour);
						out.close();
					}
					catch(Exception e){
						System.err.println("Error: " + e.getMessage());
					}
		    		
		    		if (teorethicalMidSlope<0) return null;//the maximum intensity must be greater than the last point of the maximum increase
		    		if (!midCorrectBehaviour) return null;
		    	}
				//checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+1)+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
				checker.doCustomFit( "y=a*exp(b*(x-"+(maxIndex+1)+"))+"+backgroundAvg, new double[]{1.0,1.0}, false);
				double gof = checker.getFitGoodness();
				double[] expParams=new double[2];
				expParams = checker.getParams();
				double fit[][] = new double[xFitter.length][2];
				for(int i=0;i<xFitter.length;i++)
				{
					fit[i][0] = xFitter[i]*timeFrames;
					fit[i][1] = checker.f(expParams, xFitter[i]);
					//System.out.println("Exp: x->"+fit[0][i]+" y->"+fit[1][i]);
				}
				double tau=-1/expParams[1];
				double precision=4;
				double convertedTau=BMaths.roundDouble(tau,precision);
				double amplitude=expParams[0];
				double halfAmplitude=backgroundAvg+amplitude/2;
				System.out.println("End increase: "+endIncrease);
				double increaseDisplacement= intensities[endIncrease]-increaseRatio*endIncrease;
				double firstInstTMean=(halfAmplitude-increaseDisplacement)/increaseRatio;
				double secondInstTMean=((Math.log(halfAmplitude-backgroundAvg)-Math.log(amplitude))/expParams[1])+(maxIndex+1);
				double convertedAmplitude=BMaths.roundDouble(amplitude/backgroundAvg, precision);
				double []tmeanData=new double[]{firstInstTMean,secondInstTMean,increaseRatio*firstInstTMean+increaseDisplacement,checker.f(expParams, secondInstTMean)};
				try{
					FileWriter fstream = new FileWriter("test.txt",true);
					PrintWriter out = new PrintWriter(fstream);
					out.println("Ajuste desde: "+maxIndex+" hasta "+maxLimit);
					out.println(checker.getResultString());
					out.println("gof "+gof);
					out.close();
				}
				catch(Exception e){
					System.err.println("Error: " + e.getMessage());
				}
				//int startMaxIncrease=maxIncreaseIndexes[0];
			    //int endMaxIncrease=maxIncreaseIndexes[1];
			    //double increaseRatio=-1;
			    //if (maxIndex!=0)increaseRatio=(intensities[endMaxIncrease]-intensities[startMaxIncrease])/(endMaxIncrease-startMaxIncrease);
			    
				
				
				if (convertedAmplitude>=minIntensityIncrease){
					if ((startIncrease==0 && endIncrease==0 && gof>=0.85) || (maxIndex!=0 && gof>=0.75 && increaseRatio>1)){// && framesFitted<=tau){// && (maxIndex==0 || increaseRatio>10)){
				    	System.out.println("Esto hipotéticamente es un evento de fusión :), tau: "+tau+" amp: "+amplitude);
				    	
				    	event=new Event(id,lastStdX,lastStdY,firstCenterX,firstCenterY,startFrame,startFrame+(int)(tau+0.5),convertedAmplitude,convertedTau,tmeanData,intensities,fit,increaseData);
				    	
					    //this code was originally on line 1220
					    int r1=(int)(std1+0.5);
					    int r2=(int)(std2+0.5);
					    double [] intensities2=new double[impEndFrame];
					    double [] vesicleIntensities=new double[impEndFrame];
					    int x2_1=centerX-r2;
					    int x2_2=centerX+r2;
					    int y2_1=centerY-r2;
					    int y2_2=centerY+r2;
					    double r2cuad=r2*r2;
					    double r1cuad=r1*r1;
					    double xaux,yaux;
					    double sumcuad;
					    int npixels;
					    int vesiclenpix;
					    for (int f=1;f<impEndFrame;f++){
					    	npixels=0;
					    	vesiclenpix=0;
					    	ip=is.getProcessor(f);
						    for (int xi=x2_1;xi<=x2_2;xi++){
						    	for (int yi=y2_1;yi<=y2_2;yi++){
						    		xaux=xi-centerX;
						    		yaux=yi-centerY;
						    		sumcuad=(xaux*xaux)+(yaux*yaux);
						    		if (sumcuad>r1cuad && sumcuad<r2cuad ){
						    			intensities2[f-1]=intensities2[f-1]+ip.getPixel(xi, yi);
						    			npixels++;
						    			//System.out.println("Pixel2 "+"xi: "+xi+" yi: "+yi);
						    		}else{
						    			if (sumcuad<=r1cuad){
						    				vesicleIntensities[f-1]=vesicleIntensities[f-1]+ip.getPixel(xi, yi);
						    				vesiclenpix++;
						    			}
						    		}
						    	}
						    }
						    intensities2[f-1]=intensities2[f-1]/npixels;
						    vesicleIntensities[f-1]=vesicleIntensities[f-1]/vesiclenpix;
					    }
					    //
					    
					    PlotWindow.noGridLines = false;
					    //Plot plot = new Plot("Trajectory: "+traj.getId(),"X Axis","Y Axis",completeX,completeY);
						Plot plot = new Plot("Traj: "+id+" prob: "+(avgR2*gof)+" x: "+centerX+" y: "+centerY+" pendiente inc: "+increaseRatio+"vesDisp: "+vesicleDisplacement+" tolerated "+displacementAccepted,"X Axis","Y Axis",x,intensities);
					    //Plot plot = new Plot(" x: "+centerX+" y: "+centerY,"X Axis","Y Axis",x,intensities);
					    plot.setLimits(0, impEndFrame, 0, 255);
					    plot.setLineWidth(1);
					    plot.setColor(Color.red);
					    //plot.addPoints(xFitter,yFit,PlotWindow.LINE);
					    plot.drawLine(1, backgroundAvg, impEndFrame, backgroundAvg);
					    plot.show();
					    //End plotting code
				    }else{
				    	System.out.println("NO ES EVENTO DE FUSION NORMAL");
				    }
				}else{
					try{
						FileWriter fstream = new FileWriter("test.txt",true);
						PrintWriter out = new PrintWriter(fstream);
						out.println(checker.getResultString());
						out.println("No pasa el umbral necesario para ser FE");
						out.close();
					}catch(Exception e){
						System.err.println("Error: " + e.getMessage());
					}
				}
			}
	    }    
	    }  
		    
	
	return event;
	}	
	//}
	
	public void updateAllCenterIntensities(double[] intensities, int xEvent, int yEvent){
		ImageStack is=imp.getImageStack();
		ImageProcessor ip;
		for (int i=1;i<=impEndFrame;i++){
			ip=is.getProcessor(i);
			intensities[i-1]=(ip.getPixel(xEvent, yEvent)+ip.getPixel(xEvent-1, yEvent)+ip.getPixel(xEvent+1,yEvent)+ip.getPixel(xEvent,yEvent-1)+ip.getPixel(xEvent, yEvent+1))/5;
		}
	}
	
	public boolean isDocking(double[] x, double[] intensities, double maxIndex,int framesFitted, double slopeStdX, double slopeStdY){
		int length=intensities.length;
		double maxInt=intensities[0];
		if (maxInt>maxIntThreshold){
			if (framesFitted>=5 && (slopeStdX >=-0.15 && slopeStdX<=0.15) && (slopeStdY>=-0.15 && slopeStdY<=0.15)){
				double[] smoothIntensities=new double[length];
				for (int k=1;k<length-1;k++){
					smoothIntensities[k]=(intensities[k-1]+2*intensities[k]+intensities[k+1])/4;
				}
				smoothIntensities[0]=smoothIntensities[1];
				smoothIntensities[length-1]=smoothIntensities[length-2];
				CurveFitter checker = new CurveFitter(x, smoothIntensities);
				checker.doCustomFit( "y=a*x+b", new double[]{0.5,maxInt}, false);
				
				double gof = checker.getFitGoodness();
				double r2= checker.getRSquared();
				try{
					FileWriter fstream = new FileWriter("test.txt",true);
					PrintWriter out = new PrintWriter(fstream);
					out.println(checker.getResultString());
					out.println("gof "+gof+ " max int threshold: "+ maxIntThreshold);
					out.close();
				}catch(Exception e){
					System.err.println("Error: " + e.getMessage());
				}
				double a=checker.getParams()[0];
				//if (gof>0.1 && (a<0.5 && a>-0.5))return true;
				if (a<dockingSlopeTolerance && a>-1.*dockingSlopeTolerance && r2>0.0) return true;
				return false;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public int isNormalFE(int maxIndex,ArrayList<Double> stdXArray,ArrayList<Double> stdYArray,ArrayList<Double> ampArray){
		int n1=stdXArray.size();
		int n2=stdYArray.size();
		if (n1!=n2) return 0;
		double lastGaussAmp=ampArray.get(0);
		double lastStdX=stdXArray.get(0);
		double lastStdY=stdYArray.get(0);
		int endPuffin=0;
		boolean puffinBehaviour=false;
		if (lastGaussAmp<maxIntThreshold){//Detects if the vesicle dont reach (theoretically) the minimum threshold to be classified like a FE
			return 0;
		}
		for (int i=1;i<n1;i++){
			double stdx=stdXArray.get(i);
			double stdy=stdYArray.get(i);
			double amp=ampArray.get(i);
			/*if (i==1 && amp<maxIntThreshold){
				return 0;
			}*/
			if((lastGaussAmp-amp)>-(lastGaussAmp*0.1) && (stdx-lastStdX)>-(lastStdX*0.25) && (stdy-lastStdY)>-(lastStdY*0.25)){
				if (!puffinBehaviour)puffinBehaviour=true;
				lastStdX=stdx;
				lastStdY=stdy;
				lastGaussAmp=amp;
				endPuffin++;
			}
			else{
				if (!puffinBehaviour) return 0;
				break;
			}
		}
		double slopeStdXFit=lineFitting(new ArrayList<Double>(stdXArray.subList(0, endPuffin-1)),maxIndex+1);
	    double slopeStdYFit=lineFitting(new ArrayList<Double>(stdYArray.subList(0, endPuffin-1)),maxIndex+1);
	    try{
			FileWriter fstream = new FileWriter("test.txt",true);
			PrintWriter out = new PrintWriter(fstream);
			out.println("Pendiente sigmax: "+slopeStdXFit+" Pendiente sigmay: "+slopeStdYFit);
			out.close();
		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	    if (slopeStdXFit>0 || slopeStdYFit>0) return endPuffin;
		return 0;
	}
	
	public int isUnknownFE(int maxIndex,ArrayList<Double> stdXArray,ArrayList<Double> stdYArray,ArrayList<Double> ampArray){
		int n1=stdXArray.size();
		int n2=stdYArray.size();
		if (n1!=n2) return 0;
		double lastGaussAmp=ampArray.get(0);
		double lastStdX=stdXArray.get(0);
		double lastStdY=stdYArray.get(0);
		int endPuffin=0;
		boolean puffinBehaviour=false;
		if (lastGaussAmp<maxIntThreshold){//Detects if the vesicle dont reach (theoretically) the minimum threshold to be classified like a FE
			return 0;
		}
		for (int i=1;i<n1;i++){
			double stdx=stdXArray.get(i);
			double stdy=stdYArray.get(i);
			double amp=ampArray.get(i);
			/*if (i==1 && amp<maxIntThreshold){
				return 0;
			}*/
			if((lastGaussAmp-amp)>-(lastGaussAmp*0.1) && (stdx-lastStdX)<(lastStdX*0.25) && (stdy-lastStdY)<(lastStdY*0.25)){
				if (!puffinBehaviour)puffinBehaviour=true;
				lastStdX=stdx;
				lastStdY=stdy;
				lastGaussAmp=amp;
				endPuffin++;
			}
			else{
				if (!puffinBehaviour) return 0;
				break;
			}
		}
		double slopeStdXFit=lineFitting(new ArrayList<Double>(stdXArray.subList(0, endPuffin-1)),maxIndex+1);
	    double slopeStdYFit=lineFitting(new ArrayList<Double>(stdYArray.subList(0, endPuffin-1)),maxIndex+1);
	    if (slopeStdXFit<0 && slopeStdYFit<0) return endPuffin;
		return 0;
	}
}
