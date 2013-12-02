package visualization;


import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.ImageCanvas;
import ij.gui.StackWindow;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import particleTracker.ParticleTracker;
import data.Trajectory;

/**
 * Defines a window to display trajectories according to their <code>to_display</code> status.
 * The trajectories displayed on this window are drawn an given Canvas
 * <br>In the window the user can select a specific Trajectory, a region of interest (ROI)
 * and filter trajectories by length.
 * <br>User requests regarding filtering will be listened to and engaged from the <code>actionPerformed</code>
 * method implemented here. 
 * <br>User selections of trajectories with the mouse will be listened to and engaged from the
 * <code>mousePressed</code> method implemented here
 * <br>All other ImageJ window options (e.g. ROI selection, focus, animation) are inherited 
 * from the stackWindow Class
 */
public class TrajectoryStackWindow extends StackWindow implements ActionListener, MouseListener
{
    
//	private static final long serialVersionUID = 1L;
	private Button button;
	private JCheckBox all;
	
	
	private ParticleTracker particleTracker;
	TrajectoryCanvas icanvas;
   
    /**
     * Constructor.
     * <br>Creates an instance of TrajectoryStackWindow from a given <code>ImagePlus</code>
     * and <code>ImageCanvas</code> and a creates GUI panel.
     * <br>Adds this class as a <code>MouseListener</code> to the given <code>ImageCanvas</code>
     * @param aimp
     * @param icanvas
     */
    public TrajectoryStackWindow(ImagePlus aimp, ImageCanvas icanvas, ParticleTracker particleTracker) 
    {
    	super(aimp, icanvas);
    	icanvas.addMouseListener(this);
    	this.icanvas = (TrajectoryCanvas)icanvas;
    	this.particleTracker = particleTracker;
    	addPanel();
    }

    /**
     * Adds a Panel with filter options button in it to this window 
     */
    private void addPanel() 
    {
    	all = new JCheckBox("All trajs");
    	all.setVisible(true);
    	all.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
//				icanvas.showAllTrajs(all.isSelected());
				icanvas.repaint();
			}
		});
    	button = new Button("Show Trajs");
    	button.addActionListener(this);
    	add(button);
        add(all);
        pack();
//        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//        Point loc = getLocation();
//        Dimension size = getSize();
//        if (loc.y+size.height>screen.height)
//            getCanvas().zoomOut(0, 0);
     }
    
	/** 
	 * Defines the action taken upon an <code>ActionEvent</code> triggered from buttons
	 * that have class <code>TrajectoryStackWindow</code> as their action listener:
	 * <br><code>Button filter_length</code>
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
    public synchronized void actionPerformed(ActionEvent e) 
    {
        Object b = e.getSource();
        if (b==button) 
        {
        	icanvas.showAllTrajs();
			icanvas.repaint();
        }

    }
    public synchronized void adjustmentValueChanged(AdjustmentEvent e)
    {
    	super.adjustmentValueChanged(e);
    	((TrajectoryCanvas)getCanvas()).setLastFrame(this.slice);
    }

	/** 
	 * Defines the action taken upon an <code>MouseEvent</code> triggered by left-clicking 
	 * the mouse anywhere in this <code>TrajectoryStackWindow</code>
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public synchronized void mousePressed(MouseEvent e) 
	{
		IJ.showMessage("TODO!!");
		/* get the coordinates of mouse while it was clicked*/
		int x = e.getX();
		int y = e.getY();
		/* covert them to offScreen coordinates using the ImageCanvas of this window*/
		int offscreenX = this.ic.offScreenX(x);
		int offscreenY = this.ic.offScreenY(y);
		
		boolean trajectory_clicked = false;
		int min_dis = Integer.MAX_VALUE;
		//Iterator iter = all_traj.iterator();
		/* find the best Trajectory to match the mouse click*/
		//while (iter.hasNext())
		Trajectory[] trajs = ((TrajectoryCanvas)getCanvas()).getTrajs();
		int chosen_traj=0;
		for(int i=0; i< trajs.length; i++)
		{
			Trajectory curr_traj = trajs[i];				
			// only trajectories that the mouse click is within their mouse_selection_area
			// and that are not filtered (to_display == true) are considered as a candidate
			if ((((TrajectoryCanvas)getCanvas()).getMouseSelectionArea(i)).contains(offscreenX, offscreenY) && curr_traj.isToDisplay())
			{
				// we have a least 1 candidate => a trajectory will be set
				trajectory_clicked = true;
				// for each particle in a candidate trajectory, check the distance 
				// from it to the mouse click point
				for (int p = 0; p<curr_traj.getParticles().length; p++) 
				{
					int dis = ((int)curr_traj.getParticles()[p].getX() - offscreenY)*
								((int)curr_traj.getParticles()[p].getX() - offscreenY) +
								((int)curr_traj.getParticles()[p].getY() - offscreenX)*
								((int)curr_traj.getParticles()[p].getY() - offscreenX);
					// if the distance for this particle  is lower than the min distance found
					// for all trajectories until now - save this trajectory for now
					if (dis < min_dis) 
					{
						min_dis = dis;
						chosen_traj = curr_traj.getId();							
					}
				}
			}
		} 			
		
		if (trajectory_clicked) 
		{
			/* focus or mark the selected Trajectory according the the type of mouse click*/
			this.imp.killRoi();
			this.imp.updateImage();
			// show the number of the selected Trajectory on the per trajectory 
			// panel in the results window
			//results_window.per_traj_label.setText("Trajectory " + chosen_traj);
			
			if (e.getClickCount() == 2) 
			{
				// "double-click" 
				// Set the ROI to the trajectory focus_area
				//IJ.getImage().setRoi(((Trajectory)all_traj.elementAt(chosen_traj-1)).focus_area);
				IJ.getImage().setRoi(((TrajectoryCanvas)getCanvas()).getFocusArea((chosen_traj)));
				// focus on Trajectory (ROI)
				//generateTrajFocusView(chosen_traj-1, magnification_factor);
				generateTrajFocusView(chosen_traj, 1);
			}
			else 
			{
				// single-click - mark the selected trajectory by setting the ROI to the 
				// trajectory’s mouse_selection_area
				this.imp.setRoi(((TrajectoryCanvas)getCanvas()).getMouseSelectionArea(chosen_traj));
				this.particleTracker.trajectoryStackWindow_trajClicked(chosen_traj);
			}
		} else {
			chosen_traj = -1;
			//results_window.per_traj_label.setText("Trajectory (select from view)");
		}			
		
	}
	public void setTrajectorySelected(int index)
	{
		/* focus or mark the selected Trajectory according the the type of mouse click*/
		this.imp.killRoi();
		this.imp.updateImage();
		// show the number of the selected Trajectory on the per trajectory 
		// panel in the results window
		//results_window.per_traj_label.setText("Trajectory " + chosen_traj);
		
		// single-click - mark the selected trajectory by setting the ROI to the 
		// trajectory’s mouse_selection_area
		this.imp.setRoi(((TrajectoryCanvas)getCanvas()).getMouseSelectionArea(index-1));
		
	}
	
	public void generateTrajFocusView(int trajectory_index, int magnification) 
	{		
		ImageStack traj_stack;
		String new_title = "[Trajectory number " + (trajectory_index+1) + "]";
		
		// get the trajectory at the given index
		Trajectory traj = ((TrajectoryCanvas)getCanvas()).getTrajs()[trajectory_index];
		
		// set the Roito be magnified as the given trajectory predefined focus_area
		IJ.getImage().setRoi(((TrajectoryCanvas)getCanvas()).getFocusArea(trajectory_index));
		
		// Save the ID of the last active image window - the one the ROI set on
		int roi_image_id = IJ.getImage().getID();
		
		// ImageJ macro command to rescale and image - this will create a new ImagePlus (stack)
		// that will be the active window 
		IJ.run("Scale...", "x=" + magnification + " y=" + magnification +" process create title=" + new_title);
		IJ.freeMemory();
		
		// Get the new-scaled image (stack) and assign it duplicated_imp
		ImagePlus duplicated_imp = IJ.getImage();
		
		
		// get the first and last frames of the trajectory
		int first_frame = traj.getMovieFrame();
		int last_frame = first_frame + traj.getLength();
		
		// remove from the new-scaled image stack any frames not relevant to this trajectory
		ImageStack tmp = duplicated_imp.getStack();
		int passed_frames = 0;
		int removed_frames_from_start = 0;
		for (int i = 1; i <= tmp.getSize() ; i++) {			
			if (passed_frames< first_frame-5 || passed_frames>last_frame+5) {
				tmp.deleteSlice(i);	
				// when deleting slice from the stack, all following slice numbers are 
				// decreased by 1 so i is decreased by 1 as well.
				// there is no risk of infinite loop since tmp.getSize() is decreased as well
				// every time deleteSlice(i) is invoked
				i--;
			}
			if (passed_frames< first_frame-5) {
				// keep track of frames that were removed from start (prefix) of the stack
				// for the animate method later
				removed_frames_from_start++;
			}
			passed_frames++;
		}
		duplicated_imp.setStack(duplicated_imp.getTitle(), tmp);
		IJ.freeMemory();
		
		// Convert the stack to RGB so color can been drawn on it and get its ImageStac
		IJ.run("RGB Color");
		traj_stack = duplicated_imp.getStack();
		IJ.freeMemory();

		// Reset the active imageJ window to the one the trajectory was selected on - 
		// info from that window is still needed
		IJ.selectWindow(roi_image_id);
		
		// animate the trajectory 
		//traj.animate(magnification, removed_frames_from_start);
		
		// set the new window to be the active one
		IJ.selectWindow(duplicated_imp.getID());
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
		// Auto-generated method stub			
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {
		// Auto-generated method stub			
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
		// Auto-generated method stub			
	}
    
} // CustomStackWindow inner class