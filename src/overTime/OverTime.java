package overTime;

import ij.measure.CurveFitter;
import data.Trajectory;
import main.Thinker;

public class OverTime 
{
	private GUI gui;
	private Thinker thinker;
	
	public OverTime(Thinker thinker)
	{
		this.thinker = thinker;
		this.gui = new GUI(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	
	public void close()
	{
		
		
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	
	public void jCheckBox_VSTime_stateChanged()
	{
		gui.getJFreeChart_VSTime().showIntensity(gui.jCheckBox_Intensity_isSelected());
		gui.getJFreeChart_VSTime().showVelocity(gui.jCheckBox_Velocity_isSelected());
		gui.getJFreeChart_VSTime().showAngle(gui.jCheckBox_Angle_isSelected());
		gui.getJFreeChart_VSTime().showAngleChanges(gui.jCheckBox_AngleChanges_isSelected());
	}
//	public void jSpinner_VSTime_stateChanged()
//	{
//		Trajectory traj = thinker.getLastSelectedTraj();
//		double timeLength = traj.getTimeLength();
//		int frameLength = traj.getLength();
//		double[] time = new double[frameLength];
//		for(int i=0;i<time.length;i++)
//			time[i] = i*timeLength/(time.length-1);
//		double[] fita = Maths.funcSin(time,gui.jSpinners_getValues());
//		gui.getJFreeChart_VSTime().setFitA(time, fita);
//		//TODO arreglar estopoo
//		gui.getJFreeChart_VSTime().update();
//	}
	public void jSpinner_VSTime_update()
	{
		Trajectory traj = thinker.getLastSelectedTraj();
		double[] time = traj.getTime();
		double[] angle = traj.getAngles();
		double[] timeNo0 = new double[time.length-1];
		double[] angleNo0 = new double[time.length-1];
		for(int i=0;i<timeNo0.length;i++)
		{
			timeNo0[i]=time[i+1];
			angleNo0[i]=angle[i+1];
		}
		CurveFitter cv = new CurveFitter(timeNo0, angleNo0);
		cv.doFit(CurveFitter.STRAIGHT_LINE);
		double[] cb = cv.getParams();
		double maxDif = 0;
		double dif = 0;
		for(int i=0;i<timeNo0.length;i++)
		{
			dif = Math.abs(cb[0]+cb[1]*timeNo0[i]-angleNo0[i]);
			if(maxDif<dif)maxDif=dif;
		}
//		gui.jSpinner_BC_setValues(maxDif, cb[1], cb[0]);
	}
	
	public void update()
	{
		Trajectory traj = thinker.getSelectedTraj();
		
		int initialFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getInitialFrame();
		int finalFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getFinalFrame();
		if(initialFrame<finalFrame && (initialFrame!=1 || finalFrame!=traj.getFrame(traj.getFrames().length-1)) && finalFrame-initialFrame>4)
			traj = traj.getTempSubtraj(initialFrame, finalFrame);
		
		gui.getJFreeChart_VSTime().setTime(traj.getTime());//N
		gui.getJFreeChart_VSTime().setIntensities(traj.getIntensidades());//N
		gui.getJFreeChart_VSTime().setVelocities(traj.getVelocity());//N-1
		gui.getJFreeChart_VSTime().setAngles(traj.getAngles());//N-1
		gui.getJFreeChart_VSTime().setAngleChanges(traj.getAnglesChanges());//N-2
		
		//TODO arreglar estopoo
//		jSpinner_VSTime_update();
//		jSpinner_VSTime_stateChanged();
//		jCheckBox_VSTime_stateChanged();
	}
	

}
