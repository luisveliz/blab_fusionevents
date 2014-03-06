package simulator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

public class GUI extends JFrame 
{
	Simulator simulator;
	
	private JPanel contentPane;
	private JLabel label;
	private JTextField textField_D1;
	private JLabel label_1;
	private JTextField textField_D2;
	private JLabel label_2;
	private JTextField textField_P12;
	private JLabel label_3;
	private JTextField textField_P21;
	private JPanel panel_2;
	private JLabel label_4;
	private JSpinner spinner_Ntrajs;
	private JLabel lblNPoints;
	private JSpinner spinner_Nsteps;
	private JLabel label_6;
	private JTextField textField_TimeStep;
	private JPanel panel_3;
	private JProgressBar progressBar;
	private JButton button;
	private JRadioButton rdbtnOneState;
	private JRadioButton rdbtnTwoState;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel;
	private JPanel panel_4;
	private JPanel panel_1;
	private JCheckBox chckbxNormal;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JCheckBox chckbxConstrained;
	private JLabel lblD;
	private JTextField textField_DC;
	private JLabel lblL;
	private JTextField textField_L;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;
	private JPanel panel_11;
	private JCheckBox chckbxDirected;
	private JLabel lblD_1;
	private JLabel lblV;
	private JTextField textField_DD;
	private JTextField textField_V;
	
	public boolean jCheckBox_Normal_isSelected()
	{
		return chckbxNormal.isSelected();
	}
	public boolean jCheckBox_Constrained_isSelected()
	{
		return chckbxConstrained.isSelected();
	}
	public boolean jCheckBox_Directed_isSelected()
	{
		return chckbxDirected.isSelected();
	}
	public boolean radioButton_TwoState_isSelected()
	{
		return rdbtnTwoState.isSelected();
	}
	public void jTextfield_setEnabled(boolean bool)
	{
		textField_D2.setEnabled(bool);
		textField_P12.setEnabled(bool);
		textField_P21.setEnabled(bool);
	}
	public double get_D1()
	{
		return Double.parseDouble(textField_D1.getText());
	}
	public double get_D2()
	{
		return Double.parseDouble(textField_D2.getText());
	}
	public double get_P12()
	{
		return Double.parseDouble(textField_P12.getText());
	}
	public double get_P21()
	{
		return Double.parseDouble(textField_P21.getText());
	}
	public double get_DC()
	{
		return Double.parseDouble(textField_DC.getText());
	}
	public double get_L()
	{
		return Double.parseDouble(textField_L.getText());
	}
	public double get_DD()
	{
		return Double.parseDouble(textField_DD.getText());
	}
	public double get_V()
	{
		return Double.parseDouble(textField_V.getText());
	}
	
	
	public int get_nTrajs()
	{
		return ((Integer)(spinner_Ntrajs.getValue())).intValue();
	}
	public int get_nPoints()
	{
		return ((Integer)(spinner_Nsteps.getValue())).intValue();
	}
	public double get_TimeStep()
	{
		return Double.parseDouble(textField_TimeStep.getText());
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public GUI(Simulator simulator) 
	{
		setVisible(true);
		this.simulator = simulator;
		setTitle("Simulator");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 440, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{696, 0};
		gbl_contentPane.rowHeights = new int[]{77, 80, 78, 38, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		contentPane.add(getPanel_1(), gbc_panel_1);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		contentPane.add(getPanel_5(), gbc_panel_5);
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 2;
		contentPane.add(getPanel_8(), gbc_panel_8);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		contentPane.add(getPanel_2(), gbc_panel_2);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 4;
		contentPane.add(getPanel_3(), gbc_panel_3);
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("D1:");
			label.setHorizontalTextPosition(SwingConstants.TRAILING);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label;
	}
	private JTextField getTextField_D1() {
		if (textField_D1 == null) {
			textField_D1 = new JTextField();
			textField_D1.setText("0.1");
			textField_D1.setPreferredSize(new Dimension(40, 25));
			textField_D1.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return textField_D1;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("D2:");
			label_1.setHorizontalTextPosition(SwingConstants.TRAILING);
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label_1;
	}
	private JTextField getTextField_D2() {
		if (textField_D2 == null) {
			textField_D2 = new JTextField();
			textField_D2.setEnabled(false);
			textField_D2.setText("0.01");
			textField_D2.setPreferredSize(new Dimension(50, 25));
			textField_D2.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return textField_D2;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("P12:");
			label_2.setHorizontalTextPosition(SwingConstants.TRAILING);
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label_2;
	}
	private JTextField getTextField_P12() {
		if (textField_P12 == null) {
			textField_P12 = new JTextField();
			textField_P12.setEnabled(false);
			textField_P12.setText("0.1");
			textField_P12.setPreferredSize(new Dimension(50, 25));
			textField_P12.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return textField_P12;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("P21:");
			label_3.setHorizontalTextPosition(SwingConstants.TRAILING);
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label_3;
	}
	private JTextField getTextField_P21() {
		if (textField_P21 == null) {
			textField_P21 = new JTextField();
			textField_P21.setEnabled(false);
			textField_P21.setText("0.05");
			textField_P21.setPreferredSize(new Dimension(50, 25));
			textField_P21.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return textField_P21;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setPreferredSize(new Dimension(25, 25));
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel_2.rowHeights = new int[]{0, 0};
			gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_2.setLayout(gbl_panel_2);
			GridBagConstraints gbc_label_4 = new GridBagConstraints();
			gbc_label_4.anchor = GridBagConstraints.EAST;
			gbc_label_4.insets = new Insets(0, 0, 0, 5);
			gbc_label_4.gridx = 0;
			gbc_label_4.gridy = 0;
			panel_2.add(getLabel_4(), gbc_label_4);
			GridBagConstraints gbc_spinner_Ntrajs = new GridBagConstraints();
			gbc_spinner_Ntrajs.insets = new Insets(0, 0, 0, 5);
			gbc_spinner_Ntrajs.gridx = 1;
			gbc_spinner_Ntrajs.gridy = 0;
			panel_2.add(getSpinner_Ntrajs(), gbc_spinner_Ntrajs);
			GridBagConstraints gbc_lblNPoints = new GridBagConstraints();
			gbc_lblNPoints.anchor = GridBagConstraints.EAST;
			gbc_lblNPoints.insets = new Insets(0, 0, 0, 5);
			gbc_lblNPoints.gridx = 2;
			gbc_lblNPoints.gridy = 0;
			panel_2.add(getLblNPoints(), gbc_lblNPoints);
			GridBagConstraints gbc_spinner_Nsteps = new GridBagConstraints();
			gbc_spinner_Nsteps.insets = new Insets(0, 0, 0, 5);
			gbc_spinner_Nsteps.gridx = 3;
			gbc_spinner_Nsteps.gridy = 0;
			panel_2.add(getSpinner_Nsteps(), gbc_spinner_Nsteps);
			GridBagConstraints gbc_label_6 = new GridBagConstraints();
			gbc_label_6.anchor = GridBagConstraints.EAST;
			gbc_label_6.insets = new Insets(0, 0, 0, 5);
			gbc_label_6.gridx = 4;
			gbc_label_6.gridy = 0;
			panel_2.add(getLabel_6(), gbc_label_6);
			GridBagConstraints gbc_textField_TimeStep = new GridBagConstraints();
			gbc_textField_TimeStep.weightx = 0.0;
			gbc_textField_TimeStep.fill = GridBagConstraints.VERTICAL;
			gbc_textField_TimeStep.gridx = 5;
			gbc_textField_TimeStep.gridy = 0;
			panel_2.add(getTextField_TimeStep(), gbc_textField_TimeStep);
		}
		return panel_2;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel();
			label_4.setText("N Trajs:");
			label_4.setHorizontalTextPosition(SwingConstants.TRAILING);
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label_4;
	}
	private JSpinner getSpinner_Ntrajs() {
		if (spinner_Ntrajs == null) {
			spinner_Ntrajs = new JSpinner(new SpinnerNumberModel(10, 1, 100000, 1));
			spinner_Ntrajs.setPreferredSize(new Dimension(60, 25));
		}
		return spinner_Ntrajs;
	}
	private JLabel getLblNPoints() {
		if (lblNPoints == null) {
			lblNPoints = new JLabel();
			lblNPoints.setText("N Points:");
			lblNPoints.setHorizontalTextPosition(SwingConstants.TRAILING);
			lblNPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblNPoints;
	}
	private JSpinner getSpinner_Nsteps() {
		if (spinner_Nsteps == null) {
			spinner_Nsteps = new JSpinner(new SpinnerNumberModel(100, 1, 100000, 1));
			spinner_Nsteps.setPreferredSize(new Dimension(60, 25));
		}
		return spinner_Nsteps;
	}
	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel();
			label_6.setText("Time Step:");
			label_6.setHorizontalTextPosition(SwingConstants.TRAILING);
			label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label_6;
	}
	private JTextField getTextField_TimeStep() {
		if (textField_TimeStep == null) {
			textField_TimeStep = new JTextField();
			textField_TimeStep.setText("1");
			textField_TimeStep.setPreferredSize(new Dimension(50, 25));
			textField_TimeStep.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return textField_TimeStep;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setPreferredSize(new Dimension(25, 25));
			GridBagLayout gbl_panel_3 = new GridBagLayout();
			gbl_panel_3.columnWidths = new int[]{0, 0, 0};
			gbl_panel_3.rowHeights = new int[]{0, 0};
			gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_3.setLayout(gbl_panel_3);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.insets = new Insets(0, 0, 0, 5);
			gbc_progressBar.gridx = 0;
			gbc_progressBar.gridy = 0;
			panel_3.add(getProgressBar(), gbc_progressBar);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.gridx = 1;
			gbc_button.gridy = 0;
			panel_3.add(getButton(), gbc_button);
		}
		return panel_3;
	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setPreferredSize(new Dimension(300, 20));
		}
		return progressBar;
	}
	private JButton getButton() {
		if (button == null) {
			button = new JButton();
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0){
					simulator.jButton_Simulate_clicked();
				}
			});
			button.setText("Simulate!");
		}
		return button;
	}
	private JRadioButton getRdbtnOneState() {
		if (rdbtnOneState == null) {
			rdbtnOneState = new JRadioButton("One state");
			rdbtnOneState.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					simulator.radioButton_stateChanged();
				}
			});
			rdbtnOneState.setSelected(true);
			buttonGroup.add(rdbtnOneState);
		}
		return rdbtnOneState;
	}
	private JRadioButton getRdbtnTwoState() {
		if (rdbtnTwoState == null) {
			rdbtnTwoState = new JRadioButton("Two state");
			rdbtnTwoState.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					simulator.radioButton_stateChanged();
				}
			});
			buttonGroup.add(rdbtnTwoState);
		}
		return rdbtnTwoState;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{50, 50, 50, 50, 45, 50, 50, 50, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.EAST;
			gbc_label.insets = new Insets(0, 0, 0, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 0;
			panel.add(getLabel(), gbc_label);
			GridBagConstraints gbc_textField_D1 = new GridBagConstraints();
			gbc_textField_D1.fill = GridBagConstraints.BOTH;
			gbc_textField_D1.insets = new Insets(0, 0, 0, 5);
			gbc_textField_D1.gridx = 1;
			gbc_textField_D1.gridy = 0;
			panel.add(getTextField_D1(), gbc_textField_D1);
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.anchor = GridBagConstraints.EAST;
			gbc_label_1.insets = new Insets(0, 0, 0, 5);
			gbc_label_1.gridx = 2;
			gbc_label_1.gridy = 0;
			panel.add(getLabel_1(), gbc_label_1);
			GridBagConstraints gbc_textField_D2 = new GridBagConstraints();
			gbc_textField_D2.fill = GridBagConstraints.BOTH;
			gbc_textField_D2.insets = new Insets(0, 0, 0, 5);
			gbc_textField_D2.gridx = 3;
			gbc_textField_D2.gridy = 0;
			panel.add(getTextField_D2(), gbc_textField_D2);
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.anchor = GridBagConstraints.EAST;
			gbc_label_2.insets = new Insets(0, 0, 0, 5);
			gbc_label_2.gridx = 4;
			gbc_label_2.gridy = 0;
			panel.add(getLabel_2(), gbc_label_2);
			GridBagConstraints gbc_textField_P12 = new GridBagConstraints();
			gbc_textField_P12.fill = GridBagConstraints.BOTH;
			gbc_textField_P12.insets = new Insets(0, 0, 0, 5);
			gbc_textField_P12.gridx = 5;
			gbc_textField_P12.gridy = 0;
			panel.add(getTextField_P12(), gbc_textField_P12);
			GridBagConstraints gbc_label_3 = new GridBagConstraints();
			gbc_label_3.anchor = GridBagConstraints.EAST;
			gbc_label_3.insets = new Insets(0, 0, 0, 5);
			gbc_label_3.gridx = 6;
			gbc_label_3.gridy = 0;
			panel.add(getLabel_3(), gbc_label_3);
			GridBagConstraints gbc_textField_P21 = new GridBagConstraints();
			gbc_textField_P21.fill = GridBagConstraints.BOTH;
			gbc_textField_P21.gridx = 7;
			gbc_textField_P21.gridy = 0;
			panel.add(getTextField_P21(), gbc_textField_P21);
		}
		return panel;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			GridBagLayout gbl_panel_4 = new GridBagLayout();
			gbl_panel_4.columnWidths = new int[]{0, 73, 0, 0};
			gbl_panel_4.rowHeights = new int[]{23, 0};
			gbl_panel_4.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_4.setLayout(gbl_panel_4);
			GridBagConstraints gbc_chckbxNormal = new GridBagConstraints();
			gbc_chckbxNormal.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxNormal.gridx = 0;
			gbc_chckbxNormal.gridy = 0;
			panel_4.add(getChckbxNormal(), gbc_chckbxNormal);
		}
		return panel_4;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{405, 0};
			gbl_panel_1.rowHeights = new int[]{23, 37, 38, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_panel_4 = new GridBagConstraints();
			gbc_panel_4.anchor = GridBagConstraints.WEST;
			gbc_panel_4.fill = GridBagConstraints.VERTICAL;
			gbc_panel_4.insets = new Insets(0, 0, 5, 0);
			gbc_panel_4.gridx = 0;
			gbc_panel_4.gridy = 0;
			panel_1.add(getPanel_4(), gbc_panel_4);
			GridBagConstraints gbc_panel_11 = new GridBagConstraints();
			gbc_panel_11.anchor = GridBagConstraints.WEST;
			gbc_panel_11.insets = new Insets(0, 0, 5, 0);
			gbc_panel_11.fill = GridBagConstraints.VERTICAL;
			gbc_panel_11.gridx = 0;
			gbc_panel_11.gridy = 1;
			panel_1.add(getPanel_11(), gbc_panel_11);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.fill = GridBagConstraints.VERTICAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			panel_1.add(getPanel(), gbc_panel);
		}
		return panel_1;
	}
	private JCheckBox getChckbxNormal() {
		if (chckbxNormal == null) {
			chckbxNormal = new JCheckBox("Normal");
		}
		return chckbxNormal;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			GridBagLayout gbl_panel_5 = new GridBagLayout();
			gbl_panel_5.columnWidths = new int[]{235, 0};
			gbl_panel_5.rowHeights = new int[]{23, 31, 0};
			gbl_panel_5.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel_5.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_5.setLayout(gbl_panel_5);
			GridBagConstraints gbc_panel_6 = new GridBagConstraints();
			gbc_panel_6.fill = GridBagConstraints.BOTH;
			gbc_panel_6.insets = new Insets(0, 0, 5, 0);
			gbc_panel_6.gridx = 0;
			gbc_panel_6.gridy = 0;
			panel_5.add(getPanel_6(), gbc_panel_6);
			GridBagConstraints gbc_panel_7 = new GridBagConstraints();
			gbc_panel_7.fill = GridBagConstraints.BOTH;
			gbc_panel_7.gridx = 0;
			gbc_panel_7.gridy = 1;
			panel_5.add(getPanel_7(), gbc_panel_7);
		}
		return panel_5;
	}
	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			GridBagLayout gbl_panel_6 = new GridBagLayout();
			gbl_panel_6.columnWidths = new int[]{0, 0};
			gbl_panel_6.rowHeights = new int[]{0, 0};
			gbl_panel_6.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_6.setLayout(gbl_panel_6);
			GridBagConstraints gbc_chckbxConstrained = new GridBagConstraints();
			gbc_chckbxConstrained.gridx = 0;
			gbc_chckbxConstrained.gridy = 0;
			panel_6.add(getChckbxConstrained(), gbc_chckbxConstrained);
		}
		return panel_6;
	}
	private JPanel getPanel_7() {
		if (panel_7 == null) {
			panel_7 = new JPanel();
			GridBagLayout gbl_panel_7 = new GridBagLayout();
			gbl_panel_7.columnWidths = new int[]{52, 55, 42, 63, 0};
			gbl_panel_7.rowHeights = new int[]{0, 0};
			gbl_panel_7.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_7.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_7.setLayout(gbl_panel_7);
			GridBagConstraints gbc_lblD = new GridBagConstraints();
			gbc_lblD.insets = new Insets(0, 0, 0, 5);
			gbc_lblD.anchor = GridBagConstraints.EAST;
			gbc_lblD.gridx = 0;
			gbc_lblD.gridy = 0;
			panel_7.add(getLblD(), gbc_lblD);
			GridBagConstraints gbc_textField_DC = new GridBagConstraints();
			gbc_textField_DC.insets = new Insets(0, 0, 0, 5);
			gbc_textField_DC.fill = GridBagConstraints.BOTH;
			gbc_textField_DC.gridx = 1;
			gbc_textField_DC.gridy = 0;
			panel_7.add(getTextField_DC(), gbc_textField_DC);
			GridBagConstraints gbc_lblL = new GridBagConstraints();
			gbc_lblL.anchor = GridBagConstraints.EAST;
			gbc_lblL.insets = new Insets(0, 0, 0, 5);
			gbc_lblL.gridx = 2;
			gbc_lblL.gridy = 0;
			panel_7.add(getLblL(), gbc_lblL);
			GridBagConstraints gbc_textField_L = new GridBagConstraints();
			gbc_textField_L.fill = GridBagConstraints.BOTH;
			gbc_textField_L.gridx = 3;
			gbc_textField_L.gridy = 0;
			panel_7.add(getTextField_L(), gbc_textField_L);
		}
		return panel_7;
	}
	private JCheckBox getChckbxConstrained() {
		if (chckbxConstrained == null) {
			chckbxConstrained = new JCheckBox("Constrained");
		}
		return chckbxConstrained;
	}
	private JLabel getLblD() {
		if (lblD == null) {
			lblD = new JLabel("D:");
		}
		return lblD;
	}
	private JTextField getTextField_DC() {
		if (textField_DC == null) {
			textField_DC = new JTextField();
			textField_DC.setText("0.1");
			textField_DC.setColumns(10);
		}
		return textField_DC;
	}
	private JLabel getLblL() {
		if (lblL == null) {
			lblL = new JLabel("L:");
		}
		return lblL;
	}
	private JTextField getTextField_L() {
		if (textField_L == null) {
			textField_L = new JTextField();
			textField_L.setText("1");
			textField_L.setColumns(10);
		}
		return textField_L;
	}
	private JPanel getPanel_8() {
		if (panel_8 == null) {
			panel_8 = new JPanel();
			GridBagLayout gbl_panel_8 = new GridBagLayout();
			gbl_panel_8.columnWidths = new int[]{323, 0};
			gbl_panel_8.rowHeights = new int[]{24, 49, 0};
			gbl_panel_8.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel_8.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_8.setLayout(gbl_panel_8);
			GridBagConstraints gbc_panel_9 = new GridBagConstraints();
			gbc_panel_9.fill = GridBagConstraints.BOTH;
			gbc_panel_9.insets = new Insets(0, 0, 5, 0);
			gbc_panel_9.gridx = 0;
			gbc_panel_9.gridy = 0;
			panel_8.add(getPanel_9(), gbc_panel_9);
			GridBagConstraints gbc_panel_10 = new GridBagConstraints();
			gbc_panel_10.fill = GridBagConstraints.BOTH;
			gbc_panel_10.gridx = 0;
			gbc_panel_10.gridy = 1;
			panel_8.add(getPanel_10(), gbc_panel_10);
		}
		return panel_8;
	}
	private JPanel getPanel_9() {
		if (panel_9 == null) {
			panel_9 = new JPanel();
			GridBagLayout gbl_panel_9 = new GridBagLayout();
			gbl_panel_9.columnWidths = new int[]{0, 0};
			gbl_panel_9.rowHeights = new int[]{0, 0};
			gbl_panel_9.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel_9.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_9.setLayout(gbl_panel_9);
			GridBagConstraints gbc_chckbxDirected = new GridBagConstraints();
			gbc_chckbxDirected.gridx = 0;
			gbc_chckbxDirected.gridy = 0;
			panel_9.add(getChckbxDirected(), gbc_chckbxDirected);
		}
		return panel_9;
	}
	private JPanel getPanel_10() {
		if (panel_10 == null) {
			panel_10 = new JPanel();
			GridBagLayout gbl_panel_10 = new GridBagLayout();
			gbl_panel_10.columnWidths = new int[]{53, 55, 37, 0, 0};
			gbl_panel_10.rowHeights = new int[]{0, 0};
			gbl_panel_10.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_panel_10.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_10.setLayout(gbl_panel_10);
			GridBagConstraints gbc_lblD_1 = new GridBagConstraints();
			gbc_lblD_1.anchor = GridBagConstraints.EAST;
			gbc_lblD_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblD_1.gridx = 0;
			gbc_lblD_1.gridy = 0;
			panel_10.add(getLblD_1(), gbc_lblD_1);
			GridBagConstraints gbc_textField_DD = new GridBagConstraints();
			gbc_textField_DD.insets = new Insets(0, 0, 0, 5);
			gbc_textField_DD.fill = GridBagConstraints.BOTH;
			gbc_textField_DD.gridx = 1;
			gbc_textField_DD.gridy = 0;
			panel_10.add(getTextField_DD(), gbc_textField_DD);
			GridBagConstraints gbc_lblV = new GridBagConstraints();
			gbc_lblV.anchor = GridBagConstraints.EAST;
			gbc_lblV.insets = new Insets(0, 0, 0, 5);
			gbc_lblV.gridx = 2;
			gbc_lblV.gridy = 0;
			panel_10.add(getLblV(), gbc_lblV);
			GridBagConstraints gbc_textField_V = new GridBagConstraints();
			gbc_textField_V.fill = GridBagConstraints.BOTH;
			gbc_textField_V.gridx = 3;
			gbc_textField_V.gridy = 0;
			panel_10.add(getTextField_V(), gbc_textField_V);
		}
		return panel_10;
	}
	private JPanel getPanel_11() {
		if (panel_11 == null) {
			panel_11 = new JPanel();
			panel_11.add(getRdbtnOneState());
			panel_11.add(getRdbtnTwoState());
		}
		return panel_11;
	}
	private JCheckBox getChckbxDirected() {
		if (chckbxDirected == null) {
			chckbxDirected = new JCheckBox("Directed");
		}
		return chckbxDirected;
	}
	private JLabel getLblD_1() {
		if (lblD_1 == null) {
			lblD_1 = new JLabel("D:");
		}
		return lblD_1;
	}
	private JLabel getLblV() {
		if (lblV == null) {
			lblV = new JLabel("V:");
		}
		return lblV;
	}
	private JTextField getTextField_DD() {
		if (textField_DD == null) {
			textField_DD = new JTextField();
			textField_DD.setText("0.5");
			textField_DD.setColumns(10);
		}
		return textField_DD;
	}
	private JTextField getTextField_V() {
		if (textField_V == null) {
			textField_V = new JTextField();
			textField_V.setText("0.1");
			textField_V.setColumns(10);
		}
		return textField_V;
	}
}
