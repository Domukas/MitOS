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
        System.out.println("JobGovernor blokuojasi dėl resurso [Vartotojo atmintis]. Puslapių lentelei saugoti");
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
        
        System.out.println("JobGovernor blokuojasi dėl resurso [Vartotojo atmintis]. Prašo " + count + " blokų");
        pd.core.requestResource(this, ResName.VartotojoAtmintis, count);
        next();
    }
    
    //3
    private void createMessageToLoader()
    {
        System.out.println("JobGovernor kuria pranešimą Loader procesui");
        
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
        System.out.println("JobGovernor blokuojasi dėl resurso [Užduoties pakrovimas į vartotojo atmintį baigtas]");
        pd.core.requestResource(this, ResName.UzduotiesPakrovimasBaigtas, 1);
        
        next();
    }
    
    //5
    private void createSharedMemoryControl()
    {
        System.out.println("JobGovernor kuria SharedMemoryControl");
        pd.core.createProcess(this, ProcessState.Ready, 60, null, ProcName.SharedMemoryControl);
        
        next();
    }
    
    //6
    private void createVM()
    {
        System.out.println("JobGovernor kuria Virtualią mašiną");
        
        LinkedList<ResComponent> toGive = new LinkedList<>();
        
        //for (ResComponent rc:pd.ownedResources)
        //    System.out.println(rc.value.toString());
        
        String temp = (String)pd.ownedResources.getLast().value;
        if (temp.length() == 1)
            temp = "0".concat(temp);
        if (programInHDD.getFirst().length() == 1) //Butinai turi but 2 simboliai
            temp += "0";
        temp += programInHDD.getFirst(); //Reikia perduot 2 skaicius...
        pd.ownedResources.getLast().value = temp;
        toGive.add(pd.ownedResources.getLast());
        
        //System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\" + (String)toGive.getFirst().value);
        
        pd.core.createProcess(this, ProcessState.Ready, 50, toGive, ProcName.VirtualMachine);
        
        next();
    }
    
    //7
    private void blockForInterrupt()
    {
        System.out.println("JobGovernor blokuojasi dėl resurso [Pertraukimas]");
        pd.core.requestResource(this, ResName.Pertraukimas, 1);
        
        next();
    }
    
    //8
    private void stopVirtualMachine()
    {
        System.out.println("JobGovernor stabdo VM");
        pd.children.getLast().pd.state = ProcessState.ReadyS;
        
        next();
    }
    
    //9
    private void isIOorSoundInt()
    {
        for (ResComponent rc: pd.ownedResources)
            System.out.println(rc.value);
        
        tempList = (LinkedList<Object>)pd.ownedResources.getLast().value;
        
        
        if (((String)tempList.getFirst() == "DG") || ((String)tempList.getFirst() == "DP") ||
                ((String)tempList.getFirst() == "Garsiakalbis"))
        {
            System.out.println("IO arba garso pertraukimas");
            goTo(16);
        }
        else
        {
            System.out.println("Ne IO arba garso pertraukimas");
            goTo(10);
        }
        
    }
    //10
    private void isSharedMemoryInterrupt()
    {
        if ((String)tempList.getFirst() == "Bendra atmintis")
        {
            System.out.println("Darbo su bendra atmintimi pertraukimas");
            goTo(23);
        }
        else
        {
            System.out.println("Ne darbo su bendra atmintimi pertraukimas. Naikinama VM");
            goTo(11);
        }         
        
    }
    
    //11
    private void destroyVM()
    {
        System.out.println("JobGovernor naikina VM");
        pd.core.destroyProcess(pd.children.getLast());
        next();
        
    }
    
    //12
    private void freeMemory()
    {
        System.out.println("JobGovernor atlaisvina atmintį");
        pd.core.freeResource(this, pd.ownedResources.get(2).parent);
        
        next();
    }
    
    //13
    private void destroySharedMemoryControl()
    {
        System.out.println("JobGovernor naikina procesą SharedMemoryControl");
        
        next();
    }
    
    //14
    private void createJobInHDD()
    {
        System.out.println("JobGovernor kuria fiktyvų užduoties resursą");
        LinkedList<String> tmp = new LinkedList<>();
        LinkedList<Object> parameter = new LinkedList<>();
        
        parameter.add(tmp);
        
        pd.core.createResource(this, ResName.UzduotisHDD, parameter);
        
        next();
    }
    
    //15
    private void blockForUnexistingResource()
    {
        System.out.println();
        pd.core.requestResource(this, ResName.Neegzistuojantis, 1);
        
    }
    
    //16
    private void isIOInterrupt()
    {
        if (((String)tempList.getFirst() == "DG") || ((String)tempList.getFirst() == "DP"))
        {
            System.out.println("IO pertraukimas");
            goTo(18);
        }
        else
        {
            System.out.println("Garso pertraukimas");
            goTo(17);
        }   
    }
    
    //17
    private void createMessageForSoundControl()
    {
        
    }
    
    //18
    private void isDPInterrupt()
    {
        if ((String)tempList.getFirst() == "DP")
        {
            System.out.println("DP komanda");
            goTo(19);
        }
        else
        {
            System.out.println("DG komanda");
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
        
        goTo(26); //Reikia eit i aktyvavima VM'o
    }
    
    //20
    private void blockForLinePrinted()
    {
        
    }
    
    //21
    private void createMessageForGetLine()
    {
        System.out.print("JobGovernor kuria resursą [Pranešimas GetLine procesui]");
        commandParameter = (Integer)tempList.get(1);
        
        pd.core.createResource(this, ResName.PranesimasGetLineProcesui,
                createMessage(Integer.toString(commandParameter)));
        
        next();
    }
    
    //22
    private void blockForEnteredLineInSupervisor()
    {
        System.out.print("JobGovernor blokuojasi dėl resurso [Įvesta eilutė atmintyje]");
        pd.core.requestResource(this, ResName.IvestaEiluteSupervizorinejeAtmintyje, 1);
        
        goTo(26);
        
    }
    //23
    private void messageToSharedMemoryControl()
    {
        System.out.print("JobGovernor kuria resursą [Pranešimas SharedMemoryControl]");
        LinkedList<Object> tempParameters = new LinkedList<>();
        commandParameter = (Integer)tempList.get(2);
        
        
        tempParameters.add(tempList.get(1)); 
        tempParameters.add(commandParameter);
        tempParameters.add(pd.children.getLast());
        
        LinkedList<Object> parameters = new LinkedList<>();
        parameters.add(tempParameters);
        
        pd.core.createResource(this, ResName.PranesimasSharedMemorycontrolProcesui, parameters);
        
        next();
        
    }
    
    //24
    private void blockForResult()
    {
        System.out.print("JobGovernor blokuojasi dėl resurso [Pranešimas JobGovernor procesui]");
        pd.core.requestResource(this, ResName.PranesimasJobGovernor, 1);
        
        next();
        
    }
    
    //25
    private void isResultZero()
    {
        String result = (String)pd.ownedResources.getLast().value;
        
        if (Integer.parseInt(result) == 0)
        {
            System.out.print("Rezultatas = 0");
            goTo(26);
        }
        else 
        {
            System.out.print("Rezultatas = 1");
            goTo(11);
        }
        
    }
    
    //26
    private void activateVirtualMachine()
    {
        System.out.println("JobGovernor aktyvuoja VM");
        pd.children.getLast().pd.state = ProcessState.Ready;
        
        next();
        
        
    }
    
    //27
    private void createResourceResumeVM()
    {
        System.out.println("JobGovernor kuria resursą [Pratęsti VM darbą]");
        pd.core.createResource(this, ResName.PratestiVMDarba, createMessage("PratestiDarba"));
        
        goTo(7);
    }
}
