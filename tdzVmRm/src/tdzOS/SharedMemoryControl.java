/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;

/**
 *
 * @author Zory
 */
public class SharedMemoryControl extends Process{
    
    String command;
    int adress;
    VirtualMachine VM;
    int[] lockedBLocks = new int[16];
    
    public int blockedFor = -1;
    
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
                locking();
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
                unlocking();
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
        
        pd.ownedResources.clear();
        
        next();
    }
    
    //2
    private void isBlockLocking()
    {
        //Issisaugom ka gavom
        
        LinkedList<Object> temp;
        temp =(LinkedList<Object>) pd.ownedResources.getFirst().value;
        
        command = (String)temp.get(0);
        adress = (Integer)temp.get(1);
        VM = (VirtualMachine)temp.get(2);
        
        for (Object o:temp)
            System.out.println("gauna:" + o);
        
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
        if(!pd.processor.S.isBitSet(adress)) //0 ar 1 nesvarbu, nes ir tas ir tas turetu ta pati rodyti
        {                                    //laikau kad antrasis komponentas yra adresas, o pirmas pati komanda 
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
        
        pd.core.requestResource(this, OS.ResName.BlokasAtrakintas, 1);
        blockedFor = adress;
        
        
        next();
        /*
        if(!pd.processor.S.isBitSet((int)pd.ownedResources.get(1).value))
        {
            next();
        }
        * 
        */
    }
    
    //5
    private void locking()
    {
        blockedFor = -1;
        
        System.out.println("SharedMemoryControl rakina bloką: " + adress);
        lockAll(adress);
        goTo(13);
    }
    
    //6
    private void isBlockUnlocking()
    {
        System.out.println("SharedMemoryControl tikrina ar bandomas atrakinti blokas");
        if(command.equals("ULC"))
        {
            next();
        }
        else
        {
            goTo(10);
        }
    }
    
    //7
    private void isBlockUnlocked2()
    {
        System.out.println("SharedMemoryControl tikrina ar tas blokas atrakintas");
        if(!pd.processor.S.isBitSet(adress))
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
        System.out.println("SharedMemoryControl tikrina ar bloka uzrakino ta pati VM");
        //System.out.println(lockedBLocks + " " + adress);
        if(lockedBLocks[adress] == 1) //ar uzrakino ta pati masina
        {
            next();
        }
        else
        {
            goTo(14);
        }
    }
    
    //9
    private void unlocking()
    {
        System.out.println("SharedMemoryControl atrakinamas blokas");
        unlockAll(adress);
        
        
        //Jei kas nors laukia to bloko atsirakinimo, tai sukuriam resursa, kad tas blokas atrakintas
        for (Process p:pd.core.blockedProcesses)
        {
            if (p instanceof SharedMemoryControl)
            {
                SharedMemoryControl smc = (SharedMemoryControl)p;
                if (smc.blockedFor == adress)
                {
                    LinkedList<Object> tempList = new LinkedList<>();
                    tempList.add(smc);
                    
                    pd.core.createResource(p, OS.ResName.BlokasAtrakintas, tempList);
                    break;
                }
            }
        }
        
        goTo(13);
    }
    
    //10
    private void isBlockLocked()
    {
        System.out.println("SharedMemoryControl tikrina ar tas blokas užrakintas");
        //System.out.println(adress + " " + adress/16 + Integer.toBinaryString(pd.processor.S.getValue()) + pd.processor.S.isBitSet(adress/16));
        if(pd.processor.S.isBitSet(adress/16))
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
        System.out.println("SharedMemoryControl tikrina ar bloka uzrakino ta pati VM");
        if(lockedBLocks[adress/16] == 1) //ar uzrakino ta pati masina
        {
            next();
        }
        
        else
        {
            goTo(16);
        }
 
    }
    
    //12
    private void readingOrWriting()
    {
        System.out.println("SharedMemoryControl atlieka rašymą arba skaitymą");
        
        switch (command)
        {
            case "X1":
                VM.pd.procesorState.R1.setValue(VM.memory.getSharedMemoryWord(adress));
                break;
            case "X2":
                VM.pd.procesorState.R2.setValue(VM.memory.getSharedMemoryWord(adress));
                break;
            case "Z1":
                VM.memory.setSharedMemoryWord(adress,(VM.pd.procesorState.R1.getValue()));
                break;
            case "Z2":
                VM.memory.setSharedMemoryWord(adress,(VM.pd.procesorState.R2.getValue()));
                break;
                
        }
        next();
    }
    
    //13
    private void createResourcePranesimasJobGovernor0()
    {
        System.out.println("SharedMemoryControl kuria resursą [PrnesimasJobGovernor] su parametru [rezultatas = 0]");
          
        //Kuriamas resursas..
        LinkedList<Object> tmp = new LinkedList<>();
        tmp.add("0");
        tmp.add(pd.ownedResources.getFirst().parent.rd.creator);
        
        LinkedList<Object> parameters = new LinkedList<>();
        parameters.add(tmp);
        
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, parameters);
        
        pd.ownedResources.clear();
        
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
        
        LinkedList<Object> tmp = new LinkedList<>();
        tmp.add("1");
        tmp.add(pd.ownedResources.getFirst().parent.rd.creator);
        
        LinkedList<Object> parameters = new LinkedList<>();
        parameters.add(tmp);
        
        pd.core.createResource(this, OS.ResName.PranesimasJobGovernor, parameters);
        
        pd.ownedResources.clear();
        
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
    
    private void lockAll(int number)
    {
        for (Processor p:RealMachine.proc)
        {
            p.S.setBit(number);
            lockedBLocks[number] = 1;
        }
    }
    
    private void unlockAll(int number)
    {
        lockedBLocks[number] = 0;
        
        for (Processor p:RealMachine.proc)
        {
            p.S.unsetBit(number);
        }        
    }
}
