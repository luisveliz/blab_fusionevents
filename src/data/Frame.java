package data;


import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.text.NumberFormat;


/**
 * Defines a MyFrame that is based upon an ImageProcessor or information from a text file.
 * <br>MyFrame class has all the necessary methods to detect and report the "real" particles 
 * for them to be linked.
 * <br>Some of its methods use global variables defined and calculated in <code>ParticleTracker_</code>
 * @see ParticleTracker_#mask
 * @see ParticleTracker_#kernel
 * @see ParticleTracker_#cutoff
 * @see ParticleTracker_#percentile
 * @see ParticleTracker_#radius
 * @see ParticleTracker_#linkrange
 * @see ParticleTracker_#global_max
 * @see ParticleTracker_#global_min
 *
 */
/**
 * @author Luis
 *
 */
public class Frame 
{
	ImageParticle[] particles;		// an array Particle, holds all the particles detected in this frame
								// after particle discrimination holds only the "real" particles
	
	int particles_number;		// number of particles initialy detected
	int real_particles_number;	// number of "real" particles discrimination
	int frame_number;			// Serial number of this frame in the movie (can be 0)
	StringBuffer info_before_discrimination;// holdes string with ready to print info
											// about this frame before particle discrimination 
	
	/* only relevant to frames representing real images */
	ImageProcessor original_ip;	// the original image 	
	ImageProcessor original_fp; // the original image after convertion to float processor
	ImageProcessor restored_fp; // the floating processor after image restoration
	
	//////////////////////////////////////////////////////////////////////////////////////
	ImageProcessor dilated_ip;
	//////////////////////////////////////////////////////////////////////////////////////
	float threshold;			// threshold for particle detection 
	boolean normalized = false;

	
	/**
	 * Constructor for ImageProcessor based MyFrame.
	 * <br>All particles and other information will be derived from the given <code>ImageProcessor</code>
	 * by applying internal MyFrame methods  
	 * @param ip the original ImageProcessor upon this MyFrame is based
	 * @param frame_num the serial number of this frame in the movie
	 */
	public Frame (ImageProcessor ip, int frame_num) 
	{
		this.original_ip = ip;
		this.frame_number = frame_num;
	}
	
	/**
	 * @return
	 */
	public ImageProcessor getOriginal_ip() 
	{
		return original_ip;
	}

	/**
	 * @param originalIp
	 */
	public void setOriginal_ip(ImageProcessor originalIp) 
	{
		original_ip = originalIp;
	}
	
	
	/**
	 * Generates a "ready to print" string with all the 
	 * particles positions AFTER discrimination in this frame.
	 * @return a <code>StringBuffer</code> with the info
	 */
	private StringBuffer getFrameInfoAfterDiscrimination() 
	{
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(6);
		nf.setMinimumFractionDigits(6);
		
		// I work with StringBuffer since its faster than String
		StringBuffer info = new StringBuffer("%\tParticles after non-particle discrimination (");
		info.append(this.real_particles_number);
		info.append(" particles):\n");
		for (int i = 0; i<this.particles.length; i++) {
			info.append("%\t\t");
			info.append(nf.format(this.particles[i].x));
			info.append(" ");
			info.append(nf.format(this.particles[i].y));
			info.append("\n");
		}
		return info;
	}
	
	/**
	 * Generates (in real time) a "ready to print" StringBuffer with this frame 
	 * infomation before and after non particles discrimination
	 * @return a StringBuffer with the info
	 * @see Frame#getFrameInfoAfterDiscrimination()
	 * @see #info_before_discrimination
	 */
	public StringBuffer getFullFrameInfo() 
	{
		StringBuffer info = new StringBuffer();
		info.append(info_before_discrimination);
		info.append(getFrameInfoAfterDiscrimination());
		return info;					
	}
	
	/**
	 * Generates a "ready to print" string that shows for each particle in this frame 
	 * (AFTER discrimination) all the particles it is linked to.
	 * @return a String with the info
	 */	
	public String toString() 
	{			
		return toStringBuffer().toString();
	}
	
	/**
	 * The method <code>toString()</code> calls this method
	 * <br>Generates a "ready to print" StringBuffer that shows for each particle in this frame 
	 * (AFTER discrimination) all the particles it is linked to.
	 * @return a <code>StringBuffer</code> with the info
	 */	
	public StringBuffer toStringBuffer() 
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(6);
		nf.setMinimumFractionDigits(6);
		StringBuffer sb = new StringBuffer("% Frame ");
	/*	sb.append(this.frame_number);
		sb.append("\n");
		for(int j = 0; j < this.particles.length; j++) {
			sb.append("%\tParticle ");
			sb.append(j);
			sb.append(" (");
			sb.append(nf.format(this.particles[j].x));
			sb.append(", ");
			sb.append(nf.format(this.particles[j].y));
			sb.append(")\n");					
			for(int k = 0; k < linkrange; k++) {
				sb.append("%\t\tlinked to particle ");
				sb.append(this.particles[j].next[k]);
				sb.append(" in frame ");
				sb.append((this.frame_number + k + 1));
				sb.append("\n");					
			}
		}*/
		return sb;
	}
	
	/**
	 * Creates a <code>ByteProcessor</code> and draws on it the particles defined in this MyFrame 
	 * <br>The background color is <code>Color.black</code>
	 * <br>The color of the dots drawn for each particle is <code>Color.white</code>
	 * <br>particles position have floating point precision but can be drawn only at integer precision - 
	 * therefore the created image is only an estimation
	 * @param width defines the width of the created <code>ByteProcessor</code>
	 * @param height defines the height of the created <code>ByteProcessor</code>
	 * @return the created processor
	 * @see ImageProcessor#drawDot(int, int)
	 */
	private ImageProcessor createImage(int width, int height) 
	{
		ImageProcessor ip = new ByteProcessor(width, height);
		ip.setColor(Color.black);
        ip.fill();
        ip.setColor(Color.white);
		for (int i = 0; i<this.particles.length; i++) 
		{
			ip.drawDot( (int) Math.round(this.particles[i].y), (int) Math.round(this.particles[i].x));
		}
		return ip;		
	}

	/**
	 * @return
	 */
	public ImageParticle[] getParticles() {
		return particles;
	}

	/**
	 * @param particles
	 */
	public void setParticles(ImageParticle[] particles) {
		this.particles = particles;
	}

	/**
	 * @return
	 */
	public int getFrame_number() {
		return frame_number;
	}

	/**
	 * @param frameNumber
	 */
	public void setFrame_number(int frameNumber) {
		this.frame_number = frameNumber;
	}
	
	/**
	 * @return
	 */
	public int getParticles_number() {
		return particles_number;
	}

	/**
	 * @param particlesNumber
	 */
	public void setParticles_number(int particlesNumber) {
		particles_number = particlesNumber;
	}

	/**
	 * @return
	 */
	public int getReal_particles_number() {
		return real_particles_number;
	}

	/**
	 * @param realParticlesNumber
	 */
	public void setReal_particles_number(int realParticlesNumber) {
		real_particles_number = realParticlesNumber;
	}

	/**
	 * @return
	 */
	public ImageProcessor getOriginal_fp() {
		return original_fp;
	}

	/**
	 * @param originalFp
	 */
	public void setOriginal_fp(ImageProcessor originalFp) {
		original_fp = originalFp;
	}

	/**
	 * @return
	 */
	public ImageProcessor getRestored_fp() {
		return restored_fp;
	}

	/**
	 * @param restoredFp
	 */
	public void setRestored_fp(ImageProcessor restoredFp) {
		restored_fp = restoredFp;
	}

	/**
	 * @return
	 */
	public ImageProcessor getDilated_ip() {
		return dilated_ip;
	}

	/**
	 * @param dilatedIp
	 */
	public void setDilated_ip(ImageProcessor dilatedIp) {
		dilated_ip = dilatedIp;
	}

	/**
	 * @return
	 */
	public float getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 */
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return
	 */
	public boolean isNormalized() {
		return normalized;
	}

	/**
	 * @param normalized
	 */
	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
	}

	/**
	 * @param i
	 * @return
	 */
	public ImageParticle getParticle(int i)
	{
		return this.particles[i];
	}
}