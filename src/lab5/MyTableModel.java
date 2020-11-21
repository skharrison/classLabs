package lab5;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 533696381671079810L;

	public MyTableModel() {
      super(new String[]{"Chrom", "Start", "Stop", "Verified", "IGV"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 3:
          clazz = Boolean.class;
          break;
        case 4:
          clazz = ImageIcon.class;
          break;
      }
      return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return column == 3;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 3) {
        System.out.println(aValue);
        Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(3, (boolean)aValue);
        fireTableCellUpdated(row, column);
      }
    }

  }
