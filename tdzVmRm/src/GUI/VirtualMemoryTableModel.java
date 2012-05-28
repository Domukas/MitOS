/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import tdzOS.ProcessDescriptor;
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
        public int getRowCount()
        {
                int icValue = vm.pd.procesorState.PLR.getA0();
                if (icValue == 0) 
                    return 0x10;
                else
                    return icValue;
        }
}
