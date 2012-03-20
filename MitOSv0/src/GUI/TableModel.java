/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Zory
 */
public class TableModel extends AbstractTableModel{
    
    protected String[] columnNames = {"Addr",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F"};
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
