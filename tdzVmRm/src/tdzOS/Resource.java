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
            LinkedList<Process> waitingProcesses, LinkedList<Integer> waitingProcessComponentCount, ResourceManager resourceManager)
    {
        
        LinkedList<ResComponent> resComponents = new LinkedList<ResComponent>();
        for (Object o: components)
            resComponents.add(new ResComponent(o, this));
            
        
        rd = new ResourceDescriptor(creator, externalID, internalID,
                reusable, resComponents, waitingProcesses, waitingProcessComponentCount, resourceManager);
        
    }
}
