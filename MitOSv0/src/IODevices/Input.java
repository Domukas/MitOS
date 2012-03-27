/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IODevices;

import mitosv0.TypeConversion;

/**
 *
 * @author Zory
 */
public class Input {
    
    InputWindow inputWindow;
    
    public Input()
    {
       inputWindow =  new InputWindow();
       inputWindow.setVisible(true);
    }
    public String get()
    {
        String text = inputWindow.getText();

       
        if (text.length() > 64)
            text = text.substring(0, 64);
        
        return text;
   }  
    
}