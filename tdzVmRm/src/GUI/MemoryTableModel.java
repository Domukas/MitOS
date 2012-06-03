/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import tdzVmRm.RealMachine;
import tdzVmRm.MemoryBlock;
import tdzVmRm.Word;
import tdzVmRm.Memory;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Domukas
 */
public class MemoryTableModel extends TableModel {
	
    Memory memory;
    RealMachineGUI ownerGUI;
    
	public MemoryTableModel(RealMachine realMachine, RealMachineGUI ownerGUI){
            memory = realMachine.memory;
            this.ownerGUI = ownerGUI;
	}

	public int getRowCount() {
            return memory.getMaxMemoryBlocks();
	}
        
        public void setValueAt(Object value, int row, int col) {
            String stringValue = (String) value;
            
            memory.getBlock(row).setWord(col-1, new Word(stringValue));
            
            fireTableCellUpdated(row, col);
        }
        
	public Object getValueAt(int rowIndex, int columnIndex) {
            MemoryBlock memoryBlock;
            memoryBlock = memory.getBlock(rowIndex);
            
            if (columnIndex == 0) 
                return Integer.toHexString(rowIndex).toUpperCase();
                else
                return memoryBlock.getWord(columnIndex-1).getValue();
	}
	

}
