/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzVmRm.RealMachine;

/**
 *
 * @author Tomas
 */
public class OS {
    
    public enum ProcessState 
    {
        Run, Ready, ReadyS, Blocked, BlockedS
    }
    
    LinkedList<Process> processes;
    LinkedList<Process> readyProcesses;
    LinkedList<Process> runProcesses;
    LinkedList<Resource> resources;
    
    RealMachine rm;
    
    public OS (RealMachine rm)
    {
        this.rm = rm;
        initProcesses();
    }
    
    private void initProcesses()
    {
        //TODO
    }
}
