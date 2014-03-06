package particleTracker;

import ij.IJ;
import ij.measure.Measurements;
import ij.plugin.filter.Convolver;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import ij.process.StackStatistics;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import bTools.BMaths;

import data.Frame;
import data.ImageParticle;
import data.Movie;

/**
 * 
 * 
 * 
 */
public class ParticleDetector 
{
	/* user defined parameters */
	public static int radius;
	public static double cutoff;
	public static float percentile; 	// default (user input/100)
	
	/*	image Restoration vars	*/
	private static int[] mask;
	public static float[] kernel;	

	///////////////////////////////////////  atributos de MyFrame /////////////////////////////
	private static ImageParticle[] particles;		// an array Particle, holds all the particles detected in this frame
	// after particle discrimination holds only the "real" particles

	private static int particles_number;		// number of particles initialy detected 
	private static int real_particles_number;	// number of "real" particles discrimination
	private static int frame_number;			// Serial number of this frame in the movie (can be 0)
	//private static StringBuffer info_before_discrimination;// holdes string with ready to print info
											// 	about this frame before particle discrimination 
	

	/* only relevant to frames representing real images */
	private static ImageProcessor original_ip;	// the original image 	
	private static ImageProcessor original_fp; // the original image after convertion to float processor
	private static ImageProcessor restored_fp; // the floating processor after image restoration
	private static ImageProcessor dilated_ip;
	
	private static float threshold = 0;			// threshold for particle detection 
	private static boolean normalized = false;
	///////////////////////////////////////  atributos de MyFrame /////////////////////////////
	
			
	public static void detectParticles(Movie movie, StackStatistics stack_stats)
	{		
		//TODO spt_gui.setProcessBarMaxValue(movie.getLenght());
		//TODO spt_gui.setProcessBarValue(0);
		for(int i=0;i<movie.getLenght();i++)
		{
			//System.out.println("Procesando frame: "+(i+1));			
			detectParticles(movie.getFrame(i), (float)stack_stats.max, (float)stack_stats.min);
			
			/*final int value=i;			
			SwingUtilities.invokeLater(new Runnable()
		    {
		        @Override
		        public void run()
		        {
		        	System.out.println("run()");
		        	spt_gui.setProcessBarValue(value+1);
		        	show_FrameInfo(movie.getFrame(value));		        	
		        }
		    });*/			
			//TODO spt_gui.setProcessBarValue(i+1);
        	//tirfAnalyzer.show_FrameInfo(movie.getFrame(i));
		}		
	}
	/**
	 * Antiguo featurePointDetection()
	 */
	public static void detectParticles(Frame frame, float global_max, float global_min)
	{
		/*	image Restoration vars	*/
		//int[] mask = null;		
		//float[] kernel = null;
					
		/////////////////////////////////////////  atributos de MyFrame /////////////////////////////
		//Particle[] particles = null;		// an array Particle, holds all the particles detected in this frame
		// after particle discrimination holds only the "real" particles

		particles_number = 0;		// number of particles initialy detected 
		real_particles_number = 0;	// number of "real" particles discrimination
		frame_number = frame.getFrame_number();			// Serial number of this frame in the movie (can be 0)
		//StringBuffer info_before_discrimination;// holdes string with ready to print info
												// 	about this frame before particle discrimination 
		

		/* only relevant to frames representing real images */
		original_ip = frame.getOriginal_ip();	// the original image 	
		original_fp = null; // the original image after convertion to float processor
		restored_fp = null; // the floating processor after image restoration
		dilated_ip = null;

		threshold = 0;			// threshold for particle detection 
		//normalized = false;
		///////////////////////////////////////  atributos de MyFrame /////////////////////////////
				
		//TODO agregar que verifique la actualización de parámetros
		mask = generateMask();
		kernel = makeKernel();	
				
        
		/* Converting the original imageProcessor to float 
		 * This is a constraint caused by the lack of floating point precision of pixels 
		 * value in 16bit and 8bit image processors in ImageJ therefore, if the image is not
		 * converted to 32bit floating point, false particles get detected */
		original_fp = original_ip.convertToFloat();	  
		//original_fp = frame.getOriginal_ip();
		///////////////////////////////////////////////////////////////
		//this.original_fp.setLineWidth(4);
		///////////////////////////////////////////////////////////////
		
		/* The algorithm is initialized by normalizing the frame*/
		normalizeFrameFloat(original_fp, global_max, global_min);
		
		/* Image Restoration - Step 1 of the algorithm */
        restored_fp = imageRestoration(original_fp);       
    
        /* Estimation of the point location - Step 2 of the algorithm */
		findThreshold(restored_fp);
		pointLocationsEstimation(restored_fp);
		
		//System.out.println("particles.length afuera: "+particles.length);
		
		/* Refinement of the point location - Step 3 of the algorithm */
		pointLocationsRefinement(restored_fp);
		
		/* Save frame information before particle discrimination - it will be lost otherwise*/
		//info_before_discrimination = generateFrameInfoBeforeDiscrimination();
		
		/* Non Particle Discrimination - Step 4 of the algorithm */
		nonParticleDiscrimination();
		
		/* remove all the "false" particles from paricles array */
		removeNonParticle();	
		
		calculateParticleBrightness();
				
		//frame.setOriginal_ip(original_ip);
		frame.setOriginal_fp(original_fp);
		frame.setRestored_fp(restored_fp);
		frame.setDilated_ip(dilated_ip);		
		frame.setFrame_number(frame_number);
		frame.setReal_particles_number(real_particles_number);
		frame.setThreshold(threshold);	
		frame.setParticles(particles);	
	}
	
	
	/**
	 * First phase of the algorithm - time and memory consuming !!
	 * <br>Determines the "real" particles in this frame (only for frame constructed from Image)
	 * <br>Converts the <code>original_ip</code> to <code>FloatProcessor</code>, normalizes it, convolutes and dilates it,
	 * finds the particles, refine their position and filters out non particles
	 * @see ImageProcessor#convertToFloat()
	 * @see Frame#normalizeFrameFloat(ImageProcessor)
	 * @see Frame#imageRestoration(ImageProcessor)
	 * @see Frame#pointLocationsEstimation(ImageProcessor)
	 * @see Frame#pointLocationsRefinement(ImageProcessor)
	 * @see Frame#nonParticleDiscrimination()
	 */
	/*private static void featurePointDetection (Frame frame) 
	{
		
	}*/	

	/**
	 * Normalizes a given <code>ImageProcessor</code> to [0,1].
	 * <br>According to the pre determend global min and max pixel value in the movie.
	 * <br>All pixel intensity values I are normalized as (I-gMin)/(gMax-gMin)
	 * @param ip ImageProcessor to be normalized
	 */
	public static void normalizeFrameFloat(ImageProcessor ip, float global_max, float global_min) 
	{
		//TODO revisar que onda con normalized
		//if (!this.normalized) 
		//{	
		
			StringBuffer sb = new StringBuffer();
			
			float[] pixels=(float[])ip.getPixels();	//getPixels() retorna una referencia!!! no una copia.		
			
			/*sb.append("ip.getPixels antes:\n");
			for(int i=0;i<pixels.length;i++)
			{				
				sb.append(pixels[i]+" ");
			}
			sb.append("\n");*/
			
			float tmp_pix_value;
			for (int i = 0; i < pixels.length; i++) 
			{
				tmp_pix_value = (pixels[i]-global_min)/(global_max - global_min);
				pixels[i] = (float)(tmp_pix_value);
			}
			
			/*sb.append("pixels normalizados:\n");
			for(int i=0;i<pixels.length;i++)
			{				
				sb.append(pixels[i]+" ");
			}
			sb.append("\n");
			
			pixels=(float[])ip.getPixels();
			sb.append("ip.getPixels después:\n");			
			for(int i=0;i<pixels.length;i++)
			{				
				sb.append(pixels[i]+" ");
			}*/
			//System.out.println(sb.toString());
			//normalized = true;
		//} else {
//			 TODO set error
		//}
	}
	
	/**
	 * Corrects imperfections in the given <code>ImageProcessor</code> by
	 * convolving it with the pre calculated <code>kernel</code>
	 * @param ip ImageProcessor to be restored
	 * @return the restored <code>ImageProcessor</code>
	 * @see Convolver#convolve(ij.process.ImageProcessor, float[], int, int)
	 * @see ParticleTracker_#kernel
	 */
	public static ImageProcessor imageRestoration(ImageProcessor ip)
	{        
		ImageProcessor restored = ip.duplicate();
		int kernel_radius = radius;//getRadius();
		int kernel_width = (kernel_radius * 2) + 1;
        Convolver convolver = new Convolver();
        // no need to normalize the kernel - its already normalized
        convolver.setNormalize(false);
        convolver.convolve(restored, kernel, kernel_width , kernel_width);        
        return restored;	        
    }
	
    /**
     * Estimates the feature point locations in the given <code>ImageProcessor</code>
     * <br>Any pixel with the same value before and after dilation and value higher
     * then the pre calculated threshold is considered as a feature point (Particle).
     * <br>Adds each found <code>Particle</code> to the <code>particles</code> array.
     * <br>Mostly adapted from Ingo Oppermann implementation
     * @param ip ImageProcessor, should be after conversion, normalization and restoration 
     */
    private static void pointLocationsEstimation(ImageProcessor ip) 
    {
    	//System.out.println("principio pointLocationEstimation()");
        int particles_num = 0;
        
        /* do a grayscale dilation */
        //ImageProcessor dilated_ip = dilateGeneric(ip, mask, radius);
        ////////////////////////////////////////////////////////////////////////////////////////
        dilated_ip = dilateGeneric(ip);
        ////////////////////////////////////////////////////////////////////////////////////////
        
        //TODO corregir: no debería contarse primero y luego crear->>> trabajar con Vector o ArrayList
        /* loop over all pixels and checks how many particles meet the criteria */
        for (int i = 0; i < ip.getHeight(); i++){
        	for (int j = 0; j < ip.getWidth(); j++){  
        		if (ip.getPixelValue(j, i) - dilated_ip.getPixelValue(j, i) == 0.0 && 
        				ip.getPixelValue(j, i) > threshold)
        				//ip.getPixelValue(j, i) > findLocalThreshold(ip, j, i))
        		{
        			particles_num++; 
        		}        		
        	}
        }
        /* initialise the particles array */
        particles = new ImageParticle[particles_num];
        //System.out.println("particles.length in pointLocationEstimation(): "+particles.length);
        particles_number = particles_num;
        particles_num = 0;
        
        /* loop over all pixels */ 
        for (int i = 0; i < ip.getHeight(); i++)
        {
        	for (int j = 0; j < ip.getWidth(); j++)
        	{  
        		if (ip.getPixelValue(j, i) - dilated_ip.getPixelValue(j, i) == 0.0 && 
        				ip.getPixelValue(j, i) > threshold)
        				//ip.getPixelValue(j, i) > findLocalThreshold(ip, j, i))
        		{
        			/* and add each particle thats meets the criteria to the particles array */
        			particles[particles_num] = new ImageParticle(i, j, frame_number);
        			//System.out.println("Partícula detectada en frame: "+ frame_number);
        			particles_num++;        			        			
        		} 
        	}
        }	
        //System.out.println("fin pointLocationEstimation()");
    }
	
	/**
	 * The positions of the found particles will be refined according to their momentum terms
	 * <br> Adapted "as is" from Ingo Oppermann implementation
	 * @param ip ImageProcessor, should be after conversion, normalization and restoration
	 */
	private static void pointLocationsRefinement(ImageProcessor ip) 
	{
		//System.out.println("principio pointLocationRefinement()");
		
		int m, k, l, x, y, tx, ty;
		float epsx, epsy, c;
		
		int mask_width = 2 * radius +1;
		
		/* Set every value that ist smaller than 0 to 0 */		
		for (int i = 0; i < ip.getHeight(); i++) {
			for (int j = 0; j < ip.getWidth(); j++) {
				if(ip.getPixelValue(j, i) < 0.0)
					ip.putPixelValue(j, i, 0.0);
			}
		}
			
		/* Loop over all particles */
		//System.out.println("particles.length in pointLocationRefinement(): "+particles.length);
		for(m = 0; m < particles.length; m++) {
			
			/////////////////////////////////////////////////////////////////////////////////
			//int pixel_count = 0;
			//float brightness = 0;
			/////////////////////////////////////////////////////////////////////////////////
			
			
			particles[m].special = true;
			particles[m].score = 0.0F;
			epsx = epsy = 1.0F;

			while (epsx > 0.5 || epsx < -0.5 || epsy > 0.5 || epsy < -0.5) 
			{
				particles[m].m0 = 0.0F;
				particles[m].m2 = 0.0F;
				epsx = 0.0F;
				epsy = 0.0F;

				for(k = -radius; k <= radius; k++) 
				{
					if(((int)particles[m].x + k) < 0 || ((int)particles[m].x + k) >= ip.getHeight())
						continue;
					x = (int)particles[m].x + k;
					
					for(l = -radius; l <= radius; l++) 
					{
						if(((int)particles[m].y + l) < 0 || ((int)particles[m].y + l) >= ip.getWidth())
							continue;
						y = (int)particles[m].y + l;

						c = ip.getPixelValue(y, x) * (float)mask[BMaths.coord(k + radius, l + radius, mask_width)];
						particles[m].m0 += c;
						///////////////////////////////////////////////////////////////////////
						//TODO ver bien esto!!
						//brightness+=ip.getPixelValue(y, x);
						//pixel_count++;
						///////////////////////////////////////////////////////////////////////
						
						epsx += (float)k * c;
						epsy += (float)l * c;
						particles[m].m2 += (float)(k * k + l * l) * c;
					}
				}
				////////////////////////////////////////////////////////////////////////////////
				//particles[m].average_brightness = particles[m].m0/pixel_count;
				//particles[m].average_brightness = brightness/pixel_count;
				//particles[m].n_pixels_inside = pixel_count;
				//particles[m].z = -(float)Math.log(particles[m].average_brightness)/160;
				////////////////////////////////////////////////////////////////////////////////
				

				epsx /= particles[m].m0;
				epsy /= particles[m].m0;
				particles[m].m2 /= particles[m].m0;

				// This is a little hack to avoid numerical inaccuracy
				tx = (int)(10.0 * epsx);
				ty = (int)(10.0 * epsy);

				if((float)(tx)/10.0 > 0.5) {
					if((int)particles[m].x + 1 < ip.getHeight())
						particles[m].x++;
				}
				else if((float)(tx)/10.0 < -0.5) {
					if((int)particles[m].x - 1 >= 0)
						particles[m].x--;						
				}
				if((float)(ty)/10.0 > 0.5) {
					if((int)particles[m].y + 1 < ip.getWidth())
						particles[m].y++;
				}
				else if((float)(ty)/10.0 < -0.5) {
					if((int)particles[m].y - 1 >= 0)
						particles[m].y--;
				}

				if((float)(tx)/10.0 <= 0.5 && (float)(tx)/10.0 >= -0.5 && (float)(ty)/10.0 <= 0.5 && (float)(ty)/10.0 >= -0.5)
					break;
			}
			
			particles[m].x += epsx;
			particles[m].y += epsy;
		}	
		//System.out.println("fin pointLocationRefinement()");
	}

	/**
	 * Rejects spurious particles detections such as unspecific signals, dust, or particle aggregates. 
	 * <br>The implemented classification algorithm after Crocker and Grier [68] is based on the
	 * intensity moments of orders 0 and 2.
	 * <br>Particles with lower final score than the user-defined cutoff are discarded 
	 * <br>Adapted "as is" from Ingo Oppermann implementation
	 */
	private static void nonParticleDiscrimination()
	{
		int j, k;
		double score;
		real_particles_number = particles_number;
		for(j = 0; j < particles.length; j++) {				
			for(k = j + 1; k < particles.length; k++) {
				score = (double)((1.0 / (2.0 * Math.PI * 0.1 * 0.1)) * 
						Math.exp(-(particles[j].m0 - particles[k].m0) *
						(particles[j].m0 - particles[k].m0) / (2.0 * 0.1) -
						(particles[j].m2 - particles[k].m2) * 
						(particles[j].m2 - particles[k].m2) / (2.0 * 0.1)));
				particles[j].score += score;
				particles[k].score += score;
			}
			if(particles[j].score < cutoff) {
				particles[j].special = false;
				real_particles_number--;					
			}
		}				
	}
    
    /**
     * removes particles that were discarded by the <code>nonParticleDiscrimination</code> method
     * from the particles array. 
     * <br>Non particles will be removed from the <code>particles</code> array so if their info is 
     * needed, it should be saved before calling this method
     * @see Frame#nonParticleDiscrimination()
     */
    private static void removeNonParticle() 
    {
       	ImageParticle[] new_particles = new ImageParticle[real_particles_number];
    	int new_par_index = 0;
    	for (int i = 0; i< particles.length; i++)
    	{
    		if (particles[i].special) 
    		{
    			new_particles[new_par_index] = particles[i];
    			new_par_index++;
    		}
    	}
    	particles = new_particles;
    }
    
    
    
    private static void calculateParticleBrightness()
    {
    	boolean debug = false;
    	Shape circle;
    	double brightness = 0.0;
    	int pixel_count = 0;
    	int x_pixel;
    	int y_pixel;
    	int value;
    	StringBuffer sb = new StringBuffer();
    	
    	ArrayList<int[]> insideList = new ArrayList<int[]>();
    	ArrayList<int[]> evaluatedList = new ArrayList<int[]>();
    	
    	for (int p = 0; p<particles.length; p++)
    	{
    		circle = new Ellipse2D.Double(particles[p].y-radius, particles[p].x-radius, 2*radius, 2*radius);
    		for(int i = -radius-1; i<=radius; i++)
    		{
    			for(int j = -radius-1; j<=radius; j++)
    			{
    				x_pixel = (int)Math.round(particles[p].y+j);
    				y_pixel = (int)Math.round(particles[p].x+i);
    				evaluatedList.add(new int[]{x_pixel,y_pixel});
    				
    				value = (int) original_ip.getPixelValue(x_pixel, y_pixel);
    				sb.append("DATA pixel:"+evaluatedList.size()+" x:"+x_pixel+" y:"+y_pixel+" value:"+value);
    				if(circle.contains(x_pixel+0.5, y_pixel+0.5))
    				{
    					sb.append(" INSIDE");
    					insideList.add(new int[]{x_pixel,y_pixel});
        				brightness += value;
    				}
    				else
    					sb.append(" OUTSIDE");
    			}
    			sb.append("\n");
    		}
    		sb.append("Num of evaluated:"+evaluatedList.size());
    		int[][] evaluated = new int[evaluatedList.size()][2];
    		for(int i=0;i<evaluatedList.size();i++)
    		{
    			evaluated[i][0] = evaluatedList.get(i)[0];
    			evaluated[i][1] = evaluatedList.get(i)[1];    			
    		}
    		particles[p].evaluated = evaluated;
    		
    		sb.append(" Num of inside:"+insideList.size()+"\n");
    		int[][] inside = new int[insideList.size()][2];
    		for(int i=0;i<insideList.size();i++)
    		{
    			inside[i][0] = insideList.get(i)[0];
    			inside[i][1] = insideList.get(i)[1];    			
    		}
    		particles[p].inside = inside;
    		
    		particles[p].n_pixels_inside = insideList.size();
    		particles[p].brightness = (float) (brightness/insideList.size());
    		
    		sb.append("Particle "+p+"\n"+"Brightness:"+particles[p].brightness+" n_pixels:"+pixel_count);
    		if(debug)
    		{
    			IJ.log(sb.toString());
    			sb.delete(0,sb.length());
    		}
    	}
    }
    
	
	/**
	 * Finds and sets the threshold value for this frame given the 
	 * user defined percenticle and an ImageProcessor
	 * @param ip ImageProcessor after conversion, normalization and restoration
	 * @param percent the upper rth percentile to be considered as candidate Particles
	 */
	private static void findThreshold(ImageProcessor ip)
	{
		
		int i, j, thold;			
		
		/* find this ImageProcessors min and max pixel value */
		ImageStatistics stats = ImageStatistics.getStatistics(ip, Measurements.MIN_MAX, null);
		float min = (float)stats.min;
		float max = (float)stats.max;

		double[] hist = new double[256];
		for (i = 0; i< hist.length; i++) {
			hist[i] = 0;
		}

		for(i = 0; i < ip.getHeight(); i++) {
			for(j = 0; j < ip.getWidth(); j++) {
				hist[(int)((ip.getPixelValue(j, i) - min) * 255.0 / (max - min))]++;
			}
		}				

		for(i = 254; i >= 0; i--)
			hist[i] += hist[i + 1];

		thold = 0;
		while(hist[255 - thold] / hist[0] < percentile)
		{
			thold++;	
			if(thold > 255)
				break;				
		}
		thold = 255 - thold + 1;
		threshold = ((float)(thold / 255.0) * (max - min) + min);			
	}
	
	private static float findLocalThreshold(ImageProcessor ip, int x, int y)
	{
		if(x>20 && x<ip.getWidth()-20 && y>20 && y<ip.getHeight()-20)
		{
			int i, j, thold;			
			
			/* find this ImageProcessors min and max pixel value */
			ImageStatistics stats = ImageStatistics.getStatistics(ip, Measurements.MIN_MAX, null);
			float min = (float)stats.min;
			float max = (float)stats.max;
	
			double[] hist = new double[256];
			for (i = 0; i< hist.length; i++) {
				hist[i] = 0;
			}
			
			int aux=0;
	
			for(i = y-20; i < y+20; i++) {
				for(j = x-20; j < x+20; j++) 
				{
					aux=(int)((ip.getPixelValue(j, i) - min) * 255.0 / (max - min));					
					hist[aux]++;
					//System.out.println(i+","+j+" aux:"+aux+" hist:"+hist[aux]);
				}
			}				
	
			for(i = 254; i >= 0; i--)
				hist[i] += hist[i + 1];
	
			thold = 0;
			while(hist[255 - thold] / hist[0] < percentile)
			{
				thold++;	
				if(thold > 255)
					break;				
			}
			thold = 255 - thold + 1;
			return ((float)(thold / 255.0) * (max - min) + min);		
		}
		else
		{
			return threshold;
		}
	}
	
	/**
	 * Dilates a copy of a given ImageProcessor with a pre calculated <code>mask</code>.
	 * Adapted as is from Ingo Oppermann implementation
	 * @param ip ImageProcessor to do the dilation with
	 * @return the dilated copy of the given <code>ImageProcessor</code> 
	 * @see ParticleTracker_#mask
	 */
	private static ImageProcessor dilateGeneric(ImageProcessor ip)
	{		
		float[] input= (float[])ip.getPixels();
		ImageProcessor dilated_ip = ip.duplicate();
		float[] dilated= (float[])dilated_ip.getPixels();
		
		int i, j, k, l, m, x, y;
		float h;
		int kernel_width = (radius*2) + 1;
		// upper bound and lower bound
		for(j = 0; j < ip.getWidth(); j++) 
		{
			// upper bound
			for(i = 0; i < radius; i++) 
			{
				h = input[BMaths.coord(i, j, ip.getWidth())];
				for(k = -radius; k <= radius; k++) 
				{
					if((i + k) < 0)
						continue;
					else
						x = i + k;
					for(l = -radius; l <= radius; l++) 
					{
						if(mask[BMaths.coord(k + radius, l + radius, kernel_width)] == 0)
							continue;
						if((j + l) < 0 || (j + l) >= ip.getWidth())
							continue;
						else
							y = j + l;
						if(input[BMaths.coord(x, y, ip.getWidth())] > h)
							h = input[BMaths.coord(x, y, ip.getWidth())];
					}
				}
				dilated[BMaths.coord(i, j, ip.getWidth())] = h;
			}

			// lower bound
			for(i = (ip.getHeight()- radius); i < ip.getHeight(); i++) {
				h = input[BMaths.coord(i, j, ip.getWidth())];
				for(k = -radius; k <= radius; k++) {
					if((i + k) >= ip.getHeight())
						continue;
					else
						x = i + k;
					for(l = -radius; l <= radius; l++) {
						if(mask[BMaths.coord(k + radius, l + radius, kernel_width)] == 0)
							continue;
						if((j + l) < 0 || (j + l) >= ip.getWidth())
							continue;
						else
							y = j + l;
						if(input[BMaths.coord(x, y, ip.getWidth())] > h)
							h = input[BMaths.coord(x, y, ip.getWidth())];
					}
				}
				dilated[BMaths.coord(i, j, ip.getWidth())] = h;
			}
		}
		// left bound and right bound
		for(i = radius; i < (ip.getHeight()- radius); i++) {
			// left bound
			for(j = 0; j < radius; j++) {
				h = input[BMaths.coord(i, j, ip.getWidth())];
				for(k = -radius; k <= radius; k++) {
					x = i + k;
					for(l = -radius; l <= radius; l++) {
						if(mask[BMaths.coord(k + radius, l + radius, kernel_width)] == 0)
							continue;
						if((j + l) < 0)
							continue;
						else
							y = j + l;
						if(input[BMaths.coord(x, y, ip.getWidth())] > h)
							h = input[BMaths.coord(x, y, ip.getWidth())];
					}
				}
				dilated[BMaths.coord(i, j, ip.getWidth())] = h;
			}
			// right bound
			for(j = (ip.getWidth() - radius); j < ip.getWidth(); j++) {
				h = input[BMaths.coord(i, j, ip.getWidth())];
				for(k = -radius; k <= radius; k++) {
					x = i + k;
					for(l = -radius; l <= radius; l++) {
						if(mask[BMaths.coord(k + radius, l + radius, kernel_width)] == 0)
							continue;
						if((j + l) >= ip.getWidth())
							continue;
						else
							y = j + l;
						if(input[BMaths.coord(x, y, ip.getWidth())] > h)
							h = input[BMaths.coord(x, y, ip.getWidth())];
					}
				}
				dilated[BMaths.coord(i, j, ip.getWidth())] = h;
			}
		}

		// the interior
		for(i = radius; i < (ip.getHeight() - radius); i++) {
			for(j = radius; j < (ip.getWidth() - radius); j++) {

				k = BMaths.coord(i - radius, j - radius, ip.getWidth());
				h = input[k];

				for(l = 0; l < kernel_width; l++) {
					for(m = 0; m < kernel_width; m++) {
						if(mask[BMaths.coord(l, m, kernel_width)] == 0)
							continue;

						if(input[k + m] > h)
							h = input[k + m];
					}
					k += ip.getWidth();
				}
				dilated[BMaths.coord(i, j, ip.getWidth())] = h;
			}
		}
		return dilated_ip;
	}
	
	/**
	 * Generates a "ready to print" StringBuffer with all the particles initial
	 * and refined positions BEFORE discrimination in this frame.
	 * <br>sets <code>info_before_discrimination</code> to hold this info
	 * @see #info_before_discrimination
	 */
	/*private static String generateFrameInfoBeforeDiscrimination() 
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(6);
		nf.setMinimumFractionDigits(6);
		
		// I work with StringBuffer since its faster than String
		StringBuffer info = new StringBuffer("% Frame ");
		info.append(this.frame_number);
		info.append(":\n");
		info.append("%\t");
		info.append(this.particles_number);
		info.append(" particles found\n");
		info.append("%\tDetected particle positions:\n");
		for (int i = 0; i<this.particles.length; i++) {
			info.append("%\t\t");
			info.append(nf.format(this.particles[i].original_x));
			info.append(" ");
			info.append(nf.format(this.particles[i].original_y));
			info.append("\n");
		}
		info.append("%\tParticles after position refinement:\n");
		for (int i = 0; i<this.particles.length; i++) {
			info.append("%\t\t");
			info.append(nf.format(this.particles[i].x));
			info.append(" ");
			info.append(nf.format(this.particles[i].y));
			info.append("\n");
		}
		return info;
	}*/
	
		
    /**
     * Generates the dilation mask
     * <code>mask</code> is a var of class ParticleTracker_ and its modified internally here
     * Adapted from Ingo Oppermann implementation
     * @param mask_radius the radius of the mask (user defined)
     */
    private static int[] generateMask() 
    {
    	int mask_radius = radius;
    	
    	int width = (2 * mask_radius) + 1;
    	int[] mask = new int[width*width];

    	for(int i = -mask_radius; i <= mask_radius; i++) 
    	{
    		for(int j = -mask_radius; j <= mask_radius; j++) 
    		{
    			int index = BMaths.coord(i + mask_radius, j + mask_radius, width);
    			if((i * i) + (j * j) <= mask_radius * mask_radius)
    				mask[index] = 1;
    			else
    				mask[index] = 0;
    			
    		}
    	}
    	return mask;
    }
    
    /**
     * Generates the Convolution Kernel as described in the Image Restoration 
     * part of the original algorithm 
     * <code>kernel</code> is a var of class ParticleTracker_ and its modified internally here
     * @param kernel_radius (the radius of the kernel (user defined))
     */
    public static float[] makeKernel()
    {
    	int kernel_radius = radius;
    	int lambda_n = 1;		
    	int kernel_width = (kernel_radius * 2) + 1;		
		float[] kernel = new float[kernel_width*kernel_width];		
		double b = calculateB(kernel_radius, lambda_n);
		double norm_cons = calculateNormalizationConstant(b, kernel_radius, lambda_n);
		
//		COORD(a, b, c)	(((a) * (c)) + (b));
		for (int i = -kernel_radius; i<=kernel_radius; i++)
		{
			for (int j = -kernel_radius; j<=kernel_radius; j++){
				int index = (i + kernel_radius)*kernel_width + j + kernel_radius;
				kernel[index]= (float)((1.0/b)* Math.exp(-((i * i + j * j)/(4.0*lambda_n*lambda_n))));				
				kernel[index]= kernel[index] - (float)(1.0/(kernel_width * kernel_width));
				kernel[index]= (float) ((double)kernel[index] / norm_cons);
			}
		}
		return kernel;
	}
    /** Auxiliary function for the kernel generation
	 * @param kernel_radius
	 * @param lambda
	 * @return the calculated B parameter
	 */
	private static double calculateB(int kernel_radius, int lambda){
		double b = 0.0;
		for (int i=-(kernel_radius); i<=kernel_radius; i++) {
			b = b + Math.exp(-((i * i)/(4.0 * (lambda * lambda))));
		}
		b = b * b;
		return b;
	}
	
	/**
	 * Auxiliary function for the kernel generation
	 * @param b
	 * @param kernel_radius
	 * @param lambda
	 * @return the calculated normalization constant
	 */
	private static double calculateNormalizationConstant(double b, int kernel_radius, int lambda){
		double constant = 0.0;
		int kernel_width = (kernel_radius * 2) + 1;
		for (int i=-(kernel_radius); i<=kernel_radius; i++) {
			constant = constant + Math.exp(-((double)(i * i)/(2.0*(lambda * lambda)))); 
		}		
		constant = ((constant * constant) / b) - (b/(double)(kernel_width * kernel_width));		
		return constant;
	}


}
