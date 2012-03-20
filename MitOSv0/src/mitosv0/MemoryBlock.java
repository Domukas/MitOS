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
    public final int MEMORY_BLOCK_SIZE = 16;
    
    MemoryBlock(){
        data = new int[MEMORY_BLOCK_SIZE];
    }
    
    public int getWord(int index){
        return data[index];
    }
    public void setWord(int index, int value){
        data[index] = value;
    }
    public int getBlockSize(){
        return MEMORY_BLOCK_SIZE;
    }
}
