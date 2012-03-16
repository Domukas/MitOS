/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mitosv0.MemoryBlock;
import mitosv0.RealMemory;

/**
 *
 * @author Tomas
 */
public class MemoryTable {
    
    private DefaultTableModel tableModel;
    String[] colNames;
    Object [][] data;
    
    RealMemory memory;
    JTable table;
    
    MemoryTable(RealMemory memory, JTable table)
    {
        this.memory = memory;
        this.table = table;
        initialize();
    }
    
    private void initialize()
    {
        int wordCount = memory.MAX_MEMORY_BLOCKS * 16;
            
        colNames = new String[]{"Adress", "Data", "CharData"};
        data = new Object[wordCount][3];
        
        for (int i = 0; i < wordCount; i++)
        {
            data[i][0] = Integer.toHexString(i).toUpperCase();
        }
        
        updateTableData();
        
        tableModel = new DefaultTableModel(data, colNames);
        table.setModel(tableModel);   
    }
    
    public void updateMemoryTable()
    {
        updateTableData(); 
        
        tableModel = new DefaultTableModel(data, colNames);
        table.setModel(tableModel);
    }
    
    private void updateTableData()
    {
        for (int i = 0; i < memory.MAX_MEMORY_BLOCKS; i++)
        {
            MemoryBlock b = memory.getBlock(i);
            for (int n = 0; n < 16; n++)
            {
                data[i*16+n][1] = Integer.toHexString(b.getWord(n)).toUpperCase();
                data[i*16+n][2] = (char)b.getWord(n);
            }
        }
    }
}
