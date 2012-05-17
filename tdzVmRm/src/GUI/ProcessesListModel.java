/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import tdzOS.OS;

/**
 *
 * @author Domukas
 */
public class ProcessesListModel implements ListModel {
    
    OS os;
    
    public ProcessesListModel(OS os)
    {
        
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getElementAt(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
