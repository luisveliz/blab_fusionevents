package event;

import data.TrajSet;
import main.Thinker;

public class FusionEvents {
	
	Thinker thinker;
	
	GUI_FusionEvents gui_fusionevents;
	EventEvaluator eventEvaluator;
	
	
	public FusionEvents(Thinker thinker){
		System.out.println("Fusion Events!!!!");
		this.thinker = thinker;
		gui_fusionevents = new GUI_FusionEvents(thinker);
		
		fusionEvents();
		
		
		
	}
	public void openGUI(){
		
	}
	

	
	
	
	public void fusionEvents(){
		
		eventEvaluator = new EventEvaluator(thinker.particleTracker.getMovie().getImp(), 4, 15);
		TrajSet trajSet = thinker.getSelectedSet();
		
		EventSet eventSet = new EventSet();
		System.out.println("Number of trajs: "+trajSet.getNumOfTrajs());
		for(int i=0; i< trajSet.getNumOfTrajs(); i++){
			
			Event event = eventEvaluator.evaluate(trajSet.getTraj(i));
			if(event!=null){
				System.out.println("Eventooooooooooooooooooooooo!!\n\n");
				eventSet.addEvent(event);
				gui_fusionevents.addRowInFETableModel(
						new Object[]{new Integer(event.getId()),
									new Double(event.getTau())});
				
				
			}
			
			
		}
		
	}
}
