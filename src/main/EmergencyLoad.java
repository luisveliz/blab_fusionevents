package main;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.AVI_Reader;
import ij.process.ImageProcessor;
import ij.process.StackStatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import data.Trajectory;
import data.Movie;
import data.Particle;

import particleTracker.ParticleDetector;
import particleTracker.ParticleLinker;

public class EmergencyLoad 
{
	public EmergencyLoad()
	{
		String path = "E:\\Chlamy\\analisis de moviemto cepa 124 gametos\\";
		File directory = new File(path);
		File[][] avistxt = getAvisAndTxts(directory);
		AVI_Reader aviReader = new AVI_Reader();
		ImageStack imageStack;
		ImagePlus imp;
		String filename;
		Trajectory[] trajs;
		float[] params;
		for(int i=0;i<avistxt[0].length;i++)
		{
			IJ.log("Avi:"+avistxt[0][i].getName());
			aviReader = new AVI_Reader();
			imageStack = aviReader.makeStack(avistxt[0][i].getPath(), 1, 0, false, true, false);
			imp = new ImagePlus(avistxt[0][i].getName(), imageStack);
			invert(imp);
			//imp.show();
			params = getParameters(avistxt[1][i].getName());
			IJ.log("parameters r:"+params[0]+" p:"+params[1]+" l:"+params[2]+" d:"+params[3]);
			filename = "new_"+avistxt[1][i].getName();
			trajs = track(imp, params, avistxt[0][i].getParentFile().getPath()+avistxt[0][i].getName()); 
			exportTrajsAsTXT(trajs, avistxt[0][i].getParentFile().getPath(), filename);
		}
		/*IJ.log("Ahora los txt");
		for(int i=0;i<avistxt[1].length;i++)
			IJ.log("Txt:"+avistxt[1][i].getName());
		*/
	}
	public float[] getParameters(String filename)
	{
		float r,p,l,d;
		IJ.log("filename: "+filename);
		int pos = filename.indexOf('.');
	//	IJ.log("index of . "+pos);
		String aux = filename.substring(pos+6, filename.length());
	//	IJ.log("aux: "+aux);
		r = Float.valueOf(aux.substring(0,aux.indexOf('_')));
		aux = aux.substring(aux.indexOf('_')+2, aux.length());
	//	IJ.log("aux: "+aux);
		p = Float.valueOf(aux.substring(0,aux.indexOf('_')));
		aux = aux.substring(aux.indexOf('_')+2, aux.length());
	//	IJ.log("aux: "+aux);
		l = Float.valueOf(aux.substring(0,aux.indexOf('_')));
		aux = aux.substring(aux.indexOf('_')+2, aux.length());
	//	IJ.log("aux: "+aux);
		d = Float.valueOf(aux.substring(0,aux.indexOf('.')));
		return new float[]{r,p,l,d};
	}
	
	public File[][] getAvisAndTxts(File directory)
	{
		File[] files = directory.listFiles();
		File[][] avistxtsAux;
		ArrayList<File> avis = new ArrayList<File>();
		ArrayList<File> txts = new ArrayList<File>();
		
		for(int i=0;i<files.length;i++)
		{
			if(files[1].isFile())
			{
				//IJ.log("File:"+files[i].getPath());
				if(files[i].getName().contains(".txt"))
					txts.add(files[i]);
				else
					avis.add(files[i]);
			}
			else
			{
				//IJ.log("Directory:"+files[i].getPath());
				avistxtsAux = getAvisAndTxts(files[i]);
				for(int j=0;j<avistxtsAux[0].length;j++)
				{
					avis.add(avistxtsAux[0][j]);
					txts.add(avistxtsAux[1][j]);
				}
			}			
		}		
		File[][] avistxtsf = new File[2][avis.size()];
		for(int i=0;i<avistxtsf[0].length;i++)
		{
			avistxtsf[0][i]=avis.get(i);
			avistxtsf[1][i]=txts.get(i);
		}
		return avistxtsf;
	}
	
	public void invert(ImagePlus imp) 
	{
		int slices = imp.getStackSize();
		
		ImageProcessor ip;	
		
		for(int f=0;f<slices;f++)
		{
			ip = imp.getStack().getProcessor(f+1);
			for(int i=0;i<imp.getHeight();i++)
			{
				for(int j=0;j<imp.getWidth();j++)
					ip.set(j, i, 255-ip.getPixel(j, i));					
			}
		}
	}
	
	public Trajectory[] track(ImagePlus imp, float[] params,String name)
	{
		ParticleDetector.radius = (int) params[0];
		ParticleDetector.cutoff = 0;
		ParticleDetector.percentile = (float) params[1];
		ParticleLinker.linkRange = (int) params[2];
		ParticleLinker.displacement = (int) params[3];
		
		StackStatistics stack_stats = new StackStatistics(imp);
		Movie movie = new Movie(imp);
		for(int i=0;i<movie.getLenght();i++)
		{
			IJ.log(name+" frame:"+(i+1));			
			ParticleDetector.detectParticles(movie.getFrame(i), (float)stack_stats.max, (float)stack_stats.min);
		}
		IJ.log("Linking Particles");
		ParticleLinker.linkParticles(movie);
		IJ.freeMemory();
		IJ.log("Generating Trajectories");
		return ParticleLinker.generateTrajectories(movie);
	}
	public void exportTrajsAsTXT(Trajectory[] trajs, String path, String name)
	{
		IJ.log("Exportando nuevo: "+name);
		int[] frames;
		Particle[] particles;
		String filename = path+"\\"+name+".txt";
		IJ.log("Path completo: "+filename);
		
		FileWriter ryt;
		
		try {
			ryt = new FileWriter(filename);
		
		    BufferedWriter out = new BufferedWriter(ryt);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
