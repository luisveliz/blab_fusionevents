package twoStateDiffusion;



import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.text.NumberFormat;


/**
 * @author Luis
 *
 */
public class HMMXYCanvasPro extends Canvas
{
	HMMTraj traj;
	double[] x_scaled;
	double[] y_scaled;
	double centroX;
	double centroY;
	
	double maxX, maxY, minX, minY;
	
	double scaleX, scaleY;
	
	double zoom;
	
	String unit;
	double factor;
	
	boolean antialiasing, doubleBuffer;
	boolean showAllTrajs, showPoints, showGaps, showSubtraj, showCorrals;	

	/**
	 * @param eX
	 * @param eY
	 */
	public HMMXYCanvasPro()
	{
		super();		
		setBackground(Color.BLACK);
		traj = new HMMTraj(100, 100, 0.005, 0.1, 0.01, 0.1, 0.05);
		x_scaled = new double[traj.getX().length];
		y_scaled = new double[traj.getY().length];
		
		
		scaleX=1.0f;
		scaleY=1.0f;
		zoom = 1.0f;//100%
		
		minX=0;
		minY=0;
		maxX=0;
		maxY=0;
		
		//frames = new int[0];
		antialiasing = true;
		doubleBuffer = true;
		showAllTrajs = false;
		showPoints = false;
		showGaps = false;
		showSubtraj = false;
		showCorrals = false;
		
		findMiniMaxs();
		scaling();
		this.repaint();
	}
	/*----------------------------Set Data-----------------------*/
	/**
	 * @param eX
	 * @param eY
	 */
	public void setTraj(HMMTraj traj)
	{
		this.traj = traj;
		x_scaled = new double[traj.getX().length];
		y_scaled = new double[traj.getY().length];
		findMiniMaxs();
		scaling();
		this.repaint();
	}
	private void findMiniMaxs()
	{
		minX=traj.getX()[0];
		minY=traj.getY()[0];
		maxX=traj.getX()[0];
		maxY=traj.getY()[0];
		
		for(int i=0;i<traj.getX().length;i++)
		{
			if(traj.getX()[i]>maxX)
				maxX=traj.getX()[i];
			if(traj.getX()[i]<minX)
				minX=traj.getX()[i];			
			if(traj.getY()[i]>maxY)
				maxY=traj.getY()[i];
			if(traj.getY()[i]<minY)
				minY=traj.getY()[i];
		}
	}
	private void scaling()
	{
		this.scaleX=(this.getWidth()-20)/(maxX-minX);
		this.scaleY=(this.getHeight()-20)/(maxY-minY);
		if(scaleX>scaleY)
		{
			scaleX=scaleY;
		}
		else
		{
			scaleY=scaleX;
		}
		System.out.println("Scaling...  scaleX:"+scaleX+" scaleY:"+scaleY);
		//System.out.println("XOriginal     XScaling");
		for(int i=0;i<traj.getX().length;i++)
		{
			x_scaled[i] = (traj.getX()[i]-minX)*scaleX*zoom;
			y_scaled[i] = (traj.getY()[i]-minY)*scaleY*zoom;
		}
	}
	
	/*---------------------------Visualization Settings-----------------------*/
	/**
	 * @param showGaps
	 */
	public void setShowPoints(boolean showPoints) 
	{
		this.showPoints = showPoints;
		this.repaint();
	}
	
	public void setShowAllTrajs(boolean showAllTrajs) 
	{
		this.showAllTrajs = showAllTrajs;
		this.repaint();
	}
	
	/**
	 * @param showGaps
	 */
	public void setShowGaps(boolean showGaps) 
	{
		this.showGaps = showGaps;
		this.repaint();
	}
	public void setShowSubtraj(boolean showSubtraj) 
	{
		this.showSubtraj = showSubtraj;
		this.repaint();
	}
	public void setShowCorrals(boolean showCorrals) 
	{
		this.showCorrals = showCorrals;
		this.repaint();
	}


	/**
	 * @param zoom
	 */
	public void setZoom(int zoom)
	{		
		this.zoom = ((float)zoom);//100;
		scaling();
		this.repaint();
	}
	
	/**
	 * @param antialiasing
	 */
	public void setAntialiasing(boolean antialiasing) 
	{
		this.antialiasing = antialiasing;
		this.repaint();
	}

	/**
	 * @param doubleBuffer
	 */
	public void setDoubleBuffer(boolean doubleBuffer) 
	{
		this.doubleBuffer = doubleBuffer;
	}
	

	public void setUnit(String unit)
	{
		this.unit = unit;
		this.repaint();		
	}
	
	public void setUnitFactor(float factor)
	{
		this.factor = factor;
		this.repaint();
		
	}
	/*----------------------------Painting-----------------------*/
	/* (non-Javadoc)
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		//System.out.println("set bounds");
		super.setBounds(x, y, width, height);
		//System.out.println("maxX:"+maxX+" minX:"+minX);
		//System.out.println("maxY:"+maxY+" minY:"+minY);
		if(traj!=null)
		{
			scaling();
		}
		this.repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) 
	{	
		if(doubleBuffer)
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
		else
		{
			paint(g);
		}
    }
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{		
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		if(antialiasing)
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		//g2D.scale(zoom, zoom);	
		
		//drawChimuchinaGeneral(g2D);
		drawUnitReference(g2D);		
		//g2D.translate((this.getWidth()-20)/2-maxXscaled/2+10, (this.getHeight()-20)/2-maxYscaled/2+10);
		g2D.translate(10,10);
		//g2D.translate(((this.getWidth()-20)/2)-(maxX-minX)/2,((this.getHeight()-20)/2)-(maxY-minY)/2);
		//g2D.translate(((this.getWidth()-20)/2)-minX-(maxX-minX)/2,((this.getHeight()-20)/2)-minY-(maxY-minY)/2);
		//g2D.scale(zoom, zoom);
		
		//drawManualSubtraj(g2D);
		
		drawTraj(g2D);
		if(showPoints)
			drawPoints(g2D);
	}

	/**
	 * @param g2D
	 */
	private void drawChimuchinaGeneral(Graphics2D g2D)
	{
		g2D.setStroke(new BasicStroke(2f));	    
		g2D.setColor(Color.GRAY);
		
		g2D.fillOval(10-10, 10-10, 20,20);
		g2D.fillOval(this.getWidth()-10-10, 10-10, 20,20);
		g2D.fillOval(this.getWidth()-10-10, this.getHeight()-10-10, 20,20);
		g2D.fillOval(10-10, this.getHeight()-10-10, 20,20);
		
		//g2D.setColor(Color.GRAY);
		g2D.draw(new Line2D.Float(10,10,this.getWidth()-10,10));
		g2D.draw(new Line2D.Float(this.getWidth()-10,10,this.getWidth()-10,this.getHeight()-10));
		g2D.draw(new Line2D.Float(this.getWidth()-10,this.getHeight()-10,10,this.getHeight()-10));
		g2D.draw(new Line2D.Float(10,this.getHeight()-10,10,10));		
	}
	
	private void drawUnitReference(Graphics2D g2D)
	{
		g2D.setStroke(new BasicStroke(4f));	    
		g2D.setColor(Color.YELLOW);
		
		//Unit reference bar: 60px
		g2D.draw(new Line2D.Float(this.getWidth()-80,this.getHeight()-20,this.getWidth()-20,this.getHeight()-20));

		
		//Conversions
		 
		float unitRefBarLength = ((float)((60/scaleX/zoom) * factor));
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);		
		
		
		Font serifFont = new Font("Arial", Font.BOLD, 14);
	    g2D.setFont(serifFont);
	    g2D.drawString(nf.format(unitRefBarLength)+" "+unit, this.getWidth()-75,this.getHeight()-5);
	}
	
	private void drawTraj(Graphics2D g2D)
	{
		
		g2D.setStroke(new BasicStroke(1f));
		g2D.setColor(Color.WHITE);
		for(int j=1;j<traj.getX().length;j++)
		{
			if(traj.getGState()[j-1]==1)
			{
				g2D.setColor(Color.BLUE);
			}
			else
			{
				g2D.setColor(Color.RED);
			}
			//g.drawLine(Math.round(x[i-1]), Math.round(y[i-1]), Math.round(x[i]), Math.round(y[i]));
			g2D.draw(new Line2D.Double(x_scaled[j-1], y_scaled[j-1], x_scaled[j], y_scaled[j]));
		}
	}
	
	/**
	 * @param g2D
	 */
	private void drawPoints(Graphics2D g2D)
	{
		g2D.setColor(Color.ORANGE);		
		for(int i=1;i<traj.getX().length-1;i++)
		{
			g2D.fillOval(Math.round((float)traj.getX()[i])-3, Math.round((float)traj.getY()[i])-3, 6, 6);
		}
		g2D.setColor(Color.RED);
		g2D.fillOval(Math.round((float)x_scaled[0])-5, 
					 Math.round((float)y_scaled[0])-5, 10, 10);		
		g2D.fillOval(Math.round((float)x_scaled[x_scaled.length-1])-5, 
				     Math.round((float)y_scaled[y_scaled.length-1])-5, 10, 10);
	}
}

