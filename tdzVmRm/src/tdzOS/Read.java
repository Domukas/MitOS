/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class Read extends Process
{
    public Read(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr,
           LinkedList<ResComponent> or, OS.ProcessState state, int priority,
           Process parent, LinkedList<Process> c, OS core)
    {
        super(inList, internalID, externalID, ps, p, cr, or, state,
                priority, parent, c, core);
    }
    
    public void step()
    {
        switch (nextInstruction)
        {
            case 1:
                blockForIvedimoSrautas();
                break;
                
            case 2:
                blockForSupervizorineAtmintis();
                break;
                
            case 3:
                copyLine();
                break;
                
            case 4:
                checkIfDoneCopying();
                break;
                
            case 5:
                createResourceUzduotisSupervizorinejeAtmintyje();
                break;
        }
        
        nextInstruction++;
        
        if (nextInstruction > 5)
        {
            nextInstruction = 1;
        }
    }
    
    private void blockForIvedimoSrautas()
    {
        
    }
    
    private void blockForSupervizorineAtmintis()
    {
        
    }
    
    private void copyLine()
    {
        
    }
    
    private void checkIfDoneCopying()
    {
        
    }
            
    private void createResourceUzduotisSupervizorinejeAtmintyje()
    {
        
    }
}
