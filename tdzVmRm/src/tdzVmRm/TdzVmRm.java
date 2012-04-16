/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

import GUI.RealMachineGUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zory
 */
public class TdzVmRm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        RealMachine RM = new RealMachine(0x65);
        
        RealMachineGUI rmGUI = new RealMachineGUI(RM);
        RM.addGui(rmGUI);
    }
}
