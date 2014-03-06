package data;

import java.util.ArrayList;

import bTools.BMaths;

import typeOfMotion.TypeOfMotionAnalysis;
import Jama.util.Maths;

public class TrajSet 
{
	public final static int TXT = 0;
	public final static int IMAGE = 1;
	public final static int SIMULATED = 2;
	private int originalSource;
	private String originalFileDirectory;
	private String originalFileName;
	private String userName;
	
	//Set of TXT Trajectory
	private Trajectory[] trajectories;
	//Data
	private int lengthFilter;
	
	boolean analyzed;
	
	Metrics metrics;
	
	public TrajSet(int originalSource, String originalFileDirectory, String originalFileName, Trajectory[] trajs, Metrics metrics)
	{
		lengthFilter = 0;
		this.originalSource = originalSource;
		this.originalFileDirectory = originalFileDirectory;
		this.originalFileName = originalFileName;
		userName = originalFileName;
		this.trajectories = trajs;
		analyzed = false;
		this.metrics = metrics;
	}
	
	
	public void setMetrics(double frameStep, double distanceFactor, int timeUnit, int distanceUnit)
	{
		System.out.println("seteando metricas en selescted set");
		this.metrics = new Metrics(frameStep, distanceFactor, timeUnit, distanceUnit);
		
		for(int i=0;i<getNumOfTrajs();i++)
		{
			System.out.println("actualizando trayectoria "+i+" subtrajs:"+getTraj(i).getSubtrajs().length);
			getTraj(i).setMetrics(metrics);
			getTraj(i).updateMetricData();
			
			for(int j=0;j<getTraj(i).getSubtrajs().length;j++)
			{
				System.out.println("actualizando subtrayectoria "+i+" "+j);
				getTraj(i).getSubtraj(j).setMetrics(metrics);
				getTraj(i).getSubtraj(j).updateMetricData();
			}
		}
	}
	public Metrics getMetrics()
	{
		return metrics;
	}
	
	
	public void setLengthFilter(int lengthFilter)
	{
		this.lengthFilter = lengthFilter;
	}
	public int getOriginalSource()
	{
		return originalSource;
	}
	public String getOriginalSourceToString()
	{
		switch(originalSource)
		{
			case IMAGE:return "Image";
			case TXT:return "TXT";
			case SIMULATED:return "Sim";
			default:return "dafault!!";
		}
	}
	public String getName()
	{
		return originalFileName;
	}
	public String getDirectory()
	{
		return originalFileDirectory;
	}
	
	private int getArrayIndex(int id)
	{
		int index = 0;
		while(index<getNumOfTrajs() && trajectories[index].getId()!=id)
			index++;
		return index;
	}
	public void deleteTraj(int id)
	{
		Trajectory[] newTrajs = new Trajectory[getNumOfTrajs()-1];
		int count = 0;
		for(int i=0;i<getNumOfTrajs();i++)
		{
			if(trajectories[i].getId()!=id)
			{
				newTrajs[count]=trajectories[i];
				count++;
			}
		}
		trajectories = newTrajs;
	}
	public Trajectory getTrajById(int id)
	{
		return trajectories[getArrayIndex(id)];
	}
	public Trajectory getTraj(int index) 
	{
		return trajectories[index];
	}
	public Trajectory[] getTrajs() 
	{
		return trajectories;
	}
	public Trajectory[] getTrajsOfType(boolean unclassified, boolean normal, boolean anomalous, boolean constrained, boolean directed) 
	{
		Trajectory[] trajs;
		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		int type;
		for(int i=0;i<trajectories.length;i++)
		{
			type = trajectories[i].getTrajType();
			if(trajectories[i].getLength()>=lengthFilter && trajectories[i].isUsar() &&
				(type==TypeOfMotionAnalysis.NOT_DEFINED && unclassified ||
				 type==TypeOfMotionAnalysis.NORMAL && normal || 
				 type==TypeOfMotionAnalysis.ANOMALOUS && anomalous ||
				 type==TypeOfMotionAnalysis.CORRALLED && constrained ||
				 type==TypeOfMotionAnalysis.DIRECTED && directed))
					list.add(trajectories[i]);
		}
		trajs = new Trajectory[list.size()];
		for(int i=0;i<list.size();i++)
			trajs[i] = list.get(i);
		return trajs;
	}
	public Trajectory[] getAllTrajsOfType(int length, boolean unclassified, boolean normal, boolean anomalous, boolean constrained, boolean directed) 
	{
		Trajectory[] trajs;
		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		int type;
		for(int i=0;i<trajectories.length;i++)
		{
			type = trajectories[i].getTrajType();
			if(	((type==TypeOfMotionAnalysis.NOT_DEFINED && unclassified) ||
				 (type==TypeOfMotionAnalysis.NORMAL && normal) || 
				 (type==TypeOfMotionAnalysis.ANOMALOUS && anomalous) ||
				 (type==TypeOfMotionAnalysis.CORRALLED && constrained) ||
				 (type==TypeOfMotionAnalysis.DIRECTED && directed)) && trajectories[i].getLength()>=length)
					list.add(trajectories[i]);
		}
		trajs = new Trajectory[list.size()];
		for(int i=0;i<list.size();i++)
			trajs[i] = list.get(i);
		return trajs;
	}
	
	
	
	//No incluye "no usar" y filtra por largo
	public Trajectory[] getTrajsWithSubtrajs() 
	{
		Trajectory[] trajsWithSub;
		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		for(int i=0;i<trajectories.length;i++)
		{
			if(trajectories[i].getSubtrajs().length>0)
			{
				for(int j = 0; j<trajectories[i].getSubtrajs().length;j++)
					if(trajectories[i].getSubtraj(j).getLength()>=lengthFilter && trajectories[i].getSubtraj(j).isUsar())
						list.add(trajectories[i].getSubtraj(j));
			}
			else if(trajectories[i].getLength()>=lengthFilter && trajectories[i].isUsar())
				list.add(trajectories[i]);
		}
		trajsWithSub = new Trajectory[list.size()];
		for(int i=0;i<list.size();i++)
			trajsWithSub[i] = list.get(i);
		return trajsWithSub;
	}
	
	public Trajectory[] getTrajsWithSubtrajsOfUserType(boolean zero, boolean one, boolean two, boolean three, boolean four) 
	{
		Trajectory[] trajsWithSub;
		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		int type;
		for(int i=0;i<trajectories.length;i++)
		{
			if(trajectories[i].getSubtrajs().length>0)
			{
				for(int j = 0; j<trajectories[i].getSubtrajs().length;j++)
				{
					type = trajectories[i].getSubtraj(j).getUserType();
					if(trajectories[i].getSubtraj(j).getLength()>=lengthFilter &&
					   trajectories[i].getSubtraj(j).isUsar() &&
					   (type==0 & zero || type==1 && one || type==2 && two || type==3 && three || type==4 && four))
						list.add(trajectories[i].getSubtraj(j));
				}
			}
			else
			{
				type = trajectories[i].getUserType();
				if(trajectories[i].getLength()>=lengthFilter && trajectories[i].isUsar() && (type==0 & zero || type==1 && one || type==2 && two || type==3 && three || type==4 && four))
					list.add(trajectories[i]);
			}
		}
		trajsWithSub = new Trajectory[list.size()];
		for(int i=0;i<list.size();i++)
			trajsWithSub[i] = list.get(i);
		return trajsWithSub;
	}
	
	public Trajectory[] getTrajsWithSubtrajsOfType(boolean[] trajTypeSelection) 
	{
		Trajectory[] trajsWithSub;
		ArrayList<Trajectory> list = new ArrayList<Trajectory>();
		int type;
		for(int i=0;i<trajectories.length;i++)
		{
			if(trajectories[i].getSubtrajs().length>0)
			{
				for(int j = 0; j<trajectories[i].getSubtrajs().length;j++)
				{
					type = trajectories[i].getSubtraj(j).getTrajType();
					if(trajectories[i].getSubtraj(j).getLength()>=lengthFilter &&
					   trajectories[i].getSubtraj(j).isUsar() &&
					   (type==TypeOfMotionAnalysis.NOT_DEFINED && trajTypeSelection[0] ||
						type==TypeOfMotionAnalysis.NORMAL && trajTypeSelection[1] || 
					    type==TypeOfMotionAnalysis.ANOMALOUS && trajTypeSelection[2] ||
					    type==TypeOfMotionAnalysis.CORRALLED && trajTypeSelection[3] ||
					    type==TypeOfMotionAnalysis.DIRECTED && trajTypeSelection[4]))
						list.add(trajectories[i].getSubtraj(j));
				}
			}
			else
			{
				type = trajectories[i].getTrajType();
				if(trajectories[i].getLength()>=lengthFilter && trajectories[i].isUsar() &&
					(type==TypeOfMotionAnalysis.NOT_DEFINED && trajTypeSelection[0] ||
					 type==TypeOfMotionAnalysis.NORMAL && trajTypeSelection[1] || 
					 type==TypeOfMotionAnalysis.ANOMALOUS && trajTypeSelection[2] ||
					 type==TypeOfMotionAnalysis.CORRALLED && trajTypeSelection[3] ||
					 type==TypeOfMotionAnalysis.DIRECTED && trajTypeSelection[4]))
					list.add(trajectories[i]);
			}
		}
		trajsWithSub = new Trajectory[list.size()];
		for(int i=0;i<list.size();i++)
			trajsWithSub[i] = list.get(i);
		return trajsWithSub;
	}
	
	public int getNumOfTrajs()
	{
		if(trajectories!=null)
			return trajectories.length;
		else
			return 0;
	}
	
	public double[][] getAVGMSD(boolean userType, boolean zeroORunclassified, boolean oneORnormal, boolean twoORanomalous, boolean threeORconstrained, boolean fourORdirected)
	{
		
		Trajectory[] trajectories;
		if(userType)
			trajectories = getTrajsWithSubtrajsOfUserType(zeroORunclassified, oneORnormal, twoORanomalous, threeORconstrained, fourORdirected);
		else
			trajectories = getTrajsWithSubtrajsOfType(new boolean[]{zeroORunclassified, oneORnormal, twoORanomalous, threeORconstrained, fourORdirected});
		
		int maxLength = 0;
		double[] timeLag = new double[0];
		
		for(int i=0;i<trajectories.length;i++)
		{
			if(trajectories[i].getMSD().length>maxLength)
			{
				maxLength = trajectories[i].getMSD().length;
				timeLag = trajectories[i].getTimeLag();
			}
		}
		
		double[] avgMSD = new double[maxLength];
		double[] avgMSDSD = new double[maxLength];
		double[] avgMSDVar = new double[maxLength];
		double[] avgMSDNpoints = new double[maxLength];
		
		double[] msd;
		
		for(int i = 0;i<trajectories.length;i++)
		{
			msd = trajectories[i].getMSD();
			for(int j=0;j<msd.length;j++)
			{
				if(j==4)
					System.out.println("antes i:"+i+" msd["+j+"]:"+msd[j]+" avgMSD["+j+"]"+avgMSD[j]);
				avgMSD[j]+=msd[j];
				if(j==4)
					System.out.println("after i:"+i+" msd["+j+"]:"+msd[j]+" avgMSD["+j+"]"+avgMSD[j]);
				avgMSDNpoints[j]++;
			}
		}
		int[] count = new int[maxLength];
		for(int j=0;j<avgMSD.length;j++)
		{
//			System.out.println("avgMSD["+j+"]"+avgMSD[j]);
			avgMSD[j]/=avgMSDNpoints[j];
//			System.out.println("avgMSD["+j+"]"+avgMSD[j]);
			for(int i=0;i<trajectories.length;i++)
			{
				if(trajectories[i].getMSD().length>j)
				{
					avgMSDSD[j] += Math.pow(trajectories[i].getMSD()[j] - avgMSD[j], 2);
					count[j]++;
				}
			}
		}
		for(int j=0;j<avgMSDSD.length;j++)
		{
			avgMSDVar[j] = avgMSDSD[j]/count[j];
			avgMSDSD[j] = Math.sqrt(avgMSDVar[j]);
		}
		return new double[][]{timeLag, avgMSD, avgMSDNpoints, avgMSDSD, avgMSDVar};
	}
	public double[] getAVGAngleProbability(boolean[] trajTypeSelection, boolean weighted)
	{
		double[] avgAngleProbability = new double[8];
		double[] angleProbability = new double[8];
		Trajectory[] trajs = getTrajsWithSubtrajsOfType(trajTypeSelection);
		int totalLength = 0;		
		for(int i=0; i<trajs.length; i++)
		{
			totalLength+= trajs[i].getLength();
			angleProbability = trajs[i].getAngleProbability();

			
			if(weighted)
			{
				for(int j=0;j<avgAngleProbability.length;j++)
					avgAngleProbability[j]+=angleProbability[j]*trajs[i].getLength();
			}
			else
			{
				for(int j=0;j<avgAngleProbability.length;j++)
					avgAngleProbability[j]+=angleProbability[j];
			}
			
		}
		if(weighted)
		{
			for(int i=0;i<avgAngleProbability.length;i++)
				avgAngleProbability[i]/=totalLength;	
		}
		else
		{
			for(int i=0;i<avgAngleProbability.length;i++)
				avgAngleProbability[i]/=trajs.length;
		}
		return avgAngleProbability;
		
	}
	public double[] getMicroDiffusion(boolean[] trajTypeSelection)
	{
		Trajectory[] trajs = getTrajsWithSubtrajs();
		double[] dif = new double[trajs.length];
		for(int i=0;i<dif.length;i++)
			dif[i]=trajs[i].getMicroD();
		return dif;		
	}
//	public double[] getD12(boolean[] trajTypeSelection)
//	{
//		Trajectory[] trajs = getTrajsWithSubtrajs();
//		double[] dif = new double[trajs.length];
//		for(int i=0;i<dif.length;i++)
//			dif[i]=trajs[i].getD12();
//		return dif;		
//	}
//	public double[] getD13(boolean[] trajTypeSelection)
//	{
//		Trajectory[] trajs = getTrajsWithSubtrajs();
//		double[] dif = new double[trajs.length];
//		for(int i=0;i<dif.length;i++)
//			dif[i]=trajs[i].getD13();
//		return dif;		
//	}
//	public double[] getD14(boolean[] trajTypeSelection)
//	{
//		Trajectory[] trajs = getTrajsWithSubtrajs();
//		double[] dif = new double[trajs.length];
//		for(int i=0;i<dif.length;i++)
//			dif[i]=trajs[i].getD14();
//		return dif;		
//	}
	
	public double[] getMacroDiffusion(boolean[] trajTypeSelection)
	{
		Trajectory[] trajs = getTrajsWithSubtrajsOfType(trajTypeSelection);
		double[] dMacro = new double[trajs.length];
		for(int i=0;i<dMacro.length;i++)
			dMacro[i]=trajs[i].getMacroD();
		return dMacro;		
	}
	public double[] getModelDiffusion(boolean[] trajTypeSelection)
	{
		Trajectory[] trajs = getTrajsWithSubtrajsOfType(trajTypeSelection);
		double[] dModel = new double[trajs.length];
		for(int i=0;i<dModel.length;i++)
			dModel[i]=trajs[i].getTypeParams()[0];
		return dModel;		
	}
	
	
	public double[] getAvgModelDiffusion()
	{
		Trajectory[] trajs = getTrajsWithSubtrajs();
		double dModel = 0;
		double std = 0;
		int count = 0;
		for(int i=0;i<trajs.length;i++)
		{
			if(trajs[i].isAnalyzed())
			{
				dModel+=trajs[i].getTypeParams()[0];
				count++;
			}
		}
		dModel/=count;
		for(int i=0;i<trajs.length;i++)
			if(trajs[i].isAnalyzed())
				std+=Math.pow(trajs[i].getTypeParams()[0]-dModel,2);
		std=Math.sqrt(std/count);
		return new double[]{dModel,std};
	}
		
	public double[][] getParams(int trajType)
	{
		Trajectory[] trajs = new Trajectory[0];
		switch(trajType)
		{
			case TypeOfMotionAnalysis.NORMAL:
				trajs = getTrajsWithSubtrajsOfType(new boolean[]{false,true,false,false,false});
				break;
			case TypeOfMotionAnalysis.ANOMALOUS:
				trajs = getTrajsWithSubtrajsOfType(new boolean[]{false,false,true,false,false});
				break;
			case TypeOfMotionAnalysis.CORRALLED:
				trajs = getTrajsWithSubtrajsOfType(new boolean[]{false,false,false,true,false});
				break;
			case TypeOfMotionAnalysis.DIRECTED:
				trajs = getTrajsWithSubtrajsOfType(new boolean[]{false,false,false,false,true});
				break;
		}
		double[][] params = new double[trajs.length][2];
		for(int i=0;i<trajs.length;i++)
			params[i]=trajs[i].getTypeParams();
		return params;
	}
	
	public double[] getVelocity(boolean[] trajTypeSelection)
	{
		Trajectory[] trajs = getTrajsWithSubtrajsOfType(trajTypeSelection);
		double[] vel = new double[trajs.length];
		for(int i=0;i<vel.length;i++)
			vel[i]=BMaths.avg(trajs[i].getVelocity());
		return vel;		
	}
	public double[] getAngleAVG()
	{
		Trajectory[] trajs = getTrajsWithSubtrajs();
		double[] angleAVG = new double[trajs.length];
		for(int i=0;i<angleAVG.length;i++)
			angleAVG[i]=BMaths.avg(trajs[i].getAngles());
		return angleAVG;		
	}
	public boolean isAnalyzed()
	{
		return analyzed;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
}
