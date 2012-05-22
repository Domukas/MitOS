/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class ProcessManager {
 
        OS os;
    public ProcessManager(OS os)
    {
        this.os = os;
    }
    
    public void Execute() //planuotojo darbas
    {
        System.out.println("Planuojas pradeda darba");
        
        //Sutvarkom blokuotus procesus. Galima situacija, kad procesas prase resurso ir jo negavo.
        //Todel turim is jo atimt procesoriu
        handleBlockedProcesses();
        
        Processor idleProcessor = getIdleProcessor();
        Process highestReady = getHighestPriorityReadyProcess();
        
        //Kol yra laisvu procesoriu, juos dalinam procesam
        while ((idleProcessor != null) && (highestReady != null))
        {
            loadAndSet(highestReady, idleProcessor);
            
            idleProcessor = getIdleProcessor();
            highestReady = getHighestPriorityReadyProcess();
        }
        
        //Atimam procesoriu is procesu, kurie turi maziausia prioriteta
        //Ir atiduodam tiem, kurie turi didziausia prioriteta is pasiruosusiu.
        Process lowestRunning = getLeastPriorityRunningProcess();
        while ((highestReady != null) && (lowestRunning != null))
        {
            if (highestReady.pd.priority > lowestRunning.pd.priority)
            {
                stopProcess(lowestRunning);
                loadAndSet(highestReady, getIdleProcessor());

                lowestRunning = getLeastPriorityRunningProcess();
                highestReady = getHighestPriorityReadyProcess();
            }
            else break;
        }
        
    }
    
    
    private Process getLeastPriorityRunningProcess()
    {   
        Process p = null;
        
        if (os.runProcesses.size() != 0)
        {
            int priority = os.runProcesses.get(0).pd.priority;
            p = os.runProcesses.get(0);
            for (int i = 1; i<os.runProcesses.size(); i++)
            {
                if (os.runProcesses.get(i).pd.priority < priority)
                {
                    p = os.runProcesses.get(i);
                    priority = p.pd.priority; 
                }
            }    
        }
        
        return p;
    }
    
    private void handleBlockedProcesses()
    {        
        LinkedList<Process> tempList = new LinkedList<>();
        for (Process p:os.runProcesses)
            tempList.add(p);
        
        for (Process p:tempList)
        {
            if (p.pd.state == ProcessState.Blocked)
            {
                stopProcess(p);
                os.blockedProcesses.add(p);
            }   
        }
    }
    
    private void stopProcess(Process p) //ji stabdom ir pridedam prie pasiruosusiu procesu saraso, jei blokuotas tuomet pridedam prie blokuotu saraso
    {
        Process currentProcess = p;

        //pasalinam is dabar vykdomu saraso
        os.runProcesses.remove(currentProcess);

            System.out.println("Atimamas procesorius #"+ currentProcess.pd.processor.pd.number +
            " is: " + currentProcess.pd.externalID + "#" + currentProcess.pd.internalID);
            
        //Issaugom procesoriaus busena ir atimam procesoriaus resursa
        currentProcess.pd.procesorState.saveProcessorState(currentProcess.pd.processor);
        //Nustatom, kad tas procesorius nevykdo proceso
        currentProcess.pd.processor.pd.currentProcess = null;
        currentProcess.pd.processor = null; //Atimam procesoriu
        
        //Jei jis ne blokuotas, tai pridedam prie pasiruosusiu
        if (currentProcess.pd.state != ProcessState.Blocked)
        {
            os.readyProcesses.add(currentProcess); 
            currentProcess.pd.state = ProcessState.Ready;
        }
            
    }

    /*
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
    * 
    */
        
    public Process highestReadyProcess()//jei yra pasiruosusiu tai paima didziausio prioriteto, jei ne tai println
    {
        Process highestReadyProcess = null;
        if(os.readyProcesses.size() != 0) //jei yra pasiruosusiu
        {
            highestReadyProcess = getHighestPriorityReadyProcess();
        }
        else
        {
            System.out.println("Nera pasiruosusiu procesu");
        }
        return highestReadyProcess;
    }
    
    public Process getHighestPriorityReadyProcess() //pasiima pasiruosusi procesa su didziausiu prioritetu
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
        currentProcess.pd.procesorState.loadProcessorState(idleProcessor); 
        os.readyProcesses.remove(currentProcess);
        os.runProcesses.add(currentProcess);
        currentProcess.pd.processor = idleProcessor; //procesui duodamas procesorius
        currentProcess.pd.state = ProcessState.Run;
        
        //procesoriaus deskriptoriuje nurodom dabar vykdoma procesa
        idleProcessor.pd.currentProcess = currentProcess;                                                   
        
        System.out.println("Procesorius #" + idleProcessor.pd.number + 
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
