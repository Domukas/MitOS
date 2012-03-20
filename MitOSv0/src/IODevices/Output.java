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
        String text = "";
        for(int i=0; i<numberArray.length;i++){
            if(numberArray[i]<=9){
                text = text + numberArray[i];
            }else{
                text = text + (char)numberArray[i];
            }
        }
        outputWindow.setText(text);
    }
    
}
