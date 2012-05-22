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
public class ProcessesTableModel extends AbstractTableModel {
    
    OS os;
    
    public ProcessesTableModel(OS os)
    {
        this.os = os;
    }

    @Override
    public int getRowCount() {
        return os.processes.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return os.processes.get(rowIndex).pd.externalID+"#"+os.processes.get(rowIndex).pd.internalID;
    }

    public String getColumnName(int column) {
        return "Processes";
    }

}
