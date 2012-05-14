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
    
    public LinkedList<Process> processes;
    public LinkedList<Process> readyProcesses;
    public LinkedList<Process> runProcesses;
    public LinkedList<Process> blockedProcesses;
    
    public LinkedList<Resource> resources;
    
    public RealMachine rm;
    ProcessManager processManager;
    
    public OS (RealMachine rm)
    {
        this.rm = rm;
        initProcesses();
    }
    
    private void initProcesses()
    {
        processManager = new ProcessManager(this);
        //TODO
    }
}
