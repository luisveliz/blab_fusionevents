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

public class EventEvaluator{
	
	private ImagePlus imp;
	private int limitRadius;
	private int deltaWindow;
	private int areaRadius;
	private int fitPatchSize=3;
	
	private int impEndFrame; //Actual length of the movie.
	
	private StackWindow stack_window;
	private PreviewCanvas pc;
	
	public EventEvaluator(ImagePlus imp, int limitRadius, int deltaWindow,int areaRadius){
		this.imp=imp;
		this.limitRadius=limitRadius;
		this.deltaWindow=deltaWindow;
		this.areaRadius=areaRadius;
		
		
		//Checking which is the biggest one, NSlices or NFrames.
		if(imp.getNFrames() > 1){
			impEndFrame = imp.getNFrames();
		}else if(imp.getNSlices() > 1){
			impEndFrame = imp.getNSlices();
		}else{
			System.out.println("No es una película!!");
		}
		
	
		/*/Prueba graficación
		double magnification = imp.getWindow().getCanvas().getMagnification();
		ImageStack stack=imp.getStack();
		Frame preview_frame = new Frame(stack.getProcessor(50), 50);
		pc = new PreviewCanvas(imp, preview_frame, magnification, ParticleDetector.radius);		
		// display the image and canvas in a stackWindowtu 
		stack_window = new StackWindow(imp, pc);
		//fin prueba graficación*/
	}
	
	public int getImpEndFrame(){
		return impEndFrame;
	}
	
	public Event evaluate(Trajectory traj){
		int max=0;
		int maxIndex=-1;
		
		
		/*if (traj.getRadio() > limitRadius){
			return null;
		}*/
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
		}*/
		
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
	}
	
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
		
		//Código que ajusta el centro de la vesicula en el punto de la máxima intensidad
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
		
		double background=getAvgEventBackground(intensities,startFrame,endFrame);
		
		int endAnalysis=maxIndex+deltaWindow;
		if (endAnalysis>=impEndFrame) endAnalysis=impEndFrame-1;
		
		Event ev=verifyExponentialImproved(traj,xCenter,yCenter,maxIndex,endAnalysis,startFrame,intensities,background);
		System.out.println("Termine verificacion de la exponencial");
		return ev;
	}
	
	public Event verifyExponentialImproved(Trajectory traj, int centerX, int centerY, int startPos,int endPos, int startFrame, double[] intensities, double background){
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
			checker.doCustomFit( "y=a*exp(b*(x-"+(startPos+1)+"))+"+String.valueOf(background), new double[]{1.0,1.0}, false);
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
						int testLimit=startPos+3;
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
						    	gaussAmp=aguess[5];
						    }
						    else{
						    	eventGauss=false;
						    	System.out.println("NO ES EVENTO");
						    }
						    System.out.println("Frame aux: "+aux+" Sigmax: "+aguess[0]+" Sigmay: "+aguess[1]+" x0: "+(int)(aguess[2]+0.5)+" y0: "+(int)(aguess[3]+0.5)+" b: "+aguess[4]+" amp: "+aguess[5]);
						    out.println("Traj "+traj.getId()+"Frame aux: "+aux+" Sigmax: "+aguess[0]+" Sigmay: "+aguess[1]+" x0: "+(int)(aguess[2]+0.5)+" y0: "+(int)(aguess[3]+0.5)+" b: "+aguess[4]+" amp: "+aguess[5]);
						    out.println("Chi square: "+LMauthor.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
						    out.println("R2: "+LMauthor.rSquared(patchXY, aguess, intensitiesPatch, s, f));
						    centerX=(int)(aguess[2]+0.5);
						    centerY=(int)(aguess[3]+0.5);
						    //amp=(int)aguess[5];
						}
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
				    
					Event eventTested=new Event(traj.getId(),1.387*aguess[0],1.387*aguess[1],(int)(aguess[2]+0.5),(int)(aguess[3]+0.5),startPos+1,endPos+1,amplitude,tau,0,intensities);
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
		return background/nFrames;
	}
	
	int getMaxIntensityIndex(double[] intensities, int startFrame, int endFrame){
		double maxIntensity=-1.0;
		int maxIndex=-1;
		for (int i=startFrame-1;i<endFrame;i++){
			if (intensities[i]>=maxIntensity){
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
	
	public int getMaxIncreaseIndex(double [] intensities){
		double currentMaxIncrease=0;
		double maxIncrease=0;
		int maxIndex=-1;
		double delta=-1;
		for (int i=1;i<intensities.length;i++){
			delta=intensities[i]-intensities[i-1];
			if (delta>0){
				currentMaxIncrease=currentMaxIncrease+delta;
				if (currentMaxIncrease>maxIncrease){
					maxIndex=i;
					maxIncrease=currentMaxIncrease;
				}
			}else{
				currentMaxIncrease=0;
			}
		}
		return maxIndex;
	}
	
	public void evaluateSelectedArea(int x1, int y1, int x2, int y2){
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
		int centerX=0;
		int centerY=0;
		int aux=maxIndex+1;
		double lastGaussAmp=300.;
		while(testGauss){
		double []intensitiesPatch= new double[areaSize];
		c=0;
		ip=is.getProcessor(aux);
		for (int xi=x1;xi<=x2;xi++){
			for (int yi=y1;yi<=y2;yi++){
				intensitiesPatch[c]=ip.getPixel(xi, yi);
				c++;
			}
		}
		System.out.println("Old x center: "+xCenter+" Old y center: "+yCenter);
		for (int h=0;h<intensitiesPatch.length;h++) System.out.print(intensitiesPatch[h]+",");
		double background=getAvgEventBackground(intensities,maxIndex-3,maxIndex+3);
		int amp=ip.getPixel(xCenter,yCenter);
	    LMfunc f=new Gauss2dIsotropic(patchXY,intensitiesPatch,amp-background,xCenter,yCenter,1.0,background);
	    double[] aguess=new double[5];
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
	      LMIsotropic2dGaussian.solve( patchXY, aguess, intensitiesPatch, s, vary, f, 0.001, 0.01, 100, 2,limits);
	    }
	    catch(Exception ex) {
	      System.err.println("Exception caught: " + ex.getMessage());
	      System.exit(1);
	    }
	    double r2=LMIsotropic2dGaussian.rSquared(patchXY, aguess, intensitiesPatch, s, f);
	    System.out.println("R2: "+r2);
	    System.out.println("Desvest: "+aguess[0]+"Centro x: "+aguess[1]+" Centro y: "+aguess[2]+" amplitude: "+aguess[4]);
	    if (aux==maxIndex+1){
		    centerX=(int)(aguess[1]+0.5);
		    centerY=(int)(aguess[2]+0.5);
		    updateAllEventIntensities(intensities,centerX,centerY);
	    }
	    try{
	    	FileWriter fstream = new FileWriter("test.txt",true);
			PrintWriter out = new PrintWriter(fstream);
	    	out.println("Frame aux: "+aux+" Sigma: "+aguess[0]+" x0: "+centerX+" y0: "+centerY+" b: "+aguess[3]+" amp: "+aguess[4]);
		    out.println("Chi square: "+LMIsotropic2dGaussian.chiSquared(patchXY, aguess, intensitiesPatch, s, f));
		    out.println("R2: "+r2);
		    out.close();
	    }catch(Exception e){
			System.err.println("Error: " + e.getMessage());
	    }
	    /*if (aguess[4]<lastGaussAmp){
	    	lastGaussAmp=aguess[4];
	    	aux++;*/
	    if (r2>0.4){
	    	aux++;
	    }else{
	    	testGauss=false;
	    }
		}
	    int maxIncreaseIndex=getMaxIncreaseIndex(intensities);
	    //Plotting intensities code
	    double[] x=new double[impEndFrame];
	    for (int i=1;i<=impEndFrame;i++){
	    	x[i-1]=i;
	    }
	    PlotWindow.noGridLines = false;
	    //Plot plot = new Plot("Trajectory: "+traj.getId(),"X Axis","Y Axis",completeX,completeY);
		Plot plot = new Plot(" x: "+centerX+" y: "+centerY+" max increase: "+maxIncreaseIndex,"X Axis","Y Axis",x,intensities);
	    plot.setLimits(0, impEndFrame, -255, 255);
	    plot.setLineWidth(1);
	    plot.setColor(Color.red);
	    //plot.drawLine(1, aguess[4], impEndFrame, aguess[4]);
	    plot.show();
	    //End plotting code
		
		
	}
}
