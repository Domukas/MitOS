/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IODevices;

import tdzVmRm.TypeConversion;

/**
 *
 * @author Zory
 */
public class Output {
    OutputWindow outputWindow;
    
    public Output()
    {
        outputWindow = new OutputWindow();
        outputWindow.setVisible(true);
    }
    
    public void send(String text)
    {
        outputWindow.setText(text);
    }
    
}
