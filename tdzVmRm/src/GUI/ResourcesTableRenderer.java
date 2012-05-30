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
import tdzOS.VirtualMachine;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;

/**
 *
 * @author Domukas
 */
public class ResourcesTableRenderer extends DefaultTableCellRenderer {
    public ResourcesTableRenderer() {
        super(); 
    }

public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
        setFont(table.getFont());
        if(isSelected)
        {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        }
        else
        {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
            //Spalvinam virtualios masinos atminties blokus geltonai
            if (row > RealMachine.gui.os.resources.size())
            {
                setBackground(new Color(0x66FF66));
            }
        }
        setText(table.getValueAt(row,column).toString());
         return this;
     }
}
