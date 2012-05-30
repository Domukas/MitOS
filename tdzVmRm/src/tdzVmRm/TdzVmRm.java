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
import tdzOS.OS;

/**
 *
 * @author Zory
 */
public class TdzVmRm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RealMachine RM = new RealMachine(0x65);
        OS os = new OS(RM);
        Thread tGUI = new Thread(new RealMachineGUI(RM, os));
        Thread t = new Thread(os);
        tGUI.start();
        t.start();
    }
}
