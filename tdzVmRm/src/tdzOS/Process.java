/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class Process {
    
    public ProcessDescriptor pd;
    
    public Process(LinkedList inList, int internalID, String externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr,
           LinkedList<Resource> or, ProcessState state, int priority,
           Process parent, LinkedList<Process> c)
    {
       
        pd = new ProcessDescriptor(inList, internalID, externalID, ps, p,
                cr, or, state, priority, parent, c);
    }
    
    public void step()
    {
        
    }
    
}
