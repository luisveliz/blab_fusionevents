package frequency;

import ij.IJ;

import java.util.ArrayList;
import java.util.Vector;
import main.Thinker;
import data.TrajSet;
import data.Trajectory;

public class Histograms
{
	Thinker thinker;
	GUIHistograms gui;
	
	
	public Histograms(Thinker thinker)
	{
		this.thinker = thinker;
		gui = new GUIHistograms(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void openGUI()
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
	}
	public void jComboBox_FrequencyChart_clicked()
	{
		updateFrequencyChart();
//		if(thinker.info!=null)
//			thinker.info.updateGlobalInfo();
	}
	public void updateFrequencyChart()
	{
		boolean[] trajTypeSelection = thinker.getTrajTypeSelection();
		int nbins = gui.jSpinner_NBins_getValue();
		boolean normalized = gui.jCheckBox_Normalized_isSelected();
		boolean logAxis = gui.jCheckBox_Log_isSelected();
		
		System.out.println("Selected index:"+gui.jComboBox_getSelectedIndex());
		switch(gui.jComboBox_getSelectedIndex())
		{
			case 0:
				gui.jSpinner_setEnabled(true);
				gui.jCheckBox_Log_setEnabled(true);
				gui.jPanel_Frequencies_setVisible(true);
				gui.jPanel_Pie_setVisible(false);
				gui.getJFreeChart().setData("Displacements", thinker.getSelectedTraj().getDisplacements(), nbins, logAxis);
				gui.getJFreeChart().setNormalized(normalized);
				break;
			case 1:
				gui.jSpinner_setEnabled(true);
				gui.jCheckBox_Log_setEnabled(true);
				gui.jPanel_Frequencies_setVisible(true);
				gui.jPanel_Pie_setVisible(false);
				gui.getJFreeChart().setData("D micro", thinker.getSelectedSet().getMicroDiffusion(trajTypeSelection), nbins, logAxis);
				gui.getJFreeChart().setNormalized(normalized);
				break;
			case 2:
				gui.jSpinner_setEnabled(true);
				gui.jCheckBox_Log_setEnabled(true);
				gui.jPanel_Frequencies_setVisible(true);
				gui.jPanel_Pie_setVisible(false);
				gui.getJFreeChart().setData("D model", thinker.getSelectedSet().getModelDiffusion(trajTypeSelection), nbins, logAxis);
				gui.getJFreeChart().setNormalized(normalized);
				break;
			case 3:
				gui.jSpinner_setEnabled(true);
				gui.jCheckBox_Log_setEnabled(true);
				gui.jPanel_Frequencies_setVisible(true);
				gui.jPanel_Pie_setVisible(false);
				gui.getJFreeChart().setData("Velocity", thinker.getSelectedSet().getVelocity(trajTypeSelection), nbins, logAxis);
				gui.getJFreeChart().setNormalized(normalized);
				break;
			default:System.err.println("DEFUALT comboboxgetselectedindex frecuencies!!!!!!!!!!!");break;
		}
		
	}
}
