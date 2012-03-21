/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import mitosv0.Memory;
import mitosv0.MemoryBlock;
import mitosv0.RealMachine;
import mitosv0.RealMemory;

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
            return memory.MAX_MEMORY_BLOCKS;
	}
        
        public void setValueAt(Object value, int row, int col) {
            String stringValue = (String) value;
            int intValue;
            if (ownerGUI.getTableDataType() == RealMachineGUI.TableDataTypes.Int) {
                try {
                  intValue = Integer.parseInt(stringValue); 
                  memory.getBlock(row).setWord(col-1, intValue);
                } catch (NumberFormatException e) {}
            } else if (ownerGUI.getTableDataType() == RealMachineGUI.TableDataTypes.Hex) {
                try {
                    intValue = Integer.parseInt(stringValue, 16);
                    memory.getBlock(row).setWord(col-1, intValue);
                } catch (NumberFormatException e) {}
            } else {
                intValue = 0;
                for (int i = 0; stringValue.length()-1 > 0 && i < 4; i++) {
                    intValue += (((int) (stringValue.charAt(stringValue.length()-i-1) & 0xff)) << i*8);
                    stringValue.substring(stringValue.length() - i);
                }
                memory.getBlock(row).setWord(col-1, intValue);
            }
            fireTableCellUpdated(row, col);
        }
        
	public Object getValueAt(int rowIndex, int columnIndex) {
            MemoryBlock memoryBlock;
            memoryBlock = memory.getBlock(rowIndex);
            if (columnIndex == 0) {
                return Integer.toHexString(rowIndex);
            } else if (ownerGUI.getTableDataType() == RealMachineGUI.TableDataTypes.Hex)
            {
                return Integer.toHexString(memoryBlock.getWord(columnIndex-1));
            } else if (ownerGUI.getTableDataType() == RealMachineGUI.TableDataTypes.Int)
            {
                return memoryBlock.getWord(columnIndex-1);
            } else 
            {
                String str = "";
                int value = memoryBlock.getWord(columnIndex-1);
                str = (char)(value % 0x100) + str; 
                value /= 0x100; 
                str = (char)(value % 0x100) + str; 
                value /= 0x100; 
                str = (char)(value % 0x100) + str; 
                value /= 0x100; 
                str = (char)(value % 0x100) + str; 
                return str;
            }
	}
	

}
