/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

/**
 *
 * @author Zory
 */
public abstract class Memory {

    public abstract MemoryBlock getBlock(int index);
    public abstract int getMaxMemoryBlocks();
}
