package particleTracker;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JSlider;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIParticleTracker extends JFrame {

	private ParticleTracker particleTracker;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_GuyLevi = null;
	private JPanel jPanel_ParticleDetection = null;
	private JSlider jSlider_Cutoff = null;
	private JSlider jSlider_Radius = null;
	private JLabel jLabel_Radius = null;
	private JTextField jTextField_Radius = null;
	private JLabel jLabel_Cutoff = null;
	private JTextField jTextField_Cutoff = null;
	private JTextField jTextField_Percentile = null;
	private JSlider jSlider_Percentile = null;
	private JLabel jLabel_Percentile = null;
	private JLabel jLabel_Frame = null;
	private JSpinner jSpinner_Detection_NFrame = null;
	private JPanel jPanel_LinkRange = null;
	private JSlider jSlider_LinkRange = null;
	private JSlider jSlider_Displacement = null;
	private JLabel jLabel_LinkRange = null;
	private JTextField jTextField_LinkRange = null;
	private JLabel jLabel_Displacement = null;
	private JTextField jTextField_Displacement = null;
	private JPanel jPanel_GuyLevi_Process = null;
	private JButton jButton_Linking = null;
	private JProgressBar jProgressBar_ProcessMovie = null;
	private JCheckBox jCheckBox = null;
	private JTextField txtStatus;
	private JButton btnNewButton_1;
	private JButton jButton_Detection;
	private JPanel panel;
	/**
	 * This is the default constructor
	 */
	public GUIParticleTracker(ParticleTracker particleTracker) 
	{
		super();
		this.particleTracker = particleTracker;
		initialize();
	}

	/*-----------------------------------------------------------------------------------*/
	public void jButton_Process_setEnabled(boolean enabled)
	{
		jButton_Linking.setEnabled(enabled);		
	}
	
	/**
	 * @return
	 */
	public int jSpinner_Detection_Nframe_getValue()
	{
		return ((Integer)(jSpinner_Detection_NFrame.getModel().getValue())).intValue();
	}
	public void jSpinner_Detection_Nframe_setMaxValue(int maxValue)
	{
		jSpinner_Detection_NFrame.setModel(new SpinnerNumberModel(1, 1, maxValue, 1));
	}
	
	/**
	 * @return
	 */
	public int getLinkRange()
	{
		return Integer.parseInt(jTextField_LinkRange.getText());
	}
	public double getDisplacement()
	{
		return Double.parseDouble(jTextField_Displacement.getText());
	}
	/**
	 * @param value
	 */
	public void jProgressBar_Detection_ProcessMovie_setValue(int value)
	{		
		jProgressBar_ProcessMovie.setValue(value);
		jProgressBar_ProcessMovie.setString(value+"%");		
	}
	
	/**
	 * @param lenght
	 */
	public void jProgressBar_Detection_ProcessMovie_setMaxValue(int lenght)
	{
		jProgressBar_ProcessMovie.setMaximum(lenght);		
	}
	
	public boolean jCheckBox_showPixels_isSelected()
	{
		return jCheckBox.isSelected();
	}

	public void jButton_Detection_setEnabled(boolean bool)
	{
		jButton_Detection.setEnabled(bool);
		if(bool)
		{
			jButton_Detection.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Detection pressed");
					//TODO jTable_TrajectoriesTable_clean();
					particleTracker.jButton_Detection_clicked();
				}
			});
		}
		else
		{
			jButton_Linking.removeActionListener(jButton_Detection.getActionListeners()[0]);			
		}		
	}
	public void jButton_Linking_setEnabled(boolean bool)
	{
		jButton_Linking.setEnabled(bool);
		if(bool)
		{
			jButton_Linking.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Linking pressed");
					//TODO jTable_TrajectoriesTable_clean();
					particleTracker.jButton_Linking_clicked();
				}
			});
		}
		else
		{
			jButton_Linking.removeActionListener(jButton_Linking.getActionListeners()[0]);			
		}		
	}
	
	public void jTextField_Radius_setText(String text)
	{
		jTextField_Radius.setText(text);
	}
	public void jTextField_Cutoff_setText(String text)
	{
		jTextField_Cutoff.setText(text);
	}
	public void jTextField_Percentile_setText(String text)
	{
		jTextField_Percentile.setText(text);
	}
	public void jTextField_LinkRange_setText(String text)
	{
		jTextField_LinkRange.setText(text);
	}
	public void jTextField_Displacement_setText(String text)
	{
		jTextField_Displacement.setText(text);
	}
	public int jSlider_Radius_getValue()
	{
		return jSlider_Radius.getValue();
	}
	public int jSlider_Cutoff_getValue()
	{
		return jSlider_Cutoff.getValue();
	}
	public int jSlider_Displacement_getValue()
	{
		return jSlider_Displacement.getValue();
	}
	public int jSlider_Percentile_getValue()
	{
		return jSlider_Percentile.getValue();
	}
	public int jSlider_LinkRange_getValue()
	{
		return jSlider_LinkRange.getValue();
	}
	public void jTextField_setText(String text)
	{
		txtStatus.setText(text);
	}
	/*-----------------------------------------------------------------------------------*/
	
	
	
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
		this.setSize(452, 585);
		this.setContentPane(getJContentPane());
		this.setTitle("Particle Tracker");
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
			jContentPane.add(getJPanel_GuyLevi(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel_GuyLevi	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_GuyLevi() {
		if (jPanel_GuyLevi == null) {
			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			gridBagConstraints101.fill = GridBagConstraints.BOTH;
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.gridy = 2;
			gridBagConstraints101.weightx = 0.0D;
			gridBagConstraints101.weighty = 1.0D;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.ipadx = 10;
			gridBagConstraints5.ipady = 10;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.weighty = 1.0D;
			gridBagConstraints5.gridheight = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 0;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.weighty = 1.0D;
			gridBagConstraints7.gridx = 0;
			jPanel_GuyLevi = new JPanel();
			jPanel_GuyLevi.setLayout(new GridBagLayout());
			jPanel_GuyLevi.setPreferredSize(new Dimension(25, 25));
			jPanel_GuyLevi.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel_GuyLevi.add(getJPanel_ParticleDetection(), gridBagConstraints7);
			jPanel_GuyLevi.add(getJPanel_LinkRange(), gridBagConstraints5);
			jPanel_GuyLevi.add(getJPanel_GuyLevi_Process(), gridBagConstraints101);
		}
		return jPanel_GuyLevi;
	}

	/**
	 * This method initializes jPanel_ParticleDetection	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ParticleDetection() {
		if (jPanel_ParticleDetection == null) {
			TitledBorder titledBorder5 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Particle Detection", TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.BOLD, 12), new Color(51, 51, 51));
			titledBorder5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			titledBorder5.setTitleFont(new Font("SansSerif", Font.BOLD, 12));
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.gridx = 0;
			jLabel_Percentile = new JLabel();
			jLabel_Percentile.setPreferredSize(new Dimension(60, 30));
			jLabel_Percentile.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_Percentile.setText("Percentile:");
			jLabel_Percentile.setToolTipText("");
			GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
			gridBagConstraints92.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints92.fill = GridBagConstraints.NONE;
			gridBagConstraints92.gridx = 1;
			gridBagConstraints92.gridy = 2;
			gridBagConstraints92.weightx = 1.0;
			gridBagConstraints92.gridwidth = 1;
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints82.fill = GridBagConstraints.NONE;
			gridBagConstraints82.gridy = 2;
			gridBagConstraints82.weightx = 1.0;
			gridBagConstraints82.gridx = 2;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints61.fill = GridBagConstraints.NONE;
			gridBagConstraints61.gridy = 1;
			gridBagConstraints61.weightx = 1.0;
			gridBagConstraints61.gridx = 2;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridy = 1;
			gridBagConstraints51.gridwidth = 1;
			jLabel_Cutoff = new JLabel();
			jLabel_Cutoff.setPreferredSize(new Dimension(60, 30));
			jLabel_Cutoff.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_Cutoff.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel_Cutoff.setText("Cutoff:");
			jLabel_Cutoff.setToolTipText("");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints41.fill = GridBagConstraints.NONE;
			gridBagConstraints41.gridx = 2;
			gridBagConstraints41.gridy = 0;
			gridBagConstraints41.weightx = 1.0;
			gridBagConstraints41.gridwidth = 1;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints32.fill = GridBagConstraints.BOTH;
			gridBagConstraints32.gridx = 0;
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.weightx = 0.0D;
			gridBagConstraints32.gridwidth = 1;
			jLabel_Radius = new JLabel();
			jLabel_Radius.setPreferredSize(new Dimension(60, 30));
			jLabel_Radius.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_Radius.setText("Radius:");
			jLabel_Radius.setToolTipText("");
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			gridBagConstraints22.gridx = 1;
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.gridwidth = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints14.fill = GridBagConstraints.NONE;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 1;
			jPanel_ParticleDetection = new JPanel();
			GridBagLayout gbl_jPanel_ParticleDetection = new GridBagLayout();
			gbl_jPanel_ParticleDetection.rowHeights = new int[]{0, 0, 0, 51, 0};
			gbl_jPanel_ParticleDetection.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_jPanel_ParticleDetection.columnWeights = new double[]{1.0, 0.0, 0.0};
			jPanel_ParticleDetection.setLayout(gbl_jPanel_ParticleDetection);
			jPanel_ParticleDetection.setPreferredSize(new Dimension(400, 200));
			jPanel_ParticleDetection.setBorder(titledBorder5);
			jPanel_ParticleDetection.add(getJSlider_Cutoff(), gridBagConstraints14);
			jPanel_ParticleDetection.add(getJSlider_Radius(), gridBagConstraints22);
			jPanel_ParticleDetection.add(jLabel_Radius, gridBagConstraints32);
			jPanel_ParticleDetection.add(getJTextField_Radius(), gridBagConstraints41);
			jPanel_ParticleDetection.add(jLabel_Cutoff, gridBagConstraints51);
			jPanel_ParticleDetection.add(getJTextField_Cutoff(), gridBagConstraints61);
			jPanel_ParticleDetection.add(getJTextField_Percentile(), gridBagConstraints82);
			jPanel_ParticleDetection.add(getJSlider_Percentile(), gridBagConstraints92);
			jPanel_ParticleDetection.add(jLabel_Percentile, gridBagConstraints12);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 3;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 3;
			jPanel_ParticleDetection.add(getPanel(), gbc_panel);
			GridBagConstraints gbc_jButton_Detection = new GridBagConstraints();
			gbc_jButton_Detection.gridwidth = 3;
			gbc_jButton_Detection.fill = GridBagConstraints.HORIZONTAL;
			gbc_jButton_Detection.insets = new Insets(0, 0, 0, 5);
			gbc_jButton_Detection.gridx = 0;
			gbc_jButton_Detection.gridy = 4;
			jPanel_ParticleDetection.add(getJButton_Detection(), gbc_jButton_Detection);
		}
		return jPanel_ParticleDetection;
	}

	/**
	 * This method initializes jSlider_Cutoff	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Cutoff() {
		if (jSlider_Cutoff == null) {
			jSlider_Cutoff = new JSlider();
			jSlider_Cutoff.setPreferredSize(new Dimension(180, 25));
			jSlider_Cutoff.setValue(0);
			jSlider_Cutoff.setSnapToTicks(true);
			jSlider_Cutoff.setMaximum(30);
			jSlider_Cutoff.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					particleTracker.jSlider_Cutoff_stateChanged();
				}
			});
		}
		return jSlider_Cutoff;
	}

	/**
	 * This method initializes jSlider_Radius	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Radius() {
		if (jSlider_Radius == null) {
			jSlider_Radius = new JSlider();
			jSlider_Radius.setPreferredSize(new Dimension(180, 25));
			jSlider_Radius.setMinimum(1);
			jSlider_Radius.setValue(2);
			jSlider_Radius.setSnapToTicks(true);
			jSlider_Radius.setMaximum(20);
			jSlider_Radius.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					particleTracker.jSlider_Radius_stateChanged();
				}
			});
		}
		return jSlider_Radius;
	}

	/**
	 * This method initializes jTextField_Radius	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Radius() {
		if (jTextField_Radius == null) {
			jTextField_Radius = new JTextField();
			jTextField_Radius.setPreferredSize(new Dimension(80, 30));
			jTextField_Radius.setText("2");
			jTextField_Radius.setToolTipText("Estimate radius of the particle in pixels.");
		}
		return jTextField_Radius;
	}

	/**
	 * This method initializes jTextField_Cutoff	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Cutoff() {
		if (jTextField_Cutoff == null) {
			jTextField_Cutoff = new JTextField();
			jTextField_Cutoff.setPreferredSize(new Dimension(80, 30));
			jTextField_Cutoff.setText("0");
		}
		return jTextField_Cutoff;
	}

	/**
	 * This method initializes jTextField_Percentile	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Percentile() {
		if (jTextField_Percentile == null) {
			jTextField_Percentile = new JTextField();
			jTextField_Percentile.setPreferredSize(new Dimension(80, 30));
			jTextField_Percentile.setText("0.01%");
			jTextField_Percentile.setToolTipText("The brightest percentage of particles to considerer.");
		}
		return jTextField_Percentile;
	}

	/**
	 * This method initializes jSlider_Percentile	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Percentile() {
		if (jSlider_Percentile == null) {
			jSlider_Percentile = new JSlider();
			jSlider_Percentile.setPreferredSize(new Dimension(180, 25));
			jSlider_Percentile.setValue(1);
			jSlider_Percentile.setSnapToTicks(true);
			jSlider_Percentile.setMaximum(100);
			jSlider_Percentile.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					particleTracker.jSlider_Percentile_stateChanged();
				}
			});
		}
		return jSlider_Percentile;
	}

	/**
	 * This method initializes jSpinner_Detection_NFrame	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_Detection_NFrame() {
		if (jSpinner_Detection_NFrame == null) {
			jSpinner_Detection_NFrame = new JSpinner();
			jSpinner_Detection_NFrame.setPreferredSize(new Dimension(80, 30));
			jSpinner_Detection_NFrame.setModel(new SpinnerNumberModel(1, 1, 100, 1));
			jSpinner_Detection_NFrame
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							particleTracker.jSpinner_Detection_Nframe_stateChanged();
						}
					});
		}
		return jSpinner_Detection_NFrame;
	}

	/**
	 * This method initializes jPanel_LinkRange	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_LinkRange() {
		if (jPanel_LinkRange == null) {
			TitledBorder titledBorder6 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Particle Linking", TitledBorder.LEFT, TitledBorder.TOP, new Font("Comic Sans MS", Font.BOLD, 12), new Color(51, 51, 51));
			titledBorder6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			titledBorder6.setTitleFont(new Font("SansSerif", Font.BOLD, 12));
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints91.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints91.gridy = 1;
			gridBagConstraints91.weightx = 1.0D;
			gridBagConstraints91.gridx = 2;
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints81.fill = GridBagConstraints.BOTH;
			gridBagConstraints81.gridy = 1;
			gridBagConstraints81.gridx = 0;
			jLabel_Displacement = new JLabel();
			jLabel_Displacement.setPreferredSize(new Dimension(80, 30));
			jLabel_Displacement.setText("Displacement:");
			jLabel_Displacement.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints31.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.gridx = 2;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.gridx = 0;
			jLabel_LinkRange = new JLabel();
			jLabel_LinkRange.setPreferredSize(new Dimension(80, 30));
			jLabel_LinkRange.setText("Link Range:");
			jLabel_LinkRange.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridx = 1;
			jPanel_LinkRange = new JPanel();
			jPanel_LinkRange.setLayout(new GridBagLayout());
			jPanel_LinkRange.setPreferredSize(new Dimension(400, 150));
			jPanel_LinkRange.setBorder(titledBorder6);
			jPanel_LinkRange.add(getJSlider_LinkRange(), gridBagConstraints4);
			jPanel_LinkRange.add(getJSlider_Displacement(), gridBagConstraints11);
			jPanel_LinkRange.add(jLabel_LinkRange, gridBagConstraints21);
			jPanel_LinkRange.add(getJTextField_LinkRange(), gridBagConstraints31);
			jPanel_LinkRange.add(jLabel_Displacement, gridBagConstraints81);
			jPanel_LinkRange.add(getJTextField_Displacement(), gridBagConstraints91);
			GridBagConstraints gbc_jButton_Linking = new GridBagConstraints();
			gbc_jButton_Linking.fill = GridBagConstraints.HORIZONTAL;
			gbc_jButton_Linking.gridwidth = 3;
			gbc_jButton_Linking.insets = new Insets(0, 0, 0, 5);
			gbc_jButton_Linking.gridx = 0;
			gbc_jButton_Linking.gridy = 2;
			jPanel_LinkRange.add(getJButton_Linking(), gbc_jButton_Linking);
		}
		return jPanel_LinkRange;
	}

	/**
	 * This method initializes jSlider_LinkRange	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_LinkRange() {
		if (jSlider_LinkRange == null) {
			jSlider_LinkRange = new JSlider();
			jSlider_LinkRange.setMaximum(15);
			jSlider_LinkRange.setPreferredSize(new Dimension(100, 30));
			jSlider_LinkRange.setValue(2);
			jSlider_LinkRange.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					particleTracker.jSlider_LinkRange_stateChanged();
				}
			});
		}
		return jSlider_LinkRange;
	}

	/**
	 * This method initializes jSlider_Displacement	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Displacement() {
		if (jSlider_Displacement == null) {
			jSlider_Displacement = new JSlider();
			jSlider_Displacement.setPreferredSize(new Dimension(100, 30));
			jSlider_Displacement.setValue(4);
			jSlider_Displacement.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					particleTracker.jSlider_Displacement_stateChanged();
				}
			});
		}
		return jSlider_Displacement;
	}

	/**
	 * This method initializes jTextField_LinkRange	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_LinkRange() {
		if (jTextField_LinkRange == null) {
			jTextField_LinkRange = new JTextField();
			jTextField_LinkRange.setPreferredSize(new Dimension(60, 25));
			jTextField_LinkRange.setText("2");
		}
		return jTextField_LinkRange;
	}

	/**
	 * This method initializes jTextField_Displacement	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Displacement() {
		if (jTextField_Displacement == null) {
			jTextField_Displacement = new JTextField();
			jTextField_Displacement.setPreferredSize(new Dimension(60, 25));
			jTextField_Displacement.setText("4");
		}
		return jTextField_Displacement;
	}

	/**
	 * This method initializes jPanel_GuyLevi_Process	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_GuyLevi_Process() {
		if (jPanel_GuyLevi_Process == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 0;
			jPanel_GuyLevi_Process = new JPanel();
			GridBagLayout gbl_jPanel_GuyLevi_Process = new GridBagLayout();
			gbl_jPanel_GuyLevi_Process.columnWeights = new double[]{1.0};
			jPanel_GuyLevi_Process.setLayout(gbl_jPanel_GuyLevi_Process);
			jPanel_GuyLevi_Process.setPreferredSize(new Dimension(25, 50));
			GridBagConstraints gbc_txtStatus = new GridBagConstraints();
			gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
			gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtStatus.gridx = 0;
			gbc_txtStatus.gridy = 0;
			jPanel_GuyLevi_Process.add(getTxtStatus(), gbc_txtStatus);
			jPanel_GuyLevi_Process.add(getJProgressBar_ProcessMovie(), gridBagConstraints9);
		}
		return jPanel_GuyLevi_Process;
	}

	/**
	 * This method initializes jButton_Process	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Linking() {
		if (jButton_Linking == null) {
			jButton_Linking = new JButton();
			jButton_Linking.setPreferredSize(new Dimension(100, 25));
			jButton_Linking.setText("Particle Linking");
			jButton_Linking.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					particleTracker.jButton_Linking_clicked();
				}
			});
		}
		return jButton_Linking;
	}

	/**
	 * This method initializes jProgressBar_ProcessMovie	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar_ProcessMovie() {
		if (jProgressBar_ProcessMovie == null) {
			jProgressBar_ProcessMovie = new JProgressBar();
			jProgressBar_ProcessMovie.setPreferredSize(new Dimension(100, 30));
			jProgressBar_ProcessMovie.setStringPainted(true);
		}
		return jProgressBar_ProcessMovie;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("Show pixels");
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					particleTracker.jCheckBox_ShowPixels_stateChanged();
				}
			});
		}
		return jCheckBox;
	}

	private JTextField getTxtStatus() {
		if (txtStatus == null) {
			txtStatus = new JTextField();
			txtStatus.setEditable(false);
			txtStatus.setText("Status:");
			txtStatus.setColumns(10);
		}
		return txtStatus;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Preview restoration");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					particleTracker.jButton_Internal_clicked();
				}
			});
		}
		return btnNewButton_1;
	}
	private JButton getJButton_Detection() {
		if (jButton_Detection == null) {
			jButton_Detection = new JButton("Particle Detection");
			jButton_Detection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					particleTracker.jButton_Detection_clicked();
				}
			});
		}
		return jButton_Detection;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{112, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			jLabel_Frame = new JLabel();
			GridBagConstraints gbc_jLabel_Frame = new GridBagConstraints();
			gbc_jLabel_Frame.fill = GridBagConstraints.HORIZONTAL;
			gbc_jLabel_Frame.insets = new Insets(0, 0, 0, 5);
			gbc_jLabel_Frame.gridx = 0;
			gbc_jLabel_Frame.gridy = 0;
			panel.add(jLabel_Frame, gbc_jLabel_Frame);
			jLabel_Frame.setForeground(Color.black);
			jLabel_Frame.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_Frame.setText("Preview frame:");
			jLabel_Frame.setPreferredSize(new Dimension(80, 30));
			GridBagConstraints gbc_jSpinner_Detection_NFrame = new GridBagConstraints();
			gbc_jSpinner_Detection_NFrame.insets = new Insets(0, 0, 0, 5);
			gbc_jSpinner_Detection_NFrame.gridx = 1;
			gbc_jSpinner_Detection_NFrame.gridy = 0;
			panel.add(getJSpinner_Detection_NFrame(), gbc_jSpinner_Detection_NFrame);
			GridBagConstraints gbc_jCheckBox = new GridBagConstraints();
			gbc_jCheckBox.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox.gridx = 2;
			gbc_jCheckBox.gridy = 0;
			panel.add(getJCheckBox(), gbc_jCheckBox);
			GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
			gbc_btnNewButton_1.gridx = 3;
			gbc_btnNewButton_1.gridy = 0;
			panel.add(getBtnNewButton_1(), gbc_btnNewButton_1);
		}
		return panel;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
