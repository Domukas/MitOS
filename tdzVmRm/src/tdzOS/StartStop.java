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
import tdzVmRm.RealMachine;

/**
 *
 * @author Tomas
 */
public class StartStop extends Process {
    
    public StartStop(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           ProcessState state, int priority, Process parent, OS core)
    {
        super(inList, internalID, externalID, ps, p, or, state,
                priority, parent, core);
        
        //TIK testinimui
        //step();

    }
    
    public void step()
    {
        switch (nextInstruction)
        {
            case 1:
                createSystemResources();
                break;
                
            case 2:
                createSystemProcesses();
                break;
                
            case 3:
                blockForExit();
                break;
                
            case 4:
                destroySystemProcesses();
                break;
                
            case 5:
                destroySystemResources();
                break;
        }
                
    }
 
    private void createSystemResources()
    {
        //Kuriam sisteminius resursus
        System.out.println("StartStop kuria sisteminius resursus");  
        
        createResourceVartotojoAtmintis();
        createResourceSupervizorineAtmintis(265);
        createResourceHDD(256);
        
        System.out.println("Kuria IO irenginio resursus");  
        pd.core.createResource(this, ResName.IvedimoIrenginys, createMessage("IvedimoIrenginys"));
        pd.core.createResource(this, ResName.IsvedimoIrenginys, createMessage("IsvedimoIrenginys"));
        System.out.println("IO irenginiu resursai sukurti");  
        
        System.out.println("StartStop kuria garsiakalbio irenginio resursus");
        pd.core.createResource(this, ResName.GarsiakalbioIrenginys, createMessage("Garsiakalbis#1"));
        pd.core.createResource(this, ResName.GarsiakalbioIrenginys, createMessage("Garsiakalbis#2"));
        System.out.println("Garsiakalbio irenginio resursai sukurti");
        
        next();
    }
    
    private void createSystemProcesses() //TODO
    {
        System.out.println("StartStop kuria sisteminius procesus");  
        
        System.out.println("Kuriamas procesas Read");  
        pd.core.createProcess(this, ProcessState.Ready, 80, null, ProcName.Read); 
        
        System.out.println("Kuriamas procesas JCL");  
        pd.core.createProcess(this, ProcessState.Ready, 79, null, ProcName.JCL); 
        
        System.out.println("Kuriamas procesas JobToHDD");  
        pd.core.createProcess(this, ProcessState.Ready, 78, null, ProcName.JobToHDD); 
        
        System.out.println("Kuriamas procesas PrintLine");  
        pd.core.createProcess(this, ProcessState.Ready, 60, null, ProcName.PrintLine); 
        
        System.out.println("Kuriamas procesas MainProc");  
        pd.core.createProcess(this, ProcessState.Ready, 77, null, ProcName.MainProc);
        
        System.out.println("Kuriamas procesas Loader");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.Loader); 
        
        System.out.println("Kuriamas procesas Interupt");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.Interrupt);  
        
        System.out.println("Kuriamas procesas GetLine");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.GetLine);  
        
        System.out.println("Kuriamas procesas SoundControl");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.SoundControl);          
        
        
        
        next();
    }
    
    private void blockForExit() //TODO
    {
        System.out.println("StartStop blokuojasi del MOS pabaigos resurso");  
        pd.core.requestResource(this, ResName.MOSPabaiga, 1);
        
        next();
    }
    
    private void destroySystemProcesses() //TODO
    {
        System.out.println("StartStop naikina procesus");  
        
        next();
    }
    
    private void destroySystemResources() //TODO
    {
        System.out.println("StartStop naikina resursus");
        
        next();
    }
    
    private void createResourceVartotojoAtmintis()
    {
        System.out.println("Kuriam atminties resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<Object>();
        int totalBlocks = pd.core.rm.memory.getMaxMemoryBlocks();
        
        //Pridedam i komponentu sarasa nuorodas i jau egzistuojancia atminti
        for (int i = 0; i < RealMachine.SHARED_MEMORY_BLOCK_OFFSET; i++)
        {
            memoryBlocks.add(pd.core.rm.memory.getBlock(i));
        }
        
        System.out.println("Nuorodos nukopijuotos. Skaicius:" +
                memoryBlocks.size());
        
        pd.core.createResource(this, ResName.VartotojoAtmintis, memoryBlocks);
        
    }
    
    
    //Supervizorine is string'u del paprastumo...
    private void createResourceSupervizorineAtmintis(int totalBlocks)
    {
        System.out.println("Kuriam supervizorines atminties resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<Object>();
        
        //Kuriam supervizorines atminties blokus
        for (int i = 0; i < totalBlocks; i++)
        {
            memoryBlocks.add(new String());
        }
        
        System.out.println("Sukurta " + totalBlocks + " supervizorines atminties bloku");
        
        pd.core.createResource(this, ResName.SupervizorineAtmintis, memoryBlocks);
    }
    
    //HDD atmintis taip pat is string...
    private void createResourceHDD(int totalBlocks)
    {
        System.out.println("Kuriam HDD resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<Object>();
        
        //Kuriam HDD blokus
        for (int i = 0; i < totalBlocks; i++)
        {
            memoryBlocks.add(new String());
        }
        
        System.out.println("Sukurta " + totalBlocks + " HDD bloku");
        
        pd.core.createResource(this, ResName.HDD, memoryBlocks);
    }    
    
}
