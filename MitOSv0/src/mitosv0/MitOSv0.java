/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import GUI.RealMachineGUI;
import GUI.VirtualMachineGUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zory
 */
public class MitOSv0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        RealMachine RM = new RealMachine(0x10F);
        RealMachineGUI rmGUI = new RealMachineGUI(RM);
 

    }
}
