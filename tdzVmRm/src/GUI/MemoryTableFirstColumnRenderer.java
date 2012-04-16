/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import tdzVmRm.RealMachine;

/**
 *
 * @author Domukas
 */
public class MemoryTableFirstColumnRenderer extends DefaultTableCellRenderer {
    RealMachine rm;
    public MemoryTableFirstColumnRenderer(RealMachine rm) {
        super(); 
        this.rm = rm;
    }

public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
        Object columnValue=table.getValueAt(row,column);
        setFont(new Font(this.getFont().getFontName(), Font.BOLD, this.getFont().getSize()));
        if (value != null) setText(value.toString().toUpperCase());
        if(isSelected)
        {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }
        else
        {
            setToolTipText("");
            setBackground(table.getBackground());
            setForeground(table.getForeground());
            //Spalvinam virtualios masinos atminties blokus geltonai
            int blockCount = 0x10;
            if (rm.PLR.getA1() != 0)
                blockCount = rm.PLR.getA1();
            for (int i = 0; i <= blockCount-1; i++){
                if (row == rm.memory.getBlock(rm.PLR.getA2()*0x10 + rm.PLR.getA3()).getWord(i).getIntValue())
                {
                    setBackground(new Color(0xFFFF00));
                    setToolTipText("Virtualios masinos "+(i+1)+" blokas");
                }
            }
            //Spalvinam bendros atminties blokus zaliai
            if (row >= rm.SHARED_MEMORY_BLOCK_OFFSET && row < rm.SHARED_MEMORY_BLOCK_OFFSET+0x10)
            {
                setToolTipText("Bendra atmintis");
                setBackground(new Color(0x77FF77));
            }
            //Spalvinam PLR registro naudojama bloka raudonai
            if (row == rm.PLR.getA2()*0x10+rm.PLR.getA3())
            {
                setToolTipText("Puslapiavimo lentele");
                setBackground(new Color(0xFF0000));
            }
        }
         return this;
     }
}
