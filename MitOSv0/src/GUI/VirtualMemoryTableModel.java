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
	
    VirtualMemory memory;
    RealMachineGUI ownerGUI;
    
	public VirtualMemoryTableModel(RealMachine realMachine, RealMachineGUI ownerGUI){
            super(realMachine, ownerGUI);
            memory = realMachine.VM.memory;
            this.ownerGUI = ownerGUI;
	}

    /*   
    @Override
        public void setValueAt(Object value, int row, int col) {
            String stringValue = (String) value;
            int intValue;
            if (ownerGUI.getTableDataType() == VirtualMachineGUI.TableDataTypes.Int) {
                try {
                  intValue = Integer.parseInt(stringValue); 
                  memory.getBlock(row).setWord(col-1, intValue);
                } catch (NumberFormatException e) {}
            } else if (ownerGUI.getTableDataType() == VirtualMachineGUI.TableDataTypes.Hex) {
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
        
    @Override
	public Object getValueAt(int rowIndex, int columnIndex) {
            MemoryBlock memoryBlock;
            memoryBlock = memory.getBlock(rowIndex);
            if (columnIndex == 0) {
                return Integer.toHexString(rowIndex);
            } else if (ownerGUI.getTableDataType() == VirtualMachineGUI.TableDataTypes.Hex)
            {
                return Integer.toHexString(memoryBlock.getWord(columnIndex-1));
            } else if (ownerGUI.getTableDataType() == VirtualMachineGUI.TableDataTypes.Int)
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
	
*/
}
