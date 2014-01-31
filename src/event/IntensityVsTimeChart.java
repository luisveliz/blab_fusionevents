package event;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
	
public class IntensityVsTimeChart extends JFreeChart
{
	double[] time;
	

	XYSeries intensity;
	XYSeriesCollection intensityC;
	
	ArrayList<XYSeries> intensityFit;
	XYSeriesCollection intensityFitC;
	
	final ValueAxis rangeAxisI;
	
//	XYSeries dif;
//	XYSeriesCollection difC;
//	final ValueAxis rangeAxisD;
	
	XYDotRenderer dotRenderer0;
	XYDotRenderer dotRenderer1;
	XYDotRenderer dotRenderer2;
	XYDotRenderer dotRendererDif;
	
	XYLineAndShapeRenderer lineRenderer;
	
	final ValueAxis domainAxis;
	
	XYLineAnnotation difT1A;
	XYLineAnnotation difT2A;
	XYLineAnnotation intTA;
	XYLineAnnotation intFETA;
	XYLineAnnotation frameA;
	XYLineAnnotation time_instant;
	
	ArrayList<IntervalMarker> eventsMarkers;
	
	boolean showFrame, showDifT, showIntT, showFET, showIntensity, showGaussX, showGaussY, showFE, showFit;
	
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

		
//		intensityFit = new XYSeries("ExpFit");
		intensityFit = new ArrayList<XYSeries>();
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
		
		domainAxis = new NumberAxis("Time (frames)");
		
		rangeAxisI = new NumberAxis("Intensity");
		rangeAxisI.setRange(0, 260);
		
		lineRenderer = new XYLineAndShapeRenderer();
		lineRenderer.setSeriesShapesVisible(0, false);
		lineRenderer.setSeriesPaint(0, Color.BLACK);
		
		intensityPlot.setRangeAxis(rangeAxisI);
		intensityPlot.setRenderer(0,dotRenderer0);
		intensityPlot.setRenderer(1,dotRenderer1);
		intensityPlot.setRenderer(2,dotRenderer2);
		intensityPlot.setRenderer(3,lineRenderer);
		
		intensityPlot.setDataset(0,intensityC);
		intensityPlot.setDataset(3,intensityFitC);
		
		intensityPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		intensityPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
//		dif = new XYSeries("|uX-uY|");
//		difC = new XYSeriesCollection(dif);
//		rangeAxisD = new NumberAxis("|uX-uY|");
//		rangeAxisD.setRange(-100, 100);
//		dotRendererDif = new XYDotRenderer();
//		dotRendererDif.setSeriesPaint(0,Color.BLACK);
//		dotRendererDif.setDotWidth(2);
//
//		difPlot.setDataset(difC);
//		difPlot.setRangeAxis(rangeAxisD);
//		difPlot.setRenderer(dotRendererDif);
//		difPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
//		difPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
//		((CombinedDomainXYPlot) this.getPlot()).add(difPlot);
		((CombinedDomainXYPlot) this.getPlot()).add(intensityPlot);
		((CombinedDomainXYPlot) this.getPlot()).setDomainAxis(domainAxis);
		((CombinedDomainXYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((CombinedDomainXYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		this.setAntiAlias(true);
	}
	public void update()
	{
		System.out.println("Hola entre a update");
//		difC.removeAllSeries();
//		difC.addSeries(dif);
		intensityC.removeAllSeries();
		if(showIntensity)intensityC.addSeries(intensity);
		intensityFitC.removeAllSeries();
		if(showFit)
			for(int i=0;i<intensityFit.size();i++)
			{
				intensityFitC.addSeries(intensityFit.get(i));
				lineRenderer.setSeriesShapesVisible(i, false);
				lineRenderer.setSeriesPaint(i, Color.BLACK);
			}
		
		
//		difPlot.clearAnnotations();
//		if(showDifT)
//		{
//			difPlot.addAnnotation(annotations[0]);
//			difPlot.addAnnotation(annotations[1]);
//		}
		intensityPlot.clearAnnotations();
		if(showIntT && intTA!=null)intensityPlot.addAnnotation(intTA);
		
		if (showFrame){
			if(time_instant!=null){
				intensityPlot.addAnnotation(time_instant);
			}else{
				if(frameA!=null){
					intensityPlot.addAnnotation(frameA);
				}
			}
		}
		
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
	public void setDifT(double difT)
	{
		difT1A = new XYLineAnnotation(0, difT, time[time.length-1], difT, new BasicStroke(), Color.BLACK);
		difT2A = new XYLineAnnotation(0, -difT, time[time.length-1], -difT, new BasicStroke(), Color.BLACK);
	}
	public void setIntT(int intThreshold)
	{
		intTA = new XYLineAnnotation(0, intThreshold, time[time.length-1], intThreshold, new BasicStroke(), Color.BLACK);
	}
	public void setIntFET(int intFET)
	{
		intFETA = new XYLineAnnotation(0, intFET, time[time.length-1], intFET, new BasicStroke(), Color.GREEN);
	}
	public void setFrame(int frame)
	{
		frameA = new XYLineAnnotation(frame, 0, frame, 260, new BasicStroke(), Color.BLACK);
	}
	
	public void setTimeInstant (double time){
		time_instant = new XYLineAnnotation(time, 0, time, 260, new BasicStroke(), Color.BLACK);
	}
	
	public void setTime(double[] time){
		this.time=time;
	}
	
	public void updateTime(double[] time, int[]current_intensity, double[] current_gaussX, double[] current_gaussY)
	{
		this.time = time;
		this.intensity.clear();
		for (int i=0;i<time.length;i++){
			this.intensity.add(time[i],current_intensity[i]);
		}
		this.domainAxis.setRange(time[0],time[time.length -1]);
		this.domainAxis.setLabel("Time (s)");
		this.update();
		
	}
	
	

//	public void setDif(double[] dif)
//	{
//		assert dif.length == time.length;
//		this.dif.clear();
//		for(int i=0;i<time.length;i++)
//			this.dif.add(time[i],dif[i]);
//	}
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
	
	public void addFit(double[] x, double[] fit)
	{
		XYSeries xyfit = new XYSeries("Fit");
		for(int i=0;i < x.length;i++)
			xyfit.add(x[i],fit[i]);
		intensityFit.add(xyfit);
	}
	public void clearFits()
	{
		intensityFit.clear();
	}
	public void addEventMarker(int begin, int end)
	{
		eventsMarkers.add(new IntervalMarker(begin, end, Color.GREEN, new BasicStroke(), Color.BLACK, new BasicStroke(), 0.3f));
	}
	
	public void addEventMarker (double begin, double end){
		eventsMarkers.add(new IntervalMarker(begin, end, Color.GREEN, new BasicStroke(), Color.BLACK, new BasicStroke(), 0.3f));
	}
	
	public void clearEventMarkers()
	{
		eventsMarkers.clear();
	}
	public void showFrame(boolean showFrame)
	{
		this.showFrame = showFrame;
	}

	public void showIntT(boolean showIntT)
	{
		this.showIntT = showIntT;
	}
	public void showIntensity(boolean showIntensity)
	{
		this.showIntensity = showIntensity;
	}

	public void showFE(boolean showFE)
	{
		this.showFE = showFE;
	}
	public void showFit(boolean showFit)
	{
		this.showFit = showFit;
	}
	
}