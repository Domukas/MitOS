/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import tdzOS.VirtualMachine;
import tdzVmRm.MemoryBlock;
import tdzVmRm.RealMachine;
import tdzVmRm.VirtualMemory;

/**
 *
 * @author Domukas
 */
public class VirtualMemoryTableModel extends MemoryTableModel {
	
    RealMachineGUI ownerGUI;
    public VirtualMachine vm;
    
	public VirtualMemoryTableModel(RealMachine realMachine, VirtualMachine virtualMachine, RealMachineGUI ownerGUI){
            super(realMachine, ownerGUI);
            memory = virtualMachine.memory;
            this.ownerGUI = ownerGUI;
            this.vm = virtualMachine;
	}
}
