/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzOS.OS.ProcessState;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class StartStop extends Process {
    
    public StartStop(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr,
           LinkedList<Resource> or, ProcessState state, int priority,
           Process parent, LinkedList<Process> c, OS core)
    {
        super(inList, internalID, externalID, ps, p, cr, or, state,
                priority, parent, c, core);
        
        
        createSystemResources();
        
    }
 
    private void createSystemResources()
    {
        //Kuriam sisteminius resursus
        System.out.println("StartStop kuria sisteminius resursus");     
        createResourceVartotojoAtmintis();
    }
    
    private void createResourceVartotojoAtmintis()
    {
        System.out.println("Kuriam atminties resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<Object>();
        int totalBlocks = pd.core.rm.memory.getMaxMemoryBlocks();
        
        //Pridedam i komponentu sarasa nuorodas i jau egzistuojancia atminti
        for (int i = 0; i < totalBlocks; i++)
        {
            memoryBlocks.add(pd.core.rm.memory.getBlock(i));
        }
        
        System.out.println("Nuorodos nukopijuotos. Skaicius:" +
                memoryBlocks.size());
        
        pd.core.createResource(this, ResName.VartotojoAtmintis, memoryBlocks);
    }
    
    private void createResourceSupervizorineAtmintis()
    {
        //Supervizorine is string'u
    }
    
}
