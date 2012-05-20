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
    LinkedList<String> commandList, blocks;
    private int currentBlock = 0;
    private int requiredBlockCount;
    String block;
    
    

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
        System.out.println("JCL blokuojasi del resurso Uzduotis supervizorineje atmintyje");
        pd.core.requestResource(this, ResName.UzduotisSupervizorinejeAtmintyje, 1);
        
        next();
    }
    
    //2
    private void initCommandsList()
    {
         //Kai gaunam resursa pasidarom nuoroda i bloku sarasa tik del patogumo
        
        ResComponent rc = pd.ownedResources.getFirst();
        blocks = (LinkedList<String>) rc.value;
        
        
        System.out.println("JCL inicijuoja programos komandu sarasa");
        commandList = new LinkedList<>();
         
        next();
    }
    
    //3
    private void getBlockFromSupervisor()
    {
        block = blocks.get(currentBlock);
        
        System.out.println("JCL paima " + currentBlock + " -taji bloka is saraso. Reiksme: "
                + block);
        
        next();
    }
    
    //4
    private void checkMemoryBlock()
    {
        System.out.println("JCL tikrina, ar yra @Memory blokas");
        block = block.replaceAll(" ", "");
        if (block.startsWith("@Memory"))
        {
            System.out.println("Blokas yra");
            goTo(6); //Jei blokas taisyklingas tai einam toliau
        }
        else
        {
            System.out.println("BLOKAS NERASTAS!");
            goTo(5); //Jei netaisyklingas, tada einam sukurt pranesima
        }
    }
    
    //5
    private void memoryBlockNotFound()
    {
        //Savaime surpantama...
        System.out.println("JCL kuria pranesima, kad nerastas @Memory blokas");
        pd.core.createResource(this, ResName.EiluteAtmintyje, createMessage("Trūksta @Memory bloko"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        
        goTo(1);
    }
    
    //6
    private void isMemoryBlockCorrect()
    {
        System.out.println("JCL tikrina @Memory bloko korektiškumą");
        block = block.substring(7, block.length());
        int value = Integer.parseInt(block, 16);
        
        //Jei blokas korektiskas. t.y. nurodytas tinkamas bloku skaicius
        if ((value <= 16) && (value > 0))
        {
            System.out.println("Blokas korektiškas");
            requiredBlockCount = value;//isiminam kiek bloku reiks isskirt atminty
            goTo(8);
        }
        else
        {
            System.out.println("BLOKAS NEKOREKTIŠKAS!");
            goTo(7);
        }
    }
    
    //7
    private void incorrectMemoryBlock()
    {
        System.out.println("JCL kuria pranešimą, @Memory blokas nekorektiškas");
        pd.core.createResource(this, ResName.EiluteAtmintyje,
                createMessage("Netaisyklingas @Memory blokas"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        goTo(1);
    }
    
    //8
    private void createProgramBlockCount()
    {
        System.out.println("JCL kuria resursą Programos blokų skaičius supervizorinėje atmintyje. "
                + "Reikšmė: " + requiredBlockCount);
        pd.core.createResource(this, ResName.ProgramosBlokuSkaicius,
                createMessage(Integer.toString(requiredBlockCount)));
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
