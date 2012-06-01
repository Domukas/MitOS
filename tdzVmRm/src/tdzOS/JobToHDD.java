/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
public class JobToHDD extends Process
{
    LinkedList<String> taskInSupervisor, taskInHDD;

    public JobToHDD (LinkedList inList, int internalID, OS.ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           OS.ProcessState state, int priority, Process parent, OS core)
    {
        super(inList, internalID, externalID, ps, p, or, state,
            priority, parent, core);
    }    
    
    public void step()
    {
        switch (nextInstruction)
        {
            case 1:
                blockForProgramBlockCount();
                break;
                
            case 2:
                blockForReadyTaskInSupervisor();
                break;
                
            case 3:
                blockForHDD();
                break;
                
            case 4:
                copyToHDD();
                break;
                
            case 5:
                createTaskInHDD();
                break;
            case 6:
                freeSupervisor();
                break;
                        
        }
    }
    
    //1
    private void blockForProgramBlockCount()
    {
        OS.printToConsole("JobToHDD blokuojasi dėl resurso Programos blokų skaičius supervizorinėje atmintyje");
        pd.core.requestResource(this, ResName.ProgramosBlokuSkaicius, 1);
        next();
    }
    
    //2
    private void blockForReadyTaskInSupervisor()
    {
        OS.printToConsole("JobToHDD blokuojasi dėl resurso paruošta užduotis supervizorinėje atmintyje");
        pd.core.requestResource(this, ResName.ParuostaUzduotis, 1);
        next();
    }
    
    //3
    private void blockForHDD()
    {
        OS.printToConsole("JobToHDD blokuojasi dėl resurso HDD");
        
        //Pasiskaiciuojam, kiek reiks bloku is HDD gauti
        //paskutinis gautas resursas bus paruosta uzduotis supervizorineje atmintyje, todel paskaiciuojam
        //kokio dydzio ji yra
        
        //Pasidarom nuoroda del patogumo
        taskInSupervisor = (LinkedList<String>) pd.ownedResources.getLast().value;
        
        //Prasom HDD tiek, kokio dydzio pas mus programa
        //Vienu daugiau, nes turim dar issaugot skaiciu, kiek RM bloku programai reiks
        pd.core.requestResource(this, ResName.HDD, taskInSupervisor.size() + 1);

        next();
    }
    
    //4
    private void copyToHDD()
    {
        OS.printToConsole("JobToHDD kopijuoja duomenis į diską");
        
        pd.core.rm.setCH3ClosedForAllProcessors();
        //Susikuriam sarasa del patogumo
        taskInHDD = new LinkedList<>();
        
        //Pirmas turimas resursas yra reikalingu RM bloku skaicius
        String requiredBlocks = (String)pd.ownedResources.getFirst().value;
        
        //Ji idedam i sarasa
        taskInSupervisor.addFirst(requiredBlocks);
        
        for(String s:taskInSupervisor)
        {
            for (ResComponent r:pd.ownedResources)
            {
                if (r.value instanceof String)
                {
                    String temp = (String) r.value;
                    if (temp.length() == 0)
                    {
                        OS.printToConsole("Kopijuojama eilute " + s);
                        r.value = s;
                        taskInHDD.add((String)r.value); //TODO
                        //reikia butinai priskirt r.value ta reiksme.
                        //jei priskiriam temp'ui, tai reiksme neissaugoma    

                        break;
                    }
                }
            }
        }
        pd.core.rm.setCH3OpenForAllProcessors();
        //galim naikint resursa 
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        
        next();
    }
    
    //5
    private void createTaskInHDD()
    {
        OS.printToConsole("JobToHDD kuria resursą Užduotis HDD");
        
        LinkedList<Object> components = new LinkedList();
        components.add(taskInHDD);
        //Sukuriam paruosta uzduoti supervizorineje atmintyje
        pd.core.createResource(this, ResName.UzduotisHDD, components);
        
        next();
    }
    
    private void freeSupervisor()
    {
        OS.printToConsole("JobToHDD atlaisvina resursą Supervizorinė atmintis");
     
        
        LinkedList<Object> tmpList = (LinkedList<Object>)pd.ownedResources.get(1).value;

        int count = tmpList.size() + 2;
        
        for (Resource r:pd.core.resources)
            if (r.rd.externalID == ResName.SupervizorineAtmintis)
            {
                for (int i = 0; i < count; i++)
                {
                    r.rd.components.add(new ResComponent(new String(), r));
                }
            }
        

        
        pd.ownedResources.clear();
        goTo (1);
    }
    
}
