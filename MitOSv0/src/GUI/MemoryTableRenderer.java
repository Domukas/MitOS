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
public class MemoryTableRenderer extends DefaultTableCellRenderer {
    public MemoryTableRenderer() { super(); }

public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
        Object columnValue=table.getValueAt(row,column);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, this.getFont().getSize()));
        if (value != null) setText(value.toString());
        if(isSelected)
        {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }
        else
        {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
            //Netuscius langelius spalvinam melynai
            if (!(columnValue.equals("0") || columnValue.equals("\0\0\0\0") || columnValue.equals(0))) setBackground(new Color(0xBBBBFF));
            if (table.getModel() instanceof  VirtualMemoryTableModel)
            {
                if ((row == RealMachine.IC.getValue() / 16)&&(column == RealMachine.IC.getValue() % 16))
                    setBackground(new Color(0xFFFF00));
            } else if (table.getModel() instanceof  MemoryTableModel)
            {
                for (int i = 0; i < 15; i++)
                {
                    if (column == RealMachine.IC.getValue() % 16 && RealMachine.IC.getValue() / 16 == row && row == RealMachine.memory.getBlock(RealMachine.PLR.getA2()*0x10 + RealMachine.PLR.getA3()).getWord(i))
                {
                    setBackground(new Color(0xFFFF00));
                    setToolTipText("Virtualios masinos "+(i+1)+" blokas");
                }
            }
            }
        }
         return this;
     }
}
