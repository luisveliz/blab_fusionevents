package overTime;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import main.Thinker;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;


import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Font;

public class GUI extends JFrame {

	OverTime vsTime;	
	private ChartPanel cp_vsTime = null;
	
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_Intensity = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JCheckBox jCheckBox_Intensity = null;
	private JCheckBox jCheckBox_Velocity = null;
	private JCheckBox jCheckBox_Angle = null;
	private JCheckBox jCheckBox_AngleChanges = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel = null;
	private JSpinner jSpinner_A = null;
	private JLabel jLabel1 = null;
	private JSpinner jSpinner_w = null;
	private JLabel jLabel2 = null;
	private JSpinner jSpinner_T = null;
	private JLabel jLabel3 = null;
	private JSpinner jSpinner_b = null;
	private JLabel jLabel4 = null;
	private JSpinner jSpinner_C = null;
	private JLabel jLabel5 = null;

	/**
	 * This is the default constructor
	 */
	public GUI(OverTime vsTime) {
		super();
		this.vsTime = vsTime;
		initialize();
	}
	
	public JFreeChartOverTime getJFreeChart_VSTime()
	{
		return (JFreeChartOverTime)this.cp_vsTime.getChart();
	}
	public boolean jCheckBox_Intensity_isSelected()
	{
		return jCheckBox_Intensity.isSelected();
	}
	public boolean jCheckBox_Velocity_isSelected()
	{
		return jCheckBox_Velocity.isSelected();
	}
	public boolean jCheckBox_Angle_isSelected()
	{
		return jCheckBox_Angle.isSelected();
	}
	public boolean jCheckBox_AngleChanges_isSelected()
	{
		return jCheckBox_AngleChanges.isSelected();
	}
	public double[] jSpinners_getValues()
	{
		return new double[]{(Double) jSpinner_A.getValue(),
							(Double) jSpinner_w.getValue(),
							(Double) jSpinner_T.getValue(),
							(Double) jSpinner_b.getValue(),
							(Double) jSpinner_C.getValue()};
	}
	public void jSpinner_BC_setValues(double A, double b, double c)
	{
		jSpinner_A.setValue(A);
		jSpinner_b.setValue(b);
		jSpinner_C.setValue(c);
	}

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
		this.setSize(500, 450);
		this.setPreferredSize(new Dimension(500, 450));
		this.setContentPane(getJContentPane());
		this.setTitle("VS Time");
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
	 * This method initializes jPanel_Intensity	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Intensity() {
		if (jPanel_Intensity == null) {
			GridBagConstraints gBC_cp_Intensity = new GridBagConstraints();
			gBC_cp_Intensity.fill = GridBagConstraints.BOTH;
			gBC_cp_Intensity.weighty = 1.0D;
			gBC_cp_Intensity.weightx = 1.0D;
			jPanel_Intensity = new JPanel();
			jPanel_Intensity.setLayout(new GridBagLayout());
			jPanel_Intensity.setPreferredSize(new Dimension(25, 25));
			jPanel_Intensity.add(getCp_Intensity(), gBC_cp_Intensity);
			jPanel_Intensity.setVisible(true);
		}
		return jPanel_Intensity;
	}

	/**
	 * This method initializes cp_Intensity	
	 * 	
	 * @return org.jfree.chart.ChartPanel	
	 */
	private ChartPanel getCp_Intensity() {
		if (cp_vsTime == null) {
			cp_vsTime = new ChartPanel(new JFreeChartOverTime(new XYPlot()));
		}
		return cp_vsTime;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.weightx = 1.0D;
			jPanel = new JPanel();
			GridBagLayout gbl_jPanel = new GridBagLayout();
			gbl_jPanel.rowHeights = new int[]{19, 0};
			jPanel.setLayout(gbl_jPanel);
			jPanel.add(getJPanel1(), gridBagConstraints1);
			jPanel.add(getJPanel_Intensity(), gridBagConstraints);
//			jPanel.add(getJPanel2(), gridBagConstraints11);
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
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 3;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setPreferredSize(new Dimension(25, 50));
			jPanel1.add(getJCheckBox_Intensity(), gridBagConstraints2);
			jPanel1.add(getJCheckBox_Velocity(), gridBagConstraints3);
			jPanel1.add(getJCheckBox_Angle(), gridBagConstraints4);
			jPanel1.add(getJCheckBox_AngleChanges(), gridBagConstraints5);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCheckBox_Intensity	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Intensity() {
		if (jCheckBox_Intensity == null) {
			jCheckBox_Intensity = new JCheckBox();
			jCheckBox_Intensity.setText("Intensity");
			jCheckBox_Intensity.setSelected(true);
			jCheckBox_Intensity.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					vsTime.jCheckBox_VSTime_stateChanged();
				}
			});
		}
		return jCheckBox_Intensity;
	}

	/**
	 * This method initializes jCheckBox_Velocity	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Velocity() {
		if (jCheckBox_Velocity == null) {
			jCheckBox_Velocity = new JCheckBox();
			jCheckBox_Velocity.setText("Velocity");
			jCheckBox_Velocity.setSelected(false);
			jCheckBox_Velocity.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					vsTime.jCheckBox_VSTime_stateChanged();
				}
			});
		}
		return jCheckBox_Velocity;
	}

	/**
	 * This method initializes jCheckBox_Angle	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Angle() {
		if (jCheckBox_Angle == null) {
			jCheckBox_Angle = new JCheckBox();
			jCheckBox_Angle.setText("Angle");
			jCheckBox_Angle.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					vsTime.jCheckBox_VSTime_stateChanged();
				}
			});
		}
		return jCheckBox_Angle;
	}

	/**
	 * This method initializes jCheckBox_AngleChanges	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_AngleChanges() {
		if (jCheckBox_AngleChanges == null) {
			jCheckBox_AngleChanges = new JCheckBox();
			jCheckBox_AngleChanges.setText("Angle Changes");
			jCheckBox_AngleChanges.setSelected(false);
			jCheckBox_AngleChanges.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					vsTime.jCheckBox_VSTime_stateChanged();
				}
			});
		}
		return jCheckBox_AngleChanges;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 1;
			gridBagConstraints17.gridwidth = 9;
			gridBagConstraints17.gridy = 0;
			jLabel5 = new JLabel();
			jLabel5.setText("y = A sin ( w t  +  p )  +  b t  +  c");
			jLabel5.setFont(new Font("SansSerif", Font.PLAIN, 14));
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 10;
			gridBagConstraints16.gridy = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 9;
			gridBagConstraints15.gridy = 1;
			jLabel4 = new JLabel();
			jLabel4.setText("*t+");
			jLabel4.setFont(new Font("SansSerif", Font.PLAIN, 16));
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 8;
			gridBagConstraints14.gridy = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 7;
			gridBagConstraints13.gridy = 1;
			jLabel3 = new JLabel();
			jLabel3.setText(")+");
			jLabel3.setFont(new Font("SansSerif", Font.PLAIN, 16));
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 6;
			gridBagConstraints12.gridy = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 5;
			gridBagConstraints10.gridy = 1;
			jLabel2 = new JLabel();
			jLabel2.setText("*t+");
			jLabel2.setFont(new Font("SansSerif", Font.PLAIN, 16));
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 4;
			gridBagConstraints9.gridy = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 3;
			gridBagConstraints8.gridy = 1;
			jLabel1 = new JLabel();
			jLabel1.setText("*sin(");
			jLabel1.setFont(new Font("SansSerif", Font.PLAIN, 16));
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.gridy = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 1;
			jLabel = new JLabel();
			jLabel.setText("y=");
			jLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setPreferredSize(new Dimension(25, 50));
			jPanel2.add(jLabel, gridBagConstraints6);
			jPanel2.add(getJSpinner_A(), gridBagConstraints7);
			jPanel2.add(jLabel1, gridBagConstraints8);
			jPanel2.add(getJSpinner_w(), gridBagConstraints9);
			jPanel2.add(jLabel2, gridBagConstraints10);
			jPanel2.add(getJSpinner_T(), gridBagConstraints12);
			jPanel2.add(jLabel3, gridBagConstraints13);
			jPanel2.add(getJSpinner_b(), gridBagConstraints14);
			jPanel2.add(jLabel4, gridBagConstraints15);
			jPanel2.add(getJSpinner_C(), gridBagConstraints16);
			jPanel2.add(jLabel5, gridBagConstraints17);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jSpinner_A	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_A() {
		if (jSpinner_A == null) {
			jSpinner_A = new JSpinner(new SpinnerNumberModel(0.1,0,360,0.1));
			jSpinner_A.setPreferredSize(new Dimension(60, 28));
			jSpinner_A.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					vsTime.jSpinner_VSTime_stateChanged();
				}
			});
		}
		return jSpinner_A;
	}

	/**
	 * This method initializes jSpinner_w	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_w() {
		if (jSpinner_w == null) {
			jSpinner_w = new JSpinner(new SpinnerNumberModel(0.1,0,100,0.1));
			jSpinner_w.setPreferredSize(new Dimension(60, 28));
			jSpinner_w.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					vsTime.jSpinner_VSTime_stateChanged();
				}
			});
		}
		return jSpinner_w;
	}

	/**
	 * This method initializes jSpinner_T	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_T() {
		if (jSpinner_T == null) {
			jSpinner_T = new JSpinner(new SpinnerNumberModel(0.1,0,100,0.1));
			jSpinner_T.setPreferredSize(new Dimension(60, 28));
			jSpinner_T.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					vsTime.jSpinner_VSTime_stateChanged();
				}
			});
		}
		return jSpinner_T;
	}

	/**
	 * This method initializes jSpinner_b	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_b() {
		if (jSpinner_b == null) {
			jSpinner_b = new JSpinner(new SpinnerNumberModel(0.1,-1000,1000,0.1));
			jSpinner_b.setPreferredSize(new Dimension(60, 28));
			jSpinner_b.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					vsTime.jSpinner_VSTime_stateChanged();
				}
			});
		}
		return jSpinner_b;
	}

	/**
	 * This method initializes jSpinner_C	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_C() {
		if (jSpinner_C == null) {
			jSpinner_C = new JSpinner(new SpinnerNumberModel(0.1,0,360,0.1));
			jSpinner_C.setPreferredSize(new Dimension(60, 28));
			jSpinner_C.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					vsTime.jSpinner_VSTime_stateChanged();
				}
			});
		}
		return jSpinner_C;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
