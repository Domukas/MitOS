/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

/**
 *
 * @author Domukas
 */
public class RealMemory extends Memory{

    private MemoryBlock[] memory;
    private final int MAX_MEMORY_BLOCKS;
    
    RealMemory(int blocks) {
        MAX_MEMORY_BLOCKS = blocks;
        memory = new MemoryBlock[blocks];
        for (int i = 0; i < blocks; i++)
            memory[i] = new MemoryBlock();
    }
    
    @Override
    public MemoryBlock getBlock(int index)
    {
        return memory[index];
    }
    
    public int getMaxMemoryBlocks(){
        return MAX_MEMORY_BLOCKS;
    }
    
}
