/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.util.LinkedList;
import tdzOS.OS.ProcName;
import tdzOS.OS.ProcessState;
import tdzOS.OS.ResName;
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
   public LinkedList<ResName> waitingFor;          //Resursu isoriniai vardai, kuriu dabar laukia procesas 
   public LinkedList<Integer> waitingCount;        //Atitinkamo laukiamo resurso reikalingas kiekis 
   public ProcessState state;
   public int priority;
   public Process parent;
   public LinkedList<Process> children;
   public OS core;
   
   public ProcessDescriptor(LinkedList inList, int internalID, ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           ProcessState state, int priority, Process parent, OS core)
   {
       this.inList = inList;
       this.internalID = internalID;
       this.externalID = externalID;
       this.procesorState = ps;
       this.processor = p;
       this.createdResources = new LinkedList<Resource>();
       this.ownedResources = or;
       this.waitingFor = new LinkedList<ResName>();
       this.waitingCount = new LinkedList<Integer>();
       this.state = state;
       this.priority = priority;
       this.parent = parent;
       this.children = new LinkedList<Process>();   
       this.core = core;
   }
   
   
}
