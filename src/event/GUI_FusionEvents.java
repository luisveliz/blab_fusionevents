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

import bTools.BMaths;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.DropMode;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class GUI_FusionEvents extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private Thinker thinker;
	private TableModel_FE tablemodel_fe;

	private JPanel contentPane;
	private JPanel panel_table;
	
	private int table_selectedRow=-1;
	private JScrollPane scrollPane;
	private JTable table;
	
	private ChartPanel cp_intVsTime;
	private ChartPanel cp_histogram;
	private JButton btnDeleteSelectedEvent;
	private JPanel panel_info;
	private JPanel panel_plot;
	private JPanel panel_histogram;
	private JPanel panel_manualSearch;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private JLabel lblInitialRadiusX;
	private JLabel lblEndingRadiusX;
	private JLabel lblInitialRadiusY;
	private JLabel lblEndingRadiusY;
	private JLabel startTime;
	private JTextArea lblStatisticsInfo;
	private JTextArea lblNumClasses;
	private JSpinner numberClassesSpinner;
	private JButton btnNewButton;
	private JSplitPane splitPane;
	private JPanel panel_generalStats;
	private JPanel panel_dataHistogram;
	private JLabel lblNumberOfEvents;
	private JLabel lblShortestEvent;
	private JLabel lblLongestEvent;
	private JLabel lblAvgDurationtau;
	private JLabel lblMinIntensityIncrease;
	private JLabel lblMaxIntensityIncrease;
	private JLabel lblAvgIntensityIncrease;
	private JLabel NumberOfEvents;
	private JLabel ShortestEvent;
	private JLabel LongestEvent;
	private JLabel AvgDurationtau;
	private JLabel MinIntensityIncrease;
	private JLabel MaxIntensityIncrease;
	private JLabel AvgIntensityIncrease;
	private JTextArea lblManualInfo;
	
	int nClassesHistogram=1;
	private JLabel endTime;
	private JLabel initialRadiusX;
	private JLabel initialRadiusY;
	private JButton btnStartManualSearch;


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
		setBounds(100,100,800,600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowHeights = new int[] {150, 150, 150, 150};
		gbl_contentPane.columnWidths = new int[] {500, 300};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panel_table = new GridBagConstraints();
		gbc_panel_table.anchor = GridBagConstraints.NORTH;
		gbc_panel_table.weighty = 1.0;
		gbc_panel_table.weightx = 1.0;
		gbc_panel_table.fill = GridBagConstraints.BOTH;
		gbc_panel_table.gridwidth=1;
		gbc_panel_table.gridheight = 2;
		gbc_panel_table.insets = new Insets(0, 0, 5, 5);
		gbc_panel_table.gridx = 0;
		gbc_panel_table.gridy = 0;
		contentPane.add(getPanel_table(), gbc_panel_table);
		GridBagConstraints gbc_panel_info = new GridBagConstraints();
		gbc_panel_info.anchor = GridBagConstraints.NORTH;
		gbc_panel_info.insets = new Insets(0, 0, 5, 0);
		gbc_panel_info.fill = GridBagConstraints.BOTH;
		gbc_panel_info.gridx = 1;
		gbc_panel_info.gridy = 0;
		contentPane.add(getPanel_info(), gbc_panel_info);
		GridBagConstraints gbc_panel_plot = new GridBagConstraints();
		gbc_panel_plot.anchor = GridBagConstraints.NORTH;
		gbc_panel_plot.insets = new Insets(0, 0, 5, 0);
		gbc_panel_plot.gridheight = 2;
		gbc_panel_plot.fill = GridBagConstraints.BOTH;
		gbc_panel_plot.gridx = 1;
		gbc_panel_plot.gridy = 1;
		contentPane.add(getPanel_plot(), gbc_panel_plot);
		GridBagConstraints gbc_panel_histogram = new GridBagConstraints();
		gbc_panel_histogram.anchor = GridBagConstraints.NORTH;
		gbc_panel_histogram.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_histogram.gridheight = 2;
		gbc_panel_histogram.insets = new Insets(0, 0, 5, 5);
		gbc_panel_histogram.gridx = 0;
		gbc_panel_histogram.gridy = 2;
		contentPane.add(getPanel_histogram(), gbc_panel_histogram);
		GridBagConstraints gbc_panel_manualSearch = new GridBagConstraints();
		gbc_panel_manualSearch.anchor = GridBagConstraints.NORTH;
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
			gbl_panel_table.columnWidths = new int[] {350};
			gbl_panel_table.rowHeights = new int[] {250, 50};
			gbl_panel_table.columnWeights = new double[]{1.0};
			gbl_panel_table.rowWeights = new double[]{1.0, 1.0};
			panel_table.setLayout(gbl_panel_table);
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.weighty = 1.0;
			gbc_scrollPane.weightx = 1.0;
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
	
	public void startFeDetection(int fitPatchSize, int timeBetweenFrames,double minIntIncrease){
		
		thinker.getFusionEvents().fusionEvents(fitPatchSize,timeBetweenFrames,minIntIncrease);
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
	
	public void plotHistogram(double[] dataArray){
		   HistogramDataset dataset = new HistogramDataset();
	       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	       dataset.addSeries("Tau Histogram",dataArray,nClassesHistogram);
	       String plotTitle = "Tau Histogram"; 
	       String xaxis = "Tau";
	       String yaxis = "Frequency";
	       PlotOrientation orientation = PlotOrientation.VERTICAL; 
	       boolean show = true; 
	       boolean toolTips = true;
	       boolean urls = false; 
	       JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	                dataset, orientation, show, toolTips, urls);
	       GridBagConstraints gbc= new GridBagConstraints();
		   gbc.gridx = 0;
		   gbc.gridy = 0;
	       ChartPanel cp=new ChartPanel(chart);
	       gbc.gridheight=1;
	       gbc.gridwidth=1;
	       gbc.fill=GridBagConstraints.BOTH;
	       cp.setPreferredSize(new Dimension(250,150));
	       panel_dataHistogram.add(cp,gbc);
	       panel_dataHistogram.setVisible(true);
	}
	
	private JPanel getPanel_info() {
		if (panel_info == null) {
			panel_info = new JPanel();
			panel_info.setBorder(new TitledBorder(null, "Event Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_info = new GridBagLayout();
			gbl_panel_info.columnWidths = new int[]{150, 50, 150, 50};
			gbl_panel_info.rowHeights = new int[]{50, 50, 50, 50};
			gbl_panel_info.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
			gbl_panel_info.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
			panel_info.setLayout(gbl_panel_info);
			GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
			gbc_lblStartTime.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblStartTime.insets = new Insets(10, 0, 5, 5);
			gbc_lblStartTime.gridx = 0;
			gbc_lblStartTime.gridy = 0;
			panel_info.add(getLblStartTime(), gbc_lblStartTime);
			GridBagConstraints gbc_lblEndTime = new GridBagConstraints();
			gbc_lblEndTime.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblEndTime.insets = new Insets(10, 0, 5, 5);
			gbc_lblEndTime.gridx = 2;
			gbc_lblEndTime.gridy = 0;
			panel_info.add(getLblEndTime(), gbc_lblEndTime);
			GridBagConstraints gbc_endTime = new GridBagConstraints();
			gbc_endTime.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_endTime.insets = new Insets(10, 0, 5, 0);
			gbc_endTime.gridx = 3;
			gbc_endTime.gridy = 0;
			panel_info.add(getEndTime(), gbc_endTime);
			GridBagConstraints gbc_lblInitialRadiusX = new GridBagConstraints();
			gbc_lblInitialRadiusX.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblInitialRadiusX.insets = new Insets(10, 0, 5, 5);
			gbc_lblInitialRadiusX.gridx = 0;
			gbc_lblInitialRadiusX.gridy = 1;
			panel_info.add(getLblInitialRadiusX(), gbc_lblInitialRadiusX);
			GridBagConstraints gbc_initialRadiusX = new GridBagConstraints();
			gbc_initialRadiusX.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_initialRadiusX.insets = new Insets(10, 0, 5, 5);
			gbc_initialRadiusX.gridx = 1;
			gbc_initialRadiusX.gridy = 1;
			panel_info.add(getInitialRadiusX(), gbc_initialRadiusX);
			GridBagConstraints gbc_lblEndingRadiusX = new GridBagConstraints();
			gbc_lblEndingRadiusX.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblEndingRadiusX.insets = new Insets(10, 0, 5, 5);
			gbc_lblEndingRadiusX.gridx = 2;
			gbc_lblEndingRadiusX.gridy = 1;
			panel_info.add(getLblEndingRadiusX(), gbc_lblEndingRadiusX);
			GridBagConstraints gbc_lblInitialRadiusY = new GridBagConstraints();
			gbc_lblInitialRadiusY.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblInitialRadiusY.insets = new Insets(10, 0, 5, 5);
			gbc_lblInitialRadiusY.gridx = 0;
			gbc_lblInitialRadiusY.gridy = 2;
			panel_info.add(getLblInitialRadiusY(), gbc_lblInitialRadiusY);
			GridBagConstraints gbc_startTime = new GridBagConstraints();
			gbc_startTime.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_startTime.insets = new Insets(10, 0, 5, 5);
			gbc_startTime.gridx = 1;
			gbc_startTime.gridy = 0;
			panel_info.add(getStartTime(), gbc_startTime);
			GridBagConstraints gbc_initialRadiusY = new GridBagConstraints();
			gbc_initialRadiusY.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_initialRadiusY.insets = new Insets(10, 0, 5, 5);
			gbc_initialRadiusY.gridx = 1;
			gbc_initialRadiusY.gridy = 2;
			panel_info.add(getInitialRadiusY(), gbc_initialRadiusY);
			GridBagConstraints gbc_lblEndingRadiusY = new GridBagConstraints();
			gbc_lblEndingRadiusY.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblEndingRadiusY.insets = new Insets(10, 0, 5, 5);
			gbc_lblEndingRadiusY.gridx = 2;
			gbc_lblEndingRadiusY.gridy = 2;
			panel_info.add(getLblEndingRadiusY(), gbc_lblEndingRadiusY);
		}
		return panel_info;
	}
	
	private JPanel getPanel_plot() {
		if (panel_plot == null) {
			panel_plot = new JPanel();
			panel_plot.setBorder(new TitledBorder(null, "Event Intensities Plot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_plot.add(getIntVsTimeChartPanel());
			GridBagLayout gbl_panel_plot = new GridBagLayout();
			gbl_panel_plot.columnWidths = new int[]{0};
			gbl_panel_plot.rowHeights = new int[]{0};
			gbl_panel_plot.columnWeights = new double[]{Double.MIN_VALUE};
			gbl_panel_plot.rowWeights = new double[]{Double.MIN_VALUE};
			panel_plot.setLayout(gbl_panel_plot);
			GridBagConstraints gbc_plotChart=new GridBagConstraints();
			gbc_plotChart.anchor=GridBagConstraints.NORTH;
			gbc_plotChart.gridx=0;
			gbc_plotChart.gridy=0;
			gbc_plotChart.fill=GridBagConstraints.BOTH;
			panel_plot.add(getIntVsTimeChartPanel(),gbc_plotChart);
		}
		return panel_plot;
	}
	
	private JPanel getPanel_histogram() {
		if (panel_histogram == null) {
			panel_histogram = new JPanel();
			panel_histogram.setBorder(new TitledBorder(null, "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_histogram = new GridBagLayout();
			gbl_panel_histogram.columnWidths = new int[]{200,50,150};
			gbl_panel_histogram.rowHeights = new int[]{60,60,150};
			gbl_panel_histogram.columnWeights = new double[]{1.0,1.0,1.0};
			gbl_panel_histogram.rowWeights = new double[]{1.0,1.0,1.0};
			panel_histogram.setLayout(gbl_panel_histogram);
			GridBagConstraints gbc_lblStatisticsInfo = new GridBagConstraints();
			gbc_lblStatisticsInfo.insets = new Insets(5, 0, 0, 0);
			gbc_lblStatisticsInfo.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblStatisticsInfo.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblStatisticsInfo.gridx = 0;
			gbc_lblStatisticsInfo.gridy = 0;
			gbc_lblStatisticsInfo.gridwidth=3;
			gbc_lblStatisticsInfo.gridheight=1;
			panel_histogram.add(getLblStatisticsInfo(), gbc_lblStatisticsInfo);
			GridBagConstraints gbc_lblNumClasses = new GridBagConstraints();
			gbc_lblNumClasses.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblNumClasses.insets = new Insets(0, 0, 0, 5);
			gbc_lblNumClasses.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblNumClasses.gridx = 0;
			gbc_lblNumClasses.gridy = 1;
			panel_histogram.add(getLblNumClasses(), gbc_lblNumClasses);
			GridBagConstraints gbc_numberClassesSpinner = new GridBagConstraints();
			gbc_numberClassesSpinner.anchor = GridBagConstraints.BASELINE;
			gbc_numberClassesSpinner.insets = new Insets(0, 0, 5, 5);
			gbc_numberClassesSpinner.gridx = 1;
			gbc_numberClassesSpinner.gridy = 1;
			panel_histogram.add(getNumberClassesSpinner(), gbc_numberClassesSpinner);
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton.anchor = GridBagConstraints.BASELINE;
			gbc_btnNewButton.gridx = 2;
			gbc_btnNewButton.gridy = 1;
			panel_histogram.add(getBtnNewButton(), gbc_btnNewButton);
			GridBagConstraints gbc_splitPane = new GridBagConstraints();
			gbc_splitPane.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_splitPane.insets = new Insets(0, 0, 0, 5);
			gbc_splitPane.fill = GridBagConstraints.BOTH;
			gbc_splitPane.gridx = 0;
			gbc_splitPane.gridy = 2;
			gbc_splitPane.gridwidth=3;
			panel_histogram.add(getSplitPane(), gbc_splitPane);
		}
		return panel_histogram;
	}
	
	private JPanel getPanel_manualSearch() {
		if (panel_manualSearch == null) {
			panel_manualSearch = new JPanel();
			panel_manualSearch.setBorder(new TitledBorder(null, "Manual Event Searching", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_manualSearch = new GridBagLayout();
			gbl_panel_manualSearch.columnWidths = new int[]{250, 150};
			gbl_panel_manualSearch.rowHeights = new int[]{50};
			gbl_panel_manualSearch.columnWeights = new double[]{1.0, 1.0};
			gbl_panel_manualSearch.rowWeights = new double[]{1.0};
			panel_manualSearch.setLayout(gbl_panel_manualSearch);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.gridx=0;
			gbc.gridy=0;
			gbc.gridheight=1;
			gbc.gridwidth=1;
			gbc.insets=new Insets(5, 0, 0, 5);
			panel_manualSearch.add(getManualSearchInfo(),gbc);
			GridBagConstraints gbc_btnStartManualSearch = new GridBagConstraints();
			gbc_btnStartManualSearch.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnStartManualSearch.anchor = GridBagConstraints.BELOW_BASELINE;
			gbc_btnStartManualSearch.gridx = 1;
			gbc_btnStartManualSearch.gridy = 0;
			panel_manualSearch.add(getBtnStartManualSearch(), gbc_btnStartManualSearch);
		}
		return panel_manualSearch;
	}
	
	public IntensityVsTimeChart getCurrentIntVsTimeChart(){
		if (table_selectedRow>=0) return (IntensityVsTimeChart) cp_intVsTime.getChart();
		else return null;
	}
	
	public int getSelectedRow(){
		return table_selectedRow;
	}
	private JLabel getLblStartTime() {
		if (lblStartTime == null) {
			lblStartTime = new JLabel("Start time:");			
			lblStartTime.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblStartTime;
	}
	private JLabel getLblEndTime() {
		if (lblEndTime == null) {
			lblEndTime = new JLabel("End Time:");
			lblEndTime.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblEndTime;
	}
	
	
	public void setEndTime(double time){
		double precision=3.0;
		double roundedTime=BMaths.roundDouble(time, precision);
		endTime.setText(Double.toString(roundedTime));
	}
	
	private JLabel getLblInitialRadiusX() {
		if (lblInitialRadiusX == null) {
			lblInitialRadiusX = new JLabel("Initial radius x axis:");
			lblInitialRadiusX.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblInitialRadiusX;
	}
	
	public void setInitialRadiusX(double radX){
		double precision=3.0;
		double roundedRad=BMaths.roundDouble(radX, precision);
		initialRadiusX.setText(Double.toString(roundedRad));
	}
	
	private JLabel getLblEndingRadiusX() {
		if (lblEndingRadiusX == null) {
			lblEndingRadiusX = new JLabel("Final radius x axis:");
			lblEndingRadiusX.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return lblEndingRadiusX;
	}
	private JLabel getLblInitialRadiusY() {
		if (lblInitialRadiusY == null) {
			lblInitialRadiusY = new JLabel("Initial radius y axis:");
		}
		return lblInitialRadiusY;
	}
	
	public void setInitialRadiusY(double radY){
		double precision=3.0;
		double roundedRad=BMaths.roundDouble(radY, precision);
		initialRadiusY.setText(Double.toString(roundedRad));
	}
	
	private JLabel getLblEndingRadiusY() {
		if (lblEndingRadiusY == null) {
			lblEndingRadiusY = new JLabel("Final radius y axis:");
		}
		return lblEndingRadiusY;
	}
	private JLabel getStartTime() {
		if (startTime == null) {
			startTime = new JLabel("");
		}
		return startTime;
	}
	
	public void setStartTime(double start){
		startTime.setText(Double.toString((double)Math.round(start*1000)/1000));
	}
	
	public void clearStartTime(){
		startTime.setText("");
	}
	private JTextArea getLblStatisticsInfo() {
		if (lblStatisticsInfo == null) {
			lblStatisticsInfo = new JTextArea();
			lblStatisticsInfo.setFont(new Font("Dialog", Font.PLAIN, 11));
			lblStatisticsInfo.setText("If you are ready with the movie fusion events detection, you can generate statistics about the events data");
			lblStatisticsInfo.setEditable(false);
			lblStatisticsInfo.setFocusable(false);
			lblStatisticsInfo.setLineWrap(true);
			lblStatisticsInfo.setWrapStyleWord(true);
			lblStatisticsInfo.setBorder(null);
			lblStatisticsInfo.setBackground(new Color(0,0,0,0));
		}
		return lblStatisticsInfo;
	}
	private JTextArea getLblNumClasses() {
		if (lblNumClasses == null) {
			lblNumClasses = new JTextArea();
			lblNumClasses.setFont(new Font("Dialog", Font.PLAIN, 11));
			lblNumClasses.setText("Number of classes clustering the events according to the exponential decay tau");
			lblNumClasses.setEditable(false);
			lblNumClasses.setFocusable(false);
			lblNumClasses.setLineWrap(true);
			lblNumClasses.setWrapStyleWord(true);
			lblNumClasses.setBorder(null);
			lblNumClasses.setBackground(new Color(0,0,0,0));
			
			
		}
		return lblNumClasses;
	}
	private JSpinner getNumberClassesSpinner() {
		if (numberClassesSpinner == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 15, 1);
			numberClassesSpinner = new JSpinner(model);
			numberClassesSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					nClassesHistogram=(Integer)numberClassesSpinner.getValue();
				}
			});
		}
		return numberClassesSpinner;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Generate Statistics");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					thinker.generateStatistics();
				}
			});
		}
		return btnNewButton;
	}
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setLeftComponent(getPanel_generalStats());
			splitPane.setRightComponent(getPanel_dataHistogram());
			splitPane.setDividerLocation(100);
		}
		return splitPane;
	}
	private JPanel getPanel_generalStats() {
		if (panel_generalStats == null) {
			panel_generalStats = new JPanel();
			GridBagLayout gbl_panel_generalStats = new GridBagLayout();
			gbl_panel_generalStats.columnWidths = new int[]{100,50};
			gbl_panel_generalStats.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel_generalStats.columnWeights = new double[]{1.0, 1.0};
			gbl_panel_generalStats.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
			panel_generalStats.setLayout(gbl_panel_generalStats);
			GridBagConstraints gbc_lblNumberOfEvents = new GridBagConstraints();
			gbc_lblNumberOfEvents.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblNumberOfEvents.insets = new Insets(5, 0, 5, 5);
			gbc_lblNumberOfEvents.gridx = 0;
			gbc_lblNumberOfEvents.gridy = 0;
			panel_generalStats.add(getLblNumberOfEvents(), gbc_lblNumberOfEvents);
			GridBagConstraints gbc_NumberOfEvents = new GridBagConstraints();
			gbc_NumberOfEvents.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_NumberOfEvents.fill = GridBagConstraints.BOTH;
			gbc_NumberOfEvents.insets = new Insets(5, 0, 5, 0);
			gbc_NumberOfEvents.gridx = 1;
			gbc_NumberOfEvents.gridy = 0;
			panel_generalStats.add(getNumberOfEvents(), gbc_NumberOfEvents);
			GridBagConstraints gbc_lblShortestEvent = new GridBagConstraints();
			gbc_lblShortestEvent.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblShortestEvent.insets = new Insets(5, 0, 5, 5);
			gbc_lblShortestEvent.gridx = 0;
			gbc_lblShortestEvent.gridy = 1;
			panel_generalStats.add(getLblShortestEvent(), gbc_lblShortestEvent);
			GridBagConstraints gbc_ShortestEvent = new GridBagConstraints();
			gbc_ShortestEvent.fill = GridBagConstraints.BOTH;
			gbc_ShortestEvent.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_ShortestEvent.insets = new Insets(5, 0, 5, 0);
			gbc_ShortestEvent.gridx = 1;
			gbc_ShortestEvent.gridy = 1;
			panel_generalStats.add(getShortestEvent(), gbc_ShortestEvent);
			GridBagConstraints gbc_lblLongestEvent = new GridBagConstraints();
			gbc_lblLongestEvent.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblLongestEvent.insets = new Insets(5, 0, 5, 5);
			gbc_lblLongestEvent.gridx = 0;
			gbc_lblLongestEvent.gridy = 2;
			panel_generalStats.add(getLblLongestEvent(), gbc_lblLongestEvent);
			GridBagConstraints gbc_LongestEvent = new GridBagConstraints();
			gbc_LongestEvent.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_LongestEvent.insets = new Insets(5, 0, 5, 0);
			gbc_LongestEvent.gridx = 1;
			gbc_LongestEvent.gridy = 2;
			panel_generalStats.add(getLongestEvent(), gbc_LongestEvent);
			GridBagConstraints gbc_lblAvgDurationtau = new GridBagConstraints();
			gbc_lblAvgDurationtau.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblAvgDurationtau.insets = new Insets(5, 0, 5, 5);
			gbc_lblAvgDurationtau.gridx = 0;
			gbc_lblAvgDurationtau.gridy = 3;
			panel_generalStats.add(getLblAvgDurationtau(), gbc_lblAvgDurationtau);
			GridBagConstraints gbc_AvgDurationtau = new GridBagConstraints();
			gbc_AvgDurationtau.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_AvgDurationtau.insets = new Insets(5, 0, 5, 0);
			gbc_AvgDurationtau.gridx = 1;
			gbc_AvgDurationtau.gridy = 3;
			panel_generalStats.add(getAvgDurationtau(), gbc_AvgDurationtau);
			GridBagConstraints gbc_lblMinIntensityIncrease = new GridBagConstraints();
			gbc_lblMinIntensityIncrease.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblMinIntensityIncrease.insets = new Insets(5, 0, 5, 5);
			gbc_lblMinIntensityIncrease.gridx = 0;
			gbc_lblMinIntensityIncrease.gridy = 4;
			panel_generalStats.add(getLblMinIntensityIncrease(), gbc_lblMinIntensityIncrease);
			GridBagConstraints gbc_MinIntensityIncrease = new GridBagConstraints();
			gbc_MinIntensityIncrease.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_MinIntensityIncrease.insets = new Insets(5, 0, 5, 0);
			gbc_MinIntensityIncrease.gridx = 1;
			gbc_MinIntensityIncrease.gridy = 4;
			panel_generalStats.add(getMinIntensityIncrease(), gbc_MinIntensityIncrease);
			GridBagConstraints gbc_lblMaxIntensityIncrease = new GridBagConstraints();
			gbc_lblMaxIntensityIncrease.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_lblMaxIntensityIncrease.insets = new Insets(5, 0, 5, 5);
			gbc_lblMaxIntensityIncrease.gridx = 0;
			gbc_lblMaxIntensityIncrease.gridy = 5;
			panel_generalStats.add(getLblMaxIntensityIncrease(), gbc_lblMaxIntensityIncrease);
			GridBagConstraints gbc_MaxIntensityIncrease = new GridBagConstraints();
			gbc_MaxIntensityIncrease.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_MaxIntensityIncrease.insets = new Insets(5, 0, 5, 0);
			gbc_MaxIntensityIncrease.gridx = 1;
			gbc_MaxIntensityIncrease.gridy = 5;
			panel_generalStats.add(getMaxIntensityIncrease(), gbc_MaxIntensityIncrease);
			GridBagConstraints gbc_lblAvgIntensityIncrease = new GridBagConstraints();
			gbc_lblAvgIntensityIncrease.insets = new Insets(5, 0, 5, 5);
			gbc_lblAvgIntensityIncrease.gridx = 0;
			gbc_lblAvgIntensityIncrease.gridy = 6;
			panel_generalStats.add(getLblAvgIntensityIncrease(), gbc_lblAvgIntensityIncrease);
			GridBagConstraints gbc_AvgIntensityIncrease = new GridBagConstraints();
			gbc_AvgIntensityIncrease.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_AvgIntensityIncrease.insets = new Insets(5, 0, 5, 0);
			gbc_AvgIntensityIncrease.gridx = 1;
			gbc_AvgIntensityIncrease.gridy = 6;
			panel_generalStats.add(getAvgIntensityIncrease(), gbc_AvgIntensityIncrease);
		}
		return panel_generalStats;
	}
	private JPanel getPanel_dataHistogram() {
		if (panel_dataHistogram == null) {
			panel_dataHistogram = new JPanel();
		}
		return panel_dataHistogram;
	}
	private JLabel getLblNumberOfEvents() {
		if (lblNumberOfEvents == null) {
			lblNumberOfEvents = new JLabel("Number of events:");
		}
		return lblNumberOfEvents;
	}
	
	public void setNumberOfEvents(int numberEv){
		NumberOfEvents.setText(Integer.toString(numberEv));
	}
	
	public void clearNumberOfEvents(){
		NumberOfEvents.setText("");
	}
	
	private JLabel getLblShortestEvent() {
		if (lblShortestEvent == null) {
			lblShortestEvent = new JLabel("Shortest event (tau):");
		}
		return lblShortestEvent;
	}
	
	public void setShortestEvent(double tau){
		ShortestEvent.setText(Double.toString(tau));
	}
	
	public void clearShortestEvent(){
		ShortestEvent.setText("");
	}
	
	private JLabel getLblLongestEvent() {
		if (lblLongestEvent == null) {
			lblLongestEvent = new JLabel("Longest event (tau):");
		}
		return lblLongestEvent;
	}
	
	public void setLongestEvent(double tau){
		LongestEvent.setText(Double.toString(tau));
	}
	
	public void clearLongestEvent(){
		LongestEvent.setText("");
	}
	
	private JLabel getLblAvgDurationtau() {
		if (lblAvgDurationtau == null) {
			lblAvgDurationtau = new JLabel("Avg duration (tau):");
		}
		return lblAvgDurationtau;
	}
	
	public void setAvgDuration(double tau){
		AvgDurationtau.setText(Double.toString(tau));
	}
	
	public void clearAvgDuration(){
		AvgDurationtau.setText("");
	}
	
	private JLabel getLblMinIntensityIncrease() {
		if (lblMinIntensityIncrease == null) {
			lblMinIntensityIncrease = new JLabel("Min intensity increase:");
		}
		return lblMinIntensityIncrease;
	}
	
	public void setMinIncrease(double inc){
		MinIntensityIncrease.setText(Double.toString(inc));
	}
	
	public void clearMinIncrease(){
		MinIntensityIncrease.setText("");
	}
	
	private JLabel getLblMaxIntensityIncrease() {
		if (lblMaxIntensityIncrease == null) {
			lblMaxIntensityIncrease = new JLabel("Max intensity increase:");
		}
		return lblMaxIntensityIncrease;
	}
	
	public void setMaxIncrease(double inc){
		MaxIntensityIncrease.setText(Double.toString(inc));
	}
	
	public void clearMaxIncrease(){
		MaxIntensityIncrease.setText("");
	}
	
	private JLabel getLblAvgIntensityIncrease() {
		if (lblAvgIntensityIncrease == null) {
			lblAvgIntensityIncrease = new JLabel("Avg intensity increase:");
		}
		return lblAvgIntensityIncrease;
	}
	
	public void setAvgIncrease(double inc){
		AvgIntensityIncrease.setText(Double.toString(inc));
	}
	
	public void clearAvgIncrease(){
		AvgIntensityIncrease.setText("");
	}
	private JLabel getNumberOfEvents() {
		if (NumberOfEvents == null) {
			NumberOfEvents = new JLabel("");
		}
		return NumberOfEvents;
	}
	private JLabel getShortestEvent() {
		if (ShortestEvent == null) {
			ShortestEvent = new JLabel("");
		}
		return ShortestEvent;
	}
	private JLabel getLongestEvent() {
		if (LongestEvent == null) {
			LongestEvent = new JLabel("");
		}
		return LongestEvent;
	}
	private JLabel getAvgDurationtau() {
		if (AvgDurationtau == null) {
			AvgDurationtau = new JLabel("");
		}
		return AvgDurationtau;
	}
	private JLabel getMinIntensityIncrease() {
		if (MinIntensityIncrease == null) {
			MinIntensityIncrease = new JLabel("");
		}
		return MinIntensityIncrease;
	}
	private JLabel getMaxIntensityIncrease() {
		if (MaxIntensityIncrease == null) {
			MaxIntensityIncrease = new JLabel("");
		}
		return MaxIntensityIncrease;
	}
	private JLabel getAvgIntensityIncrease() {
		if (AvgIntensityIncrease == null) {
			AvgIntensityIncrease = new JLabel("");
		}
		return AvgIntensityIncrease;
	}
	private JLabel getEndTime() {
		if (endTime == null) {
			endTime = new JLabel("");
			endTime.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return endTime;
	}
	private JLabel getInitialRadiusX() {
		if (initialRadiusX == null) {
			initialRadiusX = new JLabel("");
		}
		return initialRadiusX;
	}
	private JLabel getInitialRadiusY() {
		if (initialRadiusY == null) {
			initialRadiusY = new JLabel("");
		}
		return initialRadiusY;
	}
	
	private JTextArea getManualSearchInfo() {
		if (lblManualInfo == null) {
			lblManualInfo = new JTextArea();
			lblManualInfo.setFont(new Font("Dialog", Font.PLAIN, 11));
			lblManualInfo.setText("If you want to detect an event that was not detected automatically, you can search it in an specific area, making a rectangular selection centered in the center of the hypothetical fusioned vesicle");
			lblManualInfo.setEditable(false);
			lblManualInfo.setFocusable(false);
			lblManualInfo.setLineWrap(true);
			lblManualInfo.setWrapStyleWord(true);
			lblManualInfo.setBorder(null);
			lblManualInfo.setBackground(new Color(0,0,0,0));
			
		}
		return lblManualInfo;
	}
	private JButton getBtnStartManualSearch() {
		if (btnStartManualSearch == null) {
			btnStartManualSearch = new JButton("Start Manual Search");
			btnStartManualSearch.setPreferredSize(new Dimension(175,100));
		}
		return btnStartManualSearch;
	}
}
