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
    public void createProcess(Process parent, ProcessState state, int priority,
            LinkedList<ResComponent> resources, ProcName externalID)
    {
        System.out.println("-------------------------");
        System.out.println("Kurti procesa iskviestas!");
        
        if (resources == null)
            resources = new LinkedList<ResComponent>();
        
        switch (externalID)
        {
            case StartStop:
                int internalID = generateProcessInternalID();
                
                System.out.println("Kuriamas procesas" + externalID);
                
                Process p = new StartStop(processes, internalID, externalID,
                    new ProcessorState(), rm.proc[0], new LinkedList<Resource>(),
                    resources, state, priority, parent,
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
    
    //primityvas naikinimui proceso
    public void destroyProcess()
    {
        
    }
    
    //primityvas stabdymui proceso
    public void stopProcess()
    {
        
    }
    
    //primityvas aktyvavimui proceso
    public void activateProcess()
    {
        //TODO
    }
    
    //Primityvas resurso kurimui
    public void createResource(Process creator, ResName externalID, LinkedList<Object> parameters)
    {
        System.out.println("-------------------------");
        System.out.println("Kurti resursa iskviestas!");
        
        
        //sarasas procesu, kurie laukia sito resurso...
        //turi but tuscias...
        LinkedList<Process> waitingProcesses = new LinkedList<Process>();
        LinkedList<Integer> waitingProcessComponentCount = new LinkedList<Integer>();
        //Generuojam isorini ID pagal tai, koki kuriam
        int internalID = generateResourceInternalID(externalID);
        Resource r = null;
                
        switch (externalID)
        {
            case EiluteAtmintyje:         
                System.out.println("Kuriamas resursas " + externalID +
                        " su parametru " + (String)parameters.getFirst());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, false, 
                    parameters, waitingProcesses, waitingProcessComponentCount, resourceManager); 
                

                
            break;
                
            case VartotojoAtmintis:
            case SupervizorineAtmintis:  
            case HDD:    
                
                System.out.println("Kuriamas resursas " + externalID +
                        " Bloku skaicius: " + parameters.size());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, true, //Pakartotino naudojimo
                    parameters, waitingProcesses, waitingProcessComponentCount, resourceManager);     
            break;    
                
            case IvedimoIrenginys:
            case IsvedimoIrenginys:
            case GarsiakalbioIrenginys:
                
            System.out.println("Kuriamas resursas " + externalID);

            //Paduodam:
            //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
            r = new Resource(creator, externalID, internalID, true, //Pakartotino naudojimo
                parameters, waitingProcesses, waitingProcessComponentCount, resourceManager);                 
                
            break;    
                
                
        }
        
        //pridedam tevui i sukurtu resursu sarasa sita resursa
        creator.pd.createdResources.add(r);
                
        //pridedam i visu resursu sarasa
        resources.add(r);
                
        System.out.println("Resursas sukurtas-------");
        System.out.println("-------------------------");
    }
    
    //TODO primityvas resurso sunaikinimui
    public void destroyResource(Resource r)
    {
        //istrinam resursa is ji sukurusio proceso sukurtu resursu saraso
        Process creatorProcess = r.rd.creator;
        creatorProcess.pd.createdResources.remove(r);
        //istrinam jo elementu sarasa
        for (int i = 0; i < r.rd.components.size(); i++)
        {
            r.rd.components.remove(i);
        }
        //atblokuojami visi procesai laukiantys sio resurso
        for(int i = 0; i < processes.size(); i++)
        {
            Process currentProcess = processes.get(i);
            for(int j = 0; j < currentProcess.pd.ownedResources.size(); j++)
            {
                if((currentProcess.pd.ownedResources.get(j).equals(r)) && (currentProcess.pd.state == ProcessState.Blocked))
                {
                    currentProcess.pd.state = ProcessState.Ready;
                }
            }
        }
        //ismetamas is bendro resursu saraso
        resources.remove(r);
    }
    
    //TODO primityvas resurso prasymui
    public void requestResource(Process currentProcess, Resource r, int count)
    {
        //Procesas, iškvietęs šį primityvą, yra užblokuojamas
        currentProcess.pd.state = ProcessState.Blocked;
        //įtraukiamas į to resurso laukiančių procesų sąrašą.
        r.rd.waitingProcesses.add(currentProcess);
        //Itraukiama ir kiek reikia jam butent resurso elementu
        r.rd.waitingProcessComponentCount.add(count);
        //kvieciamas resurso paskirstytojas.
        resourceManager.execute();
    }
    
    //TODO primityvas resurso atlaisvinimui
    public void freeResource(Process process, Resource r)
    {
        //Atlaisvinti resursą.
        //Šį primityvą kviečia procesas, kuris nori atlaisvinti jam nereikalingą
        //resursą arba tiesiog perduoti pranešimą ar informaciją kitam procesui.
        //Resurso elementas, primityvui perduotas kaip funkcijos parametras,
        //yra pridedamas prie resurso elementų sąrašo. Šio primityvo pabaigoje
        //yra kviečiamas resursų paskirstytojas.
        //Resurso elementas pridedamas prie resurso elementų sąrašo.  

        boolean isInFreeList = false;
        Resource tmpRes = null;
        
        //randam resursa resursu sarase
        for(int i = 0; i < resources.size(); i++)
        {
            //gal galima patikrint pagal kalses??? ///TODO
            if((resources.get(i).rd.externalID == r.rd.externalID) && (resources.get(i).rd.internalID == r.rd.internalID))
            {
                //jei randam laisvu sarase ta resursa, tai reiskia, kad 
                //jam turim grazint tuos komponentus. Todel isimenam ji.
                isInFreeList = true;
                tmpRes = resources.get(i);
                break;
            }
        }
        
        if (isInFreeList)
        {   
            //reiks prideti jo komponentus

            for (ResComponent c:process.pd.ownedResources)
            {
                //neaisku ar suveiks.... //TODO
                //atiduodam komponentus...
                if (c.parent == tmpRes)
                {
                    tmpRes.rd.components.add(c);
                    process.pd.ownedResources.remove(c);
                }
            }
        }
        else
        {
            //Jei ne laisvu sarase, tada i ta sarasa resursa idedam
            //Ir jam vel grazinam komponentus
            tmpRes = r;
            resources.add(r);
            
            //Grazinam jam komponentus
            for (ResComponent c:process.pd.ownedResources)
            {
                tmpRes.rd.components.add(c);
                process.pd.ownedResources.remove(c);
            }
        }
        
        resourceManager.execute();
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
        
        resourceManager = new ResourceManager(this);
        System.out.println("Resource manager sukurtas");
        
        System.out.println("Darbo pradzia");
        createProcess(null, OS.ProcessState.Ready, 90, null, ProcName.StartStop);
        //TODO
    }
    
    private int generateProcessInternalID()
    {
        return 0;
        //TODO
    }
    
    private int generateResourceInternalID(ResName resName)
    {
        //TODDO
        return 0;
    }
}
