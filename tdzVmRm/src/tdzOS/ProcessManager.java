/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.Iterator;
import tdzOS.OS.ProcessState;

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
        CurrentProcessState();
        Process currentProcess = HighestReadyProcess();
        LoadAndSet(currentProcess);
    }
    
    public void CurrentProcessState() //jei procesas nera blokuotas tuomet ji stabdom ir pridedam prie pasiruosusiu procesu saraso, jei blokuotas tuomet pridedam prie blokuotu saraso
    {
        Process currentProcess = getHighestRunningProcess();
        int index = os.runProcesses.indexOf(currentProcess);
        os.runProcesses.remove(index);
        if(currentProcess.pd.state != ProcessState.Blocked)
        {
            os.readyProcesses.add(currentProcess);
        }
        else
        {
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
        
    public Process HighestReadyProcess()//jei yra pasiruosusiu tai paima didziausio prioriteto, jei ne tai println
    {
        Process highestReadyProcess = null;
        if(AreReadyProccesses() == true)
        {
            highestReadyProcess = getHighestReadyProcess();
        }
        else
        {
            System.out.println("There is no ready processes");
        }
        return highestReadyProcess;
    }
    
    public boolean AreReadyProccesses() //ar yra pasiruosusiu procesu sarase
    {
        boolean areReadyProccesses = false;
        for (int i = 0; i < os.readyProcesses.size(); i++)
        {
            areReadyProccesses = true;
        }
        return areReadyProccesses;
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
    
    public void LoadAndSet(Process currentProcess)//pakrauna proceso busena RM procesoriui, pasalina ir pasiruosusiu ir ideda i veikianciu procesu sarasa
    {
        currentProcess.pd.procesorState.LoadProcessorState(os.rm.proc[0]); //cia reiktu procesoriu keisti
        int index = os.readyProcesses.indexOf(currentProcess);
        os.readyProcesses.remove(index);
        os.runProcesses.add(currentProcess);
        currentProcess.pd.processor = os.rm.proc[0];
    }
}
