package event;

import data.Trajectory;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

public class EventEvaluator{
	
	private ImagePlus imp;
	private int limitRadius;
	private int deltaWindow;
	
	public EventEvaluator(ImagePlus imp, int limitRadius, int deltaWindow){
		this.imp=imp;
		this.limitRadius=limitRadius;
		this.deltaWindow=deltaWindow;//Al parecer no es lo mejor utilizar un delta dada la simplicidad de acceder a un arreglo por sus indices(revisar)
		
	}
	
	public void evaluate(Trajectory traj){ //En primer lugar probaré trabajando con sólo el centroide (después incluir promedio)
		if (traj.getRadio() < limitRadius){
			return;
		}
		double x=traj.getCentroide_X();//Duda: ¿puedo asumir que el centroide de la tray. es el centroide del FE?
		double y=traj.getCentroide_Y();
		int xEvent=(int)x;
		int yEvent=(int)y;
	
		int delta=this.deltaWindow;
		int Intensities[]=new int[2*delta];
		
		int startTraj=traj.getMovieFrame();//Obtiene el primer frame donde se detecta la trayectoria (verificar)
		int startFrame=startTraj-delta;
		int endFrame=startTraj+delta;//Verificar límites de la película que no se sobrepasen
		ImageStack is=imp.getStack();
		ImageProcessor ip;
		
		for (int i=startFrame;i<endFrame;i++){
			ip=is.getProcessor(i);
			Intensities[i-startFrame]=ip.getPixel(xEvent, yEvent);
		}
		
		
		
	}
	
}