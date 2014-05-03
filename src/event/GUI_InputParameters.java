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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

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
	private double minimumIntIncrease;
	private ArrayList<Double> nonFusionedIntArray;
	private JLabel lblMinimumIntensity;
	private JSpinner minIntSpinner;
	/**
	 * Create the frame.
	 */
	public GUI_InputParameters(GUI_FusionEvents gui_fe) {
		nonFusionedIntArray=new ArrayList<Double>();
		this.GUI_Fe=gui_fe;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 415, 200);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{330, 70};
		gbl_contentPane.rowHeights = new int[]{25, 25, 25, 25, 25,25};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblSquareWindowSize = new GridBagConstraints();
		gbc_lblSquareWindowSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblSquareWindowSize.gridx = 0;
		gbc_lblSquareWindowSize.gridy = 1;
		contentPane.add(getLblSquareWindowSize(), gbc_lblSquareWindowSize);
		GridBagConstraints gbc_patchSizeSpinner = new GridBagConstraints();
		gbc_patchSizeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_patchSizeSpinner.insets = new Insets(0, 0, 5, 15);
		gbc_patchSizeSpinner.gridx = 1;
		gbc_patchSizeSpinner.gridy = 1;
		gbc_patchSizeSpinner.insets = new Insets(0,0,0,15);
		contentPane.add(getPatchSizeSpinner(), gbc_patchSizeSpinner);
		GridBagConstraints gbc_lblTimeBetweenFrames = new GridBagConstraints();
		gbc_lblTimeBetweenFrames.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeBetweenFrames.gridx = 0;
		gbc_lblTimeBetweenFrames.gridy = 2;
		contentPane.add(getLabel_1(), gbc_lblTimeBetweenFrames);
		GridBagConstraints gbc_timeFramesSpinner = new GridBagConstraints();
		gbc_timeFramesSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeFramesSpinner.insets = new Insets(0, 0, 5, 15);
		gbc_timeFramesSpinner.gridx = 1;
		gbc_timeFramesSpinner.gridy = 2;
		gbc_timeFramesSpinner.insets = new Insets(0,0,0,15);
		contentPane.add(getTimeFramesSpinner(), gbc_timeFramesSpinner);
		GridBagConstraints gbc_lblMinimumIntensity = new GridBagConstraints();
		gbc_lblMinimumIntensity.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimumIntensity.gridx = 0;
		gbc_lblMinimumIntensity.gridy = 3;
		contentPane.add(getLblMinimumIntensity(), gbc_lblMinimumIntensity);
		GridBagConstraints gbc_minIntSpinner = new GridBagConstraints();
		gbc_minIntSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_minIntSpinner.insets = new Insets(0, 0, 5, 15);
		gbc_minIntSpinner.gridx = 1;
		gbc_minIntSpinner.gridy = 3;
		contentPane.add(getMinIntSpinner(), gbc_minIntSpinner);
		GridBagConstraints gbc_startFeDetectionButton = new GridBagConstraints();
		gbc_startFeDetectionButton.gridx = 0;
		gbc_startFeDetectionButton.gridy = 5;
		gbc_startFeDetectionButton.gridheight=1;
		gbc_startFeDetectionButton.gridwidth=2;
		contentPane.add(getStartFeDetectionButton(), gbc_startFeDetectionButton);
		setVisible(false);
		setTitle("Step 3: FE Detector Input Parameters");
	}

	private JSpinner getPatchSizeSpinner() {
		if (patchSizeSpinner == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(3, 3, 99, 2);
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
					try{
				    	FileWriter fstream = new FileWriter("test.txt",true);
						PrintWriter out = new PrintWriter(fstream);
						out.println("empecé a trabajar");
						out.close();
				    }catch(Exception ev){
						System.err.println("Error: " + ev.getMessage());
				    }
					GUI_Fe.startFeDetection(fitPatchSize,timeFrames,minimumIntIncrease,nonFusionedIntArray);
					setVisible(false);
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
			timeFramesSpinner = new JSpinner(model);
			timeFrames=(Integer)timeFramesSpinner.getValue();
			timeFramesSpinner.addChangeListener(new ChangeListener() {
			    @Override
			    public void stateChanged(ChangeEvent e) {
			        timeFrames=(Integer)timeFramesSpinner.getValue();
			    }
			});
		}
		return timeFramesSpinner;
	}
	private JLabel getLblMinimumIntensity() {
		if (lblMinimumIntensity == null) {
			lblMinimumIntensity = new JLabel("Minimum acceptable intensity increase ");
		}
		return lblMinimumIntensity;
	}
	
	private JSpinner getMinIntSpinner() {
		if (minIntSpinner == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(1., 1., 100., 0.1);
			minIntSpinner = new JSpinner(model);
			minimumIntIncrease=(Double) minIntSpinner.getValue();
			minIntSpinner.addChangeListener(new ChangeListener() {
			    @Override
			    public void stateChanged(ChangeEvent e) {
			        minimumIntIncrease=(Double)minIntSpinner.getValue();
			    }
			});
		}
		return minIntSpinner;
	}
	
	public void startParametersInput(){
		setVisible(true);
	}
	
	public GUI_FusionEvents getGUI_FE(){
		return GUI_Fe;
	}
	
	public void addNonFusionedVesicle(double avgVesicleInt){
		nonFusionedIntArray.add(avgVesicleInt);
	}
}
