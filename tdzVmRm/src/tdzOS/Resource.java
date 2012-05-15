/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ResName;

/**
 *
 * @author Tomas
 */
class Resource {
    public ResourceDescriptor rd;
    
    public Resource(Process creator, ResName externalID, int internalID,
            boolean reusable, LinkedList<Object> components, //APTARNAUJAMU SARASAS NEREIKALINGAS AR REIKALINGAS?
            LinkedList<Process> waitingProcesses, ResourceManager resourceManager)
    {
        rd = new ResourceDescriptor(creator, externalID, internalID,
                reusable, components, waitingProcesses, resourceManager);
        
    }
}
