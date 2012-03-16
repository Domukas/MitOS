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
    public int[] send(String text){
        int[] numberArray = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (isNumeric(symbol)){
                numberArray[i] = Character.digit(text.charAt(i), 10);
            }else{
                numberArray[i] = (int)symbol;
            }  
        }
        return numberArray;
   }  
    
    private boolean isNumeric(char symbol){
        try{
            String number="";
            number = Character.toString(symbol);
            Integer.parseInt(number);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    
}