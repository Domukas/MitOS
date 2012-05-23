/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzVmRm.Processor;
import tdzVmRm.Word;

/**
 *
 * @author Zory
 */
public class SharedMemoryControl extends Process{
    
    String command;
    int adress;
    Process VM;
    
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
        //Issisaugom ka gavom
        
        LinkedList<Object> temp = new LinkedList<>();
        temp =(LinkedList<Object>) pd.ownedResources.getFirst().value;
        
        command = (String)temp.get(0);
        adress = (Integer)temp.get(1);
        VM = (Process)temp.get(2);
        
        System.out.println("SharedMemoryControl tikrina ar bloką bando užrakinti");
        if(command.equals("LCK"))
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
        if(!pd.processor.S.isBitSet((int)pd.ownedResources.get(1).value)) //0 ar 1 nesvarbu, nes ir tas ir tas turetu ta pati rodyti
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
        if(!pd.processor.S.isBitSet((int)pd.ownedResources.get(1).value))
        {
            next();
        }
    }
    
    //5
    private void blocking()
    {
        System.out.println("SharedMemoryControl rakina bloką");
        pd.processor.S.setBit((int)pd.ownedResources.get(1).value);
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
        if(!pd.processor.S.isBitSet((int)pd.ownedResources.get(1).value))
        {
            goTo(13);
        }
        else
        {
            next();
        }
    }
    
    //8
    private void isBlockedByThisVM1()//TODO
    {
        System.out.println("SharedMemoryControl tikrina ar bloka uzrakino ta pati VM");
        if(pd.ownedResources.get(2).value == pd.parent.pd.children.get(1)) //ar uzrakino ta pati masina
        {
            next();
        }
        else
        {
            goTo(14);
        }
    }
    
    //9
    private void unblocking()
    {
        System.out.println("SharedMemoryControl atrakinamas blokas");
        pd.processor.S.unsetBit((int)pd.ownedResources.get(1).value);
        goTo(13);
    }
    
    //10
    private void isBlockLocked()
    {
        System.out.println("SharedMemoryControl tikrina tas blokas užrakintas");
        if(pd.processor.S.isBitSet((int)pd.ownedResources.get(1).value))
        {
            next();
        }
        else
        {
            goTo(15);
        }
    }
    
    //11
    private void isBlockedByThisVM2()//TODO
    {
        System.out.println("SharedMemoryControl tikrina ar bloka uzrakino ta pati VM");
        if(pd.ownedResources.get(2).value == pd.parent.pd.children.get(1)) //ar uzrakino ta pati masina
        {
            next();
        }
        else
        {
            goTo(16);
        }
    }
    
    //12
    private void readingOrWriting()//TODO
    {
        System.out.println("SharedMemoryControl atlieka rašymą arba skaitymą");
        if((String)pd.ownedResources.getFirst().value == "X1")
        {
          //  pd.processor.R1.setValue((Word)pd.ownedResources.get(1).value); //nukopijuoja adreso reiksme/
        }
        else if((String)pd.ownedResources.getFirst().value == "X2")
        {
         //   pd.processor.R2.setValue((Word)pd.ownedResources.get(2).value);
        }
        else if((String)pd.ownedResources.getFirst().value == "Z1")
        {
      //     pd.ownedResources.get(2).value  .setValue(pd.processor.R1.getValue());
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
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, createMessage("0"));
        
        goTo(1);
    }
    
    //14
    private void createResourceEiluteAtmintyje1()
    {
        System.out.println("SharedMemoryControl kuria resursą [EiluteAtmintyje] su parametru [Bandoma atrakinti kitos VM užrakintą bloką]");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.EiluteAtmintyje, createMessage("Bandoma atrakinti kitos VM užrakintą bloką"));
        next();
    }
    
    //15
    private void createResourcePranesimasJobGovernor1()
    {
        System.out.println("SharedMemoryControl kuria resursą [PrnesimasJobGovernor] su parametru [1]");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, createMessage("1"));
        goTo(1);
    }
    
    //16
    private void createResourceEiluteAtmintyje2()
    {
        System.out.println("SharedMemoryControl kuria resursą [EiluteAtmintyje] su parametru [Atmintis neprieinama]");
          
        //Kuriamas resursas..
        pd.core.createResource(this, OS.ResName.EiluteAtmintyje, createMessage("Atmintis neprieinama"));
        goTo(15);
    }
}
