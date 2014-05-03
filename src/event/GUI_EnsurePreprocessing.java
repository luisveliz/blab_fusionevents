package event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import main.Thinker;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_EnsurePreprocessing extends JFrame {

	private JPanel contentPane;
	private GUI_NFVesicleSelection GUI_nfvs;
	private JTextArea txtInfo;
	private JButton btnNextStep;
	private JButton btnCancelAnalysis;
	private JButton btnExitPlugin;

	/**
	 * Create the frame.
	 */
	public GUI_EnsurePreprocessing(GUI_NFVesicleSelection gui_nfvesicle) {
		GUI_nfvs=gui_nfvesicle;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{140,140,140};
		gbl_contentPane.rowHeights = new int[]{175,75};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_txtInfo = new GridBagConstraints();
		gbc_txtInfo.insets = new Insets(0, 0, 5, 0);
		gbc_txtInfo.fill = GridBagConstraints.BOTH;
		gbc_txtInfo.gridwidth = 3;
		gbc_txtInfo.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtInfo.gridx = 0;
		gbc_txtInfo.gridy = 0;
		contentPane.add(getTxtInfo(), gbc_txtInfo);
		GridBagConstraints gbc_btnExitPlugin = new GridBagConstraints();
		gbc_btnExitPlugin.anchor = GridBagConstraints.PAGE_START;
		gbc_btnExitPlugin.insets = new Insets(0, 0, 0, 5);
		gbc_btnExitPlugin.gridx = 0;
		gbc_btnExitPlugin.gridy = 1;
		contentPane.add(getBtnExitPlugin(), gbc_btnExitPlugin);
		GridBagConstraints gbc_btnCancelAnalysis = new GridBagConstraints();
		gbc_btnCancelAnalysis.anchor = GridBagConstraints.PAGE_START;
		gbc_btnCancelAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelAnalysis.gridx = 1;
		gbc_btnCancelAnalysis.gridy = 1;
		contentPane.add(getBtnCancelAnalysis(), gbc_btnCancelAnalysis);
		GridBagConstraints gbc_btnNextStep = new GridBagConstraints();
		gbc_btnNextStep.anchor = GridBagConstraints.PAGE_START;
		gbc_btnNextStep.gridx = 2;
		gbc_btnNextStep.gridy = 1;
		contentPane.add(getBtnNextStep(), gbc_btnNextStep);
		setTitle("Step 1: Ensuring the movie preprocessing");
		setVisible(true);
		setResizable(false);
	}

	private JTextArea getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextArea();
			txtInfo.setFont(new Font("Dialog", Font.PLAIN, 11));
			txtInfo.setText("Ensure yourself that you have made the correct preprocessing for your movie, that means at least a Bleachiing Correction and a Background Substraction./n If you haven't you must Exit Plugin and then re-enter to Image J and make the processing, you can find a Bleaching Correction Tool at BrauchiLab Plugins Menu and a Substracting Background Tool at Process->Substract Background at the Main Menu./n If you don't want to continue with this analysis you can continue using the SPT Plugin pressing Cancel Analysis");
			txtInfo.setEditable(false);
			txtInfo.setFocusable(false);
			txtInfo.setLineWrap(true);
			txtInfo.setWrapStyleWord(true);
			txtInfo.setBorder(null);
			txtInfo.setBackground(new Color(0,0,0,0));
		}
		return txtInfo;
	}
	private JButton getBtnNextStep() {
		if (btnNextStep == null) {
			btnNextStep = new JButton("Next Step ->");
			btnNextStep.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					GUI_nfvs.startNFVesicleSelection();
					setVisible(false);
					dispose();
				}
			});
		}
		return btnNextStep;
	}
	private JButton getBtnCancelAnalysis() {
		if (btnCancelAnalysis == null) {
			btnCancelAnalysis = new JButton("Cancel Analysis");
			btnCancelAnalysis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return btnCancelAnalysis;
	}
	private JButton getBtnExitPlugin() {
		if (btnExitPlugin == null) {
			btnExitPlugin = new JButton("Exit Plugin");
			btnExitPlugin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return btnExitPlugin;
	}
}
