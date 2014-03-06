package loader;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GUILoader extends JFrame 
{
	/*-------------------------------------------------------------*/
	FileLoader loader;
	ButtonGroup loadSaveGroup;
	ButtonGroup filetypeGroup;
	String pathToolTip;
	/*-------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextField_Directory = null;
	private JButton jButton_LoadSave = null;
	private JButton jButton_Browse = null;
	private JProgressBar jProgressBar = null;
	private JRadioButton rdbtnSptProject;
	private JRadioButton rdbtnXyTxt;
	private JRadioButton rdbtnImageStack;
	private JPanel panel;
	private JTextField txtFilexml_name;
	private JLabel lblDirectory;
	private JPanel panel_1;
	private JRadioButton rdbtnLoad;
	private JRadioButton rdbtnSave;

	/**
	 * This is the default constructor
	 */
	public GUILoader(FileLoader loader) 
	{
		super();
		this.loader = loader;
		initialize();
		loadSaveGroup = new ButtonGroup();
		loadSaveGroup.add(rdbtnLoad);
		loadSaveGroup.add(rdbtnSave);
		filetypeGroup = new ButtonGroup();
		filetypeGroup.add(rdbtnSptProject);
		filetypeGroup.add(rdbtnXyTxt);
		filetypeGroup.add(rdbtnImageStack);
	}

	
	/*---------------------------------------------------*/
	public boolean radioButton_Load_isSelected()
	{
		return rdbtnLoad.isSelected();
	}
	public int radioButton_Type_getOption()
	{
		if(rdbtnSptProject.isSelected())
			return FileLoader.XML;
		else if(rdbtnXyTxt.isSelected())
			return FileLoader.TXT;
		else
			return FileLoader.IMAGE;
	}
	public void radioButton_Image_setEnabled(boolean b)
	{
		rdbtnImageStack.setEnabled(b);
	}
	public void jTextField_Name_setEditable(boolean b)
	{
		txtFilexml_name.setEditable(b);
	}
	
	public String jTextField_Directory_getText()
	{
		return jTextField_Directory.getText();
	}
	public void jTextField_Directory_setText(String text)
	{
		jTextField_Directory.setText(text);
		jTextField_Directory.setToolTipText(text);
	}
	public String jTextField_Name_getText()
	{
		return txtFilexml_name.getText();
	}
	public void jTextField_Name_setText(String text)
	{
		txtFilexml_name.setText(text);
		txtFilexml_name.setToolTipText(text);
	}
	public void jButton_LoadSave_setText(String text)
	{
		jButton_LoadSave.setText(text);
	}
	public void jProgressBar_setValue(int value)
	{
		jProgressBar.setValue(value);
	}
	/*---------------------------------------------------*/
	
	
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
		this.setSize(518, 144);
		this.setContentPane(getJContentPane());
		this.setTitle("Loader/Saver");
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent e) {
				System.out.println("Load trajs from txt windowActivated()"); // TODO Auto-generated Event stub windowActivated()
			}
		});
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
			gbl_jContentPane.columnWidths = new int[]{0, 133, 178, 49, 19, 0};
			gbl_jContentPane.rowHeights = new int[]{48, 0, 0, 0};
			gbl_jContentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_jContentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			jContentPane.setLayout(gbl_jContentPane);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.insets = new Insets(0, 0, 5, 5);
			gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel_1.gridx = 1;
			gbc_panel_1.gridy = 0;
			jContentPane.add(getPanel_1(), gbc_panel_1);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.anchor = GridBagConstraints.WEST;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			jContentPane.add(getPanel(), gbc_panel);
			GridBagConstraints gbc_lblDirectory = new GridBagConstraints();
			gbc_lblDirectory.insets = new Insets(0, 0, 5, 5);
			gbc_lblDirectory.anchor = GridBagConstraints.EAST;
			gbc_lblDirectory.gridx = 0;
			gbc_lblDirectory.gridy = 1;
			jContentPane.add(getLblDirectory(), gbc_lblDirectory);
			GridBagConstraints gbc_jTextField_Path = new GridBagConstraints();
			gbc_jTextField_Path.fill = GridBagConstraints.HORIZONTAL;
			gbc_jTextField_Path.anchor = GridBagConstraints.WEST;
			gbc_jTextField_Path.insets = new Insets(0, 0, 5, 5);
			gbc_jTextField_Path.gridx = 1;
			gbc_jTextField_Path.gridy = 1;
			jContentPane.add(getJTextField_Path(), gbc_jTextField_Path);
			GridBagConstraints gbc_txtFilexml_name = new GridBagConstraints();
			gbc_txtFilexml_name.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtFilexml_name.anchor = GridBagConstraints.WEST;
			gbc_txtFilexml_name.insets = new Insets(0, 0, 5, 5);
			gbc_txtFilexml_name.gridx = 2;
			gbc_txtFilexml_name.gridy = 1;
			jContentPane.add(getTxtFilexml_name(), gbc_txtFilexml_name);
			GridBagConstraints gbc_jButton_Browse = new GridBagConstraints();
			gbc_jButton_Browse.fill = GridBagConstraints.HORIZONTAL;
			gbc_jButton_Browse.insets = new Insets(0, 0, 5, 5);
			gbc_jButton_Browse.gridx = 3;
			gbc_jButton_Browse.gridy = 1;
			jContentPane.add(getJButton_Browse(), gbc_jButton_Browse);
			GridBagConstraints gbc_jProgressBar = new GridBagConstraints();
			gbc_jProgressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_jProgressBar.gridwidth = 2;
			gbc_jProgressBar.insets = new Insets(0, 0, 0, 5);
			gbc_jProgressBar.gridx = 1;
			gbc_jProgressBar.gridy = 2;
			jContentPane.add(getJProgressBar(), gbc_jProgressBar);
			GridBagConstraints gbc_jButton_LoadSave = new GridBagConstraints();
			gbc_jButton_LoadSave.insets = new Insets(0, 0, 0, 5);
			gbc_jButton_LoadSave.fill = GridBagConstraints.HORIZONTAL;
			gbc_jButton_LoadSave.gridx = 3;
			gbc_jButton_LoadSave.gridy = 2;
			jContentPane.add(getJButton_LoadSave(), gbc_jButton_LoadSave);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField_LoadXYPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Path() {
		if (jTextField_Directory == null) {
			jTextField_Directory = new JTextField();
			jTextField_Directory.setPreferredSize(new Dimension(300, 30));
		}
		return jTextField_Directory;
	}

	/**
	 * This method initializes jButton_LoadXYfile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_LoadSave() {
		if (jButton_LoadSave == null) {
			jButton_LoadSave = new JButton();
			jButton_LoadSave.setText("Load");
			jButton_LoadSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					((ProjectManager)loader).jButton_LoadXY_clicked();
					loader.jButton_LoadSave_clicked();
				}
			});
		}
		return jButton_LoadSave;
	}


	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Browse() {
		if (jButton_Browse == null) {
			jButton_Browse = new JButton();
			jButton_Browse.setText("Browse");
			jButton_Browse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					loader.jButton_Browse_clicked();
				}
			});
		}
		return jButton_Browse;
	}


	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setPreferredSize(new Dimension(100, 30));
			jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}

	private JRadioButton getRdbtnSptProject() {
		if (rdbtnSptProject == null) {
			rdbtnSptProject = new JRadioButton("SPT project",true);
			rdbtnSptProject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loader.radioButton_Type_stateChanged();
				}
			});
		}
		return rdbtnSptProject;
	}
	private JRadioButton getRdbtnXyTxt() {
		if (rdbtnXyTxt == null) {
			rdbtnXyTxt = new JRadioButton("XY txt");
			rdbtnXyTxt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					loader.radioButton_Type_stateChanged();
				}
			});
		}
		return rdbtnXyTxt;
	}
	private JRadioButton getRdbtnImageStack() {
		if (rdbtnImageStack == null) {
			rdbtnImageStack = new JRadioButton("Image stack");
			rdbtnImageStack.setVisible(false);
			rdbtnImageStack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					loader.radioButton_Type_stateChanged();
				}
			});
		}
		return rdbtnImageStack;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_rdbtnSptProject = new GridBagConstraints();
			gbc_rdbtnSptProject.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnSptProject.gridx = 0;
			gbc_rdbtnSptProject.gridy = 0;
			panel.add(getRdbtnSptProject(), gbc_rdbtnSptProject);
			GridBagConstraints gbc_rdbtnXyTxt = new GridBagConstraints();
			gbc_rdbtnXyTxt.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnXyTxt.gridx = 1;
			gbc_rdbtnXyTxt.gridy = 0;
			panel.add(getRdbtnXyTxt(), gbc_rdbtnXyTxt);
			GridBagConstraints gbc_rdbtnImageStack = new GridBagConstraints();
			gbc_rdbtnImageStack.gridx = 2;
			gbc_rdbtnImageStack.gridy = 0;
			panel.add(getRdbtnImageStack(), gbc_rdbtnImageStack);
		}
		return panel;
	}
	private JTextField getTxtFilexml_name() {
		if (txtFilexml_name == null) {
			txtFilexml_name = new JTextField();
			txtFilexml_name.setEditable(false);
			txtFilexml_name.setPreferredSize(new Dimension(6, 30));
			txtFilexml_name.setColumns(10);
		}
		return txtFilexml_name;
	}
	private JLabel getLblDirectory() {
		if (lblDirectory == null) {
			lblDirectory = new JLabel("Path:");
		}
		return lblDirectory;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{67, 50, 0, 0};
			gbl_panel_1.rowHeights = new int[]{18, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_rdbtnLoad = new GridBagConstraints();
			gbc_rdbtnLoad.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnLoad.anchor = GridBagConstraints.NORTHWEST;
			gbc_rdbtnLoad.gridx = 0;
			gbc_rdbtnLoad.gridy = 0;
			panel_1.add(getRdbtnLoad(), gbc_rdbtnLoad);
			GridBagConstraints gbc_rdbtnSave = new GridBagConstraints();
			gbc_rdbtnSave.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnSave.gridx = 1;
			gbc_rdbtnSave.gridy = 0;
			panel_1.add(getRdbtnSave(), gbc_rdbtnSave);
		}
		return panel_1;
	}
	private JRadioButton getRdbtnLoad() {
		if (rdbtnLoad == null) {
			rdbtnLoad = new JRadioButton("Load", true);
			rdbtnLoad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					loader.radioButton_LoadSave_stateChanged();
				}
			});
		}
		return rdbtnLoad;
	}
	private JRadioButton getRdbtnSave() {
		if (rdbtnSave == null) {
			rdbtnSave = new JRadioButton("Save");
			rdbtnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loader.radioButton_LoadSave_stateChanged();
				}
			});
		}
		return rdbtnSave;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
