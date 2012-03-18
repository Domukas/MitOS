/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Domukas
 */
public class MemoryTableRenderer extends DefaultTableCellRenderer {
    public MemoryTableRenderer() { super(); }

public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
        Object columnValue=table.getValueAt(row,column);
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
            if (columnValue.equals("0") || columnValue.equals("\0\0\0\0")) setBackground(new Color(0xBFFFFF));
        }
         return this;
     }
}
