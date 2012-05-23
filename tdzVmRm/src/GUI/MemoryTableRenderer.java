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
import tdzOS.OS;
import tdzOS.OS.ProcessState;
import tdzOS.ProcessDescriptor;
import tdzOS.VirtualMachine;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;

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
            //Spalvinam IC reiksme
            if (table.getModel() instanceof  VirtualMemoryTableModel)
            {
                ProcessDescriptor pd = ((VirtualMemoryTableModel) table.getModel()).vm.pd;
                int icValue = pd.procesorState.IC.getValue();
                if (pd.state == ProcessState.Run)
                    icValue = pd.processor.IC.getValue();
                if ((row ==  icValue / 16)&&(column-1 == icValue % 16))
                    setBackground(new Color(0xFFFF00));   
            } 
            /* Nebespalvinam RM'e IC reiksmes, ju per daug
            else if (table.getModel() instanceof  MemoryTableModel)
            {
                for (Processor p:RealMachine.proc)
                {
                    if ((column-1 == (p.IC.getValue() % 16)) &&
                        (row == RealMachine.memory.getBlock(p.PLR.getA2()*0x10 + p.PLR.getA3()).getWord(p.IC.getValue() / 16).getIntValue()))
                    {
                        setBackground(new Color(0xFFFF00));
                    }
                }
            }*/
        }
         return this;
     }
}
