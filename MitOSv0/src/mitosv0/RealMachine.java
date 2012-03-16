/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mitosv0.registers.TimerRegister;
import mitosv0.registers.SemaphoreRegister;
import mitosv0.registers.PLRRegister;
import mitosv0.registers.ModeRegister;
import mitosv0.registers.INTRegister;
import mitosv0.registers.ICRegister;
import mitosv0.registers.DataRegister;
import mitosv0.registers.CRegister;
import mitosv0.registers.CHRegister;

/**
 *
 * @author Tomas
 */
public class RealMachine {
    public static PLRRegister PLR;
    public static DataRegister R1, R2;
    public static ICRegister IC;
    public static CRegister C;
    public static SemaphoreRegister S;
    public static TimerRegister timer; //Timer ir mode gal kazkaip kitaip pervadint
    public static ModeRegister mode;
    public static INTRegister PI, SI;
    public static CHRegister CH1, CH2, CH3, CH4;
    public static VirtualMachine VM;
    private static final int PLR_BLOCK_MEMORY_OFFSET = 0x100;
    private static final byte PLR_MIN_A2 = 0x10;
    private static final int PLR_MAX_BLOCK_INDEX = 0xEF;
    private RealMemory memory;
    public RealMachine(int blocks)
    {
        PLR = new PLRRegister();
        R1 = new DataRegister();
        R2 = new DataRegister();
        IC = new ICRegister();
        C = new CRegister();
        S = new SemaphoreRegister();
        timer = new TimerRegister(3); //Skaicius???
        mode  = new ModeRegister();
        PI = new INTRegister();
        SI = new INTRegister();
        CH1 = new CHRegister();
        CH2 = new CHRegister();
        CH3 = new CHRegister();
        CH4 = new CHRegister();
        
        memory = new RealMemory(blocks);
        CreateVirtualMachine();
        
    }
    
    private void CreateVirtualMachine(){
        
        PLR.getValue();
        PLR.setA2(PLR_MIN_A2);
        PLR.setA3((byte) 0x00);
        
        //Irasom belenkokiu skaiciu i atminti
        for (int i = 0; i < PLR_MAX_BLOCK_INDEX*0x10; i++)
        {
            MemoryBlock block = memory.getBlock(i/0x10);
            block.setWord(i%0x10, i);
        }
        VM = new VirtualMachine(R1, R2, IC, C, new VirtualMemory(PLR, memory));
        
        /*
        try {
            FileInputStream input = new FileInputStream("src/mitosv0/program1.mit");
            int c;
            while ((c = input.read()) != -1){
                System.out.println(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(MitOSv0.class.getName()).log(Level.SEVERE, null, ex);
        }
        * 
        */
    }
    
}
