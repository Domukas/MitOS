/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import IODevices.InputWindow;
import java.util.LinkedList;
import tdzVmRm.MemoryBlock;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;

/**
 *
 * @author Zory
 */
public class GetLine extends Process{
    
    String line;
    
    public GetLine (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForPranesimasGetLineProcesui();
                break;
                
            case 2:
                blockForIvedimoIrenginys();
                break;
                
            case 3:
                blockForVartotojoIvestaEilute();
                break;
                
            case 4:
                createEiluteAtmintyje();
                break;
                
            case 5:
                freeResourceIvedimoIrenginys();
                break;
                
            case 6:
                createResourceIvestaEiluteSupervizorinejeAtmintyje();
                break;
        }
    }
    
    //1
    private void blockForPranesimasGetLineProcesui()
    {
        System.out.println("GetLine blokuojasi dėl resurso [Pranešimas GetLine Procesui]");
        pd.core.requestResource(this, OS.ResName.PranesimasGetLineProcesui, 1);
        next();
    }
    
    //2
    private void blockForIvedimoIrenginys()
    {
        System.out.println("GetLine blokuojasi dėl resurso [Įvedimo įrenginys]");
        pd.core.requestResource(this, OS.ResName.IvedimoIrenginys, 1);
        next();
    }
    
    //3
    private void blockForVartotojoIvestaEilute()
    {
        System.out.println("GetLine blokuojasi dėl resurso [Vartotojo įvesta eilutė]");
        pd.core.rm.setCH2ClosedForAllProcessors();
        
        InputWindow inputWindow = new InputWindow(pd.core, this);
        inputWindow.setVisible(true); 
        
        pd.core.requestResource(this, OS.ResName.VartotojoIvestaEilute, 1);
        next();
    }
    
    //4
    private void createEiluteAtmintyje()
    { 
        System.out.println("GetLine įrašo eilute į atmintį");
        String adress = (String)pd.ownedResources.get(0).value;
        line = (String)pd.ownedResources.getLast().value;
        MemoryBlock block = RealMachine.memory.getBlock(Integer.parseInt(adress));
        
        for (int i = 0; i < line.length() / 4 + 1; i++)
        {
            int end = i*4+4;
            if (end > line.length())
                end = line.length();
            
           block.setWord(i, new Word(line.substring(i*4, end))); 
        }
        
        next();
    }
    
    //5
    private void freeResourceIvedimoIrenginys()
    {  
        System.out.println("GetLine atlaisvina resursą [įvedimo įrenginys]");
        pd.core.freeResource(this, pd.ownedResources.getFirst().parent);
        pd.core.rm.setCH2OpenForAllProcessors();
        next();
    }
    
    //6
    private void createResourceIvestaEiluteSupervizorinejeAtmintyje()
    {
        System.out.println("GetLine kuria resursą [įvesta eilutė supervizorinėje atmintyje]");
        
        LinkedList<Object> components = new LinkedList();
        components.add("įvesta");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.IvestaEiluteSupervizorinejeAtmintyje, components);
        
        pd.ownedResources.clear();
        
        //Pereinam i proceso pradine busena
        goTo(1);
    }
}
