/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tdzVmRm.Processor;
import tdzVmRm.Speaker;
import tdzOS.OS;

/**
 *
 * @author Zory
 */
public class SoundControl extends Process{
    
    int speaker;
    String command;
    VirtualMachine VM;
    
    public SoundControl (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForPranesimasSoundControlProcesui();
                break;
                
            case 2:
                blockForGarsiakalbioIrenginys();
                break;
                
            case 3:
                isCommandPlay();
                break;
                
            case 4:
                sendFrequencyToSoundSpeaker();
                break;
                
            case 5:
                freeResourceGarsiakalbioIrenginys();
                break;
                
            case 6:
                sendVolumeToSoundSpeaker();
                break;
        }
    }
    
    //1
    private void blockForPranesimasSoundControlProcesui()
    {
        OS.printToConsole("SoundControl blokuojasi dėl resurso [Pranešimas SoundControl procesui]");
        pd.core.requestResource(this, OS.ResName.PranesimasSoundControlProcesui, 1);
        
        next();
    }
    
    //2
    private void blockForGarsiakalbioIrenginys()
    {
        OS.printToConsole("SoundControl blokuojasi dėl resurso [Garsiakalbio įrenginys]");
        pd.core.requestResource(this, OS.ResName.GarsiakalbioIrenginys, 1);
        
     //   for(ResComponent rc : pd.ownedResources)
     //       OS.printToConsole(rc.value);
        
        //pasidedam komanda 
        command = (String)pd.ownedResources.getFirst().value;
        VM = (VirtualMachine)pd.ownedResources.getFirst().parent.rd.creator.pd.children.getLast();
        
        next();
    }
    
    //3
    private void isCommandPlay()
    {
        
        String temp = (String)pd.ownedResources.getLast().value;
        //kuri speakeri turi
        speaker = Integer.parseInt(temp);

        //tikrina ar grojimas ar garsumo nustatymas
        if((command.equals("GNR1")) || (command.equals("GNR2")))
        {
            next();
        }
        else
        {
            goTo(6);
        }
    }
    
    //4
    private void sendFrequencyToSoundSpeaker()
    {
        OS.printToConsole("Į garsiakalbį siunčiamas pranešimas groti nurodyto dažnio garsą");
        pd.core.rm.setCH4ClosedForAllProcessors();
        if(command.equals("GNR1"))
        {
            pd.core.rm.speakers[speaker].play(VM.pd.procesorState.R1.getValue().getIntValue()); 
        }
        else
        {
            pd.core.rm.speakers[speaker].play(VM.pd.procesorState.R2.getValue().getIntValue());
        }
        pd.core.rm.setCH4OpenForAllProcessors();
        next();
    }
    
    //5
    private void freeResourceGarsiakalbioIrenginys()
    {
        OS.printToConsole("SoundControl atlaisvina resursą [Garsiakalbio įrenginys]");
        pd.core.freeResource(this, pd.ownedResources.getLast().parent);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //6
    private void sendVolumeToSoundSpeaker()
    {
        OS.printToConsole("Į garsiakalbį siunčiamas pranešimas nustatyti nurodytą garso lygį");
        pd.core.rm.setCH4ClosedForAllProcessors();
        if(command.equals("GGR1"))
        {
            pd.core.rm.speakers[speaker].setLength(VM.pd.procesorState.R1.getValue().getIntValue());
        }
        else
        {
            pd.core.rm.speakers[speaker].setLength(VM.pd.procesorState.R2.getValue().getIntValue()); 
        }
        pd.core.rm.setCH4OpenForAllProcessors();
        goTo(5);
    }
    
}
