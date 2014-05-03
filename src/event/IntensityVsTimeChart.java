package event;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
	
public class IntensityVsTimeChart extends JFreeChart
{
	double[] time;
	

	XYSeries intensity;
	XYSeriesCollection intensityC;
	
	XYSeries intensityFit;
	XYSeriesCollection intensityFitC;
	
	XYSeries tmeanFit;
	XYSeriesCollection tmeanFitC;
	
	final ValueAxis rangeAxisI;
	
//	XYSeries dif;
//	XYSeriesCollection difC;
//	final ValueAxis rangeAxisD;
	
	XYDotRenderer dotRenderer0;
	XYDotRenderer dotRenderer1;
	XYDotRenderer dotRenderer2;
	XYDotRenderer dotRendererDif;
	
	XYLineAndShapeRenderer lineRenderer1;
	XYLineAndShapeRenderer lineRenderer2;
	
	final ValueAxis domainAxis;
	
	XYLineAnnotation difT1A;
	XYLineAnnotation difT2A;
	XYLineAnnotation intTA;
	XYLineAnnotation intFETA;
	XYLineAnnotation currentFrame;
	XYLineAnnotation time_instant;
	XYLineAnnotation maxIncrease;
	XYLineAnnotation tMean;
	
	ArrayList<IntervalMarker> eventsMarkers;
	
	boolean showFrame, showDifT, showIntT, showFET, showIntensity, showGaussX, showGaussY, showFE, showFit;
	boolean showIncrease=true;
	XYPlot intensityPlot;
	XYPlot difPlot;

	public IntensityVsTimeChart (CombinedDomainXYPlot xyplot)
	{
		super(xyplot);
		time = new double[]{0};
		intensityPlot = new XYPlot();
//		difPlot = new XYPlot();
		showFrame = true; 
		showDifT = true; 
		showIntT = true;
		showFET = true;
		showIntensity = true;
		showFE = true;
		showFit = true;
		eventsMarkers = new ArrayList<IntervalMarker>();
		
		intensity = new XYSeries("Intensity");
		intensityC = new XYSeriesCollection(intensity);

		tmeanFit=new XYSeries("Graphics TMean");
		tmeanFitC=new XYSeriesCollection();
		
		intensityFit = new XYSeries("ExpFit");
		intensityFitC = new XYSeriesCollection();
		
		dotRenderer0 = new XYDotRenderer();
		dotRenderer0.setSeriesPaint(0,Color.BLACK);
		dotRenderer0.setDotWidth(2);
		dotRenderer1 = new XYDotRenderer();
		dotRenderer1.setSeriesPaint(0,Color.RED);
		dotRenderer1.setDotWidth(1);
		dotRenderer2 = new XYDotRenderer();
		dotRenderer2.setSeriesPaint(0,Color.BLUE);
		dotRenderer2.setDotWidth(1);
		
		domainAxis = new NumberAxis("Time (seconds)");
		
		rangeAxisI = new NumberAxis("Intensity");
		rangeAxisI.setRange(0, 260);
		
		lineRenderer1 = new XYLineAndShapeRenderer();
		lineRenderer1.setSeriesShapesVisible(0, false);
		lineRenderer1.setSeriesPaint(0, Color.BLACK);
		
		lineRenderer2 = new XYLineAndShapeRenderer();
		
		intensityPlot.setRangeAxis(rangeAxisI);
		intensityPlot.setRenderer(0,dotRenderer0);
		intensityPlot.setRenderer(1,dotRenderer1);
		intensityPlot.setRenderer(2,dotRenderer2);
		intensityPlot.setRenderer(3,lineRenderer1);
		intensityPlot.setRenderer(4,lineRenderer2);
		
		intensityPlot.setDataset(0,intensityC);
		intensityPlot.setDataset(3,intensityFitC);
		intensityPlot.setDataset(4,tmeanFitC);
		
		intensityPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		intensityPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		((CombinedDomainXYPlot) this.getPlot()).add(intensityPlot);
		((CombinedDomainXYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((CombinedDomainXYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((CombinedDomainXYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		this.setAntiAlias(true);
	}
	
	public void setShowFit(boolean bool){
		showFit=bool;
	}
	
	public void update()
	{
//		difC.removeAllSeries();
//		difC.addSeries(dif);
		intensityC.removeAllSeries();
		intensityPlot.clearAnnotations();
		
		if(showIntensity)intensityC.addSeries(intensity);
		intensityFitC.removeAllSeries();
		tmeanFitC.removeAllSeries();
		if(showFit){
				intensityFit.setKey("Increase and Exp Decay Fit");
				intensityFitC.addSeries(intensityFit);
				intensityPlot.setDataset(3,intensityFitC);
				lineRenderer1.setSeriesPaint(0, Color.GREEN);
				tmeanFitC.addSeries(tmeanFit);
				intensityPlot.setDataset(4,tmeanFitC);
				lineRenderer2.setSeriesPaint(0, Color.BLUE);
				//intensityPlot.addAnnotation(tMean);
		}else{
			intensityFit.setKey("Increase fit");
			intensityFitC.addSeries(intensityFit);
			intensityPlot.setDataset(3,intensityFitC);
			lineRenderer1.setSeriesPaint(0, Color.GREEN);
			
		}
		
		
		if(showIntT && intTA!=null)intensityPlot.addAnnotation(intTA);
		
		if (showFrame){
			if(time_instant!=null){
				intensityPlot.addAnnotation(time_instant);
			}else{
				if(currentFrame!=null){
					intensityPlot.addAnnotation(currentFrame);
				}
			}
		}
		//if (showIncrease){
			//intensityPlot.addAnnotation(maxIncrease);
		//}
		
		if(showFET && intFETA!=null)intensityPlot.addAnnotation(intFETA);
		
//		difPlot.clearDomainMarkers();
		intensityPlot.clearDomainMarkers();
		if(showFE)
			for(int i=0;i<eventsMarkers.size();i++)
			{
//				difPlot.addDomainMarker(marker);
				intensityPlot.addDomainMarker(eventsMarkers.get(i));
			}
	}
	
	public void setIntensity(int[] intensity)
	{
		assert time.length == intensity.length;
		this.intensity.clear();
		for(int i=0;i < time.length;i++)
			this.intensity.add(time[i],intensity[i]);
	}
	
	public void setMeanIntensity(double[] intensity)
	{
		assert time.length == intensity.length;
		this.intensity.clear();
		for(int i=0;i < time.length;i++)
			this.intensity.add(time[i],intensity[i]);
	}
	
	public void addFit(double[][] fit)
	{
		if (fit!=null){
			intensityFit = new XYSeries("Event Increase and Exp Decay Fit");
			for(int i=0;i < fit.length;i++)
				intensityFit.add(fit[i][0],fit[i][1]);
		}else{
			clearFits();
		}
	}
	
	public void clearFits()
	{
		intensityFit.clear();
	}

	public void setTime(double[] time){
		this.time=time;
	}
	
	public void setCurrentTimeInstant(double time)
	{
		this.currentFrame = new XYLineAnnotation(time, 0, time, 260, new BasicStroke(), Color.RED);
	}
	
	public void setMaxIncrease(int start, int end, double startInt, double endInt, double timeFactor){
		if (!showFit){
			intensityFit.clear();
			intensityPlot.setDataset(1,null);
		}
		intensityFit.add(start*timeFactor,startInt);
		intensityFit.add(end*timeFactor,endInt);
		//this.maxIncrease = new XYLineAnnotation(start*timeFactor, startInt, end*timeFactor, endInt, new BasicStroke(), Color.GREEN);
		//System.out.println("start: "+start+" startInt: "+startInt+" end: "+end+" endInt: "+endInt);
	}
	
	public void setTMean(double firstInstTMean, double secondInstTMean, double firstInt, double secondInt){
		tmeanFit.clear();
		tmeanFit.add(firstInstTMean,firstInt);
		tmeanFit.add(secondInstTMean,secondInt);
		//this.tMean = new XYLineAnnotation(firstInstTMean,firstInt,secondInstTMean,secondInt,new BasicStroke(), Color.BLUE);
	}
	
}