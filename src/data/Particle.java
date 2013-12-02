package data;

import bTools.BNF;



/**
 * Defines a particle that holds all the relevant info for it.
 * A particle is detected in an image or given as input in test file mode 
 * 		X and Y coordinates are not in the usual graph coordinates sense but in the image sense;
 * 		(0,0) is the upper left corner
 *  	x is vertical top to bottom
 *  	y is horizontal left to right
 */
public class Particle {
	
	public double x, y; 					// the originally given coordinates - to be refined 
	 		
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public int[][] evaluated;
	public int[][] inside;
	public double brightness;       // Suma de las intensidades dividido en el número de pixeles considerados para el cálculo de m0.
	public double n_pixels_inside;          // Número de pixeles contabilizados para el cálculo de m0.
	public double z;
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Particle (double x, double y) 
	{
		this.x = x;
		this.y = y;
		//this.next = new int[linkrange];
	}
	
	public String toString() {  
		StringBuffer sb = new StringBuffer();
		sb.append("\t"+BNF.sF(this.x));
		sb.append("\t"+BNF.sF(this.y));
		sb.append("\t"+BNF.sF(this.brightness));
		sb.append("\t"+BNF.sF(this.z));
		sb.append("\t"+BNF.sF(this.n_pixels_inside));
		sb.append("\n");
		return sb.toString();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public double getBrightness() {
		return brightness;
	}

	public void setAvgBrightness(double brightness) {
		this.brightness = brightness;
	}
}