/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.Arrays;
import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.MemoryBlock;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
public class Loader extends Process
{
    int PagingAdress;
    LinkedList<String> inHDDList;
    
    public Loader (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForMessageToLoader();
                break;
            case 2:
                copyToMemory();
                break;
            case 3:
                createResourceLoadingFinished();
                break;
                
            case 4:
                freeHDD();
                break;
        }
    }
    
    //1
    private void blockForMessageToLoader()
    {
        OS.printToConsole("Loader blokuojasi dėl resurso [Pranešimas Loader procesui]");
        //Pirmas pranesimo elementas - HDD. antras - vartotojo atmintis
        pd.core.requestResource(this, ResName.PranesimasLoaderProcesui, 2);
        
        next();
    }
    
    //2
    private void copyToMemory()
    {
        OS.printToConsole("Loader kopijuoja duomenis į vartotojo atmintį ");
        
        pd.core.rm.setCH3ClosedForAllProcessors();
        //sarasuose saugom nuorodas i blokus HDD ir isskirtus blokus RM
        
        inHDDList = (LinkedList<String>)pd.ownedResources.getFirst().value;
        LinkedList<MemoryBlock> inMemoryList = (LinkedList<MemoryBlock>)pd.ownedResources.getLast().value;
        
        //Sudarom PLR
        PagingAdress = CreatePagingTable(inMemoryList);
        
        
        //Pirmo nekopijuojam, nes ten yra nurodyta, kiek bloku reiks
        int i = 1; //Pradedam nuo 1, nes nulinaime yra PLR
        int m = 1; //kelintas einamasis blokas is HDD
        
        //einam per vius gautus atminties blokus
        
        while ((i<inMemoryList.size()) && (m < inHDDList.size()))
        {
            //einam per visus to bloko zodzius
            int n = 0;
            while (n<inMemoryList.get(i).getBlockSize() && (m < inHDDList.size()))
            {
                inMemoryList.get(i).setWord(n, new Word(inHDDList.get(m)));
                n++;
                m++;
            }
            i++;
        }
        pd.core.rm.setCH3OpenForAllProcessors();
        //tik testavimui
        next();
        
    }
    
    //3
    private void createResourceLoadingFinished()
    {
        OS.printToConsole("Loader kuria resursą [Užduoties pakrovimas į vartotojo atmintį baigtas]");
        
        //Dar paduodam puslapiavimo lenteles adresa
        pd.core.createResource(this, ResName.UzduotiesPakrovimasBaigtas,
                createMessage(Integer.toHexString(PagingAdress)));
        
        next();
    }
    
    private void freeHDD()
    {
        for (Resource r:pd.core.resources)
            if (r.rd.externalID == ResName.HDD)
            {
                for (int i = 0; i < inHDDList.size(); i++)
                {
                    r.rd.components.add(new ResComponent(new String(), r));
                }
            }
        
        pd.ownedResources.clear();
        
        goTo(1);
    }
    
    private int CreatePagingTable(LinkedList<MemoryBlock> memory)
    {
        int tableAdress = getBlockAbsoluteAdress(memory.getFirst());
        
        //nulinio nelieciam, nes ten bus pati lentete
        for (int i = 1; i<memory.size(); i++)
        {
            //Irasom bloko adresa i PLR
            memory.getFirst().setWord(i-1, new Word(getBlockAbsoluteAdress(memory.get(i))));
        }
        
        return tableAdress;
    }
    
    private int getBlockAbsoluteAdress(MemoryBlock block)
    {
        for (int i=0; i< RealMachine.memory.getMaxMemoryBlocks(); i++)
        {
            if (RealMachine.memory.getBlock(i) == block)
                return i;
        }
        
        //Nerastas blokas atminty!
        return -1;
        
    }
}
