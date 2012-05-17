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
    public void Execute(Process process, Resource r, int count)
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
    }
    //----------------------------------------
    //paskirstytojas visiems procesams
    public void Execute(Resource r, int count)
    {
        //jei yra procesu kuriems res reikalingas
        if (r.rd.waitingProcesses.size() != 0)
        {
            //tai randam su didziausiu prioritetu procesa
            Process tmpProcess = findHighestProcessWhereResourceIsNeeded(r);
            //duodam resursa arba jo dali procesui
            giveToProcessAResourceComponents(tmpProcess, r, count);
            //jei tam procesui daugiau jokiu resursu nereikia, pazymim ji pasiruosusiu
            readyTheProcess(tmpProcess);
        } 
        //kvieciam procesu planuotoja
        os.processManager.Execute();
    }
    //---------------------------------------------------------
    Process findHighestProcessWhereResourceIsNeeded(Resource r)
    {
        int priority = 0;
        Process tmpProcess = null;
        for(int i = 0; i < r.rd.waitingProcesses.size(); i++)
        {
            if(priority < r.rd.waitingProcesses.get(i).pd.priority)
            {
                priority = r.rd.waitingProcesses.get(i).pd.priority;
                tmpProcess = r.rd.waitingProcesses.get(i);
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
                Resource tmpR = new Resource(r.rd.creator, r.rd.externalID, r.rd.internalID, r.rd.reusable, newComponents, r.rd.waitingProcesses, r.rd.resourceManager);
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
