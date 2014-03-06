package twoStateDiffusion;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HMMStateSequenceCanvas extends Canvas 
{
	int[] state;
	public HMMStateSequenceCanvas()
	{
		state = new int[]{1,2,1,2};		
	}
	
	public void setStateSequence(int[] state)
	{
		this.state = state;
		repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		float width = this.getWidth() / state.length;
		g2D.setStroke(new BasicStroke(width));
		
		for(int i=0;i<state.length;i++)
		{
			if(state[i]==1)
				g2D.setColor(Color.RED);
			else
				g2D.setColor(Color.BLUE);
			g2D.drawLine((int)(i*width+width*0.5), 0, (int)(i*width+width*0.5), this.getHeight());
		}
		
		/*
		g2D.setColor(Color.WHITE);
		g2D.setStroke(new BasicStroke(1f));
		g2D.drawLine(0, 0, 0, this.getHeight());
		g2D.drawLine(49, 0, 49, this.getHeight());
		g2D.drawLine(149, 0, 149, this.getHeight());
		g2D.drawLine(199, 0, 199, this.getHeight());
		Font serifFont = new Font("Arial", Font.PLAIN, 10);
	    g2D.setFont(serifFont);
    	g2D.drawString("width:"+width, 10,10);
		*/
		
		
	}
}
