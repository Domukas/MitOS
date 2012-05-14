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
    
    public void createProcess(Process parent, OS.ProcessState state, int priority,
            LinkedList<Resource> resources, String externalID)
    {
        switch (externalID)
        {
            case "StartStop":
                int internalID = generateInternalID();
                
                Process p = new StartStop(processes, internalID, externalID,
                    new ProcessorState(), rm.proc[0], new LinkedList<Resource>(),
                    new LinkedList<Resource>(), state, priority, parent, new LinkedList<Process>());
                
                processes.add(p);    
            break;
        }
    }
    
    public void createResource(Process creator, String externalID)
    {
        switch (externalID)
        {
            case "???":
                Resource r = new Resource(); //TODO
                //kuriam nauja resursa.....
            break;   
        }
    }
    
    public void step()
    {
        System.out.println("Step!");
    }
    
    private void initProcesses()
    {
        processes = new LinkedList<Process>();
        runProcesses = new LinkedList<Process>();
        readyProcesses = new LinkedList<Process>();
        resources = new LinkedList<Resource>();
     
        processManager = new ProcessManager(this);
        
        createProcess(null, OS.ProcessState.Ready, 1, resources, "StartStop");
        //TODO
    }
    
    private int generateInternalID()
    {
        return 0;
        //TODO
    }
}
