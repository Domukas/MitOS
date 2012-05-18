/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzOS.OS.ProcessState;
import tdzVmRm.Processor;

/**
 *
 * @author Tomas
 */
public class ProcessDescriptor {
   public LinkedList inList;                //sąrašas, kuriame yra procesas
   public int internalID;
   public ProcName externalID;
   public ProcessorState procesorState;
   public Processor processor;
   public LinkedList<Resource> createdResources;
   public LinkedList<ResComponent> ownedResources; //Cia ne patys resursai, o ju komponentai
   public ProcessState state;
   public int priority;
   public Process parent;
   public LinkedList<Process> children;
   public OS core;
   
   public ProcessDescriptor(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<Resource> cr, LinkedList<ResComponent> or,
           ProcessState state, int priority, Process parent, LinkedList<Process> c, OS core)
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
       this.core = core;
   }
   
   
}
