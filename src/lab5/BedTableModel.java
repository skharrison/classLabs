package lab5;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

public class BedTableModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;

	private String[] columnNames = new String[]
	{
		"CHROM","START","STOP","Verified", "IGV",
	};
	
	private Class[] classTypes = new Class[]
	{
		String.class, String.class, String.class, JCheckBox.class, ImageIcon.class
	};

	private List<BedRegion> beds;
	
	public BedTableModel(List<BedRegion> bed)
	{
		this.beds = bed;
	}

	@Override
	public String getColumnName(int column)
	{
		return columnNames[column];
	}
 
	@Override
	public Class<?> getColumnClass(int column)
	{
		return classTypes[column];
	}

	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		
		return beds.size();
	}
	
	@Override
	public boolean isCellEditable(int row, int column)
	{
		if ( column == 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
 

	@Override
	public Object getValueAt(int row, int column) 
	{
		BedRegion b = beds.get(row);
		if(column == 0)
		{
			return b.chrom;
		}
		else if(column == 1)
		{
			return b.start;
		}
		else if(column == 2)
		{
			return b.stop;
		}
		else if(column == 3)
		{
			return b.verified;
		}
		else if(column == 4)
		{
			return b.igvImage;
		}
		else 
		{
			return null;
		}
	}
	
//	public void setValueAt(int row, int column)
//	{
//		BedRegion b = beds.get(row);
//		if(column==3)
//		{
//			b.verified = Boolean.TRUE;
//		}
//		super.fireTableCellUpdated(row, column);
//	}
}
