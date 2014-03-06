package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.lang.String;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import main.Thinker;

public class GUIMetrics extends JFrame 
{
	
	Thinker thinker;

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_DimensionSettings = null;
	private JLabel jLabel_FrameStep = null;
	private JTextField jTextField_FrameStep = null;
	private JComboBox jComboBox_TimeUnit = null;
	private JLabel jLabel_OnePixel = null;
	private JTextField jTextField_OnePixel = null;
	private JComboBox jComboBox_DistanceUnit = null;
	private JButton jButton_SetMetrics = null;

	private JLabel jLabel = null;

	private JTextField jTextField = null;

	/**
	 * This is the default constructor
	 */
	public GUIMetrics(Thinker thinker) {
		super();
		this.thinker = thinker;
		initialize();
	}
	
	/*----------------------------------------------------------*/
	public String jTextField_FrameStep_getText()
	{
		return jTextField_FrameStep.getText();
	}
	public String jTextField_DistanceFactor_getText()
	{
		return jTextField_OnePixel.getText();
	}
	public int jComboBox_TimeUnit_getSelectedIndex()
	{
		return jComboBox_TimeUnit.getSelectedIndex();
	}
	public int jComboBox_DistanceUnit_getSelectedIndex()
	{
		return jComboBox_DistanceUnit.getSelectedIndex();
	}
	
	
	/*----------------------------------------------------------*/

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
		this.setSize(330, 230);
		this.setContentPane(getJContentPane());
		this.setTitle("Metrics");
		this.setVisible(true);
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
			jContentPane.add(getJPanel_DimensionSettings(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel_DimensionSettings	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_DimensionSettings() {
		if (jPanel_DimensionSettings == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.weightx = 0.0D;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			jLabel = new JLabel();
			jLabel.setText("EFDC:");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setPreferredSize(new Dimension(150, 25));
			GridBagConstraints gridBagConstraints100 = new GridBagConstraints();
			gridBagConstraints100.fill = GridBagConstraints.BOTH;
			gridBagConstraints100.gridwidth = 2;
			gridBagConstraints100.gridy = 3;
			gridBagConstraints100.weightx = 0.0D;
			gridBagConstraints100.weighty = 0.0D;
			gridBagConstraints100.gridx = 1;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.fill = GridBagConstraints.NONE;
			gridBagConstraints67.gridy = 1;
			gridBagConstraints67.weightx = 0.0D;
			gridBagConstraints67.weighty = 0.0D;
			gridBagConstraints67.gridx = 2;
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints66.gridy = 1;
			gridBagConstraints66.gridx = 1;
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints65.gridy = 1;
			gridBagConstraints65.gridx = 0;
			jLabel_OnePixel = new JLabel();
			jLabel_OnePixel.setPreferredSize(new Dimension(150, 25));
			jLabel_OnePixel.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabel_OnePixel.setText("1px =");
			jLabel_OnePixel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.fill = GridBagConstraints.NONE;
			gridBagConstraints70.gridy = 0;
			gridBagConstraints70.weightx = 0.0D;
			gridBagConstraints70.weighty = 0.0D;
			gridBagConstraints70.gridx = 2;
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints69.gridy = 0;
			gridBagConstraints69.weightx = 0.0D;
			gridBagConstraints69.gridx = 1;
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.fill = GridBagConstraints.NONE;
			gridBagConstraints68.gridy = 0;
			gridBagConstraints68.weightx = 0.0D;
			gridBagConstraints68.weighty = 0.0D;
			gridBagConstraints68.gridx = 0;
			jLabel_FrameStep = new JLabel();
			jLabel_FrameStep.setPreferredSize(new Dimension(150, 25));
			jLabel_FrameStep.setText("Time between 2 frames:");
			jLabel_FrameStep.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel_DimensionSettings = new JPanel();
			GridBagLayout gbl_jPanel_DimensionSettings = new GridBagLayout();
			gbl_jPanel_DimensionSettings.rowHeights = new int[]{33, 35, 30, 35};
			gbl_jPanel_DimensionSettings.columnWidths = new int[]{130, 60, 113};
			jPanel_DimensionSettings.setLayout(gbl_jPanel_DimensionSettings);
			jPanel_DimensionSettings.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jPanel_DimensionSettings.setPreferredSize(new Dimension(25, 30));
			jPanel_DimensionSettings.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel_DimensionSettings.add(jLabel_FrameStep, gridBagConstraints68);
			jPanel_DimensionSettings.add(getJTextField_FrameStep(), gridBagConstraints69);
			jPanel_DimensionSettings.add(getJComboBox_TimeUnit(), gridBagConstraints70);
			jPanel_DimensionSettings.add(jLabel_OnePixel, gridBagConstraints65);
			jPanel_DimensionSettings.add(getJTextField_OnePixel(), gridBagConstraints66);
			jPanel_DimensionSettings.add(getJComboBox_DistanceUnit(), gridBagConstraints67);
			jPanel_DimensionSettings.add(getJButton_SetMetrics(), gridBagConstraints100);
			jPanel_DimensionSettings.add(jLabel, gridBagConstraints);
			jPanel_DimensionSettings.add(getJTextField(), gridBagConstraints1);
		}
		return jPanel_DimensionSettings;
	}

	/**
	 * This method initializes jTextField_FrameStep	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_FrameStep() {
		if (jTextField_FrameStep == null) {
			jTextField_FrameStep = new JTextField();
			jTextField_FrameStep.setPreferredSize(new Dimension(60, 25));
			jTextField_FrameStep.setText("1");
			jTextField_FrameStep.setToolTipText("Ex: \"1\", \"1.0\", \"10.3\"");
		}
		return jTextField_FrameStep;
	}

	/**
	 * This method initializes jComboBox_TimeUnit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_TimeUnit() {
		if (jComboBox_TimeUnit == null) {
			jComboBox_TimeUnit = new JComboBox(
					new String[] { "sec", "msec", "usec" });
			jComboBox_TimeUnit.setPreferredSize(new Dimension(70, 25));
		}
		return jComboBox_TimeUnit;
	}

	/**
	 * This method initializes jTextField_OnePixel	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_OnePixel() {
		if (jTextField_OnePixel == null) {
			jTextField_OnePixel = new JTextField();
			jTextField_OnePixel.setPreferredSize(new Dimension(60, 25));
			jTextField_OnePixel.setText("1");
			jTextField_OnePixel.setHorizontalAlignment(JTextField.LEADING);
			jTextField_OnePixel.setToolTipText("Ex: \"1\", \"1.0\", \"10.3\"");
		}
		return jTextField_OnePixel;
	}

	/**
	 * This method initializes jComboBox_DistanceUnit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_DistanceUnit() {
		if (jComboBox_DistanceUnit == null) {
			jComboBox_DistanceUnit = new JComboBox(new String[] { "mm", "um", "nm" });
			jComboBox_DistanceUnit.setSelectedIndex(1);
			jComboBox_DistanceUnit.setPreferredSize(new Dimension(70, 25));
		}
		return jComboBox_DistanceUnit;
	}

	/**
	 * This method initializes jButton_SetMetrics	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_SetMetrics() {
		if (jButton_SetMetrics == null) {
			jButton_SetMetrics = new JButton();
			jButton_SetMetrics.setEnabled(true);
			jButton_SetMetrics.setText("Set metrics on selected set");
			jButton_SetMetrics.setPreferredSize(new Dimension(100, 25));
			jButton_SetMetrics.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton_SetMetrics_clicked();
				}
			});
		}
		return jButton_SetMetrics;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(60, 25));
		}
		return jTextField;
	}

}
