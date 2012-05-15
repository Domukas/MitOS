/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class Process {
    
    enum actions
    {;
        public actions getNext()
        {
            return values()[(ordinal()+1) % values().length];
        }
    }
    
    public ProcessDescriptor pd;
    private actions nextInstruction;
    
    public Process(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr,
           LinkedList<Resource> or, ProcessState state, int priority,
           Process parent, LinkedList<Process> c, OS core)
    {
       
        pd = new ProcessDescriptor(inList, internalID, externalID, ps, p,
                cr, or, state, priority, parent, c, core);
        
        ProcName pn = ProcName.GetLine;
        
    }
    
    public void step()
    {

    }
    
    //Sukuriam sarasa is vieno elemento, kuris yra String'as
    //To reikia, nes primityvas kurti resursa priima tik resurso elementu sarasa
    protected LinkedList<Object> createMessage(String message)
    {
        LinkedList<Object> tempList = new LinkedList<Object>();
        tempList.addLast(message);
        
        return tempList;
    }
}
