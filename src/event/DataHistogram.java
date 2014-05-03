package event;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

public class DataHistogram extends JFreeChart{
	double [] dataArray;
	int nClasses;
	
	public DataHistogram(XYPlot histogram){
		super(histogram);
	}
	
	public void updateNumberOfClasses(int nclasses){
		nClasses=nclasses;
	}
	
	public void updateData(double[] newData){
		dataArray=newData;
	}
}
