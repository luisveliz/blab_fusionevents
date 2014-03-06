package direction;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.ui.TextAnchor;


public class MyPolarPlot extends PolarPlot
{
	 @Override
     protected List<NumberTick> refreshAngleTicks() {
         List<NumberTick> ticks = new ArrayList<NumberTick>();
         // produce some ticks, e.g. NumberTick instances
         ticks.add(new NumberTick(0, "90", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(45, "45", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(90, "0", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(135, "315", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(180, "270", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(225, "225", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(270, "180", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         ticks.add(new NumberTick(315, "135", TextAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, 0));
         return ticks;
     }
	 
	 @Override
	 protected void drawGridlines(Graphics2D g2, Rectangle2D dataArea, List angularTicks, List radialTicks) 
	 {

		// no renderer, no gridlines...
		if (this.getRenderer() == null) 
		{
			return;
		}
		
		
		// draw the domain grid lines, if any...
		if (isAngleGridlinesVisible()) 
		{
			Stroke gridStroke = getAngleGridlineStroke();
			Paint gridPaint = getAngleGridlinePaint();
			if ((gridStroke != null) && (gridPaint != null)) 
			{
				this.getRenderer().drawAngularGridLines(g2, this, angularTicks, dataArea);
			}
		}
		
		// draw the radius grid lines, if any...
		if (isRadiusGridlinesVisible()) 
		{
			Stroke gridStroke = getRadiusGridlineStroke();
			Paint gridPaint = getRadiusGridlinePaint();
			if ((gridStroke != null) && (gridPaint != null)) 
			{
				this.getRenderer().drawRadialGridLines(g2, this, this.getAxis(), radialTicks, dataArea);
			}
		}
	}
}
