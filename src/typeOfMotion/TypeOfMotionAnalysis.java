package typeOfMotion;

import bTools.BMaths;
import bTools.BNF;
import ij.IJ;
import main.Thinker;
import data.Metrics;
import data.TrajSet;
import data.Trajectory;

public class TypeOfMotionAnalysis
{
	//Trajectory type of motion
	public static final int NOT_DEFINED = -1;
	public static final int NORMAL = 1;
	public static final int ANOMALOUS = 2;
	public static final int CORRALLED = 3;
	public static final int DIRECTED = 4;
	
	public static final int IJSIMPLEX = 0;
	public static final int LM = 1;
	public static final int LMW = 2;
	
	Thinker thinker;
	GUI gui;
	GUIFitInfo guiFitInfo;
	int fitMethod = IJSIMPLEX;
	boolean fitWithOffset;
	
	double[] timeLag, msd, msdSD, msdVar, nPoints;
	
	boolean analyzed;
	
	double[] curveFitted;
	
	double[] normalFit;
	double[] anomalousFit;
	double[] corralledFit;
	double[] directedFit;
	double normalGOF, anomalousGOF, corralledGOF, directedGOF;
	
	
	public TypeOfMotionAnalysis(Thinker thinker)
	{
		this.thinker = thinker;
		gui = new GUI(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
		fitWithOffset = false;
		
		guiFitInfo = new GUIFitInfo();
		guiFitInfo.setLocation(thinker.gui.getX(),thinker.gui.getY());
		
//		segmentator = new Segmentator();
	}
	public void close()
	{
		gui.dispose();
		gui = null;
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void updateGUI()
	{
		int initialFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getInitialFrame();
		int finalFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getFinalFrame();
		
		Trajectory traj = thinker.getSelectedTraj();
		double[] timelag = traj.getTimeLag();
		gui.jSlider_FinalMSDPoint_setMax(timelag.length);
		gui.jCheckBox_MSD_ShowAutomatic_setSelected(traj.isAnalyzed());
		
		if(initialFrame<finalFrame && (initialFrame!=1 || finalFrame!=traj.getFrame(traj.getFrames().length-1)))
			gui.setFittingPanelEnabled(false);
		else
			gui.setFittingPanelEnabled(true);
		if(!thinker.trajTableLastClicked)
			gui.setFittingPanelEnabled(true);
	}
	
	/*---------------------MSD Analysis--------------------------*/
	public void finalMSDFitPoint_stateChanged()
	{
		gui.jTextfield_FinalMSDFitPoint_setText(String.valueOf(gui.jSlider_FinalMSDPoint()));
		double frameStep = thinker.getSelectedSet().getMetrics().getFrameStep();
		gui.getJFreeChart_MSD().setFitMarks(gui.jSpinner_InitialMSDPoint()*frameStep, gui.jSlider_FinalMSDPoint()*frameStep);
		gui.getJFreeChart_MSD().update();
	}
	public void jSpinner_SpinnersStep_stateChanged()
	{
		gui.jSpinners_setStep(Math.pow(10, gui.jSpinner_SpinnerStep_getValue()));
	}
	public void jSpinner_stateChanged()
	{
		updateData();
		updateMSDChart();
		updateFitInfo();
	}
	public void jCheckBox_AvgMSD_stateChanged()
	{
		updateData();
		updateMSDChart();
		updateFitInfo();
	}
	public void jCheckBox_itemStateChanged()
	{
		updateData();
		updateMSDChart();
		updateFitInfo();
	}
	public void jComboBox_FitMethod_stateChanged()
	{
		fitMethod = gui.jComboBox_FitMethod_getSelectedIndex();
	}
	public void jCheckBox_fitWithOffset_stateChanged()
	{
		fitWithOffset = gui.jCheckBox_fitWithOffset_isSelected();
		gui.jSpinner_C_setEnabled(fitWithOffset);
		updateData();
		updateMSDChart();
	}
	public void jCheckBox_showFitInfo()
	{
		guiFitInfo.setVisible(gui.jCheckBox_showFitInfo_isSelected());
	}
	public void jButton_BestFitNormal_clicked()
	{
//		double[][] fiteableMSD = getFiteableMSD();
		int initialP = gui.jSpinner_InitialMSDPoint();
		int finalP = gui.jSlider_FinalMSDPoint();
		
		double[] normalFit;
		switch(fitMethod)
		{
			case IJSIMPLEX:
//				normalFit = Fitter.normalFitIJ(fiteableMSD[0], fiteableMSD[1], fitWithOffset);
				normalFit = Fitter.normalFitIJ(BMaths.getSubArray(timeLag,initialP-1, finalP-1), BMaths.getSubArray(msd,initialP-1, finalP-1), fitWithOffset);
				break;
			case LM:
//				normalFit = Fitter.normalFitLM(fiteableMSD[0], fiteableMSD[1], fitWithOffset, false, null);
				normalFit = Fitter.normalFitLM(BMaths.getSubArray(timeLag,initialP-1, finalP-1), BMaths.getSubArray(msd,initialP-1, finalP-1), fitWithOffset, false, null);
				break;
			case LMW:
//				normalFit = Fitter.normalFitLM(fiteableMSD[0], fiteableMSD[1], fitWithOffset, true, fiteableMSD[2]);
				normalFit = Fitter.normalFitLM(BMaths.getSubArray(timeLag,initialP-1, finalP-1), BMaths.getSubArray(msd,initialP-1, finalP-1), fitWithOffset, true, BMaths.getSubArray(msdVar,initialP-1, finalP-1));
				break;
			default:
//				normalFit = Fitter.normalFitLM(fiteableMSD[0], fiteableMSD[1], fitWithOffset, false, null);
				normalFit = Fitter.normalFitLM(BMaths.getSubArray(timeLag,initialP-1, finalP-1), BMaths.getSubArray(msd,initialP-1, finalP-1), fitWithOffset, false, null);
				break;
		}
		gui.jSpinner_Normal_D_setValue(BNF.truncate(normalFit[0]));
		IJ.log("Best Normal Fit D:"+BNF.sF(normalFit[0]));
		if(fitWithOffset)
		{
			IJ.log("Best Normal Fit C:"+BNF.sF(normalFit[1]));
			gui.jSpinner_Normal_C_setValue(BNF.truncate(normalFit[1]));
		}
	}
	public void jButton_BestFitCorralled_clicked()
	{
		double[][] fiteableMSD = getFiteableMSD();
		double[] timeLag = fiteableMSD[0];
		double[] msd = fiteableMSD[1];
		double[] msdVar = fiteableMSD[2];
//		double[] time12 = BMaths.getSubArray(timeLag, 0, 1);
//		double[] msd12 = BMaths.getSubArray(msd, 0, 1);
//		double[] msdVar12 = BMaths.getSubArray(msdVar, 0, 1);
		

//		double[] normal = Fitter.normalFitLM(time12, msd12, fitWithOffset,true, msdVar12); 
		double[] normal = Fitter.normalFitLM(timeLag, msd, fitWithOffset,true, msdVar);
		double estimatedL = BMaths.avg(msd);
		double[] corralled;
		switch(fitMethod)
		{
			case IJSIMPLEX:
				corralled = Fitter.corralledFitIJ(fiteableMSD[0], fiteableMSD[1], new double[]{normal[0], estimatedL}, fitWithOffset);
				break;
			case LM:
				corralled = Fitter.corralledFitLM(fiteableMSD[0], fiteableMSD[1], new double[]{normal[0], estimatedL}, fitWithOffset, false, null);
				break;
			case LMW:
				corralled = Fitter.corralledFitLM(fiteableMSD[0], fiteableMSD[1], new double[]{normal[0], estimatedL}, fitWithOffset, true, fiteableMSD[2]);
				break;
			default:
				corralled = Fitter.corralledFitLM(fiteableMSD[0], fiteableMSD[1], new double[]{normal[0], estimatedL}, fitWithOffset, false, null);
				break;
		}
		IJ.log("Best Corralled Fit D:"+corralled[0]);
		IJ.log("Best Corralled Fit L:"+corralled[1]);
		gui.jSpinner_Corralled_D_setValue(BNF.truncate(corralled[0]));
		gui.jSpinner_Corralled_L_setValue(BNF.truncate(corralled[1]));
		if(fitWithOffset)
		{
			IJ.log("Best Corralled Fit C:"+corralled[2]);
			gui.jSpinner_Corralled_C_setValue(BNF.truncate(corralled[2]));
		}
	}
	public void jButton_BestFitAnomalous_clicked()
	{
		double[][] fiteableMSD = getFiteableMSD();
		double[] timeLag = fiteableMSD[0];
		double[] msd = fiteableMSD[1];
		double[] msdVar = fiteableMSD[2];
		double[] time12 = BMaths.getSubArray(timeLag, 0, 5);
		double[] msd12 = BMaths.getSubArray(msd, 0, 5);
		double[] msdVar12 = BMaths.getSubArray(msdVar, 0, 5);
		for(int i=0;i<time12.length;i++)
		{
			System.out.println("time12["+i+"]:"+time12[i]+"\tmsd12["+i+"]:"+msd12[i]+"\tmsdVar12["+i+"]:"+msdVar12[i]);
		}
		

		double[] normal = Fitter.normalFitLM(time12, msd12, fitWithOffset, true, msdVar12); 
		double[] anomalous;
		switch(fitMethod)
		{
			case IJSIMPLEX:
				anomalous = Fitter.anomalousFitIJ(timeLag, msd, new double[]{normal[0],1.0,0.0}, fitWithOffset);
				break;
			case LM:
				anomalous = Fitter.anomalousFitLM(timeLag, msd, new double[]{normal[0],1.0,0.0}, fitWithOffset, false, null);
				break;
			case LMW:
				anomalous = Fitter.anomalousFitLM(timeLag, msd, new double[]{normal[0],1.0,0.0}, fitWithOffset, true, msdVar);
				break;
			default:
				anomalous = Fitter.anomalousFitLM(timeLag, msd, new double[]{normal[0],1.0,0.0}, fitWithOffset, false, null);
				break;
		}
		IJ.log("Best Anomalous Fit D:"+anomalous[0]);
		IJ.log("Best Anomalous Fit Alfa:"+anomalous[1]);
		gui.jSpinner_Anomalous_D_setValue(BNF.truncate(anomalous[0]));
		gui.jSpinner_Anomalous_Alfa_setValue(BNF.truncate(anomalous[1]));
		if(fitWithOffset)
		{
			IJ.log("Best Anomalous Fit C:"+anomalous[2]);
			gui.jSpinner_Anomalous_C_setValue(BNF.truncate(anomalous[2]));
		}
	}
	public void jButton_BestFitDirected_clicked()
	{
		double[][] fiteableMSD = getFiteableMSD();
		double[] timeLag = fiteableMSD[0];
		double[] msd = fiteableMSD[1];
		double[] msdVar = fiteableMSD[2];
		double[] time12 = BMaths.getSubArray(timeLag, 0, 1);
		double[] msd12 = BMaths.getSubArray(msd, 0, 1);
		double[] msdVar12 = BMaths.getSubArray(msdVar, 0, 1);
		

		double[] normal = Fitter.normalFitLM(time12, msd12, fitWithOffset,true, msdVar12); 
		double[] directed;
		switch(fitMethod)
		{
			case IJSIMPLEX:
				directed = Fitter.directedFitIJ(timeLag, msd,new double[]{normal[0],1.0},fitWithOffset);
				break;
			case LM:
				directed = Fitter.directedFitLM(timeLag, msd,new double[]{normal[0],1.0},fitWithOffset, false, null);
				break;
			case LMW:
				directed = Fitter.directedFitLM(timeLag, msd,new double[]{normal[0],1.0},fitWithOffset, true, msdVar);
				break;
			default:
				directed = Fitter.directedFitLM(timeLag, msd,new double[]{normal[0],1.0},fitWithOffset, false, null);
				break;
		}
		IJ.log("Best Directed Fit D:"+directed[0]);
		IJ.log("Best Directed Fit V:"+directed[1]);
		gui.jSpinner_Directed_D_setValue(BNF.truncate(directed[0]));
		gui.jSpinner_Directed_V_setValue(BNF.truncate(directed[1]));
		if(fitWithOffset)
		{
			IJ.log("Best Directed Fit C:"+directed[2]);
			gui.jSpinner_Directed_C_setValue(BNF.truncate(directed[2]));
		}
	}
	public void jButton_Manual_AutomaticMSDFit_clicked()
	{
		double[][] fiteableMSD = getFiteableMSD();
		double[] timeLag = fiteableMSD[0];
		double[] msd = fiteableMSD[1];
		double[] msdVar = fiteableMSD[2];
		double[] time24 = new double[3];
		double[] msd24 = new double[3];
		double[] msdVar24 = new double[3];
		
		for(int i=0;i<3;i++)
		{
			time24[i] = timeLag[i+1];
			msd24[i] = msd[i+1];
			msdVar24[i] = msdVar[i+1];
		}
		Object[] ajuste = Fitter.automaticBestFit(timeLag, msd, fitWithOffset, fitMethod == LMW, msdVar);
		//TODO actualizar info, data, plots, 
		//traj.setTypeAndParams(((Integer)ajuste[0]).intValue(), (double[])ajuste[1]);
			//updateMSDChart();
	}
	public void jButton_AutoFitAllTrajs()
	{
		Object[] ajuste;
//		int initialP = gui.jSpinner_InitialMSDPoint();
//		int finalP = gui.jSlider_FinalMSDPoint();
		double[] timeLag;
		double[] msd;
		double[] weights;
		Trajectory[] trajs =  thinker.getSelectedSet().getTrajsWithSubtrajs();
		boolean rb25 = gui.radioButton25_isSelected();
		int npoints;
		for(int i=0;i<trajs.length;i++)
		{
			System.out.println(i+" fiteando Traj:"+trajs[i].getId());
			timeLag = trajs[i].getTimeLag();//BMaths.getSubArray(trajs[i].getTimeLag(),initialP-1, finalP-1);
			msd = trajs[i].getMSD();//BMaths.getSubArray(trajs[i].getMSD(),initialP-1, finalP-1);
			weights = trajs[i].getMSDVar();//BMaths.getSubArray(trajs[i].getMSDVar(),initialP-1, finalP-1);
			if(rb25)
			{
				npoints = Math.round(timeLag.length*0.25f);			
				timeLag = BMaths.getSubArray(timeLag, 0, npoints-1);	
				msd = BMaths.getSubArray(msd, 0, npoints-1);
				weights = BMaths.getSubArray(weights, 0, npoints-1);
			}
			ajuste = Fitter.automaticBestFit(timeLag, msd, fitWithOffset, fitMethod == LMW, weights);
			trajs[i].setTypeAndParams(((Integer)ajuste[0]).intValue(), (double[])ajuste[1], ((Double)ajuste[2]).doubleValue());
		}
		thinker.updateTrajectoryInfo();
		//TODO actualizar info, data, plots, 
	}
	public void jButton_SetNormal_clicked()
	{
		thinker.getLastSelectedTraj().setTypeAndParams(TypeOfMotionAnalysis.NORMAL, new double[]{gui.jSpinner_Normal_D_getValue(),0}, normalGOF);
		thinker.getLastSelectedTraj().resetSubtrajs();
		
		if(thinker.trajTableLastClicked)thinker.updateTrajTable();
		else thinker.updateSubTrajTable();
		thinker.updateAnalysis();
	}
	public void jButton_SetAnomalous_clicked()
	{
		thinker.getLastSelectedTraj().setTypeAndParams(TypeOfMotionAnalysis.ANOMALOUS, new double[]{gui.jSpinner_Anomalous_D_getValue(),gui.jSpinner_Anomalous_Alfa_getValue()}, anomalousGOF);
		thinker.getLastSelectedTraj().resetSubtrajs();
		if(thinker.trajTableLastClicked)thinker.updateTrajTable();
		else thinker.updateSubTrajTable();
		if(thinker.info!=null)
			thinker.info.updateTrajectoryInfo();
	}
	public void jButton_SetCorralled_clicked()
	{
		thinker.getLastSelectedTraj().setTypeAndParams(TypeOfMotionAnalysis.CORRALLED, new double[]{gui.jSpinner_Corralled_D_getValue(),gui.jSpinner_Corralled_L_getValue()}, corralledGOF);
		thinker.getLastSelectedTraj().resetSubtrajs();
		if(thinker.trajTableLastClicked)thinker.updateTrajTable();
		else thinker.updateSubTrajTable();
		if(thinker.info!=null)
			thinker.info.updateTrajectoryInfo();
	}
	public void jButton_SetDirected_clicked()
	{
		thinker.getLastSelectedTraj().setTypeAndParams(TypeOfMotionAnalysis.DIRECTED, new double[]{gui.jSpinner_Directed_D_getValue(),gui.jSpinner_Directed_V_getValue()}, directedGOF);
		thinker.getLastSelectedTraj().resetSubtrajs();
		if(thinker.trajTableLastClicked)thinker.updateTrajTable();
		else thinker.updateSubTrajTable();
		if(thinker.info!=null)
			thinker.info.updateTrajectoryInfo();
	}


	/*-------------------------------------------------------------*/
	public void updateData()
	{
		if(gui.jCheckBox_AvgMSD_isSelected())
		{
			TrajSet trajSet = thinker.getSelectedSet();
			double[][] msdthings;
			switch(gui.jComboBox_AvgMSDOption_getSelectedIndex())
			{
				case 0:msdthings = trajSet.getAVGMSD(false, true, true, true, true, true);break;
				case 1:msdthings = trajSet.getAVGMSD(false, false, true, false, false, false);break;
				case 2:msdthings = trajSet.getAVGMSD(false, false, false, true, false, false);break;
				case 3:msdthings = trajSet.getAVGMSD(false, false, false, false, true, false);break;
				case 4:msdthings = trajSet.getAVGMSD(false, false, false, false, false, true);break;
				default:msdthings = trajSet.getAVGMSD(false, false, true, true, true, true);break;
			}
			timeLag = msdthings[0];
			msd = msdthings[1];
			nPoints = msdthings[2];
			msdSD = msdthings[3];
			msdVar = msdthings[4];
			analyzed = trajSet.isAnalyzed();
			curveFitted = new double[0];//getMSDCurveFitted(traj);
		}
		else
		{
			Trajectory traj = thinker.getLastSelectedTraj();
			if(thinker.trajTableLastClicked)
			{
				int initialFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getInitialFrame();
				int finalFrame = thinker.gui.jSpinner_Manual_SubtrajEditor_getFinalFrame();
				if(initialFrame<finalFrame && (initialFrame!=1 || finalFrame!=traj.getFrame(traj.getFrames().length-1)))
				{
					traj = traj.getTempSubtraj(initialFrame, finalFrame);
//					chart.showCorralledFit(false);
//					chart.showAnomalousFit(false);
//					chart.showNormalFit(false);
//					chart.showDirectedFit(false);
				}
			}
			timeLag = traj.getTimeLag();
			msd = traj.getMSD();
			msdVar = traj.getMSDVar();
			msdSD = traj.getMSDSD();
			nPoints = traj.getMSDNPoints();	
			analyzed = traj.isAnalyzed();
			curveFitted = getMSDCurveFitted(traj);
		}
		gui.jSlider_FinalMSDPoint_setMax(msd.length);
		
		if(fitWithOffset)
		{
			normalFit = Evaluator.funcNormalWO(timeLag, gui.jSpinner_Normal_D_getValue(), gui.jSpinner_Normal_C_getValue());
			anomalousFit = Evaluator.funcAnomalousWO(timeLag, gui.jSpinner_Anomalous_D_getValue(), gui.jSpinner_Anomalous_Alfa_getValue(), gui.jSpinner_Anomalous_C_getValue());
			corralledFit = Evaluator.funcCorralledWO(timeLag, gui.jSpinner_Corralled_D_getValue(), gui.jSpinner_Corralled_L_getValue(), gui.jSpinner_Corralled_C_getValue());
			directedFit = Evaluator.funcDirectedWO(timeLag, gui.jSpinner_Directed_D_getValue(), gui.jSpinner_Directed_V_getValue(), gui.jSpinner_Directed_C_getValue());			
		}
		else
		{
			normalFit = Evaluator.funcNormal(timeLag, gui.jSpinner_Normal_D_getValue());
			anomalousFit = Evaluator.funcAnomalous(timeLag, gui.jSpinner_Anomalous_D_getValue(), gui.jSpinner_Anomalous_Alfa_getValue());
			corralledFit = Evaluator.funcCorralled(timeLag, gui.jSpinner_Corralled_D_getValue(), gui.jSpinner_Corralled_L_getValue());
			directedFit = Evaluator.funcDirected(timeLag, gui.jSpinner_Directed_D_getValue(), gui.jSpinner_Directed_V_getValue());
		}
	}
	public void updateMSDChart()
	{
		Metrics metrics = thinker.getSelectedSet().getMetrics();
		JFreeChartMSD chart = gui.getJFreeChart_MSD(); 
		chart.cleanSeries();
		chart.showCorralledFit(gui.jCheckBox_MSD_ShowCorralled_isSelected());
		chart.showAnomalousFit(gui.jCheckBox_MSD_ShowAnomalous_isSelected());
		chart.showNormalFit(gui.jCheckBox_MSD_ShowNormal_isSelected());
		chart.showDirectedFit(gui.jCheckBox_MSD_ShowDirected_isSelected());
		chart.showFitMarks(gui.jCheckBox_ShowFitMarks_isSelected());
		chart.showSD(gui.jCheckBox_showSD_isSelected());
		chart.showVariance(gui.jCheckBox_showVariance_isSelected());
		chart.showNPoints(gui.jCheckBox_showNPoints_isSelected());
		
		chart.setTime(timeLag);
		chart.setMSD(msd);
		chart.setMSDSD(msdSD);
		chart.setMSDVar(msdVar);
		chart.setMSDNPoints(nPoints);
		
		if(analyzed && gui.jCheckBox_MSD_ShowAutomatic_isSelected())
			chart.setAutomaticFit(curveFitted);

		chart.showAutomaticFit(gui.jCheckBox_MSD_ShowAutomatic_isSelected());
		
		double[] timeLag0 = new double[timeLag.length+1];
		timeLag0[0] = 0;
		for(int i=1;i<timeLag0.length;i++)
			timeLag0[i]=timeLag[i-1];
		double[] normal0 = new double[timeLag.length+1];
		double[] anomalous0 = new double[timeLag.length+1];
		double[] corralled0 = new double[timeLag.length+1];
		double[] directed0 = new double[timeLag.length+1];
		if(fitWithOffset)
		{
			normal0 = Evaluator.funcNormalWO(timeLag0, gui.jSpinner_Normal_D_getValue(), gui.jSpinner_Normal_C_getValue());
			anomalous0 = Evaluator.funcAnomalousWO(timeLag0, gui.jSpinner_Anomalous_D_getValue(), gui.jSpinner_Anomalous_Alfa_getValue(), gui.jSpinner_Anomalous_C_getValue());
			corralled0 = Evaluator.funcCorralledWO(timeLag0, gui.jSpinner_Corralled_D_getValue(), gui.jSpinner_Corralled_L_getValue(), gui.jSpinner_Corralled_C_getValue());
			directed0 = Evaluator.funcDirectedWO(timeLag0, gui.jSpinner_Directed_D_getValue(), gui.jSpinner_Directed_V_getValue(), gui.jSpinner_Directed_C_getValue());			
		}
		else
		{
			normal0 = Evaluator.funcNormal(timeLag0, gui.jSpinner_Normal_D_getValue());
			anomalous0 = Evaluator.funcAnomalous(timeLag0, gui.jSpinner_Anomalous_D_getValue(), gui.jSpinner_Anomalous_Alfa_getValue());
			corralled0 = Evaluator.funcCorralled(timeLag0, gui.jSpinner_Corralled_D_getValue(), gui.jSpinner_Corralled_L_getValue());
			directed0 = Evaluator.funcDirected(timeLag0, gui.jSpinner_Directed_D_getValue(), gui.jSpinner_Directed_V_getValue());
		}
		
		chart.setNormal(timeLag0, normal0);
		chart.setAnomalous(timeLag0, anomalous0);
		chart.setCorralled(timeLag0, corralled0);
		chart.setDirected(timeLag0, directed0);
		double frameStep = metrics.getFrameStep();
		chart.setFitMarks(gui.jSpinner_InitialMSDPoint()*frameStep, gui.jSlider_FinalMSDPoint()*frameStep);
		chart.updateUnits(thinker.getSelectedSet().getMetrics().getDistUnittoString(), thinker.getSelectedSet().getMetrics().getTimeUnittoString());
		chart.update();
	}
	public void updateFitInfo()
	{
		StringBuffer sb = new StringBuffer();
		int startIndex = gui.jSpinner_InitialMSDPoint()-1;
		int finalIndex = gui.jSlider_FinalMSDPoint()-1;
		double[] timeLag = BMaths.getSubArray(this.timeLag, startIndex, finalIndex);
		double[] msd = BMaths.getSubArray(this.msd, startIndex, finalIndex);
		double[] normalFit = BMaths.getSubArray(this.normalFit, startIndex, finalIndex);
		double[] anomalousFit = BMaths.getSubArray(this.anomalousFit, startIndex, finalIndex);
		double[] corralledFit = BMaths.getSubArray(this.corralledFit, startIndex, finalIndex);
		double[] directedFit = BMaths.getSubArray(this.directedFit, startIndex, finalIndex);
		
		sb.append("\t\t\tNormal\tAnomalous\tCorralled\tDirected\n");
		
		normalGOF = BMaths.getFitGoodness(msd, normalFit, 1);
		anomalousGOF = BMaths.getFitGoodness(msd, anomalousFit, 2);
		corralledGOF = BMaths.getFitGoodness(msd, corralledFit, 2);
		directedGOF = BMaths.getFitGoodness(msd, directedFit, 2);
		
		sb.append("TimeLag\tMSD\tGoodness Of Fit\t"+BNF.sF(normalGOF)+"\t"+BNF.sF(anomalousGOF)+"\t"+BNF.sF(corralledGOF)+"\t"+BNF.sF(directedGOF)+"\n");
		sb.append("\t\tRSquared\t\t"+BNF.sF(BMaths.getRSquared(msd, normalFit))+"\t"+BNF.sF(BMaths.getRSquared(msd, anomalousFit))+"\t"+BNF.sF(BMaths.getRSquared(msd, corralledFit))+"\t"+BNF.sF(BMaths.getRSquared(msd, directedFit))+"\n");
		sb.append("\t\tSumOfResiduals\t"+BNF.sF(BMaths.getSRS(msd, normalFit))+"\t"+BNF.sF(BMaths.getSRS(msd, anomalousFit))+"\t"+BNF.sF(BMaths.getSRS(msd, corralledFit))+"\t"+BNF.sF(BMaths.getSRS(msd, directedFit))+"\n");
		sb.append("\n");
		for(int i=0;i<msd.length;i++)
			sb.append(BNF.sF(timeLag[i])+"\t"+BNF.sF(msd[i])+"\tResidual "+(i+1)+"\t\t"+BNF.sF(BMaths.sqr(msd[i]-normalFit[i]))+"\t"+BNF.sF(BMaths.sqr(msd[i]-anomalousFit[i]))+"\t"+BNF.sF(BMaths.sqr(msd[i]-corralledFit[i]))+"\t"+BNF.sF(BMaths.sqr(msd[i]-directedFit[i]))+"\n");
		
		
		guiFitInfo.jTextArea_setText(sb.toString());
	}
	
	/*
	public void updateSegmentationTab()
	{		
		Trajectory traj = thinker.getSelectedTraj();
		Segmentator segmentator = new Segmentator(traj, gui);
		gui.getJFreeChart_SegmentationParams().setTime(segmentator.getTime());
		gui.getJFreeChart_SegmentationParams().setD(traj.getDif());
		gui.getJFreeChart_SegmentationParams().setDev(traj.getDev());
		gui.getJFreeChart_SegmentationParams().setAsym(traj.getAsym());
	}*/
	public void updateSegmentationMins()
	{
//		gui.getJFreeChart_SegmentationParams().setTime(segmentator.getTime());
	}
	
	
	public double[] getMSDCurveFitted(Trajectory traj)
	{
		double[] x = traj.getTimeLag();
		double[] curveFitted = new double[x.length];
		double[] params = traj.getTypeParams();
		//System.out.println("getMSDCurveFItted(): la trayectoria es de tipo:"+this.trajType);
		
		switch(traj.getTrajType())
		{	
			case NORMAL:curveFitted = Evaluator.funcNormal(x, params[0]);break;
			case CORRALLED:curveFitted = Evaluator.funcCorralled(x, params[0], params[1]);break;
			case ANOMALOUS:curveFitted = Evaluator.funcAnomalous(x, params[0], params[1]);break;
			case DIRECTED:curveFitted = Evaluator.funcDirected(x, params[0], params[1]);break;				
			default:System.out.println("ERRROROROROROROR!!!!!!!!!!!!!!!!!!!    DEFAULT IN getMSDCurveFItted()");		
		}	
		return curveFitted;
	}
	public double[][] getMSDbyTime(Trajectory traj)
	{
		double[] timeLag = traj.getTimeLag();
		double[] msd = traj.getMSD();
		double[][] msdByTime_time = new double[2][msd.length-1];
		for(int i=0;i<msdByTime_time[0].length;i++)
		{
			msdByTime_time[0][i] = timeLag[i+1];
			msdByTime_time[1][i] = msd[i+1]/timeLag[i+1];
			//IJ.log("msdByTime["+i+"]:"+msdByTime_time[1][i]);
		}
		return msdByTime_time;
	}
	
	/*-----------------------------------------------------------------------------------*/

	public double[][] getFiteableMSD()
	{
		int initialP = gui.jSpinner_InitialMSDPoint();
		int finalP = gui.jSlider_FinalMSDPoint();
		
		double[][] timeMSD = new double[2][];
		double[] weights;
		
		if(gui.jCheckBox_AvgMSD_isSelected())
		{
			double[][] msdThings = getAVGMSD();
			timeMSD[0] = msdThings[0];
			timeMSD[1] = msdThings[1];
			weights = msdThings[4];
		}
		else
		{
			Trajectory traj = thinker.getLastSelectedTraj();
			timeMSD[0] = traj.getTimeLag();//traj.getTimeLagMSD(initialP-1, finalP-1);
			timeMSD[1] = traj.getMSD();
			weights = traj.getMSDVar();
//			weights = new double[timeMSD[0].length];
//			for(int i=0;i<weights.length;i++)
//				weights[i]=1/traj.getMSDNPoints()[i];
		}
		return new double[][]{BMaths.getSubArray(timeMSD[0],initialP-1, finalP-1), BMaths.getSubArray(timeMSD[1],initialP-1, finalP-1), BMaths.getSubArray(weights,initialP-1, finalP-1)};
	}
	
	public double[][] getAVGMSD()
	{
		TrajSet trajSet = thinker.getSelectedSet();
		double[][] msdthings;
		switch(gui.jComboBox_AvgMSDOption_getSelectedIndex())
		{
		
			case 0:
				msdthings = trajSet.getAVGMSD(false, true, true, true, true, true);
				break;
			case 1:
				msdthings = trajSet.getAVGMSD(false, false, true, false, false, false);
				break;
			case 2:
				msdthings = trajSet.getAVGMSD(false, false, false, true, false, false);
				break;
			case 3:
				msdthings = trajSet.getAVGMSD(false, false, false, false, true, false);
				break;
			case 4:
				msdthings = trajSet.getAVGMSD(false, false, false, false, false, true);
				break;
			default:
				msdthings = trajSet.getAVGMSD(false, false, true, true, true, true);
				break;
		}
		return msdthings;
	}
	/*-----------------------------------------------------------------------------------*/
}
