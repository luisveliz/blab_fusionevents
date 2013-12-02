package particleTracker;

import bTools.BNF;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Plot;
import ij.gui.StackWindow;
import ij.process.StackStatistics;
import main.Thinker;
import visualization.PreviewCanvas;
import visualization.TrajectoryCanvas;
import visualization.TrajectoryStackWindow;
import data.Frame;
import data.Metrics;
import data.Movie;
import data.TrajSet;
import data.Trajectory;

public class ParticleTracker 
{
	
	
	Thinker thinker;
	GUIParticleTracker gui;
	
	private Movie movie;
//	private ImagePlus imp;
	
	//Parameters
	int n_frame; 
	
	//Cosas del stack
	ImageStack stack;	
	StackStatistics stack_stats;

	//Windows and Canvas
	public StackWindow stack_window;
	PreviewCanvas pc;
	
	public TrajectoryStackWindow trajectories_stack_window;
	TrajectoryCanvas tc;
	

	ImagePlus impWithTrajs;
	StackWindow stack_window_forImpWithTrajs;
	
	
	public ParticleTracker(Thinker thinker, ImagePlus imp)
	{
		this.thinker = thinker;
		movie = new Movie(imp);
//		this.imp = imp;
		
		// initialize ImageStack stack
		stack = imp.getStack();
		
		// get global minimum and maximum
		stack_stats = new StackStatistics(imp);
		
		n_frame = 1;
		ParticleDetector.radius = 2; 
		ParticleDetector.cutoff = 0;
		ParticleDetector.percentile = 0.001f;
		ParticleLinker.linkRange = 2;
		ParticleLinker.displacement = 4;
		
		gui = new GUIParticleTracker(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
		gui.jSpinner_Detection_Nframe_setMaxValue(movie.getLenght());
	}
	public void close()
	{
		thinker = null;
		movie = null;
		//imp = null;
		// initialize ImageStack stack
		stack = null;
		
		// get global minimum and maximum
		stack_stats = null;
		
		gui.dispose();
		gui = null;
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	
	
	public void jSlider_Radius_stateChanged()
	{
		ParticleDetector.radius = gui.jSlider_Radius_getValue();
		gui.jTextField_Radius_setText(String.valueOf(ParticleDetector.radius));
	}
	public void jSlider_Cutoff_stateChanged()
	{
		ParticleDetector.cutoff = gui.jSlider_Cutoff_getValue()*0.1;
		gui.jTextField_Cutoff_setText(String.valueOf(ParticleDetector.cutoff));
	}
	public void jSlider_Percentile_stateChanged()
	{
		ParticleDetector.percentile = gui.jSlider_Percentile_getValue()*0.0001f;
		gui.jTextField_Percentile_setText(String.valueOf(BNF.sF(ParticleDetector.percentile*100))+"%");
	}
	public void jSlider_Displacement_stateChanged()
	{
		ParticleLinker.displacement = gui.jSlider_Displacement_getValue();
		gui.jTextField_Displacement_setText(String.valueOf(ParticleLinker.displacement));
	}
	public void jSlider_LinkRange_stateChanged()
	{
		ParticleLinker.linkRange = gui.jSlider_LinkRange_getValue();
		gui.jTextField_LinkRange_setText(String.valueOf(ParticleLinker.linkRange));
	}
	
	
	
	
	
	public void jButton_Internal_clicked()
	{
		StackWindow sw = new StackWindow(movie.getRestoredImp());
	}
	
	
	/**
	 * 
	 */
	public void jSpinner_Detection_Nframe_stateChanged()
	{
		//TODO crear mï¿½todo para actualizar el preview canvas y llamarlo directamente
		n_frame = gui.jSpinner_Detection_Nframe_getValue();
		//this.particleTracker.updateParams(spt_gui.getUserParticleDetectionParams());
		ImagePlus imp = movie.getImp();
		if (imp == null) return;
		// the stack of the original loaded image (it can be 1 frame)
		stack = imp.getStack();
		
		imp.setSlice(n_frame);
		
		//Frame preview_frame = new Frame(stack.getProcessor(preview_slice), 1);
		Frame preview_frame = new Frame(stack.getProcessor(n_frame), n_frame);
				
		// create a new MyFrame from the current_slice in the stack
		ParticleDetector.detectParticles(preview_frame,(float)stack_stats.max,(float)stack_stats.min);
		
		// save the current magnification factor of the current image window
		double magnification = imp.getWindow().getCanvas().getMagnification();
		IJ.log("Magggggggggggggggggg:"+magnification);
		
		if(stack_window==null)
		{
			// generate the previewCanvas - while generating it the drawing will be done 
			pc = new PreviewCanvas(imp, preview_frame, magnification, ParticleDetector.radius);		
			
			// display the image and canvas in a stackWindowtu 
			stack_window = new StackWindow(imp, pc);
		}
		else
		{
			pc.setFrame(preview_frame);
			pc.setMagnification(magnification);
			pc.setParticleRadius(ParticleDetector.radius);
			pc.repaint();
			pc.getImage().setSlice(n_frame);
		}
		//}

		//ImagePlus restored_imp = new ImagePlus("Restored_fp",preview_frame.getRestored_fp());
		//restored_imp.show();
		
		// magnify the canvas to match the original image magnification
		/*while (stack_window.getCanvas().getMagnification() < magnification) {
			pc.zoomIn(0,0);
		}*/
		thinker.show_FrameInfo(preview_frame);
		//spt_gui.addRowInTrajectoryTableModel(new Object[]{1,"blabla","blabla"});
	}
	
	public void jCheckBox_ShowPixels_stateChanged()
	{
		if(pc!=null)
			pc.showPixels(gui.jCheckBox_showPixels_isSelected());
	}
	
	/**
	 * 
	 */
	

	public void jButton_Detection_clicked()
	{		
		IJ.log("Detecting particles");			
		gui.jButton_Detection_setEnabled(false);
		
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() 
			{
				try
				{
					//movie = new SPTMovie(original_imp);
					System.gc();
					
					//System.out.println("Procesando pelicula1");
					//Detecting particles					
					for(int i=0;i<movie.getLenght();i++)
					{
//						IJ.log("Procesando frame: "+(i+1));
						gui.jTextField_setText("Status: Detecting particles in frame "+(i+1));
						ParticleDetector.detectParticles(movie.getFrame(i), (float)stack_stats.max, (float)stack_stats.min);
						gui.jProgressBar_Detection_ProcessMovie_setValue(100*i/movie.getLenght());
//			        	thinker.show_FrameInfo(movie.getFrame(i));
					}
					gui.jProgressBar_Detection_ProcessMovie_setValue(0);
					
					
					/*-----------Show Particles vs Frame-------------------*/
					double[] frames = new double[movie.getLenght()];
					double[] nparticles = new double[movie.getLenght()];
					for(int i=0;i<movie.getLenght();i++)
					{
						frames[i] = i+1;
						nparticles[i] = movie.getFrame(i).getReal_particles_number();
					}		
					
					Plot plot =  new Plot("Particles detection", "Frame", "N° of Particles", frames, nparticles);
					plot.show();
					
					/*-------------------------------*/
					//TODO show particles vs frame
					
//					/* link the particles found */					
//					IJ.log("Linking Particles");
//					ParticleLinker.linkParticles(movie);
//					IJ.freeMemory();
//					gui.jProgressBar_Detection_ProcessMovie_setValue(80);
//							
//					/* generate trajectories */		 
//					IJ.log("Generating Trajectories");
//					thinker.addTrajSet(new TrajSet(TrajSet.IMAGE,movie.getDirectory(),movie.getName(),ParticleLinker.generateTrajectories(movie),new Metrics()));
//					thinker.updateMain();				
//					thinker.updateAnalysis();
//					
//					trajectories_stack_window = null;
//					updateTrajectoriesStackWindow();
//					stack_window = null; 
//					
//					gui.jButton_Process_setEnabled(true);
//					IJ.freeMemory();			
//					gui.jProgressBar_Detection_ProcessMovie_setValue(100);
//					gui.jProgressBar_Detection_ProcessMovie_setValue(0);
					
					gui.jButton_Detection_setEnabled(true);
//					gui.setVisible(false);
					
				}
				catch(Exception e)
				{
					
				}
			}
		};
		Thread hilo = new Thread (runnable);
		hilo.start();	
	}
	public void jButton_Linking_clicked()
	{		
		IJ.log("Procesando pelicula");			
		gui.jButton_Linking_setEnabled(false);
		
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() 
			{
				try
				{
					//movie = new SPTMovie(original_imp);
					System.gc();
					
					/* link the particles found */					
					IJ.log("Linking Particles");
					//ParticleTracker.linkParticles(spt_movie, spt_gui.getUserParticleLinkingParams());
					ParticleLinker.linkParticles(movie);
					IJ.freeMemory();
					gui.jProgressBar_Detection_ProcessMovie_setValue(80);
							
					/* generate trajectories */		 
					IJ.log("Generating Trajectories");
					thinker.addTrajSet(new TrajSet(TrajSet.IMAGE,movie.getDirectory(),movie.getName(),ParticleLinker.generateTrajectories(movie),new Metrics()));
					thinker.updateMain();				
					thinker.updateAnalysis();
					
					trajectories_stack_window = null;
					updateTrajectoriesStackWindow();
					stack_window = null; 
					
					gui.jButton_Process_setEnabled(true);
					IJ.freeMemory();			
					gui.jProgressBar_Detection_ProcessMovie_setValue(100);
					gui.jProgressBar_Detection_ProcessMovie_setValue(0);
					
					gui.jButton_Linking_setEnabled(true);
					gui.setVisible(false);
					
				}
				catch(Exception e)
				{
					
				}
			}
		};
		Thread hilo = new Thread (runnable);
		hilo.start();	
	}
	
	
	
	public void setTrajectorySelectedInTrajWindow(int index)
	{
		if(!trajectories_stack_window.isClosed())
		{
			tc.setSelectedTraj(index);
		}
	}
	
	public void trajectoryStackWindow_trajClicked(int chosenTraj)
	{
		/*
		trajTableLastClicked = true;
		selectedRow = chosenTraj;
		this.movie.getTrajectory(selectedRow).setUsar(gui.trajTableModel_selectedRow_isUsar());
		selectedTraj = this.movie.getTrajectory(selectedRow);
		
		showTrajectoryInfo(selectedTraj);		
		updateGUIControls();
		updateMSDChart();
		updateLogMSDChart();
		updateTrajectoryPlot();
		updateSubTrajectoryTableModel();
		setTrajectorySelectedInTrajWindow(selectedTraj.getSerialNumber());
		lwjglObject.setTrayectoria(this.movie.getTrajectories()[gui.getTrajectoriesTable_SelectedRow()].get3dTrajectory());
		*/
	}
	
	public void updateTrajectoriesStackWindow()
	{
		//System.out.println("updating trajectory stack window");
		//set trajectories to display
		/*TODO movie.setTrajectoriesToDisplay(gui.jSpinner_TrajectoryLength_getValue(),
									   gui.jCheckBox_Corralled_isSelected(),
									   gui.jCheckBox_Anomalous_isSelected(),
									   gui.jCheckBox_Normal_isSelected(),
									   gui.jCheckBox_Directed_isSelected(),
									   gui.jCheckBox_NotDefined_isSelected());*/
		
		ImagePlus imp = movie.getImp();
		double magnification = imp.getWindow().getCanvas().getMagnification();
		//System.out.println("magnification original imp:"+magnification);
		if(trajectories_stack_window==null)
		{
			// Create a new canvas based on the image - the canvas is the view
			// The trajectories are drawn on this canvas when itï¿½s constructed and not on the image
			// Canvas is an overlay window on top of the ImagePlus
			//tc = new TrajectoryCanvas(duplicated_imp, this.movie.getTrajectories());
//			tc = new TrajectoryCanvas(imp, this.movie.getTrajectories());
			tc = new TrajectoryCanvas(imp, thinker.getFilteredTrajs());
			
			
									
			// Create a new window to hold the image and canvas
			trajectories_stack_window = new TrajectoryStackWindow(imp, tc, this);
		}
		else
		{
			tc.setTrajs(thinker.getFilteredTrajs());
		}
		
		// zoom the window until its magnification will reach the set maparticleTracker.getMovie()cation magnification
		while (trajectories_stack_window.getCanvas().getMagnification() < magnification) {
			tc.zoomIn(0,0);
		}	
	}
	public void setSlice(int n)
	{
		movie.getImp().setSlice(n);
		tc.setLastFrame(n);
	}
	
	public Movie getMovie() {
		return movie;
	}
}
