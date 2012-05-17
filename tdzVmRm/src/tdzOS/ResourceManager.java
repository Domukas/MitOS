/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;

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
        //ziuri visus laisvus resursus
        for(int i = 0; i < os.resources.size(); i++)
        {
            //paima kiekviena resursa
            Resource r = os.resources.get(i);
            //ir kiekvieno resurso visus procesus kuriems jo reikia
            LinkedList<Process> tmpProcessList = new LinkedList<>();
            
            //sortas
            //jei neskaidomas resursas
            
            tmpProcessList = r.rd.waitingProcesses;
            for(int j = 0 ; j < tmpProcessList.size(); j++)
            {
                //tada iesko visad didziausio prioriteto
                Process tmpProcess = findHighestProcessWhereResourceIsNeeded(tmpProcessList);
                //duodam resursa arba jo dali procesui
                int count = os.resources.get(j).rd.waitingProcessComponentCount.get(j);   //???
                giveToProcessAResourceComponents(tmpProcess, r, count);
                //jei tam procesui daugiau jokiu resursu nereikia, pazymim ji pasiruosusiu
                readyTheProcess(tmpProcess);
                //ismetam is laikino saraso kad veliau vel surastu didziausio prioriteto
                tmpProcessList.remove(j);
            }
        }
        //kvieciam procesu planuotoja
        os.processManager.Execute();
    }
    //---------------------------------------------------------
    Process findHighestProcessWhereResourceIsNeeded(LinkedList<Process> tmpProcessList)
    {
        int priority = 0;
        Process tmpProcess = null;
        for(int i = 0; i < tmpProcessList.size(); i++)
        {
            if(priority < tmpProcessList.get(i).pd.priority)
            {
                priority = tmpProcessList.get(i).pd.priority;
                tmpProcess = tmpProcessList.get(i);
            }
        }
        return tmpProcess;
    }
    //-----------------------------------------------------------------------------
    void giveToProcessAResourceComponents(Process tmpProcess, Resource r, int count)
    {
        if((r.rd.components.size() >= count) && (tmpProcess != null))
            {
                //sukuriam tuscia komponentu sarasa
                LinkedList<Object> newComponents = new LinkedList<Object>();
                //tuomet priskiriam tam naujam resursui tuos komponentus, o is originalo juos isimam
                for(int i = 0; i < count; i++)
                {
                    Object tmpComponent = r.rd.components.get(i);
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
            } 
    }
    //--------------------------------------
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
    }
    //---------------------    
}
