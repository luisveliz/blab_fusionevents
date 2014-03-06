package frequency;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.DomainOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;

public class JFreeChartFrequencyBarChart extends JFreeChart
{
	
	HistogramDataset dataset;
//	IntervalXYDataset dataset;
	
	XYBarRenderer barRenderer;
	ValueAxis domainAxis;
	ValueAxis rangeAxis;
	
	public JFreeChartFrequencyBarChart(XYPlot plot)
	{
		super(plot);
		dataset = new HistogramDataset();
		barRenderer = new XYBarRenderer();
		barRenderer.setSeriesPaint(0, Color.BLUE);
		barRenderer.setDrawBarOutline(false);
		domainAxis = new NumberAxis();
		rangeAxis = new NumberAxis("Frequency");			
		
		((XYPlot)this.getPlot()).setDomainAxis(domainAxis);
		((XYPlot)this.getPlot()).setRangeAxis(rangeAxis);
		((XYPlot)this.getPlot()).setRenderer(barRenderer);
		((XYPlot)this.getPlot()).setDataset(dataset);
		((XYPlot)this.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
				
		this.setAntiAlias(true);
	}
	public void setData(String name, double[] values, int nbins, boolean log)
	{
		if(log)
			domainAxis = new LogarithmicAxis("");
		else
			domainAxis = new NumberAxis();
		
		dataset = new HistogramDataset();
		dataset.addSeries(name, values, nbins);		
		((XYPlot)this.getPlot()).setDataset(dataset);
		((XYPlot)this.getPlot()).setDomainAxis(domainAxis);
	}
	public void setNormalized(boolean normalized)
	{
		if(normalized)
			dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		else
			dataset.setType(HistogramType.FREQUENCY);
	}
	
	
}
