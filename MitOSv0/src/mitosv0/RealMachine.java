/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import IODevices.Output;
import IODevices.Input;
import GUI.RealMachineGUI;
import GUI.VirtualMachineGUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
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
 
        
        //Nustatome PLR registro reiksmes skirtas vienai virtualiai masinai
        PLR.setA0((byte) 0x00);
        PLR.setA1((byte) 0x00);
        PLR.setA2(PLR_MIN_A2);
        PLR.setA3((byte) 0x00);
        
        //uzpildom lentele, kad rodytu i atsitiktines vietas
        MemoryBlock block = memory.getBlock(PLR_MIN_A2*0x10);
        Random rnd = new Random();
        for (int i = 0; i < 16; i++){
            int blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
            for (int j = 0; j < i; j++){
                if (blockIndex == block.getWord(j)){
                    blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
                    j = 0;
                }
            }
            block.setWord(i,rnd.nextInt(PLR_MAX_BLOCK_INDEX));
        }

        VirtualMemory virtualMemory = new VirtualMemory(PLR, memory);
        
        loadProgram(virtualMemory, "src/mitosv0/program.mit");
        
        VM = new VirtualMachine(R1, R2, IC, C, virtualMemory);
    }
    public void loadProgram(VirtualMemory memory, String fileName)
    {
        try {
            FileInputStream input = new FileInputStream(fileName);
            int i = 0;
            byte[] buffer = new byte[4];
            
            while ((input.read(buffer)) != -1){
                memory.setWord(i, byteBufferToInt(buffer));
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(MitOSv0.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
        
    private int byteBufferToInt(byte[] buf)
    {
        int newInt = 0;
        
        for (int i = 3; i >= 0; i--)
        {
            newInt += ((int)buf[3-i]) << 8*i;
        }
        return newInt;  
    }
    
    
}
