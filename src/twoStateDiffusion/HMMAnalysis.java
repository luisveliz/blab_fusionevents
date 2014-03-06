package twoStateDiffusion;

import bTools.BNF;
import main.Thinker;
import data.TrajSet;
import data.Trajectory;

public class HMMAnalysis 
{
	Thinker thinker;
	GUI gui;
	HMM hmm;

	//Segmentation params
	double segD1;
	double segD2;
	double segP12;
	double segP21;
	private boolean haveResult;
	public HMMAnalysis(Thinker thinker)
	{
		this.thinker = thinker;
		hmm = new HMM();
		gui = new GUI(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void close()
	{
		gui.dispose();
		gui = null;
	}
	
	/*---------------------------GUI---------------------*/
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void updateSSDChart()
	{
		Trajectory traj = thinker.getSelectedTraj();
//		gui.getJFreeChart_HMM_SSD().setTime(traj.getTimePro());
//		gui.getJFreeChart_HMM_SSD().setSSD(traj.getSSDPro());
		gui.getJFreeChart_HMM_SSD().setTime(traj.getTime());
		gui.getJFreeChart_HMM_SSD().setSSD(traj.getSSD());
		gui.getJFreeChart_HMM_SSD().update();
	}

	public void updateStateCanvas()
	{
		int[] oState = thinker.getLastSelectedTraj().getState();
		int[] gState = thinker.getLastSelectedTraj().getGState();
		gui.getOriginalStateCanvas().setStateSequence(oState);
		gui.getGuessStateCanvas().setStateSequence(gState);
		
		int aSLS=0, aSHS=0, aGLS=0, aGHS = 0, good=0;
		for(int i=0;i<gState.length;i++)
		{
			if(thinker.getSelectedSet().getOriginalSource()==TrajSet.SIMULATED)
			{
				if(oState[i]==1)
					aSLS++;
				else
					aSHS++;
				
				if(oState[i]==gState[i])
					good++;
			}
			if(gState[i]==1)
				aGLS++;
			else
				aGHS++;
			
		}
		
		if(thinker.getSelectedSet().getOriginalSource()==TrajSet.SIMULATED)
		{
			gui.jTextField_SimLowState_setText(String.valueOf(100*aSLS/oState.length)+"%");
			gui.jTextField_SimHighState_setText(String.valueOf(100*aSHS/oState.length)+"%");
			gui.jTextField_Success_setText(String.valueOf(100*good/gState.length)+"%");
		}
		else
		{
			gui.jTextField_SimLowState_setText("Only for Simulated");
			gui.jTextField_SimHighState_setText("Only for Simulated");
			gui.jTextField_Success_setText("Only for Simulated");
		}
			
		gui.jTextField_GuessLowState_setText(String.valueOf(100*aGLS/gState.length)+"%");
		gui.jTextField_GuessHighState_setText(String.valueOf(100*aGHS/gState.length)+"%");
			
		
		
		
	}
	
	public void jButton_HMM_MCMC_update_clicked()
	{
		double[] scale = new double[]{gui.jTextfield_HMM_MCMC_ScaleD1_getValue(),
									  gui.jTextfield_MCMC_ScaleD2_getValue(),
									  gui.jTextfield_MCMC_ScaleP12_getValue(),
									  gui.jTextfield_MCMC_ScaleP21_getValue()};
		hmm.mcmc.setScale(scale);
		int upf = gui.jTextfield_MCMC_UpdatePlotsFreq_getValue();
		hmm.mcmc.setUpdateFrecuency(upf);
		gui.getJFreeChart_MCMCsteps().setUPF(upf);
	}
	public void jButton_HMM_MCMC_Start_clicked()
	{
		if(hmm.mcmc!=null && hmm.mcmc.finish==false)
		{
			if(hmm.mcmc.pause==false)
			{
				hmm.pauseMCMC();
				gui.hmm_MCMC_setEnabled(false);
				gui.jButton_MCMC_setText("continue");
			}
			else
			{
				hmm.continueMCMC();
				gui.hmm_MCMC_setEnabled(true);
				gui.jButton_MCMC_setText("pause");
			}
		}
		else
		{
			gui.hmm_MCMC_setEnabled(false);
			gui.jButton_MCMC_setText("pause");
			gui.getJFreeChart_MCMCsteps().cleanSeries();
			double initialD1 = gui.jTextfield_MCMC_InitialD1_getValue();
			double initialD2 = gui.jTextfield_MCMC_InitialD2_getValue();
			double initialP12 = gui.jTextfield_MCMC_InitialP12_getValue();
			double initialP21 = gui.jTextfield_MCMC_InitialP21_getValue();
			int steps = gui.jTextfield_MCMC_Steps_getValue();
			double[] scale = new double[]{gui.jTextfield_HMM_MCMC_ScaleD1_getValue(),
					  gui.jTextfield_MCMC_ScaleD2_getValue(),
					  gui.jTextfield_MCMC_ScaleP12_getValue(),
					  gui.jTextfield_MCMC_ScaleP21_getValue()};
			int upf = gui.jTextfield_MCMC_UpdatePlotsFreq_getValue();
			gui.getJFreeChart_MCMCsteps().setUPF(upf);
			gui.jSlider_Burn_SetMax(steps);
			hmm.MCMC(thinker.getSelectedSet().getTrajs(), gui, thinker.getSelectedSet().getMetrics().getFrameStep(), new double[]{Math.log10(initialD1), Math.log10(initialD2),initialP12,initialP21}, steps, scale, upf);
		}
	}
	public void jButton_MCMC_stop_clicked() 
	{
		gui.hmm_MCMC_setEnabled(true);
		hmm.stopMCMC();			
		gui.jButton_MCMC_setText("start");
	}
	public void jSlider_Burn_stateChanged()
	{
		int updateFreq = gui.jTextfield_MCMC_UpdatePlotsFreq_getValue();
		int burn = gui.jSlider_Burn_getValue();
		gui.jTextField_HMM_Burn_setText(String.valueOf(burn));
//		gui.getJFreeChart_MCMCsteps().setBurn(4*burn/updateFreq);
		gui.getJFreeChart_MCMCsteps().setBurn(burn);
	}
	public void jButton_HMM_Result_clicked()
	{
		//int steps = spt_gui.jTextfield_HMM_MCMC_Steps_getValue();
		int updateFreq = gui.jTextfield_MCMC_UpdatePlotsFreq_getValue();
		int burn = gui.jSlider_Burn_getValue();
		
		hmm.mcmc.calculateResults(4*burn);
		double[] results = hmm.mcmc.getResults(); 
		
		//df.setMaximumFractionDigits(3);
		
		gui.jTextField_HMM_Result_D1_setText(BNF.sF(results[0]));
		gui.jTextField_HMM_Result_D2_setText(BNF.sF(results[1]));
		gui.jTextField_HMM_Result_P12_setText(BNF.sF(results[2]));
		gui.jTextField_HMM_Result_P21_setText(BNF.sF(results[3]));
		gui.getJFreeChart_MCMCsteps().setResults(results[0], results[1], results[2], results[3]);
		
		gui.jTextField_HMM_SEG_D1_setText(BNF.sF(results[0]));
		gui.jTextField_HMM_SEG_D2_setText(BNF.sF(results[1]));
		gui.jTextField_HMM_SEG_P12_setText(BNF.sF(results[2]));
		gui.jTextField_HMM_SEG_P21_setText(BNF.sF(results[3]));
		
		haveResult = true;
	}
	public void jCheckBox_HMM_ShowResults_stateChanged()
	{
		boolean show = gui.jCheckBox_HMM_ShowResults_isSelected();
	}
	public void jButton_Segment_clicked()
	{
		segD1 = Double.parseDouble(gui.jTextField_SEG_D1_getText());
		segD2 = Double.parseDouble(gui.jTextField_SEG_D2_getText());
		segP12 = Double.parseDouble(gui.jTextField_SEG_P12_getText());
		segP21 = Double.parseDouble(gui.jTextField_SEG_P21_getText());
		if(gui.jRadioButton_All_isSelected())
			hmm.segment(gui, thinker.getSelectedSet().getTrajs(), new double[]{segD1,segD2,segP12,segP21});
		else
			hmm.segment(thinker.getSelectedTraj(), new double[]{segD1,segD2,segP12,segP21});
		updateSSDChart();
	}
}
