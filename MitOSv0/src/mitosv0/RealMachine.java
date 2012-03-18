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
    
    public static Speaker speakers;
    public static Input in;
    public static Output out;
    
    public static VirtualMachine VM;
    
    public static final int PLR_BLOCK_MEMORY_OFFSET = 0x100;
    public static final byte PLR_MIN_A2 = 0x10;
    public static final int PLR_MAX_BLOCK_INDEX = 0xEF;
    public static final int SHARED_MEMORY_BLOCK_OFFSET = 0xF0;
    
    public static RealMemory memory;
    
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
        speakers = new Speaker();
        in = new Input();
        out = new Output();
        
        memory = new RealMemory(blocks);
        CreateVirtualMachine();
        
    }
    
    private void CreateVirtualMachine(){
 
        PLR.setA0((byte) 0x00);
        PLR.setA1((byte) 0x00);
        PLR.setA2(PLR_MIN_A2);
        PLR.setA3((byte) 0x00);
        
        //uzpildom lentele, kad rodytu i 0 1 2 ... 15 pirmuju bloku.
        MemoryBlock block = memory.getBlock(PLR_MIN_A2*0x10);
        for (int i = 15; i > 0; i--)
        {
            block.setWord(i, i);
        }
        //DumpMemory();

        VM = new VirtualMachine(R1, R2, IC, C, new VirtualMemory(PLR, memory));

        /*
        //Nu cia tipo programa sitoj vietoj skaitysim gal, ania?
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
    public void DumpMemory()
    {
        for (int i = 0; i < memory.MAX_MEMORY_BLOCKS; i++)
        {
            System.out.print(i+": ");
            for (int j = 0; j < 16; j++)
            {
                System.out.print(memory.getBlock(i).getWord(j) + " ");
            }
            System.out.println();
        }
    }
    
    
}
