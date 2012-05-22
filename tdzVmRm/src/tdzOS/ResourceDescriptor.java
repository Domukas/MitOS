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
    public int internalID;
    public ResName externalID;
    boolean reusable;
    public Process creator;          //DOKUMENTE PARASYTA, KAD VIDINIS KUREJO VARDAS, O NE ID
    public LinkedList<ResComponent> components; 
    ResourceManager resourceManager;
 
    
    public ResourceDescriptor(Process creator, ResName externalID, int internalID,
            boolean reusable, LinkedList<ResComponent> components, //APTARNAUJAMU SARASAS NEREIKALINGAS AR REIKALINGAS?
            ResourceManager resourceManager)
    {
        this.creator = creator;
        this.externalID = externalID;
        this.internalID = internalID;
        this.reusable = reusable;
        this.components = components;
        this.resourceManager = resourceManager;
    }
}
