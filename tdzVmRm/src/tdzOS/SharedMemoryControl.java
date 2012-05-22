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
public class SharedMemoryControl extends Process{ //TODO NEBAIGTAS IR NEPATIKRINTAS
    
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
                isBlockUnlocked2();
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
        System.out.println("SharedMemoryControl tikrina ar bloką bando užrakinti");
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
        System.out.println("SharedMemoryControl tikrina tas blokas atrakintas");
        if(!pd.core.rm.proc[0].S.isBitSet((int)pd.ownedResources.get(2).value)) //0 ar 1 nesvarbu, nes ir tas ir tas turetu ta pati rodyti
        {                                                                   //laikau kad antrasis komponentas yra adresas, o pirmas pati komanda 
            goTo(5);
        }
        else
        {
            next(); 
        }
    }
    
    //4
    private void blockWhileIsUnlocked()
    {
        System.out.println("SharedMemoryControl blokuojasi kol bus atblokuotas tas blokas");
        if(!pd.core.rm.proc[0].S.isBitSet((int)pd.ownedResources.get(2).value))
        {
            next();
        }
    }
    
    //5
    private void blocking()
    {
        System.out.println("SharedMemoryControl rakina bloką");
        pd.core.rm.proc[0].S.setBit((int)pd.ownedResources.get(2).value);
        goTo(13);
    }
    
    //6
    private void isBlockUnlocking()
    {
        System.out.println("SharedMemoryControl tikrina ar bandomas atrakinti blokas");
        if((String)pd.ownedResources.getFirst().value == "ULC")
        {
            next();
        }
        else
        {
            goTo(10);
        }
    }
    
    //7
    private void isBlockUnlocked2() //doke klaida pagal mane
    {
        System.out.println("SharedMemoryControl tikrina tas blokas atrakintas");
        if(!pd.core.rm.proc[0].S.isBitSet((int)pd.ownedResources.get(2).value))
        {
            goTo(13);
        }
        else
        {
            next();
        }
    }
    
    //8
    private void isBlockedByThisVM1()
    {
        
       // if(pd.core.rm.VM. == )
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
        System.out.println("SharedMemoryControl atrakinamas blokas");
        pd.core.rm.proc[0].S.unsetBit((int)pd.ownedResources.get(2).value);
        goTo(13);
    }
    
    //10
    private void isBlockLocked()
    {
        System.out.println("SharedMemoryControl tikrina tas blokas užrakintas");
        if(pd.core.rm.proc[0].S.isBitSet((int)pd.ownedResources.get(2).value))
        {
            next();
        }
        else
        {
            goTo(15);
        }
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
        System.out.println("SharedMemoryControl atlieka rašymą arba skaitymą");
        if((String)pd.ownedResources.getFirst().value == "X1")
        {
        }
        else if((String)pd.ownedResources.getFirst().value == "X2")
        {
        }
        else if((String)pd.ownedResources.getFirst().value == "Z1")
        {
        }
        else if((String)pd.ownedResources.getFirst().value == "Z2")
        {
        }
        next();
    }
    
    //13
    private void createResourcePranesimasJobGovernor0()
    {
        System.out.println("SharedMemoryControl kuria resursą [PrnesimasJobGovernor] su parametru [rezultatas = 0]");
        LinkedList<Object> components = new LinkedList();
        components.add("rezultatas = 0");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, components);
        
        goTo(1);
    }
    
    //14
    private void createResourceEiluteAtmintyje1()
    {
        System.out.println("SharedMemoryControl kuria resursą [EiluteAtmintyje] su parametru [Bandoma atrakinti kitos VM užrakintą bloką]");
        LinkedList<Object> components = new LinkedList();
        components.add("Bandoma atrakinti kitos VM užrakintą bloką");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.EiluteAtmintyje, components);
        next();
    }
    
    //15
    private void createResourcePranesimasJobGovernor1()
    {
        System.out.println("SharedMemoryControl kuria resursą [PrnesimasJobGovernor] su parametru [rezultatas = 1]");
        LinkedList<Object> components = new LinkedList();
        components.add("rezultatas = 1");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, components);
        goTo(1);
    }
    
    //16
    private void createResourceEiluteAtmintyje2()
    {
        System.out.println("SharedMemoryControl kuria resursą [EiluteAtmintyje] su parametru [Atmintis neprieinama]");
        LinkedList<Object> components = new LinkedList();
        components.add("Atmintis neprieinama");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.EiluteAtmintyje, components);
        goTo(15);
    }
}
