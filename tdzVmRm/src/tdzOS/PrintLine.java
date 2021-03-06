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
 * @author Zory
 */
public class PrintLine extends Process
{
    
    public PrintLine (LinkedList inList, int internalID, OS.ProcName externalID, 
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
                blockForEiluteAtmintyje();
                break;
                
            case 2:
                blockForIsvedimoIrenginys();
                break;
                
            case 3:
                sendMessageToOutput();
                break;
                
            case 4:
                freeResourceIsvedimoIrenginys();
                break;
            case 5:
                createResourceIsvestaEilute();
                break;
        }
    }
    
    //1
    private void blockForEiluteAtmintyje()
    { 
        OS.printToConsole("PrintLine blokuojasi dėl resurso [Eilutė atmintyje]");
        pd.core.requestResource(this, OS.ResName.EiluteAtmintyje, 1);
        next();
    }
    
    //2
    private void blockForIsvedimoIrenginys()
    {
        OS.printToConsole("PrintLine blokuojasi dėl resurso [Išvedimo įrenginys]");
        pd.core.requestResource(this, OS.ResName.IsvedimoIrenginys, 1);
        next();
    }
    
    //3
    private void sendMessageToOutput()
    {
        OS.printToConsole("PrintLine spausdina į išvedimo įrenginį");
        pd.core.rm.setCH1ClosedForAllProcessors();
        pd.core.rm.out.send((String)pd.ownedResources.getFirst().value);
        next();
    }
 
    //4
    private void freeResourceIsvedimoIrenginys()
    {
        OS.printToConsole("PrintLine atlaisvina resursą [išvedimo įrenginys]");
        for (ResComponent re:pd.ownedResources)
            OS.printToConsole(re.value);
        
        pd.core.freeResource(this, pd.ownedResources.getLast().parent);
        
        pd.core.rm.setCH1OpenForAllProcessors();
        
        
        
        if (pd.ownedResources.getFirst().parent.rd.creator instanceof JobGovernor)
        {
            OS.printToConsole("true");
            next();
        }
        else
        {
            pd.ownedResources.clear();
            goTo(1);
        }
    }
    
    
    
    //5
    private void createResourceIsvestaEilute()
    {
        OS.printToConsole("PrintLine kuria resursą [išvesta eilutė]");
        
        LinkedList<Object> components = new LinkedList();
        components.add(pd.ownedResources.getFirst().parent.rd.creator);
          
        //Kuriamas resursas..
        pd.core.createResource(this, ResName.IsvestaEilute, components);
        
        pd.ownedResources.clear();
        //Pereinam i proceso pradine busena
        goTo(1);
    }
}
