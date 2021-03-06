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
public class Resource {
    public ResourceDescriptor rd;
    public static int numberOfInstances = 0;
    
    public Resource(Process creator, ResName externalID, int internalID,
            boolean reusable, LinkedList<Object> components,
            ResourceManager resourceManager)
    {
        
        LinkedList<ResComponent> resComponents = new LinkedList<>();
        for (Object o: components)
            resComponents.add(new ResComponent(o, this));
            
        
        rd = new ResourceDescriptor(creator, externalID, internalID,
                reusable, resComponents, resourceManager);
        
    }
}
