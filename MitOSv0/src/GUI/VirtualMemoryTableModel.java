/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import mitosv0.MemoryBlock;
import mitosv0.RealMachine;
import mitosv0.VirtualMachine;
import mitosv0.VirtualMemory;

/**
 *
 * @author Domukas
 */
public class VirtualMemoryTableModel extends MemoryTableModel {
	
    RealMachineGUI ownerGUI;
    
	public VirtualMemoryTableModel(RealMachine realMachine, RealMachineGUI ownerGUI){
            super(realMachine, ownerGUI);
            memory = realMachine.VM.memory;
            this.ownerGUI = ownerGUI;
	}
}
