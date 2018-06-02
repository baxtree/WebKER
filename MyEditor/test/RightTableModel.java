package MyEditor.test;

import javax.swing.table.AbstractTableModel;

public class RightTableModel extends AbstractTableModel{
	String[][] data = {{"","",""}};
	String[] column = {"OBJECT","PREDICATE","SUBJECT"};
	String[][] empty = {{"","",""}};
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
