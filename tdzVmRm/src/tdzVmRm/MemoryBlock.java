/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

/**
 *
 * @author Domukas
 */
public class MemoryBlock {
    private Word [] data;
    public final int MEMORY_BLOCK_SIZE = 16;
    
    MemoryBlock(){
        data = new Word[MEMORY_BLOCK_SIZE];
        
        for (int i = 0; i < data.length; i++)
            data[i] = new Word();
    }
    
    public Word getWord(int index){
        return data[index];
    }
    public void setWord(int index, Word value){
        data[index] = value;
    }
    public int getBlockSize(){
        return MEMORY_BLOCK_SIZE;
    }
}
