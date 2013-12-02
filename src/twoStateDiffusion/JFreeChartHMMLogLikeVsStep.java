package twoStateDiffusion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartHMMLogLikeVsStep extends JFreeChart
{
	static boolean ocupado=false;
	double[] param1;
	double[] param2;
	double[] fit;
	
	boolean showAutomaticFit;
	
	XYSeries logLikeSeries;
	XYSeriesCollection llSC;
	XYSeries d1;
	XYSeries d2;
	XYSeriesCollection difSC;
	XYSeries p12;
	XYSeries p21;
	XYSeriesCollection probSC;
	
	
	ValueAxis llRangeAxis;
	ValueAxis difRangeAxis;
	ValueAxis probRangeAxis;
	float maxDomainAxis;
	float maxRangeAxis;
	
	String rangeUnit;	
	String domainUnit;
	
	double burn;
	
	int upf;
	XYPlot loglikePlot;
	XYPlot difPlot;
	XYPlot probPlot;
	
	XYDotRenderer dotRenderer;
	XYDotRenderer dotRenderer2;
	XYDotRenderer dotRenderer3;
	ValueAxis domainAxis;
	
	public JFreeChartHMMLogLikeVsStep(CombinedDomainXYPlot plot)
	{
		super(plot);
		loglikePlot = new XYPlot();
		difPlot = new XYPlot();
		probPlot = new XYPlot();
		rangeUnit = "?";
		domainUnit = "?";
				
		showAutomaticFit = false;
		
		logLikeSeries = new XYSeries("LogLike");
		llSC = new XYSeriesCollection(logLikeSeries);
		d1 = new XYSeries("D1");
		d2 = new XYSeries("D2");
		difSC = new XYSeriesCollection(d1);
		difSC.addSeries(d2);
		p12 = new XYSeries("P12");
		p21 = new XYSeries("P21");
		probSC = new XYSeriesCollection(p12);
		probSC.addSeries(p21);
		
		dotRenderer = new XYDotRenderer();
		dotRenderer.setSeriesPaint(0, Color.BLUE);
		dotRenderer.setSeriesPaint(1, Color.RED);
		dotRenderer.setDotHeight(2);
		dotRenderer.setDotWidth(2);
		
		llRangeAxis = new NumberAxis("LogLike");
		loglikePlot.setRangeAxis(llRangeAxis);
		loglikePlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		loglikePlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		loglikePlot.setDataset(llSC);
		loglikePlot.setRenderer(dotRenderer);
		
		dotRenderer2 = new XYDotRenderer();
		dotRenderer2.setSeriesPaint(0, Color.BLUE);
		dotRenderer2.setSeriesPaint(1, Color.RED);
		dotRenderer2.setDotHeight(2);
		dotRenderer2.setDotWidth(2);
		
		difRangeAxis = new LogarithmicAxis("Diffusion ("+rangeUnit+")");
		difPlot.setRangeAxis(difRangeAxis);
		difPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		difPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		difPlot.setDataset(difSC);
		difPlot.setRenderer(dotRenderer2);
		
		dotRenderer3 = new XYDotRenderer();
		dotRenderer3.setSeriesPaint(0, Color.BLUE);
		dotRenderer3.setSeriesPaint(1, Color.RED);
		dotRenderer3.setDotHeight(2);
		dotRenderer3.setDotWidth(2);
		
		probRangeAxis = new NumberAxis("Probability");
		probPlot.setRangeAxis(probRangeAxis);
		probPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		probPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		probPlot.setDataset(probSC);
		probPlot.setRenderer(dotRenderer3);
		
		domainAxis = new NumberAxis("Step ("+domainUnit+")");
		
		((CombinedDomainXYPlot) this.getPlot()).setDomainAxis(domainAxis);
//		((CombinedDomainXYPlot) this.getPlot()).add(loglikePlot);
		((CombinedDomainXYPlot) this.getPlot()).add(difPlot);
		((CombinedDomainXYPlot) this.getPlot()).add(probPlot);
		
		this.setAntiAlias(true);
	}
	public void cleanSeries()
	{
		logLikeSeries.clear();
		d1.clear();
		d2.clear();
		p12.clear();
		p21.clear();
		loglikePlot.clearAnnotations();
		difPlot.clearAnnotations();
		probPlot.clearAnnotations();
	}	
	public void addLogLikeValue(double value)
	{
		if(logLikeSeries.getItemCount()>0)
			logLikeSeries.add(this.logLikeSeries.getX(logLikeSeries.getItemCount()-1).doubleValue()+upf,value);
		else
			logLikeSeries.add(upf,value);
		llSC.removeAllSeries();
		llSC.addSeries(logLikeSeries);
	}
	public void addDifValue(double d1, double d2)
	{
		if(this.d1.getItemCount()>0)
		{
			this.d1.add(this.d1.getX(this.d1.getItemCount()-1).doubleValue()+upf,d1);
			this.d2.add(this.d2.getX(this.d2.getItemCount()-1).doubleValue()+upf,d2);
		}
		else
		{
			this.d1.add(upf,d1);
			this.d2.add(upf,d2);
		}
		difSC.removeAllSeries();
		difSC.addSeries(this.d1);
		difSC.addSeries(this.d2);
	}
	public void addProbValue(double p12, double p21)
	{
		if(this.p12.getItemCount()>0)
		{
			this.p12.add(this.p12.getX(this.p12.getItemCount()-1).doubleValue()+upf,p12);
			this.p21.add(this.p21.getX(this.p21.getItemCount()-1).doubleValue()+upf,p21);
		}
		else
		{
			this.p12.add(upf,p12);
			this.p21.add(upf,p21);
		}
		probSC.removeAllSeries();
		probSC.addSeries(this.p12);
		probSC.addSeries(this.p21);
	}
	public void setBurn(double value)
	{
		burn = value;
		XYLineAnnotation aBurn = new XYLineAnnotation(value, 0, value, logLikeSeries.getMaxY()+10, new BasicStroke(), Color.BLACK);
		loglikePlot.clearAnnotations();
		loglikePlot.addAnnotation(aBurn);
//		difPlot.clearAnnotations();
//		difPlot.addAnnotation(aBurn);
//		probPlot.clearAnnotations();
//		probPlot.addAnnotation(aBurn);
	}
	public void setUPF(int upf)
	{
		this.upf = upf;
	}
	
	public void setResults(double d1, double d2, double p12, double p21)
	{
		double last = this.p12.getX(this.p12.getItemCount()-1).doubleValue();
		XYLineAnnotation ad1 = new XYLineAnnotation(0, d1, last, d1, new BasicStroke(), Color.BLUE);
		XYLineAnnotation ad2 = new XYLineAnnotation(0, d2, last, d2, new BasicStroke(), Color.RED);
		XYLineAnnotation ap12 = new XYLineAnnotation(0, p12, last, p12, new BasicStroke(), Color.BLUE);
		XYLineAnnotation ap21 = new XYLineAnnotation(0, p21, last, p21, new BasicStroke(), Color.RED);
		difPlot.clearAnnotations();
		difPlot.addAnnotation(ad1);
		difPlot.addAnnotation(ad2);
		probPlot.clearAnnotations();
		probPlot.addAnnotation(ap12);
		probPlot.addAnnotation(ap21);
	}
	
	public void setRangeUnit(String rangeUnit) {
		this.rangeUnit = rangeUnit;
		llRangeAxis = new NumberAxis("? ("+rangeUnit+"^2)");
		//rangeAxis.setRange(0, maxRangeAxis);
		((XYPlot) this.getPlot()).setRangeAxis(llRangeAxis);
	}
	public void setDomainUnit(String domainUnit) {
		this.domainUnit = domainUnit;
		domainAxis = new NumberAxis("Step ("+domainUnit+")");
		//domainAxis.setRange(0, maxDomainAxis);
		((XYPlot) this.getPlot()).setDomainAxis(domainAxis);
	}
	public BufferedImage getImage(int width, int height)
	{
		return createBufferedImage(width, height);		
	}
}