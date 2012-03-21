/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Domukas
 */
public class RealMemory extends Memory{

    private MemoryBlock[] memory;
    
    RealMemory(int blocks) {
        super(blocks);
        memory = new MemoryBlock[blocks];
        for (int i = 0; i < blocks; i++)
            memory[i] = new MemoryBlock();
    }
    
    @Override
    public MemoryBlock getBlock(int index)
    {
        return memory[index];
    }
    
}
