package main;

import event.Event;
import event.EventEvaluator;
import event.EventSet;
import event.FusionEvents;
import frequency.Histograms;
import gui.GUIAbout;
import gui.GUIMetrics;
import gui.GUIPreferences;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Plot;
import ij.gui.PlotWindow;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import info.GlobalInfo;

import bTools.BMaths;

import java.util.ArrayList;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import bTools.BNF;
import loader.FileLoader;
import overTime.OverTime;
import particleTracker.ParticleTracker;
import simulator.Simulator;
import twoStateDiffusion.HMMAnalysis;
import typeOfMotion.TypeOfMotionAnalysis;
import data.Frame;
import data.Subtrajectory;
import data.TrajSet;
import data.Trajectory;
import direction.Direction;

public class Thinker 
{
	
	Plot plot;
	
	//GUI
	public GUImain gui;
	private GUIPreferences gui_preferences;
	private GUIAbout gui_about;
	private GUIMetrics gui_metrics;
	public GlobalInfo info;
	
	//TODO Source of trajs???
	private boolean trajsFromXML;

	//Input Data Modules
	//Loaders
	private FileLoader fileLoader;
	//Tracker
	public ParticleTracker particleTracker;
	//Simulator
	private Simulator simulator;
	
	//Data
	private ArrayList<TrajSet> sets = new ArrayList<TrajSet>();
	int selectedSetIndex;
//	private TrajSet trajSet;
	
	//Analysis
	private TypeOfMotionAnalysis toma;
	private HMMAnalysis hmma;
	private OverTime overTime;
	private Histograms histograms;
	private Direction direction;
	
	private FusionEvents fusionEvents;
	
	//Status
	public boolean trajTableLastClicked=true;
	int selectedRowTraj;
	int[] rowTrajID; //stores id of trajs
	int selectedRowSubTraj;
	
	public Thinker()
	{	
		BNF.setMaximumFractionDigits(3);
		BNF.setDecimalSeparator('.');
		BNF.setExponentSeparator("^");
		//new EmergencyLoad();
		gui = new GUImain(this);
		gui.setBounds(ImageJ.getWindows()[0].getX(),ImageJ.getWindows()[0].getY()+ImageJ.getWindows()[0].getHeight(), gui.getWidth(), gui.getHeight());
		trajsFromXML = false;
//		IJ.log("It's recommendable set the metrics before load data.");
	}
	
	
	public void guiClosing()
	{
		gui.dispose();
		gui = null;
		if(gui_about != null)
		{
			gui_about.dispose();
			gui_about = null;
		}
		if(gui_metrics != null)
		{
			gui_metrics.dispose();
			gui_metrics = null;
		}
		if(info != null)
		{
			info.dispose();
			info = null;
		}
		if(overTime != null)
		{
			overTime.close();
			overTime = null;
		}
		if(fileLoader!=null)
		{
			fileLoader.close();
			fileLoader = null;
		}
		if(particleTracker != null)
		{
			particleTracker.close();
			particleTracker = null;
		}
		if(toma != null)
		{		
			toma.close();
			toma = null;
		}
		if(hmma != null)
		{
			//hmma.close();
			hmma = null;
		}
		if(simulator!=null)
		{
			simulator.close();
			simulator=null;
		}

//		sets = null;
//		data = null;
//		trajectories = null;
		System.gc();
		
		//IJ.log(IJ.freeMemory());
		
	}
	/*------------------------------------------------------------*/	
	/*------------------------------------------------------------*/
	public void jMenu_Preferences_clicked()
	{
		if(gui_preferences!=null)
			gui_preferences.setVisible(true);
		else
			gui_preferences = new GUIPreferences(this);
		gui_preferences.setLocation(gui.getX(),gui.getY());
	}

	public void jButton_SavePreferences_clicked()
	{
		BNF.setMaximumFractionDigits(gui_preferences.jSpinner_NDecimals_getValue());
		updateMain();
		updateAnalysis();
		gui_preferences.setVisible(false);
	}
	
	public void jMenu_About_BrauchiLab_clicked()
	{
		if(gui_about!=null)
			gui_about.setVisible(true);
		else
			gui_about = new GUIAbout();
		gui_about.setLocation(gui.getX(),gui.getY());
	}
	public void jMenu_LoadSave()
	{
		if(fileLoader!=null)
			fileLoader.openGUI();
		else
			fileLoader = new FileLoader(this, gui.getX(), gui.getY());
	}
	public void jMenu_Info_clicked()
	{
		if(info!=null)
			info.setVisible(true);
		else
			info = new GlobalInfo(this);
		info.setLocation(gui.getX(),gui.getY());
		info.updateGlobalInfo();
		info.updateTrajectoryInfo();
	}
	public void jMenu_SetMetrics()
	{
		if(sets.size()>0)
		{
			if(gui_metrics!=null)
				gui_metrics.setVisible(true);				
			else
				gui_metrics = new GUIMetrics(this);
			gui_metrics.setLocation(gui.getX(),gui.getY());
		}
		else
			IJ.error("You have to load some data before set metrics.");
	}
	public void jButton_SetMetrics_clicked()
	{
		int timeUnit = gui_metrics.jComboBox_TimeUnit_getSelectedIndex();
		int distanceUnit = gui_metrics.jComboBox_DistanceUnit_getSelectedIndex()+3;
		double frameStep = Double.parseDouble(gui_metrics.jTextField_FrameStep_getText());
		double distanceFactor = Double.parseDouble(gui_metrics.jTextField_DistanceFactor_getText());
		if(sets.size()>0)
			getSelectedSet().setMetrics(frameStep, distanceFactor, timeUnit, distanceUnit);

		gui_metrics.setVisible(false);
		updateXYCanvas();
		updateGUIControls();
		if(info!=null)
		{
			info.updateGlobalInfo();
			info.updateTrajectoryInfo();
		}
		if(toma!=null)
		{
			toma.updateGUI();
			toma.updateData();
			toma.updateMSDChart();
		}
		if(overTime!=null)
			overTime.update();
		if(histograms!=null)
			histograms.updateFrequencyChart();
		
	}
	public void jMenu_ParticleTracker_clicked()
	{
		//source = IMAGE;
		if(particleTracker!=null)
			particleTracker.openGUI();
		else
			IJ.showMessage("Info", "A image must be loaded before open this plugin");
	}
	public void jMenu_SimulateTrajectories()
	{
		if(simulator!=null)
			simulator.openGUI();
		else
			simulator = new Simulator(this);
	}
	public void jMenu_TypeOfMotionAnalysis()
	{
		if(toma!=null)
			toma.openGUI();
		else
			toma = new TypeOfMotionAnalysis(this);
		toma.updateGUI();
		toma.updateData();
		toma.updateMSDChart();
	}
	public void jMenu_2StateDiffusion()
	{
		if(hmma!=null)
			hmma.openGUI();
		else
			hmma = new HMMAnalysis(this);
	}
	public void jMenu_OverTime()
	{
		if(overTime!=null)
			overTime.openGUI();
		else
			overTime = new OverTime(this);
	}
	public void jMenu_Histograms()
	{
		if(histograms!=null)
			histograms.openGUI();
		else
			histograms = new Histograms(this);
		histograms.updateFrequencyChart();
	}
	public void jMenu_Direction()
	{
		if(direction!=null)
			direction.openGUI();
		else
			direction = new Direction(this);
		direction.updateDirectionChart();
	}
	public void jMenu_FusionEvents(){
		if(fusionEvents!=null){
			fusionEvents.openGUI();
		}
		else{
			fusionEvents = new FusionEvents(this);
		}
	}
	
	/*------------------------------------------------------------*/
	public void filters_Trajectories_stateChanged()
	{
		updateCurrentSet();
		updateMain();
		updateAnalysis();
	}
	public void jTable_TrajectoriesTable_clicked()
	{
		trajTableLastClicked = true;
		if(selectedRowTraj!=gui.getTrajectoriesTable_SelectedRow())
		{
			selectedRowTraj = gui.getTrajectoriesTable_SelectedRow();
			//selectedTraj = currentFilteredTrajs[selectedRowTraj];//getSelectedSet().setSelectedTraj(selectedRow);
			updateSubTrajTable();
			//TODO tal vez esto puede mejorarse
			gui.getXYCanvas().setBeginEndSubtraj(0,0);
		}
		getSelectedTraj().setUsar(((Boolean)gui.jTable_Trajectories_getModel().getValueAt(selectedRowTraj, 0)).booleanValue());
		updateGUIControls();
		updateXYCanvas();
		if(particleTracker!=null)
		{
			if(particleTracker.getMovie()!=null)
			{
				particleTracker.setSlice(getSelectedTraj().getMovieFrame()+getSelectedTraj().getLast()-1);
				particleTracker.setTrajectorySelectedInTrajWindow(getSelectedTraj().getId());
			}
		}
		
		updateAnalysis();
		
		
		/************************************************/
		plotIntensities();
		
		
		
		/************************************************/
	}
	public void jTable_SubtrajectoriesTable_clicked()
	{				
		trajTableLastClicked = false;
		selectedRowSubTraj = gui.getSubtrajectoriesTable_SelectedRow();
		getSelectedSubtraj().setUsar(((Boolean)gui.jTable_SubTrajectories_getModel().getValueAt(selectedRowSubTraj, 0)).booleanValue());
		updateGUIControls();
		updateXYCanvas();
		updateAnalysis();
	}
	public void jButton_Manual_SubTrajEditor_Add_clicked()
	{
		int initialFrame = gui.jSpinner_Manual_SubtrajEditor_getInitialFrame();
		int finalFrame = gui.jSpinner_Manual_SubtrajEditor_getFinalFrame();
		if(getSelectedTraj().getNumOfRealParticlesBetween(initialFrame, finalFrame)>4)
		{
			getSelectedTraj().addSubtrajectory(initialFrame, finalFrame);
			updateSubTrajTable();
			updateAnalysis();
		}
		else
			IJ.showMessage("New subtraj must be have more than 4 frames detected, try again.");
		gui.jCheckBox_ShowSubtraj_setSelected(true);
	}
	public void jButton_Manual_SubTrajEditor_Reset_clicked()
	{
		getSelectedTraj().resetSubtrajs();
		updateSubTrajTable();
		updateAnalysis();
	}
	public void jSlider_Manual_SubtraEditor_Zoom_stateChanged()
	{
		//spt_gui.getXYCanvas().setZoom(spt_gui.jSlider_Manual_SubtraEditor_Zoom_getValue());
		gui.getXYCanvas().setZoom(gui.jSlider_Manual_SubtraEditor_Zoom_getValue());
		
	}
	public void jSpinner_Manual_SubtrajEditor_InitialFinalFrame_stateChanged()
	{
		updateXYCanvas();
		if(toma!=null)
		{
			toma.updateGUI();
			toma.updateData();
			toma.updateMSDChart();
		}
		if(overTime!=null)
			overTime.update();
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowPoints_itemStateChanged()
	{
		gui.getXYCanvas().setShowPoints(gui.jCheckBox_Manual_SubtrajEditor_ShowPoints_isSelected());
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowAllTrajs_itemStateChanged()
	{
		gui.getXYCanvas().setShowAllTrajs(gui.jCheckBox_Manual_SubtrajEditor_ShowAllTrajs_isSelected());
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowGaps_itemStateChanged()
	{
		gui.getXYCanvas().setShowGaps(gui.jCheckBox_Manual_SubtrajEditor_ShowGaps_isSelected());
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowSubtraj_itemStateChanged()
	{
		gui.getXYCanvas().setShowSubtraj(gui.jCheckBox_Manual_SubtrajEditor_ShowSubtraj_isSelected());
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowInfo_itemStateChanged()
	{
		gui.getXYCanvas().setShowInfo(gui.jCheckBox_Manual_SubtrajEditor_ShowInfo_isSelected());
	}
	public void jCheckBox_Manual_SubtrajEditor_ShowVectors_itemStateChanged()
	{
		gui.getXYCanvas().showVectors(gui.jCheckBox_ShowVectors_isSelected());
	}
	public void jCheckBox_Show2StateMode_itemStateChanged()
	{
		gui.getXYCanvas().show2State(gui.jCheckBox_Show2State_isSelected());
	}
	
	public void jButton1_clicked()
	{
		getLastSelectedTraj().setUserType(1);
		updateTrajTable();
		updateAnalysis();
	}
	public void jButton2_clicked()
	{
		getLastSelectedTraj().setUserType(2);
		updateTrajTable();
		updateAnalysis();
	}
	public void jButton3_clicked()
	{
		getLastSelectedTraj().setUserType(3);
		updateTrajTable();
		updateAnalysis();
	}
	public void jButton4_clicked()
	{
		getLastSelectedTraj().setUserType(4);
		updateTrajTable();
		updateAnalysis();
	}
	public void xyCanvas_mousePressed(int x, int y)
	{
		gui.getXYCanvas().setMousePressed(x,y);
	}
	public void xyCanvas_mouseDragged(int x, int y)
	{
		gui.getXYCanvas().setMouseDragged(x,y);
	}
	public void xyCanvas_mouseReleased()
	{
		gui.getXYCanvas().setMouseReleased();
	}
	public void jComboBox_CurrentSet_stateChanged()
	{
		updateCurrentSet();
		updateMain();
		updateAnalysis();
	}
	public void jButton_RenameTrajSet_clicked()
	{
		getSelectedSet().setUserName(gui.jTextField_NewName_getText());
		gui.jComboBox_Sets_Update(getTrajSetNames());
		gui.jComboBox_Sets_setSelectedIndex(selectedSetIndex);
	}
	public void jButton_DeleteSet_clicked()
	{
		if(sets.size()>0)
		{
			sets.remove(selectedSetIndex);
			gui.jComboBox_Sets_Update(getTrajSetNames());
		}
		updateMain();
		updateAnalysis();
	}
	public void jButton_DeleteTraj_clicked()
	{
		if(getSelectedSet().getNumOfTrajs()>1)
		{
			getSelectedSet().deleteTraj(getSelectedTraj().getId());
			updateMain();
			updateAnalysis();
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void updateCurrentSet()
	{
		selectedSetIndex = gui.jComboBox_Sets_getSelectedIndex();
		//TODO fusionar ambos metodos...
		getSelectedSet().setLengthFilter(gui.jSpinner_TrajectoryLength_getValue());
		
		/*currentFilteredTrajs = getSelectedSet().getTrajsOfType(gui.jCheckBox_NotDefined_isSelected(),gui.jCheckBox_Normal_isSelected(),
				gui.jCheckBox_Anomalous_isSelected(),gui.jCheckBox_Corralled_isSelected(),gui.jCheckBox_Directed_isSelected());*/
	}
	public void updateMain()
	{
		updateTrajTable();
		updateSubTrajTable();
		updateGUIControls();
		updateXYCanvas();
		if(particleTracker!=null)
			particleTracker.updateTrajectoriesStackWindow();
	}
	public void updateAnalysis()
	{
		if(toma!=null)
		{	
			toma.updateGUI();
			toma.updateData();
			toma.updateMSDChart();
		}
		if(hmma!=null)
		{
			hmma.updateSSDChart();
			hmma.updateStateCanvas();
		}
		if(overTime!=null)
			overTime.update();
			
		if(histograms!=null)
			histograms.updateFrequencyChart();
		if(direction!=null)
			direction.updateDirectionChart();
		
		
		if(info!=null)
		{
			info.updateGlobalInfo();
			info.updateTrajectoryInfo();
		}
	}
	//solo usar para cambios en Metrics
	/*public void updateData()
	{
		for(int i=0;i<trajSet.getNumOfTrajs();i++)
			trajSet.getTraj(i).updateData();
	}*/
	public void updateTrajTable()
	{
		gui.jTable_TrajectoriesTable_clean();
		gui.jTable_SubtrajectoriesTable_clean();
//		Trajectory[] trajs = getSelectedSet().getTrajs();
		Trajectory[] trajs = getAllFilteredTrajs();	
		rowTrajID = new int[trajs.length];
		for(int i=0;i<trajs.length;i++)
		{
			rowTrajID[i] = trajs[i].getId();
			gui.addRowInTrajectoryTableModel(new Object[]{
					new Boolean(trajs[i].isUsar()),
					new Integer(trajs[i].getId()),
					new Integer(trajs[i].getLength()),
					new String(trajs[i].getTrajTypeToString())
					});
		}			
//		getSelectedSet().setSelectedTraj(0);
		selectedRowTraj = 0;
	}
	public void updateSubTrajTable()
	{
		gui.jTable_SubtrajectoriesTable_clean();
		Trajectory traj = getSelectedTraj();
		
		Subtrajectory[] subtrajs = traj.getSubtrajs();
		if(subtrajs!=null)
		{
			for(int i=0;i<subtrajs.length;i++)
			{
				//TODO agregar filtro!!!
				gui.addRowIntSubTrajectoryTableModel(new Object[]{
						new Boolean(subtrajs[i].isUsar()),
						new String(traj.getId()+"."+subtrajs[i].getSubId()),
						new String(subtrajs[i].getStartFrame()+"-"+subtrajs[i].getEndFrame()),
						new String(subtrajs[i].getTrajTypeToString())
						}
				);
			}
		}
	}
	public void updateGUIControls()
	{
		if(sets.size()>0)
		{
			gui.controlsSetEnabled(true);
			if(!trajTableLastClicked)
			{
				gui.setSubtrajectorySelectionControlsEnabled(false);
				if(toma!=null)
				{
					toma.updateGUI();
					//gui_typeMotion.setAutomaticPanelEnabled(true);
					//gui_typeMotion.jCheckBox_MSD_ShowAutomatic_setSelected(selectedTxtSubTraj.isAnalyzed());
				}
			}
			else if(selectedRowTraj>=0 && getSelectedSet().getNumOfTrajs()>0)
			{
				Trajectory traj = getSelectedSet().getTrajById(rowTrajID[selectedRowTraj]);
				gui.setSubtrajectorySelectionControlsEnabled(true);
				gui.jSpinner_Manual_subtrajEditor_setModel(1, traj.getLength());
				System.out.println("selected traj length:"+traj.getLength());
				gui.jSlider_Manual_SubtrajEditor_setModel(1, traj.getLength());
				if(toma!=null)
				{
					toma.updateGUI();
					//gui_typeMotion.jCheckBox_MSD_ShowAutomatic_setSelected(selectedTxtTraj.isAnalyzed());
				}
			}
		}
		else
		{
			gui.controlsSetEnabled(false);
		}
	}
	public void updateXYCanvas()
	{
		gui.getXYCanvas().clearDrag();
		
		if(!trajsFromXML && getSelectedSet().getOriginalSource() == TrajSet.IMAGE)//movie loaded?
			gui.getXYCanvas().setTrajs(this, particleTracker.getMovie().getHeight(), particleTracker.getMovie().getWidth());
		else
			gui.getXYCanvas().setTrajs(this, 0, 0);
		
		gui.getXYCanvas().setSelectedTraj(selectedRowTraj);
		gui.getXYCanvas().setInitialFinalFrame(gui.jSpinner_Manual_SubtrajEditor_getInitialFrame(),gui.jSpinner_Manual_SubtrajEditor_getFinalFrame());
		
		if(!trajTableLastClicked)
		{
			Subtrajectory subtraj = getSelectedSubtraj();
			if(subtraj!=null)
			{
				gui.getXYCanvas().setBeginEndSubtraj(subtraj.getStartFrame(), subtraj.getEndFrame());
				gui.getXYCanvas().setInitialFinalFrame(1,1);
			}
		}
		
	}
	public void show_FrameInfo(Frame frame)
	{
		StringBuffer info = new StringBuffer();
		info.append("Preview Detection:");
		info.append("\n");
		info.append("\n");
		info.append("Current frame: "+frame.getFrame_number());
		info.append("\n");
		info.append("Particles detected: "+frame.getParticles().length);
		info.append("\n");
		info.append("more info....");
		
		IJ.log(info.toString());		
	}
	public void newParticleTracker(ImagePlus imp)
	{
		particleTracker = new ParticleTracker(this, imp);		
	}
	public ParticleTracker getParticleTracker() {
		return particleTracker;
	}
	/*-------------------------------------------------------------------------*/
	
	/*
	
	public void fusionEvents(){
		
		EventEvaluator ee = new EventEvaluator(this.particleTracker.getMovie().getImp(), 4, 15 , 1);
		TrajSet trajSet = sets.get(selectedSetIndex);
		
		EventSet eventSet = new EventSet();
		System.out.println("Number of trajs: "+trajSet.getNumOfTrajs());
		for(int i=0; i< trajSet.getNumOfTrajs(); i++){
			
			Event event = ee.evaluateImproved(trajSet.getTraj(i));
			if(event!=null){
				
				eventSet.addEvent(event);
				
			}
			
			
		}

		if (eventSet.getNumberOfEvents()!=0){
			particleTracker.addEventsToCanvas(eventSet);
			particleTracker.changeShowEvents();
		}
		
		
		
		
		
		
	}*/
	
	
	
	/*-------------------------------------------------------------------------*/
	public void updateTrajectoryInfo() 
	{
		if(info!=null)
			info.updateTrajectoryInfo();
	}
	public int getLengthFilter()
	{
		return gui.jSpinner_TrajectoryLength_getValue();
	}
	public ArrayList<TrajSet> getSets()
	{
//		this.trajSet = data;
		return sets;
	}
	public TrajSet getSelectedSet()
	{
		if(sets.size()>0)
			return sets.get(selectedSetIndex);
		else
			return null;
	}
	public void addTrajSet(TrajSet trajSet)
	{
		sets.add(trajSet);
		gui.jComboBox_Sets_Update(getTrajSetNames());
	}
	public String[] getTrajSetNames()
	{
		String[] names = new String[sets.size()];
		for(int i = 0;i<names.length;i++)
			names[i] = sets.get(i).getUserName()+" from "+sets.get(i).getOriginalSourceToString();
		return names;
	}
	public Trajectory getSelectedTraj()
	{
		return getSelectedSet().getTrajById(rowTrajID[selectedRowTraj]);
	}
	public Subtrajectory getSelectedSubtraj() 
	{
		if(getSelectedTraj().getSubtrajs().length>0)
			return getSelectedTraj().getSubtraj(selectedRowSubTraj);
		else
			return null;
	}
	public Trajectory getLastSelectedTraj()
	{
		if(trajTableLastClicked)return getSelectedTraj();
		else return getSelectedSubtraj();
	}
	/*public Trajectory getTraj(int index) 
	{
		return getSelectedSet().getTraj(index);
	}*/
	public Trajectory[] getFilteredTrajs() 
	{
		boolean[] b = getTrajTypeSelection();
		return getSelectedSet().getTrajsOfType(b[0],b[1],b[2],b[3],b[4]);
	}	
	public Trajectory[] getAllFilteredTrajs() 
	{
		boolean[] b = getTrajTypeSelection();
		return getSelectedSet().getAllTrajsOfType(gui.jSpinner_TrajectoryLength_getValue(), b[0],b[1],b[2],b[3],b[4]);
	}
	public int getNumOfTrajs()
	{
		return getSelectedSet().getNumOfTrajs();
	}
	public boolean[] getTrajTypeSelection()
	{
		return new boolean[]{gui.jCheckBox_NotDefined_isSelected(),gui.jCheckBox_Normal_isSelected(),gui.jCheckBox_Anomalous_isSelected(),gui.jCheckBox_Corralled_isSelected(),gui.jCheckBox_Directed_isSelected()};
	}
	private void createStackWindow_for_IMPwithTrajs()
	{
		
		
//		stack_window_forImpWithTrajs.setVisible(true);
	}
	public boolean isTrajsFromXML() {
		return trajsFromXML;
	}
	public void setTrajsFromXML(boolean bool)
	{
		this.trajsFromXML = bool;
	}
	
	
	
	
/*********************************************************/
 	
 	public void plotIntensities(){
 		
 		int x = ((Double)getSelectedTraj().getCentroide_X()).intValue();
 		int y = ((Double)getSelectedTraj().getCentroide_Y()).intValue();
 		
 		
 		Frame[] frames = particleTracker.getMovie().getFrames();
 		
 		
 		double[] intensities = new double[frames.length];
 		double[] time = new double[intensities.length];
 		
 		
 		double intensitySum = 0;
 		
 		
 		
 		for(int f=0;f<intensities.length;f++){
 			intensitySum = 0;
 			for(int i = 0; i < 3;i++){
 	 			for(int j = 0; j < 3; j++){
 	 				intensitySum += frames[f].getOriginal_ip().getPixelValue(y, x);
 	 			}
 	 		}
 	 		intensities[f] = intensitySum/9;
 	 		time[f] = f+1;
 		}
 		
 		 PlotWindow.noGridLines = false; // draw grid lines
         plot = new Plot("Example Plot","frame","intensity",time,intensities);
         //plot.setLimits(0, 1, 0, 10);
         plot.setLineWidth(1);
         //plot.addErrorBars(e);
         
         

         // add a second curve
         /*float x2[] = {.4f,.5f,.6f,.7f,.8f};
         float y2[] = {4,3,3,4,5};
         plot.setColor(Color.red);
         plot.addPoints(x2,y2,PlotWindow.X);
         plot.addPoints(x2,y2,PlotWindow.LINE);

         // add label
         plot.setColor(Color.black);
         plot.changeFont(new Font("Helvetica", Font.PLAIN, 24));
         plot.addLabel(0.15, 0.95, "This is a label");

         plot.changeFont(new Font("Helvetica", Font.PLAIN, 16));
         plot.setColor(Color.blue);*/
         plot.show();
 		
 		
 		
 	}
 	
 	public FusionEvents getFusionEvents(){
 		return fusionEvents;
 	}
 	
 	public void deleteSelectedEvent(int eventIndex){
 		fusionEvents.deleteEvent(eventIndex);
 		fusionEvents.getFusionEventsGUI().deleteRow(eventIndex);
 		updateEventSetToCanvasAfterDeleting();
 	}
 	
 	public void showEventInfo(int eventIndex){
 		int nFrames=fusionEvents.getEventEvaluator().getImpEndFrame();
 		double time[]=new double[nFrames];
 		Event eventSelected=fusionEvents.getEventSet().getEvent(eventIndex);
 		double timeFactor=fusionEvents.getSampleTime();
 		for (int i=1;i<nFrames;i++){
 			time[i-1]=i*timeFactor;
 		}
 		fusionEvents.getFusionEventsGUI().setStartTime(eventSelected.getStart()*timeFactor);
 		fusionEvents.getFusionEventsGUI().setInitialRadiusX(eventSelected.getRadiusX());
 		fusionEvents.getFusionEventsGUI().setInitialRadiusY(eventSelected.getRadiusY());
 		fusionEvents.getFusionEventsGUI().setEndTime(eventSelected.getEnd()*timeFactor);
 		fusionEvents.getFusionEventsGUI().getJFreeChartIntVsTime().setTime(time);
		fusionEvents.getFusionEventsGUI().getJFreeChartIntVsTime().setMeanIntensity(eventSelected.getIntensities());
		if (eventSelected.getExpFit()!=null)fusionEvents.getFusionEventsGUI().getJFreeChartIntVsTime().addFit(eventSelected.getExpFit());
		fusionEvents.getFusionEventsGUI().getJFreeChartIntVsTime().update();
 	}
 	
 	public void showEventSelectedInCanvas(int eventIndex){
 		Event eventSelected=fusionEvents.getEventSet().getEvent(eventIndex);
 		particleTracker.getTrajectoryCanvas().setLastFrame(eventSelected.getStart());//No sé si realmente sirva
 		System.out.println("Este evento comenzó en el frame: "+eventSelected.getStart());
 		particleTracker.getTrajectoryCanvas().setSelectedEvent(eventIndex);
 		IJ.getImage().setSlice(eventSelected.getStart());
 		
 	}
 	
 	public void manualFeSearch(){//TO DO: adding investigator option that can do a manual fitting of the intensity chart in case that this analysis dont deliver a positive event
 		Roi selectedRoi=particleTracker.trajectories_stack_window.getSelectedRoi();
 		if (selectedRoi!=null){
	 		int x1=(int)(selectedRoi.getBounds().getMinX()+0.5);
	 		int y1=(int)(selectedRoi.getBounds().getMinY()+0.5);
	 		int x2=(int)(selectedRoi.getBounds().getMaxX()+0.5);
	 		int y2=(int)(selectedRoi.getBounds().getMaxY()+0.5);
	 		Event eventEvaluated=fusionEvents.evaluateSelectedArea(x1, y1, x2, y2);
	 		if (eventEvaluated!=null){ 
	 			EventSet eventSet=fusionEvents.getEventSet();
	 			int lastIndex=getParticleTracker().getTrajectoryCanvas().updateEventSetAfterAdding(eventSet);
	 			fusionEvents.getFusionEventsGUI().automaticRowSelection(lastIndex);
	 			showEventInfo(lastIndex);
	 			showEventSelectedInCanvas(lastIndex);
	 			
	 		}
 		}else{
 			IJ.showMessage("You must select an area to analize it");
 		}
 	}
 	
 	public void updateEventSetToCanvasAfterDeleting(){
 		EventSet eventSet=fusionEvents.getEventSet();
 		getParticleTracker().getTrajectoryCanvas().updateEventSetAfterDeleting(eventSet);
 		
 	}
	
 	public void generateHistogram(){
 		EventSet eventSet=fusionEvents.getEventSet();
 		int eventSetSize=eventSet.getNumberOfEvents();
 		double[] tauArray=new double[eventSetSize];
 		for (int i=0;i<eventSetSize;i++){
 			tauArray[i]=eventSet.getEvent(i).getTau();
 		}
 		if (eventSetSize!=0){
	        fusionEvents.getFusionEventsGUI().plotHistogram(tauArray);
	    }
 	}
 	
 	public void generateStatistics(){
 		fusionEvents.getFusionEventsGUI().setNumberOfEvents(fusionEvents.getEventSet().getNumberOfEvents());
 		fusionEvents.getEventSet().updateAvgTau();
 		fusionEvents.getFusionEventsGUI().setAvgDuration(fusionEvents.getEventSet().getAvgTau());
 		fusionEvents.getEventSet().updateAvgAmplitude();
 		fusionEvents.getFusionEventsGUI().setAvgIncrease(fusionEvents.getEventSet().getAvgAmplitude());
 		fusionEvents.getFusionEventsGUI().setShortestEvent(fusionEvents.getEventSet().getMinTau());
 		fusionEvents.getFusionEventsGUI().setLongestEvent(fusionEvents.getEventSet().getMaxTau());
 		fusionEvents.getFusionEventsGUI().setMinIncrease(fusionEvents.getEventSet().getMinAmplitude());
 		fusionEvents.getFusionEventsGUI().setMaxIncrease(fusionEvents.getEventSet().getMaxAmplitude());
 		generateHistogram();
 	}
 	
 	public void addNonFusionedVesicle(){
 		Roi selectedRoi=particleTracker.trajectories_stack_window.getSelectedRoi();
 		if (selectedRoi!=null){
	 		int x1=(int)(selectedRoi.getBounds().getMinX()+0.5);
	 		int y1=(int)(selectedRoi.getBounds().getMinY()+0.5);
	 		int x2=(int)(selectedRoi.getBounds().getMaxX()+0.5);
	 		int y2=(int)(selectedRoi.getBounds().getMaxY()+0.5);
	 		int arrayDim=(x2-x1+1)*(y2-y1+1);
	 		double [] currIntArray=new double[arrayDim];
	 		int nFrames=fusionEvents.getEventEvaluator().getImpEndFrame();
	 		ImageStack is=getParticleTracker().getMovie().getImp().getImageStack();
	 		ImageProcessor ip;
	 		double maxCurrInt;
	 		double avgNFVesicleInt=0.;
	 		for (int i=1;i<=nFrames;i++){
	 			int c=0;
	 			ip=is.getProcessor(i);
	 			for(int xi=x1;xi<x2;xi++){
	 				for (int yi=y1;yi<y2;yi++){
	 					currIntArray[c]=ip.getPixel(xi, yi);
	 					c++;
	 				}
	 			}
	 			maxCurrInt=BMaths.max(currIntArray);
	 			avgNFVesicleInt=+maxCurrInt;
	 			System.out.println("Frame: "+i+" maxInt: "+maxCurrInt);
	 		}
	 		
	 		avgNFVesicleInt=avgNFVesicleInt/nFrames;
	 		System.out.println("Avg max int: "+ avgNFVesicleInt);
	 		
	 		getFusionEvents().getNFVesicleGUI().addNFVesicle();
	 		getFusionEvents().getEventEvaluator().addNonFusionedVesicle(avgNFVesicleInt);
	 	}else{
 			IJ.showMessage("You must select an area that contains a non-fusioned vesicle!");
 		}
 	}
	
	
	
	
	
	
}

