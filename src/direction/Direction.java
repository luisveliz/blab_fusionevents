package direction;

import java.util.ArrayList;

import bTools.BMaths;
import bTools.BNF;

import main.Thinker;
import data.TrajSet;

public class Direction
{
	Thinker thinker;
	GUIDirection gui;
	
	
	public Direction(Thinker thinker)
	{
		this.thinker = thinker;
		gui = new GUIDirection(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void jComboBox_DirectionChart_clicked()
	{
		updateDirectionChart();
//		if(thinker.info!=null)
//			thinker.info.updateGlobalInfo();
	}
	public void updateDirectionChart()
	{
		StringBuffer sb =  new StringBuffer();
		
		boolean[] trajTypeSelection = thinker.getTrajTypeSelection();
		boolean selectedSet = gui.jCheckBox_AllTrajsAVG_isSelected();
		boolean selectedTraj = gui.jCheckBox_SelectedTraj_isSelected();
		boolean allSets = gui.jCheckBox_AllSets_isSelected();
		boolean weighted = gui.jCheckBox_TrajLenghtWeighted_isSelected();
		
		double[] probability;
		
		double[] angle = new double[8];
		for(int i=0;i<angle.length;i++)
			angle[i]=(i+1)*360/8 - 22.5;
		
		gui.getJFreeChartFrequencyPieChart().clearSeries();
		
		
		sb.append("Probability or direction preferences:\n");
		sb.append("Direction\t0°-45°\t45°-90°\t90°-135°\t135°-180°\t180°-225°\t225°-270°\t270°-335°\t335°-360°\tsum\n");
		if(selectedTraj)
		{
			probability = thinker.getLastSelectedTraj().getAngleProbability();
			for(int i=0;i<probability.length;i++)
			{
				System.out.println("i:"+probability[i]);
			}
			sb.append("Selected Traj\t"+BNF.sF(probability[0])+"\t"+BNF.sF(probability[1])+"\t"+BNF.sF(probability[2])+"\t"+BNF.sF(probability[3])+"\t"+
										BNF.sF(probability[4])+"\t"+BNF.sF(probability[5])+"\t"+BNF.sF(probability[6])+"\t"+BNF.sF(probability[7])+"\t"+BMaths.sum(probability)+"\n");
			gui.getJFreeChartFrequencyPieChart().setSelectedTraj(angle, probability);
			sb.append("Selected traj:\n");
		}
		if(selectedSet)
		{
			probability = thinker.getSelectedSet().getAVGAngleProbability(trajTypeSelection, weighted);
			sb.append("Selected Set\t"+BNF.sF(probability[0])+"\t"+BNF.sF(probability[1])+"\t"+BNF.sF(probability[2])+"\t"+BNF.sF(probability[3])+"\t"+
									   BNF.sF(probability[4])+"\t"+BNF.sF(probability[5])+"\t"+BNF.sF(probability[6])+"\t"+BNF.sF(probability[7])+"\t"+BMaths.sum(probability)+"\n");
			gui.getJFreeChartFrequencyPieChart().setSelectedSet(angle, probability);
		}
		if(allSets)
		{
			ArrayList<TrajSet> sets = thinker.getSets();
			for(int i=0;i<sets.size();i++)
			{
				probability = sets.get(i).getAVGAngleProbability(trajTypeSelection,weighted);
				sb.append("Set "+sets.get(i).getUserName()+"\t"+BNF.sF(probability[0])+"\t"+BNF.sF(probability[1])+"\t"+BNF.sF(probability[2])+"\t"+BNF.sF(probability[3])+"\t"+
																BNF.sF(probability[4])+"\t"+BNF.sF(probability[5])+"\t"+BNF.sF(probability[6])+"\t"+BNF.sF(probability[7])+"\t"+BMaths.sum(probability)+"\n");
				gui.getJFreeChartFrequencyPieChart().addTrajSetSerie(angle, probability, sets.get(i).getUserName());
			}
		}
		gui.textArea_setText(sb.toString());
	}
}
