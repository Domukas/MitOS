/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.MemoryBlock;
import tdzVmRm.Processor;
import tdzVmRm.Word;

/**
 *
 * @author Tomas
 */
public class Loader extends Process
{
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
        }
    }
    
    //1
    private void blockForMessageToLoader()
    {
        System.out.println("Loader blokuojasi dėl resurso [Pranešimas Loader procesui]");
        //Pirmas pranesimo elementas - HDD. antras - vartotojo atmintis
        pd.core.requestResource(this, ResName.PranesimasLoaderProcesui, 2);
        
        next();
    }
    
    //2
    private void copyToMemory()
    {
        System.out.println("Loader kopijuoja duomenis į vartotojo atmintį ");
        
        //sarasuose saugom nuorodas i blokus HDD ir isskirtus blokus RM
        
        LinkedList<String> inHDDList = (LinkedList<String>)pd.ownedResources.getFirst().value;
        LinkedList<MemoryBlock> inMemoryList = (LinkedList<MemoryBlock>)pd.ownedResources.getLast().value;
        
        
        //Pirmo nekopijuojam, nes ten yra nurodyta, kiek bloku reiks
        int i = 0;
        int m = 1; //kelintas einamasis blokas is HDD
        
        System.out.println(inMemoryList.size() + " " + inMemoryList.get(i).getBlockSize());
        //einam per vius gautus atminties blokus
        
        while ((i<inMemoryList.size()) && (m < inHDDList.size()))
        {
            //einam per visus to bloko zodzius
            int n = 0;
            while (n<inMemoryList.get(i).getBlockSize() && (m < inHDDList.size()))
            {
                inMemoryList.get(i).setWord(n, new Word(inHDDList.get(m)));
                System.out.println(i + "tasis blokas" + n + " tasis zodis gauna reiksme " + inHDDList.get(m));
                n++;
                m++;
            }
            i++;
        }
        
        //tik testavimui
        pd.core.requestResource(this, ResName.MOSPabaiga, 1);
        
    }
    
    //3
    private void createResourceLoadingFinished()
    {
        
    }
}
