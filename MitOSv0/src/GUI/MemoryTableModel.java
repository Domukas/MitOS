/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.table.AbstractTableModel;
import mitosv0.MemoryBlock;
import mitosv0.RealMachine;
import mitosv0.RealMemory;

/**
 *
 * @author Domukas
 */
public class MemoryTableModel extends AbstractTableModel {
	
    RealMemory memory;
	
    private String[] columnNames = {"Addr",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F"};
    
	public MemoryTableModel(RealMachine realMachine){
		memory = realMachine.memory;
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		
		return memory.MAX_MEMORY_BLOCKS;
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
            MemoryBlock memoryBlock;
            memoryBlock = memory.getBlock(rowIndex);
            switch (columnIndex){
                case 0: return rowIndex;
                default: return Integer.toHexString(memoryBlock.getWord(columnIndex-1));
            }
	}
	

}
