package typeOfMotion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import bTools.BMaths;

public class JFreeChartMSD extends JFreeChart
{
	double[] time;
	double[] msd;
	double[] automaticFit;
	double[] normal;
	double[] corralled;
	double[] anomalous;
	double[] directed;
	
	boolean showAutomaticFit;
	boolean showNormalFit;
	boolean showCorralledFit;
	boolean showAnomalousFit;
	boolean showDirectedFit;	
	boolean showSD;
	boolean showVariance;
	boolean showFitMarks;
	boolean showNPoints;
	
	XYSeries msdSeries;
//	XYSeries msdSD;
	XYLineAnnotation[] sd;
	XYSeries msdVar;
	XYSeriesCollection msdSeriesCollection;
	XYDotRenderer msdDotRenderer;
	
	XYSeries msdNPoints;
	XYSeriesCollection msdNPointsSC;	
	ValueAxis npointsRangeAxis;
	XYDotRenderer msdNPointsDotRenderer;
	
	
	XYSeries automaticFitSeries;
	XYSeries normalSeries;
	XYSeries corralledSeries;
	XYSeries anomalousSeries;
	XYSeries directedSeries;
	XYSeriesCollection fittedMSDSeriesCollection;
	XYLineAndShapeRenderer fittedMSDLineRenderer;
	
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	
	double maxDomainAxis;
	double maxRangeAxis;
	
	String distanceUnit;	
	String timeUnit;
	
	XYLineAnnotation startFitPoint;
	XYLineAnnotation finalFitPoint;
	
	public JFreeChartMSD(XYPlot plot, String distanceUnit, String timeUnit)
	{
		super(plot);
		this.distanceUnit = distanceUnit;
		this.timeUnit = timeUnit;
				
		showAutomaticFit = false;
		showCorralledFit = false;
		showAnomalousFit = false;
		showDirectedFit = false;
		showFitMarks = false;
		showSD = false;
		showVariance = false;
		showNPoints = false;
		
		msdSeries = new XYSeries("MSD");
//		msdSD = new XYSeries("SD");
		msdVar = new XYSeries("Var");
		msdSeriesCollection = new XYSeriesCollection();
		msdDotRenderer = new XYDotRenderer();
		msdDotRenderer.setSeriesPaint(0, Color.RED);
		msdDotRenderer.setSeriesPaint(1, Color.BLUE);
		msdDotRenderer.setDotHeight(2);
		msdDotRenderer.setDotWidth(2);
		
		
		msdNPoints = new XYSeries("N points averaged");
		msdNPointsSC = new XYSeriesCollection(msdNPoints);
		msdNPointsDotRenderer = new XYDotRenderer();
		msdNPointsDotRenderer.setSeriesPaint(0, Color.MAGENTA);
		msdNPointsDotRenderer.setDotHeight(2);
		msdNPointsDotRenderer.setDotWidth(2);
		
		
		automaticFitSeries = new XYSeries("Automatic");
		normalSeries = new XYSeries("Normal");
		corralledSeries = new XYSeries("Corralled");
		anomalousSeries = new XYSeries("Anomalous");
		directedSeries = new XYSeries("Directed");
		fittedMSDSeriesCollection = new XYSeriesCollection();		
		fittedMSDLineRenderer = new XYLineAndShapeRenderer();
		
		
		domainAxis = new NumberAxis("TimeLag ("+timeUnit+")");
		rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		npointsRangeAxis = new NumberAxis("N° of Points");
		domainAxis.setAutoRange(true);
		rangeAxis.setAutoRange(true);
		
		//xyplot = new XYPlot();
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot) this.getPlot()).setRangeAxis(0,rangeAxis);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(0, 0);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(1, 1);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(2, 0);
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		this.setAntiAlias(true);
	}
	public void update()
	{
		
		/*----------------------------------------------------------------*/
		((XYPlot) this.getPlot()).clearAnnotations();
		if(showFitMarks)
		{
			((XYPlot) this.getPlot()).addAnnotation(startFitPoint);
			((XYPlot) this.getPlot()).addAnnotation(finalFitPoint);
		}
		if(showSD)
		{
			for(int i=0;i<sd.length;i++)
				((XYPlot) this.getPlot()).addAnnotation(sd[i]);
		}
		if(showVariance)
			msdSeriesCollection.addSeries(msdVar);
		((XYPlot) this.getPlot()).setDataset(0, msdSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(0, msdDotRenderer);
		/*----------------------------------------------------------------*/

		
		/*----------------------------------------------------------------*/
		((XYPlot) this.getPlot()).clearRangeAxes();
		((XYPlot) this.getPlot()).setRangeAxis(0,rangeAxis);
		if(showNPoints)
		{
			((XYPlot) this.getPlot()).setRangeAxis(1, npointsRangeAxis);
			((XYPlot) this.getPlot()).setDataset(1, msdNPointsSC);
			((XYPlot) this.getPlot()).setRenderer(1, msdNPointsDotRenderer);
		}
		/*----------------------------------------------------------------*/
		
		/*----------------------------------------------------------------*/
		fittedMSDSeriesCollection.removeAllSeries();
		int seriesCount=0;
		if(showAutomaticFit)
		{
			fittedMSDSeriesCollection.addSeries(automaticFitSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.BLACK);
			seriesCount++;
		}
		if(showNormalFit)
		{
			fittedMSDSeriesCollection.addSeries(normalSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;
		}
		if(showCorralledFit)
		{
			fittedMSDSeriesCollection.addSeries(corralledSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.CYAN);
			seriesCount++;
		}
		
		if(showAnomalousFit)
		{
			fittedMSDSeriesCollection.addSeries(anomalousSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.MAGENTA);
			seriesCount++;
		}
		if(showDirectedFit)
		{
			fittedMSDSeriesCollection.addSeries(directedSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.GREEN);
			seriesCount++;
		}
		((XYPlot) this.getPlot()).setDataset(2,fittedMSDSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(2,fittedMSDLineRenderer);
		/*----------------------------------------------------------------*/
	}
	
	
	public void cleanSeries()
	{
		automaticFitSeries.clear();
		normalSeries.clear();
		corralledSeries.clear();
		anomalousSeries.clear();
		directedSeries.clear();
	}
	public void setMaxs(double domainMax, double rangeMax)
	{
		this.maxDomainAxis = domainMax;
		this.maxRangeAxis = rangeMax;
		domainAxis.setRange(0, maxDomainAxis);
		rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		
	}
	public void setTime(double[] time)
	{
		this.time = time;		
		maxDomainAxis = time[time.length-1]*1.1;
		domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
	
	public void setMSD(double[] msd)
	{
		this.msd = msd;
		msdSeries.clear();
		for(int i=0;i < time.length;i++)
			msdSeries.add(time[i],msd[i]);		

		maxRangeAxis = BMaths.max(msd)*1.1;
		rangeAxis.setRange(0, maxRangeAxis);

		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		msdSeriesCollection.removeAllSeries();
		msdSeriesCollection.addSeries(msdSeries);
	}	
	public void setMSDSD(double[] msdSD)
	{
//		this.msdSD.clear();
		sd = new XYLineAnnotation[msdSD.length];
		for(int i=0;i < time.length;i++)
			sd[i] = new XYLineAnnotation(time[i], msd[i]-(msdSD[i]/2), time[i], msd[i]+(msdSD[i]/2));			
	}
	public void setMSDVar(double[] msdVar)
	{
		this.msdVar.clear();
		for(int i=0;i < time.length;i++)
			this.msdVar.add(time[i],msdVar[i]);		
	}
	public void setMSDNPoints(double[] nPoints)
	{
		msdNPoints.clear();
		for(int i=0;i < time.length;i++)
			msdNPoints.add(time[i],nPoints[i]);		
	}
	

	public void setFitMarks(double startPoint, double finalPoint)
	{
		if(msd!=null)
		{
			startFitPoint = new XYLineAnnotation(startPoint, 0, startPoint, BMaths.max(msd), new BasicStroke(), Color.GREEN);
			finalFitPoint = new XYLineAnnotation(finalPoint, 0, finalPoint, BMaths.max(msd), new BasicStroke(), Color.GREEN);
		}
	}
	
	public void setAutomaticFit(double[] automaticFit)
	{
		this.automaticFit = automaticFit;
		automaticFitSeries.clear();
		for(int i=0;i < time.length;i++)
		{
			automaticFitSeries.add(time[i],automaticFit[i]);
		}		
	}
	public void setNormal(double[] x, double[] normal)
	{
		this.normal = normal;
		normalSeries.clear();
		for(int i=0;i < x.length;i++)
			normalSeries.add(x[i],normal[i]);		
	}
	public void setCorralled(double[] x, double[] corralled)
	{
		this.corralled = corralled;
		corralledSeries.clear();
		for(int i=0;i < x.length;i++)
			corralledSeries.add(x[i],corralled[i]);		
	}
	public void setAnomalous(double[] x, double[] anomalous)
	{
		this.anomalous = anomalous;
		anomalousSeries.clear();
		for(int i=0;i < x.length;i++)
			anomalousSeries.add(x[i],anomalous[i]);		
	}
	public void setDirected(double[] x, double[] directed)
	{
		this.directed = directed;
		directedSeries.clear();
		for(int i=0;i < x.length;i++)
			directedSeries.add(x[i],directed[i]);		
	}
	public void updateUnits(String distanceUnit, String timeUnit)
	{
		this.distanceUnit = distanceUnit;
		rangeAxis = new NumberAxis("MSD ("+distanceUnit+"^2)");
		rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setRangeAxis(rangeAxis);
		this.timeUnit = timeUnit;
		domainAxis = new NumberAxis("TimeLag ("+timeUnit+")");
		domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
	

	public void showAutomaticFit(boolean showAutomaticFit) {
		this.showAutomaticFit = showAutomaticFit;
	}
	public void showNormalFit(boolean showNormalFit) {
		this.showNormalFit = showNormalFit;
	}
	public void showCorralledFit(boolean showCorralledFit) {
		this.showCorralledFit = showCorralledFit;
	}
	public void showAnomalousFit(boolean showAnomalousFit) {
		this.showAnomalousFit = showAnomalousFit;
	}
	public void showDirectedFit(boolean showDirectedFit) {
		this.showDirectedFit = showDirectedFit;
	}
	public void showSD(boolean showSD)
	{
		this.showSD = showSD;
	}
	public void showVariance(boolean showVariance)
	{
		this.showVariance = showVariance;
	}
	public void showNPoints(boolean showNPoints)
	{
		this.showNPoints = showNPoints;
	}
	public void showFitMarks(boolean showFitMarks)
	{
		this.showFitMarks = showFitMarks;
	}

	public BufferedImage getImage(int width, int height)
	{
		fittedMSDSeriesCollection.removeAllSeries();
		int seriesCount=0;
		if(showAutomaticFit)
		{
			fittedMSDSeriesCollection.addSeries(automaticFitSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.BLUE);
			seriesCount++;
		}
		if(showNormalFit)
		{
			fittedMSDSeriesCollection.addSeries(normalSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.ORANGE);
			seriesCount++;
		}
		if(showCorralledFit)
		{
			fittedMSDSeriesCollection.addSeries(corralledSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.ORANGE);
			seriesCount++;
		}
		
		if(showAnomalousFit)
		{
			fittedMSDSeriesCollection.addSeries(anomalousSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.MAGENTA);
			seriesCount++;
		}
		if(showDirectedFit)
		{
			fittedMSDSeriesCollection.addSeries(directedSeries);
			fittedMSDLineRenderer.setSeriesLinesVisible(seriesCount, true);
			fittedMSDLineRenderer.setSeriesShapesVisible(seriesCount, false);
			fittedMSDLineRenderer.setSeriesPaint(seriesCount, Color.GREEN);
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
		
        
		((XYPlot) this.getPlot()).setDataset(0,msdSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(0,msdDotRenderer);
		((XYPlot) this.getPlot()).setDataset(1,fittedMSDSeriesCollection);
		((XYPlot) this.getPlot()).setRenderer(1,fittedMSDLineRenderer);
	
		return createBufferedImage(width, height);		
	}
}