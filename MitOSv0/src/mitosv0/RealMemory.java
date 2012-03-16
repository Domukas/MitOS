/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Domukas
 */
public class RealMemory {

    private MemoryBlock[] memory;
    public final int MAX_MEMORY_BLOCKS;
    
    RealMemory(int blocks) {
        MAX_MEMORY_BLOCKS = blocks;
        memory = new MemoryBlock[blocks];
        for (int i = 0; i < blocks; i++)
            memory[i] = new MemoryBlock();
    }
    
    public MemoryBlock getBlock(int index)
    {
        return memory[index];
    }
    
}
