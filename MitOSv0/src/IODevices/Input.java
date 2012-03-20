/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IODevices;

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
    public int[] get()
    {
        String text = inputWindow.getText();
        int[] numberArray = new int[16];
        int maxLen = 16;
        
        if (text.length() < maxLen)
            maxLen = text.length();
        
        for (int i = 0; i < maxLen; i++)
        {
            char symbol = text.charAt(i);
            if (isNumeric(symbol))
                numberArray[i] = Character.digit(text.charAt(i), 10);
            else
                numberArray[i] = (int)symbol;  
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