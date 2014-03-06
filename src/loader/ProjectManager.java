package loader;

import ij.IJ;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import main.Thinker;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.input.SAXBuilder;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;

import typeOfMotion.TypeOfMotionAnalysis;
import data.Metrics;
import data.TrajSet;
import data.Trajectory;
import data.Particle;
import data.Subtrajectory;

public class ProjectManager
{
	Color[] colores;
	Thinker thinker;
	GUILoader gui;
	String directory;
	String name;
	
	SAXBuilder builder;
    Document doc;
    Element eRaiz = new Element("SPTProject");  
    Element eSource = new Element("source");
    Element eName = new Element("name");
    Element eDirectory = new Element("directory");
    Element eMetrics = new Element("metrics");
    Element eDistanceU = new Element("distanceUnit");
    Element eTimeU = new Element("timeUnit");
    Element eDistanceF = new Element("distanceF");
    Element eTimeF = new Element("timeF");
    
    Element eTrajsTag, eTrajTag, eId, eParticlesTag, eParticle, eFrame;
    Element eMovieFrame, eUserType;
    Element eModelType, eModelParamD, eModelParam2, eModelGOF;
    Element eX, eY, eZ, eB;
    Element eUsar;
    Element eSubtrajs, eSubtraj, eSubId;
    Element eBegin, eEnd;
    
    List<Element> trajs;
	
	public ProjectManager(Thinker thinker, GUILoader gui, String directory, String name)
	{
//		super(x,y);
		this.thinker = thinker;
		this.gui = gui;
		this.directory = directory;
		this.name = name;
		
		System.out.println("directory:"+directory);
		System.out.println("directory:"+name);
		builder = new SAXBuilder(false);
//		gui.setTitle("Project loader");
	}	
//	@SuppressWarnings("unchecked")
//	@Override
	public void load()
	{
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				try 
				{
					if(directory!=null)
					{
						colores = new Color[]{Color.BLUE,Color.CYAN,Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.WHITE, Color.YELLOW};
//						thinker.xmlPath = path;
						doc = builder.build(directory+name);
						eRaiz = doc.getRootElement();
						eMetrics = eRaiz.getChild("metrics");
						
						int originalSource = Integer.parseInt(eRaiz.getChildText("source"));
						
						int distanceUnit = Integer.parseInt(eMetrics.getChildText("distanceUnit"));
						int timeUnit = Integer.parseInt(eMetrics.getChildText("timeUnit"));
						double distanceFactor = Double.parseDouble(eMetrics.getChildText("distanceF"));
						double frameStep = Double.parseDouble(eMetrics.getChildText("timeF"));
						Metrics metrics = new Metrics(frameStep, distanceFactor, timeUnit, distanceUnit);
						
						double modelGOF = 0;
						
						eTrajsTag = eRaiz.getChild("trajectories");
						trajs = (List<Element>)eTrajsTag.getChildren();
						ArrayList<Trajectory> trajsArray = new ArrayList<Trajectory>();
						for(int i=0;i<trajs.size();i++)
						{
							eTrajTag = (Element)trajs.get(i);
					    	eId = eTrajTag.getChild("id");
					    	
					    	if(originalSource == TrajSet.IMAGE)
					    		eMovieFrame = eTrajTag.getChild("movieFrame");
					    	
					    	eUserType = eTrajTag.getChild("ChlamyType");
					    	eUsar = eTrajTag.getChild("Usar");
					    	eModelType = eTrajTag.getChild("ModelType");
					    	eModelParamD = eTrajTag.getChild("ModelParamD");
					    	eModelParam2 = eTrajTag.getChild("ModelParam2");
					    	
					    	
					    	modelGOF = 0;
					    	if(eTrajTag.getChild("ModelGOF")!=null)
					    		modelGOF = Double.parseDouble(eTrajTag.getChild("ModelGOF").getText());
					    	
					    	
					    	@SuppressWarnings("unchecked")
							List<Element> iParticles = (List<Element>)eTrajTag.getChild("particles").getChildren();
							ArrayList<Particle> aParticles = new ArrayList<Particle>();
							ArrayList<Integer> aFrames = new ArrayList<Integer>();
					    	for(int p=0;p<iParticles.size();p++)
					    	{
					    		eParticle = (Element)iParticles.get(p);
					    		eFrame = eParticle.getChild("frame");
					    		aFrames.add(Integer.parseInt(eFrame.getText()));
					    		
					    		eX = eParticle.getChild("x");
					    		eY = eParticle.getChild("y");
					    		eZ = eParticle.getChild("z");
					    		eB = eParticle.getChild("b");
//								IJ.log(eX.getText()+"  "+Float.parseFloat(eX.getText()));
					    		aParticles.add(new Particle(Float.parseFloat(eX.getText()),Float.parseFloat(eY.getText())));
					    		aParticles.get(p).setZ(Float.parseFloat(eZ.getText()));
					    		aParticles.get(p).setAvgBrightness(Float.parseFloat(eB.getText()));
					    	}
					    	Particle[] particles = new Particle[aParticles.size()];
					    	int[] frames = new int[aFrames.size()];
					    	for(int k=0;k<particles.length;k++)
					    	{
					    		frames[k] = aFrames.get(k);
					    		particles[k] = aParticles.get(k);						    		
					    	}
					    	trajsArray.add(new Trajectory(Integer.parseInt(eId.getText()), particles, frames, metrics));
					    	trajsArray.get(i).setUserType(Integer.parseInt(eUserType.getText()));
					    	trajsArray.get(i).setUsar(Boolean.parseBoolean(eUsar.getText()));
					    	if(originalSource == TrajSet.IMAGE)
					    		trajsArray.get(i).setMovieFrame(Integer.parseInt(eMovieFrame.getText()));
					    	trajsArray.get(i).setColor(colores[i%9]);
					    	
					    	int modelType = Integer.parseInt(eModelType.getText());
					    	if(eTrajTag.getChild("subtrajs")!=null)
					    		modelType = TypeOfMotionAnalysis.NOT_DEFINED;
					    	
					    	if(eModelType!=null)
					    		trajsArray.get(i).setTypeAndParams(modelType, new double[]{Double.parseDouble(eModelParamD.getText()),Double.parseDouble(eModelParam2.getText())}, modelGOF);
					    	
					    	
					    	if(eTrajTag.getChild("subtrajs")!=null)
					    	{
						    	@SuppressWarnings("unchecked")
								List<Element> iSubtrajs = (List<Element>)eTrajTag.getChild("subtrajs").getChildren();
//								ArrayList<Subtrajectory> aSubtrajs = new ArrayList<Subtrajectory>();
						    	
						    	
					    		for(int j=0;j<iSubtrajs.size();j++)
					    		{
					    			modelGOF = 0;
						    		eSubtraj = iSubtrajs.get(j);
						    		eBegin = eSubtraj.getChild("begin");
						    		eEnd = eSubtraj.getChild("end");
						    		eSubId = eSubtraj.getChild("subId");
						    		eUserType = eSubtraj.getChild("ChlamyType");
						    		eUsar = eSubtraj.getChild("Usar");
						    		eModelType = eSubtraj.getChild("ModelType");
							    	eModelParamD = eSubtraj.getChild("ModelParamD");
							    	eModelParam2 = eSubtraj.getChild("ModelParam2");
							    	
							    	if(eSubtraj.getChild("ModelGOF")!=null)
							    		modelGOF = Double.parseDouble(eSubtraj.getChild("ModelGOF").getText());
						    		
						    		trajsArray.get(i).addSubtrajectory(Integer.parseInt(eBegin.getText()), Integer.parseInt(eEnd.getText()));
						    		trajsArray.get(i).getSubtraj(j).setSubIndex(Integer.parseInt(eSubId.getText()));
						    		trajsArray.get(i).getSubtraj(j).setUserType(Integer.parseInt(eUserType.getText()));
						    		trajsArray.get(i).getSubtraj(j).setUsar(Boolean.parseBoolean(eUsar.getText()));
						    		trajsArray.get(i).getSubtraj(j).setSid(trajsArray.get(i).getId()+"."+trajsArray.get(i).getSubtraj(j).getSubId());
						    		if(eModelType!=null)
						    			trajsArray.get(i).getSubtraj(j).setTypeAndParams(Integer.parseInt(eModelType.getText()), new double[]{Double.parseDouble(eModelParamD.getText()),Double.parseDouble(eModelParam2.getText())},modelGOF);
						    	}
					    	}
					    	gui.jProgressBar_setValue(100*(i+1)/trajs.size());
					    }
						
						Trajectory[] trajectories = new Trajectory[trajsArray.size()];
						for(int i=0;i<trajectories.length;i++)
							trajectories[i]=trajsArray.get(i);		
						
						
						thinker.setTrajsFromXML(true);
						String originalFileDirectory = eRaiz.getChildText("directory");
						String originalFileName = eRaiz.getChildText("name");
						
						thinker.addTrajSet(new TrajSet(originalSource, originalFileDirectory, originalFileName, trajectories, metrics));
						thinker.updateMain();				
						thinker.updateAnalysis();
						if(originalSource==TrajSet.IMAGE && thinker.particleTracker!=null)
						{
							if(!thinker.particleTracker.getMovie().getName().equals(eRaiz.getChildText("name")))
							{
								IJ.showMessage("Warning", "It seems the current movie:"+thinker.particleTracker.getMovie().getName()+"\n" +
									" is not related with the XML file:"+eRaiz.getChildText("name"));
							}
							thinker.particleTracker.trajectories_stack_window = null;
							thinker.particleTracker.updateTrajectoriesStackWindow();
							thinker.particleTracker.stack_window = null;
						}
						gui.jProgressBar_setValue(0);
						gui.setVisible(false);
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		};
		Thread hilo = new Thread(run);
		hilo.start();
	}
	
	public void save()
	{
//		gui.setVisible(true);
		Runnable run = new Runnable() {
			@Override
			public void run() {
				int[] frames;
				Particle[] particles;
				Subtrajectory[] subTrajs;
				
				eRaiz = new Element("SPTProject");  
			    eSource = new Element("source");
			    eDirectory = new Element("directory");
			    eName = new Element("name");
			    eMetrics = new Element("metrics");
			    eDistanceU = new Element("distanceUnit");
			    eTimeU = new Element("timeUnit");
			    eDistanceF = new Element("distanceF");
			    eTimeF = new Element("timeF");
				
			    TrajSet data = thinker.getSelectedSet();
			    Metrics metrics = data.getMetrics();
			    
			    
			    eDistanceU.setText(String.valueOf(metrics.getDistUnit()));
			    eTimeU.setText(String.valueOf(metrics.getTimeUnit()));
			    eDistanceF.setText(String.valueOf(metrics.getDistanceFactor()));
			    eTimeF.setText(String.valueOf(metrics.getFrameStep()));
			    eMetrics.addContent(eDistanceU);
			    eMetrics.addContent(eDistanceF);
			    eMetrics.addContent(eTimeU);
			    eMetrics.addContent(eTimeF);
			    eRaiz.addContent(eMetrics);
			    
		    	eSource.setText(String.valueOf(data.getOriginalSource()));
				eName.setText(data.getName());
				eDirectory.setText(data.getDirectory());
				eRaiz.addContent(eSource);
				eRaiz.addContent(eName);
				eRaiz.addContent(eDirectory);

				Trajectory[] trajs = thinker.getSelectedSet().getTrajs();
				
				eTrajsTag = new Element("trajectories");
				
				for(int i=0;i<trajs.length;i++)
			    {
					eTrajTag = new Element("trajectory");
			    	eId = new Element("id");
			    	eId.setText(String.valueOf(trajs[i].getId()));
			    	eUserType = new Element("ChlamyType");
			    	eUserType.setText(String.valueOf(trajs[i].getUserType()));
			    	eUsar = new Element("Usar");
			    	eUsar.setText(String.valueOf(trajs[i].isUsar()));
			    	eModelType = new Element("ModelType");
			    	eModelType.setText(String.valueOf(trajs[i].getTrajType()));
			    	eModelParamD = new Element("ModelParamD");
			    	eModelParamD.setText(String.valueOf(trajs[i].getTypeParams()[0]));
			    	eModelParam2 = new Element("ModelParam2");
			    	eModelParam2.setText(String.valueOf(trajs[i].getTypeParams()[1]));
			    	eModelGOF = new Element("ModelGOF");
			    	eModelGOF.setText(String.valueOf(trajs[i].getModelGOF()));
			    	
			    	eTrajTag.addContent(eId);
			    	if(data.getOriginalSource()==TrajSet.IMAGE)
		    		{
			    		eMovieFrame = new Element("movieFrame");
			    		eMovieFrame.setText(String.valueOf(trajs[i].getMovieFrame()));
			    		eTrajTag.addContent(eMovieFrame);
		    		}
			    	eTrajTag.addContent(eUserType);
			    	eTrajTag.addContent(eUsar);
			    	eTrajTag.addContent(eModelType);
			    	eTrajTag.addContent(eModelParamD);
			    	eTrajTag.addContent(eModelParam2);
			    	eTrajTag.addContent(eModelGOF);
			    	
			    	eParticlesTag = new Element("particles");
			    	particles = trajs[i].getParticles();	
			    	frames = trajs[i].getFrames();
			    	for(int j=0;j<particles.length;j++)
			    	{
			    		eParticle = new Element("particle");
			    		eFrame = new Element("frame");
			    		eFrame.setText(String.valueOf(frames[j]));
			    		eX = new Element("x");
			    		eX.setText(String.valueOf(particles[j].getX()));
			    		eY = new Element("y");
			    		eY.setText(String.valueOf(particles[j].getY()));
			    		eZ = new Element("z");
			    		eZ.setText(String.valueOf(particles[j].getZ()));
			    		eB = new Element("b");
			    		eB.setText(String.valueOf(particles[j].getBrightness()));
			    		
			    		eParticle.addContent(eFrame);
			    		eParticle.addContent(eX);
			    		eParticle.addContent(eY);
			    		eParticle.addContent(eZ);
			    		eParticle.addContent(eB);
			    		eParticlesTag.addContent(eParticle);
			    	}
			    	eTrajTag.addContent(eParticlesTag);
			    	
			    	if(trajs[i].getSubtrajs().length>0)
			    	{
			    		eSubtrajs = new Element("subtrajs");
			    		subTrajs = trajs[i].getSubtrajs();
			    		for(int k=0;k<subTrajs.length;k++)
			    		{
				    		eSubtraj = new Element("subtraj");
				    		eBegin = new Element("begin");
				    		eBegin.setText(String.valueOf(subTrajs[k].getStartFrame()));
				    		eSubtraj.addContent(eBegin);
				    		eEnd = new Element("end");
				    		eEnd.setText(String.valueOf(subTrajs[k].getEndFrame()));
				    		eSubtraj.addContent(eEnd);
				    		
				    		eSubId = new Element("subId");
					    	eSubId.setText(String.valueOf(subTrajs[k].getSubId()));
					    	eUserType = new Element("ChlamyType");
					    	eUserType.setText(String.valueOf(subTrajs[k].getUserType()));
					    	eUsar = new Element("Usar");
					    	eUsar.setText(String.valueOf(subTrajs[k].isUsar()));
					    	
					    	eModelType = new Element("ModelType");
					    	eModelType.setText(String.valueOf(subTrajs[k].getTrajType()));
					    	eModelParamD = new Element("ModelParamD");
					    	eModelParamD.setText(String.valueOf(subTrajs[k].getTypeParams()[0]));
					    	eModelParam2 = new Element("ModelParam2");
					    	eModelParam2.setText(String.valueOf(subTrajs[k].getTypeParams()[1]));
					    	eModelGOF = new Element("ModelGOF");
					    	eModelGOF.setText(String.valueOf(subTrajs[k].getModelGOF()));
					    	
					    	eSubtraj.addContent(eSubId);
					    	eSubtraj.addContent(eUserType);		
					    	eSubtraj.addContent(eUsar);
					    	eSubtraj.addContent(eModelType);
					    	eSubtraj.addContent(eModelParamD);
					    	eSubtraj.addContent(eModelParam2);
					    	eSubtraj.addContent(eModelGOF);
					    	
					    	
				    		eSubtrajs.addContent(eSubtraj);
			    		}
			    		eTrajTag.addContent(eSubtrajs);
			    	}
			    	eTrajsTag.addContent(eTrajTag);
			    	gui.jProgressBar_setValue(100*(i)/trajs.length);
			    }
				eRaiz.addContent(eTrajsTag);
				
				Document docNuevo = new Document();
				docNuevo.addContent(eRaiz);  
				
			    // Vamos a serializar el XML  
		        // Lo primero es obtener el formato de salida  
		        // Partimos del "Formato bonito", aunque tambi�n existe el plano y el compacto  
			    Format format = Format.getPrettyFormat();  
		        // Creamos el serializador con el formato deseado  
		        XMLOutputter xmloutputter = new XMLOutputter(format);  
		        // Serializamos nuestro nuevo document  
		        String docNuevoStr = xmloutputter.outputString(docNuevo);  
//		        System.out.println("XML nuevo:\n"+docNuevoStr);
		        
		        FileWriter ryt;
				try {
//			        if(thinker.xmlPath!=null)
					if(directory!=null)
					{
						if(IJ.showMessageWithCancel("Warning", "Your xml will be replaced."))
						{
							ryt = new FileWriter(directory+name+".xml");
						    BufferedWriter out = new BufferedWriter(ryt);
							out.write(docNuevoStr);
							out.close();
						}
					}
			        else
			        {
			        	ryt = new FileWriter(directory+name+".xml");
					    BufferedWriter out = new BufferedWriter(ryt);
						out.write(docNuevoStr);
						out.close();
			        }
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				gui.jProgressBar_setValue(100);
				gui.setVisible(false);
				gui.jProgressBar_setValue(0);
			}
		};
		Thread hilo = new Thread(run);
		hilo.start();	
	}
}
