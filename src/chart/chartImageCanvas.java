package chart;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class chartImageCanvas extends Canvas
{
	
	BufferedImage image;
	
	public chartImageCanvas()
	{
		//this.image = image;
		super();
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
		repaint();
	}	
	
	public void update(Graphics g)
	{	
		Graphics offgc;
		
		Image offscreen = null;
		Dimension d = new Dimension(this.getWidth(), this.getHeight());
	
		// create the offscreen buffer and associated Graphics
		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();
		// clear the exposed area
		offgc.setColor(getBackground());
		offgc.fillRect(0, 0, d.width, d.height);
		offgc.setColor(getForeground());
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(offscreen, 0, 0, this);
    }
	
	public void paint(Graphics g)
	{
		super.paint(g);
		//Graphics2D g2d = (Graphics2D)g;
		if(image!=null)
		{
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
			System.out.println("Imagen agregada al canvas");
		}
		else
		{
			System.out.println("Imagen nula");
		}
	}
	

}
