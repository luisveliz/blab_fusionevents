package twoStateDiffusion;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartHMM2ParamsVsStep extends JFreeChart
{
	static boolean ocupado = false;
	double[] param1;
	double[] param2;
	double[] fit;
	boolean showResult;
	
	XYSeries series1;
	XYSeries series2;
	
	XYSeries result1;
	XYSeries result2;
	XYSeries burn;
	
	XYSeriesCollection sc;
	XYSeriesCollection result;
	
	XYDotRenderer dotRenderer;
	XYLineAndShapeRenderer lineRenderer;
	
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	float maxDomainAxis;
	float maxRangeAxis;
	
	//XYPlot xyplot;
	//JFreeChart chart;
	String rangeUnit;	
	String domainUnit;
	
	public JFreeChartHMM2ParamsVsStep(XYPlot plot, boolean logYaxis, String serie1, String serie2)
	{
		super(plot);
		rangeUnit = "?";
		domainUnit = "?";
				
		showResult = false;
		
		series1 = new XYSeries(serie1);
		series2 = new XYSeries(serie2);
		
		sc = new XYSeriesCollection();
		sc.addSeries(series1);
		sc.addSeries(series2);
		
		dotRenderer = new XYDotRenderer();
		dotRenderer.setSeriesPaint(0, Color.BLUE);
		dotRenderer.setSeriesPaint(1, Color.RED);
		dotRenderer.setDotHeight(2);
		dotRenderer.setDotWidth(2);
		
		domainAxis = new NumberAxis("Step ("+domainUnit+")");
		if(logYaxis)
		{
			rangeAxis = new LogarithmicAxis("Param ("+rangeUnit+"^2)");
			rangeAxis.setRange(0.001, 1);
		}
		else
		{
			rangeAxis = new NumberAxis("Param ("+rangeUnit+")^2");
			rangeAxis.setRange(-0.2, 1.2);
		}
		//maxDomainAxis = 100;
		//domainAxis.setRange(0, maxDomainAxis);
		//maxRangeAxis = 400;
		
		
		//xyplot = new XYPlot();
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(0,rangeAxis);
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDataset(0,sc);
		((XYPlot) this.getPlot()).setRenderer(0,dotRenderer);
		
		
		result1 = new XYSeries("d1Result");
		result2 = new XYSeries("d2Result");
		burn = new XYSeries("Burn");
		
		result1.add(0, 0.1);
		result1.add(40, 0.1);
		result2.add(0, 0.01);
		result2.add(40, 0.01);
		burn.add(50,0.00001);
		burn.add(50,1.0);
		
		result = new XYSeriesCollection();
		result.addSeries(result1);
		result.addSeries(result2);
		result.addSeries(burn);
		
		lineRenderer = new XYLineAndShapeRenderer();
		lineRenderer.setSeriesShapesVisible(0, false);
		lineRenderer.setSeriesShapesVisible(1, false);
		lineRenderer.setSeriesShapesVisible(2, false);
		lineRenderer.setSeriesPaint(0, Color.BLACK);
		lineRenderer.setSeriesPaint(1, Color.BLACK);
		lineRenderer.setSeriesPaint(2, Color.GREEN);
		
		//((XYPlot) this.getPlot()).setDataset(1,result);
		((XYPlot) this.getPlot()).setRenderer(1,lineRenderer);
		
		
		this.setAntiAlias(true);
	}
	public void cleanSeries()
	{
		series1.clear();
		series2.clear();
	}	
	public void addData(double param1, double param2)
	{
		series1.add(series1.getItemCount()+1,param1);
		series2.add(series2.getItemCount()+1,param2);
		sc.removeAllSeries();
		sc.addSeries(series1);
		sc.addSeries(series2);
		domainAxis.setRange(0,series2.getItemCount()+1);
		((XYPlot) this.getPlot()).setDomainAxis(0, domainAxis);
		((XYPlot) this.getPlot()).setDataset(0,sc);
		
	}	
	public void setBurn(double burnSelected)
	{
		burn.clear();
		burn.add(burnSelected,0.00001);
		burn.add(burnSelected,1.0);	
		result = new XYSeriesCollection();
		result.addSeries(burn);
		((XYPlot) this.getPlot()).setDataset(1,result);
	}
	
	public void setResult(double d1R, double d2R)
	{
		result1.clear();
		result2.clear();
		
		result1.add(0, d1R);
		result1.add(1000, d1R);
		result2.add(0, d2R);
		result2.add(1000, d2R);
		
		result = new XYSeriesCollection();
		result.addSeries(result1);
		result.addSeries(result2);
		result.addSeries(burn);
		System.out.println("setResult showResult:"+showResult);
		if(showResult)
		{
			((XYPlot) this.getPlot()).setDataset(1,result);		
		}
	}
	
	
	public void setRangeUnit(String rangeUnit) {
		this.rangeUnit = rangeUnit;
		rangeAxis = new NumberAxis("? ("+rangeUnit+"^2)");
		//rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
	}
	public void setDomainUnit(String domainUnit) {
		this.domainUnit = domainUnit;
		domainAxis = new NumberAxis("Step ("+domainUnit+")");
		//domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
	public void setShowResult(boolean show)
	{
		
		showResult = show;
		result = new XYSeriesCollection();
		if(show)
		{
			result.addSeries(result1);
			result.addSeries(result2);
			result.addSeries(burn);
			((XYPlot) this.getPlot()).setDataset(0,sc);
			((XYPlot) this.getPlot()).setDataset(1,result);
		}
		else
		{	
			//result.addSeries(burn);
			((XYPlot) this.getPlot()).setDataset(0,sc);
			((XYPlot) this.getPlot()).setDataset(1,result);
		}
		System.out.println("setShowResult showResult:"+showResult);
	}
	public BufferedImage getImage(int width, int height)
	{
		return createBufferedImage(width, height);		
	}
}