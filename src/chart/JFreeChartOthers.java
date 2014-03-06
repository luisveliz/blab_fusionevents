package chart;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartOthers extends JFreeChart
{
	double[] time;
	double[] conf;
	double[] asym;
	
	boolean showConf;
	boolean showAsym;	
	
	XYSeries confSeries;
	XYSeries asymSeries;
	
	XYSeriesCollection othersSeriesCollection;
	
	XYDotRenderer msdDotRenderer;
	
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	double maxDomainAxis;
	double maxRangeAxis;
	
	String distanceUnit;	
	String timeUnit;
	
	public JFreeChartOthers(XYPlot plot)
	{
		super(plot);
		distanceUnit = "px";
		timeUnit = "frames";
				
		showConf = false;
		showAsym = false;
		
		confSeries = new XYSeries("Conf");
		asymSeries = new XYSeries("Asym");
		
		othersSeriesCollection = new XYSeriesCollection();
		
		msdDotRenderer = new XYDotRenderer();
		msdDotRenderer.setSeriesPaint(0, Color.GREEN);
		msdDotRenderer.setSeriesPaint(1, Color.BLUE);
		msdDotRenderer.setDotHeight(2);
		msdDotRenderer.setDotWidth(2);
		
		
		domainAxis = new NumberAxis("TimeLag ("+timeUnit+")");
		rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		maxDomainAxis = 100;
		domainAxis.setRange(0, maxDomainAxis);
		maxRangeAxis = 400;
		rangeAxis.setRange(0, maxRangeAxis);
		
		//xyplot = new XYPlot();
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		this.setAntiAlias(true);
	}
	public void update()
	{
		othersSeriesCollection.removeAllSeries();
		if(showConf)
		{
			othersSeriesCollection.addSeries(confSeries);
		}
		if(showAsym)
		{
			othersSeriesCollection.addSeries(asymSeries);
		}
				
		((XYPlot) this.getPlot()).setDataset(0,othersSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(0,msdDotRenderer);
	}
	
	
	public void cleanSeries()
	{
		asymSeries.clear();
		confSeries.clear();
	}	
	public void setTime(double[] time)
	{
		this.time = time;		
		maxDomainAxis = time[time.length-1]+15;
		maxRangeAxis = 4*time[time.length-1]+50;
		domainAxis.setRange(0, maxDomainAxis);
		rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
	}
	
	public void setConf(double[] conf)
	{
		this.conf = conf;
		confSeries.clear();
		for(int i=0;i < time.length;i++)
		{
			confSeries.add(time[i],conf[i]);		
		}		
	}
	public void setAsym(double[] asym)
	{
		this.asym = asym;
		asymSeries.clear();
		for(int i=0;i < time.length;i++)
		{
			asymSeries.add(time[i],asym[i]);		
		}		
	}
	public void setShowConf(boolean showConf) {
		this.showConf = showConf;
	}
	public void setShowAsym(boolean showAsym) {
		this.showAsym = showAsym;
	}
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
		rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
		domainAxis = new NumberAxis("TimeLag ("+timeUnit+")");
		domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
}