package visualization;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import bTools.BMaths;
import bTools.BNF;

import main.Thinker;
import data.Metrics;
import data.Trajectory;


/**
 * @author Luis
 *
 */
public class XYCanvas extends Canvas
{
	
	double height_movie, width_movie;//in pixels
	
	//int[] frames;
	
	Trajectory[] trajs_original;
	double[][][] trajs;
	
	int selectedTraj;	
	
	double maxX, maxY, minX, minY;
	double maxXscaled, maxYscaled;
	
	double scaleX, scaleY;
	
	double zoom;
	
	int initialFrame, finalFrame;
	//int initialPoint, finalPoint;
	
	//String unit;
	//double factor;
	
	//double[] x_original,y_original,x,y;
	Metrics metrics;
	
	
	boolean antialiasing, doubleBuffer;
	boolean showAllTrajs, showPoints, showGaps, showSubtraj, showInfo, showVectors, show2State;	
	
	int beginSubtraj, endSubtraj;
	
	double[][] corrales_original;
	double[][] corrales;

	int xPos;
	int yPos;
	int xPress;
	int yPress;
	int xDrag;
	int yDrag;
	/**
	 * @param eX
	 * @param eY
	 */
	public XYCanvas()
	{
		super();		
		setBackground(Color.BLACK);
		
		metrics = new Metrics();
		trajs_original = new Trajectory[0];
		
		trajs = new double[0][0][0];		
		selectedTraj = 0;
		
		corrales_original = new double[0][0];
		corrales = new double[0][0];
		
		scaleX=1.0f;
		scaleY=1.0f;
		zoom = 1.0f;//100%
		
		minX=0;
		minY=0;
		maxX=0;
		maxY=0;
		maxXscaled=0;
		maxYscaled=0;
		//findMaxs();
		
		initialFrame = 0;
		finalFrame = 0;

		antialiasing = true;
		doubleBuffer = true;
		showAllTrajs = true;
		showPoints = false;
		showGaps = false;
		showSubtraj = true;
		showInfo = true;
		showVectors = false;
	}
	
	
	/*----------------------------Set Data-----------------------*/
	/**
	 * @param eX
	 * @param eY
	 */
	public void setTrajs(Thinker thinker, double height_movie, double width_movie)
	{
		//alto cambiado por ancho pa que coincida
		
			
		this.selectedTraj=0;
		
		this.metrics = thinker.getSelectedSet().getMetrics();
		
		this.trajs_original = thinker.getAllFilteredTrajs();
		
		if(height_movie==0 && width_movie==0 & trajs_original.length>0)
		{
			double[][][] trajs = new double[trajs_original.length][3][];
			//TODO cambiar calculo de max min!!
			double maxX = trajs_original[0].getX()[0];
			double maxY = trajs_original[0].getX()[1];
			double minX = maxX;
			double minY = maxY;
			double aux;
			for(int i=0;i<trajs_original.length;i++)
			{
				trajs[i][0] = trajs_original[i].getX(); 
				trajs[i][1] = trajs_original[i].getY();
				trajs[i][2] = trajs_original[i].getDoubleFrames();
				aux =  BMaths.max(trajs[i][0]);
				if(aux>maxX)
					maxX = aux;
				if(aux<minX)
					minX = aux;
				aux = BMaths.min(trajs[i][1]);					
				if(aux>maxY)
					maxY = aux;
				if(aux<minY)
					minY = aux;
			}
			this.height_movie = maxY-minY;
			this.width_movie = maxX-minX;
		}
		else
		{
			this.height_movie = (double) height_movie;
			this.width_movie = (double) width_movie;
		}
		
		this.trajs = new double[trajs_original.length][2][];//0:x, 1:y, 2:trajectory_frame
		
		this.scaleX=1.0f;
		this.scaleY=1.0f;
		minX=0;
		minY=0;
		maxX=0;
		maxY=0;
		maxXscaled=0;
		maxYscaled=0;
		//findMaxs();
				
		this.scaleX=(this.getWidth()-20)/this.width_movie;
		this.scaleY=(this.getHeight()-20)/this.height_movie;
		
		if(scaleX>scaleY)
			scaleX=scaleY;
		else
			scaleY=scaleX;
		scaling();
		findMiniMaxs();
		//findMaxsScaled();
		//this.update(this.getGraphics());
		this.repaint();
	}
	
	public void setSelectedTraj(int selectedTraj) 
	{
		this.selectedTraj = selectedTraj;
		findMiniMaxs();
		this.repaint();
	}
	
	/**
	 * @param initialFrame
	 * @param finalFrame
	 */
	public void setInitialFinalFrame(int initialFrame, int finalFrame)
	{
		this.initialFrame = initialFrame;
		this.finalFrame = finalFrame;
		this.repaint();
	}
	public void setBeginEndSubtraj(int beginSubtraj, int endSubtraj)
	{
		this.beginSubtraj = beginSubtraj;
		this.endSubtraj = endSubtraj;
	}
	public void setCorrales(double[][] corrales)
	{
		this.corrales_original = corrales;
		this.corrales = new double[corrales.length][3];
		scaling();
		this.repaint();
		
	}
	public void setMousePressed(int x, int y)
	{
		xPress=x;
		yPress=y;
		xDrag=x;
		yDrag=y;
		repaint();
	}
	public void setMouseDragged(int x, int y)
	{
		xDrag = x;
		yDrag = y;
		repaint();
	}
	public void setMouseReleased()
	{
		xPos += xDrag-xPress;
		yPos += yDrag-yPress;
		xPress=xDrag;
		yPress=yDrag;
	}
	
	public void clearDrag()
	{
		xPress=xDrag;
		yPress=yDrag;
		xPos=0;
		yPos=0;
	}
	
	
	/*--------------------------Processing data-------------------*/
	/**
	 * 
	 */
	private void scaling()
	{
		for(int i=0;i<trajs.length;i++)
		{
			trajs[i][0] = trajs_original[i].getY();
			trajs[i][1] = trajs_original[i].getX();
			
			for(int j=0;j<trajs[i][0].length;j++)
			{
				trajs[i][0][j] *= scaleX*zoom;
				trajs[i][1][j] *= scaleX*zoom;
			}
		}
		/*for(int i=0;i<this.corrales_original.length;i++)
		{			
			corrales[i][0]=corrales_original[i][0]*scaleX*zoom;
			corrales[i][1]=corrales_original[i][1]*scaleY*zoom;
			corrales[i][2]=corrales_original[i][2]*scaleX*zoom;
			System.out.println("El corral original es "+corrales_original[i][0]+","+corrales_original[i][1]+" y radio"+corrales_original[i][2]);
			System.out.println("El corral escalado quedo con centroide:"+corrales[i][0]+","+corrales[i][1]+" y radio"+corrales[i][2]);
			
		}*/
		//this.repaint();
	}
	private void findMiniMaxs()
	{
		minX=trajs[selectedTraj][0][0];
		minY=trajs[selectedTraj][1][0];
		maxX=trajs[selectedTraj][0][0];
		maxY=trajs[selectedTraj][1][0];
		
		for(int i=0;i<trajs[selectedTraj][0].length;i++)
		{
			if(trajs[selectedTraj][0][i]>maxX)
				maxX=trajs[selectedTraj][0][i];
			if(trajs[selectedTraj][0][i]<minX)
				minX=trajs[selectedTraj][0][i];			
			if(trajs[selectedTraj][1][i]>maxY)
				maxY=trajs[selectedTraj][1][i];
			if(trajs[selectedTraj][1][i]<minY)
				minY=trajs[selectedTraj][1][i];
		}
	}
	
	
	/*-
	 * ---------------------------Visualization Settings-----------------------*/
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
	public void setShowInfo(boolean showInfo) 
	{
		this.showInfo = showInfo;
		this.repaint();
	}
	public void showVectors(boolean showVectors)
	{
		this.showVectors = showVectors;
		this.repaint();
	}
	public void show2State(boolean show2State)
	{
		this.show2State = show2State;
		this.repaint();
	}


	/**
	 * @param zoom
	 */
	public void setZoom(int zoom)
	{		
		System.out.print(zoom);
		this.zoom = (double) (zoom*0.1);//100;
		scaling();
		findMiniMaxs();
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
	

	/*public void setUnit(String unit)
	{
		this.unit = unit;
		this.repaint();		
	}*/
	
	/*public void setUnitFactor(double factor)
	{
		this.factor = factor;
		this.repaint();
		
	}*/
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
		this.scaleX=(this.getWidth()-20)/this.width_movie;
		this.scaleY=(this.getHeight()-20)/this.height_movie;
		if(scaleX>scaleY)
		{
			scaleX=scaleY;
		}
		else
		{
			scaleY=scaleX;
		}
		if(trajs.length>0)
		{
			scaling();
			findMiniMaxs();
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

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//drawChimuchinaGeneral(g2D);
		if(showInfo && trajs_original.length>0)
			drawInfo(g2D);
		drawUnitReference(g2D);	
		
		
		g2D.translate(xPos + xDrag-xPress,yPos+ yDrag-yPress);
		
//		IJ.log("translate: "+(xPos+xDrag-xPress)+","+ (yPos+yDrag-yPress));

		//g2D.scale(zoom, zoom);	
		
			
		//g2D.translate((this.getWidth()-20)/2-maxXscaled/2+10, (this.getHeight()-20)/2-maxYscaled/2+10);
		g2D.translate(10,10);
		//g2D.translate(((this.getWidth()-20)/2)-(maxX-minX)/2,((this.getHeight()-20)/2)-(maxY-minY)/2);
		g2D.translate(((this.getWidth()-20)/2)-minX-(maxX-minX)/2,((this.getHeight()-20)/2)-minY-(maxY-minY)/2);
		//g2D.scale(zoom, zoom);
		
		drawTrajs(g2D);
		if(!show2State)
			drawManualSubtraj(g2D);
		if(showPoints)
			drawPoints(g2D);
		if(showVectors)
			drawVectors(g2D);
		if(showSubtraj)
			drawDefinedSubtraj(g2D);
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
	
	private void drawInfo(Graphics2D g2D)
	{
		
		//TODO arreglar coherencias entre initial/final frames y Pro 
		g2D.setStroke(new BasicStroke(2f));	    
		Font serifFont = new Font("Arial", Font.PLAIN, 10);
	    g2D.setFont(serifFont);
	    if(trajs_original[selectedTraj].getFramesPro()[initialFrame-1]==0)
	    	g2D.setColor(Color.RED);
	    else
	    	g2D.setColor(Color.GREEN);
    	g2D.drawString("Initial Frame:"+initialFrame, 10,this.getHeight()-70);
    	g2D.drawString("x:"+BNF.sF(trajs_original[selectedTraj].getParticlesPro()[initialFrame-1].getX())+
    				"   y:"+BNF.sF(trajs_original[selectedTraj].getParticlesPro()[initialFrame-1].getY()), 10,this.getHeight()-60);
//    	g2D.drawString("Angle:"+BNF.sF(trajs_original[selectedTraj].getAnglesPro()[initialFrame-1]), 10,this.getHeight()-50);

    	if(trajs_original[selectedTraj].getFramesPro()[finalFrame-1]==0)
	    	g2D.setColor(Color.RED);
	    else
	    	g2D.setColor(Color.GREEN);
	    g2D.drawString("Final Frame:"+finalFrame, 10,this.getHeight()-40);
	    g2D.drawString("x:"+BNF.sF(trajs_original[selectedTraj].getParticlesPro()[finalFrame-1].getX())+
    		        "   y:"+BNF.sF(trajs_original[selectedTraj].getParticlesPro()[finalFrame-1].getY()), 10,this.getHeight()-30);
//	    g2D.drawString("Angle:"+BNF.sF(trajs_original[selectedTraj].getAnglesPro()[finalFrame-1]), 10,this.getHeight()-20);
	}
	private void drawUnitReference(Graphics2D g2D)
	{
		g2D.setStroke(new BasicStroke(4f));	    
		g2D.setColor(Color.YELLOW);
		
		//Unit reference bar: 60px
		g2D.draw(new Line2D.Float(this.getWidth()-80,this.getHeight()-20,this.getWidth()-20,this.getHeight()-20));
		
		//Conversions
		double unitRefBarLength = (60/scaleX/zoom) * metrics.getDistanceFactor();
		
		Font serifFont = new Font("Arial", Font.BOLD, 14);
	    g2D.setFont(serifFont);
	    g2D.drawString(BNF.sF(unitRefBarLength)+" "+ metrics.getDistUnittoString(), this.getWidth()-75,this.getHeight()-5);
	}
	
	
	private void drawTrajs(Graphics2D g2D)
	{
		g2D.setStroke(new BasicStroke(1f));
		
		if(show2State)
		{
			for(int i=0; i<trajs.length; i++)
			{
				if(i==selectedTraj)
				{
					g2D.setStroke(new BasicStroke(2f));
					for(int j=1;j<trajs[selectedTraj][0].length;j++)
					{				
//						if(trajs_original[selectedTraj].guessState[j-1]==1 && showGaps)
						if(trajs_original[selectedTraj].guessState[j-1]==1)
							g2D.setColor(Color.BLUE);
						else
							g2D.setColor(Color.RED);
						g2D.draw(new Line2D.Double(trajs[selectedTraj][0][j-1], trajs[selectedTraj][1][j-1],trajs[selectedTraj][0][j],trajs[selectedTraj][1][j]));
					}
				}
				else if(showAllTrajs)
				{
					g2D.setStroke(new BasicStroke(1f));
					for(int j=1;j<trajs[i][0].length;j++)
					{
						if(trajs_original[i].guessState[j-1]==1)
							g2D.setColor(Color.BLUE);
						else
							g2D.setColor(Color.RED);
						//g.drawLine(Math.round(x[i-1]), Math.round(y[i-1]), Math.round(x[i]), Math.round(y[i]));
						g2D.draw(new Line2D.Double(trajs[i][0][j-1], trajs[i][1][j-1], trajs[i][0][j], trajs[i][1][j]));
					}				
				}
			}
		}
		else
		{
			for(int i=0; i<trajs.length; i++)
			{
				if(i==selectedTraj)
				{
					g2D.setColor(Color.WHITE);
					for(int j=1;j<trajs[i][0].length;j++)
					{
						//g.drawLine(Math.round(x[i-1]), Math.round(y[i-1]), Math.round(x[i]), Math.round(y[i]));
						g2D.draw(new Line2D.Double(trajs[i][0][j-1], trajs[i][1][j-1], trajs[i][0][j], trajs[i][1][j]));
					}
				}
				else if(showAllTrajs)
				{
					g2D.setColor(Color.LIGHT_GRAY);
					for(int j=1;j<trajs[i][0].length;j++)
					{
						//g.drawLine(Math.round(x[i-1]), Math.round(y[i-1]), Math.round(x[i]), Math.round(y[i]));
						g2D.draw(new Line2D.Double(trajs[i][0][j-1], trajs[i][1][j-1], trajs[i][0][j], trajs[i][1][j]));
					}				
				}
			}
		}		
	}
	
	/**
	 * @param g2D
	 */
	private void drawManualSubtraj(Graphics2D g2D)
	{
		//System.out.println("intial:"+initialFrame+" final:"+finalFrame);
		
		if(initialFrame!=0 && finalFrame!=0)
		{
			
			for(int i=1;i<trajs[selectedTraj][0].length;i++)
			{
				g2D.setStroke(new BasicStroke(2f));
				if(initialFrame<=trajs_original[selectedTraj].getFrame(i-1) && trajs_original[selectedTraj].getFrame(i)<=finalFrame)
				{
					if(trajs_original[selectedTraj].getFrame(i)-trajs_original[selectedTraj].getFrame(i-1)==1)
						g2D.setColor(Color.GREEN);						
					else
						g2D.setColor(Color.RED);
					g2D.draw(new Line2D.Double(trajs[selectedTraj][0][i-1], trajs[selectedTraj][1][i-1],
											  trajs[selectedTraj][0][i],   trajs[selectedTraj][1][i]));
				}
			}
			g2D.setStroke(new BasicStroke(1f));
			for(int i=1;i<trajs[selectedTraj][0].length;i++)
			{				
				g2D.setColor(Color.WHITE);
				g2D.draw(new Line2D.Double(trajs[selectedTraj][0][i-1], trajs[selectedTraj][1][i-1],trajs[selectedTraj][0][i],trajs[selectedTraj][1][i]));
			}
		}
	}


	private void drawDefinedSubtraj(Graphics2D g2D)
	{
		
		g2D.setColor(Color.BLUE);
		g2D.setStroke(new BasicStroke(0.7f));
		
		if(beginSubtraj!=0 && endSubtraj!=0)
		{
			for(int i=1;i<trajs[selectedTraj][0].length;i++)
			{
				if(beginSubtraj<=trajs_original[selectedTraj].getFrame(i-1) && trajs_original[selectedTraj].getFrame(i)<=endSubtraj)
				{					
					g2D.draw(new Line2D.Double(trajs[selectedTraj][0][i-1], trajs[selectedTraj][1][i-1],
											  trajs[selectedTraj][0][i], trajs[selectedTraj][1][i]));
				}
			}
		}	
		
	}	
		
	/**
	 * @param g2D
	 */
	private void drawPoints(Graphics2D g2D)
	{
		g2D.setColor(Color.ORANGE);		
		for(int i=0;i<trajs[selectedTraj][0].length;i++)
		{
			g2D.fillOval(Math.round((float)trajs[selectedTraj][0][i])-3, Math.round((float)trajs[selectedTraj][1][i])-3, 6, 6);
		}
		g2D.setColor(Color.RED);
		if(trajs_original[selectedTraj].getFramesPro()[initialFrame-1]!=0)
		{
			g2D.fillOval((int)Math.round(trajs_original[selectedTraj].getParticlesPro()[initialFrame-1].getY()*scaleX*zoom)-5, 
					     (int)Math.round(trajs_original[selectedTraj].getParticlesPro()[initialFrame-1].getX()*scaleX*zoom)-5, 10, 10);
		}

		if(trajs_original[selectedTraj].getFramesPro()[finalFrame-1]!=0)
			g2D.fillOval((int)Math.round(trajs_original[selectedTraj].getParticlesPro()[finalFrame-1].getY()*scaleX*zoom)-5, 
					     (int)Math.round(trajs_original[selectedTraj].getParticlesPro()[finalFrame-1].getX()*scaleX*zoom)-5, 10, 10);
	}

	private void drawVectors(Graphics2D g2D)
	{
		g2D.setColor(Color.BLUE);
		g2D.setStroke(new BasicStroke(1.0f));
		System.out.println("dibujando vectores");
		double[][] vectors = trajs_original[selectedTraj].getVectors();
		double alfa;
		for(int i=0;i<vectors.length;i++)
		{
			g2D.draw(new Line2D.Double(trajs[selectedTraj][0][i],trajs[selectedTraj][1][i],
									   trajs[selectedTraj][0][i]+2*vectors[i][0]*scaleX*zoom,trajs[selectedTraj][1][i]+vectors[i][1]*2*scaleX*zoom));
			/*alfa = Math.atan(vectors[i][0]/vectors[i][1]);
			g2D.rotate(alfa+Math.PI*5/6,trajs[selectedTraj][0][i],trajs[selectedTraj][1][i]);
			g2D.draw(new Line2D.Double(0,0,scaleX*zoom,scaleX*zoom));
			g2D.rotate(Math.PI/3,trajs[selectedTraj][0][i],trajs[selectedTraj][1][i]);
			g2D.draw(new Line2D.Double(0,0,scaleX*zoom,scaleX*zoom));
			g2D.rotate(-Math.PI/3,trajs[selectedTraj][0][i],trajs[selectedTraj][1][i]);
			g2D.rotate(-(alfa+Math.PI*5/6),trajs[selectedTraj][0][i],trajs[selectedTraj][1][i]);*/
		}	
	}
	
	private void drawCorrales(Graphics2D g2D)
	{
		g2D.setColor(Color.PINK);
		g2D.setStroke(new BasicStroke(1.5f));
		System.out.println("dibujando corrales rosados");
		for(int i=0;i<corrales.length;i++)
		{
			g2D.drawOval((int)Math.round(corrales[i][0]-corrales[i][2]),
					(int)Math.round(corrales[i][1]-corrales[i][2]),
					(int)Math.round(corrales[i][2]*2),
					(int)Math.round(corrales[i][2]*2));
		}
	}
	
	
	
	
}




















