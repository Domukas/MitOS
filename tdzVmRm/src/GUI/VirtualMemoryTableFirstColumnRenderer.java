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

/**
 *
 * @author Domukas
 */
public class VirtualMemoryTableFirstColumnRenderer extends DefaultTableCellRenderer {
    VirtualMachine vm;
    public VirtualMemoryTableFirstColumnRenderer(VirtualMachine vm) {
        super(); 
        this.vm = vm;
    }

    @Override
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
         //   for (int i = 0; i < 15; i++){
         //       if (row == vm.memory.getBlock(vm.PLR.getA2()*0x10 + vm.PLR.getA3()).getWord(i))
         //       {
         //           setBackground(new Color(0xFFFF00));
         //           setToolTipText("Virtualios masinos "+(i+1)+" blokas");
         //       }
         //   }
            //Spalvinam bendros atminties blokus zaliai
        //    if (row >= rm.SHARED_MEMORY_BLOCK_OFFSET && row < rm.SHARED_MEMORY_BLOCK_OFFSET+0x10)
       //     {
        //        setToolTipText("Bendra atmintis");
      //          setBackground(new Color(0x77FF77));
      //      }

        }
         return this;
     }
}
