package event;

public class Event{
	
	private int id;
	
	private double eventRadius;//Duda: este es el radio de la trayectoria, o de la particula(zona) donde ocurre la fusion
	private int start;
	private int end;
	private double amplitude;
	private double tau;
	private double tmean;//Duda: ¿estos datos se manejaran como frames(int) o como tiempo(double) directamente?
	
	public Event(double eventRadius, int start, int end, double amplitude, double tau, double tmean){
		this.eventRadius=eventRadius;
		this.start=start;
		this.end=end;
		this.amplitude=amplitude;
		this.tau=tau;
		this.tmean=tmean;
		
	}
	public int getId(){
		return id;
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
		return tmean;
	}
	
}