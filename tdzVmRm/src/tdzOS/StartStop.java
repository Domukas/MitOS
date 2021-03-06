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
import tdzOS.OS;

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
        OS.printToConsole("StartStop kuria sisteminius resursus");  
        
        createResourceVartotojoAtmintis();
        createResourceSupervizorineAtmintis(256);
        createResourceHDD(256);
        
        OS.printToConsole("Kuria IO irenginio resursus");  
        pd.core.createResource(this, ResName.IvedimoIrenginys, createMessage("IvedimoIrenginys"));
        pd.core.createResource(this, ResName.IsvedimoIrenginys, createMessage("IsvedimoIrenginys"));
        OS.printToConsole("IO irenginiu resursai sukurti");  
        
        OS.printToConsole("StartStop kuria garsiakalbio irenginio resursus");
        pd.core.createResource(this, ResName.GarsiakalbioIrenginys, createMessage("0"));
        pd.core.createResource(this, ResName.GarsiakalbioIrenginys, createMessage("1"));
        OS.printToConsole("Garsiakalbio irenginio resursai sukurti");
        
        next();
    }
    
    private void createSystemProcesses()
    {
        OS.printToConsole("StartStop kuria sisteminius procesus");  
        
        OS.printToConsole("Kuriamas procesas Read");  
        pd.core.createProcess(this, ProcessState.Ready, 80, null, ProcName.Read); 
        
        OS.printToConsole("Kuriamas procesas JCL");  
        pd.core.createProcess(this, ProcessState.Ready, 79, null, ProcName.JCL); 
        
        OS.printToConsole("Kuriamas procesas JobToHDD");  
        pd.core.createProcess(this, ProcessState.Ready, 78, null, ProcName.JobToHDD); 
        
        OS.printToConsole("Kuriamas procesas PrintLine");  
        pd.core.createProcess(this, ProcessState.Ready, 60, null, ProcName.PrintLine); 
        
        OS.printToConsole("Kuriamas procesas MainProc");  
        pd.core.createProcess(this, ProcessState.Ready, 77, null, ProcName.MainProc);
        
        OS.printToConsole("Kuriamas procesas Loader");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.Loader); 
        
        OS.printToConsole("Kuriamas procesas Interupt");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.Interrupt);  
        
        OS.printToConsole("Kuriamas procesas GetLine");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.GetLine);  
        
        OS.printToConsole("Kuriamas procesas SoundControl");  
        pd.core.createProcess(this, ProcessState.Ready, 76, null, ProcName.SoundControl);          
        
        
        
        next();
    }
    
    private void blockForExit()
    {
        OS.printToConsole("StartStop blokuojasi del MOS pabaigos resurso");  
        pd.core.requestResource(this, ResName.MOSPabaiga, 1);
        
        next();
    }
    
    private void destroySystemProcesses() 
    {
        for (Processor p:RealMachine.proc)
            if (p.pd.currentProcess != null)
                if (p.pd.currentProcess != this)
                    pd.core.stopProcess(p.pd.currentProcess);
        
        OS.printToConsole("StartStop naikina procesus");  
        LinkedList<Process> temp = new LinkedList<>();
        for (Process p:pd.children)
            temp.add(p);
        
        for (Process p:temp)
            pd.core.destroyProcess(p);
        
        pd.core.blockedProcesses.clear();
        pd.core.readyProcesses.clear();
        pd.core.runProcesses.clear();
        

        
        next();
    }
    
    private void destroySystemResources() 
    {
        OS.printToConsole("StartStop naikina resursus");
        
        LinkedList<Resource> temp = new LinkedList<>();
        for (Resource r:pd.createdResources)
            temp.add(r);
        
        for (Resource r:temp)
            pd.core.destroyResource(r);
        pd.core.destroyProcess(this);
    }
    
    private void createResourceVartotojoAtmintis()
    {
        OS.printToConsole("Kuriam atminties resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<>();
     
        //Pridedam i komponentu sarasa nuorodas i jau egzistuojancia atminti
        for (int i = 0; i < RealMachine.SHARED_MEMORY_BLOCK_OFFSET; i++)
        {
            memoryBlocks.add(pd.core.rm.memory.getBlock(i));
        }
        
        OS.printToConsole("Nuorodos nukopijuotos. Skaicius:" +
                memoryBlocks.size());
        
        pd.core.createResource(this, ResName.VartotojoAtmintis, memoryBlocks);
        
    }
    
    
    //Supervizorine is string'u del paprastumo...
    private void createResourceSupervizorineAtmintis(int totalBlocks)
    {
        OS.printToConsole("Kuriam supervizorines atminties resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<>();
        
        //Kuriam supervizorines atminties blokus
        for (int i = 0; i < totalBlocks; i++)
        {
            memoryBlocks.add(new String());
        }
        
        OS.printToConsole("Sukurta " + totalBlocks + " supervizorines atminties bloku");
        
        pd.core.createResource(this, ResName.SupervizorineAtmintis, memoryBlocks);
    }
    
    //HDD atmintis taip pat is string...
    private void createResourceHDD(int totalBlocks)
    {
        OS.printToConsole("Kuriam HDD resursa");

        LinkedList<Object> memoryBlocks = new LinkedList<>();
        
        //Kuriam HDD blokus
        for (int i = 0; i < totalBlocks; i++)
        {
            memoryBlocks.add(new String());
        }
        
        OS.printToConsole("Sukurta " + totalBlocks + " HDD bloku");
        
        pd.core.createResource(this, ResName.HDD, memoryBlocks);
    }    
    
}
