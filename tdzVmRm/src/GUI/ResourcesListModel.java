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
public class ResourcesListModel implements ListModel {
    
    OS os;
    
    public ResourcesListModel(OS os)
    {
        this.os = os;
    }

    public int getSize() {
        return os.resources.size();
    }

    public Object getElementAt(int index) {
        return os.resources.get(index);
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }
    
}
