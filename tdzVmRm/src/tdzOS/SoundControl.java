/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzVmRm.Processor;

/**
 *
 * @author Zory
 */
public class SoundControl extends Process{
    
    int speaker;
    
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
                sendFreaquencyToSoundSpeaker();
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
        System.out.println("SoundControl blokuojasi dėl resurso [Pranešimas SoundControl procesui]");
        pd.core.requestResource(this, OS.ResName.PranesimasSoundControlProcesui, 1);
        next();
    }
    
    //2
    private void blockForGarsiakalbioIrenginys()
    {
        System.out.println("SoundControl blokuojasi dėl resurso [Garsiakalbio įrenginys]");
        pd.core.requestResource(this, OS.ResName.GarsiakalbioIrenginys, 1);
        next();
    }
    
    //3
    private void isCommandPlay()
    {
        //kuri speakeri turi
        speaker = (int)pd.ownedResources.getLast().value;

        //tikrina ar grojimas ar garsumo nustatymas
        if(((String)pd.ownedResources.getFirst().value == "GNR1") || ((String)pd.ownedResources.getFirst().value == "GNR2"))
        {
            next();
        }
        else
        {
            goTo(6);
        }
    }
    
    //4
    private void sendFreaquencyToSoundSpeaker()
    {
        System.out.println("Į garsiakalbį siunčiamas pranešimas groti nurodyto dažnio garsą");
        pd.core.rm.setCH4ClosedForAllProcessors();
        if((String)pd.ownedResources.getFirst().value == "GNR1")
        {
            pd.core.rm.speakers[speaker].play(pd.procesorState.R1.getValue().getIntValue());   
        }
        else
        {
            pd.core.rm.speakers[speaker].play(pd.procesorState.R2.getValue().getIntValue());   
        }
        pd.core.rm.setCH4OpenForAllProcessors();
        next();
    }
    
    //5
    private void freeResourceGarsiakalbioIrenginys()
    {
        System.out.println("SoundControl atlaisvina resursą [Garsiakalbio įrenginys]");
        pd.core.freeResource(this, pd.ownedResources.getFirst().parent);
        goTo(1);
    }
    
    //6
    private void sendVolumeToSoundSpeaker()
    {
        System.out.println("Į garsiakalbį siunčiamas pranešimas nustatyti nurodytą garso lygį");
        pd.core.rm.setCH4ClosedForAllProcessors();
        if((String)pd.ownedResources.getFirst().value == "GGR1")
        {
            pd.core.rm.speakers[speaker].setVolume(pd.procesorState.R1.getValue().getIntValue());
        }
        else
        {
            pd.core.rm.speakers[speaker].setVolume(pd.procesorState.R2.getValue().getIntValue());  
        }
        pd.core.rm.setCH4OpenForAllProcessors();
        goTo(5);
    }
    
}
