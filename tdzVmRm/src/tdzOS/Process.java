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
public abstract class Process {
    
    public ProcessDescriptor pd;
    public int nextInstruction; 
    
    public static int numberOfInstances = 0;
    
    public Process(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           ProcessState state, int priority, Process parent, OS core)
    {
       
        pd = new ProcessDescriptor(inList, internalID, externalID, ps, p,
                or, state, priority, parent, core); 
        
        nextInstruction = 1;
    }
    
    public abstract void step();

    //Sukuriam sarasa is vieno elemento, kuris yra String'as
    //To reikia, nes primityvas kurti resursa priima tik resurso elementu sarasa
    protected LinkedList<Object> createMessage(String message)
    {
        LinkedList<Object> tempList = new LinkedList<Object>();
        tempList.addLast(message);
        
        return tempList;
    }
    
    //persoka i kuria nors proceso dali
    protected void goTo(int index)
    {
        nextInstruction = index;
    }
    
    protected void next()
    {
        nextInstruction++;
    }
}
