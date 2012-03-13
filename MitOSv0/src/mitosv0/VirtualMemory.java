/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Domukas
 */
public class VirtualMemory {
    
    private Word[] memory;
    
    public VirtualMemory(int size){
        memory = new Word[size];
    }
    
    public Word getWord(int index)
    {
        return memory[index];
    }
    public void setWord(int index, Word value)
    {
        memory[index] = value;
    }
}
