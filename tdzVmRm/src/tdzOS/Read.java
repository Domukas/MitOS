/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tdzOS.OS.ProcName;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzOS.OS;

/**
 *
 * @author Tomas
 */
public class Read extends Process
{
    FileInputStream stream;
    LinkedList<String> fileData;
    
    public Read (LinkedList inList, int internalID, ProcName externalID, 
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
                blockForIvedimoSrautas();
                break;
                
            case 2:
                blockForSupervizorineAtmintis();
                break;
                
            case 3:
                copyLines();
                break;
                
            case 4:
                createResourceUzduotisSupervizorinejeAtmintyje();
                break;
        }
    }
    
    private void blockForIvedimoSrautas()
    {
        OS.printToConsole("Procesas Read blokuojasi del resurso Ivedimo Srautas");
        pd.core.requestResource(this, ResName.IvedimoSrautas, 1);
        
        next();
    }
    
    private void blockForSupervizorineAtmintis()
    {
        OS.printToConsole("Procesas Read blokuojasi del resurso Supervizorine atmintis");
        
        //Susirandam input stream'a turimu resursu sarase
        
        stream = (FileInputStream)pd.ownedResources.getLast().value;
       
        //suskaicuojam eilutes ir...
        //Pasidedam failo turini i string'u masyva
        //Tik tam, kad veliau kas kart kopinuojant eilutes nereiktu pastoviai atidarinet failo
        //ir skaityt po eilute
        fileData = new LinkedList<>();
        int count = getLineCount(stream, fileData);
        
        
        //Papraso supervizorines atminties, tiek, kokio dydzio programa
        pd.core.requestResource(this, ResName.SupervizorineAtmintis, count);
        
        next();
    }
    
    private void copyLines()
    {
        //Cia jau kopijuojam eilute is saraso i supervizorines atminties elementus
        
        OS.printToConsole(fileData.size());
        
        for(String s:fileData)
        {
            for (ResComponent r:pd.ownedResources)
            {
                if (r.value instanceof String)
                {
                    String temp = (String) r.value;
                    if (temp.length() == 0)
                    {
                        OS.printToConsole("Kopijuojama eilute " + s);
                        r.value = s; //reikia butinai priskirt r.value ta reiksme. jei priskiriam temp'ui, tai reiksme neissaugoma
                        break;
                    }
                }
            }
        }
        
        next();
    }
    
            
    private void createResourceUzduotisSupervizorinejeAtmintyje()
    {
        LinkedList<String> blocks = new LinkedList<>();
        
        //Sudedam i komponentu sarasa nuorodas i jau nukopijuotas 
        //Programos tekstinio failo eilutes
        for (ResComponent r:pd.ownedResources)
            if (r.value instanceof String)
            {
                blocks.add((String)r.value);
            }
        
        LinkedList<Object> components = new LinkedList<>();
        components.add(blocks);
               
        //Kuriamas resursas...
        pd.core.createResource(this, ResName.UzduotisSupervizorinejeAtmintyje, components);
        
        //Pereinam i proceso pradine busena
        
        pd.ownedResources.clear();
        goTo(1);
    }
    
    private int getLineCount(FileInputStream input, LinkedList<String> tempList)
    {        
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;
        int count = 0;
        
        try {
            while ((line = br.readLine()) != null)
            {
                tempList.add(line);
                count++;
            }
            
        } catch (IOException ex) {
            OS.printToConsole("KLAIDA KOPIJUOJANT EILUTES!");
        }
        
        return count;
    }
}
