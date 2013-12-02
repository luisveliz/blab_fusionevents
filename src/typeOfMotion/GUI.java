package typeOfMotion;

import ij.IJ;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;


import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import java.lang.String;
import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame 
{
	TypeOfMotionAnalysis toma;
	private ChartPanel cp_MSD = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_MSDAnalysis = null;
	private JPanel jPanel_MSD_Plot = null;
	private JPanel jPanel_MSD_ManualFit = null;
	private JLabel jLabel_MSD_L = null;
	private JLabel jLabel_MSD_Alfa = null;
	private JLabel jLabel_MSD_V = null;
	private JSpinner jSpinner_Corralled_L = null;
	private JSpinner jSpinner_Anomalous_D = null;
	private JSpinner jSpinner_Directed_D = null;
	private JSpinner jSpinner_Corralled_D = null;
	private JSpinner jSpinner_Anomalous_Alfa = null;
	private JSpinner jSpinner_Directed_V = null;
	private JCheckBox jCheckBox_Manual_MSD_ShowCorralled = null;
	private JCheckBox jCheckBox_Manual_MSD_ShowAnomalous = null;
	private JCheckBox jCheckBox_Manual_MSD_ShowDirected = null;
	private JButton jButton_SetCorralled = null;
	private JButton jButton_SetAnomalous = null;
	private JButton jButton_SetDirected = null;
	private JButton jButton_BestFitCorralled = null;
	private JButton jButton_BestFitAnomalous = null;
	private JButton jButton_BestFitDirected = null;
	private JCheckBox jCheckBox_Manual_MSD_ShowNormal = null;
	private JSpinner jSpinner_NormalDiffusionD = null;
	private JButton jButton_BestFitNormal = null;
	private JButton jButton_SetNormal = null;
	private JPanel jPanel_MSD_Automatic = null;
	private JProgressBar jProgressBar_Fit = null;
	private JCheckBox jCheckBox_ShowAutomatic = null;
	private JButton jButton_AutomaticFit = null;
	private JButton jButton_AutoFitAllTrajs = null;
	private JSpinner jSpinner = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JSpinner jSpinner_InitialMSDPoint = null;
	private JLabel jLabel_FinalMSDPoint = null;
	private JCheckBox jCheckBox_showSD = null;
	private JSpinner jSpinner_NormalC = null;
	private JSpinner jSpinner_CorralledC = null;
	private JSpinner jSpinner_AnomalousC = null;
	private JSpinner jSpinner_DirectedC = null;
	private JComboBox jComboBox_FitMethod = null;
	private JLabel jLabel3 = null;
	private JCheckBox jCheckBox_Variance = null;
	private JCheckBox jCheckBox_offset = null;
	private Action action;
	private JPanel panel;
	private JPanel panel_1;
	private JCheckBox chckbxAverageMsd;
	private JCheckBox chckbxShowMarks;
	private JButton btnRecalculateDFor;
	private JSlider sliderMSDFinalPoint;
	private JPanel panel_2;
	private JCheckBox chckbxNPoints;
	private JTextField textField_FinalMSDFitPoint;
	private JComboBox comboBox_AvgMSDOption;
	private JCheckBox chckbxShowFitInfo;
	private JRadioButton radioButton25;
	private JRadioButton rdbtnAllPoints;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * This is the default constructor
	 */
	public GUI(TypeOfMotionAnalysis toma) {
		super();
		this.toma = toma;
		initialize();
	}

	/*--------------------------------------------------------------------------------*/

	public JFreeChartMSD getJFreeChart_MSD()
	{
		return (JFreeChartMSD)this.cp_MSD.getChart();
	}
	public int jComboBox_FitMethod_getSelectedIndex()
	{
		return jComboBox_FitMethod.getSelectedIndex();
	}
	
	public boolean jCheckBox_showFitInfo_isSelected()
	{
		return chckbxShowFitInfo.isSelected();
	}
	public boolean jCheckBox_fitWithOffset_isSelected()
	{
		return jCheckBox_offset.isSelected();
	}
	public void jSpinner_C_setEnabled(boolean bool)
	{
		jSpinner_NormalC.setEnabled(bool);
		jSpinner_AnomalousC.setEnabled(bool);
		jSpinner_CorralledC.setEnabled(bool);
		jSpinner_DirectedC.setEnabled(bool);
	}
	public boolean jCheckBox_ShowFitMarks_isSelected()
	{
		return chckbxShowMarks.isSelected();
	}
	public boolean jCheckBox_MSD_ShowAutomatic_isSelected()
	{
		return jCheckBox_ShowAutomatic.isSelected();
	}
	public boolean jCheckBox_MSD_ShowNormal_isSelected()
	{
		return jCheckBox_Manual_MSD_ShowNormal.isSelected();
	}
	public boolean jCheckBox_MSD_ShowCorralled_isSelected()
	{
		return jCheckBox_Manual_MSD_ShowCorralled.isSelected();
	}
	public boolean jCheckBox_MSD_ShowAnomalous_isSelected()
	{
		return jCheckBox_Manual_MSD_ShowAnomalous.isSelected();
	}
	public boolean jCheckBox_MSD_ShowDirected_isSelected()
	{
		return jCheckBox_Manual_MSD_ShowDirected.isSelected();
	}
	public void jCheckBox_MSD_ShowAutomatic_setSelected(boolean selected)
	{
		jCheckBox_ShowAutomatic.setSelected(selected);		
	}
	public void jCheckBox_MSD_ShowCorralled_setSelected(boolean selected)
	{
		jCheckBox_Manual_MSD_ShowCorralled.setSelected(selected);		
	}
	public void jCheckBox_MSD_ShowAnomalous_setSelected(boolean selected)
	{
		jCheckBox_Manual_MSD_ShowAnomalous.setSelected(selected);		
	}
	public void jCheckBox_MSD_ShowDirected_setSelected(boolean selected)
	{
		jCheckBox_Manual_MSD_ShowDirected.setSelected(selected);		
	}
	public boolean jCheckBox_showSD_isSelected()
	{
		return jCheckBox_showSD.isSelected();
	}
	public boolean jCheckBox_showVariance_isSelected()
	{
		return jCheckBox_Variance.isSelected();
	}
	public boolean jCheckBox_showNPoints_isSelected()
	{
		return chckbxNPoints.isSelected();
	}
	public int jSpinner_SpinnerStep_getValue()
	{
		return ((Integer)jSpinner.getModel().getValue()).intValue();
	}
	public boolean jCheckBox_AvgMSD_isSelected()
	{
		return chckbxAverageMsd.isSelected();
	}
	public int jComboBox_AvgMSDOption_getSelectedIndex()
	{
		return comboBox_AvgMSDOption.getSelectedIndex();
	}
	public int jSpinner_InitialMSDPoint()
	{
		return ((Integer)(jSpinner_InitialMSDPoint.getValue())).intValue();
	}
	public int jSlider_FinalMSDPoint()
	{
		return ((Integer)(sliderMSDFinalPoint.getValue())).intValue();
	}
	public void jSlider_FinalMSDPoint_setMax(int max)
	{
		sliderMSDFinalPoint.setMaximum(max);
	}

	public void jTextfield_FinalMSDFitPoint_setText(String text)
	{
		textField_FinalMSDFitPoint.setText(text);
	}
	public float jSpinner_Normal_D_getValue()
	{
		return ((Double)jSpinner_NormalDiffusionD.getModel().getValue()).floatValue();
	}
	public float jSpinner_Normal_C_getValue()
	{
		return ((Double)jSpinner_NormalC.getModel().getValue()).floatValue();
	}
	public float jSpinner_Corralled_D_getValue()
	{
		return ((Double)jSpinner_Corralled_D.getModel().getValue()).floatValue();
	}
	public float jSpinner_Corralled_C_getValue()
	{
		return ((Double)jSpinner_CorralledC.getModel().getValue()).floatValue();
	}
	public float jSpinner_Corralled_L_getValue()
	{
		return ((Double)jSpinner_Corralled_L.getModel().getValue()).floatValue();
	}
	public float jSpinner_Anomalous_D_getValue()
	{
		return ((Double)jSpinner_Anomalous_D.getModel().getValue()).floatValue();
	}
	public float jSpinner_Anomalous_Alfa_getValue()
	{
		return ((Double)jSpinner_Anomalous_Alfa.getModel().getValue()).floatValue();
	}
	public float jSpinner_Anomalous_C_getValue()
	{
		return ((Double)jSpinner_AnomalousC.getModel().getValue()).floatValue();
	}
	public float jSpinner_Directed_D_getValue()
	{
		return ((Double)jSpinner_Directed_D.getModel().getValue()).floatValue();
	}
	public float jSpinner_Directed_V_getValue()
	{
		return ((Double)jSpinner_Directed_V.getModel().getValue()).floatValue();
	}
	public float jSpinner_Directed_C_getValue()
	{
		return ((Double)jSpinner_DirectedC.getModel().getValue()).floatValue();
	}
	public void jSpinners_setStep(double step)
	{
		jSpinner_NormalDiffusionD.setModel(new SpinnerNumberModel(jSpinner_Normal_D_getValue(), 0, 999999999, step));
		jSpinner_NormalC.setModel(new SpinnerNumberModel(jSpinner_Normal_C_getValue(), -1000, 1000, step));
		jSpinner_Corralled_D.setModel(new SpinnerNumberModel(jSpinner_Corralled_D_getValue(), 0, 999999999, step));
		jSpinner_Corralled_L.setModel(new SpinnerNumberModel(jSpinner_Corralled_L_getValue(), 0, 999999999, step));
		jSpinner_CorralledC.setModel(new SpinnerNumberModel(jSpinner_Corralled_C_getValue(), -1000, 1000, step));
		jSpinner_Anomalous_D.setModel(new SpinnerNumberModel(jSpinner_Anomalous_D_getValue(), 0, 999999999, step));
		jSpinner_Anomalous_Alfa.setModel(new SpinnerNumberModel(jSpinner_Anomalous_Alfa_getValue(), 0, 999999999, step));
		jSpinner_AnomalousC.setModel(new SpinnerNumberModel(jSpinner_Anomalous_C_getValue(), -1000, 1000, step));
		jSpinner_Directed_D.setModel(new SpinnerNumberModel(jSpinner_Directed_D_getValue(), 0, 999999999, step));
		jSpinner_Directed_V.setModel(new SpinnerNumberModel(jSpinner_Directed_V_getValue(), 0, 999999999, step));
		jSpinner_DirectedC.setModel(new SpinnerNumberModel(jSpinner_Directed_C_getValue(), -1000, 1000, step));		
	}
	public void jSpinner_Normal_D_setValue(double value)
	{
		jSpinner_NormalDiffusionD.getModel().setValue(value);
	}
	public void jSpinner_Normal_C_setValue(double value)
	{
		jSpinner_NormalC.getModel().setValue(value);
	}
 	public void jSpinner_Corralled_D_setValue(double value)
	{
		jSpinner_Corralled_D.getModel().setValue(value);
	}
	public void jSpinner_Corralled_L_setValue(double value)
	{
		jSpinner_Corralled_L.getModel().setValue(value);
	}
	public void jSpinner_Corralled_C_setValue(double value)
	{
		jSpinner_CorralledC.getModel().setValue(value);
	}
	public void jSpinner_Anomalous_D_setValue(double value)
	{
		jSpinner_Anomalous_D.getModel().setValue(value);
	}
	public void jSpinner_Anomalous_Alfa_setValue(double value)
	{
		jSpinner_Anomalous_Alfa.getModel().setValue(value);
	}
	public void jSpinner_Anomalous_C_setValue(double value)
	{
		jSpinner_AnomalousC.getModel().setValue(value);
	}
	
	public void jSpinner_Directed_D_setValue(double value)
	{
		jSpinner_Directed_D.getModel().setValue(value);
	}
	public void jSpinner_Directed_V_setValue(double value)
	{
		jSpinner_Directed_V.getModel().setValue(value);
	}
	public void jSpinner_Directed_C_setValue(double value)
	{
		jSpinner_DirectedC.getModel().setValue(value);
	}

	public void jProgressBar_FitAll_setValue(int value)
	{		
		jProgressBar_Fit.setValue(value);
		jProgressBar_Fit.setString(value+"%");		
	}
	public void jProgressBar_FitAll_setMaxValue(int lenght) 
	{
		jProgressBar_Fit.setMaximum(lenght);		
	}
	public boolean radioButton25_isSelected()
	{
		return radioButton25.isSelected();
	}
	
	
	public void setFittingPanelEnabled(boolean bool)
	{
		jCheckBox_Manual_MSD_ShowNormal.setEnabled(bool);
		jCheckBox_Manual_MSD_ShowAnomalous.setEnabled(bool);
		jCheckBox_Manual_MSD_ShowCorralled.setEnabled(bool);
		jCheckBox_Manual_MSD_ShowDirected.setEnabled(bool);
		jSpinner_NormalDiffusionD.setEnabled(bool);
		jSpinner_Anomalous_D.setEnabled(bool);
		jSpinner_Anomalous_Alfa.setEnabled(bool);
		jSpinner_Corralled_D.setEnabled(bool);
		jSpinner_Corralled_L.setEnabled(bool);
		jSpinner_Directed_D.setEnabled(bool);
		jSpinner_Directed_V.setEnabled(bool);
		jCheckBox_ShowAutomatic.setEnabled(bool);
		jProgressBar_Fit.setEnabled(bool);
		jButton_AutomaticFit.setEnabled(bool);
		jButton_AutoFitAllTrajs.setEnabled(bool);
		jButton_BestFitNormal.setEnabled(bool);
		jButton_SetNormal.setEnabled(bool);
		jButton_BestFitCorralled.setEnabled(bool);
		jButton_SetCorralled.setEnabled(bool);
		jButton_BestFitAnomalous.setEnabled(bool);
		jButton_SetAnomalous.setEnabled(bool);
		jButton_BestFitDirected.setEnabled(bool);
		jButton_SetDirected.setEnabled(bool);
	}
	/*--------------------------------------------------------------------------------*/


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
		this.setSize(650, 650);
		this.setPreferredSize(new Dimension(700, 600));
		this.setContentPane(getJContentPane());
		this.setTitle("Type of Motion Analysis");
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
			jContentPane.add(getJPanel_MSDAnalysis(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel_MSDAnalysis	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_MSDAnalysis() {
		if (jPanel_MSDAnalysis == null) {
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			gridBagConstraints75.fill = GridBagConstraints.BOTH;
			gridBagConstraints75.gridy = 2;
			gridBagConstraints75.weightx = 0.0D;
			gridBagConstraints75.weighty = 0.0D;
			gridBagConstraints75.gridx = 0;
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			gridBagConstraints79.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints79.fill = GridBagConstraints.BOTH;
			gridBagConstraints79.gridy = 1;
			gridBagConstraints79.weightx = 1.0D;
			gridBagConstraints79.weighty = 0.0D;
			gridBagConstraints79.gridwidth = 1;
			gridBagConstraints79.gridx = 0;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints58.fill = GridBagConstraints.BOTH;
			gridBagConstraints58.gridx = 0;
			gridBagConstraints58.gridy = 0;
			gridBagConstraints58.weightx = 1.0D;
			gridBagConstraints58.weighty = 1.0D;
			jPanel_MSDAnalysis = new JPanel();
			GridBagLayout gbl_jPanel_MSDAnalysis = new GridBagLayout();
			gbl_jPanel_MSDAnalysis.rowWeights = new double[]{0.0, 0.0, 0.0};
			gbl_jPanel_MSDAnalysis.columnWeights = new double[]{1.0};
			gbl_jPanel_MSDAnalysis.rowHeights = new int[]{354, 211, 32};
			jPanel_MSDAnalysis.setLayout(gbl_jPanel_MSDAnalysis);
			jPanel_MSDAnalysis.add(getJPanel_MSD_Plot(), gridBagConstraints58);
			jPanel_MSDAnalysis.add(getJPanel_MSD_ManualFit(), gridBagConstraints79);
			jPanel_MSDAnalysis.add(getJPanel_MSD_Automatic(), gridBagConstraints75);
		}
		return jPanel_MSDAnalysis;
	}

	/**
	 * This method initializes jPanel_MSD_Plot	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_MSD_Plot() {
		if (jPanel_MSD_Plot == null) {
			GridBagConstraints gridBagConstraints_msdIC = new GridBagConstraints();
			gridBagConstraints_msdIC.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints_msdIC.gridx = 0;
			gridBagConstraints_msdIC.gridy = 1;
			gridBagConstraints_msdIC.fill = GridBagConstraints.BOTH;
			gridBagConstraints_msdIC.weighty = 1.0D;
			gridBagConstraints_msdIC.weightx = 1.0D;
			jPanel_MSD_Plot = new JPanel();
			jPanel_MSD_Plot.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			GridBagLayout gbl_jPanel_MSD_Plot = new GridBagLayout();
			gbl_jPanel_MSD_Plot.rowHeights = new int[]{0, 264, 0};
			jPanel_MSD_Plot.setLayout(gbl_jPanel_MSD_Plot);
			jPanel_MSD_Plot.setPreferredSize(new Dimension(100, 150));
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.insets = new Insets(0, 0, 5, 0);
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 0;
			jPanel_MSD_Plot.add(getPanel_2(), gbc_panel_2);
			jPanel_MSD_Plot.add(getCp_MSD(), gridBagConstraints_msdIC);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			jPanel_MSD_Plot.add(getPanel(), gbc_panel);
		}
		return jPanel_MSD_Plot;
	}

	/**
	 * This method initializes cp_MSD	
	 * 	
	 * @return org.jfree.chart.ChartPanel	
	 */
	private ChartPanel getCp_MSD() {
		if (cp_MSD == null) {
			cp_MSD = new ChartPanel(new JFreeChartMSD(new XYPlot(),"px","frame"));
		}
		return cp_MSD;
	}

	/**
	 * This method initializes jPanel_MSD_ManualFit	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_MSD_ManualFit() {
		if (jPanel_MSD_ManualFit == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints16.gridx = 4;
			gridBagConstraints16.weightx = 1.0D;
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridy = 3;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints15.gridx = 4;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.gridy = 1;
			TitledBorder titledBorder3 = BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(null, null), "MSD Fit", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59));
			titledBorder3.setTitle("Manual Fit");
			GridBagConstraints gridBagConstraints267 = new GridBagConstraints();
			gridBagConstraints267.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints267.gridx = 7;
			gridBagConstraints267.gridy = 1;
			GridBagConstraints gridBagConstraints266 = new GridBagConstraints();
			gridBagConstraints266.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints266.gridx = 6;
			gridBagConstraints266.gridy = 1;
			GridBagConstraints gridBagConstraints264 = new GridBagConstraints();
			gridBagConstraints264.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints264.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints264.gridy = 1;
			gridBagConstraints264.weightx = 1.0D;
			gridBagConstraints264.gridx = 1;
			GridBagConstraints gridBagConstraints263 = new GridBagConstraints();
			gridBagConstraints263.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints263.fill = GridBagConstraints.BOTH;
			gridBagConstraints263.gridy = 1;
			gridBagConstraints263.weightx = 0.0D;
			gridBagConstraints263.gridx = 0;
			GridBagConstraints gridBagConstraints197 = new GridBagConstraints();
			gridBagConstraints197.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints197.gridx = 6;
			gridBagConstraints197.gridy = 3;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints23.gridx = 7;
			gridBagConstraints23.gridy = 3;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints30.fill = GridBagConstraints.BOTH;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.gridx = 0;
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints110.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints110.gridy = 3;
			gridBagConstraints110.weightx = 1.0D;
			gridBagConstraints110.weighty = 0.0D;
			gridBagConstraints110.gridx = 1;
			GridBagConstraints gridBagConstraints109 = new GridBagConstraints();
			gridBagConstraints109.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints109.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints109.gridy = 3;
			gridBagConstraints109.weightx = 1.0D;
			gridBagConstraints109.gridx = 3;
			jPanel_MSD_ManualFit = new JPanel();
			GridBagLayout gbl_jPanel_MSD_ManualFit = new GridBagLayout();
			gbl_jPanel_MSD_ManualFit.columnWidths = new int[]{0, 144, 0, 0, 94, 0, 0, 0};
			jPanel_MSD_ManualFit.setLayout(gbl_jPanel_MSD_ManualFit);
			jPanel_MSD_ManualFit.setPreferredSize(new Dimension(300, 150));
			jPanel_MSD_ManualFit.setBorder(titledBorder3);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.anchor = GridBagConstraints.EAST;
			gbc_panel_1.gridwidth = 8;
			gbc_panel_1.insets = new Insets(0, 0, 5, 0);
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = 0;
			jPanel_MSD_ManualFit.add(getPanel_1(), gbc_panel_1);
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints33.fill = GridBagConstraints.BOTH;
			gridBagConstraints33.gridy = 2;
			gridBagConstraints33.gridx = 0;
			jPanel_MSD_ManualFit.add(getJCheckBox_Manual_MSD_ShowAnomalous(), gridBagConstraints33);
			GridBagConstraints gridBagConstraints112 = new GridBagConstraints();
			gridBagConstraints112.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints112.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints112.gridy = 2;
			gridBagConstraints112.weightx = 1.0D;
			gridBagConstraints112.gridx = 1;
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Anomalous_D(), gridBagConstraints112);
			GridBagConstraints gridBagConstraints114 = new GridBagConstraints();
			gridBagConstraints114.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints114.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints114.gridy = 2;
			gridBagConstraints114.weightx = 1.0D;
			gridBagConstraints114.gridx = 3;
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Anomalous_Alfa(), gridBagConstraints114);
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints17.gridx = 4;
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridy = 2;
			jPanel_MSD_ManualFit.add(getJSpinner_AnomalousC(), gridBagConstraints17);
			GridBagConstraints gridBagConstraints204 = new GridBagConstraints();
			gridBagConstraints204.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints204.gridx = 6;
			gridBagConstraints204.gridy = 2;
			jPanel_MSD_ManualFit.add(getJButton_Manual_MSD_BestFitAnomalous(), gridBagConstraints204);
			GridBagConstraints gridBagConstraints146 = new GridBagConstraints();
			gridBagConstraints146.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints146.gridx = 7;
			gridBagConstraints146.gridy = 2;
			jPanel_MSD_ManualFit.add(getJButton_MSD_SetAnomalous(), gridBagConstraints146);
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints34.fill = GridBagConstraints.BOTH;
			gridBagConstraints34.gridy = 4;
			gridBagConstraints34.gridx = 0;
			jPanel_MSD_ManualFit.add(getJCheckBox_Manual_MSD_ShowDirected(), gridBagConstraints34);
			GridBagConstraints gridBagConstraints115 = new GridBagConstraints();
			gridBagConstraints115.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints115.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints115.gridy = 4;
			gridBagConstraints115.weightx = 1.0D;
			gridBagConstraints115.weighty = 0.0D;
			gridBagConstraints115.gridx = 1;
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Directed_D(), gridBagConstraints115);
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Corralled_L(), gridBagConstraints109);
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Corralled_D(), gridBagConstraints110);
			jPanel_MSD_ManualFit.add(getJCheckBox_Manual_MSD_ShowCorralled(), gridBagConstraints30);
			jPanel_MSD_ManualFit.add(getJButton_MSD_SetCorralled(), gridBagConstraints23);
			GridBagConstraints gridBagConstraints118 = new GridBagConstraints();
			gridBagConstraints118.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints118.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints118.gridy = 4;
			gridBagConstraints118.weightx = 1.0D;
			gridBagConstraints118.gridx = 3;
			jPanel_MSD_ManualFit.add(getJSpinner_MSD_Directed_V(), gridBagConstraints118);
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints18.gridx = 4;
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.gridy = 4;
			jPanel_MSD_ManualFit.add(getJSpinner_DirectedC(), gridBagConstraints18);
			GridBagConstraints gridBagConstraints205 = new GridBagConstraints();
			gridBagConstraints205.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints205.gridx = 6;
			gridBagConstraints205.gridy = 4;
			jPanel_MSD_ManualFit.add(getJButton_Manual_MSD_BestFitDirected(), gridBagConstraints205);
			jPanel_MSD_ManualFit.add(getJButton_Manual_MSD_BestFitCorralled(), gridBagConstraints197);
			jPanel_MSD_ManualFit.add(getJCheckBox_Manual_MSD_ShowNormal(), gridBagConstraints263);
			jPanel_MSD_ManualFit.add(getJSpinner_NormalDiffusionD(), gridBagConstraints264);
			jPanel_MSD_ManualFit.add(getJButton_BestNormal(), gridBagConstraints266);
			jPanel_MSD_ManualFit.add(getJButton_SetNormal(), gridBagConstraints267);
			jPanel_MSD_ManualFit.add(getJSpinner_NormalC(), gridBagConstraints15);
			jPanel_MSD_ManualFit.add(getJSpinner_CorralledC(), gridBagConstraints16);
			GridBagConstraints gridBagConstraints177 = new GridBagConstraints();
			gridBagConstraints177.gridx = 7;
			gridBagConstraints177.gridy = 4;
			jPanel_MSD_ManualFit.add(getJButton_MSD_SetDirected(), gridBagConstraints177);
		}
		return jPanel_MSD_ManualFit;
	}

	/**
	 * This method initializes jSpinner_MSD_Corralled_L	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Corralled_L() {
		if (jSpinner_Corralled_L == null) {
			jSpinner_Corralled_L = new JSpinner(new SpinnerNumberModel(5, 0.0,999999.0, 0.01));
			jSpinner_Corralled_L.setToolTipText("L (corral size)");
			jSpinner_Corralled_L.setPreferredSize(new Dimension(40, 30));
			jSpinner_Corralled_L.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Corralled_L
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Corralled_L;
	}

	/**
	 * This method initializes jSpinner_MSD_Anomalous_D	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Anomalous_D() {
		if (jSpinner_Anomalous_D == null) {
			jSpinner_Anomalous_D = new JSpinner(new SpinnerNumberModel(0.01, 0.0, 999999.0, 0.01));
			jSpinner_Anomalous_D.setToolTipText("Diffusion coefficient");
			jSpinner_Anomalous_D.setPreferredSize(new Dimension(60, 30));
			jSpinner_Anomalous_D.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Anomalous_D
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Anomalous_D;
	}

	/**
	 * This method initializes jSpinner_MSD_Directed_D	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Directed_D() {
		if (jSpinner_Directed_D == null) {
			jSpinner_Directed_D = new JSpinner(new SpinnerNumberModel(0.01, 0.0,999999.0, 0.01));
			jSpinner_Directed_D.setToolTipText("Diffusion coefficient");
			jSpinner_Directed_D.setPreferredSize(new Dimension(60, 30));
			jSpinner_Directed_D.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Directed_D
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Directed_D;
	}

	/**
	 * This method initializes jSpinner_MSD_Corralled_D	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Corralled_D() {
		if (jSpinner_Corralled_D == null) {
			jSpinner_Corralled_D = new JSpinner(new SpinnerNumberModel(0.01, 0.0, 999999.0, 0.01));
			jSpinner_Corralled_D.setToolTipText("Diffusion coefficient");
			jSpinner_Corralled_D.setLocale(new Locale("en", "US", ""));
			jSpinner_Corralled_D.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Corralled_D.setPreferredSize(new Dimension(60, 30));
			jSpinner_Corralled_D
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Corralled_D;
	}

	/**
	 * This method initializes jSpinner_MSD_Anomalous_Alfa	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Anomalous_Alfa() {
		if (jSpinner_Anomalous_Alfa == null) {
			jSpinner_Anomalous_Alfa = new JSpinner(new SpinnerNumberModel(1.0,0.0, 999999.0, 0.01));
			jSpinner_Anomalous_Alfa.setToolTipText("Alfa");
			jSpinner_Anomalous_Alfa.setPreferredSize(new Dimension(40, 30));
			jSpinner_Anomalous_Alfa.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Anomalous_Alfa
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Anomalous_Alfa;
	}

	/**
	 * This method initializes jSpinner_MSD_Directed_V	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_MSD_Directed_V() {
		if (jSpinner_Directed_V == null) {
			jSpinner_Directed_V = new JSpinner(new SpinnerNumberModel(0.1, 0.0,999999.0, 0.01));
			jSpinner_Directed_V.setToolTipText("V (Velocity)");
			jSpinner_Directed_V.setPreferredSize(new Dimension(40, 30));
			jSpinner_Directed_V.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_Directed_V
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_Directed_V;
	}

	/**
	 * This method initializes jCheckBox_Manual_MSD_ShowCorralled	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_MSD_ShowCorralled() {
		if (jCheckBox_Manual_MSD_ShowCorralled == null) {
			jCheckBox_Manual_MSD_ShowCorralled = new JCheckBox();
			jCheckBox_Manual_MSD_ShowCorralled.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Manual_MSD_ShowCorralled.setText("Constrained");
			jCheckBox_Manual_MSD_ShowCorralled
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							toma.jCheckBox_itemStateChanged();
						}
					});
		}
		return jCheckBox_Manual_MSD_ShowCorralled;
	}

	/**
	 * This method initializes jCheckBox_Manual_MSD_ShowAnomalous	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_MSD_ShowAnomalous() {
		if (jCheckBox_Manual_MSD_ShowAnomalous == null) {
			jCheckBox_Manual_MSD_ShowAnomalous = new JCheckBox();
			jCheckBox_Manual_MSD_ShowAnomalous.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Manual_MSD_ShowAnomalous.setToolTipText("MSD(&t)=4D(&t)^alfa+C");
			jCheckBox_Manual_MSD_ShowAnomalous.setText("Anomalous");
			jCheckBox_Manual_MSD_ShowAnomalous
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							toma.jCheckBox_itemStateChanged();
						}
					});
		}
		return jCheckBox_Manual_MSD_ShowAnomalous;
	}

	/**
	 * This method initializes jCheckBox_Manual_MSD_ShowDirected	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_MSD_ShowDirected() {
		if (jCheckBox_Manual_MSD_ShowDirected == null) {
			jCheckBox_Manual_MSD_ShowDirected = new JCheckBox();
			jCheckBox_Manual_MSD_ShowDirected.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Manual_MSD_ShowDirected.setToolTipText("MSD(&t)=4D(&t)+(V&t)^2+C");
			jCheckBox_Manual_MSD_ShowDirected.setText("Directed");
			jCheckBox_Manual_MSD_ShowDirected
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							toma.jCheckBox_itemStateChanged();
						}
					});
		}
		return jCheckBox_Manual_MSD_ShowDirected;
	}

	/**
	 * This method initializes jButton_MSD_SetCorralled	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_MSD_SetCorralled() {
		if (jButton_SetCorralled == null) {
			jButton_SetCorralled = new JButton();
			jButton_SetCorralled.setText("Set");
			jButton_SetCorralled.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_SetCorralled.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_SetCorralled_clicked();
				}
			});
		}
		return jButton_SetCorralled;
	}

	/**
	 * This method initializes jButton_MSD_SetAnomalous	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_MSD_SetAnomalous() {
		if (jButton_SetAnomalous == null) {
			jButton_SetAnomalous = new JButton();
			jButton_SetAnomalous.setText("Set");
			jButton_SetAnomalous.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_SetAnomalous.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_SetAnomalous_clicked();
				}
			});
		}
		return jButton_SetAnomalous;
	}

	/**
	 * This method initializes jButton_MSD_SetDirected	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_MSD_SetDirected() {
		if (jButton_SetDirected == null) {
			jButton_SetDirected = new JButton();
			jButton_SetDirected.setText("Set");
			jButton_SetDirected.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_SetDirected.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_SetDirected_clicked();
				}
			});
		}
		return jButton_SetDirected;
	}

	/**
	 * This method initializes jButton_Manual_MSD_BestFitCorralled	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Manual_MSD_BestFitCorralled() {
		if (jButton_BestFitCorralled == null) {
			jButton_BestFitCorralled = new JButton();
			jButton_BestFitCorralled.setText("Best");
			jButton_BestFitCorralled.setPreferredSize(new Dimension(50, 26));
			jButton_BestFitCorralled.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_BestFitCorralled
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							toma.jButton_BestFitCorralled_clicked();
						}
					});
		}
		return jButton_BestFitCorralled;
	}

	/**
	 * This method initializes jButton_Manual_MSD_BestFitAnomalous	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Manual_MSD_BestFitAnomalous() {
		if (jButton_BestFitAnomalous == null) {
			jButton_BestFitAnomalous = new JButton();
			jButton_BestFitAnomalous.setText("Best");
			jButton_BestFitAnomalous.setPreferredSize(new Dimension(50, 26));
			jButton_BestFitAnomalous.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_BestFitAnomalous
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							toma.jButton_BestFitAnomalous_clicked();
						}
					});
		}
		return jButton_BestFitAnomalous;
	}

	/**
	 * This method initializes jButton_Manual_MSD_BestFitDirected	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Manual_MSD_BestFitDirected() {
		if (jButton_BestFitDirected == null) {
			jButton_BestFitDirected = new JButton();
			jButton_BestFitDirected.setText("Best");
			jButton_BestFitDirected.setPreferredSize(new Dimension(50, 26));
			jButton_BestFitDirected.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_BestFitDirected
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							toma.jButton_BestFitDirected_clicked();
						}
					});
		}
		return jButton_BestFitDirected;
	}

	/**
	 * This method initializes jCheckBox_Manual_MSD_ShowNormal	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_MSD_ShowNormal() {
		if (jCheckBox_Manual_MSD_ShowNormal == null) {
			jCheckBox_Manual_MSD_ShowNormal = new JCheckBox();
			jCheckBox_Manual_MSD_ShowNormal.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBox_Manual_MSD_ShowNormal.setPreferredSize(new Dimension(65, 18));
			jCheckBox_Manual_MSD_ShowNormal.setToolTipText("MSD(&t) = 4D(&t)+C");
			jCheckBox_Manual_MSD_ShowNormal.setText("Normal");
			jCheckBox_Manual_MSD_ShowNormal
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							toma.jCheckBox_itemStateChanged();
						}
					});
		}
		return jCheckBox_Manual_MSD_ShowNormal;
	}

	/**
	 * This method initializes jSpinner_NormalDiffusionD	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_NormalDiffusionD() {
		if (jSpinner_NormalDiffusionD == null) {
			jSpinner_NormalDiffusionD = new JSpinner(new SpinnerNumberModel(0.01, 0.0, 999999.0, 0.01));
			jSpinner_NormalDiffusionD.setToolTipText("Diffusion coefficient");
			jSpinner_NormalDiffusionD.setFont(new Font("SansSerif", Font.PLAIN, 12));
			jSpinner_NormalDiffusionD.setLocale(new Locale("en", "US", ""));
			jSpinner_NormalDiffusionD.setPreferredSize(new Dimension(60, 30));
			jSpinner_NormalDiffusionD
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							toma.jSpinner_stateChanged();
						}
					});
		}
		return jSpinner_NormalDiffusionD;
	}

	/**
	 * This method initializes jButton_BestNormal	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_BestNormal() {
		if (jButton_BestFitNormal == null) {
			jButton_BestFitNormal = new JButton();
			jButton_BestFitNormal.setText("Best");
			jButton_BestFitNormal.setPreferredSize(new Dimension(50, 26));
			jButton_BestFitNormal.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_BestFitNormal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_BestFitNormal_clicked();
				}
			});
		}
		return jButton_BestFitNormal;
	}

	/**
	 * This method initializes jButton_SetNormal	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_SetNormal() {
		if (jButton_SetNormal == null) {
			jButton_SetNormal = new JButton();
			jButton_SetNormal.setText("Set");
			jButton_SetNormal.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_SetNormal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_SetNormal_clicked();
				}
			});
		}
		return jButton_SetNormal;
	}

	/**
	 * This method initializes jPanel_MSD_Automatic	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_MSD_Automatic() {
		if (jPanel_MSD_Automatic == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridy = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints10.anchor = GridBagConstraints.CENTER;
			gridBagConstraints10.gridwidth = 1;
			gridBagConstraints10.gridx = 4;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.weightx = 0.0D;
			gridBagConstraints10.weighty = 0.0D;
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			GridBagConstraints gbc_jCheckBox_ShowAutomatic = new GridBagConstraints();
			gbc_jCheckBox_ShowAutomatic.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_ShowAutomatic.fill = GridBagConstraints.NONE;
			gbc_jCheckBox_ShowAutomatic.gridy = 0;
			gbc_jCheckBox_ShowAutomatic.weightx = 0.0D;
			gbc_jCheckBox_ShowAutomatic.weighty = 0.0D;
			gbc_jCheckBox_ShowAutomatic.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.weighty = 0.0D;
			gridBagConstraints6.gridx = 1;
			jPanel_MSD_Automatic = new JPanel();
			GridBagLayout gbl_jPanel_MSD_Automatic = new GridBagLayout();
			gbl_jPanel_MSD_Automatic.columnWidths = new int[]{49, 288, 0, 0, 44, 0};
			jPanel_MSD_Automatic.setLayout(gbl_jPanel_MSD_Automatic);
			jPanel_MSD_Automatic.setPreferredSize(new Dimension(100, 50));
			jPanel_MSD_Automatic.setBorder(new EmptyBorder(0, 0, 0, 0));
			jPanel_MSD_Automatic.add(getJProgressBar_MSD_Fit(), gridBagConstraints6);
			jPanel_MSD_Automatic.add(getJCheckBox_ShowAutomatic(), gbc_jCheckBox_ShowAutomatic);
			GridBagConstraints gbc_radioButton75 = new GridBagConstraints();
			gbc_radioButton75.insets = new Insets(0, 0, 0, 5);
			gbc_radioButton75.gridx = 2;
			gbc_radioButton75.gridy = 0;
			jPanel_MSD_Automatic.add(getRadioButton75(), gbc_radioButton75);
			GridBagConstraints gbc_rdbtnAllPoints = new GridBagConstraints();
			gbc_rdbtnAllPoints.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnAllPoints.gridx = 3;
			gbc_rdbtnAllPoints.gridy = 0;
			jPanel_MSD_Automatic.add(getRdbtnAllPoints(), gbc_rdbtnAllPoints);
			jPanel_MSD_Automatic.add(getJButton_Automatic_MSDFit(), gridBagConstraints10);
			jPanel_MSD_Automatic.add(getJButton_AutoFitAllTrajs(), gridBagConstraints);
		}
		return jPanel_MSD_Automatic;
	}

	/**
	 * This method initializes jProgressBar_MSD_Fit	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar_MSD_Fit() {
		if (jProgressBar_Fit == null) {
			jProgressBar_Fit = new JProgressBar();
			jProgressBar_Fit.setPreferredSize(new Dimension(80, 20));
			jProgressBar_Fit.setStringPainted(true);
		}
		return jProgressBar_Fit;
	}

	/**
	 * This method initializes jCheckBox_Manual_MSD_ShowAutomatic	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_ShowAutomatic() {
		if (jCheckBox_ShowAutomatic == null) {
			jCheckBox_ShowAutomatic = new JCheckBox();
			jCheckBox_ShowAutomatic.setHorizontalAlignment(SwingConstants.LEADING);
			jCheckBox_ShowAutomatic.setText("Automatic");
			jCheckBox_ShowAutomatic
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							toma.jCheckBox_itemStateChanged();
						}
					});
		}
		return jCheckBox_ShowAutomatic;
	}

	/**
	 * This method initializes jButton_Automatic_MSDFit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Automatic_MSDFit() {
		if (jButton_AutomaticFit == null) {
			jButton_AutomaticFit = new JButton();
			jButton_AutomaticFit.setPreferredSize(new Dimension(120, 30));
			jButton_AutomaticFit.setHorizontalTextPosition(SwingConstants.TRAILING);
			jButton_AutomaticFit.setText("Auto Fit Current traj");
			jButton_AutomaticFit.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_AutomaticFit.setHorizontalAlignment(SwingConstants.LEADING);
			jButton_AutomaticFit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_Manual_AutomaticMSDFit_clicked();
				}
			});
		}
		return jButton_AutomaticFit;
	}

	/**
	 * This method initializes jButton_AutoFitAllTrajs	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_AutoFitAllTrajs() {
		if (jButton_AutoFitAllTrajs == null) {
			jButton_AutoFitAllTrajs = new JButton();
			jButton_AutoFitAllTrajs.setText("Auto Fit All Trajs");
			jButton_AutoFitAllTrajs.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jButton_AutoFitAllTrajs.setPreferredSize(new Dimension(115, 30));
			jButton_AutoFitAllTrajs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					toma.jButton_AutoFitAllTrajs();
				}
			});
		}
		return jButton_AutoFitAllTrajs;
	}

	/**
	 * This method initializes jSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner() {
		if (jSpinner == null) {
			jSpinner = new JSpinner(new SpinnerNumberModel(-2, -5, 10, 1));
			jSpinner.setPreferredSize(new Dimension(60, 28));
			jSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					toma.jSpinner_SpinnersStep_stateChanged();
				}
			});
		}
		return jSpinner;
	}

	/**
	 * This method initializes jSpinner_InitialMSDPoint	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_InitialMSDPoint() {
		if (jSpinner_InitialMSDPoint == null) {
			jSpinner_InitialMSDPoint = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
			jSpinner_InitialMSDPoint.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					toma.finalMSDFitPoint_stateChanged();
				}
			});
		}
		return jSpinner_InitialMSDPoint;
	}

	/**
	 * This method initializes jCheckBox_showSD	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_showSD() {
		if (jCheckBox_showSD == null) {
			jCheckBox_showSD = new JCheckBox();
			jCheckBox_showSD.setText("SD");
			jCheckBox_showSD.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					toma.jCheckBox_itemStateChanged();
				}
			});
		}
		return jCheckBox_showSD;
	}

	/**
	 * This method initializes jSpinner_NormalC	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_NormalC() {
		if (jSpinner_NormalC == null) {
			jSpinner_NormalC = new JSpinner();
			jSpinner_NormalC.setPreferredSize(new Dimension(30, 28));
			jSpinner_NormalC.setEnabled(false);
			jSpinner_NormalC.setModel(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1));
			jSpinner_NormalC.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					toma.jSpinner_stateChanged();
				}
			});
		}
		return jSpinner_NormalC;
	}

	/**
	 * This method initializes jSpinner_CorralledC	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_CorralledC() {
		if (jSpinner_CorralledC == null) {
			jSpinner_CorralledC = new JSpinner();
			jSpinner_CorralledC.setPreferredSize(new Dimension(30, 28));
			jSpinner_CorralledC.setEnabled(false);
			jSpinner_CorralledC.setModel(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1));
			jSpinner_CorralledC.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					toma.jSpinner_stateChanged();
				}
			});
		}
		return jSpinner_CorralledC;
	}

	/**
	 * This method initializes jSpinner_AnomalousC	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_AnomalousC() {
		if (jSpinner_AnomalousC == null) {
			jSpinner_AnomalousC = new JSpinner();
			jSpinner_AnomalousC.setPreferredSize(new Dimension(30, 28));
			jSpinner_AnomalousC.setEnabled(false);
			jSpinner_AnomalousC.setModel(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1));
			jSpinner_AnomalousC.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					toma.jSpinner_stateChanged();
				}
			});
		}
		return jSpinner_AnomalousC;
	}

	/**
	 * This method initializes jSpinner_DirectedC	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_DirectedC() {
		if (jSpinner_DirectedC == null) {
			jSpinner_DirectedC = new JSpinner();
			jSpinner_DirectedC.setPreferredSize(new Dimension(30, 28));
			jSpinner_DirectedC.setEnabled(false);
			jSpinner_DirectedC.setModel(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1));
			jSpinner_DirectedC.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					toma.jSpinner_stateChanged();
				}
			});
		}
		return jSpinner_DirectedC;
	}

	/**
	 * This method initializes jComboBox_FitMethod	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_FitMethod() {
		if (jComboBox_FitMethod == null) {
			jComboBox_FitMethod = new JComboBox(new String[]{"IJ Simplex","LM","LM(weighted)"});
			jComboBox_FitMethod.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					toma.jComboBox_FitMethod_stateChanged();
				}
			});
		}
		return jComboBox_FitMethod;
	}

	/**
	 * This method initializes jCheckBox_Variance	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Variance() {
		if (jCheckBox_Variance == null) {
			jCheckBox_Variance = new JCheckBox();
			jCheckBox_Variance.setText("Variance");
			jCheckBox_Variance.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					toma.jCheckBox_itemStateChanged();
				}
			});
		}
		return jCheckBox_Variance;
	}

	/**
	 * This method initializes jCheckBox_offset	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_offset() {
		if (jCheckBox_offset == null) {
			jCheckBox_offset = new JCheckBox();
			jCheckBox_offset.setText("C");
			jCheckBox_offset.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					toma.jCheckBox_fitWithOffset_stateChanged();
				}
			});
		}
		return jCheckBox_offset;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private Action getAction() {
		if (action == null) {
			action = new SwingAction();
		}
		return action;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{92, 0, 92, 162, 100, 116, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			jLabel1 = new JLabel();
			GridBagConstraints gbc_jLabel1 = new GridBagConstraints();
			gbc_jLabel1.anchor = GridBagConstraints.EAST;
			gbc_jLabel1.insets = new Insets(0, 0, 0, 5);
			gbc_jLabel1.gridx = 0;
			gbc_jLabel1.gridy = 0;
			panel.add(jLabel1, gbc_jLabel1);
			jLabel1.setText("Start fit point:");
			GridBagConstraints gbc_jSpinner_InitialMSDPoint = new GridBagConstraints();
			gbc_jSpinner_InitialMSDPoint.insets = new Insets(0, 0, 0, 5);
			gbc_jSpinner_InitialMSDPoint.gridx = 1;
			gbc_jSpinner_InitialMSDPoint.gridy = 0;
			panel.add(getJSpinner_InitialMSDPoint(), gbc_jSpinner_InitialMSDPoint);
			jLabel_FinalMSDPoint = new JLabel();
			GridBagConstraints gbc_jLabel_FinalMSDPoint = new GridBagConstraints();
			gbc_jLabel_FinalMSDPoint.insets = new Insets(0, 0, 0, 5);
			gbc_jLabel_FinalMSDPoint.gridx = 2;
			gbc_jLabel_FinalMSDPoint.gridy = 0;
			panel.add(jLabel_FinalMSDPoint, gbc_jLabel_FinalMSDPoint);
			jLabel_FinalMSDPoint.setText("Final fit point:");
			GridBagConstraints gbc_sliderMSDFinalPoint = new GridBagConstraints();
			gbc_sliderMSDFinalPoint.fill = GridBagConstraints.HORIZONTAL;
			gbc_sliderMSDFinalPoint.insets = new Insets(0, 0, 0, 5);
			gbc_sliderMSDFinalPoint.gridx = 3;
			gbc_sliderMSDFinalPoint.gridy = 0;
			panel.add(getSliderMSDFinalPoint(), gbc_sliderMSDFinalPoint);
			GridBagConstraints gbc_textField_FinalMSDFitPoint = new GridBagConstraints();
			gbc_textField_FinalMSDFitPoint.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_FinalMSDFitPoint.insets = new Insets(0, 0, 0, 5);
			gbc_textField_FinalMSDFitPoint.gridx = 4;
			gbc_textField_FinalMSDFitPoint.gridy = 0;
			panel.add(getTextField_FinalMSDFitPoint(), gbc_textField_FinalMSDFitPoint);
			GridBagConstraints gbc_btnRecalculateDFor = new GridBagConstraints();
			gbc_btnRecalculateDFor.gridx = 5;
			gbc_btnRecalculateDFor.gridy = 0;
			panel.add(getBtnRecalculateDFor(), gbc_btnRecalculateDFor);
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{78, 140, 0, 0, 0, 0, 0, 0, 103, 0, 0};
			gbl_panel_1.rowHeights = new int[]{0, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			jLabel3 = new JLabel();
			GridBagConstraints gbc_jLabel3 = new GridBagConstraints();
			gbc_jLabel3.anchor = GridBagConstraints.EAST;
			gbc_jLabel3.insets = new Insets(0, 0, 0, 5);
			gbc_jLabel3.gridx = 0;
			gbc_jLabel3.gridy = 0;
			panel_1.add(jLabel3, gbc_jLabel3);
			jLabel3.setText("Fit method:");
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gbc_jComboBox_FitMethod = new GridBagConstraints();
			gbc_jComboBox_FitMethod.fill = GridBagConstraints.HORIZONTAL;
			gbc_jComboBox_FitMethod.insets = new Insets(0, 0, 0, 5);
			gbc_jComboBox_FitMethod.gridx = 1;
			gbc_jComboBox_FitMethod.gridy = 0;
			panel_1.add(getJComboBox_FitMethod(), gbc_jComboBox_FitMethod);
			GridBagConstraints gbc_chckbxShowFitInfo = new GridBagConstraints();
			gbc_chckbxShowFitInfo.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxShowFitInfo.gridx = 2;
			gbc_chckbxShowFitInfo.gridy = 0;
			panel_1.add(getChckbxShowFitInfo(), gbc_chckbxShowFitInfo);
			GridBagConstraints gbc_jCheckBox_offset = new GridBagConstraints();
			gbc_jCheckBox_offset.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_offset.gridx = 6;
			gbc_jCheckBox_offset.gridy = 0;
			panel_1.add(getJCheckBox_offset(), gbc_jCheckBox_offset);
			jLabel = new JLabel();
			GridBagConstraints gbc_jLabel = new GridBagConstraints();
			gbc_jLabel.insets = new Insets(0, 0, 0, 5);
			gbc_jLabel.gridx = 8;
			gbc_jLabel.gridy = 0;
			panel_1.add(jLabel, gbc_jLabel);
			jLabel.setText("Spinner Step 10^");
			GridBagConstraints gbc_jSpinner = new GridBagConstraints();
			gbc_jSpinner.gridx = 9;
			gbc_jSpinner.gridy = 0;
			panel_1.add(getJSpinner(), gbc_jSpinner);
		}
		return panel_1;
	}
	private JCheckBox getChckbxAverageMsd() {
		if (chckbxAverageMsd == null) {
			chckbxAverageMsd = new JCheckBox("Analyze averaged MSD:");
			chckbxAverageMsd.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					toma.jCheckBox_itemStateChanged();
				}
			});
		}
		return chckbxAverageMsd;
	}
	private JCheckBox getChckbxShowMarks() {
		if (chckbxShowMarks == null) {
			chckbxShowMarks = new JCheckBox("Start/Final fit points");
			chckbxShowMarks.setSelected(true);
			chckbxShowMarks.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					toma.jCheckBox_itemStateChanged();
				}
			});
		}
		return chckbxShowMarks;
	}
	private JButton getBtnRecalculateDFor() {
		if (btnRecalculateDFor == null) {
			btnRecalculateDFor = new JButton("Recalc All");
		}
		return btnRecalculateDFor;
	}
	private JSlider getSliderMSDFinalPoint() {
		if (sliderMSDFinalPoint == null) {
			sliderMSDFinalPoint = new JSlider(2,100,4);
			sliderMSDFinalPoint.setSnapToTicks(true);
			sliderMSDFinalPoint.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0)
				{
					toma.finalMSDFitPoint_stateChanged();
				}
			});
			
		}
		return sliderMSDFinalPoint;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.add(getChckbxShowMarks());
			panel_2.add(getJCheckBox_showSD());
			panel_2.add(getJCheckBox_Variance());
			panel_2.add(getChckbxNPoints());
			panel_2.add(getChckbxAverageMsd());
			panel_2.add(getComboBox_AvgMSDOption());
		}
		return panel_2;
	}
	private JCheckBox getChckbxNPoints() {
		if (chckbxNPoints == null) {
			chckbxNPoints = new JCheckBox("N\u00B0 of points");
			chckbxNPoints.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) 
				{
					toma.jCheckBox_itemStateChanged();
				}
			});
		}
		return chckbxNPoints;
	}
	private JTextField getTextField_FinalMSDFitPoint() {
		if (textField_FinalMSDFitPoint == null) {
			textField_FinalMSDFitPoint = new JTextField();
			textField_FinalMSDFitPoint.setEditable(false);
			textField_FinalMSDFitPoint.setHorizontalAlignment(SwingConstants.RIGHT);
			textField_FinalMSDFitPoint.setText("4");
			textField_FinalMSDFitPoint.setColumns(10);
		}
		return textField_FinalMSDFitPoint;
	}
	private JComboBox getComboBox_AvgMSDOption() {
		if (comboBox_AvgMSDOption == null) {
			comboBox_AvgMSDOption = new JComboBox(new String[]{"All","Normal", "Anomalous", "Constrained", "Directed"});
			comboBox_AvgMSDOption.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					toma.jCheckBox_AvgMSD_stateChanged();
				}
			});
		}
		return comboBox_AvgMSDOption;
	}
	private JCheckBox getChckbxShowFitInfo() {
		if (chckbxShowFitInfo == null) {
			chckbxShowFitInfo = new JCheckBox("Show Fit Info");
			chckbxShowFitInfo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					toma.jCheckBox_showFitInfo();					
				}
			});
		}
		return chckbxShowFitInfo;
	}
	private JRadioButton getRadioButton75() {
		if (radioButton25 == null) {
			radioButton25 = new JRadioButton("25%");
			buttonGroup.add(radioButton25);
			radioButton25.setSelected(true);
		}
		return radioButton25;
	}
	private JRadioButton getRdbtnAllPoints() {
		if (rdbtnAllPoints == null) {
			rdbtnAllPoints = new JRadioButton("All points");
			buttonGroup.add(rdbtnAllPoints);
		}
		return rdbtnAllPoints;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
