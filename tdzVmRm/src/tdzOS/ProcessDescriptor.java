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
   public int internalID, externalID;
   public ProcessorState procesorState;
   public Processor processor;
   public LinkedList<Resource> createdResources;
   public LinkedList<Resource> ownedResources;
   public ProcessState state;
   public int priority;
   public Process parent;
   public LinkedList<Process> children;
   
   public ProcessDescriptor()
   {
       
   }
   
   
}
