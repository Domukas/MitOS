/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import tdzVmRm.MemoryBlock;
import tdzVmRm.RealMachine;
import tdzVmRm.VirtualMemory;

/**
 *
 * @author Domukas
 */
public class VirtualMemoryTableModel extends MemoryTableModel {
	
    RealMachineGUI ownerGUI;
    
	public VirtualMemoryTableModel(RealMachine realMachine, RealMachineGUI ownerGUI){
            super(realMachine, ownerGUI);
            //memory = realMachine.VM.memory;
            this.ownerGUI = ownerGUI;
	}
}
