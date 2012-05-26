/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;

/**
 *
 * @author Zory
 */
public class Interrupt extends Process
{   
    
    int PI, SI, commandParameter;
    String OPC;
    LinkedList<Object> parameters;

    public Interrupt (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForPranesimasApiePertraukima();
                break;
            case 2:
                indentifyInterrupt();
                break;
            case 3:
                identifyJobGovernor();
                break;
            case 4:
                createResourcePertraukimas();
                break;
       }
    }
       
    private void blockForPranesimasApiePertraukima()
    {
        System.out.println("Interrupt blokuojasi dėl resurso [Pranešimas apie pertraukimą]");
        pd.core.requestResource(this, OS.ResName.PranesimasApiePertraukima, 4); //Sudarytas is 4 komponentu
        
        next();
    }
    
    private void indentifyInterrupt()
    {   
        System.out.println("Interrupt nustato pertraukino tipą");
        
        PI = (Integer)pd.ownedResources.get(0).value;
        SI = (Integer)pd.ownedResources.get(1).value;
        commandParameter = (Integer)pd.ownedResources.get(3).value;
        OPC = (String)pd.ownedResources.get(2).value;
        
        parameters = new LinkedList<>();
        switch (PI)
        {
            case 1:
                parameters.add("Atminties apsauga");
                break;
            case 2:
                parameters.add("Opkodas neegzistuoja");
                break;
        }
        
        switch (SI)
        {
            case 1:
                parameters.add("DG");
                parameters.add(commandParameter);
                break;
                
            case 2:
                parameters.add("DP");
                parameters.add(commandParameter);
                break;
                
            case 3:
                parameters.add("Garsiakalbis");
                parameters.add(OPC);
                break;
                
            case 4:
                parameters.add("Bendra atmintis");
                parameters.add(OPC);
                parameters.add(commandParameter);
                break;
                
            case 5:
                parameters.add("Halt");
                break;
        }
        
        next();
    }
    
    private void identifyJobGovernor()
    {
        System.out.println("Interrupt indentifikuoja JobGovernor");
        //interrupto kurejo tevas yra job governor
        Process jg = pd.ownedResources.getFirst().parent.rd.creator.pd.parent;
        parameters.add(jg);
        next();
    }
    
    private void createResourcePertraukimas()
    {
        
        System.out.println("Interrupt kuria resursą [Pertraukimas]");
        LinkedList<Object> tempList = new LinkedList<>(); //Viska supakauojam i viena sarasa, nes governor'ius nezinos kiek prasyt
        tempList.add(parameters);
        
        pd.core.createResource(this, ResName.Pertraukimas, tempList);
        
        pd.ownedResources.clear();
        goTo(1);
        
    }
}
