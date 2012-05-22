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
public class ResourcesTableModel extends AbstractTableModel {
    
    OS os;
    
    public ResourcesTableModel(OS os)
    {
        this.os = os;
    }

    @Override
    public int getRowCount() {
        return os.resources.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return os.resources.get(rowIndex).rd.externalID+"#"+os.resources.get(rowIndex).rd.internalID;
    }

    public String getColumnName(int column) {
        return "Resources";
    }

}
