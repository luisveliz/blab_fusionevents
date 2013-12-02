package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import main.Thinker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIPreferences extends JFrame {

	private Thinker thinker;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblNOfDecimals;
	private JSpinner spinner;
	private JButton btnSave;


	/**
	 * Create the frame.
	 */
	public GUIPreferences(Thinker thinker)
	{
		this.thinker = thinker;
		setTitle("Preferences");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		this.setVisible(true);
	}
	public int jSpinner_NDecimals_getValue()
	{
		return ((Integer)spinner.getValue()).intValue();
		
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getLblNOfDecimals());
			panel.add(getSpinner());
			panel.add(getBtnSave());
		}
		return panel;
	}
	private JLabel getLblNOfDecimals() {
		if (lblNOfDecimals == null) {
			lblNOfDecimals = new JLabel("N\u00B0 of decimals:");
		}
		return lblNOfDecimals;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
		}
		return spinner;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save preferences");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					thinker.jButton_SavePreferences_clicked();
				}
			});
		}
		return btnSave;
	}
}
