/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IODevices;

import tdzVmRm.RealMachine;

/**
 *
 * @author Zory
 */
public class Input {
    
    public String get()
    {
        String text = RealMachine.gui.showInputMessageBox("Input");
       
        if (text.length() > 64)
            text = text.substring(0, 64);
        
        return text;
   }  
    
}