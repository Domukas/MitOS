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
            char symbol = input.charAt(i);
            if (isNumeric(symbol)){
                block[i] = Character.digit(input.charAt(i), 10);
            }else{
                block[i] = (int)symbol;
            }  
        }
        return block;
   }  
    
    private boolean isNumeric(char symbol){
        try{
            Character.getNumericValue(symbol);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    
}