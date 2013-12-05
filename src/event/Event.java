package event;

public class Event{
	
	private int eventRadius;//Duda: este es el radio de la trayectoria, o de la particula(zona) donde ocurre la fusion
	private int start;
	private int end;
	private int amplitude;
	private double tau;
	private double tmean;//Duda: ¿estos datos se manejaran como frames(int) o como tiempo(double) directamente?
	
	public Event(int eventRadius, int start, int end, int amplitude, double tau, double tmean){
		this.start=start;
		this.end=end;
		this.amplitude=amplitude;
		this.tau=tau;
		this.tmean=tmean;
		
	}
}