package main;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TableModelSubTrajs extends DefaultTableModel implements TableModel, TableModelListener
{
	public TableModelSubTrajs()
	{
		super();
		super.setColumnIdentifiers(new Object[]{"Use","id","Start/End","Type"});
		//super.setDataVector(data, columnNames);		
	}
	public void removeAllRows()
	{
		int rowsNumber = this.getRowCount();
//		System.out.println("Row count: "+rowsNumber);
		
		for(int i=0;i<rowsNumber;i++)
			this.removeRow(0);
		//this.fireTableDataChanged();
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		switch(columnIndex)
		{
			case 0:return Boolean.class;
			case 1:return String.class;
			case 2:return String.class;
			case 3:return String.class;
			default:return getValueAt(0, columnIndex).getClass();				
		}
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}
