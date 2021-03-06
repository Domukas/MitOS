/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;
import tdzOS.OS;

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
        OS.printToConsole("Interrupt blokuojasi dėl resurso [Pranešimas apie pertraukimą]");
        pd.core.requestResource(this, OS.ResName.PranesimasApiePertraukima, 1);
        
        next();
    }
    
    private void indentifyInterrupt()
    {   
        OS.printToConsole("Interrupt nustato pertraukino tipą");
        
        LinkedList<Object> tempList = (LinkedList<Object>)pd.ownedResources.getLast().value;
        
        PI = (Integer)tempList.get(0);
        SI = (Integer)tempList.get(1);
        commandParameter = (Integer)tempList.get(3);
        OPC = (String)tempList.get(2);
        
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
        OS.printToConsole("Interrupt indentifikuoja JobGovernor");
        //interrupto kurejo tevas yra job governor
        Process jg = pd.ownedResources.getFirst().parent.rd.creator.pd.parent;
        parameters.add(jg);
        next();
    }
    
    private void createResourcePertraukimas()
    {
        
        OS.printToConsole("Interrupt kuria resursą [Pertraukimas]");
        LinkedList<Object> tempList = new LinkedList<>(); //Viska supakauojam i viena sarasa, nes governor'ius nezinos kiek prasyt
        tempList.add(parameters);
        
        pd.core.createResource(this, ResName.Pertraukimas, tempList);
        
        pd.ownedResources.clear();
        goTo(1);
        
    }
}
