package event;

import java.util.ArrayList;

import data.TrajSet;
import main.Thinker;

public class FusionEvents {
	
	Thinker thinker;
	
	GUI_FusionEvents gui_fusionevents;
	GUI_InputParameters gui_input;
	GUI_NFVesicleSelection gui_nonFusionedVesicles;
	GUI_EnsurePreprocessing gui_ensurePreprocessing;
	EventEvaluator eventEvaluator;
	EventSet eventSet;
	int currentFrame;
	int lastEventIndex=1;
	double sampleTime=1.;
	
	
	public FusionEvents(Thinker thinker){
		System.out.println("Fusion Events!!!!");
		this.thinker = thinker;
		
		gui_fusionevents = new GUI_FusionEvents(thinker);
		gui_input=new GUI_InputParameters(gui_fusionevents);
		gui_nonFusionedVesicles= new GUI_NFVesicleSelection(gui_input);
		gui_ensurePreprocessing= new GUI_EnsurePreprocessing(gui_nonFusionedVesicles);
		
		
		
		
	}
	public void openGUI(){
		gui_ensurePreprocessing.setVisible(true);
	}
	

	
	public void changeWindowAnalysisSize(int size){
		eventEvaluator.setWindowAnalysisSize(size);
	}
	
	public void fusionEvents(int fitPatchSize, int timeBetweenFrames, double minIntIncrease, ArrayList<Double> nonFusionedVesicles){
		
		this.sampleTime=timeBetweenFrames/1000.0;
		
		eventEvaluator = new EventEvaluator(thinker.particleTracker.getMovie().getImp(), 4 , 25 ,1,fitPatchSize,sampleTime,minIntIncrease,nonFusionedVesicles);
		TrajSet trajSet = thinker.getSelectedSet();
		
		eventSet = new EventSet();
		System.out.println("Number of trajs: "+trajSet.getNumOfTrajs());

		for(int i=0; i< trajSet.getNumOfTrajs(); i++){
			
			Event event = eventEvaluator.evaluateImproved(trajSet.getTraj(i));
			if(event!=null){
				eventSet.addEvent(event);
				System.out.println(event.getTau()+"tau evento");
				if (event.getTau()!=0 && event.getTMean()!=0){
					gui_fusionevents.addRowInFETableModel(new Object[]{lastEventIndex,Double.toString(event.getTau()),Double.toString(event.getAmplitude()),Double.toString(event.getTMean())});
				}
				else{
					gui_fusionevents.addRowInFETableModel(new Object[]{lastEventIndex,"-",Double.toString(event.getAmplitude()),"-"});
				}
				lastEventIndex++;
			}
			
			
		}
		if (eventSet.getNumberOfEvents()!=0){
			thinker.particleTracker.addEventsToCanvas(eventSet);
			thinker.particleTracker.changeShowEvents();
		}
		
	}
	
	public Event evaluateSelectedArea(int x1, int y1, int x2, int y2){
		Event event=eventEvaluator.evaluateSelectedArea(x1, y1, x2, y2);
		if (event!=null){
			eventSet.addEvent(event);
			System.out.println(event.getTau()+"tau evento");
			gui_fusionevents.addRowInFETableModel(
					new Object[]{lastEventIndex,
								event.getTau(),event.getAmplitude()});
			lastEventIndex++;
		}
		return event;
	}
	
	public EventSet getEventSet(){
		return eventSet;
	}
	
	public void deleteEvent(int indexEvent){
		eventSet.deleteEvent(indexEvent);
	}
	
	public GUI_FusionEvents getFusionEventsGUI(){
		return gui_fusionevents;
	}
	
	public GUI_InputParameters getInputParameters(){
		return gui_input;
	}
	
	public GUI_NFVesicleSelection getNFVesicleGUI(){
		return gui_nonFusionedVesicles;
	}
	
	public EventEvaluator getEventEvaluator(){
		return eventEvaluator;
	}
	
	public double getSampleTime(){
		return sampleTime;
	}
	
	public void setCurrentFrame(int frame){
		currentFrame=frame;
		boolean visible=this.getFusionEventsGUI().isVisible();
		if (visible){
			if (this.getFusionEventsGUI().getSelectedRow()>-1){
				this.getFusionEventsGUI().getCurrentIntVsTimeChart().setCurrentTimeInstant(currentFrame*sampleTime);
				this.getFusionEventsGUI().getCurrentIntVsTimeChart().update();
			}
		}
	}
	

}
