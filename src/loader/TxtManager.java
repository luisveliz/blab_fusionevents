package loader;

import ij.IJ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Thinker;
import data.Metrics;
import data.Particle;
import data.TrajSet;
import data.Trajectory;

public class TxtManager
{
	Thinker thinker;
	GUILoader gui;
	String directory;
	String name;
	
	public TxtManager(Thinker thinker, GUILoader gui, String directory, String name)
	{
		this.thinker = thinker;
		this.gui = gui;
		this.directory = directory;
		this.name = name;
	}
	
//	@Override
	public void load()
	{
		ArrayList<float[]> afxy = new ArrayList<float[]>();
		ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
		
		File file;
		Scanner scanner = null;
//		String path = gui.jTextField_LoadXY_getText();
		try
		{
			file = new File(directory+name);
			//TODO mejorar lo del path
//			thinker.txtName = name;//path.substring(path.lastIndexOf("\\")+1);
//			thinker.txtPath = path;//path.substring(0,path.lastIndexOf("\\"));			
			
			scanner = new Scanner(new FileInputStream(file));
			int countLines=0;
		    String[] sframeXY;
		    
		    float[] fxy = new float[4];
		    float[] aux = new float[4];
		    
		    int[] frames;
		    float[] xs;
		    float[] ys;
		    Particle[] particles;
		    //double[] xd;
		    //double[] yd;
			while (scanner.hasNextLine())
			{				
				//TODO arreglar que detecte espacio o tab
				sframeXY = scanner.nextLine().split("\t");
				if(sframeXY.length!=4)
				{
					IJ.error("error de parseo en línea:"+countLines+1);
					break;
				}
				else
				{
					fxy[0] = Integer.parseInt(sframeXY[0]);
					fxy[1] = Float.parseFloat(sframeXY[1]);
					fxy[2] = Float.parseFloat(sframeXY[2]);
					fxy[3] = Float.parseFloat(sframeXY[3]);
					System.out.println("line "+(countLines+1)+": "+fxy[0]+" "+fxy[1]+" "+fxy[2]+" "+fxy[3]);
					if(fxy[0]==1 && afxy.size()>0)
					{
						System.out.println("afxy.size:"+afxy.size());
						if(afxy.size()>4)
						{
							frames = new int[afxy.size()];
							xs = new float[afxy.size()];
							ys = new float[afxy.size()];
							particles = new Particle[afxy.size()];
							for(int i=0;i<xs.length;i++)
							{
								System.out.println("i:"+i+" afxy.size:"+afxy.size()+"frame:"+afxy.get(i)[0]+"x: "+afxy.get(i)[1]);
								aux = afxy.get(i);	
								frames[i]=(int) aux[0];
								xs[i]= aux[1];
								ys[i]= aux[2];
								particles[i] = new Particle(xs[i],ys[i]);
								particles[i].setAvgBrightness(aux[3]);
								System.out.println("i:"+i+" "+aux[0]+" "+aux[1]+" "+aux[2]);
							}
							trajectories.add(new Trajectory(trajectories.size()+1,particles,frames, new Metrics()));
						}
						else
							System.out.println("traj.length<5, no incluida");
						afxy.clear();			
					}
					afxy.add(new float[]{fxy[0],fxy[1],fxy[2],fxy[3]});
					
					countLines++;
				}
			}
			Trajectory[] set = new Trajectory[trajectories.size()];
			for(int i=0;i<set.length;i++)
			{
				set[i] = trajectories.get(i);
			}
			
//			thinker.source = Thinker.TXT;
//			thinker.setTrajectories(set);
			thinker.addTrajSet(new TrajSet(TrajSet.TXT,directory,name,set, new Metrics()));
			thinker.updateMain();				
			thinker.updateAnalysis();		
			gui.setVisible(false);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		finally{scanner.close();}
	}
	public void save()
	{
		Trajectory[] trajs =  thinker.getSelectedSet().getTrajs();
		int[] frames;
		Particle[] particles;
		//String name = particleTracker.getMovie().getName();
//		FileInfo fileInfo = thinker.particleTracker.getMovie().getImp().getOriginalFileInfo();
//		String name = fileInfo.fileName;
//		String path = fileInfo.directory;
//		String radius = String.valueOf(ParticleDetector.radius);
//		String percentile = String.valueOf(ParticleDetector.percentile);
//		String linkrange = String.valueOf(ParticleLinker.linkRange);
//		String displacement = String.valueOf(ParticleLinker.displacement);
//		String filename = path+name+"_r"+radius+"_p"+percentile+"_l"+linkrange+"_d"+displacement+".txt";
		String filename = directory+name;
		IJ.log("Filename: "+filename);
		
		FileWriter ryt;
		try {
			ryt = new FileWriter(filename);
		
		    BufferedWriter out = new BufferedWriter(ryt);
    		//out.write("MCMC info\n");
    		for(int i=0;i<trajs.length;i++)
    		{
    			frames = trajs[i].getFrames();
    			particles = trajs[i].getParticles();
    		
    			for(int j=0;j<particles.length;j++)
    			{
    				out.write(frames[j]+"\t"+particles[j].getX()+"\t"+particles[j].getY()+"\t"+particles[j].getBrightness());
    				out.newLine();
    			}
    		}
    		//out.write("End");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
