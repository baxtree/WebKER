package MyEditor.test;

import javax.swing.table.AbstractTableModel;

public class LeftTableModel extends AbstractTableModel{
	String[] column = {"PROPERTY","VALUE"};
	String[][] data = {{"",""}};
	String[][] empty = {{"",""}};
	public int getColumnCount() {
		return column.length;
	}
	public int getRowCount() {
		return data.length;
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	public String getColumnName(int col){
		return column[col];
	}
	public void remove(){
		data = empty;
	}
}
