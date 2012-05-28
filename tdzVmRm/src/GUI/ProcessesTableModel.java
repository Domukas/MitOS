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
import tdzOS.ProcessDescriptor;

/**
 *
 * @author Domukas
 */
public class ProcessesTableModel extends AbstractTableModel {
    
    OS os;
    String[] columnNames = {"Process",
        "Instruction",
        "State",
        "Priority",
        "Processor",
        "Parent"};
    
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
        return 6;
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessDescriptor pd = os.processes.get(rowIndex).pd;
        switch (columnIndex) {
            case 0: return pd.externalID+"#"+pd.internalID;
                
            case 1: return os.processes.get(rowIndex).nextInstruction;
                
            case 2: return pd.state;
                
            case 3: return pd.priority;
                
            case 4:
                if (pd.processor != null)
                    return pd.processor.pd.number;
                else
                    return "null";
                
            case 5:
                if (pd.parent != null)
                    return pd.parent.pd.externalID+"#"+pd.parent.pd.internalID;
                else
                    return "null";
        }
        return "ERROR";
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

}
