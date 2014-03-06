package twoStateDiffusion;


import ij.plugin.GifWriter;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartPanel;

import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

public class GUI extends JFrame 
{
	private HMMAnalysis hmma;
	private TrajectoriesTableModelHMM trajectoriesTableModel_HMMreal;
	private TrajectoriesTableModelHMM trajectoriesTableModel_HMM;
	
	private HMMStateSequenceCanvas originalStateCanvas;
	private HMMStateSequenceCanvas guessStateCanvas;

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel_tau = null;
	private JLabel jLabel_d1 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel_HMM_D2 = null;
	private JTabbedPane jTabbedPane1 = null;
	private JPanel jPanel15 = null;
	private JPanel jPanel_HMM_SSD_Plot = null;
	private ChartPanel cp_HMM_SSD = null;
	private JPanel jPanel_HMM_MCMC = null;
	private JPanel jPanel23 = null;
	private JLabel jLabel_HMM_MCMC_nsteps = null;
	private JTextField jTextField_HMM_MCMC_steps = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel_HMM_MCMC_initials = null;
	private JLabel jLabel_D1 = null;
	private JLabel jLabel_HMM_MCMC_D2 = null;
	private JLabel jLabel_P12 = null;
	private JLabel jLabel_P21 = null;
	private JButton jButton_HMM_MCMC = null;
	private JProgressBar jProgressBar_HMM_MCMC = null;
	private JButton jButton_HMM_MCMC_updateScale = null;
	private JLabel jLabel_HMM_MCMC_stepsUpdatePlots = null;
	private JTextField jTextField_HMM_UpdateEach = null;
	private JTextField jTextField_HMM_MCMC_InitialD1 = null;
	private JTextField jTextField_HMM_MCMC_InitialD2 = null;
	private JTextField jTextField_HMM_MCMC_ScaleD1 = null;
	private JTextField jTextField_HMM_MCMC_ScaleD2 = null;
	private JTextField jTextField_HMM_MCMC_InitialP12 = null;
	private JTextField jTextField_HMM_MCMC_ScaleP12 = null;
	private JTextField jTextField_HMM_MCMC_InitialP21 = null;
	private JTextField jTextField_HMM_MCMC_ScaleP21 = null;
	private JLabel jLabel_HMM = null;
	private JTextField jTextField_HMM_MCMC_AccRatioD1 = null;
	private JTextField jTextField_HMM_MCMC_AccRatioD2 = null;
	private JTextField jTextField_HMM_MCMC_AccRatioP12 = null;
	private JTextField jTextField_HMM_MCMC_AccRatioP21 = null;
	private JLabel jLabel14 = null;
	private JSlider jSlider_Burn = null;
	private JButton jButton_HMM_CalcResult = null;
	private JLabel jLabel15 = null;
	private JTextField jTextField_HMM_Result_D1 = null;
	private JTextField jTextField_HMM_Result_D2 = null;
	private JTextField jTextField_HMM_Result_P12 = null;
	private JTextField jTextField_HMM_Result_P21 = null;
	private JButton jButton_HMM_Stop = null;
	private JCheckBox jCheckBox_HMM_ShowResults = null;
	private JTextField jTextField_HMM_Burn = null;
	private JPanel jPanel_HMM_MCMC_LogLike = null;
	private ChartPanel cp_HMM_MCMC_loglike = null;
	private JPanel jPanel_HMM_Seg = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JLabel jLabel = null;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JTextField textField_Seg_D1;
	private JLabel label_1;
	private JTextField textField_Seg_D2;
	private JLabel label_2;
	private JTextField textField_Seg_P12;
	private JLabel label_3;
	private JTextField textField_Seg_P21;
	private JProgressBar progressBar;
	private JButton button;
	private JRadioButton radioButton;
	private JRadioButton jRadioButton_All;
	private JCheckBox chckbxAutoScale;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_5;
	private JTextField textField_GuessLowState;
	private JLabel lblSimulated;
	private JLabel lblGuess;
	private JLabel lblStateSequence;
	private JTextField textField_SimLowState;
	private JTextField textField_SimHighState;
	private JTextField textField_GuessHighState;
	private JLabel lblLowState;
	private JLabel lblHighState;
	private JTextField textField_Success;
	private JLabel lblSucces;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSeparator separator;
	
	
	
	/**
	 * 
	 * This is the default constructor
	 */
	public GUI(HMMAnalysis hmma) 
	{
		super();
		this.hmma = hmma;
		initialize();
	}

	/*------------------------------------------------------------*/
	public void setDataInTrajectoryTableModel_HMMreal(Object data, int rowIndex, int columnIndex)
	{
		trajectoriesTableModel_HMMreal.setValueAt(data, rowIndex, columnIndex);		
	}
	public void addRowInTrajectoryTableModel_HMMreal(Object[] rowData)
	{
		trajectoriesTableModel_HMMreal.addRow(rowData);
	}
	public void setDataInTrajectoryTableModel_HMM(Object data, int rowIndex, int columnIndex)
	{
		trajectoriesTableModel_HMM.setValueAt(data, rowIndex, columnIndex);		
	}
	
	public void addRowInTrajectoryTableModel_HMM(Object[] rowData)
	{
		trajectoriesTableModel_HMM.addRow(rowData);
	}
	public JFreeChartHMMSSD getJFreeChart_HMM_SSD()
	{
		return (JFreeChartHMMSSD)this.cp_HMM_SSD.getChart();
	}
	/*--------MCMC--------*/
	public double jTextfield_MCMC_InitialD1_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_InitialD1.getText());
	}
	public double jTextfield_MCMC_InitialD2_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_InitialD2.getText());
	}
	public double jTextfield_MCMC_InitialP12_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_InitialP12.getText());
	}
	public double jTextfield_MCMC_InitialP21_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_InitialP21.getText());
	}
	public double jTextfield_HMM_MCMC_ScaleD1_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_ScaleD1.getText());
	}
	public double jTextfield_MCMC_ScaleD2_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_ScaleD2.getText());
	}
	public double jTextfield_MCMC_ScaleP12_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_ScaleP12.getText());
	}
	public double jTextfield_MCMC_ScaleP21_getValue()
	{
		return Double.parseDouble(jTextField_HMM_MCMC_ScaleP21.getText());
	}
	public int jTextfield_MCMC_Steps_getValue()
	{
		return Integer.parseInt(jTextField_HMM_MCMC_steps.getText());
	}
	public int jTextfield_MCMC_UpdatePlotsFreq_getValue()
	{
		return Integer.parseInt(jTextField_HMM_UpdateEach.getText());
	}
	public void jProgressBar_HMM_MCMC_setValue(int value)
	{
		jProgressBar_HMM_MCMC.setValue(value);
		jProgressBar_HMM_MCMC.setString(String.valueOf(value)+"%");
	}
	public void jButton_MCMC_setText(String text)
	{
		jButton_HMM_MCMC.setText(text);
	}
	public void hmm_MCMC_setEnabled(boolean bool)
	{
		jTextField_HMM_MCMC_InitialD1.setEnabled(bool);
		jTextField_HMM_MCMC_InitialD2.setEnabled(bool);
		jTextField_HMM_MCMC_InitialP12.setEnabled(bool);
		jTextField_HMM_MCMC_InitialP21.setEnabled(bool);
		jTextField_HMM_MCMC_steps.setEnabled(bool);
	}
	public void jTextfield_HMM_MCMC_AccRatioD1_setValue(String value)
	{
		jTextField_HMM_MCMC_AccRatioD1.setText(value);
	}
	public void jTextfield_HMM_MCMC_AccRatioD2_setValue(String value)
	{
		jTextField_HMM_MCMC_AccRatioD2.setText(value);
	}
	public void jTextfield_HMM_MCMC_AccRatioP12_setValue(String value)
	{
		jTextField_HMM_MCMC_AccRatioP12.setText(value);
	}
	public void jTextfield_HMM_MCMC_AccRatioP21_setValue(String value)
	{
		jTextField_HMM_MCMC_AccRatioP21.setText(value);
	}
	
	public void jSlider_Burn_SetMax(int max)
	{
		jSlider_Burn.setMaximum(max);		
	}
	public int jSlider_Burn_getValue()
	{
		return jSlider_Burn.getValue();
	}
	public void jTextField_HMM_Burn_setText(String text)
	{
		jTextField_HMM_Burn.setText(text);
	}
	public void jTextField_HMM_Result_D1_setText(String text)
	{
		jTextField_HMM_Result_D1.setText(text);
	}
	public void jTextField_HMM_Result_D2_setText(String text)
	{
		jTextField_HMM_Result_D2.setText(text);
	}
	public void jTextField_HMM_Result_P12_setText(String text)
	{
		jTextField_HMM_Result_P12.setText(text);
	}
	public void jTextField_HMM_Result_P21_setText(String text)
	{
		jTextField_HMM_Result_P21.setText(text);
	}
	public JFreeChartHMMLogLikeVsStep getJFreeChart_MCMCsteps()
	{
		return (JFreeChartHMMLogLikeVsStep)this.cp_HMM_MCMC_loglike.getChart();
	}

	public void jTextField_HMM_SEG_D1_setText(String text)
	{
		textField_Seg_D1.setText(text);
	}
	public void jTextField_HMM_SEG_D2_setText(String text)
	{
		textField_Seg_D2.setText(text);
	}
	public void jTextField_HMM_SEG_P12_setText(String text)
	{
		textField_Seg_P12.setText(text);
	}
	public void jTextField_HMM_SEG_P21_setText(String text)
	{
		textField_Seg_P21.setText(text);
	}
	public String jTextField_SEG_D1_getText()
	{
		return textField_Seg_D1.getText();
	}
	public String jTextField_SEG_D2_getText()
	{
		return textField_Seg_D2.getText();
	}
	public String jTextField_SEG_P12_getText()
	{
		return textField_Seg_P12.getText();
	}
	public String jTextField_SEG_P21_getText()
	{
		return textField_Seg_P21.getText();
	}
	public boolean jCheckBox_HMM_ShowResults_isSelected()
	{
		return jCheckBox_HMM_ShowResults.isSelected();
	}
	public boolean jRadioButton_All_isSelected()
	{
		return jRadioButton_All.isSelected();
	}
	
	
	public HMMStateSequenceCanvas getOriginalStateCanvas()
	{
		return originalStateCanvas;
	}
	

	public HMMStateSequenceCanvas getGuessStateCanvas()
	{
		return guessStateCanvas;
	}
	
	public void jTextField_SimLowState_setText(String text)
	{
		textField_SimLowState.setText(text);
	}
	public void jTextField_SimHighState_setText(String text)
	{
		textField_SimHighState.setText(text);
	}
	public void jTextField_GuessLowState_setText(String text)
	{
		textField_GuessLowState.setText(text);
	}
	public void jTextField_GuessHighState_setText(String text)
	{
		textField_GuessHighState.setText(text);
	}
	public void jTextField_Success_setText(String text)
	{
		textField_Success.setText(text);
	}
	
	/*------------------------------------------------------------*/
	
	
	
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setSize(717, 429);
		this.setMinimumSize(new Dimension(717, 400));
		this.setContentPane(getJContentPane());
		this.setTitle("2-State Diffusion");
		this.setVisible(true);
		
		ButtonGroup bg = new ButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			GridBagLayout gbl_jContentPane = new GridBagLayout();
			gbl_jContentPane.columnWidths = new int[]{784, 0};
			gbl_jContentPane.rowHeights = new int[]{127, 0};
			gbl_jContentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_jContentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			jContentPane.setLayout(gbl_jContentPane);
			GridBagConstraints gbc_jTabbedPane1 = new GridBagConstraints();
			gbc_jTabbedPane1.weighty = 1.0;
			gbc_jTabbedPane1.weightx = 1.0;
			gbc_jTabbedPane1.fill = GridBagConstraints.BOTH;
			gbc_jTabbedPane1.gridx = 0;
			gbc_jTabbedPane1.gridy = 0;
			jContentPane.add(getJTabbedPane1(), gbc_jTabbedPane1);
		}
		return jContentPane;
	}

	/**
	 * This method initializes trajectoriesTableModel_HMMreal	
	 * 	
	 * @return gui.TrajectoriesTableModelHMM	
	 */
	private TrajectoriesTableModelHMM getTrajectoriesTableModel_HMMreal() {
		if (trajectoriesTableModel_HMMreal == null) {
			trajectoriesTableModel_HMMreal = new TrajectoriesTableModelHMM();
		}
		return trajectoriesTableModel_HMMreal;
	}

	/**
	 * This method initializes trajectoriesTableModel_HMM	
	 * 	
	 * @return gui.TrajectoriesTableModelHMM	
	 */
	private TrajectoriesTableModelHMM getTrajectoriesTableModel_HMM() {
		if (trajectoriesTableModel_HMM == null) {
			trajectoriesTableModel_HMM = new TrajectoriesTableModelHMM();
		}
		return trajectoriesTableModel_HMM;
	}

	/**
	 * This method initializes jTabbedPane1	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("SSD", null, getJPanel15(), null);
			jTabbedPane1.addTab("MCMC", null, getJPanel_HMM_MCMC(), null);
			jTabbedPane1.addTab("Segmentation", null, getJPanel_HMM_Seg(), null);
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel15	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel15() {
		if (jPanel15 == null) {
			GridBagConstraints gridBagConstraints216 = new GridBagConstraints();
			gridBagConstraints216.fill = GridBagConstraints.BOTH;
			gridBagConstraints216.gridy = 0;
			gridBagConstraints216.weightx = 1.0D;
			gridBagConstraints216.weighty = 1.0D;
			gridBagConstraints216.gridx = 0;
			jPanel15 = new JPanel();
			jPanel15.setLayout(new GridBagLayout());
			jPanel15.setPreferredSize(new Dimension(25, 25));
			jPanel15.add(getJPanel_HMM_SSD_Plot(), gridBagConstraints216);
		}
		return jPanel15;
	}

	/**
	 * This method initializes jPanel_HMM_SSD_Plot	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_HMM_SSD_Plot() {
		if (jPanel_HMM_SSD_Plot == null) {
			GridBagConstraints gBC_ssd = new GridBagConstraints();
			gBC_ssd.fill = GridBagConstraints.BOTH;
			gBC_ssd.weighty = 1.0D;
			gBC_ssd.weightx = 1.0D;
			jPanel_HMM_SSD_Plot = new JPanel();
			jPanel_HMM_SSD_Plot.setLayout(new GridBagLayout());
			jPanel_HMM_SSD_Plot.setPreferredSize(new Dimension(25, 25));
			jPanel_HMM_SSD_Plot.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "SSD", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
			jPanel_HMM_SSD_Plot.add(getCp_HMM_SSD(), gBC_ssd);
		}
		return jPanel_HMM_SSD_Plot;
	}

	/**
	 * This method initializes cp_HMM_SSD	
	 * 	
	 * @return org.jfree.chart.ChartPanel	
	 */
	private ChartPanel getCp_HMM_SSD() {
		if (cp_HMM_SSD == null) {
			cp_HMM_SSD = new ChartPanel(new JFreeChartHMMSSD(new XYPlot()));
		}
		return cp_HMM_SSD;
	}

	/**
	 * This method initializes jPanel_HMM_MCMC	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_HMM_MCMC() {
		if (jPanel_HMM_MCMC == null) {
			GridBagConstraints gridBagConstraints93 = new GridBagConstraints();
			gridBagConstraints93.fill = GridBagConstraints.BOTH;
			gridBagConstraints93.gridy = 0;
			gridBagConstraints93.weightx = 1.0D;
			gridBagConstraints93.weighty = 1.0D;
			gridBagConstraints93.gridx = 1;
			GridBagConstraints gridBagConstraints217 = new GridBagConstraints();
			gridBagConstraints217.weighty = 1.0;
			gridBagConstraints217.fill = GridBagConstraints.BOTH;
			gridBagConstraints217.gridwidth = 1;
			gridBagConstraints217.gridx = 0;
			gridBagConstraints217.gridy = 0;
			gridBagConstraints217.weightx = 1.0D;
			gridBagConstraints217.gridheight = 1;
			jPanel_HMM_MCMC = new JPanel();
			jPanel_HMM_MCMC.setLayout(new GridBagLayout());
			jPanel_HMM_MCMC.add(getJPanel23(), gridBagConstraints217);
			jPanel_HMM_MCMC.add(getJPanel_HMM_MCMC_LogLike(), gridBagConstraints93);
		}
		return jPanel_HMM_MCMC;
	}

	/**
	 * This method initializes jPanel23	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel23() {
		if (jPanel23 == null) {
			GridBagConstraints gridBagConstraints277 = new GridBagConstraints();
			gridBagConstraints277.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints277.gridy = 10;
			gridBagConstraints277.weightx = 1.0;
			gridBagConstraints277.gridx = 4;
			GridBagConstraints gridBagConstraints276 = new GridBagConstraints();
			gridBagConstraints276.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints276.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints276.gridy = 10;
			gridBagConstraints276.weightx = 1.0;
			gridBagConstraints276.gridx = 3;
			GridBagConstraints gridBagConstraints275 = new GridBagConstraints();
			gridBagConstraints275.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints275.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints275.gridy = 10;
			gridBagConstraints275.weightx = 1.0;
			gridBagConstraints275.gridx = 2;
			GridBagConstraints gridBagConstraints274 = new GridBagConstraints();
			gridBagConstraints274.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints274.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints274.gridy = 10;
			gridBagConstraints274.weightx = 1.0;
			gridBagConstraints274.gridx = 1;
			GridBagConstraints gridBagConstraints273 = new GridBagConstraints();
			gridBagConstraints273.anchor = GridBagConstraints.EAST;
			gridBagConstraints273.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints273.gridx = 0;
			gridBagConstraints273.gridy = 10;
			jLabel15 = new JLabel();
			jLabel15.setText("Result:");
			GridBagConstraints gridBagConstraints271 = new GridBagConstraints();
			gridBagConstraints271.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints271.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints271.gridx = 1;
			gridBagConstraints271.gridy = 9;
			gridBagConstraints271.weightx = 1.0;
			GridBagConstraints gridBagConstraints270 = new GridBagConstraints();
			gridBagConstraints270.anchor = GridBagConstraints.EAST;
			gridBagConstraints270.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints270.gridx = 0;
			gridBagConstraints270.gridy = 9;
			jLabel14 = new JLabel();
			jLabel14.setText("Burn:");
			GridBagConstraints gridBagConstraints233 = new GridBagConstraints();
			gridBagConstraints233.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints233.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints233.gridy = 2;
			gridBagConstraints233.weightx = 1.0;
			gridBagConstraints233.gridx = 4;
			GridBagConstraints gridBagConstraints232 = new GridBagConstraints();
			gridBagConstraints232.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints232.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints232.gridy = 1;
			gridBagConstraints232.weightx = 1.0;
			gridBagConstraints232.gridx = 4;
			GridBagConstraints gridBagConstraints231 = new GridBagConstraints();
			gridBagConstraints231.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints231.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints231.gridy = 2;
			gridBagConstraints231.weightx = 1.0;
			gridBagConstraints231.gridx = 3;
			GridBagConstraints gridBagConstraints226 = new GridBagConstraints();
			gridBagConstraints226.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints226.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints226.gridy = 1;
			gridBagConstraints226.weightx = 1.0;
			gridBagConstraints226.gridx = 3;
			GridBagConstraints gridBagConstraints221 = new GridBagConstraints();
			gridBagConstraints221.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints221.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints221.gridy = 2;
			gridBagConstraints221.weightx = 1.0;
			gridBagConstraints221.gridx = 2;
			GridBagConstraints gridBagConstraints220 = new GridBagConstraints();
			gridBagConstraints220.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints220.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints220.gridy = 2;
			gridBagConstraints220.weightx = 1.0;
			gridBagConstraints220.gridx = 1;
			GridBagConstraints gridBagConstraints171 = new GridBagConstraints();
			gridBagConstraints171.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints171.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints171.gridy = 1;
			gridBagConstraints171.weightx = 1.0;
			gridBagConstraints171.gridx = 2;
			GridBagConstraints gridBagConstraints134 = new GridBagConstraints();
			gridBagConstraints134.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints134.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints134.gridy = 1;
			gridBagConstraints134.weightx = 1.0;
			gridBagConstraints134.weighty = 0.0D;
			gridBagConstraints134.gridx = 1;
			GridBagConstraints gridBagConstraints235 = new GridBagConstraints();
			gridBagConstraints235.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints235.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints235.gridx = 1;
			gridBagConstraints235.gridy = 7;
			gridBagConstraints235.weightx = 0.0D;
			gridBagConstraints235.gridwidth = 4;
			GridBagConstraints gridBagConstraints230 = new GridBagConstraints();
			gridBagConstraints230.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints230.gridx = 4;
			gridBagConstraints230.gridy = 0;
			jLabel_P21 = new JLabel();
			jLabel_P21.setText("P21");
			GridBagConstraints gridBagConstraints229 = new GridBagConstraints();
			gridBagConstraints229.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints229.gridx = 3;
			gridBagConstraints229.gridy = 0;
			jLabel_P12 = new JLabel();
			jLabel_P12.setText("P12");
			GridBagConstraints gridBagConstraints228 = new GridBagConstraints();
			gridBagConstraints228.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints228.gridx = 2;
			gridBagConstraints228.gridy = 0;
			jLabel_HMM_MCMC_D2 = new JLabel();
			jLabel_HMM_MCMC_D2.setText("D2");
			GridBagConstraints gridBagConstraints227 = new GridBagConstraints();
			gridBagConstraints227.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints227.gridx = 1;
			gridBagConstraints227.gridy = 0;
			jLabel_D1 = new JLabel();
			jLabel_D1.setText("D1");
			GridBagConstraints gridBagConstraints225 = new GridBagConstraints();
			gridBagConstraints225.anchor = GridBagConstraints.EAST;
			gridBagConstraints225.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints225.gridx = 0;
			gridBagConstraints225.gridy = 1;
			jLabel_HMM_MCMC_initials = new JLabel();
			jLabel_HMM_MCMC_initials.setText("Initial:");
			GridBagConstraints gridBagConstraints224 = new GridBagConstraints();
			gridBagConstraints224.anchor = GridBagConstraints.EAST;
			gridBagConstraints224.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints224.gridx = 0;
			gridBagConstraints224.gridy = 2;
			jLabel8 = new JLabel();
			jLabel8.setText("Scale:");
			jPanel23 = new JPanel();
			GridBagLayout gbl_jPanel23 = new GridBagLayout();
			gbl_jPanel23.rowHeights = new int[]{27, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0};
			gbl_jPanel23.columnWidths = new int[]{56, 91, 80, 0, 66};
			jPanel23.setLayout(gbl_jPanel23);
			jPanel23.setPreferredSize(new Dimension(25, 25));
			jPanel23.add(jLabel8, gridBagConstraints224);
			jPanel23.add(jLabel_HMM_MCMC_initials, gridBagConstraints225);
			jPanel23.add(jLabel_D1, gridBagConstraints227);
			jPanel23.add(jLabel_HMM_MCMC_D2, gridBagConstraints228);
			jPanel23.add(jLabel_P12, gridBagConstraints229);
			jPanel23.add(jLabel_P21, gridBagConstraints230);
			GridBagConstraints gridBagConstraints239 = new GridBagConstraints();
			gridBagConstraints239.anchor = GridBagConstraints.EAST;
			gridBagConstraints239.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints239.gridx = 0;
			gridBagConstraints239.gridy = 3;
			jLabel_HMM = new JLabel();
			jLabel_HMM.setText("AccRat:");
			jPanel23.add(jLabel_HMM, gridBagConstraints239);
			GridBagConstraints gridBagConstraints240 = new GridBagConstraints();
			gridBagConstraints240.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints240.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints240.gridy = 3;
			gridBagConstraints240.weightx = 1.0;
			gridBagConstraints240.gridx = 1;
			jPanel23.add(getJTextField_HMM_MCMC_AccRatioD1(), gridBagConstraints240);
			GridBagConstraints gridBagConstraints241 = new GridBagConstraints();
			gridBagConstraints241.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints241.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints241.gridy = 3;
			gridBagConstraints241.weightx = 1.0;
			gridBagConstraints241.gridx = 2;
			jPanel23.add(getJTextField_HMM_MCMC_AccRatioD2(), gridBagConstraints241);
			GridBagConstraints gridBagConstraints242 = new GridBagConstraints();
			gridBagConstraints242.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints242.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints242.gridy = 3;
			gridBagConstraints242.weightx = 1.0;
			gridBagConstraints242.gridx = 3;
			jPanel23.add(getJTextField_HMM_MCMC_AccRatioP12(), gridBagConstraints242);
			GridBagConstraints gridBagConstraints243 = new GridBagConstraints();
			gridBagConstraints243.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints243.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints243.gridy = 3;
			gridBagConstraints243.weightx = 1.0;
			gridBagConstraints243.gridx = 4;
			jPanel23.add(getJTextField_HMM_MCMC_AccRatioP21(), gridBagConstraints243);
			GridBagConstraints gridBagConstraints222 = new GridBagConstraints();
			gridBagConstraints222.anchor = GridBagConstraints.EAST;
			gridBagConstraints222.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints222.gridx = 0;
			gridBagConstraints222.gridy = 4;
			jLabel_HMM_MCMC_nsteps = new JLabel();
			jLabel_HMM_MCMC_nsteps.setText("Steps:");
			jPanel23.add(jLabel_HMM_MCMC_nsteps, gridBagConstraints222);
			GridBagConstraints gridBagConstraints223 = new GridBagConstraints();
			gridBagConstraints223.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints223.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints223.gridy = 4;
			gridBagConstraints223.weightx = 1.0D;
			gridBagConstraints223.gridx = 1;
			jPanel23.add(getJTextField_HMM_MCMC_steps(), gridBagConstraints223);
			GridBagConstraints gridBagConstraints237 = new GridBagConstraints();
			gridBagConstraints237.gridwidth = 2;
			gridBagConstraints237.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints237.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints237.gridx = 2;
			gridBagConstraints237.gridy = 4;
			jLabel_HMM_MCMC_stepsUpdatePlots = new JLabel();
			jLabel_HMM_MCMC_stepsUpdatePlots.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_HMM_MCMC_stepsUpdatePlots.setText("Update rate:");
			jPanel23.add(jLabel_HMM_MCMC_stepsUpdatePlots, gridBagConstraints237);
			GridBagConstraints gridBagConstraints238 = new GridBagConstraints();
			gridBagConstraints238.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints238.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints238.gridy = 4;
			gridBagConstraints238.weightx = 0.0D;
			gridBagConstraints238.gridx = 4;
			jPanel23.add(getJTextField_HMM_UpdateEach(), gridBagConstraints238);
			GridBagConstraints gbc_chckbxAutoScale = new GridBagConstraints();
			gbc_chckbxAutoScale.fill = GridBagConstraints.HORIZONTAL;
			gbc_chckbxAutoScale.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxAutoScale.gridx = 1;
			gbc_chckbxAutoScale.gridy = 6;
			jPanel23.add(getChckbxAutoScale(), gbc_chckbxAutoScale);
			GridBagConstraints gridBagConstraints236 = new GridBagConstraints();
			gridBagConstraints236.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints236.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints236.gridx = 2;
			gridBagConstraints236.gridy = 6;
			jPanel23.add(getJButton_HMM_MCMC_updateScale(), gridBagConstraints236);
			GridBagConstraints gridBagConstraints234 = new GridBagConstraints();
			gridBagConstraints234.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints234.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints234.gridwidth = 1;
			gridBagConstraints234.gridy = 6;
			gridBagConstraints234.gridx = 3;
			jPanel23.add(getJButton_HMM_MCMC(), gridBagConstraints234);
			GridBagConstraints gridBagConstraints278 = new GridBagConstraints();
			gridBagConstraints278.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints278.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints278.gridx = 4;
			gridBagConstraints278.gridy = 6;
			jPanel23.add(getJButton_HMM_Stop(), gridBagConstraints278);
			jPanel23.add(getJProgressBar_HMM_MCMC(), gridBagConstraints235);
			jPanel23.add(getJTextField_HMM_MCMC_InitialD1(), gridBagConstraints134);
			jPanel23.add(getJTextField_HMM_MCMC_InitialD2(), gridBagConstraints171);
			jPanel23.add(getJTextField_HMM_MCMC_ScaleD1(), gridBagConstraints220);
			jPanel23.add(getJTextField_HMM_MCMC_ScaleD2(), gridBagConstraints221);
			jPanel23.add(getJTextField_HMM_MCMC_InitialP12(), gridBagConstraints226);
			jPanel23.add(getJTextField_HMM_MCMC_ScaleP12(), gridBagConstraints231);
			jPanel23.add(getJTextField_HMM_MCMC_InitialP21(), gridBagConstraints232);
			jPanel23.add(getJTextField_HMM_MCMC_ScaleP21(), gridBagConstraints233);
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.gridwidth = 5;
			gbc_separator.fill = GridBagConstraints.HORIZONTAL;
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 8;
			jPanel23.add(getSeparator(), gbc_separator);
			jPanel23.add(jLabel14, gridBagConstraints270);
			jPanel23.add(getJSlider_Burn(), gridBagConstraints271);
			GridBagConstraints gridBagConstraints280 = new GridBagConstraints();
			gridBagConstraints280.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints280.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints280.gridy = 9;
			gridBagConstraints280.weightx = 1.0;
			gridBagConstraints280.gridx = 2;
			jPanel23.add(getJTextField_HMM_Burn(), gridBagConstraints280);
			GridBagConstraints gridBagConstraints272 = new GridBagConstraints();
			gridBagConstraints272.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints272.gridx = 3;
			gridBagConstraints272.gridy = 9;
			jPanel23.add(getJButton_HMM_CalcResult(), gridBagConstraints272);
			GridBagConstraints gridBagConstraints279 = new GridBagConstraints();
			gridBagConstraints279.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints279.gridx = 4;
			gridBagConstraints279.gridy = 9;
			jPanel23.add(getJCheckBox_HMM_ShowResults(), gridBagConstraints279);
			jPanel23.add(jLabel15, gridBagConstraints273);
			jPanel23.add(getJTextField_HMM_Result_D1(), gridBagConstraints274);
			jPanel23.add(getJTextField_HMM_Result_D2(), gridBagConstraints275);
			jPanel23.add(getJTextField_HMM_Result_P12(), gridBagConstraints276);
			jPanel23.add(getJTextField_HMM_Result_P21(), gridBagConstraints277);
		}
		return jPanel23;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_steps	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_steps() {
		if (jTextField_HMM_MCMC_steps == null) {
			jTextField_HMM_MCMC_steps = new JTextField();
			jTextField_HMM_MCMC_steps.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_steps.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_steps.setText("1000");
		}
		return jTextField_HMM_MCMC_steps;
	}

	/**
	 * This method initializes jButton_HMM_MCMC	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_HMM_MCMC() {
		if (jButton_HMM_MCMC == null) {
			jButton_HMM_MCMC = new JButton();
			jButton_HMM_MCMC.setPreferredSize(new Dimension(80, 28));
			jButton_HMM_MCMC.setText("start");
			jButton_HMM_MCMC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					hmma.jButton_HMM_MCMC_Start_clicked();
				}
			});
		}
		return jButton_HMM_MCMC;
	}

	/**
	 * This method initializes jProgressBar_HMM_MCMC	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar_HMM_MCMC() {
		if (jProgressBar_HMM_MCMC == null) {
			jProgressBar_HMM_MCMC = new JProgressBar();
			jProgressBar_HMM_MCMC.setPreferredSize(new Dimension(250, 20));
			jProgressBar_HMM_MCMC.setStringPainted(true);
		}
		return jProgressBar_HMM_MCMC;
	}

	/**
	 * This method initializes jButton_HMM_MCMC_updateScale	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_HMM_MCMC_updateScale() {
		if (jButton_HMM_MCMC_updateScale == null) {
			jButton_HMM_MCMC_updateScale = new JButton();
			jButton_HMM_MCMC_updateScale.setBackground(Color.ORANGE);
			jButton_HMM_MCMC_updateScale.setPreferredSize(new Dimension(80, 28));
			jButton_HMM_MCMC_updateScale.setText("update");
			jButton_HMM_MCMC_updateScale
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							hmma.jButton_HMM_MCMC_update_clicked();
						}
					});
		}
		return jButton_HMM_MCMC_updateScale;
	}

	/**
	 * This method initializes jTextField_HMM_UpdateEach	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_UpdateEach() {
		if (jTextField_HMM_UpdateEach == null) {
			jTextField_HMM_UpdateEach = new JTextField();
			jTextField_HMM_UpdateEach.setBackground(Color.ORANGE);
			jTextField_HMM_UpdateEach.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_UpdateEach.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_UpdateEach.setText("100");
		}
		return jTextField_HMM_UpdateEach;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_InitialD1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_InitialD1() {
		if (jTextField_HMM_MCMC_InitialD1 == null) {
			jTextField_HMM_MCMC_InitialD1 = new JTextField();
			jTextField_HMM_MCMC_InitialD1.setPreferredSize(new Dimension(60, 28));
			jTextField_HMM_MCMC_InitialD1.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_InitialD1.setText("1");
		}
		return jTextField_HMM_MCMC_InitialD1;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_InitialD2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_InitialD2() {
		if (jTextField_HMM_MCMC_InitialD2 == null) {
			jTextField_HMM_MCMC_InitialD2 = new JTextField();
			jTextField_HMM_MCMC_InitialD2.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_InitialD2.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_InitialD2.setText("0.005");
		}
		return jTextField_HMM_MCMC_InitialD2;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_ScaleD1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_ScaleD1() {
		if (jTextField_HMM_MCMC_ScaleD1 == null) {
			jTextField_HMM_MCMC_ScaleD1 = new JTextField();
			jTextField_HMM_MCMC_ScaleD1.setBackground(Color.ORANGE);
			jTextField_HMM_MCMC_ScaleD1.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_ScaleD1.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_ScaleD1.setText("0.01");
		}
		return jTextField_HMM_MCMC_ScaleD1;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_ScaleD2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_ScaleD2() {
		if (jTextField_HMM_MCMC_ScaleD2 == null) {
			jTextField_HMM_MCMC_ScaleD2 = new JTextField();
			jTextField_HMM_MCMC_ScaleD2.setBackground(Color.ORANGE);
			jTextField_HMM_MCMC_ScaleD2.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_ScaleD2.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_ScaleD2.setText("0.01");
		}
		return jTextField_HMM_MCMC_ScaleD2;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_InitialP12	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_InitialP12() {
		if (jTextField_HMM_MCMC_InitialP12 == null) {
			jTextField_HMM_MCMC_InitialP12 = new JTextField();
			jTextField_HMM_MCMC_InitialP12.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_InitialP12.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_InitialP12.setText("0.5");
		}
		return jTextField_HMM_MCMC_InitialP12;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_ScaleP12	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_ScaleP12() {
		if (jTextField_HMM_MCMC_ScaleP12 == null) {
			jTextField_HMM_MCMC_ScaleP12 = new JTextField();
			jTextField_HMM_MCMC_ScaleP12.setBackground(Color.ORANGE);
			jTextField_HMM_MCMC_ScaleP12.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_ScaleP12.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_ScaleP12.setText("0.01");
		}
		return jTextField_HMM_MCMC_ScaleP12;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_InitialP21	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_InitialP21() {
		if (jTextField_HMM_MCMC_InitialP21 == null) {
			jTextField_HMM_MCMC_InitialP21 = new JTextField();
			jTextField_HMM_MCMC_InitialP21.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_InitialP21.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_InitialP21.setText("0.5");
		}
		return jTextField_HMM_MCMC_InitialP21;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_ScaleP21	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_ScaleP21() {
		if (jTextField_HMM_MCMC_ScaleP21 == null) {
			jTextField_HMM_MCMC_ScaleP21 = new JTextField();
			jTextField_HMM_MCMC_ScaleP21.setBackground(Color.ORANGE);
			jTextField_HMM_MCMC_ScaleP21.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_ScaleP21.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HMM_MCMC_ScaleP21.setText("0.01");
		}
		return jTextField_HMM_MCMC_ScaleP21;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_AccRatioD1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_AccRatioD1() {
		if (jTextField_HMM_MCMC_AccRatioD1 == null) {
			jTextField_HMM_MCMC_AccRatioD1 = new JTextField();
			jTextField_HMM_MCMC_AccRatioD1.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_AccRatioD1.setEditable(false);
		}
		return jTextField_HMM_MCMC_AccRatioD1;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_AccRatioD2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_AccRatioD2() {
		if (jTextField_HMM_MCMC_AccRatioD2 == null) {
			jTextField_HMM_MCMC_AccRatioD2 = new JTextField();
			jTextField_HMM_MCMC_AccRatioD2.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_AccRatioD2.setEditable(false);
		}
		return jTextField_HMM_MCMC_AccRatioD2;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_AccRatioP12	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_AccRatioP12() {
		if (jTextField_HMM_MCMC_AccRatioP12 == null) {
			jTextField_HMM_MCMC_AccRatioP12 = new JTextField();
			jTextField_HMM_MCMC_AccRatioP12.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_AccRatioP12.setEditable(false);
		}
		return jTextField_HMM_MCMC_AccRatioP12;
	}

	/**
	 * This method initializes jTextField_HMM_MCMC_AccRatioP21	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_MCMC_AccRatioP21() {
		if (jTextField_HMM_MCMC_AccRatioP21 == null) {
			jTextField_HMM_MCMC_AccRatioP21 = new JTextField();
			jTextField_HMM_MCMC_AccRatioP21.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_MCMC_AccRatioP21.setEditable(false);
		}
		return jTextField_HMM_MCMC_AccRatioP21;
	}

	/**
	 * This method initializes jSlider_Burn	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Burn() {
		if (jSlider_Burn == null) {
			jSlider_Burn = new JSlider();
			jSlider_Burn.setMinimum(1);
			jSlider_Burn.setValue(0);
			jSlider_Burn.setSnapToTicks(true);
			jSlider_Burn.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					hmma.jSlider_Burn_stateChanged();
				}
			});
		}
		return jSlider_Burn;
	}

	/**
	 * This method initializes jButton_HMM_CalcResult	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_HMM_CalcResult() {
		if (jButton_HMM_CalcResult == null) {
			jButton_HMM_CalcResult = new JButton();
			jButton_HMM_CalcResult.setText("Result");
			jButton_HMM_CalcResult.setPreferredSize(new Dimension(80, 28));
			jButton_HMM_CalcResult.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					hmma.jButton_HMM_Result_clicked();
				}
			});
		}
		return jButton_HMM_CalcResult;
	}

	/**
	 * This method initializes jTextField_HMM_Result_D1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_Result_D1() {
		if (jTextField_HMM_Result_D1 == null) {
			jTextField_HMM_Result_D1 = new JTextField();
			jTextField_HMM_Result_D1.setPreferredSize(new Dimension(60, 25));
		}
		return jTextField_HMM_Result_D1;
	}

	/**
	 * This method initializes jTextField_HMM_Result_D2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_Result_D2() {
		if (jTextField_HMM_Result_D2 == null) {
			jTextField_HMM_Result_D2 = new JTextField();
			jTextField_HMM_Result_D2.setPreferredSize(new Dimension(60, 25));
		}
		return jTextField_HMM_Result_D2;
	}

	/**
	 * This method initializes jTextField_HMM_Result_P12	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_Result_P12() {
		if (jTextField_HMM_Result_P12 == null) {
			jTextField_HMM_Result_P12 = new JTextField();
			jTextField_HMM_Result_P12.setPreferredSize(new Dimension(60, 25));
		}
		return jTextField_HMM_Result_P12;
	}

	/**
	 * This method initializes jTextField_HMM_Result_P21	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_Result_P21() {
		if (jTextField_HMM_Result_P21 == null) {
			jTextField_HMM_Result_P21 = new JTextField();
			jTextField_HMM_Result_P21.setPreferredSize(new Dimension(60, 25));
		}
		return jTextField_HMM_Result_P21;
	}

	/**
	 * This method initializes jButton_HMM_Stop	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_HMM_Stop() {
		if (jButton_HMM_Stop == null) {
			jButton_HMM_Stop = new JButton();
			jButton_HMM_Stop.setPreferredSize(new Dimension(80, 28));
			jButton_HMM_Stop.setText("stop");
			jButton_HMM_Stop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					hmma.jButton_MCMC_stop_clicked();
				}
			});
		}
		return jButton_HMM_Stop;
	}

	/**
	 * This method initializes jCheckBox_HMM_ShowResults	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_HMM_ShowResults() {
		if (jCheckBox_HMM_ShowResults == null) {
			jCheckBox_HMM_ShowResults = new JCheckBox();
			jCheckBox_HMM_ShowResults.setSelected(true);
			jCheckBox_HMM_ShowResults.setText("show");
		}
		return jCheckBox_HMM_ShowResults;
	}

	/**
	 * This method initializes jTextField_HMM_Burn	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_HMM_Burn() {
		if (jTextField_HMM_Burn == null) {
			jTextField_HMM_Burn = new JTextField();
			jTextField_HMM_Burn.setPreferredSize(new Dimension(60, 25));
			jTextField_HMM_Burn.setEditable(false);
		}
		return jTextField_HMM_Burn;
	}

	/**
	 * This method initializes jPanel_HMM_MCMC_LogLike	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_HMM_MCMC_LogLike() {
		if (jPanel_HMM_MCMC_LogLike == null) {
			GridBagConstraints gBC1 = new GridBagConstraints();
			gBC1.fill = GridBagConstraints.BOTH;
			gBC1.weighty = 1.0D;
			gBC1.weightx = 1.0D;
			jPanel_HMM_MCMC_LogLike = new JPanel();
			jPanel_HMM_MCMC_LogLike.setLayout(new GridBagLayout());
			jPanel_HMM_MCMC_LogLike.setPreferredSize(new Dimension(25, 25));
			jPanel_HMM_MCMC_LogLike.add(getCp_HMM_MCMC_loglike(), gBC1);
		}
		return jPanel_HMM_MCMC_LogLike;
	}

	/**
	 * This method initializes cp_HMM_MCMC_loglike	
	 * 	
	 * @return org.jfree.chart.ChartPanel	
	 */
	private ChartPanel getCp_HMM_MCMC_loglike() {
		if (cp_HMM_MCMC_loglike == null) {
			cp_HMM_MCMC_loglike = new ChartPanel(new JFreeChartHMMLogLikeVsStep(new CombinedDomainXYPlot()));
		}
		return cp_HMM_MCMC_loglike;
	}

	/**
	 * This method initializes jPanel_HMM_Seg	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_HMM_Seg() {
		if (jPanel_HMM_Seg == null) {
			jPanel_HMM_Seg = new JPanel();
			GridBagLayout gbl_jPanel_HMM_Seg = new GridBagLayout();
			gbl_jPanel_HMM_Seg.rowWeights = new double[]{1.0};
			gbl_jPanel_HMM_Seg.columnWeights = new double[]{1.0};
			jPanel_HMM_Seg.setLayout(gbl_jPanel_HMM_Seg);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			jPanel_HMM_Seg.add(getPanel(), gbc_panel);
		}
		return jPanel_HMM_Seg;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(25, 25));
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0};
			gbl_panel.rowHeights = new int[]{98, 180, 0};
			gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.weighty = 0.0;
			gbc_panel_1.weightx = 1.0;
			gbc_panel_1.insets = new Insets(0, 0, 5, 0);
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = 0;
			panel.add(getPanel_1(), gbc_panel_1);
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.fill = GridBagConstraints.BOTH;
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 1;
			panel.add(getPanel_2_1(), gbc_panel_2);
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Segmentate Trajectories", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
			panel_1.setPreferredSize(new Dimension(25, 30));
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{0, 30, 72, 43, 58, 49, 0, 42, 81, 0, 0, 0, 0, 0, 0};
			gbl_panel_1.rowHeights = new int[]{36, 43, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.EAST;
			gbc_label.weightx = 1.0;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 1;
			gbc_label.gridy = 0;
			panel_1.add(getLabel(), gbc_label);
			GridBagConstraints gbc_textField_Seg_D1 = new GridBagConstraints();
			gbc_textField_Seg_D1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Seg_D1.weightx = 1.0;
			gbc_textField_Seg_D1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Seg_D1.gridx = 2;
			gbc_textField_Seg_D1.gridy = 0;
			panel_1.add(getTextField_Seg_D1(), gbc_textField_Seg_D1);
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.anchor = GridBagConstraints.EAST;
			gbc_label_1.insets = new Insets(0, 0, 5, 5);
			gbc_label_1.gridx = 3;
			gbc_label_1.gridy = 0;
			panel_1.add(getLabel_1(), gbc_label_1);
			GridBagConstraints gbc_textField_Seg_D2 = new GridBagConstraints();
			gbc_textField_Seg_D2.weightx = 1.0;
			gbc_textField_Seg_D2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Seg_D2.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Seg_D2.gridx = 4;
			gbc_textField_Seg_D2.gridy = 0;
			panel_1.add(getTextField_Seg_D2(), gbc_textField_Seg_D2);
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.anchor = GridBagConstraints.EAST;
			gbc_label_2.insets = new Insets(0, 0, 5, 5);
			gbc_label_2.gridx = 5;
			gbc_label_2.gridy = 0;
			panel_1.add(getLabel_2(), gbc_label_2);
			GridBagConstraints gbc_textField_Seg_P12 = new GridBagConstraints();
			gbc_textField_Seg_P12.weightx = 1.0;
			gbc_textField_Seg_P12.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Seg_P12.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Seg_P12.gridx = 6;
			gbc_textField_Seg_P12.gridy = 0;
			panel_1.add(getTextField_Seg_P12(), gbc_textField_Seg_P12);
			GridBagConstraints gbc_label_3 = new GridBagConstraints();
			gbc_label_3.anchor = GridBagConstraints.EAST;
			gbc_label_3.insets = new Insets(0, 0, 5, 5);
			gbc_label_3.gridx = 7;
			gbc_label_3.gridy = 0;
			panel_1.add(getLabel_3(), gbc_label_3);
			GridBagConstraints gbc_textField_Seg_P21 = new GridBagConstraints();
			gbc_textField_Seg_P21.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Seg_P21.weightx = 1.0;
			gbc_textField_Seg_P21.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Seg_P21.gridx = 8;
			gbc_textField_Seg_P21.gridy = 0;
			panel_1.add(getTextField_Seg_P21(), gbc_textField_Seg_P21);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.gridwidth = 4;
			gbc_progressBar.insets = new Insets(0, 0, 0, 5);
			gbc_progressBar.gridx = 2;
			gbc_progressBar.gridy = 1;
			panel_1.add(getProgressBar(), gbc_progressBar);
			GridBagConstraints gbc_radioButton = new GridBagConstraints();
			gbc_radioButton.insets = new Insets(0, 0, 0, 5);
			gbc_radioButton.gridx = 6;
			gbc_radioButton.gridy = 1;
			panel_1.add(getRadioButton(), gbc_radioButton);
			GridBagConstraints gbc_jRadioButton_All = new GridBagConstraints();
			gbc_jRadioButton_All.insets = new Insets(0, 0, 0, 5);
			gbc_jRadioButton_All.gridx = 7;
			gbc_jRadioButton_All.gridy = 1;
			panel_1.add(getJRadioButton_All(), gbc_jRadioButton_All);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 0, 5);
			gbc_button.gridx = 8;
			gbc_button.gridy = 1;
			panel_1.add(getButton(), gbc_button);
		}
		return panel_1;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("D1:");
		}
		return label;
	}
	private JTextField getTextField_Seg_D1() {
		if (textField_Seg_D1 == null) {
			textField_Seg_D1 = new JTextField();
			textField_Seg_D1.setText("0.1");
			textField_Seg_D1.setPreferredSize(new Dimension(60, 28));
		}
		return textField_Seg_D1;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("D2:");
		}
		return label_1;
	}
	private JTextField getTextField_Seg_D2() {
		if (textField_Seg_D2 == null) {
			textField_Seg_D2 = new JTextField();
			textField_Seg_D2.setText("0.01");
			textField_Seg_D2.setPreferredSize(new Dimension(60, 28));
		}
		return textField_Seg_D2;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("P12:");
		}
		return label_2;
	}
	private JTextField getTextField_Seg_P12() {
		if (textField_Seg_P12 == null) {
			textField_Seg_P12 = new JTextField();
			textField_Seg_P12.setText("0.1");
			textField_Seg_P12.setPreferredSize(new Dimension(60, 28));
		}
		return textField_Seg_P12;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("P21:");
		}
		return label_3;
	}
	private JTextField getTextField_Seg_P21() {
		if (textField_Seg_P21 == null) {
			textField_Seg_P21 = new JTextField();
			textField_Seg_P21.setText("0.05");
			textField_Seg_P21.setPreferredSize(new Dimension(60, 28));
		}
		return textField_Seg_P21;
	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setPreferredSize(new Dimension(150, 20));
		}
		return progressBar;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton();
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					hmma.jButton_Segment_clicked();
				}
			});
			button.setText("Segment");
			button.setPreferredSize(new Dimension(100, 28));
		}
		return button;
	}
	private JRadioButton getRadioButton() {
		if (radioButton == null) {
			radioButton = new JRadioButton();
			buttonGroup.add(radioButton);
			radioButton.setText("current");
			radioButton.setSelected(true);
		}
		return radioButton;
	}
	private JRadioButton getJRadioButton_All() {
		if (jRadioButton_All == null) {
			jRadioButton_All = new JRadioButton();
			buttonGroup.add(jRadioButton_All);
			jRadioButton_All.setText("all");
		}
		return jRadioButton_All;
	}
	private JCheckBox getChckbxAutoScale() {
		if (chckbxAutoScale == null) {
			chckbxAutoScale = new JCheckBox("Auto scale");
		}
		return chckbxAutoScale;
	}
	private JPanel getPanel_2_1() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Selected Trajectory Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[]{0, 0, 304, 139, 143, 0};
			gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel_2.setLayout(gbl_panel_2);
			GridBagConstraints gbc_lblStateSequence = new GridBagConstraints();
			gbc_lblStateSequence.insets = new Insets(0, 0, 5, 5);
			gbc_lblStateSequence.gridx = 2;
			gbc_lblStateSequence.gridy = 1;
			panel_2.add(getLblStateSequence(), gbc_lblStateSequence);
			GridBagConstraints gbc_lblLowState = new GridBagConstraints();
			gbc_lblLowState.insets = new Insets(0, 0, 5, 5);
			gbc_lblLowState.gridx = 3;
			gbc_lblLowState.gridy = 1;
			panel_2.add(getLblLowState(), gbc_lblLowState);
			GridBagConstraints gbc_lblHighState = new GridBagConstraints();
			gbc_lblHighState.insets = new Insets(0, 0, 5, 0);
			gbc_lblHighState.gridx = 4;
			gbc_lblHighState.gridy = 1;
			panel_2.add(getLblHighState(), gbc_lblHighState);
			GridBagConstraints gbc_lblSimulated = new GridBagConstraints();
			gbc_lblSimulated.insets = new Insets(0, 0, 5, 5);
			gbc_lblSimulated.gridx = 1;
			gbc_lblSimulated.gridy = 2;
			panel_2.add(getLblSimulated(), gbc_lblSimulated);
			GridBagConstraints gbc_panel_4 = new GridBagConstraints();
			gbc_panel_4.insets = new Insets(0, 0, 5, 5);
			gbc_panel_4.gridx = 2;
			gbc_panel_4.gridy = 2;
			panel_2.add(getPanel_4(), gbc_panel_4);
			GridBagConstraints gbc_textField_SimLowState = new GridBagConstraints();
			gbc_textField_SimLowState.insets = new Insets(0, 0, 5, 5);
			gbc_textField_SimLowState.gridx = 3;
			gbc_textField_SimLowState.gridy = 2;
			panel_2.add(getTextField_SimLowState(), gbc_textField_SimLowState);
			GridBagConstraints gbc_textField_SimHighState = new GridBagConstraints();
			gbc_textField_SimHighState.insets = new Insets(0, 0, 5, 0);
			gbc_textField_SimHighState.gridx = 4;
			gbc_textField_SimHighState.gridy = 2;
			panel_2.add(getTextField_SimHighState(), gbc_textField_SimHighState);
			GridBagConstraints gbc_lblGuess = new GridBagConstraints();
			gbc_lblGuess.insets = new Insets(0, 0, 5, 5);
			gbc_lblGuess.gridx = 1;
			gbc_lblGuess.gridy = 3;
			panel_2.add(getLblGuess(), gbc_lblGuess);
			GridBagConstraints gbc_panel_5 = new GridBagConstraints();
			gbc_panel_5.insets = new Insets(0, 0, 5, 5);
			gbc_panel_5.gridx = 2;
			gbc_panel_5.gridy = 3;
			panel_2.add(getPanel_5(), gbc_panel_5);
			GridBagConstraints gbc_textField_GuessLowState = new GridBagConstraints();
			gbc_textField_GuessLowState.insets = new Insets(0, 0, 5, 5);
			gbc_textField_GuessLowState.gridx = 3;
			gbc_textField_GuessLowState.gridy = 3;
			panel_2.add(getTextField_GuessLowState(), gbc_textField_GuessLowState);
			GridBagConstraints gbc_textField_GuessHighState = new GridBagConstraints();
			gbc_textField_GuessHighState.insets = new Insets(0, 0, 5, 0);
			gbc_textField_GuessHighState.gridx = 4;
			gbc_textField_GuessHighState.gridy = 3;
			panel_2.add(getTextField_GuessHighState(), gbc_textField_GuessHighState);
			GridBagConstraints gbc_lblSucces = new GridBagConstraints();
			gbc_lblSucces.insets = new Insets(0, 0, 5, 5);
			gbc_lblSucces.gridx = 1;
			gbc_lblSucces.gridy = 4;
			panel_2.add(getLblSucces(), gbc_lblSucces);
			GridBagConstraints gbc_textField_Success = new GridBagConstraints();
			gbc_textField_Success.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Success.gridx = 2;
			gbc_textField_Success.gridy = 4;
			panel_2.add(getTextField_Success(), gbc_textField_Success);
		}
		return panel_2;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setPreferredSize(new Dimension(200, 50));
			panel_4.setSize(new Dimension(200, 50));
			GridBagLayout gbl_panel_4 = new GridBagLayout();
			gbl_panel_4.columnWidths = new int[]{100, 0};
			gbl_panel_4.rowHeights = new int[]{1, 0};
			gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_4.setLayout(gbl_panel_4);
			originalStateCanvas = new HMMStateSequenceCanvas();
			originalStateCanvas.setBounds(0,0,panel_4.getWidth(),panel_4.getHeight());
			GridBagConstraints gbc_originalStateCanvas = new GridBagConstraints();
			gbc_originalStateCanvas.weighty = 1.0;
			gbc_originalStateCanvas.fill = GridBagConstraints.BOTH;
			gbc_originalStateCanvas.gridx = 0;
			gbc_originalStateCanvas.gridy = 0;
			panel_4.add(originalStateCanvas, gbc_originalStateCanvas);
		}
		return panel_4;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setSize(new Dimension(200, 50));
			panel_5.setPreferredSize(new Dimension(200, 50));
			GridBagLayout gbl_panel_5 = new GridBagLayout();
			gbl_panel_5.columnWidths = new int[]{100, 0};
			gbl_panel_5.rowHeights = new int[]{1, 0};
			gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_5.setLayout(gbl_panel_5);
			guessStateCanvas = new HMMStateSequenceCanvas();
			guessStateCanvas.setBounds(0,0,panel_5.getWidth(),panel_5.getHeight());
			GridBagConstraints gbc_guessStateCanvas = new GridBagConstraints();
			gbc_guessStateCanvas.weighty = 1.0;
			gbc_guessStateCanvas.fill = GridBagConstraints.BOTH;
			gbc_guessStateCanvas.gridx = 0;
			gbc_guessStateCanvas.gridy = 0;
			panel_5.add(guessStateCanvas, gbc_guessStateCanvas);
		}
		return panel_5;
	}
	private JTextField getTextField_GuessLowState() {
		if (textField_GuessLowState == null) {
			textField_GuessLowState = new JTextField();
			textField_GuessLowState.setColumns(10);
		}
		return textField_GuessLowState;
	}
	private JLabel getLblSimulated() {
		if (lblSimulated == null) {
			lblSimulated = new JLabel("Simulated:");
		}
		return lblSimulated;
	}
	private JLabel getLblGuess() {
		if (lblGuess == null) {
			lblGuess = new JLabel("Guess:");
		}
		return lblGuess;
	}
	private JLabel getLblStateSequence() {
		if (lblStateSequence == null) {
			lblStateSequence = new JLabel("State sequence");
		}
		return lblStateSequence;
	}
	private JTextField getTextField_SimLowState() {
		if (textField_SimLowState == null) {
			textField_SimLowState = new JTextField();
			textField_SimLowState.setColumns(10);
		}
		return textField_SimLowState;
	}
	private JTextField getTextField_SimHighState() {
		if (textField_SimHighState == null) {
			textField_SimHighState = new JTextField();
			textField_SimHighState.setColumns(10);
		}
		return textField_SimHighState;
	}
	private JTextField getTextField_GuessHighState() {
		if (textField_GuessHighState == null) {
			textField_GuessHighState = new JTextField();
			textField_GuessHighState.setColumns(10);
		}
		return textField_GuessHighState;
	}
	private JLabel getLblLowState() {
		if (lblLowState == null) {
			lblLowState = new JLabel("Low State");
		}
		return lblLowState;
	}
	private JLabel getLblHighState() {
		if (lblHighState == null) {
			lblHighState = new JLabel(" High State");
		}
		return lblHighState;
	}
	private JTextField getTextField_Success() {
		if (textField_Success == null) {
			textField_Success = new JTextField();
			textField_Success.setColumns(10);
		}
		return textField_Success;
	}
	private JLabel getLblSucces() {
		if (lblSucces == null) {
			lblSucces = new JLabel("Success:");
		}
		return lblSucces;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
