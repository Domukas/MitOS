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
        byte[] byteArray = new byte[64];
        int maxLen = 64;
        
        if (text.length() < maxLen)
            maxLen = text.length();
        
        for (int i = 0; i < maxLen; i++)
        {
            char symbol = text.charAt(i);
            if (isNumeric(symbol))
                byteArray[i] = (byte)Character.digit(text.charAt(i), 10);
            else
                byteArray[i] = (byte)symbol;
        }
        return byteArrayToIntArry(byteArray);
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
    
    private int[] byteArrayToIntArry(byte[] buf)
    {
        int[] newInt = new int [16];
        byte[] bytebuf;
        
        for (int i = 0; i < 8; i++)
        {
            bytebuf = new byte[4];
            int z = 0;
            for (int m = i*4; m < i*4 + 4; m++)
            {
             bytebuf[z] = buf[m];
             z++;
            }
            
            for (int n = 3; n >= 0; n--)
            {
                newInt[i] += ((int)bytebuf[3-n]) << 8*n;
            }
        }
        return newInt;  
    }

    
}