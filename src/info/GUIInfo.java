package info;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JCheckBox;
import java.awt.Insets;
import javax.swing.JToolBar;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class GUIInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	protected JScrollPane jScrollPane = null;
	protected JTextArea jTextArea_TrajPoints = null;
	protected JScrollPane jScrollPane1 = null;
	protected JTextArea jTextArea_TrajInfo = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	protected JScrollPane jScrollPane2 = null;
	private JPanel jPanel1 = null;
	protected JCheckBox chckbxUserType;
	protected JCheckBox chckbxModesOfMotion;
	protected JCheckBox chckbxGeneralInfo;
	private JPanel panel;
	private JSplitPane splitPane;
	protected JTextPane textPaneGlobalInfo;

	/**
	 * This is the default constructor
	 */
	public GUIInfo() 
	{
		super();
		initialize();
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 700);
		this.setPreferredSize(new Dimension(500, 600));
		this.setContentPane(getJContentPane());
		this.setTitle("Info");
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
			GridBagLayout gbl_jContentPane = new GridBagLayout();
			gbl_jContentPane.columnWidths = new int[]{484, 0};
			gbl_jContentPane.rowHeights = new int[]{0, 0};
			gbl_jContentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_jContentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			jContentPane.setLayout(gbl_jContentPane);
			GridBagConstraints gbc_splitPane = new GridBagConstraints();
			gbc_splitPane.fill = GridBagConstraints.BOTH;
			gbc_splitPane.gridx = 0;
			gbc_splitPane.gridy = 0;
			jContentPane.add(getSplitPane(), gbc_splitPane);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(20, 20));
			jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jScrollPane.setViewportView(getJTextArea_TrajPoints());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea_TrajPoints	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_TrajPoints() {
		if (jTextArea_TrajPoints == null) {
			jTextArea_TrajPoints = new JTextArea();
			jTextArea_TrajPoints.setEditable(false);
			jTextArea_TrajPoints.setTabSize(10);
			jTextArea_TrajPoints.setLineWrap(false);
			jTextArea_TrajPoints.setWrapStyleWord(false);
			jTextArea_TrajPoints.setFont(new Font("SansSerif", Font.PLAIN, 10));
		}
		return jTextArea_TrajPoints;
	}



	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new Dimension(20, 20));
			jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jScrollPane1.setViewportView(getJTextArea_TrajInfo());
		}
		return jScrollPane1;
	}



	/**
	 * This method initializes jTextArea_TrajInfo	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea_TrajInfo() {
		if (jTextArea_TrajInfo == null) {
			jTextArea_TrajInfo = new JTextArea();
			jTextArea_TrajInfo.setEditable(false);
			jTextArea_TrajInfo.setWrapStyleWord(false);
			jTextArea_TrajInfo.setTabSize(10);
			jTextArea_TrajInfo.setLineWrap(false);
			jTextArea_TrajInfo.setFont(new Font("SansSerif", Font.PLAIN, 10));
		}
		return jTextArea_TrajInfo;
	}



	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(150);
			jSplitPane.setTopComponent(getJScrollPane1());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}



	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			jPanel = new JPanel();
			jPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Global Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			GridBagLayout gbl_jPanel = new GridBagLayout();
			gbl_jPanel.rowWeights = new double[]{0.0, 0.0};
			gbl_jPanel.columnWeights = new double[]{1.0};
			jPanel.setLayout(gbl_jPanel);
			jPanel.add(getJScrollPane2(), gridBagConstraints1);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			jPanel.add(getPanel(), gbc_panel);
		}
		return jPanel;
	}



	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setPreferredSize(new Dimension(20, 20));
			jScrollPane2.setViewportView(getTextPaneGlobalInfo());
			jScrollPane2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
				
				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {
					System.out.println("scrollbar value:"+jScrollPane2.getVerticalScrollBar().getValue());
				}
			});
		}
		return jScrollPane2;
	}



	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = -1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = -1;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Trajectory Selected", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
			jPanel1.add(getJSplitPane(), gridBagConstraints);
		}
		return jPanel1;
	}
	private JCheckBox getChckbxUserType() {
		if (chckbxUserType == null) {
			chckbxUserType = new JCheckBox("User Type");
		}
		return chckbxUserType;
	}
	private JCheckBox getChckbxModesOfMotion() {
		if (chckbxModesOfMotion == null) {
			chckbxModesOfMotion = new JCheckBox("Modes of Motion");
			chckbxModesOfMotion.setSelected(true);
		}
		return chckbxModesOfMotion;
	}
	private JCheckBox getChckbxGeneralInfo() {
		if (chckbxGeneralInfo == null) {
			chckbxGeneralInfo = new JCheckBox("General Info");
		}
		return chckbxGeneralInfo;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getChckbxGeneralInfo());
			panel.add(getChckbxModesOfMotion());
			panel.add(getChckbxUserType());
		}
		return panel;
	}
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setLeftComponent(getJPanel());
			splitPane.setRightComponent(getJPanel1());
			splitPane.setDividerLocation(300);
		}
		return splitPane;
	}
	private JTextPane getTextPaneGlobalInfo() {
		if (textPaneGlobalInfo == null) {
			textPaneGlobalInfo = new JTextPane();
			textPaneGlobalInfo.setFont(new Font("SansSerif", Font.PLAIN, 10));
		}
		return textPaneGlobalInfo;
	}
}
