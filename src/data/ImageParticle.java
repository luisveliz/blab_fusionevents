package data;

import java.text.NumberFormat;

public class ImageParticle extends Particle
{
	public float original_x , original_y; 	// the originally given coordinates - not to be changed
	public boolean special; 				// a flag that is used while detecting and linking particles
	public int[] next; 					// array that holds in position i the particle number in frame i
									// that this particle is linked to  
	public int movieFrame;                 // the number of the movie frame this particle belonges to (can't be 0)
	

	/* only relevant to particles detected in images */
	public float m0, m2; 					// zero and second order intensity moment
	public float score; 					// non-particle discrimination score
	
	/* only relevant to particles given as input */
	//sString[] all_params; 			// all params that relate to this particle,
									// 1st 2 should be x and y respectfully
	
	public ImageParticle(float x, float y, int movieFrame) 
	{
		super(x, y);
		this.movieFrame = movieFrame;
		this.original_x = x;
		this.original_y = y;
		this.special = true;	
	}
	
	@Override
	public String toString() {
		
		// I work with StringBuffer since its faster than String
		// At the end convert to String and return
		StringBuffer sb = new StringBuffer();
		///////////////////////////////////////////////////////////////////
		StringBuffer sp = new StringBuffer("          ");
		///////////////////////////////////////////////////////////////////
		// format the number to look nice in print (same number of digits)
		NumberFormat nf = NumberFormat.getInstance();			
		nf.setMaximumFractionDigits(6);
		nf.setMinimumFractionDigits(6);
		
		//sb.append(this.frame);
		//sb.append(this.frame+1);
		/*if (text_files_mode) {
			for (int i = 0; i<all_params.length; i++) {
				sb.append(sp);
				sb.append(nf.format(Float.parseFloat(all_params[i])));
			}
			sb.append("\n");
		} else {*/
			sb.append(sp);
			sb.append(nf.format(this.x));
			sb.append(sp);
			sb.append(nf.format(this.y));
			sb.append(sp);
			sb.append(nf.format(this.m0));
			sb.append(sp);
			sb.append(nf.format(this.m2));
			sb.append(sp);
			sb.append(nf.format(this.score));
			///////////////////////////////////////////////////////////////
			sb.append(sp);
			sb.append(nf.format(this.brightness));
			sb.append(sp);
			sb.append(nf.format(this.z));
			sb.append(sp);
			sb.append(nf.format(this.n_pixels_inside));
			sb.append(sp);
			for(int i=0;i<this.next.length;i++)
			{
				sb.append(this.next[i]);
				sb.append("  ");
			}
			///////////////////////////////////////////////////////////////
			sb.append("\n");
		//}						
		return sb.toString();
	}
	public int getMovieFrame()
	{
		return movieFrame;
	}
}
