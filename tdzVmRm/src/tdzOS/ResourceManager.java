/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import tdzOS.OS.ResName;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
class ResourceManager {
    
    public OS os;
    
    ResourceManager(OS os)
    {
        this.os = os;
    }

    //paskirstytojas visiems procesams visu resursu
    public void execute()
    {
        OS.printToConsole("Resursu paskirstytojas pradeda darba");
        
        //Pasidarom laikina sarasa procesu kurie kokio nors resurso laukia
        LinkedList<Process> tempList = new LinkedList<Process>();
        
        for (Process p:os.processes)
            //Jei procesas laukia kokio nrs resurso, tai ji ten dedam
            if (p.pd.waitingFor.size() != 0)
                tempList.add(p);
        
       SortWaitingProcessesList(tempList); 
       
       for (Process p:tempList)
       {
           LinkedList<ResName> resNameList = new LinkedList<>();
           LinkedList<Integer> intList = new LinkedList<>();
           
           for (int i=0; i<p.pd.waitingFor.size(); i++)
           {
               resNameList.add(p.pd.waitingFor.get(i));
               intList.add(p.pd.waitingCount.get(i));
           }
           
           for (int i=0; i<resNameList.size(); i++)
           {
               Resource r = findResourceByExternalName(resNameList.get(i));
               if (r != null)
               {//Jei toks resursas jau yra laisvu sarase, tai bandom ji duot
                   if (giveResourceToProcess(p, r, intList.get(i)))
                   {
                       //jei ta resursa davem, tai ismetam is saraso laukiamu....
                       p.pd.waitingFor.remove(r.rd.externalID);
                       p.pd.waitingCount.remove(intList.get(i));
                       
                       //jei davem resursa ir jeigu jam daugiau jokio kito resurso nereikia
                       if (p.pd.waitingCount.size() == 0)
                       {
                           p.pd.state = OS.ProcessState.Ready;
                           
                           //Jei tas procesas blokuotu sarase tai ji idedam i pasiruosusiu sarasa
                           //Blokuotu sarase jis atsiranda, jei is karto negauna reikalingo resurso
                           if (os.blockedProcesses.contains(p))
                           {
                               os.blockedProcesses.remove(p);
                               os.readyProcesses.add(p);
                           }
                       }
                                                
                   }
               } 
               //else OS.printToConsole("Resursas " + resNameList.get(i) + " nerastas laisvu sarase");
           }
       }
       
       os.processManager.Execute();
    }
    
    private void SortWaitingProcessesList(LinkedList<Process> list)
    {
        for(int i=0; i<(list.size()-1); i++)
        {
            for (int j=i+1; j<(list.size()-1); j++)
            {
                if (list.get(i).pd.priority < list.get(j).pd.priority)
                {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
    //-----------------------------------------------------------------------------
    private boolean giveResourceToProcess(Process tmpProcess, Resource r, int count)
    {
        if((r.rd.components.size() >= count) && (tmpProcess != null))
            {                
                //Vartotojo atminti dalinam atsitiktinai
                if (r.rd.externalID == ResName.VartotojoAtmintis)
                {
                    //Is saraso dalinam resursus atsitiktinai
                    Random randomGenerator = new Random();
                    
                    for (int i=0; i<count; i++)
                    {
                        //generuojam atsitiktini skaciu 
                        int randomInt = randomGenerator.nextInt(r.rd.components.size());
                        tmpProcess.pd.ownedResources.add(r.rd.components.get(randomInt));
                        r.rd.components.remove(randomInt);

                        OS.printToConsole("Procesui " + tmpProcess.pd.externalID + 
                                " skiriamas resurso " + r.rd.externalID + " " + randomInt + 
                                " -tasis elementas");
                    }
                }
                else //Visa kita is eiles dalinam
                {   
                    boolean give = true;
                     //dalinant pertraukimus reikia patikrint, ar jis tam JG skirtas
                     //arba ar pranesimas tam SharedMemoryControl skirtas
                    switch (r.rd.externalID)
                    {
                        case Pertraukimas:
                        case PranesimasSharedMemorycontrolProcesui: 
                        case PranesimasJobGovernor:
                            LinkedList<Object> contents = (LinkedList<Object>) r.rd.components.getFirst().value;

                            if ((Process)contents.getLast() != tmpProcess)
                                give = false;
                        
                            break;
                            
                        case PratestiVMDarba:
                        case BlokasAtrakintas:
                        case IvestaEiluteSupervizorinejeAtmintyje:  
                        case IsvestaEilute:
                            if ((Process)r.rd.components.getLast().value != tmpProcess)
                                give = false;     
                            
                            break;
                    }

                    
                    if (give)
                    {
                        for (int i=0; i<count; i++)
                        {
                            tmpProcess.pd.ownedResources.add(r.rd.components.getFirst());
                            r.rd.components.removeFirst();
                        }
                        OS.printToConsole("Procesui " + tmpProcess.pd.externalID + 
                            " skirta resurso " + r.rd.externalID + " " + count + " elementu");
                        
                        
                    }
                    else return false;
                    
                }
                //Jei to resurso nebeliko laisvu komponentu, tai ji pasalinam is laisvu saraso
                if (r.rd.components.size() == 0)
                    os.resources.remove(r);
                
                
                return true;
              } 
                else return false;


    }
    
    private Resource findResourceByExternalName(ResName name)
    {
        for (Resource r:os.resources)
        {        
            if (r.rd.externalID == name)
                return r;
        }
        return null;
    }
    
}
