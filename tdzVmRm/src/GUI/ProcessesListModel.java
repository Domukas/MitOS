/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import tdzOS.OS;

/**
 *
 * @author Domukas
 */
public class ProcessesListModel implements ListModel {
    
    public LinkedList<tdzOS.Process> processes;
    Random rnd;
    
    public ProcessesListModel(OS os)
    {
        processes = os.processes;
    }

    public int getSize() {
        return processes.size();
    }

    public Object getElementAt(int index) {
        return index;
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }
        

}
