package overTime;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import bTools.BMaths;


public class JFreeChartOverTime extends JFreeChart 
{
	double[] time;
	XYLineAnnotation angleChangesMedia;
	
	
	XYSeries intensity;
	XYSeriesCollection xyI;
//	XYDotRenderer dotRendererI;
	XYLineAndShapeRenderer lrI;
	final ValueAxis rangeAxisI;
	
	XYSeries velocity;
	XYSeriesCollection xyV;
//	XYDotRenderer dotRendererV;
	XYLineAndShapeRenderer lrV;
	final ValueAxis rangeAxisV;
	
	XYSeries angle;
	XYSeries angleChanges;
	XYSeriesCollection xyA;
//	XYDotRenderer dotRendererA;
	XYLineAndShapeRenderer lrA; 
	final ValueAxis rangeAxisA;
	
	
	/*XYSeries fitI;
	XYSeriesCollection xyfitI;
	XYLineAndShapeRenderer lineRendererI;
	XYSeries fitV;
	XYSeriesCollection xyfitV;
	XYLineAndShapeRenderer lineRendererV;
	
	XYSeries fitA;
	XYSeriesCollection xyfitA;
	XYLineAndShapeRenderer lineRendererA;*/
	
	final ValueAxis domainAxis;
	
	boolean show_Intensity;
	boolean show_Velocity;
	boolean show_Angle;
	boolean show_AngleChanges;

	public JFreeChartOverTime(XYPlot xyplot)
	{
		super(xyplot);
		time = new double[]{0.0,1.0};
		angleChangesMedia = new XYLineAnnotation(0, 10, time[time.length-1], 10);
		
		show_Intensity = true;
		show_Velocity = false;
		show_Angle = false;
		show_AngleChanges = false;
		
		intensity = new XYSeries("Intensity");
		xyI = new XYSeriesCollection(intensity);
//		dotRendererI = new XYDotRenderer();
//		dotRendererI.setSeriesPaint(0, Color.RED);
//		dotRendererI.setDotHeight(2);
//		dotRendererI.setDotWidth(2);
		lrI = new XYLineAndShapeRenderer();
		lrI.setSeriesShapesVisible(0, false);
		lrI.setSeriesPaint(0, Color.RED);
		rangeAxisI = new NumberAxis("Intensity");
		
		velocity = new XYSeries("Velocity");
		xyV = new XYSeriesCollection(velocity);
//		dotRendererV = new XYDotRenderer();
//		dotRendererV.setSeriesPaint(0, Color.BLUE);
//		dotRendererV.setDotHeight(2);
//		dotRendererV.setDotWidth(2);
		lrV = new XYLineAndShapeRenderer();
		lrV.setSeriesShapesVisible(0, false);
		lrV.setSeriesPaint(0, Color.BLUE);
		rangeAxisV = new NumberAxis("Velocity ()");
		
		angle = new XYSeries("Angle");
		angleChanges = new XYSeries("AngleChanges");
		xyA = new XYSeriesCollection(angle);
		xyA.addSeries(angleChanges);
//		dotRendererA = new XYDotRenderer();
//		dotRendererA.setSeriesPaint(0, Color.MAGENTA);
//		dotRendererA.setSeriesPaint(1, Color.CYAN);
//		dotRendererA.setDotHeight(2);
//		dotRendererA.setDotWidth(2);
		lrA = new XYLineAndShapeRenderer();
		lrA.setSeriesShapesVisible(0, false);
		lrA.setSeriesPaint(0, Color.MAGENTA);
		lrA.setSeriesPaint(1, Color.CYAN);
		rangeAxisA = new NumberAxis("Angle (°)");
		rangeAxisA.setRange(0,360);
		
		
		/*fitI = new XYSeries("I smooth");
		xyfitI = new XYSeriesCollection(fitI);
		lineRendererI = new XYLineAndShapeRenderer();
		lineRendererI.setSeriesShapesVisible(0, false);
		lineRendererI.setSeriesPaint(0, Color.BLACK);
		
		fitV = new XYSeries("VFit");
		xyfitV = new XYSeriesCollection(fitV);
		lineRendererV = new XYLineAndShapeRenderer();
		lineRendererV.setSeriesShapesVisible(0, false);
		lineRendererV.setSeriesPaint(0, Color.BLACK);
		
		fitA = new XYSeries("Angle Fit");
		xyfitA = new XYSeriesCollection(fitA);
		lineRendererA = new XYLineAndShapeRenderer();
		lineRendererA.setSeriesShapesVisible(0, false);
		lineRendererA.setSeriesPaint(0, Color.BLACK);*/
		
		domainAxis = new NumberAxis("Time");
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
		
		((XYPlot) this.getPlot()).setRangeAxis(0,rangeAxisI);
		((XYPlot) this.getPlot()).setRangeAxis(1,rangeAxisV);
		((XYPlot) this.getPlot()).setRangeAxis(2,rangeAxisA);
		
		
		((XYPlot) this.getPlot()).setRenderer(0,lrI);
		((XYPlot) this.getPlot()).setRenderer(1,lrV);
		((XYPlot) this.getPlot()).setRenderer(2,lrA);
//		((XYPlot) this.getPlot()).setRenderer(0,dotRendererI);
//		((XYPlot) this.getPlot()).setRenderer(1,dotRendererV);
//		((XYPlot) this.getPlot()).setRenderer(2,dotRendererA);
//		((XYPlot) this.getPlot()).setRenderer(3,lineRendererI);
//		((XYPlot) this.getPlot()).setRenderer(4,lineRendererV);
//		((XYPlot) this.getPlot()).setRenderer(5,lineRendererA);
		
		((XYPlot) this.getPlot()).setDataset(0, xyI);
		((XYPlot) this.getPlot()).setDataset(1, xyV);
		((XYPlot) this.getPlot()).setDataset(2, xyA);
//		((XYPlot) this.getPlot()).setDataset(3, xyfitI);
//		((XYPlot) this.getPlot()).setDataset(4, xyfitV);
//		((XYPlot) this.getPlot()).setDataset(5, xyfitA);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(0,0);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(1,1);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(2,2);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(3,0);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(4,1);
		((XYPlot) this.getPlot()).mapDatasetToRangeAxis(5,2);
		
		
		((XYPlot) this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((XYPlot) this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
				
		this.setAntiAlias(true);
	}
	public void update()
	{
		((XYPlot) this.getPlot()).clearAnnotations();
		if(show_Intensity)
		{
			((XYPlot) this.getPlot()).setDataset(0,xyI);
			((XYPlot) this.getPlot()).setRangeAxis(0,rangeAxisI);
		}
		else
		{
			((XYPlot) this.getPlot()).setRangeAxis(0, null);
		}
		if(show_Velocity)
		{
			((XYPlot) this.getPlot()).setDataset(1,xyV);
			((XYPlot) this.getPlot()).setRangeAxis(1,rangeAxisV);
		}
		else
		{
			((XYPlot) this.getPlot()).setRangeAxis(1, null);
		}
		
		xyA.removeAllSeries();
//		xyfitA.removeAllSeries();
		if(show_Angle)
		{
			xyA.addSeries(angle);
//			xyfitA.addSeries(fitA);			
		}
		if(show_AngleChanges)
		{
			xyA.addSeries(angleChanges);
//			((XYPlot) this.getPlot()).addAnnotation(angleChangesMedia);
		}
		
		((XYPlot) this.getPlot()).setDataset(2,xyA);
		((XYPlot) this.getPlot()).setRangeAxis(2,rangeAxisA);
		
//		((XYPlot) this.getPlot()).setDataset(3,xyfitI);	
//		((XYPlot) this.getPlot()).setDataset(4,xyfitV);
//		((XYPlot) this.getPlot()).setDataset(5,xyfitA);
	}
	
	public void setTime(double[] time)
	{
		this.time = time;
	}
	public void setIntensities(double[] intensities)
	{
		intensity.clear();
		for(int i=0;i < time.length;i++)
			intensity.add(time[i],intensities[i]);
		xyI.removeAllSeries();
//		xyfitI.removeAllSeries();
		xyI.addSeries(intensity);
	}	
	public void setVelocities(double[] velocities)
	{
		velocity.clear();
		for(int i=0;i < velocities.length;i++)
			velocity.add(time[i+1],velocities[i]);
		xyV.removeAllSeries();
//		xyfitV.removeAllSeries();
		xyV.addSeries(velocity);
	}
	public void setAngles(double[] angles)
	{
		angle.clear();
		for(int i=0;i<angles.length;i++)
			angle.add(time[i+1],angles[i]);
	}
	public void setAngleChanges(double[] angleChanges)
	{
		this.angleChanges.clear();
		for(int i=0;i < angleChanges.length;i++)
			this.angleChanges.add(time[i+2],angleChanges[i]);
		double media = BMaths.avg(angleChanges);
		angleChangesMedia = new XYLineAnnotation(0, media, time[time.length-1], media);
	}
//	public void setFitI(double[] fiti)
//	{
//		assert fiti.length == time.length;
//		fitI.clear();
//		for(int i=0;i<time.length;i++)
//			fitI.add(time[i],fiti[i]);
//		xyfitI.removeAllSeries();
//		xyfitI.addSeries(fitI);
//	}
//	public void setFitV(double[] fitv)
//	{
//		assert fitv.length == time.length;
//		fitV.clear();
//		for(int i=0;i<time.length;i++)
//			fitV.add(time[i],fitv[i]);
//		xyfitV.removeAllSeries();
//		xyfitV.addSeries(fitV);
//	}
//	public void setFitA(double[] time, double[] fita)
//	{
//		assert fita.length == time.length;
//		fitA.clear();
//		for(int i=0;i<time.length;i++)
//			fitA.add(time[i],fita[i]);
//		xyfitA.removeAllSeries();
//		xyfitA.addSeries(fitA);
//	}
	public void showIntensity(boolean showIntensity)
	{
		this.show_Intensity = showIntensity;
		update();
	}
	public void showAngle(boolean showAngle)
	{
		this.show_Angle = showAngle;
		update();
	}
	public void showAngleChanges(boolean showAngleChanges)
	{
		this.show_AngleChanges = showAngleChanges;
		update();
	}
	public void showVelocity(boolean showVelocity)
	{
		this.show_Velocity = showVelocity;
		update();
	}
}
