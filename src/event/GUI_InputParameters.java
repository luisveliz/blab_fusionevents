package event;

import ij.IJ;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagLayout;

import javax.swing.JSpinner;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Thinker;

public class GUI_InputParameters extends JFrame {

	private JPanel contentPane;
	private JSpinner patchSizeSpinner;
	private JLabel lblSquareWindowSize;
	private int windowSize;
	private JButton startFeDetectionButton;
	private GUI_FusionEvents GUI_Fe;
	
	private JLabel lblTimeBetweenFrames;
	private JSpinner timeFramesSpinner;
	
	private int fitPatchSize;
	private int timeFrames;
	/**
	 * Create the frame.
	 */
	public GUI_InputParameters(GUI_FusionEvents gui_fe) {
		this.GUI_Fe=gui_fe;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblSquareWindowSize = new GridBagConstraints();
		gbc_lblSquareWindowSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSquareWindowSize.gridx = 1;
		gbc_lblSquareWindowSize.gridy = 2;
		contentPane.add(getLblSquareWindowSize(), gbc_lblSquareWindowSize);
		GridBagConstraints gbc_patchSizeSpinner = new GridBagConstraints();
		gbc_patchSizeSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_patchSizeSpinner.gridx = 3;
		gbc_patchSizeSpinner.gridy = 2;
		contentPane.add(getPatchSizeSpinner(), gbc_patchSizeSpinner);
		GridBagConstraints gbc_lblTimeBetweenFrames = new GridBagConstraints();
		gbc_lblTimeBetweenFrames.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeBetweenFrames.gridx = 1;
		gbc_lblTimeBetweenFrames.gridy = 4;
		contentPane.add(getLabel_1(), gbc_lblTimeBetweenFrames);
		GridBagConstraints gbc_timeFramesSpinner = new GridBagConstraints();
		gbc_timeFramesSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_timeFramesSpinner.gridx = 3;
		gbc_timeFramesSpinner.gridy = 4;
		contentPane.add(getTimeFramesSpinner(), gbc_timeFramesSpinner);
		GridBagConstraints gbc_startFeDetectionButton = new GridBagConstraints();
		gbc_startFeDetectionButton.insets = new Insets(0, 0, 5, 5);
		gbc_startFeDetectionButton.gridx = 1;
		gbc_startFeDetectionButton.gridy = 7;
		contentPane.add(getStartFeDetectionButton(), gbc_startFeDetectionButton);
		setVisible(true);
		setTitle("FE Detector Input Parameters");
	}

	private JSpinner getPatchSizeSpinner() {
		if (patchSizeSpinner == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(3, 3, 15, 2);
			patchSizeSpinner = new JSpinner(model);
			fitPatchSize=(Integer)patchSizeSpinner.getValue();
			patchSizeSpinner.addChangeListener(new ChangeListener() {
			    @Override
			    public void stateChanged(ChangeEvent e) {
			        fitPatchSize=(Integer)patchSizeSpinner.getValue();
			    }
			});
		}
		return patchSizeSpinner;
	}
	private JLabel getLblSquareWindowSize() {
		if (lblSquareWindowSize == null) {
			lblSquareWindowSize = new JLabel("Square window's analysis size (in pixels)");
		}
		return lblSquareWindowSize;
	}
	
	
	private JButton getStartFeDetectionButton() {
		if (startFeDetectionButton == null) {
			startFeDetectionButton = new JButton("Start FE Detection");
			startFeDetectionButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					GUI_Fe.startFeDetection(fitPatchSize,timeFrames);
				}
			});
		}
		return startFeDetectionButton;
	}
	private JLabel getLabel_1() {
		if (lblTimeBetweenFrames == null) {
			lblTimeBetweenFrames = new JLabel("Time between frames (in miliseconds)");
		}
		return lblTimeBetweenFrames;
	}
	private JSpinner getTimeFramesSpinner() {
		if (timeFramesSpinner == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 5000, 1);
			timeFramesSpinner = new JSpinner();
			timeFrames=(Integer)timeFramesSpinner.getValue();
			timeFramesSpinner.addChangeListener(new ChangeListener() {
			    @Override
			    public void stateChanged(ChangeEvent e) {
			        timeFrames=(Integer)patchSizeSpinner.getValue();
			    }
			});
		}
		return timeFramesSpinner;
	}
}
