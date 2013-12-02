package typeOfMotion;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
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

public class JFreeChartLogMSDChart extends JFreeChart 
{
	double[] time;
	
	double[] twoFit1;
	double[] twoFit2;
	
	double[] threeFit;
	
	double[] fourFit1;
	double[] fourFit2;
	double[] fourFit3;
	double[] fourFit4;
	
	double[] fiveFit;
	
	boolean showTwoFit;
	
	XYSeries msdByTime;
	
	XYSeries twoFitSeries1;
	XYSeries twoFitSeries2;
	
	XYSeriesCollection msdByTimeSC;
	XYSeriesCollection fittedLogMsdSC;
	
	XYDotRenderer msdDotRenderer;
	XYLineAndShapeRenderer fittedMSDLineRenderer;
	
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	double maxDomainAxis;
	double maxRangeAxis;
	
	String distanceUnit;	
	String timeUnit;

	private boolean byTime;
	private boolean logAxis;
	
	public JFreeChartLogMSDChart(XYPlot plot, String distanceUnit, String timeUnit)
	{
		super(plot);
		byTime = true;
		this.distanceUnit = distanceUnit;
		this.timeUnit = timeUnit;
		
		showTwoFit = false;
		
		msdByTime = new XYSeries("MSD/Time");
		
		twoFitSeries1 = new XYSeries("Two1");
		twoFitSeries2 = new XYSeries("Two2");
		
		msdByTimeSC = new XYSeriesCollection();
		fittedLogMsdSC = new XYSeriesCollection();		
		
		msdDotRenderer = new XYDotRenderer();
		msdDotRenderer.setSeriesPaint(0, Color.RED);
		msdDotRenderer.setDotHeight(2);
		msdDotRenderer.setDotWidth(2);
		
		fittedMSDLineRenderer = new XYLineAndShapeRenderer();
		domainAxis = new LogarithmicAxis("TimeLag ("+timeUnit+")");
		rangeAxis = new LogarithmicAxis("MSD/Time ("+distanceUnit+"^2/"+timeUnit+")");
		domainAxis.setAutoRange(true);
		rangeAxis.setAutoRange(true);
		
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		this.setAntiAlias(true);
	}
	public void update()
	{
		fittedLogMsdSC.removeAllSeries();
		int seriesCount=0;
		if(showTwoFit)
		{
			fittedLogMsdSC.addSeries(twoFitSeries1);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;
			fittedLogMsdSC.addSeries(twoFitSeries2);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;			
		}
		
		if(logAxis)
		{
			domainAxis = new LogarithmicAxis("TimeLag ("+timeUnit+")");
			if(byTime)
				rangeAxis = new LogarithmicAxis("MSD/Time ("+distanceUnit+"^2/"+timeUnit+")");
			else
				rangeAxis = new LogarithmicAxis("MSD ("+distanceUnit+"^2)");
		}
		else
		{
			domainAxis = new NumberAxis("TimeLag ("+timeUnit+")");
			if(byTime)
				rangeAxis = new NumberAxis("MSD/Time ("+distanceUnit+"^2/"+timeUnit+")");
			else
				rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		}
		domainAxis.setAutoRange(true);
		rangeAxis.setAutoRange(true);
		
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		
		((XYPlot) this.getPlot()).setDataset(0,msdByTimeSC);
		((XYPlot) this.getPlot()).setRenderer(0,msdDotRenderer);
		((XYPlot) this.getPlot()).setDataset(1,fittedLogMsdSC);
		((XYPlot) this.getPlot()).setRenderer(1,fittedMSDLineRenderer);
		
	}
	public void cleanSeries()
	{
		twoFitSeries1.clear();
		twoFitSeries2.clear();
	}	
	public void setTimeLag(double[] time)
	{
		this.time = time;	
	}
	public void setMSDbyTime(double[] msdByTime)
	{
		this.msdByTime.clear();
		for(int i=0;i < time.length;i++)
		{
//			System.out.println("i:"+i+" time:"+time[i]+" msd:"+msdByTime[i]);
//			if(time[i]>0 && msdByTime[i]>=1)
			this.msdByTime.add(time[i]+1,msdByTime[i]+1);
		}
		msdByTimeSC.removeAllSeries();
		msdByTimeSC.addSeries(this.msdByTime);				
	}
	public void setTwoFit(double[] twoFit1, double[] twoFit2)
	{
		this.twoFit1 = twoFit1;
		this.twoFit2 = twoFit2;
		twoFitSeries1.clear();
		twoFitSeries2.clear();
		for(int i=0;i < twoFit1.length;i++)
			twoFitSeries1.add(time[i],twoFit1[i]);
		for(int i=0;i < twoFit2.length;i++)
			twoFitSeries2.add(time[i+twoFit1.length],twoFit2[i]);
	}
	public void updateUnits(String distanceUnit, String timeUnit)
	{
		this.distanceUnit = distanceUnit;
		if(byTime)
			rangeAxis.setLabel("MSD/Time ("+distanceUnit+"^2/"+timeUnit+")");
		else
			rangeAxis.setLabel("MSD ("+distanceUnit+"^2)");
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		this.timeUnit = timeUnit;
		domainAxis.setLabel("TimeLag ("+timeUnit+")");
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
	public void setShowTwoFit(boolean showTwoFit) 
	{
		this.showTwoFit = showTwoFit;
	}
	public void showMSDbyTime(boolean byTime)
	{
		this.byTime = byTime;
		if(byTime)msdByTime.setDescription("MSD/Time");
		else msdByTime.setDescription("MSD");
	}
	public void setLogAxis(boolean logAxis)
	{
		this.logAxis = logAxis;
	}
}
