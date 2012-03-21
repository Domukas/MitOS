/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IODevices;

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
    
    public void send(int[] numberArray){
        byte[] data = intArrayToByteArray(numberArray);
        String text = "";
        for(int i=0; i<data.length; i++){
            if(data[i]<=9){
                text = text + data[i];
            }else{
                text = text + (char)data[i];
            }
        }
        outputWindow.setText(text);
    }
    
    private byte[] intArrayToByteArray(int[] array)
    {
        byte[] rez = new byte[64];
        int m = 0;
        
        for (int i = 0; i < 16; i++)
        {
            byte[] temp = intToByteArray(array[i]);
            for (int n = 0; n < 4; n++)
            {
                rez[m] = temp[n];
                m++;
            }
        }
        return rez;
    }
    
    private byte[] intToByteArray(int value)
    {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
    
}
