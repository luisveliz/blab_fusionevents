package info;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import bTools.BMaths;
import bTools.BNF;

import main.Thinker;
import typeOfMotion.TypeOfMotionAnalysis;
import data.Metrics;
import data.Particle;
import data.Subtrajectory;
import data.TrajSet;
import data.Trajectory;

public class GlobalInfo extends GUIInfo
{
	Thinker thinker;
	SimpleAttributeSet black;
	SimpleAttributeSet blue;
	SimpleAttributeSet red;
	
	public GlobalInfo(Thinker thinker)
	{
		super();
		
		chckbxUserType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateGlobalInfo();
			}
		});
		chckbxModesOfMotion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateGlobalInfo();
			}
		});
		chckbxGeneralInfo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateGlobalInfo();
			}
		});
		
		this.thinker = thinker;
		
		
		//Font attributes
		black = new SimpleAttributeSet();
	    StyleConstants.setForeground(black, Color.BLACK);
	    StyleConstants.setFontSize(black, 10);
	    blue = new SimpleAttributeSet();
	    StyleConstants.setForeground(blue, Color.BLUE);
	    StyleConstants.setFontSize(blue, 10);
		red = new SimpleAttributeSet();
	    StyleConstants.setForeground(red, Color.RED);
	    StyleConstants.setFontSize(red, 10);
		
		updateGlobalInfo();
	}
	public void updateGlobalInfo()
	{
		textPaneGlobalInfo.setText("");
		TrajSet trajSet = thinker.getSelectedSet();
		boolean[] trajTypeSelection = thinker.getTrajTypeSelection();
		Metrics metrics = trajSet.getMetrics(); 
		StringBuffer sb = new StringBuffer();
		
		if(trajSet!=null)
		{
			if(jCheckBox_Varios_isSelected())
			{
				if(thinker.isTrajsFromXML())sb.append("Trajs loaded from SPT file (XML) ");
				switch(trajSet.getOriginalSource())
				{
					case TrajSet.TXT:sb.append("Original Source: TXT file\n");break;
					case TrajSet.IMAGE:sb.append("Original Source: IMAGE\n");break;
					case TrajSet.SIMULATED:sb.append("Original Source: SIMULATED\n");break;				
					default:sb.append("Source: default!!!!!!!!!!!!!!!!!!!!!!!!?????????????????\n");break;
				}
				
				sb.append("Name: "+trajSet.getName()+"\n");
				sb.append("Directory:"+trajSet.getDirectory()+"\n");
				sb.append("User Metrics:\n");
				sb.append("1px="+metrics.getDistanceFactor()+metrics.getDistUnittoString()+"\n");
				sb.append("time between two frames:"+metrics.getFrameStep()+metrics.getTimeUnittoString()+"\n");
			}
			
			if(jCheckBox_UserType_isSelected())
				sb.append(userTypeInfo(true));
			if(jCheckBox_ModesOfMotion_isSelected())
				sb.append(userTypeInfo(false));
					
			sb.append("\nid\tMicro D\tModel D\t2°Param\tModelGOF\tVel.["+metrics.getVelUnit()+"]\tType\tUType\n");
			sb.append("\t["+metrics.getDifUnit()+"]\t["+metrics.getDifUnit()+"]\t\t\t["+metrics.getVelUnit()+"]\t\t\n\n");
			
			sb.append("AVG\t");
			double[] avgstd = BMaths.avgstd(trajSet.getMicroDiffusion(trajTypeSelection));
			sb.append(BNF.sF(avgstd[0])+"+-"+BNF.sF(avgstd[1])+"\t");
//			avgstd = BMaths.avgstd(trajSet.getMacroDiffusion(trajTypeSelection));
//			sb.append(BNF.sF(avgstd[0])+"+-"+BNF.sF(avgstd[1])+"\t");
			
			avgstd = trajSet.getAvgModelDiffusion();
			sb.append(BNF.sF(avgstd[0])+"+-"+BNF.sF(avgstd[1])+"\t");
			sb.append("\t");
			sb.append("\t");
			avgstd = BMaths.avgstd(trajSet.getVelocity(trajTypeSelection));
			sb.append(BNF.sF(avgstd[0])+"+-"+BNF.sF(avgstd[1])+"\t");
//			avgstd = BMaths.avgstd(trajSet.getAngleAVG());
//			sb.append(BNF.sF(avgstd[0])+"+-"+BNF.sF(avgstd[1])+"\n");
			
			
			//jTextPane_GlobalInfo_setText(sb.toString()+"\n");
			append(sb.toString()+"\n\n", black);
			
			boolean wrongD=false;
			
			String sid = thinker.getLastSelectedTraj().getSid();
			Trajectory[] trajs = trajSet.getTrajsWithSubtrajsOfType(trajTypeSelection);
			for(int i=0;i<trajs.length;i++)
			{
				sb = new StringBuffer();
				sb.append(trajs[i].getSid()+"\t");
				sb.append(BNF.sF(trajs[i].getMicroD())+"\t");
//				sb.append(BNF.sF(trajs[i].getMacroD())+"\t");
				if(trajs[i].isAnalyzed())
				{
					if(trajs[i].getTypeParams()[0]<0)
						wrongD = true;
					sb.append(BNF.sF(trajs[i].getTypeParams()[0])+"\t");
					
					if(trajs[i].getTrajType()!=TypeOfMotionAnalysis.NORMAL)
					{
						sb.append(BNF.sF(trajs[i].getTypeParams()[1]));
						if(trajs[i].getTypeParams()[1]<0)
							wrongD = true;
					}
					else
						sb.append("/");
					
					sb.append("\t");
					
					if(trajs[i].getModelGOF()<0.6)
						wrongD = true;
					sb.append(BNF.sF(trajs[i].getModelGOF())+"\t");
				}
				else
					sb.append("/\t/\t/\t");
				
				sb.append(BNF.sF(BMaths.avg(trajs[i].getVelocity()))+"\t");
//				sb.append(BNF.sF(BMaths.avg(trajs[i].getAngles()))+"\t");
				sb.append(trajs[i].getTrajTypeToString()+"\t");
				sb.append(trajs[i].getUserType()+"\n");
				if(wrongD)
					append(sb.toString(), red);
				else
				{
					if(sid.equals(trajs[i].getSid()))
						append(sb.toString(), blue);
					else
						append(sb.toString(), black);
				}
				wrongD = false;
			}
		}
		
		jScrollBars_reset();
	}
	public void updateTrajectoryInfo()
	{		
		
		TrajSet trajSet = thinker.getSelectedSet();
		Metrics metrics = trajSet.getMetrics(); 
		Trajectory traj = thinker.getLastSelectedTraj();
//		if(gui_info!=null && traj!=null)
		if(traj!=null)
		{
			StringBuffer sb = new StringBuffer();
			
			sb.append("Traj id:\t"+traj.getId());sb.append("\n");
			sb.append("User type:\t"+traj.getUserType());sb.append("\n");
			sb.append("Active:\t"+traj.isUsar());sb.append("\n");
			sb.append("Usability:\t"+traj.getUsability()+"\t%");sb.append("\n");
			sb.append("MSD is ok:\t"+traj.isMsdOK());sb.append("\n");
			sb.append("Analyzed:\t"+traj.isAnalyzed());sb.append("\n\n");
			
			sb.append("Frames:\t"+traj.getLength());sb.append("\n");
			sb.append("Time Length:\t"+BNF.sF(traj.getTrajectoryTime(traj.getParticles().length-1))+"\t["+metrics.getTimeUnittoString()+"]");sb.append("\n");
			sb.append("Movie Frame:\t"+traj.getMovieFrame());sb.append("\n");
			sb.append("Total Distance:\t"+BNF.sF(traj.getTotalDistanceInPath())+"\t["+metrics.getDistUnittoString()+"]");sb.append("\n");
			sb.append("Diameter:\t"+BNF.sF(traj.getRadio()*2*metrics.getDistanceFactor())+"\t["+metrics.getDistUnittoString()+"]");sb.append("\n");
			sb.append("Micro D:\t"+BNF.sF(traj.getMicroD())+"\t["+metrics.getDifUnit()+"]");sb.append("\n");
//			sb.append("MacroDiffusion:\t\t"+BNF.sFormat(traj.getMacroD())+"\t["+Metrics.getDifUnit()+"]");sb.append("\n");
			sb.append("Velocity:\t"+BNF.sF(BMaths.avg(traj.getVelocity()))+"\t["+metrics.getVelUnit()+"]");sb.append("\n");
			sb.append("Angle:\t"+BNF.sF(BMaths.avg(traj.getAngles()))+"\t[°]");sb.append("\n");
			sb.append("Model type:\t"+traj.getTrajTypeToString());sb.append("\n");
			if(traj.isAnalyzed())
			{
				switch(traj.getTrajType())
				{
					case TypeOfMotionAnalysis.CORRALLED:
						sb.append("D:\t"+BNF.sF(traj.getTypeParams()[0])+"\n");
						sb.append("L:\t"+BNF.sF(traj.getTypeParams()[1])+"\n");
						break;
					case TypeOfMotionAnalysis.ANOMALOUS:
						sb.append("D:\t"+BNF.sF(traj.getTypeParams()[0])+"\n");
						sb.append("alfa:\t"+BNF.sF(traj.getTypeParams()[1])+"\n");
						break;
					case TypeOfMotionAnalysis.NORMAL:
						sb.append("D:\t"+BNF.sF(traj.getTypeParams()[0])+"\n");
						break;
					case TypeOfMotionAnalysis.DIRECTED:
						sb.append("D:\t"+BNF.sF(traj.getTypeParams()[0])+"\n");
						sb.append("V:\t"+BNF.sF(traj.getTypeParams()[1])+"\n");
						break;
					default:
						System.out.println("info traj is analyzed   trajtype   defaulttt!!!!!!!!");
						break;				
				}
			}			
			sb.append("\n");

			Subtrajectory[] subtrajs = traj.getSubtrajs();
			sb.append("Subtrajectories:\t"+subtrajs.length+"\n");
			for(int i=0;i<subtrajs.length;i++)
			{
				sb.append("  SubTraj id\t:"+subtrajs[i].getSid());sb.append("\n");
				sb.append("  Frames:\t"+subtrajs[i].getLength());sb.append("\n");
				sb.append("  Time Length:\t"+subtrajs[i].getTrajectoryTime(subtrajs[i].getParticles().length-1)+"["+metrics.getTimeUnittoString()+"]");sb.append("\n");
				sb.append("  Start:\t"+subtrajs[i].getStartFrame());sb.append("\n");
				sb.append("  End:\t"+subtrajs[i].getEndFrame());sb.append("\n");
				sb.append("  Total Distance in path:\t"+subtrajs[i].getTotalDistanceInPath());sb.append("\n");
				sb.append("  Diameter:\t"+BNF.sF(subtrajs[i].getRadio()*2)+"["+metrics.getDistUnittoString()+"]");sb.append("\n");
				sb.append("  Diffusion:\t"+BNF.sF(subtrajs[i].getMicroD())+" ["+metrics.getDifUnit()+"]");sb.append("\n");
//				sb.append("  MacroDiffusion:\t\t"+BNF.sFormat(subtrajs[i].getMacroD())+" ["+Metrics.getDifUnit()+"]");sb.append("\n");
				sb.append("  Velocity:\t"+BNF.sF(BMaths.avg(subtrajs[i].getVelocity()))+" ["+metrics.getVelUnit()+"]");sb.append("\n");
				sb.append("  Angle:\t"+BNF.sF(subtrajs[i].getFrequenciestAngle())+"[°]");sb.append("\n");
				sb.append("  User type:\t"+subtrajs[i].getUserType());sb.append("\n");
				sb.append("  Usability:\t\t"+subtrajs[i].getUsability());sb.append("\n");
				sb.append("  MSD is ok:\t\t"+subtrajs[i].isMsdOK());sb.append("\n");
				sb.append("  Trajectory type:\t\t"+subtrajs[i].getTrajTypeToString());sb.append("\n");
				sb.append("  Usar:\t"+subtrajs[i].isUsar());sb.append("\n");
				sb.append("\n");
			}
				
			
			sb.append("\n");
			jTextArea_TrajInfo_setText(sb.toString());
			sb.delete(0, sb.length()-1);
			
			
			sb.append("Traj Frame\tX[px]\tY[px]\tIntensity\tVelocity\tVector(x,y)\tAngle[°]\tAngleChange\n");
			Particle[] particlesPro = traj.getParticlesPro();
			
			int[] framesPro = traj.getFramesPro();
			double[] velocity = traj.getVelocity();
			double[][] vectors = traj.getVectors();
			double[] angles = traj.getAngles();
			double[] anglesChanges = traj.getAnglesChanges();
			
			
			sb.append((1)+"\t"+BNF.sF(particlesPro[0].getX())+"\t"+
							BNF.sF(particlesPro[0].getY())+"\t"+
							BNF.sF(particlesPro[0].getBrightness())+"\t-\t-,-\t-\t-\n");
			int count = 0;
			int count2 = 0;
			System.out.println("count:"+count);
			if(framesPro[1]!=0)
			{
				sb.append((2)+"\t"+BNF.sF(particlesPro[1].getX())+"\t"+
						BNF.sF(particlesPro[1].getY())+"\t"+
						BNF.sF(particlesPro[1].getBrightness())+"\t"+
						BNF.sF(velocity[count])+"\t"+
						BNF.sF(vectors[count][0])+","+
						BNF.sF(vectors[count][1])+"\t"+
						BNF.sF(angles[count])+"\t-\n");
				count++;
			}
			else
			{
				sb.append((2)+"\t"+
						BNF.sF(particlesPro[1].getX())+"\t"+
						BNF.sF(particlesPro[1].getY())+"\t"+
						BNF.sF(particlesPro[1].getBrightness())+"\t"+
						"-\t-,-\t-\t-\n");
				
			}
			System.out.println("count:"+count);
			System.out.println("particlePro.l:"+particlesPro.length+" velocity.l:"+velocity.length+"vectors.l:"+vectors.length+" angles.l:"+angles.length+" anglesChanges:"+anglesChanges.length);
			for(int i=2;i<particlesPro.length;i++)
			{
				sb.append((i+1)+"\t"+BNF.sF(particlesPro[i].getX())+"\t"+BNF.sF(particlesPro[i].getY())+"\t"+BNF.sF(particlesPro[i].getBrightness())+"\t");
				if(framesPro[i]!=0)
				{
					sb.append(BNF.sF(velocity[count])+"\t"+BNF.sF(vectors[count][0])+","+BNF.sF(vectors[count][1])+"\t"+BNF.sF(angles[count])+"\t");
					count++;
					
					if(count2<anglesChanges.length)
					{//TODO revisar esto...
						sb.append(BNF.sF(anglesChanges[count2])+"\n");
						count2++;
					}
					System.out.println("i:"+i+" count:"+count+" count2"+count2);
				}
				else
				{
					sb.append("-\t-,-\t-\t-\n");
				}
			}
			sb.append("\n");
			sb.append("TimeLag["+metrics.getTimeUnittoString()+"]\tMSD["+metrics.getDistUnittoString()+"^2]\t"+"NPoints\n");
			double[] timeLag = traj.getTimeLag();
			double[] msd = traj.getMSD(); 
			double[] msdSD = traj.getMSDSD();
			double[] msdVar = traj.getMSDVar();
			double[] msdNPoints = traj.getMSDNPoints();
			
			for(int i=0;i<msd.length;i++)
				sb.append(BNF.sF(timeLag[i])+"\t"+BNF.sF(msd[i])+"\t"+BNF.sF(msdNPoints[i])+"\n");
			
			double[] disp = traj.getDisplacements();
			double[] sq_disp = traj.getSqDis();
			double[] ssd = traj.getSSD();
			int[] gstate = traj.getGState();
			sb.append("\n");
			sb.append("Frame\tDisplacement["+metrics.getDistUnittoString()+"]\tsq_disp\tSSD["+metrics.getDistUnittoString()+"^2]\tGuessState\n");
			sb.append("1\t/\t/\t"+BNF.sF(ssd[0])+"\n");
			if(traj.isHMMAnalyzed())
			{
				for(int i=0;i<disp.length;i++)
					sb.append((i+2)+"\t"+BNF.sF(disp[i])+"\t"+BNF.sF(sq_disp[i])+"\t"+BNF.sF(ssd[i+1])+"\t"+gstate[i]+"\n");
			}
			else
			{
				for(int i=0;i<disp.length;i++)
					sb.append((i+2)+"\t"+BNF.sF(disp[i])+"\t"+BNF.sF(sq_disp[i])+"\t"+BNF.sF(ssd[i+1])+"\n");
			}
			
			jTextArea_TrajPoints_setText(sb.toString());
			jScrollBars_reset();
		}
	}
	
	public String userTypeInfo(boolean userType)
	{
		StringBuffer sb = new StringBuffer();
		String label;
		String types;
		TrajSet trajSet = thinker.getSelectedSet();
		Trajectory[][] trajs = new Trajectory[5][];
		if(userType)
		{
			label = "\nUser Type resume:\n";
			types = "\tUnclassified\tType 1\tType 2\tType 3\tType 4\tUnit";
			trajs[0] = trajSet.getTrajsWithSubtrajsOfUserType(true, false, false, false, false);
			trajs[1] = trajSet.getTrajsWithSubtrajsOfUserType(false, true, false, false, false);
			trajs[2] = trajSet.getTrajsWithSubtrajsOfUserType(false, false, true, false, false);
			trajs[3] = trajSet.getTrajsWithSubtrajsOfUserType(false, false, false, true, false);
			trajs[4] = trajSet.getTrajsWithSubtrajsOfUserType(false, false, false, false, true);
		}
		else
		{
			label = "\nModes of Motion resume:\n";
			types = "\tUnclassified\tNormal\tAnomalous\tConstrained\tDirected\tUnit\n";
			trajs[0] = trajSet.getTrajsWithSubtrajsOfType(new boolean[]{true, false, false, false, false});
			trajs[1] = trajSet.getTrajsWithSubtrajsOfType(new boolean[]{false, true, false, false, false});
			trajs[2] = trajSet.getTrajsWithSubtrajsOfType(new boolean[]{false, false, true, false, false});
			trajs[3] = trajSet.getTrajsWithSubtrajsOfType(new boolean[]{false, false, false, true, false});
			trajs[4] = trajSet.getTrajsWithSubtrajsOfType(new boolean[]{false, false, false, false, true});
			
			
		}
		
		double[][] microD = new double[5][];
		double[][] largo = new double[5][];
		double[][] velocidad = new double[5][];
		double[][] duracion = new double[5][];
		double[] duracionG = new double[5];
		double[][] modelD = new double[5][];
		
		for(int i=0;i<5;i++)
		{
			largo[i] = new double[trajs[i].length];
			velocidad[i] = new double[trajs[i].length];
			duracion[i] = new double[trajs[i].length];
			microD[i] = new double[trajs[i].length];
			modelD[i] = new double[trajs[i].length];
			for(int j=0;j<trajs[i].length;j++)
			{
				largo[i][j] = trajs[i][j].getLength();
				velocidad[i][j] = BMaths.avg(trajs[i][j].getVelocity());
				duracion[i][j] = trajs[i][j].getTrajectoryTime(trajs[i][j].getParticles().length-1);
				microD[i][j] = trajs[i][j].getMicroD();
				modelD[i][j] =  trajs[i][j].getTypeParams()[0];
			}
			duracionG[i]=BMaths.sum(duracion[i]);
		}
		double[][] largoAvg = new double[5][2];
		double[][] velocidadAvg = new double[5][2];
		double[][] duracionAvg = new double[5][2];
		double[][] microDAvg = new double[5][2];
		double[][] modelDAvg = new double[5][2];
		
		
		
		for(int i=0;i<5;i++)
		{
			largoAvg[i] = BMaths.avgstd(largo[i]);
			velocidadAvg[i] = BMaths.avgstd(velocidad[i]);
			duracionAvg[i] = BMaths.avgstd(duracion[i]);
			microDAvg[i] = BMaths.avgstd(microD[i]);
			modelDAvg[i] = BMaths.avgstd(modelD[i]);
		}
		sb.append(label);
		sb.append(types);

		sb.append("\nN° Trajs:\t");
		for(int i=0;i<5;i++)
			sb.append(trajs[i].length+"\t");

		sb.append("\nLargo:\t");
		for(int i=0;i<5;i++)
			sb.append(BNF.sF(largoAvg[i][0])+"+-"+BNF.sF(largoAvg[i][1])+"\t");
		sb.append("[Frames]");
		
		sb.append("\nDuration:\t");
		for(int i=0;i<5;i++)
			sb.append(BNF.sF(duracionAvg[i][0])+"+-"+BNF.sF(duracionAvg[i][1])+"\t");
		sb.append("["+trajSet.getMetrics().getTimeUnittoString()+"]");
		
		sb.append("\nVelocity:\t");
		for(int i=0;i<5;i++)
			sb.append(BNF.sF(velocidadAvg[i][0])+"+-"+BNF.sF(velocidadAvg[i][1])+"\t");
		sb.append("["+trajSet.getMetrics().getVelUnit()+"]");
		
		sb.append("\nMicro D:\t");
		for(int i=0;i<5;i++)
			sb.append(BNF.sF(microDAvg[i][0])+"+-"+BNF.sF(microDAvg[i][1])+"\t");		
		sb.append("["+trajSet.getMetrics().getDifUnit()+"]");
		
		if(!userType)
		{
			sb.append("\nModel D:\t");
			for(int i=0;i<5;i++)
				sb.append(BNF.sF(modelDAvg[i][0])+"+-"+BNF.sF(modelDAvg[i][1])+"\t");
			sb.append("["+trajSet.getMetrics().getDifUnit()+"]");
			
			
			
			/*Corral size (Constrained Motion)*/
			double avgCorralSize = 0;
			double stdCorralSize = 0;
			double[] corralSize = new double[trajs[3].length];
			for(int i=0;i<corralSize.length;i++)
				avgCorralSize+=trajs[3][i].getTypeParams()[1];
			avgCorralSize/=corralSize.length;
			for(int i=0;i<corralSize.length;i++)
				stdCorralSize+=Math.pow(trajs[4][i].getTypeParams()[1]-avgCorralSize,2);
			stdCorralSize=Math.sqrt(stdCorralSize/avgCorralSize);
			
			sb.append("\nCorral Size:\t\t\t\t");
			sb.append(BNF.sF(avgCorralSize)+"+-"+BNF.sF(stdCorralSize)+"\t\t["+trajSet.getMetrics().getDistUnit()+"]");
			
			
			/*Model velocity (Directed Motion)*/
			double avgDirectedVel = 0;
			double stdDirectedVel = 0;
			double[] directedVelocity = new double[trajs[4].length];
			for(int i=0;i<directedVelocity.length;i++)
				avgDirectedVel+=trajs[4][i].getTypeParams()[1];
			avgDirectedVel/=directedVelocity.length;
			for(int i=0;i<directedVelocity.length;i++)
				stdDirectedVel+=Math.pow(trajs[4][i].getTypeParams()[1]-avgDirectedVel,2);
			stdDirectedVel=Math.sqrt(stdDirectedVel/avgDirectedVel);
			
			sb.append("\nModel Vel:\t\t\t\t\t");
			sb.append(BNF.sF(avgDirectedVel)+"+-"+BNF.sF(stdDirectedVel)+"\t["+trajSet.getMetrics().getVelUnit()+"]");
		}
		sb.append("\n");
		return sb.toString();
	}
	public boolean jCheckBox_Varios_isSelected()
	{
		return chckbxGeneralInfo.isSelected();
	}
	public boolean jCheckBox_ModesOfMotion_isSelected()
	{
		return chckbxModesOfMotion.isSelected();
	}
	public boolean jCheckBox_UserType_isSelected()
	{
		return chckbxUserType.isSelected();
	}
	
	public void jTextArea_TrajInfo_setText(String text)
	{
		jTextArea_TrajInfo.setText(text);
	}
	public void jTextArea_TrajInfo_addText(String text)
	{
		jTextArea_TrajInfo.append("\n"+text);
	}	
	public void jTextArea_TrajPoints_setText(String text)
	{
		jTextArea_TrajPoints.setText(text);
	}
	public void jTextArea_TrajPoints_addText(String text)
	{
		jTextArea_TrajPoints.append("\n"+text);
	}
	

	public void jScrollBars_reset()
	{
		System.out.println("reseteando scrollbars");
		jScrollPane.getVerticalScrollBar().setValue(0);
		jScrollPane1.getVerticalScrollBar().setValue(0);
		jScrollPane2.getVerticalScrollBar().setValue(0);
		System.out.println("scrollbars values:"+
				jScrollPane.getVerticalScrollBar().getValue()+" "+
				jScrollPane1.getVerticalScrollBar().getValue()+" "+
				jScrollPane2.getVerticalScrollBar().getValue());
	}
	
	
	
	
	/*public void jTextPane_GlobalInfo_setText(String text)
	{
		textPaneGlobalInfo.setText(text);
	}*/	
	protected void append(String s, AttributeSet attributes) 
	{
		Document d = textPaneGlobalInfo.getDocument();
		try {
			d.insertString(d.getLength(), s, attributes);
		} catch (BadLocationException ble) {}
	}
}
