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
public class SharedMemoryControl extends Process{
    
    public SharedMemoryControl (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForPranesimasSharedMemoryControlProcesui();
                break;
                
            case 2:
                isBlockLocking();
                break;
                
            case 3:
                isBlockUnlocked1();
                break;
                
            case 4:
                blockWhileIsUnlocked();
                break;
                
            case 5:
                blocking();
                break;
                
            case 6:
                isBlockUnlocking();
                break;
                
            case 7:
                isBlockUnlocked();
                break;
                
            case 8:
                isBlockedByThisVM1();
                break;
                
            case 9:
                unblocking();
                break;
                
            case 10:
                isBlockLocked();
                break;
                
            case 11: 
                isBlockedByThisVM2();
                break;
                
            case 12:
                readingOrWriting();
                break;
                
            case 13:
                createResourcePranesimasJobGovernor0();
                break;
                
            case 14:
                createResourceEiluteAtmintyje1();
                break;
                
            case 15:
                createResourcePranesimasJobGovernor1();
                break;
                        
            case 16:
                createResourceEiluteAtmintyje2();
                break;
        }
    }
    
    //1
    private void blockForPranesimasSharedMemoryControlProcesui()
    {
        System.out.println("SharedMemoryControl blokuojasi dėl resurso [Pranešimas SharedMemoryControl procesui]");
        pd.core.requestResource(this, OS.ResName.PranesimasSharedMemorycontrolProcesui, 1);
        next();
    }
    
    //2
    private void isBlockLocking()
    {
        if((String)pd.ownedResources.getFirst().value == "LCK")
        {
            next();
        }
        else
        {
            goTo(6);
        }
    }
    
    //3
    private void isBlockUnlocked1()
    {
      /*  if(ar tas blokas atrakintas)
        {
            goTo(5);
        }
        else
        {
            next();
        }*/
    }
    
    //4
    private void blockWhileIsUnlocked()
    {
        next();
    }
    
    //5
    private void blocking()
    {
        goTo(13);
    }
    
    //6
    private void isBlockUnlocking()
    {
     /*   if(ar atrakinamas blokas)
        {
            next();
        }
        else
        {
            goTo(10);
        }*/
    }
    
    //7
    private void isBlockUnlocked() //doke klaida pagal mane
    {
      /*  if(ar tas blokas atrakintas)
        {
            goTo(13);
        }
        else
        {
            next();
        }*/
    }
    
    //8
    private void isBlockedByThisVM1()
    {
     /*   if(ar ta blokas uzrakino ta pati VM)
        {
            next();
        }
        else
        {
            goTo(14);
        }*/
    }
    
    //9
    private void unblocking()
    {
        goTo(13);
    }
    
    //10
    private void isBlockLocked()
    {
    /*    if(ar ta blokas uzrakintas)
        {
            next();
        }
        else
        {
            goTo(15);
        }*/
    }
    
    //11
    private void isBlockedByThisVM2()
    {
    /*    if(ar ta bloka uzrakino ta pati VM)
        {
            next();
        }
        else
        {
            goTo(16);
        }*/
    }
    
    //12
    private void readingOrWriting()
    {
        next();
    }
    
    //13
    private void createResourcePranesimasJobGovernor0()
    {
        goTo(1);
    }
    
    //14
    private void createResourceEiluteAtmintyje1()
    {
        next();
    }
    
    //15
    private void createResourcePranesimasJobGovernor1()
    {
        goTo(1);
    }
    
    //16
    private void createResourceEiluteAtmintyje2()
    {
        goTo(15);
    }
}
