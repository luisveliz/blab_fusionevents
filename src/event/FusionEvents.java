package event;

import data.TrajSet;
import main.Thinker;

public class FusionEvents {
	
	Thinker thinker;
	
	GUI_FusionEvents gui_fusionevents;
	GUI_InputParameters gui_input;
	NonFusionedVesicleSelection gui_nonFusionedVesicles;
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
		gui_nonFusionedVesicles= new NonFusionedVesicleSelection(gui_input);
		
		
		
		
	}
	public void openGUI(){
		
	}
	

	
	public void changeWindowAnalysisSize(int size){
		eventEvaluator.setWindowAnalysisSize(size);
	}
	
	public void fusionEvents(int fitPatchSize, int timeBetweenFrames, double minIntIncrease){
		
		this.sampleTime=timeBetweenFrames/1000.0;
		
		eventEvaluator = new EventEvaluator(thinker.particleTracker.getMovie().getImp(), 4 , 25 ,1,fitPatchSize,sampleTime,minIntIncrease);
		TrajSet trajSet = thinker.getSelectedSet();
		
		eventSet = new EventSet();
		System.out.println("Number of trajs: "+trajSet.getNumOfTrajs());

		for(int i=0; i< trajSet.getNumOfTrajs(); i++){
			
			Event event = eventEvaluator.evaluateImproved(trajSet.getTraj(i));
			if(event!=null){
				eventSet.addEvent(event);
				System.out.println(event.getTau()+"tau evento");
				gui_fusionevents.addRowInFETableModel(new Object[]{lastEventIndex,event.getTau(),event.getAmplitude()});
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
	
	public NonFusionedVesicleSelection getNFVesicleGUI(){
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
