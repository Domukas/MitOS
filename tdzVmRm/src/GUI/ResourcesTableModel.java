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
import tdzOS.ResourceDescriptor;
import tdzVmRm.RealMachine;

/**
 *
 * @author Domukas
 */
public class ResourcesTableModel extends AbstractTableModel {
    
    OS os;
    String[] columnNames = {"Resource",
        "Creator",
        "Components"};
    
    public ResourcesTableModel(OS os)
    {
        this.os = os;
    }

    @Override
    public int getRowCount() {
        int resOwned = 0;
        try {
            resOwned = os.processes.get(RealMachine.gui.procJTable.getSelectedRow()).pd.ownedResources.size();
        }
        catch (java.lang.IndexOutOfBoundsException e) {}
        return os.resources.size()+resOwned;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ResourceDescriptor rd;
        if (os.resources.size() > rowIndex)
            rd = os.resources.get(rowIndex).rd;
                else
                    if (RealMachine.gui.procJTable.getSelectedRow() >= 0)
                        rd = os.processes.get(RealMachine.gui.procJTable.getSelectedRow()).pd.ownedResources.get(rowIndex-os.resources.size()).parent.rd;
                    else
                        return "ERROR";
            
        switch (columnIndex) {
                case 0: return rd.externalID+"#"+rd.internalID;

                case 1: return rd.creator.pd.externalID+"#"+rd.creator.pd.internalID;

                case 2: return rd.components.size();
            }
        return "ERROR";    
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

}
