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
    
    public enum ProcName
    {
        StartStop, Read, JCL, JobToHDD, MainProc, Loader,
        JobGovernor, SharedMemoryControl, SoundControl,
        VirtualMachine, Interrupt, PrintLine, GetLine
    }
    
    public enum ResName
    {
        MOSPabaiga, HDD, VartotojoAtmintis, IvedimoIrenginys,
        IsvedimoIrenginys, GarsiakalbioIrenginys, SupervizorineAtmintis,
        UzduotisHDD, Pertraukimas, IsvestaEilute, EiluteAtmintyje, 
        IvedimoSrautas, PranesimasGetLineProcesui, PranesimasSoundControlProcesui,
        PranesimasApiePertraukima, UzduotiesPakrovimasBaigtas,//<-- UzduotiesPakrovimasIVartotojoAtmintiBaigtas
        ParuostaUzduotis, //ParuostaUzduotisSupervizorinejeAtmintyje
        ProgramosBlokuSkaicius, //ProgramosBlokuSkaiciusSupervizorinejeAtmintyje
        UzduotisSupervizorinejeAtmintyje,
        PranesimasJobGovernor, PranesimasSharedMemorycontrolProcesui, 
        PranesimasLoaderProcesui //REIKIA SUTVARKYT DOKUMENTE, BUS PAKEITIMU DEL TO
        
    }
    
    public LinkedList<Process> processes;
    public LinkedList<Process> readyProcesses;
    public LinkedList<Process> runProcesses;
    public LinkedList<Process> blockedProcesses;
    
    public LinkedList<Resource> resources;
    
    public RealMachine rm; //nuoroda i RM
    
    
    ProcessManager processManager;   //Planuotojas
    ResourceManager resourceManager; //Resursu paskirstytojas
    
    public OS (RealMachine rm)
    {
        this.rm = rm;
        initProcesses();
    }
    
    
    //Primityvas procesu kurimui
    public void createProcess(Process parent, OS.ProcessState state, int priority,
            LinkedList<Resource> resources, ProcName externalID)
    {
        System.out.println("-------------------------");
        System.out.println("Kurti procesa iskviestas!");
        
        switch (externalID)
        {
            case StartStop:
                int internalID = generateProcessInternalID();
                
                System.out.println("Kuriamas procesas" + externalID);
                
                Process p = new StartStop(processes, internalID, externalID,
                    new ProcessorState(), rm.proc[0], new LinkedList<Resource>(),
                    new LinkedList<Resource>(), state, priority, parent,
                    new LinkedList<Process>(), this);
                
                //Pridedam procesus i reikiamus sarasus
                addProcessToLlists(p);
                
                System.out.println("Procesas sukurtas-------");
            break;
        }
        
    }
    
    private void addProcessToLlists(Process p)
    {
        //pridedam i visu procesu sarasa
        processes.add(p); 
        
        //Paziurim, i koki sarasa be bendro dar itraukt
        switch (p.pd.state)
        {
            case Ready:
                readyProcesses.add(p);
            break;
                  
            case Blocked:
                blockedProcesses.add(p);
            break;
                    
            case Run:
                //Jei kuriam Vykdoma procesa tikriausiai pazkas negerai
                System.out.println("KURIAMAS PROCESAS TURI RUN STATE!");
            break;
          }
    }
    
    
    //Primityvas resurso kurimui
    public void createResource(Process creator, ResName externalID, LinkedList<Object> parameters)
    {
        System.out.println("-------------------------");
        System.out.println("Kurti resursa iskviestas!");
        
        LinkedList<Process> waitingProcesses;
        int internalID;
        Resource r;
                
        switch (externalID)
        {
            case EiluteAtmintyje:
                
                internalID = generateResourceInternalID();
                
                //sarasas procesu, kurie laukia sito resurso...
                //turi but tuscias...
                waitingProcesses = new LinkedList<Process>();
                
                System.out.println("Kuriamas resursas " + externalID +
                        " su parametru " + (String)parameters.getFirst());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, false, 
                    parameters, waitingProcesses, resourceManager); 
                
                //pridedam tevui i sukurtu resursu sarasa sita resursa
                creator.pd.createdResources.add(r);
                
                //pridedam i visu resursu sarasa
                resources.add(r);
                
                System.out.println("Resursas sukurtas-------");
                
            break;
                
            case VartotojoAtmintis:                         //NEBAIGTA
                internalID = generateResourceInternalID();
                
                //sarasas procesu, kurie laukia sito resurso...
                //turi but tuscias...
                waitingProcesses = new LinkedList<Process>();
                
                System.out.println("Kuriamas resursas " + externalID +
                        " su parametru " + (String)parameters.getFirst());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, false, 
                    parameters, waitingProcesses, resourceManager); 
                
                //pridedam tevui i sukurtu resursu sarasa sita resursa
                creator.pd.createdResources.add(r);
                
                //pridedam i visu resursu sarasa
                resources.add(r);
                
                System.out.println("Resursas sukurtas-------");
                
            break;    
                
        }
        System.out.println("-------------------------");
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
        System.out.println("Process manager sukurtas");
        
        resourceManager = new ResourceManager();
        System.out.println("Resource manager sukurtas");
        
        System.out.println("Darbo pradzia");
        createProcess(null, OS.ProcessState.Ready, 1, resources, ProcName.StartStop);
        //TODO
    }
    
    private int generateProcessInternalID()
    {
        return 0;
        //TODO
    }
    
    private int generateResourceInternalID()
    {
        //TODDO
        return 0;
    }
}
