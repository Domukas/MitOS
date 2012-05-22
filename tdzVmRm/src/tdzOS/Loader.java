/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class Loader extends Process
{
    public Loader (LinkedList inList, int internalID, OS.ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           OS.ProcessState state, int priority, Process parent, OS core)
    {
        super(inList, internalID, externalID, ps, p, or, state,
            priority, parent, core);
    }


    public void step()
    {
        switch (nextInstruction)
        {
            case 1:
                blockForMessageToLoader();
                break;
            case 2:
                copyToMemory();
                break;
                
            case 3:
                createResourceLoadingFinished();
                break;
        }
    }
    
    //1
    private void blockForMessageToLoader()
    {
        
    }
    
    //2
    private void copyToMemory()
    {
        
    }
    
    //3
    private void createResourceLoadingFinished()
    {
        
    }
}
