package typeOfMotion;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class jFreeChartSegmentationParams extends JFreeChart
{
	double[] time;
	
	XYSeries d;
	XYSeries dev;
	XYSeries asym;
	XYSeriesCollection dSC;
	XYSeriesCollection devSC;
	XYSeriesCollection asymSC;
	XYSeriesCollection allSC;
	
	XYLineAndShapeRenderer lineRendererD;
	XYLineAndShapeRenderer lineRendererDev;
	XYLineAndShapeRenderer lineRendererAsym;
	XYLineAndShapeRenderer lineRendererAll;
	
	final ValueAxis domainAxis;
	final ValueAxis asymRangeAxis;
	
	XYPlot dplot;
	XYPlot devPlot;
	XYPlot asymPlot;
	XYPlot allPlot;
	
	
//	XYLineAnnotation 
	
	
	
	boolean show_D;
	boolean show_Dev;
	boolean show_Asym;

	public jFreeChartSegmentationParams(CombinedDomainXYPlot xyplot)
	{
		super(xyplot);
		show_D = true;
		show_Dev = true;
		show_Asym = true;
		
		dplot = new XYPlot();
		devPlot = new XYPlot();
		asymPlot = new XYPlot();
		allPlot = new XYPlot();
		
		
		d = new XYSeries("D");
		dSC = new XYSeriesCollection(d);

		dev = new XYSeries("Dev");
		devSC = new XYSeriesCollection(dev);
		
		asym = new XYSeries("Asym");
		asymSC = new XYSeriesCollection(asym);
		
		allSC = new XYSeriesCollection(d);
		allSC.addSeries(dev);
		allSC.addSeries(asym);
		
		lineRendererD = new XYLineAndShapeRenderer();
		lineRendererD.setSeriesShapesVisible(0, false);
		lineRendererD.setSeriesItemLabelsVisible(0,false);
		lineRendererD.setSeriesPaint(0, Color.BLUE);
		
		lineRendererDev = new XYLineAndShapeRenderer();
		lineRendererDev.setSeriesShapesVisible(0, false);
		lineRendererDev.setSeriesItemLabelsVisible(0,false);
		lineRendererDev.setSeriesPaint(0, Color.RED);
		
		lineRendererAsym = new XYLineAndShapeRenderer();
		lineRendererAsym.setSeriesShapesVisible(0, false);
		lineRendererAsym.setSeriesItemLabelsVisible(0,false);
		lineRendererAsym.setSeriesPaint(0, Color.GREEN);
		
		lineRendererAll = new XYLineAndShapeRenderer();
		lineRendererAll.setSeriesShapesVisible(0, false);
		lineRendererAll.setSeriesItemLabelsVisible(0,false);
		lineRendererAll.setSeriesShapesVisible(1, false);
		lineRendererAll.setSeriesItemLabelsVisible(1,false);
		lineRendererAll.setSeriesShapesVisible(2, false);
		lineRendererAll.setSeriesItemLabelsVisible(2,false);
		lineRendererAll.setSeriesPaint(0, Color.BLUE);
		lineRendererAll.setSeriesPaint(1, Color.RED);
		lineRendererAll.setSeriesPaint(2, Color.GREEN);
		
		
		(dplot).setRangeAxis(new NumberAxis("DIF"));
		(devPlot).setRangeAxis(new NumberAxis("DEV"));
		
		asymRangeAxis = new NumberAxis("ASYM");
		asymRangeAxis.setRange(0.0, 1.0);
		(asymPlot).setRangeAxis(asymRangeAxis);
		(allPlot).setRangeAxis(new NumberAxis("ALL"));
		
		dplot.setRenderer(lineRendererD);
		dplot.setDataset(dSC);
		devPlot.setRenderer(lineRendererDev);
		devPlot.setDataset(devSC);
		asymPlot.setRenderer(lineRendererAsym);
		asymPlot.setDataset(asymSC);
		allPlot.setRenderer(lineRendererAll);
		allPlot.setDataset(allSC);
		
		domainAxis = new NumberAxis("Time");
		
		((CombinedDomainXYPlot)this.getPlot()).setDomainAxis(domainAxis);
		((CombinedDomainXYPlot)this.getPlot()).add(dplot);
		((CombinedDomainXYPlot)this.getPlot()).add(devPlot);
		((CombinedDomainXYPlot)this.getPlot()).add(asymPlot);
		((CombinedDomainXYPlot)this.getPlot()).add(allPlot);
		((CombinedDomainXYPlot)this.getPlot()).setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		((CombinedDomainXYPlot)this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		this.setAntiAlias(true);
	}
	public void update()
	{
		dSC.removeAllSeries();
		dSC.addSeries(d);
		devSC.removeAllSeries();
		devSC.addSeries(dev);
		asymSC.removeAllSeries();
		asymSC.addSeries(asym);
		
		allSC.removeAllSeries();
		if(show_D)
			allSC.addSeries(d);	
		if(show_Dev)
			allSC.addSeries(dev);
		if(show_Asym)
			allSC.addSeries(asym);
	}
	public void setTime(double[] time)
	{
		this.time = time;
	}
	public void setD(double[] dd)
	{
		d.clear();
		for(int i=0;i < dd.length;i++)
			d.add(time[i],dd[i]);
	}	
	public void setDev(double[] devv)
	{
		dev.clear();
		for(int i=0;i < devv.length;i++)
			dev.add(time[i],devv[i]);
	}
	public void setAsym(double[] asymm)
	{
		asym.clear();
		for(int i=0;i < asymm.length;i++)
			asym.add(time[i],asymm[i]);
	}
	
	
	
	
	public void showD(boolean showD)
	{
		this.show_D = showD;
		update();
	}
	public void showAsym(boolean showAsym)
	{
		this.show_Asym = showAsym;
		update();
	}
	public void showDev(boolean showDev)
	{
		this.show_Dev = showDev;
		update();
	}
}
