/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import IODevices.Output;
import IODevices.Input;
import GUI.RealMachineGUI;
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
    
    public static RealMachineGUI gui;
    
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
    public static final byte PLR_MAX_A2 = 0x5;
    public static final byte PLR_LAST_A3 = 0x4;
    public static final int PLR_MAX_BLOCK_INDEX = 0x54;
    public static final int SHARED_MEMORY_BLOCK_OFFSET = 0x55;
    
    public static RealMemory memory;
    
    public RealMachine(int blocks)
    {
        PLR = new PLRRegister();
        R1 = new DataRegister();
        R2 = new DataRegister();
        IC = new ICRegister();
        C = new CRegister();
        S = new SemaphoreRegister();
        timer = new TimerRegister(50); 
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
 
        Random rnd = new Random();
        //Nustatome PLR registro reiksmes skirtas vienai virtualiai masinai
        PLR.setA0((byte) 0x00);
        PLR.setA1((byte) 0x00);
        PLR.setA2((byte) rnd.nextInt(PLR_MAX_A2+1));
        if (PLR.getA2()==PLR_MAX_A2){
            PLR.setA3((byte) rnd.nextInt(PLR_LAST_A3+1));
        } else
            PLR.setA3((byte) rnd.nextInt(0x10));
        
        //uzpildom lentele, kad rodytu i atsitiktines vietas
        MemoryBlock block = memory.getBlock(PLR.getA2()*0x10+PLR.getA3());
        
        for (int i = 0; i < 16; i++){
            int blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
            for (int j = 0; j <= i; j++){
                if (blockIndex == block.getWord(j).getIntValue()){
                    blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
                    j = 0;
                }
            }
            block.setWord(i,new Word((short)rnd.nextInt(PLR_MAX_BLOCK_INDEX)));
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
                memory.setWord(i, new Word(TypeConversion.byteArrayToString(buffer)));
                i++;
            }
            
            if (i >= 256)
            {
                System.out.println("Nėra laisvos vietos");
            }
            else
            {
                RealMachine.IC.setValue(0);
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
    
        
    public void addGui(RealMachineGUI g)
    {
        gui = g;
    }

    
    
}
