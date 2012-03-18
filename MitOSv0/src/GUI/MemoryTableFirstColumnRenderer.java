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
import mitosv0.RealMachine;

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
            for (int i = 0; i < 15; i++){
                if (row == rm.memory.getBlock(rm.PLR.getA2()*0x10 + rm.PLR.getA3()).getWord(i))
                {
                    setBackground(new Color(0xFFFF00));
                    setToolTipText("Virtualios masinos atmintis");
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
