package loader;

import ij.IJ;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Thinker;

import java.awt.GridBagLayout;

public class FileLoader
{
	
	Thinker thinker;
	
	public GUILoader gui;
//	public String path;
//	public String name;
	
	
	File currentDirectory;
	
	final static int XML = 0;
	final static int TXT = 1;
	final static int IMAGE = 2;
	int type;
	
	boolean load;
	
	public FileLoader(Thinker thinker, int x, int y)
	{
		this.thinker = thinker;
		gui = new GUILoader(this);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());
		if(thinker.getParticleTracker()!=null)
		{
			currentDirectory = new File(thinker.getParticleTracker().getMovie().getDirectory());
		}
		load = true;
		type = XML;
	}
	public void openGUI() 
	{
		gui.setVisible(true);
		gui.setLocation(thinker.gui.getX(),thinker.gui.getY());	
	}
	public void close() 
	{
		gui.dispose();
		gui = null;
	}
	
	public void radioButton_LoadSave_stateChanged()
	{
//		gui.jTextField_Directory_setText("");
//		gui.jTextField_Name_setText("");
		load = gui.radioButton_Load_isSelected();
		gui.radioButton_Image_setEnabled(load);
		gui.jTextField_Name_setEditable(!load);
		if(load)
			gui.jButton_LoadSave_setText("Load");
		else
		{
			gui.jButton_LoadSave_setText("Save");
			if(thinker.getParticleTracker()!=null)
			{
				gui.jTextField_Directory_setText(thinker.getParticleTracker().getMovie().getDirectory());
				gui.jTextField_Name_setText(thinker.getParticleTracker().getMovie().getName());
			}
		}
	}
		
	public void radioButton_Type_stateChanged()
	{
//		gui.jTextField_Directory_setText("");
		gui.jTextField_Name_setText("");
		type = gui.radioButton_Type_getOption();
	}


	public void jButton_Browse_clicked() 
	{
		JFileChooser fc = new JFileChooser(currentDirectory);
		if(type!=IMAGE)
		{
			FileNameExtensionFilter filter;
			if(type==XML)
				filter = new FileNameExtensionFilter("XML", "xml");
			else
				filter = new FileNameExtensionFilter("TXT", "txt");
			fc.setFileFilter(filter);
		}
		int returnVal = fc.showOpenDialog(gui);
		if(load)
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		else
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
        	currentDirectory = fc.getCurrentDirectory();
            File file = fc.getSelectedFile();
//          path = file.getPath();
            String directory;
//            if(load)
//            {
            	directory = file.getParent()+"\\";
//            	name = file.getName();
            	String name = file.getName();
            	IJ.log("Name: " + name);
            	gui.jTextField_Name_setText(name);
//            }
//            else
//            {
//            	directory = file.getPath()+"\\";
//            }
            gui.jTextField_Directory_setText(directory);
            IJ.log("Path: " + directory);
        }
        else
        {
        	//gui.jTextField_LoadXY_setText(file.getPath()+file.getName());
        }
	}

	public void jButton_LoadSave_clicked() 
	{
		String directory = gui.jTextField_Directory_getText();
		String name = gui.jTextField_Name_getText();
		switch(type)
		{
			case XML:
				ProjectManager pm = new ProjectManager(thinker, gui, directory, name);
				if(load)pm.load();
				else pm.save();
				break;
			case TXT:
				TxtManager txtm = new TxtManager(thinker, gui, directory, name);
				if(load) txtm.load();
				else txtm.save();
				break;
		}
	}
	
	
}
