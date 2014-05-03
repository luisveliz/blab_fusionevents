package event;

import ij.IJ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI_NFVesicleSelection extends JFrame {

	private JPanel contentPane;
	private JTextArea infoText;
	private JButton btnAddVesicle;
	private JTextArea txtSelectAnArea;
	private JTextArea numberOfNonfusioned;
	private JLabel lblVesiclesSelected;
	
	private GUI_InputParameters GUI_Ip;
	private JButton btnNextStep;
	
	private int nVesicles=0;
	
	final private int minNVesicles=4;

	/**
	 * Create the frame.
	 */
	public GUI_NFVesicleSelection(GUI_InputParameters gui_ip) {
		GUI_Ip=gui_ip;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 250);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_textInfo = new GridBagLayout();
		gbl_textInfo.columnWidths = new int[]{200, 150, 150};
		gbl_textInfo.rowHeights = new int[]{75, 50, 75 ,50};
		gbl_textInfo.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_textInfo.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		contentPane.setLayout(gbl_textInfo);
		GridBagConstraints gbc_infoText = new GridBagConstraints();
		gbc_infoText.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_infoText.insets = new Insets(5, 5, 5, 0);
		gbc_infoText.fill = GridBagConstraints.BOTH;
		gbc_infoText.gridx = 0;
		gbc_infoText.gridy = 0;
		gbc_infoText.gridwidth=3;
		contentPane.add(getInfoText(), gbc_infoText);
		GridBagConstraints gbc_numberOfNonfusioned = new GridBagConstraints();
		gbc_numberOfNonfusioned.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_numberOfNonfusioned.insets = new Insets(0, 0, 5, 0);
		gbc_numberOfNonfusioned.gridx = 2;
		gbc_numberOfNonfusioned.gridy = 1;
		contentPane.add(getNumberOfNonfusioned(), gbc_numberOfNonfusioned);
		GridBagConstraints gbc_txtSelectAnArea = new GridBagConstraints();
		gbc_txtSelectAnArea.insets = new Insets(0, 5, 5, 5);
		gbc_txtSelectAnArea.fill = GridBagConstraints.BOTH;
		gbc_txtSelectAnArea.gridx = 0;
		gbc_txtSelectAnArea.gridy = 2;
		contentPane.add(getTxtSelectAnArea(), gbc_txtSelectAnArea);
		GridBagConstraints gbc_btnAddVesicle = new GridBagConstraints();
		gbc_btnAddVesicle.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddVesicle.gridx = 1;
		gbc_btnAddVesicle.gridy = 2;
		contentPane.add(getBtnAddVesicle(), gbc_btnAddVesicle);
		GridBagConstraints gbc_lblVesiclesSelected = new GridBagConstraints();
		gbc_lblVesiclesSelected.insets = new Insets(0, 0, 5, 0);
		gbc_lblVesiclesSelected.gridx = 2;
		gbc_lblVesiclesSelected.gridy = 2;
		contentPane.add(getLblVesiclesSelected(), gbc_lblVesiclesSelected);
		GridBagConstraints gbc_btnNextStep = new GridBagConstraints();
		gbc_btnNextStep.gridx = 2;
		gbc_btnNextStep.gridy = 3;
		contentPane.add(getBtnNextStep(), gbc_btnNextStep);
		setTitle("Step 2: Define the profile of the the non-fusioned vesicles");
		setVisible(false);
	}
	
	private JTextArea getInfoText() {
		if (infoText == null) {
			infoText = new JTextArea();
			infoText.setFont(new Font("Dialog", Font.PLAIN, 11));
			infoText.setText("For improving the throughput of the detection you must choose at least 4 areas of the movie that contain non-fusioned vesicles, with the aim of classifying more accurately the fusioned vesicles and non-fusioned ones");
			infoText.setEditable(false);
			infoText.setFocusable(false);
			infoText.setLineWrap(true);
			infoText.setWrapStyleWord(true);
			infoText.setBorder(null);
			infoText.setBackground(new Color(0,0,0,0));
		}
		return infoText;
	}
	private JButton getBtnAddVesicle() {
		if (btnAddVesicle == null) {
			btnAddVesicle = new JButton("Add selected vesicle");
			btnAddVesicle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUI_Ip.getGUI_FE().getThinker().addNonFusionedVesicle();
				}
			});
		}
		return btnAddVesicle;
	}
	private JTextArea getTxtSelectAnArea() {
		if (txtSelectAnArea == null) {
			txtSelectAnArea = new JTextArea();
			txtSelectAnArea.setFont(new Font("Dialog", Font.PLAIN, 11));
			txtSelectAnArea.setText("Select an area that enclosed an stationary vesicle (the ones that don't fuse with the cell membrane)");
			txtSelectAnArea.setEditable(false);
			txtSelectAnArea.setFocusable(false);
			txtSelectAnArea.setLineWrap(true);
			txtSelectAnArea.setWrapStyleWord(true);
			txtSelectAnArea.setBorder(null);
			txtSelectAnArea.setBackground(new Color(0,0,0,0));
		}
		return txtSelectAnArea;
	}
	private JTextArea getNumberOfNonfusioned() {
		if (numberOfNonfusioned == null) {
			numberOfNonfusioned = new JTextArea();
			numberOfNonfusioned.setFont(new Font("Dialog", Font.BOLD, 11));
			numberOfNonfusioned.setText("Number of non-fusioned vesicles added:");
			numberOfNonfusioned.setEditable(false);
			numberOfNonfusioned.setFocusable(false);
			numberOfNonfusioned.setLineWrap(true);
			numberOfNonfusioned.setWrapStyleWord(true);
			numberOfNonfusioned.setBorder(null);
			numberOfNonfusioned.setBackground(new Color(0,0,0,0));
		}
		return numberOfNonfusioned;
	}
	private JLabel getLblVesiclesSelected() {
		if (lblVesiclesSelected == null) {
			lblVesiclesSelected = new JLabel("0");
		}
		return lblVesiclesSelected;
	}
	private JButton getBtnNextStep() {
		if (btnNextStep == null) {
			btnNextStep = new JButton("Next Step ->");
			btnNextStep.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (nVesicles>=minNVesicles){
						GUI_Ip.startParametersInput();
						setVisible(false);
					}else{
						IJ.showMessage("You must select at least "+minNVesicles+" non-fusioned vesicles to continue!");
					}
				}
			});
		}
		return btnNextStep;
	}
	
	public void addNFVesicle(){
		nVesicles++;
		lblVesiclesSelected.setText(Integer.toString(nVesicles));
	}
	
	public void startNFVesicleSelection(){
		setVisible(true);
	}
}
