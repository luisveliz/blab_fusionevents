package event;

import bTools.BMaths;

public class Event{
	
	private int id;
	private double eventRadiusX;//Duda: este es el radio de la trayectoria, o de la particula(zona) donde ocurre la fusion
	private double eventRadiusY;
	private int start;
	private int end;
	private int centerX;
	private int centerY;
	private double amplitude;
	private double tau;
	private double[] tmeanData;//Duda: ¿estos datos se manejaran como frames(int) o como tiempo(double) directamente?
	private double intensities[];
	private double expFit[][];
	private int increaseData[];
	
	public Event(int id,double eventRadiusX, double eventRadiusY, int centerX, int centerY, int start, int end, double amplitude, double tau, double[] tmeanData, double[] intensities, double[][] expFit, int[] increaseData){
		this.id=id;
		this.eventRadiusX=eventRadiusX;
		this.eventRadiusY=eventRadiusY;
		this.centerX=centerX;
		this.centerY=centerY;
		this.start=start;
		this.end=end;
		this.amplitude=amplitude;
		this.tau=tau;
		this.tmeanData=tmeanData;
		this.intensities=intensities;
		this.expFit=expFit;
		this.increaseData=increaseData;
		
	}
	public int getId(){
		return id;
	}
	
	public double getRadiusX(){
		return eventRadiusX;
	}
	
	public double getRadiusY(){
		return eventRadiusY;
	}
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public double getAmplitude(){
		return amplitude;
	}
	
	public double getTau(){
		return tau;
	}
	
	public double getTMean(){
		if (tmeanData!=null){
			return BMaths.roundDouble(tmeanData[1]-tmeanData[0],4);
		}else{
			return 0;
		}
	}
	
	public double [] getTMeanData(){
		return tmeanData;
	}
	
	public int getCenterX(){
		return centerX;
	}
	
	public int getCenterY(){
		return centerY;
	}
	
	public double[] getIntensities(){
		return intensities;
	}
	
	public double[][] getExpFit(){
		return expFit;
	}
	
	public int[] getMaxIncrease(){
		return increaseData;
	}
	
}