import java.io.Console;
import java.io.PrintStream;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import main.Thinker;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class Fusion_Events implements PlugInFilter 
{
	Thinker thinker;

	@Override
	public int setup(String arg, ImagePlus imp) 
	{
		if(imp!=null)
		{
			if(imp.getNDimensions()==3 && imp.getNChannels()==1)
			{
				try{
					thinker = new Thinker();
					thinker.newParticleTracker(imp);
					IJ.log("No Exception");
				}
				catch (Exception e){
					IJ.log("Exception!!!!!");
					IJ.log(e.getMessage());
				}
			}
			else
			{
				IJ.showMessage("Error", "This plugin works with 1 channel Image Stacks of 1 slice and N frames or 1 frame and n slices.\n" +
						"Please check your image:" +
						"\nDimension:"+imp.getNDimensions()+
						"\nN� Channels:"+imp.getNChannels()+
						"\nN� Frames:"+imp.getNFrames()+
						"\nN� Slices:"+imp.getNSlices()+
						"\nStack Size:"+imp.getStackSize());
			}
		}
		else
		{
			try{
				thinker = new Thinker();
				IJ.log("No Exception");
			}
			catch (Exception e){
				IJ.log("Exception!!!!!");
				IJ.log(e.getMessage());
			}
			
			IJ.log("If you want to analyze some image, it must be loaded first");
		}
		return DONE;
	}
	@Override
	public void run(ImageProcessor ip) 
	{}
}
