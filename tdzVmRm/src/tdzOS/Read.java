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

/**
 *
 * @author Tomas
 */
public class Read extends Process
{
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
                copyLine();
                break;
                
            case 4:
                checkIfDoneCopying();
                break;
                
            case 5:
                createResourceUzduotisSupervizorinejeAtmintyje();
                break;
        }
        
        nextInstruction++;
        
        if (nextInstruction > 5)
        {
            nextInstruction = 1;
        }
    }
    
    private void blockForIvedimoSrautas()
    {
        System.out.println("Procesas Read blokuojasi del resurso Ivedimo Srautas");
        pd.core.requestResource(this, ResName.IvedimoSrautas, 1);
    }
    
    private void blockForSupervizorineAtmintis()
    {
        System.out.println("Procesas Read blokuojasi del resurso Supervizorine atmintis");
        
        //Susirandam input stream'a turimu resursu sarase
        
        FileInputStream stream = null;
        for (ResComponent r:pd.ownedResources)
            if (r.value instanceof FileInputStream)
            {
                stream = (FileInputStream) r.value;
            }
                
        
        //Papraso supervizorines atminties, tiek, kokio dydzio programa
        pd.core.requestResource(this, ResName.SupervizorineAtmintis, getLineCount(stream));
    }
    
    private void copyLine()
    {
        
    }
    
    private void checkIfDoneCopying()
    {
        
    }
            
    private void createResourceUzduotisSupervizorinejeAtmintyje()
    {
        
    }
    
    
    private int getLineCount (FileInputStream input)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;
        int count = 0;
        
        try {
            while ((line = br.readLine()) != null)
            {
                count++;
            }
            
        } catch (IOException ex) {
            System.out.println("KLAIDA SKAICIUOJANT EILUTES!");
        }
        
        return count;
    }
}
