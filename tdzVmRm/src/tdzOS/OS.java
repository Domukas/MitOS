/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;

/**
 *
 * @author Tomas
 */
public class OS implements Runnable {

    public static boolean showLog = false;
    
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
        PranesimasLoaderProcesui, //REIKIA SUTVARKYT DOKUMENTE, BUS PAKEITIMU DEL TO
        IvestaEiluteSupervizorinejeAtmintyje, //prikuriau nzn ar reikia
        PratestiVMDarba, BlokasAtrakintas, VartotojoIvestaEilute, Neegzistuojantis
    }

    public LinkedList<Process> processes;
    public LinkedList<Process> readyProcesses;
    public LinkedList<Process> runProcesses;
    public LinkedList<Process> blockedProcesses;

    public LinkedList<Resource> resources;
    
    public RealMachine rm; //nuoroda i RM
    
    
    ProcessManager processManager;   //Planuotojas
    ResourceManager resourceManager; //Resursu paskirstytojas
    
    
    int VMCount = 0;
    public boolean makeStep = false;
    public boolean makeStepToVM = false;
    public boolean stopOS = false;
    public boolean makeRun = false;
    
    public OS (RealMachine rm)
    {
        this.rm = rm;
        initProcesses();
        //startOS();
    }
    
    @Override
    public void run() {
        while (!stopOS)
        {
            if (makeStep){
                step();
                rm.gui.updateTables = true;
            } 
            else if (makeStepToVM){
                stepToVM();
                rm.gui.updateTables = true;
            }
            else if (makeRun)
            {
                while (runProcesses.size() != 0 && makeRun)
                    {
                        step();
                        rm.gui.updateTables = true;
                    }
            }
            makeStep = makeStepToVM = false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stopOS()
    {
        LinkedList<Object> temp = new LinkedList<>();
        temp.add("MOS pabaiga");
        createResource(getMainproc(), ResName.MOSPabaiga, temp);
    }
    
    
    //Primityvas procesu kurimui
    public void createProcess(Process parent, ProcessState state, int priority,
            LinkedList<ResComponent> components, ProcName externalID)
    {
        OS.printToConsole("-------------------------");
        OS.printToConsole("Kurti procesa iškviestas!");
        
        if (components == null)
            components = new LinkedList<>();
         
        //Generuojamas vidinis ID
        int internalID = generateProcessInternalID(externalID);
        
        OS.printToConsole("Kuriamas procesas" + externalID);
        
        //Nuoroda i procesoriu null, nes tik sukurtas procesas neturi procesoriaus,
        //ji gauna, kai jam procesoriu duoda planuotojas

        Process p = null;
        switch (externalID)
        {
            case StartStop:
                p = new StartStop(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case Read:
                p = new Read(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case JCL:
                p = new JCL(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case JobToHDD:
                p = new JobToHDD(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case PrintLine:
                p = new PrintLine(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case GetLine:
                p = new GetLine(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case MainProc:
                p = new MainProc(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break; 
            case SoundControl:
                p = new SoundControl(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case JobGovernor:
                p = new JobGovernor(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case Loader:
                p = new Loader(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;
            case VirtualMachine:
                p = new VirtualMachine(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;  
            case Interrupt:
                p = new Interrupt(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;       
            case SharedMemoryControl:
                p = new SharedMemoryControl(processes, internalID, externalID,
                    new ProcessorState(), null, components, state, priority,
                    parent, this);
                break;       
                
        }
        
        if (externalID != ProcName.StartStop)
            parent.pd.children.add(p);
        
        //Pridedam procesus i reikiamus sarasus
        addProcessToLlists(p);
        
        OS.printToConsole("Procesas sukurtas-------");   
        processManager.Execute();
        
        
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
                OS.printToConsole("KURIAMAS PROCESAS TURI RUN STATE!");
            break;
          }
    }
    
    private void removeProcessesFromLists(Process p)
    {   
        //isimam is visu procesu saraso
        processes.remove(p); 
        
        //Paziurim, i koki sarasa be bendro dar itrauktas procesas
        switch (p.pd.state)
        {
            case Ready:
                readyProcesses.remove(p);
            break;
                  
            case Blocked:
                blockedProcesses.remove(p);
            break;
                    
            case Run:
                //Jei naikinam Vykdoma procesa tikriausiai pazkas negerai
                OS.printToConsole("NAIKINAMAS PROCESAS TURI RUN STATE!");
            break;
          }
        
    }
    
    //primityvas naikinimui proceso
    //TODO reikia pataisymu, gal negerai veiks
    public void destroyProcess(Process process)
    {
        OS.printToConsole("Naikinamas procesas: " + process.pd.externalID + " #" + process.pd.internalID);
        for (Process p:process.pd.children) //Rekursiskai naikiname visus proceso vaikus
            destroyProcess(p);
        
        //naikinam procesu sukurtus resursus
        
        //Sudaromas sarasas resursu, kuriuos reikia sunaikinti
        //Reikalingas tam, kad nenaikintumem resurso is saraso, per kuri dabar einam
        LinkedList<Resource> resourcesToDestroy = new LinkedList<>();
        for (Resource r:process.pd.createdResources)
            resourcesToDestroy.add(r);
        
        //Naikinami sukurti resursai
        for (Resource r:resourcesToDestroy)
            destroyResource(r);
        
        //isimam is tevo sukurtu procesu saraso sita procesa
        //jei naikinam StartStop, tada jo tevas yra null reiksme
        if (process.pd.parent != null)
            process.pd.parent.pd.children.remove(process);
        
        //ismetam procesa is visu procesu saraso
        removeProcessesFromLists(process);
        
        LinkedList<ResComponent> tempList = new LinkedList<>();
        for (ResComponent c:process.pd.ownedResources)
            tempList.add(c);
        
        for (ResComponent c:tempList)
        {
            //jei resursas pakartotino naudojimo
            if (c.parent.rd.reusable)
            {
                //tai ji atlaisvinam (jei ten atmintis ar irenginio resursas)
                freeResource(process, c.parent);
            }
            else
            {
                //jei ne pakartotino naudojimo, tarkim, kad pranesimas
                //sunaikinam pati resursa
                
                //pirma karta paleidus destroy resource, jis ismes visus komponentus is resurso
                //bet tie komponentai dar bus pas procesa
                //tada tiesiog tuos komonentus ismetam
                if (c.parent.rd.components.size() != 0)       
                    destroyResource(c.parent);
                //einant toliau per cikla
                process.pd.ownedResources.remove(c);
            }
        }
        
        OS.printToConsole("Procesas sunaikintas");
        
    }
    
    //primityvas stabdymui proceso
    public void stopProcess(Process p)
    {
        switch (p.pd.state)
        {
            case Run:
                //pasalinam is dabar vykdomu saraso
                runProcesses.remove(p);

                //Issaugom procesoriaus busena ir atimam procesoriaus resursa
                p.pd.procesorState.saveProcessorState(p.pd.processor);
                //Nustatom, kad tas procesorius nevykdo proceso
                p.pd.processor.pd.currentProcess = null;
                p.pd.processor = null; //Atimam procesoriu

                p.pd.state = ProcessState.Ready;
                readyProcesses.add(p);
                OS.printToConsole("Procesas " + p.pd.externalID + 
                        "#" + p.pd.internalID + "perjungtas i Ready būsena");   
                
                processManager.Execute();
                break;
                
            case Ready:
                readyProcesses.remove(p);
                p.pd.state = ProcessState.ReadyS;
                
                OS.printToConsole("Procesas " + p.pd.externalID + 
                    "#" + p.pd.internalID + "perjungtas i ReadyS būsena");   
                
                break;
            case Blocked:
                blockedProcesses.remove(p);
                p.pd.state = ProcessState.BlockedS;
                OS.printToConsole("Procesas " + p.pd.externalID + 
                    "#" + p.pd.internalID + "perjungtas i BlockedS būsena");   
                break;
        }
    }
    
    //primityvas aktyvavimui proceso
    public void activateProcess(Process p)
    {
        //Jei procesas readyS busenos, pakeiciam ja i ready ir pridedam i pasiruosusiu sarasa
        if (p.pd.state == ProcessState.ReadyS)
        {
            p.pd.state = ProcessState.Ready;
            readyProcesses.add(p);
        } //Jei ne ready tada jis BlockedS ir ji idedam i blocked sarasa. Pakeiciam i blocked
        else if (p.pd.state == ProcessState.BlockedS)
        {
            p.pd.state = ProcessState.Blocked;
            blockedProcesses.add(p);
        }
        else OS.printToConsole("AKTYVUOJAMAS NE SUSPENDED PROCESAS!");
    }
    
    //Primityvas resurso kurimui
    public void createResource(Process creator, ResName externalID, LinkedList<Object> parameters)
    {
        OS.printToConsole("-------------------------");
        OS.printToConsole("Kurti resursa iskviestas!");
       
        //Generuojam isorini ID pagal tai, koki kuriam
        int internalID = generateResourceInternalID(externalID);
        Resource r = null;
                
        switch (externalID)
        {
            case EiluteAtmintyje:
            case ProgramosBlokuSkaicius:
            case UzduotiesPakrovimasBaigtas:
                OS.printToConsole("Kuriamas resursas " + externalID +
                        " su parametru " + (String)parameters.getFirst());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, false, 
                    parameters, resourceManager); 
                

                
                break;
                
            case VartotojoAtmintis:
            case SupervizorineAtmintis:  
            case HDD:    
                
                OS.printToConsole("Kuriamas resursas " + externalID +
                        " Bloku skaicius: " + parameters.size());
                
                //Paduodam:
                //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, true, //Pakartotino naudojimo
                    parameters, resourceManager);     
                break;    
                
            case IvedimoIrenginys:
            case IsvedimoIrenginys:
            case GarsiakalbioIrenginys:
                
                OS.printToConsole("Kuriamas resursas " + externalID);

            //Paduodam:
            //creator, externalID, internalID, reusable, components, waitingProcesses, resourceManager
                r = new Resource(creator, externalID, internalID, true, //Pakartotino naudojimo
                    parameters, resourceManager);                 
                
                break;
                
            case IvedimoSrautas:
                OS.printToConsole("Kuriamas resursas " + externalID);
                
                r = new Resource(creator, externalID, internalID, false, //ne pakartotinio naudojimo
                parameters, resourceManager);    
                
                break;
                
            case UzduotisSupervizorinejeAtmintyje:
            case ParuostaUzduotis:
            case UzduotisHDD:
            case IsvestaEilute:
            case IvestaEiluteSupervizorinejeAtmintyje:
            case PranesimasLoaderProcesui:
            case PranesimasApiePertraukima:
            case Pertraukimas:
            case PranesimasGetLineProcesui:
            case PratestiVMDarba:
            case PranesimasSharedMemorycontrolProcesui:
            case PranesimasJobGovernor:
            case PranesimasSoundControlProcesui:
            case BlokasAtrakintas:
            case VartotojoIvestaEilute:
            case MOSPabaiga:
                OS.printToConsole("Kuriamas resursas " + externalID);
                
                r = new Resource(creator, externalID, internalID, false, //ne pakartotinio naudojimo
                parameters, resourceManager);                   
                
                
                break;
        }
        
        //pridedam tevui i sukurtu resursu sarasa sita resursa
        creator.pd.createdResources.add(r);
                
        //pridedam i visu resursu sarasa
        resources.add(r);
                
        OS.printToConsole("Resursas sukurtas-------");
        OS.printToConsole("-------------------------");
        
        resourceManager.execute();
    }
    
    //TODO primityvas resurso sunaikinimui
    public void destroyResource(Resource r)
    {
        OS.printToConsole("Naikinamas resursas " + r.rd.externalID + " #" + r.rd.internalID);
        
        //istrinam resursa is ji sukurusio proceso sukurtu resursu saraso
        Process creatorProcess = r.rd.creator;
        creatorProcess.pd.createdResources.remove(r);
        //istrinam jo elementu sarasa
        for (int i = 0; i < r.rd.components.size(); i++)
        {
            r.rd.components.remove(i);
        }
        resources.remove(r);
        OS.printToConsole("Resursas sunaikintas");
    }
    
    //primityvas resurso prasymui
    public void requestResource(Process currentProcess, ResName r, int count)
    {
        //Procesas, iškvietęs šį primityvą, yra užblokuojamas
        currentProcess.pd.state = ProcessState.Blocked;
        OS.printToConsole(currentProcess.pd.externalID + "-->Blocked");
        
        //proceso deskriptoriuje nurodoma, kiek ir kokio resurso jis laukia
        currentProcess.pd.waitingFor.add(r);
        currentProcess.pd.waitingCount.add(count);
        
        //kvieciamas resurso paskirstytojas.
        resourceManager.execute();
    }
    
    //primityvas resurso atlaisvinimui
    public void freeResource(Process process, Resource r)
    {
        //Atlaisvinti resursą.
        //Šį primityvą kviečia procesas, kuris nori atlaisvinti jam nereikalingą
        //resursą arba tiesiog perduoti pranešimą ar informaciją kitam procesui.
        //Resurso elementas, primityvui perduotas kaip funkcijos parametras,
        //yra pridedamas prie resurso elementų sąrašo. Šio primityvo pabaigoje
        //yra kviečiamas resursų paskirstytojas.
        //Resurso elementas pridedamas prie resurso elementų sąrašo.  

        OS.printToConsole("Procesas " + process.pd.externalID + "#" + process.pd.internalID + 
                "atlaisvina resursa " + r.rd.externalID + "#" + r.rd.internalID);
        
        boolean isInFreeList = false;
        Resource tmpRes = null;
        if (r.rd.reusable)
        {
            //randam resursa resursu sarase
            for(int i = 0; i < resources.size(); i++)
            {  
                if (resources.get(i) == r)
                {
                    //jei randam laisvu sarase ta resursa, tai reiskia, kad 
                    //jam turim grazint tuos komponentus. Todel isimenam ji.
                    isInFreeList = true;
                    tmpRes = resources.get(i);
                    break;
                }

            }

            LinkedList<ResComponent> tmpList = new LinkedList<>();
            for (ResComponent c:process.pd.ownedResources)
                tmpList.add(c);

            if (isInFreeList)
            {   
                //reiks prideti jo komponentus

                for (ResComponent c: tmpList)
                {

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

                for (ResComponent c:tmpList)
                {
                    if (c.parent == tmpRes)
                    {
                        OS.printToConsole("Removing: " + c.parent.rd.externalID);
                        tmpRes.rd.components.add(c);
                        process.pd.ownedResources.remove(c);
                    }
                }  
            }
        } else {
            for (int i = 0; i < process.pd.ownedResources.size(); i ++)
            {
                if (process.pd.ownedResources.get(i).parent.equals(r))
                {
                    process.pd.ownedResources.remove(i);
                    break;
                }
            }
        }
        
        OS.printToConsole("Resursas atlaisvintas");
        resourceManager.execute();
    }
    
    public void step()
    {
        OS.printToConsole("++++++++++++++++ OS Step! ++++++++++++++++++++++++");
        
        for (Processor p:rm.proc)
        {
            if (p.pd.currentProcess != null)
            {
                OS.printToConsole("Suveikia procesas: " + p.pd.currentProcess.pd.externalID
                        + " #" + p.pd.currentProcess.pd.internalID);

                p.pd.currentProcess.step();
            }
        }
        
    }
    
    public void stepToVM()
    {
        boolean stop = false;
        while (!stop && (runProcesses.size()!= 0))
        {
            step();
            int newCount = 0;
            for (Process p:processes)
                if (p instanceof VirtualMachine)
                    newCount++;
            if (newCount != VMCount)
            {
                OS.printToConsole(newCount + " kitas " + VMCount);
                stop = true;
                VMCount = newCount;
            }
        }
        
        
    }
    
    private void initProcesses()
    {
        processes = new LinkedList<>();
        runProcesses = new LinkedList<>();
        readyProcesses = new LinkedList<>();
        blockedProcesses = new LinkedList<>();
        resources = new LinkedList<>();

        processManager = new ProcessManager(this);
        OS.printToConsole("Process manager sukurtas");
        
        resourceManager = new ResourceManager(this);
        OS.printToConsole("Resource manager sukurtas");
        
        OS.printToConsole("Darbo pradžia");
        
        createProcess(null, OS.ProcessState.Ready, 90, null, ProcName.StartStop);

    }
    
    private int generateProcessInternalID(ProcName procName)
    {
        int newId = Process.numberOfInstances;
        Process.numberOfInstances++;
        return newId;
    }
    
    private int generateResourceInternalID(ResName resName)
    {
        int newId = Resource.numberOfInstances;
        Resource.numberOfInstances++;
        return newId;
    }
    
    public Process getMainproc()
    {
        for (Process p:processes)
            if (p.pd.externalID == ProcName.StartStop)
                return p;
        
        OS.printToConsole("NERASTAS StartStop");
        return  null;
    }
    
    public static void printToConsole(Object s)
    {
        if (showLog)
            System.out.println(s);
    }
}
