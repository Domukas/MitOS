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
                getBlockFromSupervisor();
                break;
                
            case 10:
                checkCodeBlock();
                break;
                
            case 11:
                codeBlockNotFound();
                break;
                
            case 12:
                getBlockFromSupervisor();
                break;
                
            case 13:
                checkCodeEndBlock();
                break;
                
            case 14:
                createTaskInSupervisor();
                break;
                
            case 15:
                addCommand();
                break;
                
            case 16:
                blocksLeft();
                break;
                
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
        OS.printToConsole("JCL blokuojasi dėl resurso [Užduotis supervizorinėje atmintyje]");
        pd.core.requestResource(this, ResName.UzduotisSupervizorinejeAtmintyje, 1);
        
        currentBlock = 0;
        
        next();
    }
    
    //2
    private void initCommandsList()
    {
         //Kai gaunam resursa pasidarom nuoroda i bloku sarasa tik del patogumo
        
        ResComponent rc = pd.ownedResources.getLast();
        blocks = (LinkedList<String>) rc.value;
        
        
        OS.printToConsole("JCL inicijuoja programos komandų sąrašą");
        commandList = new LinkedList<>();
         
        next();
    }
    
    //3
    private void getBlockFromSupervisor()
    {
        block = blocks.get(currentBlock);
        
        OS.printToConsole("JCL paima " + currentBlock + " -tajį bloką iš sąrašo. Reiksmė: "
                + block);
        
        currentBlock++;
        
        next();
    }
    
    //4
    private void checkMemoryBlock()
    {
        OS.printToConsole("JCL tikrina, ar yra @Memory blokas");
        block = block.replaceAll(" ", "");
        if (block.startsWith("@Memory"))
        {
            OS.printToConsole("Blokas yra");
            goTo(6); //Jei blokas taisyklingas tai einam toliau
        }
        else
        {
            OS.printToConsole("BLOKAS NERASTAS!");
            goTo(5); //Jei netaisyklingas, tada einam sukurt pranesima
        }
    }
    
    //5
    private void memoryBlockNotFound()
    {
        //Savaime surpantama...
        OS.printToConsole("JCL kuria pranešimą, kad nerastas @Memory blokas");
        pd.core.createResource(this, ResName.EiluteAtmintyje, createMessage("Trūksta @Memory bloko"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //6
    private void isMemoryBlockCorrect()
    {
        OS.printToConsole("JCL tikrina @Memory bloko korektiškumą");
        block = block.substring(7, block.length());
        int value = Integer.parseInt(block, 16);
        
        //Jei blokas korektiskas. t.y. nurodytas tinkamas bloku skaicius
        if ((value <= 16) && (value > 0))
        {
            OS.printToConsole("Blokas korektiškas");
            requiredBlockCount = value;//isiminam kiek bloku reiks isskirt atminty
            goTo(8);
        }
        else
        {
            OS.printToConsole("BLOKAS NEKOREKTIŠKAS!");
            goTo(7);
        }
    }
    
    //7
    private void incorrectMemoryBlock()
    {
        OS.printToConsole("JCL kuria pranešimą, @Memory blokas nekorektiškas");
        pd.core.createResource(this, ResName.EiluteAtmintyje,
                createMessage("Netaisyklingas @Memory blokas"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //8
    private void createProgramBlockCount()
    {
        OS.printToConsole("JCL kuria resursą Programos blokų skaičius supervizorinėje atmintyje. "
                + "Reikšmė: " + requiredBlockCount);
        pd.core.createResource(this, ResName.ProgramosBlokuSkaicius,
                createMessage(Integer.toString(requiredBlockCount)));
        
        next();
    }
    
    //9 getBlockFromSupervisor

    //10
    private void checkCodeBlock()
    {
        OS.printToConsole("JCL tikrina, ar yra @Code blokas");
        block = block.replaceAll(" ", "");
        if (block.startsWith("@Code"))
        {
            OS.printToConsole("Blokas yra");
            goTo(12); //Jei blokas taisyklingas tai einam toliau
        }
        else
        {
            OS.printToConsole("BLOKAS NERASTAS!");
            goTo(11); //Jei netaisyklingas, tada einam sukurt pranesima
        }
        
    }
    
    //11
    private void codeBlockNotFound()
    {
        OS.printToConsole("JCL kuria pranešimą, kad nerastas @Code blokas");
        pd.core.createResource(this, ResName.EiluteAtmintyje, createMessage("Trūksta @Code bloko"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //12 getBlockFromSupervisor
  
    //13
    private void checkCodeEndBlock()
    {
        OS.printToConsole("JCL tikrina, ar yra @CodeEnd blokas");
        block = block.replaceAll(" ", "");
        if (block.startsWith("@CodeEnd"))
        {
            OS.printToConsole("Blokas yra");
            goTo(14); 
        }
        else
        {
            OS.printToConsole("Ne visi blokai peržiūrėti. Einama prie kito");
            goTo(15);
        }
    }
    
    //14
    private void createTaskInSupervisor()
    {
        LinkedList<Object> components = new LinkedList();
        components.add(commandList);
        //Sukuriam paruosta uzduoti supervizorineje atmintyje
        pd.core.createResource(this, ResName.ParuostaUzduotis, components);
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    //15
    private void addCommand()
    {
        OS.printToConsole("Blokas pridedamas į sąrašą");
        commandList.add(block);
        next();
    }
    
    //16
    private void blocksLeft()
    {
        //jei perziurejom sarasa
        if (blocks.size() == currentBlock)
        {
            OS.printToConsole("Daugiau blokų nėra");
            goTo(17);
        }
        else
        {
            OS.printToConsole("Dar yra blokų");
            goTo(12);
        }
    }
    
    //17
    private void codeEndBlockNotFound()
    {
        OS.printToConsole("JCL kuria pranešimą: Trūksta CodeEnd bloko");
        pd.core.createResource(this, ResName.EiluteAtmintyje,
                createMessage("Trūksta @CodeEnd bloko"));
        
        //Naikinam resursa Uzduotis supervizorineje atmintyje
        pd.core.destroyResource(pd.ownedResources.getFirst().parent);    
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
}
