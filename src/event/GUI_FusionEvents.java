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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;


public class GUI_FusionEvents extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private Thinker thinker;
	private TableModel_FE tablemodel_fe;

	private JPanel contentPane;
	private JPanel panel_table;
	
	private int table_selectedRow;
	private JScrollPane scrollPane;
	private JTable table;
	
	private ChartPanel cp_intVsTime;
	private ChartPanel cp_histogram;
	private JButton btnDeleteSelectedEvent;
	private JPanel panel_info;
	private JPanel panel_plot;
	private JPanel panel_histogram;
	private JPanel panel_manualSearch;


	/**
	 * Create the frame.
	 */
	public GUI_FusionEvents(Thinker thinker) {
		super();
		this.thinker = thinker;
		initialize();
		setTitle("Fusion Events");
		setVisible(false);
	}
	
	public void addRowInFETableModel(Object[] rowData)
	{
		tablemodel_fe.addRow(rowData);
	}

	public void automaticRowSelection(int index){
		table.setRowSelectionInterval(index, index);
	}
	
	
	private void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowHeights = new int[] {200, 200, 200, 200};
		gbl_contentPane.columnWidths = new int[] {400, 400};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panel_table = new GridBagConstraints();
		gbc_panel_table.gridheight = 2;
		gbc_panel_table.insets = new Insets(0, 0, 5, 5);
		gbc_panel_table.gridx = 0;
		gbc_panel_table.gridy = 0;
		contentPane.add(getPanel_table(), gbc_panel_table);
		GridBagConstraints gbc_panel_info = new GridBagConstraints();
		gbc_panel_info.insets = new Insets(0, 0, 5, 0);
		gbc_panel_info.fill = GridBagConstraints.BOTH;
		gbc_panel_info.gridx = 1;
		gbc_panel_info.gridy = 0;
		contentPane.add(getPanel_info(), gbc_panel_info);
		GridBagConstraints gbc_panel_plot = new GridBagConstraints();
		gbc_panel_plot.insets = new Insets(0, 0, 5, 0);
		gbc_panel_plot.gridheight = 2;
		gbc_panel_plot.fill = GridBagConstraints.BOTH;
		gbc_panel_plot.gridx = 1;
		gbc_panel_plot.gridy = 1;
		contentPane.add(getPanel_plot(), gbc_panel_plot);
		GridBagConstraints gbc_panel_histogram = new GridBagConstraints();
		gbc_panel_histogram.gridheight = 2;
		gbc_panel_histogram.insets = new Insets(0, 0, 5, 5);
		gbc_panel_histogram.fill = GridBagConstraints.BOTH;
		gbc_panel_histogram.gridx = 0;
		gbc_panel_histogram.gridy = 2;
		contentPane.add(getPanel_histogram(), gbc_panel_histogram);
		GridBagConstraints gbc_panel_manualSearch = new GridBagConstraints();
		gbc_panel_manualSearch.fill = GridBagConstraints.BOTH;
		gbc_panel_manualSearch.gridx = 1;
		gbc_panel_manualSearch.gridy = 3;
		contentPane.add(getPanel_manualSearch(), gbc_panel_manualSearch);

	}
	private JPanel getPanel_table() {
		if (panel_table == null) {
			panel_table = new JPanel();
			panel_table.setBorder(new TitledBorder(null, "Events Detected", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_table = new GridBagLayout();
			gbl_panel_table.rowHeights = new int[] {200, 50};
			gbl_panel_table.columnWidths = new int[] {400};
			gbl_panel_table.columnWeights = new double[]{1.0};
			gbl_panel_table.rowWeights = new double[]{1.0, 1.0};
			panel_table.setLayout(gbl_panel_table);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			panel_table.add(getScrollPane(), gbc_scrollPane);
			GridBagConstraints gbc_btnDeleteSelectedEvent = new GridBagConstraints();
			gbc_btnDeleteSelectedEvent.gridx = 0;
			gbc_btnDeleteSelectedEvent.gridy = 1;
			panel_table.add(getBtnDeleteSelectedEvent(), gbc_btnDeleteSelectedEvent);
			
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
				public void mouseReleased(MouseEvent e) {
					table_selectedRow = table.convertRowIndexToModel(table.getSelectedRow());					
					System.out.println("Activé el evento"+table_selectedRow);
					thinker.showEventInfo(table_selectedRow);
					thinker.showEventSelectedInCanvas(table_selectedRow);
				}
			});	
			table.addKeyListener(new java.awt.event.KeyAdapter(){
				public void keyReleased(java.awt.event.KeyEvent e) {
					table_selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
					thinker.showEventInfo(table_selectedRow);
					thinker.showEventSelectedInCanvas(table_selectedRow);
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
	
	/*public ChartPanel getHistogramChartPanel(){
		if (cp_histogram==null)
			cp_histogram=new ChartPanel(new DataHistogram(new CombinedDomainXYPlot()));
		return cp_histogram;
	}*/
	
	public IntensityVsTimeChart getJFreeChartIntVsTime()
	{
		return (IntensityVsTimeChart) cp_intVsTime.getChart();
	}
	
	public void startFeDetection(int fitPatchSize, int timeBetweenFrames){
		
		thinker.getFusionEvents().fusionEvents(fitPatchSize,timeBetweenFrames);
		
		setVisible(true);
		
	}
	
	public void deleteRow(int eventIndex){
		System.out.println("Voy a borrar la fila: "+eventIndex);
		((TableModel_FE)table.getModel()).removeRow(eventIndex);

	}
	private JButton getBtnDeleteSelectedEvent() {
		if (btnDeleteSelectedEvent == null) {
			btnDeleteSelectedEvent = new JButton("Delete selected event");
			btnDeleteSelectedEvent.setPreferredSize(new Dimension(150,30));
			btnDeleteSelectedEvent.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					thinker.deleteSelectedEvent(table_selectedRow);
				}
			});
		}
		return btnDeleteSelectedEvent;
	}
	
	public void plotHistogram(double[] dataArray, int nClasses){
		   HistogramDataset dataset = new HistogramDataset();
	       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	       dataset.addSeries("Tau Histogram",dataArray,nClasses);
	       String plotTitle = "Tau Histogram"; 
	       String xaxis = "number";
	       String yaxis = "value"; 
	       PlotOrientation orientation = PlotOrientation.VERTICAL; 
	       boolean show = true; 
	       boolean toolTips = true;
	       boolean urls = false; 
	       GridBagConstraints gbc= new GridBagConstraints();
		   gbc.gridx = 0;
		   gbc.gridy = 0;
	       JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	                dataset, orientation, show, toolTips, urls);
	       ChartPanel cp=new ChartPanel(chart);
	       //cp.setPreferredSize(new Dimension(300,450));
	       panel_histogram.add(cp,gbc);
	       panel_histogram.setVisible(true);
	       panel_histogram.setVisible(true);
	}
	
	private JPanel getPanel_info() {
		if (panel_info == null) {
			panel_info = new JPanel();
			panel_info.setBorder(new TitledBorder(null, "Event Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return panel_info;
	}
	
	private JPanel getPanel_plot() {
		if (panel_plot == null) {
			panel_plot = new JPanel();
			panel_plot.setBorder(new TitledBorder(null, "Event Intensities Plot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_plot.add(getIntVsTimeChartPanel());
		}
		return panel_plot;
	}
	
	private JPanel getPanel_histogram() {
		if (panel_histogram == null) {
			panel_histogram = new JPanel();
			panel_histogram.setBorder(new TitledBorder(null, "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return panel_histogram;
	}
	
	private JPanel getPanel_manualSearch() {
		if (panel_manualSearch == null) {
			panel_manualSearch = new JPanel();
			panel_manualSearch.setBorder(new TitledBorder(null, "Manual Event Searching", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		return panel_manualSearch;
	}
}
