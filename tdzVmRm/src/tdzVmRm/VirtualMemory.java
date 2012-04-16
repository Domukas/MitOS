/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

import tdzVmRm.registers.PLRRegister;

/**
 *
 * @author Domukas
 */
public class VirtualMemory extends Memory{
    
    private RealMemory memory;
    private PLRRegister PLR;
    
    public VirtualMemory(PLRRegister PLR, RealMemory memory){
        this.memory = memory;
        this.PLR = PLR;
    }
    
    public Word getWord(int index)
    {
        return getBlock(index / 0x10).getWord(index % 0x10);
    }
    public void setWord(int index, Word value)
    {
        getBlock(index / 0x10).setWord(index % 0x10, value);
    }
    
    public Word getSharedMemoryWord(int index)
    {
        return getSharedMemoryBlock((index / 0x10)).getWord(index % 0x10);
    }
    
    public void setSharedMemoryWord(int index, Word value)
    {
        getSharedMemoryBlock((index / 0x10)).setWord(index % 0x10, value);
    }

    public MemoryBlock getBlock(int index) {
        return memory.getBlock(memory.getBlock(PLR.getA2()*0x10 + PLR.getA3()).getWord(index).getIntValue());
    }
    
    public MemoryBlock getSharedMemoryBlock(int index)
    {
        return memory.getBlock(RealMachine.SHARED_MEMORY_BLOCK_OFFSET + index);
    }

    public int getMaxMemoryBlocks() {
        return (PLR.getA1() != 0) ? PLR.getA1() : 0x10;
    }
}
