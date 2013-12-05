package event;

import event.Event;
import bTools.BMaths; 

import java.util.ArrayList;

public class EventSet{
	private ArrayList<Event> events;
	private double averageAmplitude;
	private double averageTau;
	private double averageTMean;
	
	public EventSet(){
		this.events=new ArrayList<Event>();
		this.averageAmplitude=0;
		this.averageTau=0.0;
		this.averageTMean=0.0;
	}
	
	public void addEvent(Event event){
		events.add(event);
	}
	
	public double getAvgTau(){
		return this.averageTau;
	}
	
	public double getAvgAmplitude(){
		return this.averageAmplitude;
	}
	
	public double getAvgTMean(){
		return this.averageTMean;
	}
	
	public void updateAvgTau(){
		int nEvents=events.size();
		double tauArray[]=new double [nEvents];
		for (int i=0; i<nEvents;i++){
			tauArray[i]=events.get(i).getTau();
		}
		averageTau=BMaths.avg(tauArray);
	}
	
	void updateAvgAmplitude(){
		
	}
	
	void updateAvgTMean(){
		
	}
	
	
	
	
}