/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import IODevices.Output;
import IODevices.Input;
import GUI.RealMachineGUI;
import java.io.*;
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
    public static final int PLR_MAX_A2 = 0x5;
    public static final int PLR_LAST_A3 = 0x4;
    public static final int PLR_MAX_BLOCK_INDEX = 0x54;
    public static final int SHARED_MEMORY_BLOCK_OFFSET = 0x55;
    private static final int MAX_VIRTUAL_MACHINE_COUNT = 5;
    private static boolean [] freeBlocks;
    private static int freeBlockCount;
    private static int virtualMachineCount;
    
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
        String taskName = "program";
        
        
        memory = new RealMemory(blocks);
        freeBlocks = new boolean[blocks];
        for (int i = 0; i < blocks; i++)
            freeBlocks[i] = true;
        freeBlockCount = blocks;
        virtualMachineCount = 0;
        
        //CreateVirtualMachine("program");

        
    }
    
    public boolean CreateVirtualMachine(String fileName){
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/mitosv0/"+fileName+".mit");
            int requiredMemory = getBlockCount(input);
            
            input = new FileInputStream("src/mitosv0/"+fileName+".mit");
            if ((requiredMemory <= 16) && (requiredMemory > 0)) 
            {
                VirtualMemory virtualMemory = CreateVirtualMachineMemory(requiredMemory);
                if (virtualMemory != null)
                {
                    loadProgram(virtualMemory, input);
                    VM = new VirtualMachine(R1, R2, IC, C, virtualMemory);
                    SI.setValue(0);
                    PI.setValue(0);
                }
            }
            else
              return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    public VirtualMemory CreateVirtualMachineMemory(int requiredBlocks){
        if (virtualMachineCount < MAX_VIRTUAL_MACHINE_COUNT & requiredBlocks < freeBlockCount)
        {
            virtualMachineCount++;
            Random rnd = new Random();
            //Nustatome PLR registro reiksmes skirtas vienai virtualiai masinai
            //A1 - programai skirtas puslapiu skaicius, visada 16
            if (requiredBlocks >= 0x10)
                PLR.setA1((byte) 0);
            else
                PLR.setA1((byte) requiredBlocks);

            if (virtualMachineCount < MAX_VIRTUAL_MACHINE_COUNT-1){
                int newA2, newA3;

                newA2 = rnd.nextInt(PLR_MAX_A2+1);
                if (PLR.getA2()==((byte)PLR_MAX_A2))
                    newA3 = rnd.nextInt(PLR_LAST_A3+1);
                else
                    newA3 = rnd.nextInt(0x10);

                while (!freeBlocks[newA2*0x10+newA3] | (newA2*0x10+newA3 > PLR_MAX_BLOCK_INDEX))
                {
                    newA2 = rnd.nextInt(PLR_MAX_A2+1);
                    if (PLR.getA2()==((byte)PLR_MAX_A2)){
                        newA3 = rnd.nextInt(PLR_LAST_A3+1);
                    }
                    else {
                        newA3 = rnd.nextInt(0x10);
                    }
                }
                PLR.setA3((byte) newA3);
                PLR.setA2((byte) newA2);
                freeBlocks[newA2*0x10+newA3] = false;
                freeBlockCount--;
                //uzpildom lentele, kad rodytu i atsitiktines vietas
                MemoryBlock block = memory.getBlock(PLR.getA2()*0x10+PLR.getA3());

                for (int i = 0; i < requiredBlocks; i++){
                    int blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
                    while (!freeBlocks[blockIndex]){
                        blockIndex = rnd.nextInt(PLR_MAX_BLOCK_INDEX);
                    }
                    block.setWord(i,new Word((short) blockIndex));
                    freeBlocks[blockIndex] = false;
                    freeBlockCount--;
                }
                //Penktoji masina is penkiu, nebeduodan random, nes biski gali uztrukci
            } else {
                int i = 0;
                //randam pirma tuscia, talpinam ten lentele
                while (!freeBlocks[i]){
                    i++;
                }
                PLR.setA2((byte) (i / 0x10));
                PLR.setA3((byte) (i % 0x10));
                freeBlocks[i] = false;
                freeBlockCount--;
                MemoryBlock block = memory.getBlock(PLR.getA2()*0x10+PLR.getA3());
                for (int j = 0; j < requiredBlocks; j ++){
                    while(!freeBlocks[i])
                    {
                        i++;
                    }
                    block.setWord(j,new Word(i));
                    freeBlocks[i] = false;
                    freeBlockCount--;
                }
            }

            return new VirtualMemory(PLR, memory);
        }
        else
        {
            RealMachine.gui.showMessage("Too many virtual machines!");
            return null;
        }
    }
    public void loadProgram(VirtualMemory memory, FileInputStream input)
    {
        try {
            int i = 0;
            byte[] buffer = new byte[4];
            boolean loadOK = true;
            
            //Masinai duotu bloku (ir tuo paciu zodziu) skaicius.
            int maxWordCount = 0x100;
            if (PLR.getA1() != 0)
                maxWordCount = PLR.getA1()*0x10;

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            
            boolean codeBegins = false;
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
                line = line.replaceAll(" ", "");
                if (codeBegins && !line.equals("@CodeEnd") && (line.length()!=0))
                {
                    if (i < maxWordCount-1)
                    {
                        memory.setWord(i, new Word(line));
                        i++;
                    } 
                    else 
                    {
                        //Pykstam nes daugiau programai nebeduota vietos!
                        System.out.println("Nėra laisvos vietos");
                        loadOK = false;
                        break; 
                    }   
                    
                }
                
                if (line.equals("@Code"))
                    codeBegins = true;
            }
           
            if (loadOK)
            {
                //Nustatomas programos dydis blokais
                RealMachine.PLR.setA0((byte) (i/16+1));
                RealMachine.IC.setValue(0);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MitOSv0.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getBlockCount(FileInputStream input)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        
        String line;
        try {
            line = br.readLine();
            line = line.replaceAll(" ", "");
            if (line.startsWith("@Memory"))
            {
                line = line.substring(7, line.length());
                return Integer.parseInt(line, 16);
            }
        } catch (IOException ex) {
            Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public void DumpMemory()
    {
        for (int i = 0; i < memory.getMaxMemoryBlocks(); i++)
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
