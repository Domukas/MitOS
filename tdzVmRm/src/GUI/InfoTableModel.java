/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import tdzOS.OS;

/**
 *
 * @author Domukas
 */
public class InfoTableModel extends AbstractTableModel {
    
    OS os;
    
    public InfoTableModel(OS os)
    {
        this.os = os;
    }

    @Override
    public int getRowCount() {
        return Math.max(os.resources.size(), os.processes.size());
    }

    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (rowIndex < os.processes.size())
                return os.processes.get(rowIndex).pd.externalID+"#"+os.processes.get(rowIndex).pd.internalID;
        }
        else
            if (rowIndex < os.resources.size())
                return os.resources.get(rowIndex).rd.externalID+"#"+os.resources.get(rowIndex).rd.internalID;
        
        return "";
    }

    public String getColumnName(int column) {
        if (column == 0)
            return "Processes";
        else
            return "Resources";
    }

}
