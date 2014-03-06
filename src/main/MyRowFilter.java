package main;

import javax.swing.RowFilter;

import data.Trajectory;

public class MyRowFilter extends RowFilter<TableModelTrajectories, Object> 
{

	Trajectory[] trajs;
	int lengthFilter;
	
	public MyRowFilter(Trajectory[] trajs, int lengthFilter)
	{
		this.trajs = trajs;
		this.lengthFilter = lengthFilter;
	}
	
	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends TableModelTrajectories, ? extends Object> entry) 
	{
		
		//TrajectoriesTableModel model = entry.getModel();
	    //Object traj = (((Vector<Object>)(model.getDataVector())).elementAt((Integer)(entry.getIdentifier()))); //(entry.getIdentifier());
		//String type = (String)model.getValueAt((Integer)entry.getIdentifier(), 2);
		
		if(trajs[(Integer)entry.getIdentifier()].getLength() >= lengthFilter)
		{
			/*if(( ( traj.isMultipe() && jCheckBox_Manual_Multiple.isSelected()) ||
					(!traj.isMultipe() && ((jCheckBox_Corralled.isSelected() && traj.getTrajType()==TrajectoryImage.CORRALLED) ||
											(jCheckBox_Anomalous.isSelected() && traj.getTrajType()==TrajectoryImage.ANOMALOUS) ||
											(jCheckBox_Normal.isSelected() && traj.getTrajType()==TrajectoryImage.NORMAL) ||
											(jCheckBox_Directed.isSelected() && traj.getTrajType()==TrajectoryImage.DIRECTED) ||
											(jCheckBox_NotDefined.isSelected() && traj.getTrajType()==TrajectoryImage.NOT_DEFINED)))
				))
			{
				
			}*/

			//System.out.println("incluyendo fila "+traj.getSerialNumber()+" en table model");
	       return true;			       
	    }
		//System.out.println("No incluyendo fila "+traj.getSerialNumber()+" en table model");
		return false;
	}



}
