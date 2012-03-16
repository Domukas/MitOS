/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import mitosv0.registers.PLRRegister;

/**
 *
 * @author Domukas
 */
public class VirtualMemory {
    
    private RealMemory memory;
    private PLRRegister PLR;
    
    public VirtualMemory(PLRRegister PLR, RealMemory memory){
        this.memory = memory;
        this.PLR = PLR;
    }
    
    public int getWord(int index)
    {
        return getBlock(index / 0x10).getWord(index % 0x10);
    }
    public void setWord(int index, int value)
    {
        getBlock(index / 0x10).setWord(index % 0x10, value);
    }

    private MemoryBlock getBlock(int index) {
        return memory.getBlock(memory.getBlock(PLR.getA2()*0x10 + PLR.getA3()).getWord(index));
    }
}
