/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.Iterator;
import java.util.LinkedList;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class ProcessManager { //planuotojas
    
    OS os;
    public ProcessManager(OS os)
    {
        this.os = os;
    }
    
    public void Execute() //planuotojo darbas
    {
        System.out.println("Planuojas pradeda darba");   
        stopCurrentProcesses();
        
        Processor idleProcessor = getIdleProcessor();
        Process processToRun = highestReadyProcess();
        
        while ((idleProcessor != null) && (processToRun != null))
        {
            loadAndSet(processToRun, idleProcessor);
            idleProcessor = getIdleProcessor();
            processToRun = highestReadyProcess();
        }

        
        //Kitu atveju riekia kazka daryt, jei jau nera norinciu dirbt procesu
    }
    
    public void stopCurrentProcesses() //jei procesas nera blokuotas tuomet ji stabdom ir pridedam prie pasiruosusiu procesu saraso, jei blokuotas tuomet pridedam prie blokuotu saraso
    {
        //Jei nera veikianciu procesu
        if (os.runProcesses.size() == 0)
        {
            System.out.println("Nera vykdomu procesu!");  
            return;
        }
            
        //Jei yra vykdomu, tada einam per ju sarasa
        
        System.out.println(os.runProcesses.size());
        
        //Laikinas sarasas, kad visko neisgadintumem
        LinkedList<Process> runningProcesses = new LinkedList<>();
        for (Process p:os.runProcesses)
            runningProcesses.add(p);
        
        for (Process p:runningProcesses)
        {
            Process currentProcess = p;
             
            //pasalinam is dabar vykdomu saraso
            os.runProcesses.remove(currentProcess);

            //Issaugom procesoriaus busena ir atimam procesoriaus resursa
            currentProcess.pd.procesorState.saveProcessorState(currentProcess.pd.processor);
            //Nustatom, kad tas procesorius nevykdo proceso
            currentProcess.pd.processor.pd.currentProcess = null;
            currentProcess.pd.processor = null; //Atimam procesoriu

            System.out.println("Atimamas procesorius is: " + currentProcess.pd.externalID + 
                    "#" + currentProcess.pd.internalID);   

            if(currentProcess.pd.state != ProcessState.Blocked)
                os.readyProcesses.add(currentProcess);
            else
                os.blockedProcesses.add(currentProcess);
        }
    }

    public Process getHighestRunningProcess() //randa ir grazina didziausio prioriteto dabar vykdoma procesa
    {
        int priority = 0;
        Process highestRunnningProcess = null;
	for (int i = 0; i < os.runProcesses.size(); i++)
        {
            if (os.runProcesses.get(i).pd.priority > priority)
                {
                    priority = os.runProcesses.get(i).pd.priority;
                    highestRunnningProcess = os.runProcesses.get(i);
                }
	}
        return highestRunnningProcess;
    }
        
    public Process highestReadyProcess()//jei yra pasiruosusiu tai paima didziausio prioriteto, jei ne tai println
    {
        Process highestReadyProcess = null;
        if(os.readyProcesses.size() != 0) //jei yra pasiruosusiu
        {
            highestReadyProcess = getHighestReadyProcess();
        }
        else
        {
            System.out.println("Nera pasiruosusiu procesu");
        }
        return highestReadyProcess;
    }
    
    public Process getHighestReadyProcess() //pasiima pasiruosusi procesa su didziausiu prioritetu
    {
        int priority = 0;
        Process highestReadyProcess = null;
	for (int i = 0; i < os.readyProcesses.size(); i++)
        {
            if (os.readyProcesses.get(i).pd.priority > priority)
                {
                    priority = os.readyProcesses.get(i).pd.priority;
                    highestReadyProcess = os.readyProcesses.get(i);
                }
	}
        return highestReadyProcess;
    }
    
    public void loadAndSet(Process currentProcess, Processor idleProcessor)//pakrauna proceso busena RM procesoriui, pasalina ir pasiruosusiu ir ideda i veikianciu procesu sarasa
    {
        currentProcess.pd.procesorState.loadProcessorState(idleProcessor); //cia reiktu procesoriu keisti
        os.readyProcesses.remove(currentProcess);
        os.runProcesses.add(currentProcess);
        currentProcess.pd.processor = idleProcessor; //procesui duodamas procesorius
        
        //procesoriaus deskriptoriuje nurodom dabar vykdoma procesa
        idleProcessor.pd.currentProcess = currentProcess;                                                   
        
        System.out.println("Procesorius code:" + idleProcessor.hashCode() + 
                " perduotas procesui " + currentProcess.pd.externalID +  "#" + currentProcess.pd.internalID);
    }
    
    private Processor getIdleProcessor()
    {
        for (Processor p:os.rm.proc)
        {
            if (p.pd.currentProcess == null)
                return p;
        }
        
        return null; 
    }
}
