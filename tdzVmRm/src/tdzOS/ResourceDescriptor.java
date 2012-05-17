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
public class ResourceDescriptor {
    int internalID;
    ResName externalID;
    boolean reusable;
    Process creator;          //DOKUMENTE PARASYTA, KAD VIDINIS KUREJO VARDAS, O NE ID
    LinkedList<Object> components; //TODO
    LinkedList<Process> waitingProcesses;
    LinkedList<Integer> waitingProcessComponentCount;
    ResourceManager resourceManager;
 
    
    public ResourceDescriptor(Process creator, ResName externalID, int internalID,
            boolean reusable, LinkedList<Object> components, //APTARNAUJAMU SARASAS NEREIKALINGAS AR REIKALINGAS?
            LinkedList<Process> waitingProcesses, LinkedList<Integer> waitingProcessComponentCount, ResourceManager resourceManager)
    {
        this.creator = creator;
        this.externalID = externalID;
        this.internalID = internalID;
        this.reusable = reusable;
        this.components = components;
        this.waitingProcesses = waitingProcesses;
        this.waitingProcessComponentCount = waitingProcessComponentCount;
        this.resourceManager = resourceManager;
    }
}
