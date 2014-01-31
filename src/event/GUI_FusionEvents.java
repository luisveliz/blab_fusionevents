package event;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;

import javax.swing.JTable;

import main.Thinker;

import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CombinedDomainXYPlot;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


public class GUI_FusionEvents extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private Thinker thinker;
	private TableModel_FE tablemodel_fe;

	private JPanel contentPane;
	private JPanel panel_right;
	private JScrollPane scrollPane_textArea;
	private JPanel panel_chart;
	private JPanel panel_table;
	
	private int table_selectedRow;
	private JScrollPane scrollPane;
	private JTable table;
	
	private ChartPanel cp_intVsTime;
	
	private FusionEvents fe_controller;


	/**
	 * Create the frame.
	 */
	public GUI_FusionEvents(Thinker thinker) {
		super();
		this.thinker = thinker;
		setVisible(true);
		setTitle("Fusion Events");
		
		
		
		
		initialize();
		
		
		
	}
	
	public void addRowInFETableModel(Object[] rowData)
	{
		tablemodel_fe.addRow(rowData);
	}
	
	
	
	private void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{336, 230, 0};
		gbl_contentPane.rowHeights = new int[]{260, -33, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panel_right = new GridBagConstraints();
		gbc_panel_right.insets = new Insets(0, 0, 5, 0);
		gbc_panel_right.fill = GridBagConstraints.BOTH;
		gbc_panel_right.gridx = 1;
		gbc_panel_right.gridy = 0;
		contentPane.add(getPanel_right(), gbc_panel_right);
		GridBagConstraints gbc_panel_table = new GridBagConstraints();
		gbc_panel_table.insets = new Insets(0, 0, 5, 5);
		gbc_panel_table.fill = GridBagConstraints.BOTH;
		gbc_panel_table.gridx = 0;
		gbc_panel_table.gridy = 0;
		contentPane.add(getPanel_table(), gbc_panel_table);
	}

	private JPanel getPanel_right() {
		if (panel_right == null) {
			panel_right = new JPanel();
			
			GridBagLayout gbl_panel_right = new GridBagLayout();
			gbl_panel_right.columnWidths = new int[]{0, 0};
			gbl_panel_right.rowHeights = new int[]{0, 0, 0};
			gbl_panel_right.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_right.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			panel_right.setLayout(gbl_panel_right);
			
			GridBagConstraints gbc_scrollPane_textArea = new GridBagConstraints();
			gbc_scrollPane_textArea.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane_textArea.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_textArea.gridx = 0;
			gbc_scrollPane_textArea.gridy = 0;
			panel_right.add(getScrollPane_textArea(), gbc_scrollPane_textArea);
			
			GridBagConstraints gbc_panel_chart = new GridBagConstraints();
			gbc_panel_chart.fill = GridBagConstraints.BOTH;
			gbc_panel_chart.gridx = 0;
			gbc_panel_chart.gridy = 1;
			panel_right.add(getPanel_chart(), gbc_panel_chart);
		}
		return panel_right;
	}
	private JScrollPane getScrollPane_textArea() {
		if (scrollPane_textArea == null) {
			scrollPane_textArea = new JScrollPane();
			scrollPane_textArea.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return scrollPane_textArea;
	}
	private JPanel getPanel_chart() {
		if (panel_chart == null) {
			panel_chart = new JPanel();
			panel_chart.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Intensity vs Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_chart = new GridBagLayout();
			gbl_panel_chart.columnWidths = new int[]{0};
			gbl_panel_chart.rowHeights = new int[]{0};
			gbl_panel_chart.columnWeights = new double[]{Double.MIN_VALUE};
			gbl_panel_chart.rowWeights = new double[]{Double.MIN_VALUE};
			panel_chart.setLayout(gbl_panel_chart);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			panel_chart.add(getIntVsTimeChartPanel(),gbc_scrollPane);
		}
		return panel_chart;
	}
	private JPanel getPanel_table() {
		if (panel_table == null) {
			panel_table = new JPanel();
			panel_table.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_table = new GridBagLayout();
			gbl_panel_table.columnWidths = new int[]{0, 0};
			gbl_panel_table.rowHeights = new int[]{0, 0};
			gbl_panel_table.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel_table.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panel_table.setLayout(gbl_panel_table);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			panel_table.add(getScrollPane(), gbc_scrollPane);
			
		}
		return panel_table;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			tablemodel_fe=new TableModel_FE();
			table = new JTable(tablemodel_fe);
			table.setFillsViewportHeight(true);
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					table_selectedRow = table.convertRowIndexToModel(table.getSelectedRow());					
					//updateTrajectoryTextAreaInfo("row selected in table is:"+jTable_TrajectoriesTable.getSelectedRow()+"\n");
					//updateTrajectoryTextAreaInfo("row selected in tableModel:"+trajectoriesTable_selectedRow+"\n");
					System.out.println("Activé el evento"+table_selectedRow);
					thinker.showEventInfo(table_selectedRow);
					thinker.showEventSelectedInCanvas(table_selectedRow);
					//thinker.jTable_TrajectoriesTable_clicked();
				}
			});		
		}
		return table;
	}
	
	public ChartPanel getIntVsTimeChartPanel()
	{
		if(cp_intVsTime==null)
			cp_intVsTime = new ChartPanel(new IntensityVsTimeChart(new CombinedDomainXYPlot()));
		return cp_intVsTime;
			
	}
	
	public IntensityVsTimeChart getJFreeChartIntVsTime()
	{
		return (IntensityVsTimeChart) cp_intVsTime.getChart();
	}
	
	
}
