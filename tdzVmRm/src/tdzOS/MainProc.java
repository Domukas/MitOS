/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
public class MainProc extends Process
{   
    public MainProc (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForTaskInHDD();
                break;
            case 2:
                isRuntimeZero();
                break;
            case 3:
                createJobGovernor();
                break;
            case 4:
                destroyJobGovernor();
                break;
        }
    }
    
    //1
    private void blockForTaskInHDD()
    {
        OS.printToConsole("MainProc blokuojasi del resurso Užduotis HDD");
        pd.core.requestResource(this, OS.ResName.UzduotisHDD, 1);
        next();
    }
    
    //2
    private void isRuntimeZero()
    {
        //Laikinai pasidedam nuorodas i programos bloku sarasa
        //Patikrinam ar vykdymo laikas yra nulinis
        LinkedList<String> tempList = (LinkedList<String>)pd.ownedResources.getFirst().value;
        
        //Jeigu filtyvus resursas, t.y. nera komandu, tada vykdymo laikas nulinis
        if (tempList.size() == 0)
        {
            OS.printToConsole("MainProc gavo fiktyvų resursą");
            goTo(4);
        }
        else
        {
            goTo(3);
        }
    }
    
    //3
    private void createJobGovernor()
    {
        OS.printToConsole("MainProc kuria JobGovernor");
        //Paduodam JobGovernor nuorodas i programa HDD
        
        //Paduodam job governoriui paskutini turima resursa 
        //Kuriame issaugota nuorodos i HDD, kur programa
        
        LinkedList<ResComponent> toGive = new LinkedList<>();
        toGive.add(pd.ownedResources.getLast());
        
        pd.core.createProcess(this, ProcessState.Ready, 80, toGive, OS.ProcName.JobGovernor);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //4
    private void destroyJobGovernor()
    {
        OS.printToConsole("MainProc naikina JobGovernor");
            
        //Surandam, kas to fiktyvaus resurso tevas
        Process parent = pd.ownedResources.getFirst().parent.rd.creator;

        //Naikinam jobGovernor'iu
        pd.core.destroyProcess(parent);
        
        pd.ownedResources.clear();

        goTo(1); 
    }  
}
