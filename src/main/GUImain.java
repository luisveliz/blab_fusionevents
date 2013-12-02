package main;


import ij.IJ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import visualization.XYCanvas;
import data.Trajectory;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Action;
import javax.swing.AbstractAction;

/**
 * @author Luis
 *
 */
public class GUImain extends JFrame 
{
	private Thinker thinker;

	private TableModelTrajectories trajectoriesTableModel;
	private TableModelSubTrajs subtrajectoriesTableModel;
	
	private int trajectoriesTable_selectedRow;
	private int subtrajectoriesTable_selectedRow;
	private XYCanvas canvas_xyTrajs=null;
	
	DecimalFormat df = new DecimalFormat();
	
	
	/*-------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane_TrajectoriesTable = null;
	private JTable jTable_TrajectoriesTable = null;
	private JPanel jPanel8 = null;
	private JCheckBox jCheckBox_Corralled = null;
	private JCheckBox jCheckBox_Anomalous = null;
	private JCheckBox jCheckBox_Normal = null;
	private JCheckBox jCheckBox_Directed = null;
	private JSpinner jSpinner_TrajectoryLength = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel_SubtrajsEditor = null;
	private JPanel jPanel_MSD_XYCanvas = null;
	private JPanel jPanel_SubtrajAnalysis_Controls = null;
	private JSlider jSlider_initialFrame = null;
	private JSlider jSlider_finalFrame = null;
	private JLabel jLabel_initialFrame = null;
	private JLabel jLabel_finalFrame = null;
	private JButton jButton_AddSubtraj = null;
	private JSpinner jSpinner_initialFrame = null;
	private JSpinner jSpinner_finalFrame = null;
	private JScrollPane jScrollPane_SubtrajectoriesTable = null;
	private JTable jTable_SubtrajectoriesTable = null;
	private JCheckBox jCheckBox_ShowGaps = null;
	private JCheckBox jCheckBox_NotDefined = null;
	private JCheckBox jCheckBox_ShowPoints = null;
	private JPanel jPanel_Manual_SubtrajEditor_XYCanvasOptions = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox jCheckBox_showInfo = null;
	private JPanel jPanel_Manual_Trajectories = null;
	private JPanel jPanel_Manual_Subtrajectories = null;
	private JButton jButton_ResetSubtrajs = null;
	private JCheckBox jCheckBox_ShowSubtraj = null;
	private JSlider jSlider_zoom = null;
	private JCheckBox jCheckBox_ShowAllTrajs = null;
	private JPanel jPanel13 = null;
	private JPanel jPanel14 = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenu jMenu2 = null;
	private JMenuItem jMenuItem1 = null;
	private JMenu jMenu3 = null;
	private JMenuItem jMenuItem2 = null;
	private JMenuItem jMenuItem4 = null;
	private JMenuItem jMenuItem5 = null;
	private JMenuItem jMenuItem6 = null;
	private JMenuItem jMenuItem8 = null;
	private JMenuItem jMenuItem9 = null;
	private JMenuItem jMenuItem11 = null;
	private JSeparator jSeparator1 = null;
	private JLabel jLabel2 = null;

	private JCheckBox jCheckBox_showVectors = null;

	private JMenuItem jMenuItem14 = null;

	private JPanel jPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JLabel jLabel3 = null;
	private JMenuItem mntmSimulateTrajectories;
	private JPanel panel;
	private JComboBox comboBox_Sets;
	private JLabel lblCurrentSet;
	private JSplitPane splitPane;
	private JPanel panel_1;
	private JMenuItem mntmDataInfo;
	private JPanel panel_2;
	private JTextField txtNewTrajSet;
	private JButton btnRenameSet;
	private JButton btnDelete;
	private JButton btnDeleteTraj;
	private JCheckBox chckbxState;
	private JPanel panel_3;
	private JMenuItem mntmDirection;
	private Action action;

	/**
	 * This is the default constructor
	 */
	public GUImain(Thinker thinker) 
	{
		super();
		this.thinker = thinker;
		df.setMaximumFractionDigits(5);
		initialize();
	}

	/*-----------------------------------------------------------------------------------------*/
	
	public void controlsSetEnabled(boolean b)
	{
		jButton_AddSubtraj.setEnabled(b);
		jButton_ResetSubtrajs.setEnabled(b);
		jButton.setEnabled(b);
		jButton1.setEnabled(b);
		jButton2.setEnabled(b);
		jButton3.setEnabled(b);
		jSlider_initialFrame.setEnabled(b);
		jSlider_finalFrame.setEnabled(b);
		jSlider_zoom.setEnabled(b);
		jSpinner_initialFrame.setEnabled(b);
		jSpinner_finalFrame.setEnabled(b);
		jSpinner_TrajectoryLength.setEnabled(b);
		btnDelete.setEnabled(b);
		btnDeleteTraj.setEnabled(b);
		btnRenameSet.setEnabled(b);
		comboBox_Sets.setEnabled(b);

		jCheckBox_Normal.setEnabled(b);
		jCheckBox_Corralled.setEnabled(b);
		jCheckBox_Directed.setEnabled(b);
		jCheckBox_Anomalous.setEnabled(b);
		jCheckBox_NotDefined.setEnabled(b);
		
		jCheckBox_ShowAllTrajs.setEnabled(b);
		jCheckBox_ShowGaps.setEnabled(b);
		jCheckBox_showInfo.setEnabled(b);
		jCheckBox_ShowPoints.setEnabled(b);
		jCheckBox_ShowSubtraj.setEnabled(b);
		jCheckBox_showVectors.setEnabled(b);
		chckbxState.setEnabled(b);	
	}
	
	
	
	
	public int jComboBox_Sets_getSelectedIndex()
	{
		return comboBox_Sets.getSelectedIndex();
	}
	public void jComboBox_Sets_setSelectedIndex(int index)
	{
		comboBox_Sets.setSelectedIndex(index);
	}
	public void jComboBox_Sets_Update(String[] items)
	{
		comboBox_Sets.removeActionListener(comboBox_Sets.getActionListeners()[0]);
		
		comboBox_Sets.removeAllItems();
		for(int i=0;i<items.length;i++)
			comboBox_Sets.addItem(items[i]);
		
		comboBox_Sets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thinker.jComboBox_CurrentSet_stateChanged();
			}
		});
	}

	/**
	 * @param data
	 * @param rowIndex
	 * @param columnIndex
	 */
  	public void setDataInTrajectoryTableModel(Object data, int rowIndex, int columnIndex)
	{
		trajectoriesTableModel.setValueAt(data, rowIndex, columnIndex);		
	}
	/**
	 * @param data
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setDataInSubtrajectoryTableModel(Object data, int rowIndex, int columnIndex)
	{
		subtrajectoriesTableModel.setValueAt(data, rowIndex, columnIndex);		
	}
	
	/**
	 * @param rowData
	 */
	public void addRowInTrajectoryTableModel(Object[] rowData)
	{
		trajectoriesTableModel.addRow(rowData);
	}
	
	/**
	 * @param rowData
	 */
	public void addRowIntSubTrajectoryTableModel(Object[] rowData)
	{
		subtrajectoriesTableModel.addRow(rowData);
	}
	public boolean trajTableModel_selectedRow_isUsar()
	{
		return ((Boolean)trajectoriesTableModel.getValueAt(getTrajectoriesTable_SelectedRow(), 1)).booleanValue();
	}
	public XYCanvas getXYCanvas()
	{
		return canvas_xyTrajs;
	}
	//---------------Subtrajectories Editor Tab
	
	public int jSlider_Manual_SubtraEditor_Zoom_getValue()
	{
		return jSlider_zoom.getValue();
	}
	
	/*public void jPanel_Manual_SubtrajEditor_XYCanvas_show(Canvas canvas)
	{
		jPanel_Corrals_XYCanvas.removeAll();
		jPanel_Corrals_XYCanvas.add(canvas);
		canvas.setBounds(jPanel_Corrals_XYCanvas.getBounds());		
	}*/
	/*public void jPanel_Corrals_XYCanvas_setXY(float[] x, float[] y)
	{
		corralsXYCanvas.setXY(x, y);
	}
	public void jPanel_Corrals_XYCanvas_setXY(float[] x, float[] y, int initialFrame, int finalFrame)
	{
		corralsXYCanvas.setXY(x, y);		
	}*/
	/**
	 * @return
	 */
	public boolean jCheckBox_Manual_SubtrajEditor_ShowGaps_isSelected()
	{
		return jCheckBox_ShowGaps.isSelected();
	}
	public boolean jCheckBox_Manual_SubtrajEditor_ShowSubtraj_isSelected()
	{
		return jCheckBox_ShowSubtraj.isSelected();
	}
	
	/**
	 * @return
	 */
	public boolean jCheckBox_Manual_SubtrajEditor_ShowPoints_isSelected()
	{
		return jCheckBox_ShowPoints.isSelected();
	}
	public boolean jCheckBox_Manual_SubtrajEditor_ShowAllTrajs_isSelected()
	{
		return jCheckBox_ShowAllTrajs.isSelected();
	}
	
	/**
	 * @return
	 */
	public boolean jCheckBox_Manual_SubtrajEditor_ShowInfo_isSelected()
	{
		return jCheckBox_showInfo.isSelected();
	}
	public boolean jCheckBox_ShowVectors_isSelected()
	{
		return jCheckBox_showVectors.isSelected();
	}
	public boolean jCheckBox_Show2State_isSelected()
	{
		return chckbxState.isSelected();
	}
	public void jCheckBox_ShowSubtraj_setSelected(boolean bool)
	{
		jCheckBox_ShowSubtraj.setSelected(bool);
	}
	public void jCheckBox_ShowInfo_setSelected(boolean bool)
	{
		jCheckBox_showInfo.setSelected(bool);
	}
	
	/**
	 * @return
	 */
	public int jSpinner_Manual_SubtrajEditor_getInitialFrame()
	{
		return (Integer)jSpinner_initialFrame.getValue();
	}
	
	/**
	 * @return
	 */
	public int jSpinner_Manual_SubtrajEditor_getFinalFrame()
	{
		return (Integer)jSpinner_finalFrame.getValue();
	}
	
	/**
	 * @param initialFrame
	 * @param finalFrame
	 */
	public void jSpinner_Manual_subtrajEditor_setModel(int initialFrame, int finalFrame)
	{
		jSpinner_initialFrame.setModel(new SpinnerNumberModel(initialFrame, initialFrame, finalFrame, 1));
		jSpinner_finalFrame.setModel(new SpinnerNumberModel(finalFrame, initialFrame, finalFrame, 1));
	}
	
	/**
	 * @param initialFrame
	 * @param finalFrame
	 */
	public void jSlider_Manual_SubtrajEditor_setModel(int initialFrame, int finalFrame)
	{
		jSlider_initialFrame.setMinimum(initialFrame);
		jSlider_initialFrame.setMaximum(finalFrame);
		jSlider_initialFrame.setValue(initialFrame);
		
		jSlider_finalFrame.setMinimum(initialFrame);		
		jSlider_finalFrame.setMaximum(finalFrame);	
		jSlider_finalFrame.setValue(finalFrame);
	}
	public void subtrajsGuiControls_setEnabled(boolean bool)
	{
		this.jLabel_initialFrame.setEnabled(bool);
		this.jLabel_finalFrame.setEnabled(bool);
		this.jSlider_initialFrame.setEnabled(bool);
		this.jSlider_finalFrame.setEnabled(bool);
		this.jSpinner_initialFrame.setEnabled(bool);
		this.jSpinner_finalFrame.setEnabled(bool);
		this.jButton_AddSubtraj.setEnabled(bool);
		this.jButton_ResetSubtrajs.setEnabled(bool);
	}
	public void setSubtrajectorySelectionControlsEnabled(boolean bool)
	{
		jLabel_initialFrame.setEnabled(bool);
		jLabel_finalFrame.setEnabled(bool);
		jSlider_initialFrame.setEnabled(bool);
		jSlider_finalFrame.setEnabled(bool);
		jSpinner_initialFrame.setEnabled(bool);
		jSpinner_finalFrame.setEnabled(bool);
		jLabel.setEnabled(bool);
		jLabel1.setEnabled(bool);
		jButton_AddSubtraj.setEnabled(bool);
		jButton_ResetSubtrajs.setEnabled(bool);		
	}
	
	
	/*---AnyPlots----------------------------------------*/
	//OtrosPlots
	/*------------------------------------------------------------------------------------------*/
	
	
	/*public int getTrajectoryCanvasOption()
	{
		return jTabbedPane_TrajectoryPlots.getSelectedIndex();
	}*/
	/**
	 * 
	 */
/*	public void jTable_Manual_TrajectoriesTable_setRowFilter(MyRowFilter filter)
	{
		TableRowSorter<TableModelTrajectories> sorter = new TableRowSorter<TableModelTrajectories>(trajectoriesTableModel_manual);
		jTable_TrajectoriesTable.setRowSorter(sorter);		
		sorter.setRowFilter(filter);		
	}	*/
	
	/**
	 * 
	 */
	public void jTable_Manual_SubtrajectoriesTable_setRowFilter()
	{
		TableRowSorter<TableModelSubTrajs> sorter = new TableRowSorter<TableModelSubTrajs>(subtrajectoriesTableModel);
		jTable_SubtrajectoriesTable.setRowSorter(sorter);	
	}	
	
	public TableModelTrajectories jTable_Trajectories_getModel()
	{
		return (TableModelTrajectories) jTable_TrajectoriesTable.getModel();
	}
	public TableModelSubTrajs jTable_SubTrajectories_getModel()
	{
		return (TableModelSubTrajs) jTable_SubtrajectoriesTable.getModel();
	}
	
	public int jSpinner_TrajectoryLength_getValue()
	{
		return ((Integer)jSpinner_TrajectoryLength.getValue()).intValue();
	}
	public boolean jCheckBox_NotDefined_isSelected()
	{
		return jCheckBox_NotDefined.isSelected();
	}
	public boolean jCheckBox_Corralled_isSelected()
	{
		return jCheckBox_Corralled.isSelected();
	}
	public boolean jCheckBox_Anomalous_isSelected()
	{
		return jCheckBox_Anomalous.isSelected();
	}
	public boolean jCheckBox_Normal_isSelected()
	{
		return jCheckBox_Normal.isSelected();
	}
	public boolean jCheckBox_Directed_isSelected()
	{
		return jCheckBox_Directed.isSelected();
	}
	
	public int getTrajectoriesTable_SelectedRow()
	{
		return this.trajectoriesTable_selectedRow;
	}
	public int getSubtrajectoriesTable_SelectedRow()
	{
		return this.subtrajectoriesTable_selectedRow;
	}
	public void jTable_TrajectoriesTable_clean()
	{
		((TableModelTrajectories)(jTable_TrajectoriesTable.getModel())).removeAllRows();
	}
	public void jTable_SubtrajectoriesTable_clean()
	{
		((TableModelSubTrajs)(jTable_SubtrajectoriesTable.getModel())).removeAllRows();
	}

	public String jTextField_NewName_getText()
	{
		return txtNewTrajSet.getText();
	}
	/*-----------------------------------------------------------------------------------------*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		this.setSize(750, 650);
		this.setJMenuBar(getJJMenuBar());
		this.setResizable(true);
		this.setPreferredSize(new Dimension(700, 500));
		this.setContentPane(getJContentPane());
		this.setTitle("Single Particle Tracking Analyzer");
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thinker.guiClosing();
				
			}
		});
		/*this.radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(jRadioButton_AutomaticSubtrajs);
		radioButtonGroup.add(jRadioButton_ManualSubtrajs);*/
		
	} 

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setPreferredSize(new Dimension(800, 650));
			GridBagLayout gbl_jContentPane = new GridBagLayout();
			gbl_jContentPane.columnWidths = new int[]{684, 0};
			gbl_jContentPane.rowHeights = new int[]{40, 0, 0};
			gbl_jContentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_jContentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			jContentPane.setLayout(gbl_jContentPane);
			GridBagConstraints gbc_jPanel8 = new GridBagConstraints();
			gbc_jPanel8.insets = new Insets(0, 0, 5, 0);
			gbc_jPanel8.fill = GridBagConstraints.BOTH;
			gbc_jPanel8.gridx = 0;
			gbc_jPanel8.gridy = 1;
			jContentPane.add(getJPanel8(), gbc_jPanel8);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			jContentPane.add(getPanel(), gbc_panel);
		}
		return jContentPane;
	}



	/**
	 * This method initializes jScrollPane_TrajectoriesTable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_TrajectoriesTable() {
		if (jScrollPane_TrajectoriesTable == null) {
			jScrollPane_TrajectoriesTable = new JScrollPane();
			jScrollPane_TrajectoriesTable.setPreferredSize(new Dimension(300, 200));
			jScrollPane_TrajectoriesTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane_TrajectoriesTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane_TrajectoriesTable.setViewportView(getJTable_TrajectoriesTable());
		}
		return jScrollPane_TrajectoriesTable;
	}

	/**
	 * This method initializes jTable_TrajectoriesTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable_TrajectoriesTable() {
		if (jTable_TrajectoriesTable == null) {
			trajectoriesTableModel = new TableModelTrajectories();
			jTable_TrajectoriesTable = new JTable(trajectoriesTableModel);			
			jTable_TrajectoriesTable.setShowGrid(true);
			jTable_TrajectoriesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTable_TrajectoriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable_TrajectoriesTable.setRowHeight(15);
			//jTable_TrajectoriesTable.setPreferredSize(new Dimension(50, 100));
			jTable_TrajectoriesTable.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jTable_TrajectoriesTable.setSize(new Dimension(130, 100));
			jTable_TrajectoriesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
			jTable_TrajectoriesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
			jTable_TrajectoriesTable.getColumnModel().getColumn(2).setPreferredWidth(55);
			jTable_TrajectoriesTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					trajectoriesTable_selectedRow = jTable_TrajectoriesTable.convertRowIndexToModel(jTable_TrajectoriesTable.getSelectedRow());					
					//updateTrajectoryTextAreaInfo("row selected in table is:"+jTable_TrajectoriesTable.getSelectedRow()+"\n");
					//updateTrajectoryTextAreaInfo("row selected in tableModel:"+trajectoriesTable_selectedRow+"\n");
					System.out.println("row selected in table is:"+jTable_TrajectoriesTable.getSelectedRow()+"\n");
					System.out.println("row selected in tableModel:"+trajectoriesTable_selectedRow+"\n");
					thinker.jTable_TrajectoriesTable_clicked();
				}
			});		
			jTable_TrajectoriesTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					trajectoriesTable_selectedRow = jTable_TrajectoriesTable.convertRowIndexToModel(jTable_TrajectoriesTable.getSelectedRow());
					thinker.jTable_TrajectoriesTable_clicked();
				}
			});
			//setNewRowFilter();
		}
		return jTable_TrajectoriesTable;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			GridBagLayout gbl_jPanel8 = new GridBagLayout();
			gbl_jPanel8.columnWidths = new int[]{377};
			gbl_jPanel8.rowWeights = new double[]{1.0};
			gbl_jPanel8.columnWeights = new double[]{1.0};
			jPanel8.setLayout(gbl_jPanel8);
			jPanel8.setPreferredSize(new Dimension(400, 200));
			jPanel8.setMinimumSize(new Dimension(100,10));
			jPanel8.setBorder(new EmptyBorder(0, 0, 0, 0));
			GridBagConstraints gbc_splitPane = new GridBagConstraints();
			gbc_splitPane.fill = GridBagConstraints.BOTH;
			gbc_splitPane.gridx = 0;
			gbc_splitPane.gridy = 0;
			jPanel8.add(getSplitPane(), gbc_splitPane);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jCheckBox_Corralled	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Corralled() {
		if (jCheckBox_Corralled == null) {
			jCheckBox_Corralled = new JCheckBox();
			jCheckBox_Corralled.setEnabled(false);
			jCheckBox_Corralled.setToolTipText("Constrained");
			jCheckBox_Corralled.setSelected(true);
			jCheckBox_Corralled.setText("Cons");
			jCheckBox_Corralled.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Corralled.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//jTable_Manual_TrajectoriesTable_setRowFilter();
					thinker.filters_Trajectories_stateChanged();
				}
			});
		}
		return jCheckBox_Corralled;
	}

	/**
	 * This method initializes jCheckBox_Anomalous	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Anomalous() {
		if (jCheckBox_Anomalous == null) {
			jCheckBox_Anomalous = new JCheckBox();
			jCheckBox_Anomalous.setEnabled(false);
			jCheckBox_Anomalous.setToolTipText("Anomalous");
			jCheckBox_Anomalous.setSelected(true);
			jCheckBox_Anomalous.setText("An");
			jCheckBox_Anomalous.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Anomalous.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//jTable_Manual_TrajectoriesTable_setRowFilter();
					thinker.filters_Trajectories_stateChanged();
				}
			});
		}
		return jCheckBox_Anomalous;
	}

	/**
	 * This method initializes jCheckBox_Normal	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Normal() {
		if (jCheckBox_Normal == null) {
			jCheckBox_Normal = new JCheckBox();
			jCheckBox_Normal.setEnabled(false);
			jCheckBox_Normal.setToolTipText("Normal");
			jCheckBox_Normal.setSelected(true);
			jCheckBox_Normal.setText("Nor");
			jCheckBox_Normal.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Normal.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//jTable_Manual_TrajectoriesTable_setRowFilter();
					thinker.filters_Trajectories_stateChanged();
				}
			});
		}
		return jCheckBox_Normal;
	}

	/**
	 * This method initializes jCheckBox_Directed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Directed() {
		if (jCheckBox_Directed == null) {
			jCheckBox_Directed = new JCheckBox();
			jCheckBox_Directed.setEnabled(false);
			jCheckBox_Directed.setToolTipText("Directed");
			jCheckBox_Directed.setSelected(true);
			jCheckBox_Directed.setText("Dir");
			jCheckBox_Directed.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_Directed.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//jTable_Manual_TrajectoriesTable_setRowFilter();
					thinker.filters_Trajectories_stateChanged();
				}
			});
		}
		return jCheckBox_Directed;
	}

	/**
	 * This method initializes jSpinner_TrajectoryLength	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_TrajectoryLength() {
		if (jSpinner_TrajectoryLength == null) {
			//TODO restringir maximum
			jSpinner_TrajectoryLength = new JSpinner(new SpinnerNumberModel(0,0,99,1));
			jSpinner_TrajectoryLength.setEnabled(false);
			jSpinner_TrajectoryLength.setToolTipText("Length >");
			jSpinner_TrajectoryLength.setPreferredSize(new Dimension(50, 28));
			jSpinner_TrajectoryLength
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							//jTable_Manual_TrajectoriesTable_setRowFilter();
							thinker.filters_Trajectories_stateChanged();
						}
					});
			
		
			
		}
		return jSpinner_TrajectoryLength;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setPreferredSize(new Dimension(250, 200));
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setTopComponent(getJPanel_Manual_Trajectories());
			jSplitPane.setBottomComponent(getJPanel_Manual_Subtrajectories());
			jSplitPane.setDividerSize(10);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel_SubtrajsEditor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_SubtrajsEditor() {
		if (jPanel_SubtrajsEditor == null) {
			GridBagConstraints gridBagConstraints202 = new GridBagConstraints();
			gridBagConstraints202.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints202.gridx = 0;
			gridBagConstraints202.fill = GridBagConstraints.BOTH;
			gridBagConstraints202.weightx = 1.0D;
			gridBagConstraints202.weighty = 1.0D;
			gridBagConstraints202.gridy = 1;
			GridBagConstraints gridBagConstraints195 = new GridBagConstraints();
			gridBagConstraints195.fill = GridBagConstraints.BOTH;
			gridBagConstraints195.gridy = -1;
			gridBagConstraints195.weightx = 0.0D;
			gridBagConstraints195.gridx = -1;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints36.gridy = 0;
			gridBagConstraints36.fill = GridBagConstraints.BOTH;
			gridBagConstraints36.weightx = 0.0D;
			gridBagConstraints36.weighty = 0.0D;
			gridBagConstraints36.gridwidth = 1;
			gridBagConstraints36.gridx = 0;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridx = 1;
			gridBagConstraints28.gridy = 1;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 0;
			gridBagConstraints45.weightx = 1.0D;
			gridBagConstraints45.fill = GridBagConstraints.BOTH;
			gridBagConstraints45.weighty = 0.0D;
			gridBagConstraints45.gridwidth = 1;
			gridBagConstraints45.gridheight = 1;
			gridBagConstraints45.gridy = 2;
			jPanel_SubtrajsEditor = new JPanel();
			jPanel_SubtrajsEditor.setLayout(new GridBagLayout());
			jPanel_SubtrajsEditor.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel_SubtrajsEditor.add(getJPanel_Manual_SubtrajEditor_XYCanvasOptions(), gridBagConstraints36);
			jPanel_SubtrajsEditor.add(getJPanel13(), gridBagConstraints202);
			jPanel_SubtrajsEditor.add(getJPanel_SubtrajAnalysis_Controls(), gridBagConstraints45);
		}
		return jPanel_SubtrajsEditor;
	}



	/**
	 * This method initializes jPanel_MSD_XYCanvas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_MSD_XYCanvas() {
		if (jPanel_MSD_XYCanvas == null) {
			GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
			gridBagConstraints121.weightx = 1.0D;
			gridBagConstraints121.fill = GridBagConstraints.BOTH;
			gridBagConstraints121.gridx = 0;
			gridBagConstraints121.gridy = 0;
			gridBagConstraints121.weighty = 1.0D;
			jPanel_MSD_XYCanvas = new JPanel();
			jPanel_MSD_XYCanvas.setLayout(new GridBagLayout());
			jPanel_MSD_XYCanvas.setPreferredSize(new Dimension(380, 25));
			jPanel_MSD_XYCanvas.setBackground(new Color(102, 102, 255));
			//xyCanvas = new XYCanvas(new float[]{0}, new float[]{0});
			canvas_xyTrajs = new XYCanvas();
			//jPanel_Corrals_XYCanvas.add(xyCanvas, gridBagConstraints121);
			canvas_xyTrajs.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseDragged(java.awt.event.MouseEvent e) {
					thinker.xyCanvas_mouseDragged(e.getX(), e.getY());
				}
			});
			canvas_xyTrajs.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					thinker.xyCanvas_mouseReleased();
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
					thinker.xyCanvas_mousePressed(e.getX(), e.getY());
					thinker.xyCanvas_mouseDragged(e.getX(), e.getY());
				}
			});
			jPanel_MSD_XYCanvas.add(canvas_xyTrajs, gridBagConstraints121);
			//xyCanvas.setBounds(0,0,jPanel_Corrals_XYCanvas.getWidth(),jPanel_Corrals_XYCanvas.getHeight());
			canvas_xyTrajs.setBounds(0,0,jPanel_MSD_XYCanvas.getWidth(),jPanel_MSD_XYCanvas.getHeight());
			//corralsXYCanvas.setXY(new float[]{0,1,2,3,4}, new float[]{0,5,10,10,10});
		}
		return jPanel_MSD_XYCanvas;
	}

	/**
	 * This method initializes jPanel_SubtrajAnalysis_Controls	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_SubtrajAnalysis_Controls() {
		if (jPanel_SubtrajAnalysis_Controls == null) {
			GridBagConstraints gridBagConstraints196 = new GridBagConstraints();
			gridBagConstraints196.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints196.gridy = 0;
			gridBagConstraints196.weightx = 1.0;
			gridBagConstraints196.weighty = 0.0D;
			gridBagConstraints196.gridx = 1;
			GridBagConstraints gridBagConstraints176 = new GridBagConstraints();
			gridBagConstraints176.gridx = 4;
			gridBagConstraints176.fill = GridBagConstraints.BOTH;
			gridBagConstraints176.gridy = 1;
			GridBagConstraints gridBagConstraints106 = new GridBagConstraints();
			gridBagConstraints106.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints106.gridx = 3;
			gridBagConstraints106.fill = GridBagConstraints.BOTH;
			gridBagConstraints106.gridy = 1;
			jLabel1 = new JLabel();
			jLabel1.setText("100");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setEnabled(true);
			jLabel1.setPreferredSize(new Dimension(25, 20));
			GridBagConstraints gridBagConstraints105 = new GridBagConstraints();
			gridBagConstraints105.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints105.gridx = 3;
			gridBagConstraints105.fill = GridBagConstraints.BOTH;
			gridBagConstraints105.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("1");
			jLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setEnabled(true);
			jLabel.setPreferredSize(new Dimension(25, 20));
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints60.gridx = 2;
			gridBagConstraints60.weightx = 0.0D;
			gridBagConstraints60.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints60.gridy = 1;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints59.gridx = 2;
			gridBagConstraints59.weightx = 0.0D;
			gridBagConstraints59.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints59.gridy = 0;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints57.gridx = 4;
			gridBagConstraints57.weightx = 0.0D;
			gridBagConstraints57.weighty = 0.0D;
			gridBagConstraints57.gridheight = 1;
			gridBagConstraints57.fill = GridBagConstraints.BOTH;
			gridBagConstraints57.gridy = 0;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints55.gridx = 0;
			gridBagConstraints55.weightx = 0.0D;
			gridBagConstraints55.fill = GridBagConstraints.BOTH;
			gridBagConstraints55.gridy = 1;
			jLabel_finalFrame = new JLabel();
			jLabel_finalFrame.setText("Final:");
			jLabel_finalFrame.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints48.gridx = 0;
			gridBagConstraints48.weightx = 0.0D;
			gridBagConstraints48.fill = GridBagConstraints.BOTH;
			gridBagConstraints48.gridy = 0;
			jLabel_initialFrame = new JLabel();
			jLabel_initialFrame.setText("Initial:");
			jLabel_initialFrame.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints47.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints47.gridy = 1;
			gridBagConstraints47.weightx = 1.0;
			gridBagConstraints47.gridx = 1;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints46.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints46.gridy = 0;
			gridBagConstraints46.weightx = 1.0;
			gridBagConstraints46.gridx = 1;
			jPanel_SubtrajAnalysis_Controls = new JPanel();
			jPanel_SubtrajAnalysis_Controls.setLayout(new GridBagLayout());
			jPanel_SubtrajAnalysis_Controls.setPreferredSize(new Dimension(300, 70));
			jPanel_SubtrajAnalysis_Controls.setEnabled(true);
			jPanel_SubtrajAnalysis_Controls.add(getJSlider_initialFrame(), gridBagConstraints46);
			jPanel_SubtrajAnalysis_Controls.add(getJSlider_finalFrame(), gridBagConstraints47);
			jPanel_SubtrajAnalysis_Controls.add(jLabel_initialFrame, gridBagConstraints48);
			jPanel_SubtrajAnalysis_Controls.add(jLabel_finalFrame, gridBagConstraints55);
			jPanel_SubtrajAnalysis_Controls.add(getJButton_AddSubtraj(), gridBagConstraints57);
			jPanel_SubtrajAnalysis_Controls.add(getJSpinner_initialFrame(), gridBagConstraints59);
			jPanel_SubtrajAnalysis_Controls.add(getJSpinner_finalFrame(), gridBagConstraints60);
			jPanel_SubtrajAnalysis_Controls.add(jLabel, gridBagConstraints105);
			jPanel_SubtrajAnalysis_Controls.add(jLabel1, gridBagConstraints106);
			jPanel_SubtrajAnalysis_Controls.add(getJButton_ResetSubtrajs(), gridBagConstraints176);
		}
		return jPanel_SubtrajAnalysis_Controls;
	}

	
	
	/**
	 * This method initializes jSlider_initialFrame	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_initialFrame() {
		if (jSlider_initialFrame == null) {
			jSlider_initialFrame = new JSlider();
			jSlider_initialFrame.setSnapToTicks(true);
			jSlider_initialFrame.setPreferredSize(new Dimension(150, 25));
			jSlider_initialFrame.setValue(1);
			jSlider_initialFrame.setPaintTicks(true);
			jSlider_initialFrame.setPaintLabels(true);
			jSlider_initialFrame.setEnabled(false);
			jSlider_initialFrame.setMinimum(1);
			jSlider_initialFrame.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.print("slider initial statechanged");
					jSpinner_initialFrame.setValue(jSlider_initialFrame.getValue());
				}
			});
			
		}
		return jSlider_initialFrame;
	}

	/**
	 * This method initializes jSlider_finalFrame	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_finalFrame() {
		if (jSlider_finalFrame == null) {
			jSlider_finalFrame = new JSlider();
			jSlider_finalFrame.setSnapToTicks(true);
			jSlider_finalFrame.setPreferredSize(new Dimension(150, 25));
			jSlider_finalFrame.setValue(100);
			jSlider_finalFrame.setPaintTicks(true);
			jSlider_finalFrame.setMaximum(100);
			jSlider_finalFrame.setEnabled(false);
			jSlider_finalFrame.setMinimum(1);
			jSlider_finalFrame.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.print("slider final statechanged");
					jSpinner_finalFrame.setValue(jSlider_finalFrame.getValue());
				}
			});
		}
		return jSlider_finalFrame;
	}

	/**
	 * This method initializes jButton_AddSubtraj	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_AddSubtraj() {
		if (jButton_AddSubtraj == null) {
			jButton_AddSubtraj = new JButton();
			jButton_AddSubtraj.setText("Add");
			jButton_AddSubtraj.setEnabled(false);
			jButton_AddSubtraj.setPreferredSize(new Dimension(70, 25));
			jButton_AddSubtraj.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton_Manual_SubTrajEditor_Add_clicked();
				}
			});
		}
		return jButton_AddSubtraj;
	}

	/**
	 * This method initializes jSpinner_initialFrame	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_initialFrame() {
		if (jSpinner_initialFrame == null) {
			jSpinner_initialFrame = new JSpinner(new SpinnerNumberModel(1,1,100,1));
			jSpinner_initialFrame.setPreferredSize(new Dimension(70, 25));
			jSpinner_initialFrame.setEnabled(false);
			jSpinner_initialFrame.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.println("jSpinner initial statechanged");
					thinker.jSpinner_Manual_SubtrajEditor_InitialFinalFrame_stateChanged();
					jSlider_initialFrame.setValue((Integer)(jSpinner_initialFrame.getModel().getValue()));
				}
			});
		}
		return jSpinner_initialFrame;
	}

	/**
	 * This method initializes jSpinner_finalFrame	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	private JSpinner getJSpinner_finalFrame() {
		if (jSpinner_finalFrame == null) {
			jSpinner_finalFrame = new JSpinner(new SpinnerNumberModel(100,1,100,1));
			jSpinner_finalFrame.setPreferredSize(new Dimension(70, 25));
			jSpinner_finalFrame.setEnabled(false);
			jSpinner_finalFrame.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e){
					System.out.println("jSpinner final statechanged");
					thinker.jSpinner_Manual_SubtrajEditor_InitialFinalFrame_stateChanged();		
					jSlider_finalFrame.setValue((Integer)(jSpinner_finalFrame.getModel().getValue()));
				}
			});
			
		}
		return jSpinner_finalFrame;
	}

	/**
	 * This method initializes jScrollPane_SubtrajectoriesTable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_SubtrajectoriesTable() {
		if (jScrollPane_SubtrajectoriesTable == null) {
			jScrollPane_SubtrajectoriesTable = new JScrollPane();
			jScrollPane_SubtrajectoriesTable.setPreferredSize(new Dimension(25, 25));
			jScrollPane_SubtrajectoriesTable.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jScrollPane_SubtrajectoriesTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane_SubtrajectoriesTable.setViewportView(getJTable_SubtrajectoriesTable());
		}
		return jScrollPane_SubtrajectoriesTable;
	}

	/**
	 * This method initializes jTable_SubtrajectoriesTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable_SubtrajectoriesTable() {
		if (jTable_SubtrajectoriesTable == null) {
			
			subtrajectoriesTableModel = new TableModelSubTrajs();
			jTable_SubtrajectoriesTable = new JTable(subtrajectoriesTableModel);
			jTable_SubtrajectoriesTable.setShowGrid(true);
			jTable_SubtrajectoriesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTable_SubtrajectoriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable_SubtrajectoriesTable.setRowHeight(15);
			jTable_SubtrajectoriesTable.setFont(new Font("SansSerif", Font.PLAIN, 10));
			jTable_SubtrajectoriesTable.setSize(new Dimension(100, 100));
			jTable_SubtrajectoriesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
			jTable_SubtrajectoriesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
			jTable_SubtrajectoriesTable.getColumnModel().getColumn(2).setPreferredWidth(30);

			jTable_SubtrajectoriesTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					subtrajectoriesTable_selectedRow = jTable_SubtrajectoriesTable.convertRowIndexToModel(jTable_SubtrajectoriesTable.getSelectedRow());					
					//updateTrajectoryTextAreaInfo("row selected in table is:"+jTable_SubtrajectoriesTable.getSelectedRow()+"\n");
					//updateTrajectoryTextAreaInfo("row selected in tableModel:"+subtrajectoriesTable_selectedRow+"\n");
					IJ.log("row selected in table is:"+jTable_SubtrajectoriesTable.getSelectedRow()+"\n");
					IJ.log("row selected in tableModel:"+subtrajectoriesTable_selectedRow+"\n");
					thinker.jTable_SubtrajectoriesTable_clicked();
				}
			});
						
			TableRowSorter<TableModelSubTrajs> sorter = new TableRowSorter<TableModelSubTrajs>(subtrajectoriesTableModel);
			jTable_SubtrajectoriesTable.setRowSorter(sorter);			
		}
		return jTable_SubtrajectoriesTable;
	}

	/**
	 * This method initializes jCheckBox_Manual_SubtrajEditor_ShowGaps	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_SubtrajEditor_ShowGaps() {
		if (jCheckBox_ShowGaps == null) {
			jCheckBox_ShowGaps = new JCheckBox();
			jCheckBox_ShowGaps.setEnabled(false);
			jCheckBox_ShowGaps.setText("Gaps");
			jCheckBox_ShowGaps.setVisible(false);
			jCheckBox_ShowGaps
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							thinker.jCheckBox_Manual_SubtrajEditor_ShowGaps_itemStateChanged();
						}
					});
		}
		return jCheckBox_ShowGaps;
	}

	/**
	 * This method initializes jCheckBox_Manual_Multiple	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_NotDefined() {
		if (jCheckBox_NotDefined == null) {
			jCheckBox_NotDefined = new JCheckBox();
			jCheckBox_NotDefined.setEnabled(false);
			jCheckBox_NotDefined.setToolTipText("Not Defined");
			jCheckBox_NotDefined.setText("ND");
			jCheckBox_NotDefined.setHorizontalAlignment(SwingConstants.LEFT);
			jCheckBox_NotDefined.setSelected(true);
			jCheckBox_NotDefined.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//jTable_Manual_TrajectoriesTable_setRowFilter();
					thinker.filters_Trajectories_stateChanged();
				}
			});
		}
		return jCheckBox_NotDefined;
	}

	/**
	 * This method initializes jCheckBox_Manual_SubtrajEditor_ShowPoints	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_SubtrajEditor_ShowPoints() {
		if (jCheckBox_ShowPoints == null) {
			jCheckBox_ShowPoints = new JCheckBox();
			jCheckBox_ShowPoints.setEnabled(false);
			jCheckBox_ShowPoints.setText("Points");
			jCheckBox_ShowPoints
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							thinker.jCheckBox_Manual_SubtrajEditor_ShowPoints_itemStateChanged();
						}
					});
		}
		return jCheckBox_ShowPoints;
	}

	/**
	 * This method initializes jPanel_Manual_SubtrajEditor_XYCanvasOptions	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Manual_SubtrajEditor_XYCanvasOptions() {
		if (jPanel_Manual_SubtrajEditor_XYCanvasOptions == null) {
			GridBagConstraints gridBagConstraints200 = new GridBagConstraints();
			gridBagConstraints200.gridx = 1;
			gridBagConstraints200.fill = GridBagConstraints.BOTH;
			gridBagConstraints200.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints200.gridy = 0;
			GridBagConstraints gridBagConstraints142 = new GridBagConstraints();
			gridBagConstraints142.gridx = 0;
			gridBagConstraints142.fill = GridBagConstraints.BOTH;
			gridBagConstraints142.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints142.gridy = 0;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 2;
			gridBagConstraints35.fill = GridBagConstraints.BOTH;
			gridBagConstraints35.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints35.gridy = 0;
			GridBagConstraints gridBagConstraints104 = new GridBagConstraints();
			gridBagConstraints104.fill = GridBagConstraints.BOTH;
			gridBagConstraints104.gridy = 0;
			gridBagConstraints104.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints104.gridx = 3;
			jPanel_Manual_SubtrajEditor_XYCanvasOptions = new JPanel();
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.setLayout(new GridBagLayout());
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.setPreferredSize(new Dimension(25, 25));
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_Manual_SubtrajEditor_ShowGaps(), gridBagConstraints104);
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_Manual_SubtrajEditor_ShowPoints(), gridBagConstraints35);
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_Manual_SubtrajsEditor_showCorrals(), gridBagConstraints142);
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_ShowAllTrajs(), gridBagConstraints200);
			GridBagConstraints gridBagConstraints198 = new GridBagConstraints();
			gridBagConstraints198.gridx = 4;
			gridBagConstraints198.fill = GridBagConstraints.BOTH;
			gridBagConstraints198.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints198.gridy = 0;
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_Manual_SubtrajsEditor_showSubtrajs(), gridBagConstraints198);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints2.gridx = 5;
			gridBagConstraints2.gridy = 0;
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getJCheckBox_showVectors(), gridBagConstraints2);
			GridBagConstraints gbc_chckbxState = new GridBagConstraints();
			gbc_chckbxState.gridx = 6;
			gbc_chckbxState.gridy = 0;
			jPanel_Manual_SubtrajEditor_XYCanvasOptions.add(getChckbxState(), gbc_chckbxState);
		}
		return jPanel_Manual_SubtrajEditor_XYCanvasOptions;
	}

	/**
	 * This method initializes jCheckBox_Manual_SubtrajsEditor_showCorrals	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_SubtrajsEditor_showCorrals() {
		if (jCheckBox_showInfo == null) {
			jCheckBox_showInfo = new JCheckBox();
			jCheckBox_showInfo.setEnabled(false);
			jCheckBox_showInfo.setText("Info");
			jCheckBox_showInfo.setSelected(true);
			jCheckBox_showInfo
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							thinker.jCheckBox_Manual_SubtrajEditor_ShowInfo_itemStateChanged();
						}
					});
		}
		return jCheckBox_showInfo;
	}

	/**
	 * This method initializes jPanel_Manual_Trajectories	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Manual_Trajectories() {
		if (jPanel_Manual_Trajectories == null) {
			TitledBorder titledBorder8 = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Trajectories", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59));
			titledBorder8.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel_Manual_Trajectories = new JPanel();
			jPanel_Manual_Trajectories.setPreferredSize(new Dimension(100, 250));
			jPanel_Manual_Trajectories.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Trajectories", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_jPanel_Manual_Trajectories = new GridBagLayout();
			gbl_jPanel_Manual_Trajectories.columnWidths = new int[]{25, 0};
			gbl_jPanel_Manual_Trajectories.rowHeights = new int[]{80, 0};
			gbl_jPanel_Manual_Trajectories.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_jPanel_Manual_Trajectories.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			jPanel_Manual_Trajectories.setLayout(gbl_jPanel_Manual_Trajectories);
			GridBagConstraints gbc_jScrollPane_TrajectoriesTable = new GridBagConstraints();
			gbc_jScrollPane_TrajectoriesTable.weighty = 1.0;
			gbc_jScrollPane_TrajectoriesTable.weightx = 1.0;
			gbc_jScrollPane_TrajectoriesTable.fill = GridBagConstraints.BOTH;
			gbc_jScrollPane_TrajectoriesTable.gridx = 0;
			gbc_jScrollPane_TrajectoriesTable.gridy = 0;
			jPanel_Manual_Trajectories.add(getJScrollPane_TrajectoriesTable(), gbc_jScrollPane_TrajectoriesTable);
		}
		return jPanel_Manual_Trajectories;
	}

	/**
	 * This method initializes jPanel_Manual_Subtrajectories	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Manual_Subtrajectories() {
		if (jPanel_Manual_Subtrajectories == null) {
			TitledBorder titledBorder4 = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Subtrajectories", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59));
			titledBorder4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			GridBagConstraints gridBagConstraints138 = new GridBagConstraints();
			gridBagConstraints138.fill = GridBagConstraints.BOTH;
			gridBagConstraints138.gridy = 0;
			gridBagConstraints138.weightx = 1.0;
			gridBagConstraints138.weighty = 1.0;
			gridBagConstraints138.gridx = 0;
			jPanel_Manual_Subtrajectories = new JPanel();
			jPanel_Manual_Subtrajectories.setLayout(new GridBagLayout());
			jPanel_Manual_Subtrajectories.setPreferredSize(new Dimension(100, 250));
			jPanel_Manual_Subtrajectories.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Subtrajs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			jPanel_Manual_Subtrajectories.add(getJScrollPane_SubtrajectoriesTable(), gridBagConstraints138);
		}
		return jPanel_Manual_Subtrajectories;
	}

	/**
	 * This method initializes jButton_ResetSubtrajs	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_ResetSubtrajs() {
		if (jButton_ResetSubtrajs == null) {
			jButton_ResetSubtrajs = new JButton();
			jButton_ResetSubtrajs.setText("Reset");
			jButton_ResetSubtrajs.setEnabled(false);
			jButton_ResetSubtrajs.setPreferredSize(new Dimension(70, 25));
			jButton_ResetSubtrajs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton_Manual_SubTrajEditor_Reset_clicked();
				}
			});
		}
		return jButton_ResetSubtrajs;
	}

	/**
	 * This method initializes jCheckBox_Manual_SubtrajsEditor_showSubtrajs	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_Manual_SubtrajsEditor_showSubtrajs() {
		if (jCheckBox_ShowSubtraj == null) {
			jCheckBox_ShowSubtraj = new JCheckBox();
			jCheckBox_ShowSubtraj.setEnabled(false);
			jCheckBox_ShowSubtraj.setText("Subtraj");
			jCheckBox_ShowSubtraj.setSelected(true);
			jCheckBox_ShowSubtraj
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							thinker.jCheckBox_Manual_SubtrajEditor_ShowSubtraj_itemStateChanged();
						}
					});
		}
		return jCheckBox_ShowSubtraj;
	}

	/**
	 * This method initializes jSlider_Manual_SubtrajEditor_Zoom	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider_Manual_SubtrajEditor_Zoom() {
		if (jSlider_zoom == null) {
			jSlider_zoom = new JSlider();
			jSlider_zoom.setEnabled(false);
			jSlider_zoom.setMajorTickSpacing(1);
			jSlider_zoom.setPreferredSize(new Dimension(25, 150));
			jSlider_zoom.setPaintTicks(false);
			jSlider_zoom.setPaintLabels(false);
			jSlider_zoom.setMaximum(100);
			jSlider_zoom.setMinimum(1);
			jSlider_zoom.setOrientation(JSlider.VERTICAL);
			jSlider_zoom.setSnapToTicks(true);
			jSlider_zoom.setValue(1);
			jSlider_zoom
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							thinker.jSlider_Manual_SubtraEditor_Zoom_stateChanged();
						}
					});
		}
		return jSlider_zoom;
	}

	/**
	 * This method initializes jCheckBox_ShowAllTrajs	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_ShowAllTrajs() {
		if (jCheckBox_ShowAllTrajs == null) {
			jCheckBox_ShowAllTrajs = new JCheckBox();
			jCheckBox_ShowAllTrajs.setEnabled(false);
			jCheckBox_ShowAllTrajs.setText("All trajs");
			jCheckBox_ShowAllTrajs.setSelected(true);
			jCheckBox_ShowAllTrajs.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					thinker.jCheckBox_Manual_SubtrajEditor_ShowAllTrajs_itemStateChanged();
				}
			});
		}
		return jCheckBox_ShowAllTrajs;
	}

	/**
	 * This method initializes jPanel13	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			GridBagConstraints gridBagConstraints203 = new GridBagConstraints();
			gridBagConstraints203.gridx = 0;
			gridBagConstraints203.fill = GridBagConstraints.BOTH;
			gridBagConstraints203.gridy = 0;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.fill = GridBagConstraints.BOTH;
			gridBagConstraints29.gridx = 1;
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.weightx = 1.0D;
			gridBagConstraints29.weighty = 1.0D;
			gridBagConstraints29.gridwidth = 1;
			jPanel13 = new JPanel();
			jPanel13.setLayout(new GridBagLayout());
			jPanel13.setPreferredSize(new Dimension(300, 150));
			jPanel13.add(getJPanel14(), gridBagConstraints203);
			jPanel13.add(getJPanel_MSD_XYCanvas(), gridBagConstraints29);
		}
		return jPanel13;
	}

	/**
	 * This method initializes jPanel14	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel14() {
		if (jPanel14 == null) {
			GridBagConstraints gridBagConstraints199 = new GridBagConstraints();
			gridBagConstraints199.fill = GridBagConstraints.BOTH;
			gridBagConstraints199.gridy = -1;
			gridBagConstraints199.weightx = 1.0;
			gridBagConstraints199.weighty = 1.0D;
			gridBagConstraints199.gridx = -1;
			jPanel14 = new JPanel();
			jPanel14.setLayout(new GridBagLayout());
			jPanel14.add(getJSlider_Manual_SubtrajEditor_Zoom(), gridBagConstraints199);
		}
		return jPanel14;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu3());
			jJMenuBar.add(getJMenu2());
			jJMenuBar.add(getJMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("Analysis");
			jMenu.add(getJMenuItem4());
			jMenu.add(getJMenuItem5());
			jMenu.add(getJMenuItem11());
			jMenu.add(getJMenuItem14());
			jMenu.add(getMntmDirection());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenu2	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu2() {
		if (jMenu2 == null) {
			jMenu2 = new JMenu();
			jMenu2.setText("Data");
			jMenu2.add(getJMenuItem1());
			jMenu2.add(getJMenuItem2());
			jMenu2.add(getMntmSimulateTrajectories());
			jMenu2.add(getJSeparator1());
			jMenu2.add(getJMenuItem8());
			jMenu2.add(getMntmDataInfo());
		}
		return jMenu2;
	}

	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("Particle Tracker");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_ParticleTracker_clicked();
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jMenu3	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu3() {
		if (jMenu3 == null) {
			jMenu3 = new JMenu();
			jMenu3.setText("SPT Analyzer");
			jMenu3.add(getJMenuItem9());
			jMenu3.add(getJMenuItem6());
		}
		return jMenu3;
	}

	/**
	 * This method initializes jMenuItem2	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("Loader/Saver");
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_LoadSave();
				}
			});
		}
		return jMenuItem2;
	}

	/**
	 * This method initializes jMenuItem4	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("Type of motion");
			jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_TypeOfMotionAnalysis();
				}
			});
		}
		return jMenuItem4;
	}

	/**
	 * This method initializes jMenuItem5	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("2-state Diffusion");
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_2StateDiffusion();
				}
			});
		}
		return jMenuItem5;
	}

	/**
	 * This method initializes jMenuItem6	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem();
			jMenuItem6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.jMenu_About_BrauchiLab_clicked();
				}
			});
			jMenuItem6.setText("About...");
			
		}
		return jMenuItem6;
	}

	/**
	 * This method initializes jMenuItem8	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem8() {
		if (jMenuItem8 == null) {
			jMenuItem8 = new JMenuItem();
			jMenuItem8.setText("Set Metrics");
			jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_SetMetrics();
				}
			});
		}
		return jMenuItem8;
	}

	/**
	 * This method initializes jMenuItem9	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem9() {
		if (jMenuItem9 == null) {
			jMenuItem9 = new JMenuItem();
			jMenuItem9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.jMenu_Preferences_clicked();
				}
			});
			jMenuItem9.setText("Preferences");
		}
		return jMenuItem9;
	}

	/**
	 * This method initializes jMenuItem11	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem11() {
		if (jMenuItem11 == null) {
			jMenuItem11 = new JMenuItem();
			jMenuItem11.setText("Over Time");
			jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_OverTime();
				}
			});
		}
		return jMenuItem11;
	}

	/**
	 * This method initializes jSeparator1	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator1() {
		if (jSeparator1 == null) {
			jSeparator1 = new JSeparator();
		}
		return jSeparator1;
	}

	/**
	 * This method initializes jCheckBox_showVectors	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_showVectors() {
		if (jCheckBox_showVectors == null) {
			jCheckBox_showVectors = new JCheckBox();
			jCheckBox_showVectors.setEnabled(false);
			jCheckBox_showVectors.setText("Vectors");
			jCheckBox_showVectors.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					thinker.jCheckBox_Manual_SubtrajEditor_ShowVectors_itemStateChanged();
				}
			});
		}
		return jCheckBox_showVectors;
	}

	/**
	 * This method initializes jMenuItem14	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem14() {
		if (jMenuItem14 == null) {
			jMenuItem14 = new JMenuItem();
			jMenuItem14.setText("Histograms");
			jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jMenu_Histograms();
				}
			});
		}
		return jMenuItem14;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			jLabel3 = new JLabel();
			jLabel3.setText("User type:");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 4;
			gridBagConstraints7.gridy = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setPreferredSize(new Dimension(25, 25));
			jPanel.add(getJButton(), gridBagConstraints4);
			jPanel.add(getJButton1(), gridBagConstraints5);
			jPanel.add(getJButton2(), gridBagConstraints6);
			jPanel.add(getJButton3(), gridBagConstraints7);
			jPanel.add(jLabel3, gridBagConstraints8);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setEnabled(false);
			jButton.setText("1");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton1_clicked();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setEnabled(false);
			jButton1.setText("2");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton2_clicked();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setEnabled(false);
			jButton2.setText("3");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton3_clicked();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setEnabled(false);
			jButton3.setText("4");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thinker.jButton4_clicked();
				}
			});
		}
		return jButton3;
	}

	

	private JMenuItem getMntmSimulateTrajectories() {
		if (mntmSimulateTrajectories == null) {
			mntmSimulateTrajectories = new JMenuItem("Simulator");
			mntmSimulateTrajectories.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					thinker.jMenu_SimulateTrajectories();
					
				}
			});
		}
		return mntmSimulateTrajectories;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{79, 140, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{26, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_lblCurrentSet = new GridBagConstraints();
			gbc_lblCurrentSet.insets = new Insets(0, 0, 0, 5);
			gbc_lblCurrentSet.anchor = GridBagConstraints.EAST;
			gbc_lblCurrentSet.gridx = 0;
			gbc_lblCurrentSet.gridy = 0;
			panel.add(getLblCurrentSet(), gbc_lblCurrentSet);
			GridBagConstraints gbc_comboBox_Sets = new GridBagConstraints();
			gbc_comboBox_Sets.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_Sets.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox_Sets.gridx = 1;
			gbc_comboBox_Sets.gridy = 0;
			panel.add(getComboBox_Sets(), gbc_comboBox_Sets);
			GridBagConstraints gbc_txtNewTrajSet = new GridBagConstraints();
			gbc_txtNewTrajSet.insets = new Insets(0, 0, 0, 5);
			gbc_txtNewTrajSet.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtNewTrajSet.gridx = 2;
			gbc_txtNewTrajSet.gridy = 0;
			panel.add(getTxtNewTrajSet(), gbc_txtNewTrajSet);
			GridBagConstraints gbc_btnRenameSet = new GridBagConstraints();
			gbc_btnRenameSet.insets = new Insets(0, 0, 0, 5);
			gbc_btnRenameSet.gridx = 3;
			gbc_btnRenameSet.gridy = 0;
			panel.add(getBtnRenameSet(), gbc_btnRenameSet);
			GridBagConstraints gbc_btnDelete = new GridBagConstraints();
			gbc_btnDelete.gridx = 4;
			gbc_btnDelete.gridy = 0;
			panel.add(getBtnDelete(), gbc_btnDelete);
		}
		return panel;
	}
	private JComboBox getComboBox_Sets() {
		if (comboBox_Sets == null) {
			comboBox_Sets = new JComboBox(new String[]{"No set loaded"});
			comboBox_Sets.setEnabled(false);
			comboBox_Sets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.jComboBox_CurrentSet_stateChanged();
				}
			});
		}
		return comboBox_Sets;
	}
	private JLabel getLblCurrentSet() {
		if (lblCurrentSet == null) {
			lblCurrentSet = new JLabel("Current Set:");
		}
		return lblCurrentSet;
	}
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setLeftComponent(getPanel_1());
			splitPane.setRightComponent(getJPanel_SubtrajsEditor());
			splitPane.setDividerLocation(250);
		}
		return splitPane;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setPreferredSize(new Dimension(50, 50));
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{0, 0};
			gbl_panel_1.rowHeights = new int[]{64, 369, 44, 0};
			gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_jSplitPane = new GridBagConstraints();
			gbc_jSplitPane.fill = GridBagConstraints.BOTH;
			gbc_jSplitPane.insets = new Insets(0, 0, 5, 0);
			gbc_jSplitPane.gridx = 0;
			gbc_jSplitPane.gridy = 1;
			panel_1.add(getJSplitPane(), gbc_jSplitPane);
			GridBagConstraints gbc_jPanel = new GridBagConstraints();
			gbc_jPanel.fill = GridBagConstraints.BOTH;
			gbc_jPanel.insets = new Insets(0, 0, 5, 0);
			gbc_jPanel.gridx = 0;
			gbc_jPanel.gridy = 2;
			panel_1.add(getJPanel(), gbc_jPanel);
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.fill = GridBagConstraints.BOTH;
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 0;
			panel_1.add(getPanel_2(), gbc_panel_2);
		}
		return panel_1;
	}
	private JMenuItem getMntmDataInfo() {
		if (mntmDataInfo == null) {
			mntmDataInfo = new JMenuItem("Data Info");
			mntmDataInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					thinker.jMenu_Info_clicked();
				}
			});
		}
		return mntmDataInfo;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[]{51, 0, 0, 0, 0, 0};
			gbl_panel_2.rowHeights = new int[]{0, 0, 0};
			gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_2.setLayout(gbl_panel_2);
			GridBagConstraints gbc_panel_3 = new GridBagConstraints();
			gbc_panel_3.gridwidth = 5;
			gbc_panel_3.insets = new Insets(0, 0, 5, 0);
			gbc_panel_3.fill = GridBagConstraints.BOTH;
			gbc_panel_3.gridx = 0;
			gbc_panel_3.gridy = 0;
			panel_2.add(getPanel_3(), gbc_panel_3);
			GridBagConstraints gbc_jCheckBox_NotDefined = new GridBagConstraints();
			gbc_jCheckBox_NotDefined.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_NotDefined.gridx = 0;
			gbc_jCheckBox_NotDefined.gridy = 1;
			panel_2.add(getJCheckBox_NotDefined(), gbc_jCheckBox_NotDefined);
			GridBagConstraints gbc_jCheckBox_Normal = new GridBagConstraints();
			gbc_jCheckBox_Normal.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_Normal.gridx = 1;
			gbc_jCheckBox_Normal.gridy = 1;
			panel_2.add(getJCheckBox_Normal(), gbc_jCheckBox_Normal);
			GridBagConstraints gbc_jCheckBox_Anomalous = new GridBagConstraints();
			gbc_jCheckBox_Anomalous.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_Anomalous.gridx = 2;
			gbc_jCheckBox_Anomalous.gridy = 1;
			panel_2.add(getJCheckBox_Anomalous(), gbc_jCheckBox_Anomalous);
			GridBagConstraints gbc_jCheckBox_Corralled = new GridBagConstraints();
			gbc_jCheckBox_Corralled.insets = new Insets(0, 0, 0, 5);
			gbc_jCheckBox_Corralled.gridx = 3;
			gbc_jCheckBox_Corralled.gridy = 1;
			panel_2.add(getJCheckBox_Corralled(), gbc_jCheckBox_Corralled);
			GridBagConstraints gbc_jCheckBox_Directed = new GridBagConstraints();
			gbc_jCheckBox_Directed.gridx = 4;
			gbc_jCheckBox_Directed.gridy = 1;
			panel_2.add(getJCheckBox_Directed(), gbc_jCheckBox_Directed);
		}
		return panel_2;
	}
	private JTextField getTxtNewTrajSet() {
		if (txtNewTrajSet == null) {
			txtNewTrajSet = new JTextField();
			txtNewTrajSet.setText("set1");
			txtNewTrajSet.setColumns(10);
		}
		return txtNewTrajSet;
	}
	private JButton getBtnRenameSet() {
		if (btnRenameSet == null) {
			btnRenameSet = new JButton("Rename set");
			btnRenameSet.setEnabled(false);
			btnRenameSet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.jButton_RenameTrajSet_clicked();
				}
			});
		}
		return btnRenameSet;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("Delete");
			btnDelete.setEnabled(false);
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.jButton_DeleteSet_clicked();
				}
			});
		}
		return btnDelete;
	}
	private JButton getBtnDeleteTraj() {
		if (btnDeleteTraj == null) {
			btnDeleteTraj = new JButton("Delete Traj");
			btnDeleteTraj.setEnabled(false);
			btnDeleteTraj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					thinker.jButton_DeleteTraj_clicked();
				}
			});
		}
		return btnDeleteTraj;
	}
	private JCheckBox getChckbxState() {
		if (chckbxState == null) {
			chckbxState = new JCheckBox("2 State");
			chckbxState.setEnabled(false);
			chckbxState.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					thinker.jCheckBox_Show2StateMode_itemStateChanged();
				}
			});
		}
		return chckbxState;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			jLabel2 = new JLabel();
			panel_3.add(jLabel2);
			jLabel2.setText("Length>=");
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_3.add(getJSpinner_TrajectoryLength());
			panel_3.add(getBtnDeleteTraj());
		}
		return panel_3;
	}
	private JMenuItem getMntmDirection() {
		if (mntmDirection == null) {
			mntmDirection = new JMenuItem("Direction");
			mntmDirection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					thinker.jMenu_Direction();
				}
			});
		}
		return mntmDirection;
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private Action getAction() {
		if (action == null) {
			action = new SwingAction();
		}
		return action;
	}
}  //  @jve:decl-index=0:visual-constraint="10,9"
