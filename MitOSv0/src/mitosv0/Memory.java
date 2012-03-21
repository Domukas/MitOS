/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Zory
 */
public abstract class Memory {
    public final int MAX_MEMORY_BLOCKS;
        public Memory(int maxBlocks){
        MAX_MEMORY_BLOCKS = maxBlocks;
    }
    public abstract MemoryBlock getBlock(int index);
    
}
