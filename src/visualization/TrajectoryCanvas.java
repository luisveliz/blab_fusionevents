package visualization;


import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Roi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import data.Trajectory;



/**
 * Defines an overlay Canvas for a given <code>ImagePlus</code> on which the non 
 * filtered found trajectories are displayed for further displaying and analysis options
 */
public class TrajectoryCanvas extends ImageCanvas 
{

	Trajectory[] trajs;
	int trajIndexSelected;
	Roi mouse_selection_area[];
	Roi focus_area[];
	int lastFrame;
	boolean showAllTrajs;
    
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * <br>Creates an instance of TrajectoryCanvas from a given <code>ImagePlus</code>
	 * and <code>ImageCanvas</code>
	 * <br>Displays the detected particles from the given <code>MyFrame</code>
	 * @param aimp
	 */
//	public TrajectoryCanvas(ImagePlus aimp, TrajectoryImage[] trajs) 
	public TrajectoryCanvas(ImagePlus aimp, Trajectory[] trajs)
	{		
		super(aimp);
		
		this.trajs=trajs;
		trajIndexSelected = 1;
		setMouseSelectionAreas();
		setFocusAreas();
		showAllTrajs = true;
	}
	private void setMouseSelectionAreas()
	{
		mouse_selection_area = new Roi[trajs.length];
		for(int i=0;i<trajs.length;i++)
		{
			/* find the min and max values of the x and y positions */
			double min_x = trajs[i].getParticles()[0].getX();
			double min_y = trajs[i].getParticles()[0].getY(); 
			double max_x = trajs[i].getParticles()[0].getX();
			double max_y = trajs[i].getParticles()[0].getY();
			for (int p = 0; p<trajs[i].getParticles().length; p++)
			{
				min_x = Math.min(trajs[i].getParticles()[p].getX(), min_x);
				min_y = Math.min(trajs[i].getParticles()[p].getY(), min_y);
				max_x = Math.max(trajs[i].getParticles()[p].getX(), max_x);
				max_y = Math.max(trajs[i].getParticles()[p].getY(), max_y);
			}
			
			/* set the focus area x, y , height, width to give focus area bigger by 1 pixel 
			 * then minimal rectangle surroundings this trajectory */ 
			
			// X and Y coordinates are not in the usual graph coordinates sense but in the image sense;
			// (0,0) is the upper left corner; x is vertical top to bottom, y is horizontal left to right
			int focus_x = (int)min_y - 1;
			int focus_y = (int)min_x - 1;
			int focus_height = (int)max_x - focus_y + 1;
			int focus_width = (int)max_y - focus_x + 1;
			mouse_selection_area[i] = new Roi(focus_x, focus_y, focus_width, focus_height);					
		}
	}
	public Roi getMouseSelectionArea(int index)
	{
		return mouse_selection_area[index];
	}
	private void setFocusAreas()
	{
		System.out.println("seteando focus areas");
		focus_area = new Roi[trajs.length];
		for(int i=0; i< trajs.length;i++)
		{		
			/* find the min and max values of the x and y positions */
			double min_x = trajs[i].getParticles()[0].getX();
			double min_y = trajs[i].getParticles()[0].getY(); 
			double max_x = trajs[i].getParticles()[0].getX();
			double max_y = trajs[i].getParticles()[0].getY();	
			for (int p = 0; p<trajs[i].getParticles().length; p++)
			{
				min_x = Math.min(trajs[i].getParticles()[p].getX(), min_x);
				min_y = Math.min(trajs[i].getParticles()[p].getY(), min_y);
				max_x = Math.max(trajs[i].getParticles()[p].getX(), max_x);
				max_y = Math.max(trajs[i].getParticles()[p].getY(), max_y);
			}
			
			/* set the focus area x, y , height, width to give focus area bigger by 8 pixels 
			 * then minimal rectangle surroundings this trajectory */ 
			
			// X and Y coordinates are not in the usual graph coordinates sense but in the image sense;
			// (0,0) is the upper left corner; x is vertical top to bottom, y is horizontal left to right			
			int focus_x = Math.max((int)min_y - 8, 0);
			int focus_y = Math.max((int)min_x - 8, 0);
			int focus_height = (int)max_x - focus_y + 8;
			int focus_width = (int)max_y - focus_x + 8;			
			// make sure that the -8 or +8 didn’t create an ROI with bounds outside of the window
			//if (focus_x + focus_width > original_imp.getWidth()) 
			if (focus_x + focus_width > this.imageWidth)
			{
				focus_width = this.imageWidth - focus_x;
			}
			if (focus_y + focus_height > this.imageHeight) 
			{
				focus_height = this.imageHeight - focus_y;
			}
			focus_area[i] = new Roi(focus_x, focus_y, focus_width, focus_height);
		}
	}
	public Roi getFocusArea(int index)
	{
		return focus_area[index];
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
		drawTrajectories((Graphics2D)g); 
	}
	
	/**
	 * Draws each of the trajectories in <code>all_traj</code>
	 * on this Canvas according to each trajectorys <code>to_display</code> value
	 * @param g2D
	 * @see TrajectoryImage#drawStatic(Graphics, ImageCanvas)
	 */
	private void drawTrajectories(Graphics2D g2D) 
	{
		if (g2D == null) return;
		
		if(showAllTrajs)
		{
			for (int i=0;i<trajs.length;i++) 
			{	
				// if the trajectory to_display value is true
				if (trajs[i].isToDisplay()) 
					drawStatic(g2D, trajs[i],Color.YELLOW);
				if (trajs[i].getId()==trajIndexSelected)
					drawStatic(g2D, trajs[i], Color.BLUE);
			}
		}
//		drawStatic(g2D, trajs[trajIndexSelected-1], Color.BLUE);
	}
//	private void drawStatic(Graphics2D g2D, TrajectoryImage traj) 
	private void drawStatic(Graphics2D g2D, Trajectory traj, Color color)
	{
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i<traj.getParticles().length-1 && (traj.getMovieFrame()+traj.getFrame(i))<=lastFrame ; i++)
		{
			/*if (traj.getFrame(i+1) - traj.getFrame(i) > 1) 	    			   
				g2D.setColor(Color.RED);
			else*/
			g2D.setColor(color);
				
			g2D.drawLine(this.screenXD(traj.getParticle(i).y), 
					this.screenYD(traj.getParticle(i).x), 
					this.screenXD(traj.getParticle(i+1).y), 
					this.screenYD(traj.getParticle(i+1).x));
		}
	}
//	public TrajectoryImage[] getTrajs() 
	public Trajectory[] getTrajs()
	{
		return trajs;
	}
//	public void setTrajs(TrajectoryImage[] trajs)
	public void setTrajs(Trajectory[] trajs)
	{
		this.trajs = trajs;
		this.repaint();
	}
	public void setLastFrame(int lastFrame)
	{
		this.lastFrame = lastFrame;		
		this.repaint();
	}
	
	public void showAllTrajs()
	{
		this.showAllTrajs = !this.showAllTrajs;
	}
	public void setSelectedTraj(int index)
	{
		this.trajIndexSelected = index;
		repaint();
	}
}
