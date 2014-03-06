package direction;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;

import direction.JFreeChartFrequencyPolarChart;
import direction.MyPolarPlot;

import overTime.JFreeChartOverTime;


import main.Thinker;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class GUIDirection extends JFrame {


	Direction frequency;
	private ChartPanel cp_pie = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox chckbxSelectedSet;
	private JCheckBox chckbxSelectedTraj;
	private JCheckBox chckbxAllSets;
	private JTextArea textArea;
	private JCheckBox chckbxTrajLenghtWeight;
	/**
	 * This is the default constructor
	 */
	public GUIDirection(Direction frequency){
		super();
		this.frequency = frequency;
		initialize();
	}

	public JFreeChartFrequencyPolarChart getJFreeChartFrequencyPieChart()
	{
		return (JFreeChartFrequencyPolarChart)cp_pie.getChart();
	}
	public void jPanel_Pie_setVisible(boolean bool)
	{
		cp_pie.setVisible(bool);
	}
	public boolean jCheckBox_SelectedTraj_isSelected()
	{
		return chckbxSelectedTraj.isSelected();
	}
	public boolean jCheckBox_AllTrajsAVG_isSelected()
	{
		return chckbxSelectedSet.isSelected();
	}
	public boolean jCheckBox_AllSets_isSelected()
	{
		return chckbxAllSets.isSelected();
	}
	public boolean jCheckBox_TrajLenghtWeighted_isSelected()
	{
		return chckbxTrajLenghtWeight.isSelected();
	}
	public void textArea_setText(String text)
	{
		textArea.setText(text);
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("Direction");
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			GridBagLayout gbl_jPanel = new GridBagLayout();
			gbl_jPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
			gbl_jPanel.columnWeights = new double[]{1.0};
			gbl_jPanel.rowHeights = new int[]{39, 335, 101};
			jPanel.setLayout(gbl_jPanel);
			jPanel.add(getJPanel1(), gridBagConstraints);
			jPanel.add(getJPanel2(), gridBagConstraints1);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.gridx = 0;
			gbc_textArea.gridy = 2;
			jPanel.add(getTextArea(), gbc_textArea);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			GridBagLayout gbl_jPanel1 = new GridBagLayout();
			gbl_jPanel1.columnWidths = new int[]{0, 119, 74, 0};
			gbl_jPanel1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			jPanel1.setLayout(gbl_jPanel1);
			jPanel1.setPreferredSize(new Dimension(25, 35));
			GridBagConstraints gbc_chckbxSelectedTraj = new GridBagConstraints();
			gbc_chckbxSelectedTraj.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxSelectedTraj.gridx = 0;
			gbc_chckbxSelectedTraj.gridy = 0;
			jPanel1.add(getChckbxSelectedTraj(), gbc_chckbxSelectedTraj);
			GridBagConstraints gbc_chckbxSelectedSet = new GridBagConstraints();
			gbc_chckbxSelectedSet.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxSelectedSet.gridx = 1;
			gbc_chckbxSelectedSet.gridy = 0;
			jPanel1.add(getChckbxSelectedSet(), gbc_chckbxSelectedSet);
			GridBagConstraints gbc_chckbxAllSets = new GridBagConstraints();
			gbc_chckbxAllSets.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxAllSets.gridx = 2;
			gbc_chckbxAllSets.gridy = 0;
			jPanel1.add(getChckbxAllSets(), gbc_chckbxAllSets);
			GridBagConstraints gbc_chckbxTrajLenghtWeight = new GridBagConstraints();
			gbc_chckbxTrajLenghtWeight.gridx = 3;
			gbc_chckbxTrajLenghtWeight.gridy = 0;
			jPanel1.add(getChckbxTrajLenghtWeight(), gbc_chckbxTrajLenghtWeight);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setPreferredSize(new Dimension(25, 25));
			GridBagConstraints gBC = new GridBagConstraints();
			gBC.fill = GridBagConstraints.BOTH;
			gBC.gridx = 0;
			gBC.gridy = 0;
			gBC.weighty = 1.0D;
			gBC.weightx = 1.0D;
			jPanel2.add(getCp_Pie(), gBC);
			
			
			
		}
		return jPanel2;
	}

	private ChartPanel getCp_Pie() {
		if (cp_pie == null) {
			cp_pie = new ChartPanel(new JFreeChartFrequencyPolarChart(new MyPolarPlot()));
		}
		return cp_pie;
	}

	private JCheckBox getChckbxSelectedSet() {
		if (chckbxSelectedSet == null) {
			chckbxSelectedSet = new JCheckBox("Selected set");
			chckbxSelectedSet.setSelected(true);
			chckbxSelectedSet.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.jComboBox_DirectionChart_clicked();
				}
			});
			
		}
		return chckbxSelectedSet;
	}
	private JCheckBox getChckbxSelectedTraj() {
		if (chckbxSelectedTraj == null) {
			chckbxSelectedTraj = new JCheckBox("Selected Traj");
			chckbxSelectedTraj.setSelected(true);
			chckbxSelectedTraj.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					frequency.jComboBox_DirectionChart_clicked();
				}
			});
		}
		return chckbxSelectedTraj;
	}
	private JCheckBox getChckbxAllSets() {
		if (chckbxAllSets == null) {
			chckbxAllSets = new JCheckBox("All sets");
			chckbxAllSets.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.jComboBox_DirectionChart_clicked();
				}
			});
		}
		return chckbxAllSets;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	private JCheckBox getChckbxTrajLenghtWeight() {
		if (chckbxTrajLenghtWeight == null) {
			chckbxTrajLenghtWeight = new JCheckBox("Traj lenght weight");
			chckbxTrajLenghtWeight.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.jComboBox_DirectionChart_clicked();
				}
			});
		}
		return chckbxTrajLenghtWeight;
	}
}
