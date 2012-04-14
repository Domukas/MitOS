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

    public abstract MemoryBlock getBlock(int index);
    public abstract int getMaxMemoryBlocks();
}
