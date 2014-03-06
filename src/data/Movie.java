package data;

import particleTracker.ParticleDetector;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.io.FileInfo;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.StackStatistics;

public class Movie 
{	
	Frame[] frames;
	ImagePlus imp;
	FileInfo info;
	
	public Movie(ImagePlus imp)
	{
		this.imp = imp;
		info = imp.getOriginalFileInfo();
		frames = new Frame[imp.getStackSize()];
		for(int i=0;i<frames.length;i++)
			frames[i] = new Frame(imp.getStack().getProcessor(i+1),i+1);
	}
	
	public ImagePlus getImp()
	{
		return imp;
	}
	
	public ImagePlus getRestoredImp()
	{
		StackStatistics stack_stats = new StackStatistics(imp);
		
		ImagePlus impRestored = NewImage.createFloatImage("Restored", imp.getWidth(), imp.getHeight(), imp.getImageStackSize(), NewImage.FILL_RAMP);//imp.createHyperStack("Restored", imp.getNChannels(), imp.getNSlices(), imp.getNFrames(), imp.getBitDepth());
		ImageProcessor original_fp, restored_fp;
		
		IJ.log("impNslices:"+imp.getNSlices());
		IJ.log("impNFrames:"+imp.getNFrames());
		
		IJ.log("stack size:"+imp.getImageStackSize());
		IJ.log("restored stack size:"+impRestored.getImageStackSize());
		IJ.log("frames.lenght:"+frames.length);
		IJ.log("imp width:"+imp.getWidth());
		IJ.log("imageStack width:"+imp.getImageStack().getWidth());
		IJ.log("impRestored width:"+impRestored.getWidth());
		IJ.log("impRestoredImageStack width:"+impRestored.getImageStack().getWidth());
//		IJ.log("RestoredFp width:"+frames[0].getRestored_fp().getWidth());
		
		IJ.log("imp Height:"+imp.getHeight());
		IJ.log("imageStack Height:"+imp.getImageStack().getHeight());
		IJ.log("impRestored Height:"+impRestored.getHeight());
		
		ParticleDetector.kernel = ParticleDetector.makeKernel();	
		for(int i = 0; i<imp.getImageStackSize();i++)
		{
			original_fp = frames[i].getOriginal_ip().convertToFloat();
			ParticleDetector.normalizeFrameFloat(original_fp, (float)stack_stats.max, (float)stack_stats.min);
			restored_fp = ParticleDetector.imageRestoration(original_fp);
			impRestored.getImageStack().setPixels((float[])restored_fp.getPixels(), i+1);
		}
			
		return impRestored;
	}
	public int getLenght()
	{
		return frames.length;
	}
	public Frame getFrame(int index)
	{
		return frames[index];
	}
	public int getHeight()
	{
		return imp.getHeight();
	}
	public int getWidth()
	{
		return imp.getWidth();
	}
	public Frame[] getFrames()
	{
		return frames;
	}
	public void setFrames(Frame[] frames)
	{
		this.frames = frames;
	}
	public String getName()
	{
		return info.fileName;
	}
	public String getDirectory()
	{
		return info.directory;
	}
	
	
	

}
