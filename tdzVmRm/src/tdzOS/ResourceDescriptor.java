/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;

/**
 *
 * @author Tomas
 */
public class ResourceDescriptor {
    int internalID, externalID;
    boolean reusable;
    int creatorID;
    LinkedList<?> components; //TODO
    LinkedList<Process> waitingProcesses;
    ResourceManager resourceManager;
 
    
    public ResourceDescriptor()
    {
        
    }
}
