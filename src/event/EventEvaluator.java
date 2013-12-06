package event;

import data.Trajectory;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

public class EventEvaluator{
	
	private ImagePlus imp;
	private int limitRadius;
	private int deltaWindow;
	
	private int impEndFrame; //Actual length of the movie.
	
	public EventEvaluator(ImagePlus imp, int limitRadius, int deltaWindow){
		this.imp=imp;
		this.limitRadius=limitRadius;
		this.deltaWindow=deltaWindow;//Al parecer no es lo mejor utilizar un delta dada la simplicidad de acceder a un arreglo por sus indices(revisar)
		
		
		//Checking which is the biggest one, NSlices or NFrames.
		if(imp.getNFrames() > 1){
			impEndFrame = imp.getNFrames();
		}else if(imp.getNSlices() > 1){
			impEndFrame = imp.getNSlices();
		}else{
			System.out.println("No es una película!!");
		}
	}
	
	public Event evaluate(Trajectory traj){ //En primer lugar probaré trabajando con sólo el centroide (después incluir promedio)
		if (traj.getRadio() > limitRadius){
			return null;
		}
		double x=traj.getCentroide_X();//Duda: ¿puedo asumir que el centroide de la tray. es el centroide del FE?
		double y=traj.getCentroide_Y();
		int xEvent=(int)x;
		int yEvent=(int)y;
		
		int startTraj=traj.getMovieFrame();//Obtiene el primer frame donde se detecta la trayectoria (verificar)
		int startFrame = startTraj-deltaWindow;
		if (startFrame<1){
			startFrame=1;
		}
		
		int endFrame=startTraj+deltaWindow;//Verificar límites de la película que no se sobrepasen
		if (endFrame > impEndFrame){
			endFrame = impEndFrame;
		}
		int leftInterval= startTraj-startFrame;
		int rightInterval= endFrame-startTraj;
		int Intensities[]=new int[leftInterval+rightInterval];
		
		System.out.println("Inicio ventana:"+startFrame+" , centro ventana:"+startTraj+" , fin ventana:"+endFrame);
		
		ImageStack is=imp.getStack();
		ImageProcessor ip;
	
		
		for (int i=startFrame;i<endFrame;i++){
			ip=is.getProcessor(i);
			Intensities[i-startFrame]=ip.getPixel(xEvent, yEvent);
			System.out.println("(x,y)=("+xEvent+","+yEvent+"): "+Intensities[i-startFrame]);
		}
		
		return null;//Solo puse esto para que dejase de arrojar error
		
		
	}
	
}