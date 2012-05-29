/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import tdzOS.OS.ResName;

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
    //---------------------------------------------------------
    //paskirstytojas konkreciam procesui
    /*public void execute(Process process, Resource r, int count)
    {     
        //jei yra procesu kuriems res reikalingas
        if (r.rd.waitingProcesses.size() != 0)
        {
            //duodam resursa arba jo dali procesui
            giveToProcessAResourceComponents(process, r, count);
            //jei tam procesui daugiau jokiu resursu nereikia, pazymim ji pasiruosusiu
            readyTheProcess(process);
        } 
        //kvieciam procesu planuotoja
        os.processManager.Execute();
    }*/
    
    //----------------------------------------
    //paskirstytojas visiems procesams visu resursu
    public void execute()
    {
        System.out.println("Resursu paskirstytojas pradeda darba");
        
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
               //else System.out.println("Resursas " + resNameList.get(i) + " nerastas laisvu sarase");
           }
       }
       
       os.processManager.Execute();
                /*
        //ziuri visus laisvus resursus
        for(int i = 0; i < os.resources.size(); i++)
        {
            //paima kiekviena resursa
            Resource r = os.resources.get(i);
            
            //ir kiekvieno resurso visus procesus kuriems jo reikia
            LinkedList<Process> tmpProcessList = r.rd.waitingProcesses;
            //Kiek sarasas parodo, kiek reikia kiekvienam procesui resurso elementu
            LinkedList<Integer> requestedCountList = r.rd.waitingProcessComponentCount;
            
            //Surikiuoja laukianciu procesu sarasa pagal prioriteta
            SortWaitingProcessesList(tmpProcessList, requestedCountList);
            
            for(int j = 0 ; j < tmpProcessList.size(); j++)
            {
                int count = requestedCountList.get(j);
                
                if (giveResourceToProcess(tmpProcessList.get(j), r, count))
                {
                    //jei tam procesui daugiau jokiu resursu nereikia, pazymim ji pasiruosusiu
                    if (tmpProcessList.size() == 0)
                        //Procesa nustatom pasiruosusiu
                        //Jei jis dar laukia kokio nors resurso, tai sitas metodas
                        //Jo pasiruosusiu nepadaro
                        readyTheProcess(tmpProcessList.get(j));
                    
                    //ismetam ta procesa is resurso laukianciu saraso, jei jis gavo resursa
                    tmpProcessList.remove(j);
                    requestedCountList.remove(j);
                }
            }
        }
        //kvieciam procesu planuotoja
        os.processManager.Execute();
        * 
        */
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

                        System.out.println("Procesui " + tmpProcess.pd.externalID + 
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
                            LinkedList<Object> contents = (LinkedList<Object>) r.rd.components.getFirst().value;

                            if ((Process)contents.getLast() != tmpProcess)
                                give = false;
                        
                            break;
                            
                        case PratestiVMDarba:
                        case BlokasAtrakintas:
                        case IvestaEiluteSupervizorinejeAtmintyje:    
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
                        System.out.println("Procesui " + tmpProcess.pd.externalID + 
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
                /*
                //sukuriam tuscia komponentu sarasa
                LinkedList<ResComponent> newComponents = new LinkedList<ResComponent>();
                //tuomet priskiriam tam naujam resursui tuos komponentus, o is originalo juos isimam
                for(int i = 0; i < count; i++)
                {
                    ResComponent tmpComponent = r.rd.components.get(i);
                    r.rd.components.remove(i);
                    newComponents.add(tmpComponent);
                }
                //sukuriam resursa identiska originalui bet su kitu componentu sarasu
                Resource tmpR = new Resource(r.rd.creator, r.rd.externalID,
                        r.rd.internalID, r.rd.reusable, newComponents,r.rd.waitingProcesses,
                        r.rd.waitingProcessComponentCount, r.rd.resourceManager);
                
                //atiduodam ta nauja resursa procesui
                tmpProcess.pd.ownedResources.add(tmpR);
                
                //jei resursas nepanaudojamas dar karta tai ismetam ji is saraso
                if(!r.rd.reusable)
                {
                    os.resources.remove(r);
                }
                * 
                */

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
    
    //--------------------------------------
    /*
    void readyTheProcess(Process tmpProcess)
    {
        if(tmpProcess != null)
        {
            boolean stillNeeded = false;
            for(int i = 0; i < os.resources.size(); i++)
            {
                for(int j = 0; j < os.resources.get(i).rd.waitingProcesses.size(); j++)
                {
                    if(tmpProcess == os.resources.get(i).rd.waitingProcesses.get(j))
                    {
                        stillNeeded = true;
                    }
                }
            }
            if(!stillNeeded)
            {
                tmpProcess.pd.state =  OS.ProcessState.Ready;
            }
        }
        else System.out.println("Kazkas labai negerai!!!!!");
    }
    * */
    //---------------------    
}
