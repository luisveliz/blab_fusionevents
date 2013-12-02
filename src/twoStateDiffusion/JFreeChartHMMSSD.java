package twoStateDiffusion;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartHMMSSD extends JFreeChart
{
	double[] time;
	double[] ssd;
	double[] fit;
	
	boolean showAutomaticFit;
	
	XYSeries ssdSeries;
	XYSeries automaticFitSeries;
	
	XYSeriesCollection ssdSeriesCollection;
	XYSeriesCollection fittedSSDSeriesCollection;
	
	XYDotRenderer ssdDotRenderer;
	XYLineAndShapeRenderer fittedSSDLineRenderer;
	
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	float maxDomainAxis;
	float maxRangeAxis;
	
	//XYPlot xyplot;
	//JFreeChart chart;
	String distanceUnit;	
	String timeUnit;
	
	public JFreeChartHMMSSD(XYPlot plot)
	{
		super(plot);
		distanceUnit = "px";
		timeUnit = "frames";
				
		showAutomaticFit = false;
		
		ssdSeries = new XYSeries("SSD");
		automaticFitSeries = new XYSeries("Linear Fit");
		
		ssdSeriesCollection = new XYSeriesCollection();
		fittedSSDSeriesCollection = new XYSeriesCollection();		
		
		ssdDotRenderer = new XYDotRenderer();
		ssdDotRenderer.setSeriesPaint(0, Color.RED);
		ssdDotRenderer.setDotHeight(2);
		ssdDotRenderer.setDotWidth(2);
		
		fittedSSDLineRenderer = new XYLineAndShapeRenderer();
		
		domainAxis = new NumberAxis("Time ("+timeUnit+")");
		rangeAxis = new NumberAxis("SSD ("+distanceUnit+"^2)");
		maxDomainAxis = 100;
		domainAxis.setRange(0, maxDomainAxis);
		maxRangeAxis = 400;
		rangeAxis.setRange(0, maxRangeAxis);
		
		//xyplot = new XYPlot();
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		((XYPlot) this.getPlot()).setRenderer(0,ssdDotRenderer);
		((XYPlot) this.getPlot()).setDataset(0,ssdSeriesCollection);
		((XYPlot) this.getPlot()).setDataset(1,fittedSSDSeriesCollection);
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		this.setAntiAlias(true);
	}
	public void update()
	{
		fittedSSDSeriesCollection.removeAllSeries();
		int seriesCount=0;
		if(showAutomaticFit)
		{
			fittedSSDSeriesCollection.addSeries(automaticFitSeries);
			fittedSSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedSSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedSSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;
		}
		((XYPlot) this.getPlot()).setRenderer(1,fittedSSDLineRenderer);
	}
	
	public void cleanSeries()
	{
		automaticFitSeries.clear();
	}	
	public void setTime(double[] time)
	{
		this.time = time;		
		maxDomainAxis = (float) (time[time.length-1]*1.1);
//		maxRangeAxis = (float) (4*time[time.length-1]*1.1);
		domainAxis.setRange(0, maxDomainAxis);
//		rangeAxis.setRange(0, maxRangeAxis);
//		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
//		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
	}
	
	public void setSSD(double[] ssd)
	{
		this.ssd = ssd;
		ssdSeries.clear();
		for(int i=0;i < time.length;i++)
		{
			ssdSeries.add(time[i],ssd[i]);		
		}
		maxRangeAxis = (float) (ssd[ssd.length-1]*1.1);
		System.out.println("SSD chart range axis:"+maxRangeAxis);
		rangeAxis.setRange(0, maxRangeAxis);
//		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);

		ssdSeriesCollection.removeAllSeries();
		ssdSeriesCollection.addSeries(ssdSeries);
		
	}	
	public void setAutomaticFit(double[] automaticFit)
	{
		this.fit = automaticFit;
		automaticFitSeries.clear();
		for(int i=0;i < time.length;i++)
		{
			automaticFitSeries.add(time[i],automaticFit[i]);
		}		
	}
	public void setShowAutomaticFit(boolean showAutomaticFit) {
		this.showAutomaticFit = showAutomaticFit;
	}
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
		rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
		domainAxis = new NumberAxis("Time ("+timeUnit+")");
		domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}

	public BufferedImage getImage(int width, int height)
	{
		fittedSSDSeriesCollection.removeAllSeries();
		int seriesCount=0;
		if(showAutomaticFit)
		{
			fittedSSDSeriesCollection.addSeries(automaticFitSeries);
			fittedSSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedSSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedSSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;
		}
		//TODO revisar los metodos en el if de todos los for!!!!!!!
		/*int seriesCount = fittedMSDSeriesCollection.getSeriesCount(); 
		for(int i=0;i<=seriesCount;i++)
		{			
			fittedMSDLineRenderer.setSeriesLinesVisible(i, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(i, false);
			fittedMSDLineRenderer.setSeriesPaint(i, Color.BLUE);
		}*/
		((XYPlot) this.getPlot()).setDataset(0,ssdSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(0,ssdDotRenderer);
		((XYPlot) this.getPlot()).setDataset(1,fittedSSDSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(1,fittedSSDLineRenderer);
		return createBufferedImage(width, height);		
	}
}