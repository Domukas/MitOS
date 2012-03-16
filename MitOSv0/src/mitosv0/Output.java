/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Zory
 */
public class Output {
    public Output(){
    }
    
    public String send(int[] numberArray){
        String  text="";
        for(int i=0; i<numberArray.length;i++){
            if(numberArray[i]<=9){
                text = text + numberArray[i];
            }else{
                text = text + (char)numberArray[i];
            }
        }
        return text;
    }
    
}
