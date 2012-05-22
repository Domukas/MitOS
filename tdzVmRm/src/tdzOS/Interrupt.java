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
public class Interrupt extends Process{         //TODO
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
                setInterrupt();
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
    {}
    
    private void setInterrupt()
    {}
    
    private void identifyJobGovernor()
    {}
    
    private void createResourcePertraukimas()
    {}
}
