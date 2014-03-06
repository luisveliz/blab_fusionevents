package twoStateDiffusion;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TrajectoriesTableModelHMM extends DefaultTableModel/*AbstractTableModel*/ implements TableModel, TableModelListener
{
	public TrajectoriesTableModelHMM()
	{
		super();
		super.setColumnIdentifiers(new Object[]{"Id","Length","D1","D2","P12","P21"});
		
		//super.setDataVector(data, columnNames);		
	}
	public void removeAllRows()
	{
		int rowsNumber = this.getRowCount();
		//System.out.println("Row count: "+rowsNumber);
		
		for(int i=0;i<rowsNumber;i++)
		{
			this.removeRow(0);
		//	System.out.println("quitando row");
		}
		//this.fireTableDataChanged();
	}
	
	@Override
	public void tableChanged(TableModelEvent e) 
	{
		// TODO Auto-generated method stub		
	}
	
	/*	
	@Override
	public void addTableModelListener(TableModelListener l) 
	{
		// TODO Auto-generated method stub
	}
	*/
	
	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		switch(columnIndex)
		{
			case 0:			
				return Number.class;
			case 1:			
				return Number.class;
			case 2:			
				return Number.class;
			case 3:
				return Number.class;
			case 4:
				return Number.class;
			case 5:
				return Number.class;
			default:
				return getValueAt(0, columnIndex).getClass();				
		}
	}
	/*
	@Override
	public int getColumnCount() 
	{
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return columnNames[columnIndex].toString();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
		// TODO Auto-generated method stub
		
	}*/

}
