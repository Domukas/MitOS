/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

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
        
        
        VirtualMachine VM = new VirtualMachine();
        
        try {
            FileInputStream input = new FileInputStream("src/mitosv0/program1.mit");
            int c;
            while ((c = input.read()) != -1){
                System.out.println(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(MitOSv0.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
