package direction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;



public class JFreeChartFrequencyPolarChart extends JFreeChart
{
	DefaultPolarItemRenderer renderer;
	ValueAxis radiusAxis;
	XYSeriesCollection dataset;
    XYSeries selectedTraj;
    XYSeries selectedSet;
	
	public JFreeChartFrequencyPolarChart(MyPolarPlot plot)
	{
		super(plot);
		Color[] colors = {Color.BLUE,Color.RED, Color.GREEN, Color.BLACK, Color.CYAN, Color.MAGENTA, Color.ORANGE};

		renderer = new DefaultPolarItemRenderer();
		for(int i = 0;i<colors.length;i++)
			renderer.setSeriesPaint(i, colors[i]);
		((PolarPlot)getPlot()).setRenderer(renderer);
		radiusAxis = new NumberAxis();
		radiusAxis.setRange(0.0, 1.0);
		((PolarPlot)getPlot()).setAxis(radiusAxis);
		
		dataset = new XYSeriesCollection();
		selectedTraj = new XYSeries("Selected Traj");
		selectedSet = new XYSeries("Selected Set");
        dataset.addSeries(selectedTraj);
        dataset.addSeries(selectedSet);
        ((PolarPlot)getPlot()).setDataset(dataset);
		setAntiAlias(true);
	}

	public void clearSeries()
	{
		dataset.removeAllSeries();
	}
	
	public void setSelectedTraj(double[] angle , double[] value)
	{
		selectedTraj.clear();
		for(int i=0; i<angle.length;i++)
			selectedTraj.add(450-angle[i],value[i]);
		dataset.addSeries(selectedTraj);
	}	
	public void setSelectedSet(double[] angle , double[] value)
	{
		selectedSet.clear();
		for(int i=0; i<angle.length;i++)
			selectedSet.add(450-angle[i],value[i]);
		dataset.addSeries(selectedSet);
	}
	public void addTrajSetSerie(double[] angle , double[] value, String setName)
	{
		XYSeries serie = new XYSeries(setName);
		for(int i=0; i<angle.length;i++)
			serie.add(450-angle[i],value[i]);
		dataset.addSeries(serie);
	}
	
}
