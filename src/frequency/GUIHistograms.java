package frequency;

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

public class GUIHistograms extends JFrame {


	Histograms frequency;
	private ChartPanel cp_frequencies = null;
	private ChartPanel cp_pie = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JComboBox jComboBox_Chart = null;
	private JCheckBox jCheckBox_log = null;
	private JSpinner jSpinner_NIntervals = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox chckbxSelectedSet;
	private JCheckBox chckbxSelectedTraj;
	private JCheckBox chckbxNormalized;
	private JCheckBox chckbxAllSets;
	/**
	 * This is the default constructor
	 */
	public GUIHistograms(Histograms frequency){
		super();
		this.frequency = frequency;
		initialize();
	}

	public JFreeChartFrequencyBarChart getJFreeChart()
	{
		return (JFreeChartFrequencyBarChart)cp_frequencies.getChart();
	}
	public JFreeChartFrequencyPolarChart getJFreeChartFrequencyPieChart()
	{
		return (JFreeChartFrequencyPolarChart)cp_pie.getChart();
	}
	public int jComboBox_getSelectedIndex()
	{
		return jComboBox_Chart.getSelectedIndex();		
	}
	public int jSpinner_NBins_getValue()
	{
		return ((Integer)jSpinner_NIntervals.getValue()).intValue();
	}
	public boolean jCheckBox_Log_isSelected()
	{
		return jCheckBox_log.isSelected();		
	}
	public boolean jCheckBox_Normalized_isSelected()
	{
		return chckbxNormalized.isSelected();		
	}
	public void jSpinner_setEnabled(boolean bool)
	{
		jSpinner_NIntervals.setEnabled(bool);
	}
	public void jCheckBox_Log_setEnabled(boolean bool)
	{
		jCheckBox_log.setEnabled(bool);
	}
	public void jCheckBox_Log_setSelected(boolean bool)
	{
		jCheckBox_log.setSelected(bool);
	}
	public void jCheckBox_Normalized_setEnabled(boolean bool)
	{
		chckbxNormalized.setEnabled(bool);
	}
	public void jCheckBox_Normalized_setSelected(boolean bool)
	{
		chckbxNormalized.setSelected(bool);
	}
	public void jPanel_Frequencies_setVisible(boolean bool)
	{
		cp_frequencies.setVisible(bool);
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
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("Frecuencies");
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
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			GridBagLayout gbl_jPanel = new GridBagLayout();
			gbl_jPanel.rowHeights = new int[]{69, 0};
			jPanel.setLayout(gbl_jPanel);
			jPanel.add(getJPanel1(), gridBagConstraints);
			jPanel.add(getJPanel2(), gridBagConstraints1);
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 0;
			jPanel1 = new JPanel();
			GridBagLayout gbl_jPanel1 = new GridBagLayout();
			gbl_jPanel1.columnWidths = new int[]{173, 0, 119, 74, 92};
			gbl_jPanel1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			jPanel1.setLayout(gbl_jPanel1);
			jPanel1.setPreferredSize(new Dimension(25, 35));
			jPanel1.add(getJComboBox_Chart(), gridBagConstraints2);
			GridBagConstraints gbc_chckbxNormalized = new GridBagConstraints();
			gbc_chckbxNormalized.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNormalized.gridx = 1;
			gbc_chckbxNormalized.gridy = 0;
			jPanel1.add(getChckbxNormalized(), gbc_chckbxNormalized);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridy = 0;
			jPanel1.add(getJCheckBox_log(), gridBagConstraints4);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("N\u00B0 Bins:");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel1.add(jLabel, gridBagConstraints6);
			GridBagConstraints gbc_jSpinner_NIntervals = new GridBagConstraints();
			gbc_jSpinner_NIntervals.insets = new Insets(0, 0, 5, 0);
			gbc_jSpinner_NIntervals.anchor = GridBagConstraints.WEST;
			gbc_jSpinner_NIntervals.gridx = 4;
			gbc_jSpinner_NIntervals.weightx = 1.0D;
			gbc_jSpinner_NIntervals.gridy = 0;
			jPanel1.add(getJSpinner_NIntervals(), gbc_jSpinner_NIntervals);
			GridBagConstraints gbc_chckbxSelectedTraj = new GridBagConstraints();
			gbc_chckbxSelectedTraj.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxSelectedTraj.gridx = 1;
			gbc_chckbxSelectedTraj.gridy = 1;
			jPanel1.add(getChckbxSelectedTraj(), gbc_chckbxSelectedTraj);
			GridBagConstraints gbc_chckbxSelectedSet = new GridBagConstraints();
			gbc_chckbxSelectedSet.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxSelectedSet.gridx = 2;
			gbc_chckbxSelectedSet.gridy = 1;
			jPanel1.add(getChckbxSelectedSet(), gbc_chckbxSelectedSet);
			GridBagConstraints gbc_chckbxAllSets = new GridBagConstraints();
			gbc_chckbxAllSets.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxAllSets.gridx = 3;
			gbc_chckbxAllSets.gridy = 1;
			jPanel1.add(getChckbxAllSets(), gbc_chckbxAllSets);
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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx=0;
			gridBagConstraints3.gridy=0;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.weightx = 1.0D;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setPreferredSize(new Dimension(25, 25));
			jPanel2.add(getCp_Frequencies(), gridBagConstraints3);
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
	private ChartPanel getCp_Frequencies() {
		if (cp_frequencies == null) {
			cp_frequencies = new ChartPanel(new JFreeChartFrequencyBarChart(new XYPlot()));
			cp_frequencies.setVisible(false);
		}
		return cp_frequencies;
	}

	private ChartPanel getCp_Pie() {
		if (cp_pie == null) {
			cp_pie = new ChartPanel(new JFreeChartFrequencyPolarChart(new MyPolarPlot()));
			cp_pie.setVisible(true);
		}
		return cp_pie;
	}
	/**
	 * This method initializes jComboBox_Chart	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_Chart() {
		if (jComboBox_Chart == null) {
			jComboBox_Chart = new JComboBox(new String[]{"Displacements", "D micro", "D model","Velocity"});
			jComboBox_Chart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frequency.jComboBox_FrequencyChart_clicked();
				}
			});
		}
		return jComboBox_Chart;
	}

	/**
	 * This method initializes jCheckBox_log	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_log() {
		if (jCheckBox_log == null) {
			jCheckBox_log = new JCheckBox();
			jCheckBox_log.setText("Log Domain");
			jCheckBox_log.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					frequency.jComboBox_FrequencyChart_clicked();
				}
			});
		}
		return jCheckBox_log;
	}

	/**
	 * This method initializes jSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_NIntervals() {
		if (jSpinner_NIntervals == null) {
			jSpinner_NIntervals = new JSpinner(new SpinnerNumberModel(10,2,100,2));
			jSpinner_NIntervals.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					frequency.jComboBox_FrequencyChart_clicked();
				}
			});
		}
		return jSpinner_NIntervals;
	}

	private JCheckBox getChckbxSelectedSet() {
		if (chckbxSelectedSet == null) {
			chckbxSelectedSet = new JCheckBox("Selected set");
			chckbxSelectedSet.setSelected(true);
			chckbxSelectedSet.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.jComboBox_FrequencyChart_clicked();
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
					frequency.jComboBox_FrequencyChart_clicked();
				}
			});
		}
		return chckbxSelectedTraj;
	}
	private JCheckBox getChckbxNormalized() {
		if (chckbxNormalized == null) {
			chckbxNormalized = new JCheckBox("Normalized");
			chckbxNormalized.setSelected(true);
			chckbxNormalized.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.updateFrequencyChart();
				}
			});
			
		}
		return chckbxNormalized;
	}
	private JCheckBox getChckbxAllSets() {
		if (chckbxAllSets == null) {
			chckbxAllSets = new JCheckBox("All sets");
			chckbxAllSets.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					frequency.jComboBox_FrequencyChart_clicked();
				}
			});
		}
		return chckbxAllSets;
	}
}
