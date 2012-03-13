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
    
    private byte[] memory;
    
    public VirtualMemory(int size){
        memory = new byte [size];
    }
    
    public byte getWord(int index)
    {
        return memory[index];
    }
    public void setWord(int index, byte value)
    {
        memory[index] = value;
    }
}
