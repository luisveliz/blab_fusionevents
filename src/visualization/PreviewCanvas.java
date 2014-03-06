package visualization;


import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import data.Particle;
import data.Frame;



public class PreviewCanvas extends ImageCanvas {
    
	private static final long serialVersionUID = 1L;
	Frame preview_frame;
	//double magnification = 1.0;
	int particle_radius;
	private Boolean showPixels;
	
	/**
	 * Constructor.
     * <br>Creates an instance of PreviewCanvas from a given <code>ImagePlus</code>
     * <br>Displays the detected particles from the given <code>MyFrame</code>
	 * @param aimp - the given image plus on which the detected particles are displayed
	 * @param preview_f - the <code>MyFrame</code> with the detected particles to display
	 * @param mag - the magnification factor of the <code>ImagePlus</code> relative to the initial
	 */
	public PreviewCanvas(ImagePlus aimp, Frame preview_f, double mag, int particle_radius) {
		super(aimp);
		this.preview_frame = preview_f;
		this.magnification = mag;
		this.particle_radius = particle_radius;
		this.showPixels = false;
	}
	
	/**
	 * Overloaded Constructor.
     * <br>Creates an instance of PreviewCanvas from a given <code>ImagePlus</code>
     * <br>Displays the detected particles from the given <code>MyFrame</code>
     * <br> sets the magnification factor to 1
	 * @param aimp
	 * @param preview_f
	 */
	public PreviewCanvas(ImagePlus aimp, Frame preview_f) {
		this(aimp, preview_f, 1,1);
	}
	
	public void update(Graphics g)
	{	
		Graphics offgc;
		
		Image offscreen = null;
		Dimension d = new Dimension(this.getWidth(), this.getHeight());
	
		// create the offscreen buffer and associated Graphics
		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();
		// clear the exposed area
		offgc.setColor(getBackground());
		offgc.fillRect(0, 0, d.width, d.height);
		offgc.setColor(getForeground());
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(offscreen, 0, 0, this);
    }
	
	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {            
		super.paint(g);
		circleParticles(g);
	}
	
	/**
	 * Inner class method
	 * <br> Invoked from the <code>paint</code> overwritten method
	 * <br> draws a dot and circles the detected particle directly of the given <code>Graphics</code>
	 * @param g
	 */
	private void circleParticles(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int x,y;
		double diameter = 2*particle_radius*this.magnification-1;
		//double diameter = 2*particle_radius*this.magnification;
		Shape circle;
		Particle[] detected = this.preview_frame.getParticles();
		if (detected == null || g == null) return;

		// the preview display color is set the red
		g2D.setColor(Color.RED);
		
		IJ.log("x:"+detected[0].y+" y:"+detected[0].x+" radius:"+particle_radius+" pixels:"+detected[0].n_pixels_inside);
		IJ.log("x:"+this.screenXD(detected[0].y)+" y:"+this.screenYD(detected[0].x)+" diameter:"+diameter);
		IJ.log("mag:"+magnification);
		
		// go over all the detected particle 
		for (int p = 0; p<detected.length; p++) 
		{
			
			x = this.screenXD(detected[p].y);
			y = this.screenYD(detected[p].x);
			
			// draw a dot at the detected particle position (oval of hieght and windth of 0)
			// the members x, y of the Particle object are opposite to the screen X and Y axis
			// The x-axis points top-down and the y-axis is oriented left-right in the image plane.
			
			//g.drawOval(this.screenXD(detected[i].y), this.screenYD(detected[i].x), 0, 0);
			g2D.drawLine(x, y, x, y);
			//circle = new Ellipse2D.Double(this.screenXD(detected[i].y) - 0, this.screenYD(detected[i].x) - 0, 0, 0);
			//g2D.draw(circle);
			
			// circle the  the detected particle position according to the set radius
			//g.drawOval(this.screenXD(detected[i].y-particle_radius/1.0),this.screenYD(detected[i].x-particle_radius/1.0), 
					//2*particle_radius*this.magnification-1, 2*particle_radius*this.magnification-1);
			circle = new Ellipse2D.Double(x-diameter*0.5, y-diameter*0.5, diameter, diameter);
			
			//g2D.drawRect((int) (x-diameter*0.5),(int) (y-diameter*0.5), (int)diameter, (int)diameter);
			if(showPixels)
			{
				int x_pixel;
				int y_pixel;
				/*g2D.setColor(Color.BLUE);
				for(int i = (int) (Math.round(-diameter*0.5)-magnification); i<Math.round(diameter*0.5)+magnification; i+=magnification)
	    		{
					for(int j = (int) (Math.round(-diameter*0.5)-magnification); j<Math.round(diameter*0.5)+magnification; j+=magnification)
	    			{
						
	    				x_pixel = (int) (Math.round(this.screenXD(detected[p].y))+j);
	    				x_pixel -=x_pixel%magnification; 
	    				y_pixel = (int) (Math.round(this.screenYD(detected[p].x))+i);
	    				y_pixel -=y_pixel%magnification;
	    				//IJ.log("InsideRow "+i+" x_pixel:"+x_pixel+" y_pixel:"+y_pixel );
	    				//if(circle.contains((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2)))
	    					//g2D.setColor(Color.GREEN);
	    				
	    				g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	    				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    			}    			
	    		}*/
				
				/*g2D.setColor(Color.GREEN);
				for(int i = (int) (Math.round(-diameter*0.5)-magnification); i<Math.round(diameter*0.5)+magnification; i+=magnification)
	    		{
					for(int j = (int) (Math.round(-diameter*0.5)-magnification); j<Math.round(diameter*0.5)+magnification; j+=magnification)
	    			{
						
	    				x_pixel = (int) (Math.round(this.screenXD(detected[p].y))+j);
	    				x_pixel -=x_pixel%magnification; 
	    				y_pixel = (int) (Math.round(this.screenYD(detected[p].x))+i);
	    				y_pixel -=y_pixel%magnification;
	    				if(circle.contains((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2)))
	    				{
	    					g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	        				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    				}
	    			}    			
	    		}*/
				int pixel_count=1;
				int value;
				int x_real;
				int y_real;
				/*
				g2D.setColor(Color.BLUE);
	    		for(int i = -particle_radius; i<=particle_radius; i++)
	    		{
	    			for(int j = -particle_radius; j<=particle_radius; j++)
	    			{
	    				x_real = (int)detected[p].y+j;
	    				y_real = (int)detected[p].x+i;
	    				value = (int) preview_frame.getOriginal_ip().getPixel(x_real, y_real);
	    				//IJ.log("pixel:"+pixel_count+" x:"+x_real+" y:"+y_real+" value:"+value);
	    				x_pixel = (int) (Math.round(this.screenXD(x_real)));
	    				x_pixel -=x_pixel%magnification; 
	    				y_pixel = (int) (Math.round(this.screenXD(y_real)));
	    				y_pixel -=y_pixel%magnification;
	    				//IJ.log("InsideRow "+i+" x_pixel:"+x_pixel+" y_pixel:"+y_pixel );
	    				//if(circle.contains((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2)))
	    					//g2D.setColor(Color.GREEN);
	    				
	    				g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	    				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    				pixel_count++;
	    			}    			
	    		}
				
				g2D.setColor(Color.GREEN);
				for(int i = -particle_radius; i<=particle_radius; i++)
	    		{
	    			for(int j = -particle_radius; j<=particle_radius; j++)
	    			{
						
						x_real = (int)detected[p].y+j;
	    				y_real = (int)detected[p].x+i;
	    				//value = (int) preview_frame.getOriginal_ip().getPixel(x_real, y_real);
	    				IJ.log("GREEN pixel:"+pixel_count+" x:"+x_real+" y:"+y_real);
	    				x_pixel = (int) (Math.round(this.screenXD(x_real)));
	    				x_pixel -= x_pixel%magnification; 
	    				y_pixel = (int) (Math.round(this.screenXD(y_real)));
	    				y_pixel -= y_pixel%magnification;
	    				if(circle.contains((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2)))
	    				{
	    					g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	        				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    				}
	    			}    			
	    		}*/
				g2D.setColor(Color.BLUE);
				for(int i = 0; i<detected[p].evaluated.length; i++)
	    		{
					x_real = detected[p].evaluated[i][0];
					y_real = detected[p].evaluated[i][1];
					x_pixel = (int) (Math.round(this.screenXD(x_real)));
					x_pixel -= x_pixel%magnification; 
					y_pixel = (int) (Math.round(this.screenXD(y_real)));
					y_pixel -= y_pixel%magnification;
					
					g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	   				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    		}
				
				g2D.setColor(Color.GREEN);
				for(int i = 0; i<detected[p].inside.length; i++)
	    		{
					x_real = detected[p].inside[i][0];
					y_real = detected[p].inside[i][1];
					x_pixel = (int) (Math.round(this.screenXD(x_real)));
					x_pixel -= x_pixel%magnification; 
					y_pixel = (int) (Math.round(this.screenXD(y_real)));
					y_pixel -= y_pixel%magnification;
					
					g2D.drawRect(x_pixel, y_pixel, (int)magnification, (int)magnification);
	   				g2D.drawLine((int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2), (int)Math.round(x_pixel+magnification/2), (int)Math.round(y_pixel+magnification/2));
	    		}
			}
			g2D.setColor(Color.RED);
			g2D.draw(circle);
		}
	}
	public void setFrame(Frame frame)
	{
		this.preview_frame =  frame;
	}
	public void showPixels(Boolean showPixels)
	{
		this.showPixels = showPixels;
		repaint();
	}
	/*public void setMagnification(int magnification)
	{
		this.magnification = magnification;
	}*/
	public void setParticleRadius(int radius)
	{
		this.particle_radius = radius;
	}
}
