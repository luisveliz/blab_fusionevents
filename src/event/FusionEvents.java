package event;

import data.TrajSet;
import main.Thinker;

public class FusionEvents {
	
	Thinker thinker;
	
	GUI_FusionEvents gui_fusionevents;
	GUI_InputParameters gui_input;
	EventEvaluator eventEvaluator;
	EventSet eventSet;
	
	
	public FusionEvents(Thinker thinker){
		System.out.println("Fusion Events!!!!");
		this.thinker = thinker;
		
		gui_fusionevents = new GUI_FusionEvents(thinker);
		gui_input=new GUI_InputParameters(gui_fusionevents);
		
		
		
		
	}
	public void openGUI(){
		
	}
	

	
	public void changeWindowAnalysisSize(int size){
		eventEvaluator.setWindowAnalysisSize(size);
	}
	
	public void fusionEvents(int fitPatchSize, int timeBetweenFrames){
		
		eventEvaluator = new EventEvaluator(thinker.particleTracker.getMovie().getImp(), 4 , 25 ,1,fitPatchSize,timeBetweenFrames);
		TrajSet trajSet = thinker.getSelectedSet();
		
		eventSet = new EventSet();
		System.out.println("Number of trajs: "+trajSet.getNumOfTrajs());
		int eventIndex=0;
		for(int i=0; i< trajSet.getNumOfTrajs(); i++){
			
			Event event = eventEvaluator.evaluateImproved(trajSet.getTraj(i));
			if(event!=null){
				System.out.println("Eventooooooooooooooooooooooo!!\n\n");
				eventSet.addEvent(event);
				System.out.println(event.getTau()+"tau evento");
				gui_fusionevents.addRowInFETableModel(
						new Object[]{eventIndex,
									event.getTau(),event.getAmplitude()});
				eventIndex++;
				
				
			}
			
			
		}
		if (eventSet.getNumberOfEvents()!=0){
			thinker.particleTracker.addEventsToCanvas(eventSet);
			thinker.particleTracker.changeShowEvents();
		}
		
	}
	
	public EventSet getEventSet(){
		return eventSet;
	}
	
	public GUI_FusionEvents getFusionEventsGUI(){
		return gui_fusionevents;
	}
	
	public EventEvaluator getEventEvaluator(){
		return eventEvaluator;
	}

}
