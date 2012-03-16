/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Zory
 */
public class Input {
    public Input(){
        
    }
    public int[] send(String input){ //kiekviena simboli kol kas talpina i atskira zodi
        int[] block = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            block[i] = Character.digit(input.charAt(i), 10);  
        }
        return block;
    }
    
}