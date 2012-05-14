/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class ProcessDescriptor {
   public LinkedList inList;                //sąrašas, kuriame yra procesas
   public int internalID;
   public String externalID;
   public ProcessorState procesorState;
   public Processor processor;
   public LinkedList<Resource> createdResources;
   public LinkedList<Resource> ownedResources;
   public ProcessState state;
   public int priority;
   public Process parent;
   public LinkedList<Process> children;
   
   public ProcessDescriptor(LinkedList inList, int internalID, String externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr, LinkedList<Resource> or,
           ProcessState state, int priority, Process parent, LinkedList<Process> c)
   {
       this.inList = inList;
       this.internalID = internalID;
       this.externalID = externalID;
       this.procesorState = ps;
       this.processor = p;
       this.createdResources = cr;
       this.ownedResources = or;
       this.state = state;
       this.priority = priority;
       this.parent = parent;
       this.children = c;           
   }
   
   
}
