/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzOS.OS.ProcessState;
import tdzOS.OS.ResName;
import tdzVmRm.Memory;
import tdzVmRm.MemoryBlock;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
public class JobGovernor extends Process
{
    LinkedList<String> programInHDD;
    LinkedList<Object> tempList;
    
     //del patogumo
    int PI, SI, commandParameter;
    String OPC;
     
    
    public JobGovernor (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForMemoryForPaging();
                break;
            case 2:
                blockForMemory();
                break;
            case 3:
                createMessageToLoader();
                break;
            case 4:
                blockForLoadingFinished();
                break;
            case 5:
                createSharedMemoryControl();
                break;
            case 6:
                createVM();
                break;
            case 7:
                blockForInterrupt();
                break;
            case 8:
                stopVirtualMachine();
                break;
            case 9:
                isIOorSoundInt();
                break;
            case 10:
                isSharedMemoryInterrupt();
                break;
            case 11:
                destroyVM();
                break;
            case 12:
                freeMemory();
                break;
            case 13:
                destroySharedMemoryControl();
                break;
            case 14:
                createJobInHDD();
                break;
            case 15:
                blockForUnexistingResource();
                break;
            case 16:
                isIOInterrupt();
                break;
            case 17:
                createMessageForSoundControl();
                break;
            case 18:
                isDPInterrupt();
                break;
            case 19:
                createLineInMemory();
                break;
            case 20:
                blockForLinePrinted();
                break;
            case 21:
                createMessageForGetLine();
                break;
            case 22:
                blockForEnteredLineInSupervisor();
                break;
            case 23:
                messageToSharedMemoryControl();
                break;
            case 24:
                blockForResult();
                break;
            case 25:
                isResultZero();
                break;
            case 26:
                activateVirtualMachine();
                break;
            case 27:
                createResourceResumeVM();
                break;
        }

    }
    //1
    private void blockForMemoryForPaging()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Vartotojo atmintis]. Puslapių lentelei saugoti");
        pd.core.requestResource(this, ResName.VartotojoAtmintis, 1);
        next();
    }
    
    //2
    private void blockForMemory()
    {
        //Paziurim, kiek reiks bloku Virtualiai masinai
        programInHDD = (LinkedList<String>)pd.ownedResources.getFirst().value;
        String tempString = (String)programInHDD.getFirst();
        int count = Integer.parseInt(tempString);
        
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Vartotojo atmintis]. Prašo " + count + " blokų");
        pd.core.requestResource(this, ResName.VartotojoAtmintis, count);
        next();
    }
    
    //3
    private void createMessageToLoader()
    {
        OS.printToConsole("JobGovernor kuria pranešimą Loader procesui");
        
        LinkedList<Object> components = new LinkedList<>();
        components.add(programInHDD);
        
        LinkedList<MemoryBlock> memoryBlocks = new LinkedList<>();
        //Sudedam nuoroda i atminti
        for (int i=1; i<pd.ownedResources.size(); i++)
            memoryBlocks.add((MemoryBlock) pd.ownedResources.get(i).value);
        
        components.add(memoryBlocks);
        
        pd.core.createResource(this, ResName.PranesimasLoaderProcesui, components);
        
        next();
    }
    
    //4
    private void blockForLoadingFinished()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Užduoties pakrovimas į vartotojo atmintį baigtas]");
        pd.core.requestResource(this, ResName.UzduotiesPakrovimasBaigtas, 1);
        
        next();
    }
    
    //5
    private void createSharedMemoryControl()
    {
        OS.printToConsole("JobGovernor kuria SharedMemoryControl");
        pd.core.createProcess(this, ProcessState.Ready, 60, null, ProcName.SharedMemoryControl);
        next();
    }
    
    //6
    private void createVM()
    {
        OS.printToConsole("JobGovernor kuria Virtualią mašiną");
        
        LinkedList<ResComponent> toGive = new LinkedList<>();
        
        
        String temp = (String)pd.ownedResources.getLast().value;
        if (temp.length() == 1)
            temp = "0".concat(temp);
        if (programInHDD.getFirst().length() == 1) //Butinai turi but 2 simboliai
            temp += "0";
        temp += programInHDD.getFirst(); //Reikia perduot 2 skaicius...
        pd.ownedResources.getLast().value = temp;
        toGive.add(pd.ownedResources.getLast());
        
        
        pd.core.createProcess(this, ProcessState.Ready, 50, toGive, ProcName.VirtualMachine);
        for (ResComponent rc : pd.ownedResources)
        {
            if (rc.parent.rd.externalID == ResName.UzduotiesPakrovimasBaigtas)
                pd.core.freeResource(this, rc.parent);
        }
        next();
    }
    
    //7
    private void blockForInterrupt()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Pertraukimas]");
        pd.core.requestResource(this, ResName.Pertraukimas, 1);
        for (ResComponent rc : pd.ownedResources)
        {
            if (rc.parent.rd.externalID == ResName.Pertraukimas)
                pd.core.freeResource(this, rc.parent);
        }
        next();
    }
    
    //8
    private void stopVirtualMachine()
    {
        OS.printToConsole("JobGovernor stabdo VM");
        pd.children.getLast().pd.state = ProcessState.BlockedS;
        
        next();
    }
    
    //9
    private void isIOorSoundInt()
    {      
        tempList = (LinkedList<Object>)pd.ownedResources.getLast().value;
        
        
        if (((String)tempList.getFirst() == "DG") || ((String)tempList.getFirst() == "DP") ||
                ((String)tempList.getFirst() == "Garsiakalbis"))
        {
            OS.printToConsole("IO arba garso pertraukimas");
            goTo(16);
        }
        else
        {
            OS.printToConsole("Ne IO arba garso pertraukimas");
            goTo(10);
        }
        
    }
    //10
    private void isSharedMemoryInterrupt()
    {
        if ((String)tempList.getFirst() == "Bendra atmintis")
        {
            OS.printToConsole("Darbo su bendra atmintimi pertraukimas");
            goTo(23);
        }
        else
        {
            OS.printToConsole("Ne darbo su bendra atmintimi pertraukimas. Naikinama VM");
            goTo(11);
        }         
        
    }
    
    //11
    private void destroyVM()
    {
        OS.printToConsole("JobGovernor naikina VM");
        RealMachine.gui.dropVMTab((VirtualMachine)pd.children.getLast());
        pd.core.destroyProcess(pd.children.getLast());
        next();
        
    }
    
    //12
    private void freeMemory()
    {
        OS.printToConsole("JobGovernor atlaisvina atmintį");
        pd.core.freeResource(this, pd.ownedResources.get(2).parent);
        
        next();
    }
    
    //13
    private void destroySharedMemoryControl()
    {
        OS.printToConsole("JobGovernor naikina procesą SharedMemoryControl");
        pd.core.destroyProcess(pd.children.getFirst());
        
        next();
    }
    
    //14
    private void createJobInHDD()
    {
        OS.printToConsole("JobGovernor kuria fiktyvų užduoties resursą");
        LinkedList<String> tmp = new LinkedList<>();
        LinkedList<Object> parameter = new LinkedList<>();
        
        parameter.add(tmp);
        
        pd.core.createResource(this, ResName.UzduotisHDD, parameter);
        
        next();
    }
    
    //15
    private void blockForUnexistingResource()
    {
        pd.core.requestResource(this, ResName.Neegzistuojantis, 1);
        
    }
    
    //16
    private void isIOInterrupt()
    {
        if (((String)tempList.getFirst() == "DG") || ((String)tempList.getFirst() == "DP"))
        {
            OS.printToConsole("IO pertraukimas");
            goTo(18);
        }
        else
        {
            OS.printToConsole("Garso pertraukimas");
            goTo(17);
        }   
    }
    
    //17
    private void createMessageForSoundControl()
    {
       OS.printToConsole("JobGovernor kuria resursą [Pranešimas SoundControl procesui]");
        OPC = (String)tempList.get(1);

        pd.core.createResource(this, ResName.PranesimasSoundControlProcesui,
                createMessage(OPC));    
        
        //pd.ownedResources.removeLast();
        
        goTo (26);
        
    }
    
    //18
    private void isDPInterrupt()
    {
        if ((String)tempList.getFirst() == "DP")
        {
            OS.printToConsole("DP komanda");
            goTo(19);
        }
        else
        {
            OS.printToConsole("DG komanda");
            goTo(21);
        }
    }
    
    //19
    private void createLineInMemory()
    {
        //sukuriam eilute ir ja pasiunciam isvedimo procesui
        
        String temp = "";
        commandParameter = (Integer)tempList.get(1);

        MemoryBlock block = RealMachine.memory.getBlock(commandParameter);
        
        
        for (int i = 0; i < 16; i++)
        {
            String s = block.getWord(i).getValue();
            if (!s.equals("0"))
                temp = temp.concat(s);
        }
        
        pd.core.createResource(this, ResName.EiluteAtmintyje, createMessage(temp));
        
        next(); 
    }
    
    //20
    private void blockForLinePrinted()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Išvesta eilutė]");
        pd.core.requestResource(this, ResName.IsvestaEilute, 1);
        
        goTo(26); //Aktyvuojam VM
    }
    
    //21
    private void createMessageForGetLine()
    {
        OS.printToConsole("JobGovernor kuria resursą [Pranešimas GetLine procesui]");
        commandParameter = (Integer)tempList.get(1);
        
        pd.core.createResource(this, ResName.PranesimasGetLineProcesui,
                createMessage(Integer.toString(commandParameter)));
        
        next();
    }
    
    //22
    private void blockForEnteredLineInSupervisor()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Įvesta eilutė atmintyje]");
        pd.core.requestResource(this, ResName.IvestaEiluteSupervizorinejeAtmintyje, 1);
        
        goTo(26);
        
    }
    //23
    private void messageToSharedMemoryControl()
    {
        OS.printToConsole("JobGovernor kuria resursą [Pranešimas SharedMemoryControl]" + "nr." + pd.children.getLast().pd.internalID);
        LinkedList<Object> tempParameters = new LinkedList<>();
        commandParameter = (Integer)tempList.get(2);
        
        
        tempParameters.add(tempList.get(1)); 
        tempParameters.add(commandParameter);
        tempParameters.add(pd.children.getLast());
        tempParameters.add(pd.children.getFirst());
        
        LinkedList<Object> parameters = new LinkedList<>();
        parameters.add(tempParameters);
        
        pd.core.createResource(this, ResName.PranesimasSharedMemorycontrolProcesui, parameters);
        
        next();
        
    }
    
    //24
    private void blockForResult()
    {
        OS.printToConsole("JobGovernor blokuojasi dėl resurso [Pranešimas JobGovernor procesui]");
        pd.core.requestResource(this, ResName.PranesimasJobGovernor, 1);
        
        next();
        
    }
    
    //25
    private void isResultZero()
    {
        LinkedList<Object> tmp = (LinkedList<Object>) pd.ownedResources.getLast().value;
        String result = (String)tmp.getFirst();
        
        if (Integer.parseInt(result) == 0)
        {
            OS.printToConsole("Rezultatas = 0");
            goTo(26);
        }
        else 
        {
            OS.printToConsole("Rezultatas = 1");
            goTo(11);
        }
        
    }
    
    //26
    private void activateVirtualMachine()
    {
        OS.printToConsole("JobGovernor aktyvuoja VM");
        pd.children.getLast().pd.state = ProcessState.Blocked;
        
        next();
    }
    
    //27
    private void createResourceResumeVM()
    {
        OS.printToConsole("JobGovernor kuria resursą [Pratęsti VM darbą]");
        
        LinkedList<Object> tempParameters = new LinkedList<>();
        tempParameters.add(pd.children.getLast());
       
        
        //Siunciam tik vienai virtualiai masinai
        pd.core.createResource(this, ResName.PratestiVMDarba, tempParameters);
        
        
        goTo(7);
    }
}
