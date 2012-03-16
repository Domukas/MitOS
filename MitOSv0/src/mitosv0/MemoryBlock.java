/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Domukas
 */
public class MemoryBlock {
    private int [] data;
    
    MemoryBlock(){
        data = new int[16];
    }
    
    public int getWord(int index){
        return data[index];
    }
    public void setWord(int index, int value){
        data[index] = value;
    }
}
