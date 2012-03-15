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
    
    private int[] memory;
    
    public VirtualMemory(int size){
        memory = new int[size];
    }
    
    public int getWord(int index)
    {
        return memory[index];
    }
    public void setWord(int index, int value)
    {
        memory[index] = value;
    }
}
