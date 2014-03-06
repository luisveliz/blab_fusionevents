package event;

import event.Event;
import bTools.BMaths; 

import java.util.ArrayList;

public class EventSet{
	private ArrayList<Event> events;
	private double averageAmplitude;
	private double averageTau;
	private double averageTMean;
	private int nEvents;
	
	public EventSet(){
		events=new ArrayList<Event>();
		averageAmplitude=0;
		averageTau=0.0;
		averageTMean=0.0;
		nEvents=0;
	}
	
	public void addEvent(Event event){
		events.add(event);
		nEvents++;
	}
	
	public void deleteEvent(int indexEvent){
		events.remove(indexEvent);
		nEvents--;
	}
	
	public double getAvgTau(){
		return averageTau;
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
	
	public int getNumberOfEvents(){
		return nEvents;
	}
	
	public Event getEvent(int index){
		return events.get(index);
	}
	
	
	
	
}