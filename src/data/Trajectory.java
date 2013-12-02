package data;

import ij.IJ;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import typeOfMotion.Evaluator;
import typeOfMotion.Fitter;
import typeOfMotion.TypeOfMotionAnalysis;
import bTools.BMaths;


public class Trajectory 
{
	Metrics metrics;
	
	int id;									// serial number of this trajectory (for report and display). 0 for temporary trajs.
	String sid;
	private boolean usar;
	private int usability;
	
	int movieFrame;		 					// the first trajectorie frame, related to the movie.
	private Particle[] particles;			// N
	private Particle[] particlesPro;		// F, incluye partículas fantasmas (-1,-1) para los gaps
	
	private int[] frames;             		// N, the frames relative to the trajectory, not to the movie, always begin with 1.
	private int[] framesPro;				// F, contains 0's in the gaps.
	
	private double[] time;					// N, the time relative to the trajectory, always begin with 0;
	private double[] timePro;				// F, the time relative to the trajectory, always begin with 0, includes gaps;

	
	
	//TODO fix this: length is not necessary beacuse length = last trajectoryFrame
	private int length;						// number of frames this trajectory spans on last frame	
	
	int numOfGaps;
	//TODO probably this is not necessary
	//ArrayList<int[]> gaps = new ArrayList<int[]>(); // holds arrays (int[]) of size 2 that holds   
													// 2 indexs of particles in the existing_particles.
													// These particles are the start and end points of a gap 
													// in this trajectory
	
	
	//Geometric
	private double centroide_X;
	private double centroide_Y;
	private double radio;
	
	private double[] dx;
	private double[] dy;
	private double[] displacements;					// N-1
	private double totalDistancePath;
	private double[] sq_disp;						// N-1
	private double[] ssd;							// N
	
	private double[] velocity;						// N-1	
	private double[][] vectors;						// N-1
	private double[] angles;						// N-1
	private double[] angleProbability;
	private double[] angleChanges;
	
	//HMM
	public int[] simState;						//state 0 or 1 //only for simulated trajs
	public int[] guessState;
	protected boolean hmmAnalyzed;
	
	//MSD
	private double[] timeLag;
	private double[] msd;
	private boolean msdOK = false;
	private double[] msdSD;
	private double[] msdNPoints;
	private double[] msdVar;
	
	//Diffusion
//	private double microD24;
	private double microD12;
//	private double microD13;
//	private double microD14;
//	private double microD15;
	private double microD;
	private double macroD;
//	private double modelD;
	
	
	
	//Models
	protected boolean analyzed;
	protected int trajType = TypeOfMotionAnalysis.NOT_DEFINED;
	protected double modelGOF = 0;
	protected double[] paramsNormal= new double[2];//D y 
	protected double[] paramsAnomalous= new double[2];//D y alfa
	protected double[] paramsDirected= new double[2];//D y V
	protected double[] paramsCorralled= new double[2];//D y L

	
	private ArrayList<Subtrajectory> subtrajs;
		
	
	//Visualization
	private Color color;				// the display color of this Trajectory in TrajectoryStackWindow.
	boolean toDisplay = true;			// flag for display filter
	
	
	private int userType; 
	
	
	//Segmentation Algorithm
	double[] dif;
	double[] dev;
	double[] asym;
	
	
	public Trajectory(int id, Particle[] particles, int[] frames, Metrics metrics)
	{
		this.metrics = metrics;
		this.id = id;
		sid = String.valueOf(id); 
		this.particles = particles;
		this.frames = frames;
		length = frames[frames.length-1];
		usability = Math.round((((float)frames.length)/length)*100);
		
//		modelD=0;
		dif = new double[0];
		dev = new double[0];
		asym = new double[0];
		
		analyzed = false;
		usar = true;
		userType = 0;
		
		simState = new int[]{1,2};
		guessState = new int[]{1,2};
		
		//TODO recalcular al actualizar las metricas
		this.time = metrics.framesToTime(frames);
//		System.out.println("calculateParticlePro");
		calculateParticlePro();
		
//		System.out.println("calculateCentroide");
		calculateCentroide();
//		System.out.println("calculateRadio");
		calculateRadio();
//		System.out.println("calculateDisplacement");
		calculateDisplacements();
//		System.out.println("calculateVelocity");
		calculateVelocity();
//		System.out.println("calculateVectors");
		calculateVectors();
//		System.out.println("calculateAngles");
		calculateAngles();
		
//		System.out.println("calculateTimeLag");
		calculateTimeLag();
//		System.out.println("calculateMSD");
		calculateMSD();
//		System.out.println("calculateMicroD");
		microD12 = calculateMicroD(1,2);
//		System.out.println("calculateMacroD");
		calculateMacroD();
		System.out.println("calculateVarianza");
		calculateMSDVariance();
		System.out.println("calculateMicroDW");
		calculateMicroDW();
		subtrajs = new ArrayList<Subtrajectory>();
		
	}
	public void updateMetricData()
	{
		this.time = metrics.framesToTime(frames);
		calculateRadio();
		calculateDisplacements();
		calculateVelocity();
		calculateVectors();
		calculateAngles();
		calculateTimeLag();
		calculateMSD();
		microD12 = calculateMicroD(1,2);
		calculateMacroD();
		calculateMSDVariance();
		calculateMicroDW();
	}
	
	private void calculateParticlePro()
	{
		int dist;
		framesPro = new int[length];
		framesPro[0]=1;			
		timePro = new double[length];
		timePro[0] = 0;
		particlesPro = new Particle[length];
		particlesPro[0] = particles[0];
		//System.out.println("agrega particula pro ok"+1+" frame:"+trajectoryFramesPro[0]);
		int frameAnterior = 1;
		int count = 1;
		
		//System.out.println("calculate frames particlesPro.length: "+particlesPro.length);
		
		for(int i=1;i<frames.length;i++)
		{//TODO pk valor absoluto?
			//dist = (int) Math.sqrt(Math.pow(particles[i].getMovieFrame()-particles[i-1].getMovieFrame(),2));
			dist = frames[i]-frames[i-1];
//			System.out.println("dist:"+dist);
			if(dist>1)
			{//TODO REVISAR!!!!!
				for(int j=1;j<dist;j++)
				{
					//particlesPro[count] = new Particle(0,0,0);
					particlesPro[count] = new Particle(-1,-1);
					framesPro[count]=0;
					timePro[count]= metrics.getFrameStep()*count;
					count++;
//					System.out.println("agregada particula fantasma:"+(count));
				}
				numOfGaps++;
			}
//			System.out.println("1");
			particlesPro[count]=particles[i];
//			System.out.println("2");
			framesPro[count]=frameAnterior+dist;
			timePro[count]= metrics.getFrameStep()*count;
//			System.out.println("3");
			frameAnterior=framesPro[count];
//			System.out.println("4");
			//System.out.println("agrega particula pro ok"+(count+1)+" frame:"+trajectoryFramesPro[count]);
			count++;
		}
	}
	
	
	
	private void calculateCentroide()
	{
		double auxX=0;
		double auxY=0;
		for(int i=0; i<this.getParticles().length; i++)
		{
			//System.out.println("(x,y)"+i+": ("+this.getParticles()[i].getX()+","+this.getParticles()[i].getY()+")");
			auxX+=this.getParticles()[i].getX();
			auxY+=this.getParticles()[i].getY();
		}
//		System.out.println("(auxX,auxY):"+auxX+","+auxY);
		auxX/=this.getParticles().length;
		auxY/=this.getParticles().length;
		
		this.centroide_X = auxX;
		this.centroide_Y = auxY;		
//		System.out.println("centroide(X,Y):"+centroide_X+","+centroide_Y);
	}
	public void calculateRadio()
	{
		radio = 0;
		double radioAux = 0;
		
		for(int i=0; i<this.getParticles().length; i++)
		{
			radioAux = BMaths.dist(centroide_X, centroide_Y, this.getParticles()[i].getX(), this.getParticles()[i].getY());
			//System.out.println("Distancias del centroide a los puntos:"+radioAux);
			if(radioAux>radio)
				radio = radioAux;
		}
		radio*=metrics.getDistanceFactor();
	}
	//************with metrics****************************************************/
	//detected displacements, gaps not included

	private void calculateDisplacements()
	{
		int n = particles.length;

		dx = new double[n-1];
		dy = new double[n-1];
		displacements = new double[n-1];
		sq_disp = new double[n-1];
		ssd = new double[n];
		ssd[0] = 0;
		
		totalDistancePath=0;
		
//		System.out.println("N:"+n+" displacements.length:"+displacements.length+ " F:"+f+" displacementsPro.length:"+displacementsPro.length);
		for(int i=0;i<n-1;i++)
		{
			dx[i] = (particles[i+1].getX()-particles[i].getX())*metrics.getDistanceFactor();
			dy[i] = (particles[i+1].getY()-particles[i].getY())*metrics.getDistanceFactor();
			sq_disp[i] = BMaths.sqr(dx[i])+BMaths.sqr(dy[i]);
			displacements[i] = Math.sqrt(sq_disp[i]);
			totalDistancePath+=displacements[i];
			ssd[i+1]=ssd[i]+sq_disp[i];
		}
	}	
	private void calculateTimeLag()
	{
		timeLag = new double[getLength()-1];
		for(int i=0;i<timeLag.length;i++)
		{
			timeLag[i]=(i+1)*metrics.getFrameStep();
//			IJ.log("timeLag["+i+"]"+timeLag[i]);
		}
	}
	private void calculateMSD()
	{
		int N = getLength();
		msd = new double[N-1];
		msdSD = new double[N-1];
		msdNPoints = new double[N-1];
//		msd[0] = 0.0;
//		msdSD[0] = 0.0;
//		msdNPoints[0] = 0;
		Vector<Double> sdAux = new Vector<Double>();
		double aux = 0.0;										
		int count=0;//cuenta los puntos a promediar
		
		for(int dt=1; dt<N; dt++)//n:time lag, N:trajectory length
		{
//			IJ.log("n:"+n);
			aux=0;
			count=0;
			sdAux.clear();
			//In Jin and Verkman j=1; j<=N-1-n, this is the same.
			for(int j=0; j<N-dt; j++)
			{				
				if(framesPro[j+dt]!=0 && framesPro[j]!=0)
				{
					sdAux.add(Math.pow((particlesPro[j+dt].x-particlesPro[j].x)*metrics.getDistanceFactor(),2)+
							  Math.pow((particlesPro[j+dt].y-particlesPro[j].y)*metrics.getDistanceFactor(),2)); 
					aux += sdAux.get(sdAux.size()-1);
					count++;			
				}
			}
			msd[dt-1] = aux/count;
			msdNPoints[dt-1] = count;
//			msd[n] *= Math.pow(Metrics.distanceFactor,2);
			
			for(int k=0;k<sdAux.size();k++)
				msdSD[dt-1]+= Math.pow(sdAux.get(k)-msd[dt-1],2);
			msdSD[dt-1]= Math.sqrt(msdSD[dt-1]/sdAux.size());
			
//			IJ.log("msd["+n+"]:"+msd[n]);
//			IJ.log("msdSD["+n+"]:"+msdSD[n]+" count:"+count);
		}		
		msdOK=true;
		for(int i=0;i<msd.length;i++)
		{
			if(Double.isNaN(msd[i]))
			{
				msdOK = false;
				usar = false;
			}
		}
	}

	private void calculateMSDVariance()
	{
		int N = getLength()-1;
		double D = microD12;
		msdVar = new double[N];
		for(int n=1; n<=N; n++)//n:time lag, N:trajectory length
			msdVar[n-1]=Math.pow(4*D*n*metrics.getFrameStep(),2)*(2*n*n+1)/(3*n*(N-n+1));
	}
	private double calculateMicroD(int startMSDPoint, int finalMSDPoint)
	{
		double[] normalFit = Fitter.normalFitIJ(BMaths.getSubArray(timeLag,startMSDPoint-1,finalMSDPoint-1), BMaths.getSubArray(msd,startMSDPoint-1,finalMSDPoint-1), false);
		return normalFit[0];
	}
	private void calculateMicroDW()
	{
		double[] normalFit = Fitter.normalFitLM(BMaths.getSubArray(timeLag,0,3), BMaths.getSubArray(msd,0,3), false,true, BMaths.getSubArray(msdVar,0,3));
		microD = normalFit[0];
	}
	
 	private void calculateMacroD()
	{
		double[] normalFit = Fitter.normalFitIJ(timeLag, msd, false);
		macroD = normalFit[0];
	}
	private void calculateVelocity()
	{
		int n = particles.length;
		velocity = new double[n-1];
		for(int i=0; i<n-1; i++)
			velocity[i] = displacements[i]/(time[i+1]-time[i]);				
	}
	private void calculateVectors()
	{
		int n = particles.length;
		vectors = new double[n-1][2];
//		vectors[0] = new double[]{0,0};
		double norma;

//		for(int i=1;i<n;i++)
		for(int i=0;i<n-1;i++)
		{
			//TODO x por y??!!!
			vectors[i][0] = particles[i+1].y-particles[i].y;
			vectors[i][1] = particles[i+1].x-particles[i].x;	
			norma = Math.sqrt(vectors[i][0]*vectors[i][0]+vectors[i][1]*vectors[i][1]);
			if(norma!=0)
			{
				vectors[i][0] /=norma;
				vectors[i][1] /=norma;
			}
		}
	}
	private void calculateAngles()
	{
//		System.out.println("calculate angles");
		int n = particles.length;
		
		angles = new double[n-1];
//		angles[0] = 0;
//		for(int i=1;i<n;i++)
		for(int i=0;i<n-1;i++)
		{
			if(vectors[i][0]>=0)
			{
				if(vectors[i][1]>=0)
					angles[i] = Math.acos(vectors[i][1])*180/Math.PI+270;
				else
					angles[i] = Math.acos(vectors[i][1])*180/Math.PI-90;
			}
			else 
				if(vectors[i][1]>=0)
					angles[i] = 270-Math.acos(vectors[i][1])*180/Math.PI;
				else
					angles[i] = 270-Math.acos(vectors[i][1])*180/Math.PI;
			
			if(angles[i]==360)angles[i]=0;
		}
		
//		System.out.println("calculate anglesChanges");
		angleChanges = new double[n-2];
//		angleChanges[0] = 0;
//		angleChanges[n-1] = 0;
//		for(int i=1;i<n-1;i++)
		for(int i=0;i<n-2;i++)
			angleChanges[i] = Math.acos(vectors[i][0]*vectors[i+1][0]+vectors[i][1]*vectors[i+1][1])*180/Math.PI;
		
//		System.out.println("calculate angleProbability");
		angleProbability = new double[8];
		for(int i=0;i<angleProbability.length;i++)
			angleProbability[i]=0;

		System.out.println("N:"+n+" angles.length:"+angles.length+" displacements.length:"+displacements.length+"total displacement"+ totalDistancePath);
		int indice;
		for(int i=0;i<n-1;i++)
		{
			indice = (int) Math.round(((angles[i]-22.5)/45));
			System.out.println("i:"+i+" indice:"+indice+" displacement:"+displacements[i]);
			angleProbability[indice]+=displacements[i];
		}
		
		for(int i=0;i<angleProbability.length;i++)
		{
			System.out.println("angle prob i:"+angleProbability[i]);
			angleProbability[i]/=totalDistancePath;
			System.out.println("angle prob i /total:"+angleProbability[i]);
		}
		
	}

	public void setTypeAndParams(int tipo, double[] params, double gof)
	{
		this.trajType = tipo;
		this.modelGOF = gof;
		this.analyzed = true;
		switch(trajType)
		{
			case TypeOfMotionAnalysis.NOT_DEFINED:
				this.analyzed = false;
				break;
			case TypeOfMotionAnalysis.NORMAL:
				this.paramsNormal = params;
				if(gof==0)modelGOF = BMaths.getFitGoodness(msd, Evaluator.funcNormal(timeLag, params[0]), 1);
				break;
			case TypeOfMotionAnalysis.ANOMALOUS:
				this.paramsAnomalous = params;
				if(gof==0)modelGOF = BMaths.getFitGoodness(msd, Evaluator.funcAnomalous(timeLag, params[0], params[1]), 2);
				break;			
			case TypeOfMotionAnalysis.CORRALLED:
				this.paramsCorralled = params;
				if(gof==0)modelGOF = BMaths.getFitGoodness(msd, Evaluator.funcCorralled(timeLag, params[0], params[1]), 2);
				break;
			case TypeOfMotionAnalysis.DIRECTED:
				this.paramsDirected = params;
				if(gof==0)modelGOF = BMaths.getFitGoodness(msd, Evaluator.funcDirected(timeLag, params[0], params[1]), 2);
				break;
			default:System.err.println("defaulttt in setTypePArams!!!");break;				
		}
		
	}
	public double[] getTypeParams()
	{
		switch(trajType)
		{
			case TypeOfMotionAnalysis.CORRALLED:return paramsCorralled;
			case TypeOfMotionAnalysis.ANOMALOUS:return paramsAnomalous;
			case TypeOfMotionAnalysis.NORMAL:return paramsNormal;
			case TypeOfMotionAnalysis.DIRECTED:return paramsDirected;
			default:IJ.log("getTypeParams() defaulttt!!!!!!!!");return new double[]{0,0};
		}
	}
	
	public String getTrajTypeToString()
	{
		/*if(this.isMultipe())
			return "Multiple";			
		else
		{*/
			switch(this.trajType)
			{
				case TypeOfMotionAnalysis.NOT_DEFINED:return "Not Defined";			
				case TypeOfMotionAnalysis.CORRALLED:return "Corralled";
				case TypeOfMotionAnalysis.ANOMALOUS:return "Anomalous";
				case TypeOfMotionAnalysis.NORMAL:return "Normal";
				case TypeOfMotionAnalysis.DIRECTED:return "Directed";
				default:
					IJ.showMessage("default trajtypeToString()!!!!");
					return "Not defined";
			}
//		}
	}
	public double[][] get3dTrajectory()
	{
		int n = particles.length;
		double[][] trayectory = new double[3][n];
		
		for(int i=0; i<n; i++)
		{
			trayectory[0][i] = particles[i].x;
			trayectory[1][i] = particles[i].y;
			trayectory[2][i] = particles[i].z;
		}			
		return trayectory;
	}
	
	public double[] getIntensidades()
	{
		int N = particles.length;
		double[] intensidades = new double[N];
		
		for(int i=0; i<N; i++)
		{
			intensidades[i] = particles[i].brightness;				
		}			
		return intensidades;		
	}
	
	public int getNumOfRealParticlesBetween(int initialFrame, int finalFrame)
	{
		ArrayList<Particle> subParticles = new ArrayList<Particle>();
		for(int i=0;i<particles.length;i++)
		{
			if(initialFrame<=frames[i] && frames[i]<=finalFrame)
				subParticles.add(this.particles[i]);
		}
		return subParticles.size();
	}
	public Subtrajectory getTempSubtraj(int initialFrame, int finalFrame)
	{
		ArrayList<Particle> subParticles = new ArrayList<Particle>();
		Vector<Integer> vectorFrames = new Vector<Integer>();
		
		for(int i=0;i<particles.length;i++)
		{
			if(initialFrame<=frames[i] && frames[i]<=finalFrame)
			{
				subParticles.add(this.particles[i]);
				vectorFrames.add(frames[i]-initialFrame+1);
			}
		}
		int[] frames = new int[subParticles.size()];
		Particle[] particles = new Particle[subParticles.size()]; 
			
		for(int i=0;i<particles.length;i++)
		{
			frames[i] = vectorFrames.get(i);
			particles[i] = subParticles.get(i);
		}
		Subtrajectory subtraj = new Subtrajectory(0, particles, frames, metrics);
		return subtraj;
	}
	public void addSubtrajectory(int initialFrame, int finalFrame)
	{
		subtrajs.add(getTempSubtraj(initialFrame, finalFrame));
		subtrajs.get(subtrajs.size()-1).setSubIndex(subtrajs.size());
		subtrajs.get(subtrajs.size()-1).setSid(id+"."+subtrajs.size());
		subtrajs.get(subtrajs.size()-1).setBeginFrame(initialFrame);
		subtrajs.get(subtrajs.size()-1).setEndFrame(finalFrame);
		this.trajType = TypeOfMotionAnalysis.NOT_DEFINED;
	}
	public void resetSubtrajs()
	{
		subtrajs.clear();
	}
	public void setZ(int[] z)
	{
		//TODO		
	}
	public int getLength()
	{
		return this.length;
	}
	public double getTimeLength()
	{
		return time[time.length-1];
	}
	public int getId() {
		return id;
	}
	public int[] getFrames() {
		return frames;
	}
	public double[] getFramesd()
	{
		double[] framesd = new double[frames.length];
		for(int i=0;i<framesd.length;i++)
		{
			framesd[i] = (double)frames[i];
		}
		return framesd;
	}
	public int getFrame(int index)
	{
		return frames[index];
	}
	public double[] getDoubleFrames()
	{
		double[] floats = new double[frames.length];
		for(int i=0;i<frames.length;i++)
			floats[i] = (float)frames[i];
		return floats;
	}
	public double[] getX() 
	{
		double[] x = new double[particles.length];
		for(int i=0;i<x.length;i++)
			x[i] = particles[i].getX();
		return x;
	}
	public double[] getY() {
		double[] y = new double[particles.length];
		for(int i=0;i<y.length;i++)
			y[i] = particles[i].getY();
		return y;
	}
	public double[] getZ() {
		double[] z = new double[particles.length];
		for(int i=0;i<z.length;i++)
			z[i] = particles[i].getZ();
		return z;
	}
	public boolean isAnalyzed() {
		return analyzed;
	}
	public int getLast()
	{
		return frames[frames.length-1];
	}

	public Particle[] getParticles() 
	{
		return particles;
	}
	
	public Particle getParticle(int index)
	{
		return particles[index];
	}
	
	public void setParticles(Particle[] particles) 
	{
		this.particles = particles;
	}
	/**
	 * @return
	 */
	public int getTrajType() 
	{		
		return trajType;
	}
	public double getCentroide_X() {
		return centroide_X;
	}
	
	public double getCentroide_Y() {
		return centroide_Y;
	}
	public boolean isUsar() {
		return usar;
	}
	
	public void setUsar(boolean usar) {
		this.usar = usar;
	}
	
	/*public boolean isMultipe() {
		return multiple;
	}*/
	
	/*public void setMultipe(boolean multipe) {
		this.multiple = multipe;
	}*/
	
	public double[] getTime() 
	{
		return time;
	}
	public double[] getTimePro() 
	{
		return timePro;
	}

	public double getTrajectoryTime(int index)
	{
		return time[index];
	}
	public int getNumOfGaps()
	{
		return numOfGaps;
	}

	
	public double[] getDisplacements()
	{
		return displacements;
	}
//	public double[] getDisplacementsPro()
//	{
//		return displacementsPro;
//	}
	public double[] getSSD()
	{
		return ssd;
	}
//	public double[] getSSDPro()
//	{
//		return ssdPro;
//	}
	
	public Particle[] getParticlesPro() {
		return particlesPro;
	}

	public int[] getFramesPro() {
		return framesPro;
	}

	public double[] getTimeLag() {
		return timeLag;
	}

	public double[] getMSD() {
		return msd;
	}
	
	/*public double[][] getTimeLagMSD(int initialP, int finalP)
	{
		if(finalP>initialP && finalP<msd.length)
		{
			double[][] timeMSD = new double[2][finalP-initialP+1];//msd.length];
			for(int i=initialP;i<=finalP;i++)
			{
				timeMSD[0][i-initialP]=timeLag[i];
				timeMSD[1][i-initialP]=msd[i];
			}
			return timeMSD;
		}
		else
			return null;		
	}*/
	
	public boolean isMsdOK() {
		return msdOK;
	}

	public double getMicroD() {
		return microD;
	}
//	public double getD12() {
//		return microD12;
//	}
//	public double getD13() {
//		return microD13;
//	}
//	public double getD14() {
//		return microD14;
//	}
//	public double getD15() {
//		return microD15;
//	}

	public double getMacroD() {
		return macroD;
	}
//	public double getModelD() {
//		return modelD;
//	}
	public double[] getMSDVar()
	{
		return msdVar;
	}
	public double[] getMSDNPoints()
	{
		return msdNPoints;
	}
	public double[] getMSDSD()
	{
		return msdSD;
	}

	public double getRadio() {
		return radio;
	}
	public Subtrajectory[] getSubtrajs()
	{
		Subtrajectory[] subtrajs = new Subtrajectory[this.subtrajs.size()];
		for(int i=0;i<subtrajs.length;i++)
			subtrajs[i]=this.subtrajs.get(i);			
		return subtrajs;
	}
	public Subtrajectory getSubtraj(int index)
	{
		return subtrajs.get(index);		
	}

	public double[] getVelocity() {
		return velocity;
	}
//	public double[] getVelocityPro()
//	{
//		return velocityPro;
//	}

	public double[][] getVectors() {
		return vectors;
	}
//	public double[][] getVectorsPro() {
//		return vectorsPro;
//	}
	
	public double[] getAngles() {
		return angles;
	}
	public double[] getAngleProbability()
	{
		return angleProbability;
	}
	public double getFrequenciestAngle()
	{
		double max = 0;
		int indice = 0;
		double frequenciest;
		for(int i=0;i<angleProbability.length;i++)
		{
			if(angleProbability[i]>max)
			{
				max=angleProbability[i];
				indice = i;
			}
		}
		frequenciest = (indice+1)*45-22.5;
		return frequenciest;
	}
	public double displacementAngle()
	{
		//TODO
		return 0;
	}
//	public double[] getAnglesPro() {
//		return anglesPro;
//	}
	public double[] getAnglesChanges()
	{
		return angleChanges;
	}
//	public double[] getAnglesChangesPro()
//	{
//		return angleChangesPro;
//	}
	
	/**
	 * @return
	 */
	public double[] getParamsNormal() 
	{
		return paramsNormal;
	}

	
	/**
	 * @return
	 */
	public double[] getParamsAnomalous() 
	{
		return paramsAnomalous;
	}
	
	/**
	 * @return
	 */
	public double[] getParamsDirected() 
	{
		return paramsDirected;
	}	

	/**
	 * @return
	 */
	public double[] getParamsCorralled() 
	{
		return paramsCorralled;
	}

	public double getModelGOF()
	{
		return modelGOF;
	}
	public String toString() 
	{
		StringBuffer s = new StringBuffer();
		for (int i = 0; i< particles.length; i++) {
			s.append(particles[i].toString());
		}
		s.append("\n");
		return s.toString();
	}

	public Color getColor() 
	{
		return color;
	}
	
	public boolean isToDisplay() 
	{
		return toDisplay;
	}

	public void setToDisplay(boolean toDisplay) {
		this.toDisplay = toDisplay;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public int getMovieFrame() {
		return movieFrame;
	}
//	public TrajectoryImage inv()
//	{
//		//Trajectory invTraj;
//		Particle[] invParticles = new Particle[this.particles.length];
//		for(int i=0;i<invParticles.length;i++)
//		{
//			invParticles[invParticles.length-i-1] = this.particles[i];
//			
//			//invParticles[invParticles.length-i-1].movieFrame = this.particles[i].movieFrame;
//			//System.out.println("movieframe:"+invParticles[invParticles.length-i-1].movieFrame);
//		}	
//		return new TrajectoryImage(0,invParticles,frames,0); 		
//	}

	public void setMovieFrame(int movieFrame) 
	{
		this.movieFrame = movieFrame;
	}
	
	public void setDif(double[] dif)
	{
		this.dif = dif;
	}
	public void setDev(double[] dev)
	{
		this.dev = dev;
	}
	public void setAsym(double[] asym)
	{
		this.asym = asym;
	}
	public double[] getDif()
	{
		return dif;
	}
	public double[] getDev()
	{
		return dev;
	}
	public double[] getAsym()
	{
		return asym;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}
	public Metrics getMetrics()
	{
		return metrics;
	}

	public double[] getDX() {
		return dx;
	}
	public double[] getDY() {
		return dy;
	}
	public double[] getSqDis() {
		return sq_disp;
	}
//	public double[] getSqDispPro() {
//		return sq_dispPro;
//	}
	public int[] getState() {
		return simState;
	}
	public void setState(int[] state)
	{
		this.simState = state;
	}
	public int[] getGState() {
		return guessState;
	}
	public void setGState(int[] state)
	{
		this.guessState = state;
		hmmAnalyzed=true;
	}
	public boolean isHMMAnalyzed()
	{
		return hmmAnalyzed;
	}
	public double getTotalDistanceInPath()
	{
		return totalDistancePath;
	}
 	public int getUsability()
	{
		return usability;
	}
}
