/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class JCL extends Process
{

    public JCL (LinkedList inList, int internalID, OS.ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           OS.ProcessState state, int priority, Process parent, OS core)
    {
        super(inList, internalID, externalID, ps, p, or, state,
            priority, parent, core);
    }

    //Numeracija pagal dokumenta
    
    //1
    public void step()
    {

        switch (nextInstruction)
        {
            case 1:
                blockForUzduotisSupervizorinejeAtmintyje();
                break;
                
            case 2:
                initCommandsList();
                break;
                
            case 3:
                getBlockFromSupervisor();
                break;
                
            case 4:
                checkMemoryBlock();
                break;
                
            case 5:
                memoryBlockNotFound();
                break;
                
            case 6:
                isMemoryBlockCorrect();
                break;
                
            case 7:
                incorrectMemoryBlock();
                break;
                
            case 8:
                createProgramBlockCount();
                break;
                
            case 9:
                getBlockFromSupervisor2();
                break;
                
            case 10:
                isCodeBlock();
                break;
                
            case 11:
                codeBlockNotFound();
                break;
                
            case 12:
                getBlockFromSupervisor3();
                break;
                
            case 13:
                isCodeEndBlock();
                break;
                
            case 14:
                createTaskInSupervisor();
                break;
                
            case 15:
                addCommand();
                
            case 16:
                blocksLeft();
                
            case 17:
                codeEndBlockNotFound();
                break;
                
        }
        
        
    }
 
    //1
    private void blockForUzduotisSupervizorinejeAtmintyje()
    {
        //Prasom resurso uzduotis atmintyje su vienu nuorodu sarasu i blokus
        //Kuriuose yra nuskaitytos programos komandos
        System.out.println("JCL blokuojasi del resurso Uzduotis supervisorineje atmintyje");
        pd.core.requestResource(this, ResName.UzduotisSupervizorinejeAtmintyje, 1);
        
        nextInstruction++;
        
    }
    //2
    private void initCommandsList()
    {
        
    }
    
    //3
    private void getBlockFromSupervisor()
    {
        
    }
    
    //4
    private void checkMemoryBlock()
    {
        
    }
    
    //5
    private void memoryBlockNotFound()
    {
     
        goTo(1);
    }
    
    //6
    private void isMemoryBlockCorrect()
    {
        
    }
    
    //7
    private void incorrectMemoryBlock()
    {
     
        goTo(1);
    }
    
    //8
    private void createProgramBlockCount()
    {
        
    }
    
    //9
    private void getBlockFromSupervisor2()
    {
        
    }
    
    //10
    private void isCodeBlock()
    {
        
    }
    
    //11
    private void codeBlockNotFound()
    {
        
        goTo(1);
    }
    
    //12
    private void getBlockFromSupervisor3()
    {
        
    }
    
    //13
    private void isCodeEndBlock()
    {
        
    }
    
    //14
    private void createTaskInSupervisor()
    {
        
        goTo(1);
    }
    
    //15
    private void addCommand()
    {
        
    }
    
    //16
    private void blocksLeft()
    {
        
    }
    
    //17
    private void codeEndBlockNotFound()
    {
     
        goTo(1);
    }
}
