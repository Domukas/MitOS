/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import IODevices.Output;
import java.util.LinkedList;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;

/**
 *
 * @author Zory
 */
public class PrintLine extends Process
{
    LinkedList<Object> isvestaEilute;
    
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
        System.out.println("PrintLine blokuojasi dėl resurso [Eilutė atmintyje]");
        pd.core.requestResource(this, OS.ResName.EiluteAtmintyje, 1);
        next();
    }
    
    //2
    private void blockForIsvedimoIrenginys()
    {
        System.out.println("PrintLine blokuojasi dėl resurso [Išvedimo įrenginys]");
        pd.core.requestResource(this, OS.ResName.IsvedimoIrenginys, 1);
        next();
    }
    
    //3
    private void sendMessageToOutput()
    {
        System.out.println("PrintLine spausdina į išvedimo įrenginį");
        pd.core.rm.out.send((String)pd.ownedResources.getFirst().value);
        next();
    }
 
    //4
    private void freeResourceIsvedimoIrenginys()
    {
        System.out.println("PrintLine atlaisvina resursą [išvedimo įrenginys]");
        pd.core.freeResource(this, pd.ownedResources.getFirst().parent);
        next();
    }
    
    //5
    private void createResourceIsvestaEilute()
    {
        System.out.println("PrintLine kuria resursą išvesta eilutė");
        
        LinkedList<Object> components = new LinkedList();
        components.add("isvesta");
          
        //Kuriamas resursas...
        pd.core.createResource(this, ResName.IsvestaEilute, components);
        
        //Pereinam i proceso pradine busena
        goTo(1);
    }
}
