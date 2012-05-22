/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import tdzVmRm.registers.ICRegister;
import tdzVmRm.registers.DataRegister;
import tdzVmRm.registers.CRegister;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * Virtualus procesorius turi šiuos registrus:
Duomenų registrai:
R1 – 4 baitų bendro naudojimo registras;
R2 – 4 baitų bendro naudojimo registras;
Nuorodų registras:
IC – 2 baitų virtualios mašinos programos skaitiklis;
Loginis registras:
C – 1 baito požymio registras. 
Formuojamas komandų C1 ir C2. Kitos komandos reikšmės nekeičia.
Pirmasis bitas: zero flag (ZF):
ZF = 1, jei registro ir reikšmės, esančios adresu xx skirtumas lygus 0.
ZF = 0 – priešingu atveju.
Antrasis bitas: sign flag (SF)
SF = 1, jei atlikus aritmetinę operaciją su registro ir atminties xx reikšmėmis rezultato pirmasis bitas yra 1.
SF = 0 – priešingu atveju.
Trečiasis bitas: overflow flag (OF)
OF = 0, jei registro ir reikšmės, esančios adresu xx ženklo bitai yra vienodi, o jų skirtumo ženklo bitas skiriasi.
OF = 1 – priešingu atveju.

 * 
 * 
 * @author Tomas
 */


public class VirtualMachine {

    static final int WAIT_TIME = 3000;
    
    public DataRegister R1, R2;
    public ICRegister IC;
    public CRegister C;
    
    public VirtualMemory memory;
    
    public VirtualMachine(DataRegister R1, DataRegister R2, ICRegister IC, CRegister C, VirtualMemory memory)
    {
        this.R1 = R1;
        this.R2 = R2;
        this.IC = IC;
        this.C = C;
        this.memory = memory;   
    }
    
    public void run()
    {
        do
        {
            step();
            //RealMachine.gui.updateAll();
            //pause(500);
        } while ((RealMachine.proc[0].SI.getValue() != 5) && (RealMachine.proc[0].PI.getValue() == 0));
    }
    
    public void step()
    {    
        if ((RealMachine.proc[0].SI.getValue() != 5) && (RealMachine.proc[0].PI.getValue() == 0))
        {
            Word currentCommand = getCurrentCommand();
            goToNextCommand();
            processCommand(currentCommand);          
        }
        else
            RealMachine.gui.showMessage("Program halted");
    }
    
    
    public Word getCurrentCommand()
    {
        return memory.getWord(IC.getValue());
    }
    
    public void goToNextCommand()
    {
        int val = IC.getValue();
        val++;
        IC.setValue(val);
        
        RealMachine.proc[0].timer.timePass(1);
    }
       
    private void processCommand(Word currentWord)
    {
        String OPC = "";
        int xx;
        
        OPC = encodeBytes3and2(currentWord);
        xx = XXAdress(currentWord);
         
        switch(OPC)
        {
            case "A1":
            {
                A1(xx);
                break;
            }
            case "A2":
            {
                A2(xx); 
                break;
            }
            case "B1":
            {
                B1(xx);
                break;
            }
            case "B2":
            {
                B2(xx); 
                break;
            }
            case "C1":
            {
                C1(xx); 
                break;
            }
            case "C2":
            {
                C2(xx);  
                break;
            }
            case "L1":
            {             
                L1(xx);
                break;
            }
            case "L2":
            {
                L2(xx);
                break;
            }
            case "S1":
            {
                S1(xx);
                break;
            }
            case "S2":
            {
                S2(xx);
                break;
            }
            case "X1":
            {
                X1(xx);
                break;
            }
            case "X2":
            {
                X2(xx);
                break;
            }
            case "Z1":
            {
                Z1(xx);
                break;
            }
            case "Z2":
            {
                Z2(xx);
                break;
            }
            case "MU":
            {
                MU(xx);
                break;
            }
            case "DI":
            {
                DI(xx);
                break;
            }
            case "XR":
            {
                XR(xx);
                break;
            }
            case "AN":
            {
                AN(xx);
                break;
            }
            case "OR":
            {
                OR(xx);
                break;
            }
            case "JP":
            {
                JP(xx);
                break;
            }
            case "JE":
            {
                JE(xx);
                break;
            }
            case "JG":
            {
                JG(xx);
                break;
            }
            case "JL":
            {
                JL(xx);
                break;
            }
            case "JX":
            {
                JX(xx);
                break;
            }
            case "LO":
            {
                LO(xx);
                break;
            }
            default:
            {
                OPC += encodeByte1(currentWord);
                int x = XAdress(currentWord);
                switch (OPC)
                {
                    case "LCK":
                    {
                        LCK(x);
                        break;
                    }
                    case "ULC":
                    {
                        ULC(x);
                        break;
                    }
                    case "DGT":
                    {
                        DGT(x);
                        break;
                    }
                    case "DPT":
                    {
                        DPT(x);
                        break;
                    }
                    default:
                    {
                        OPC += encodeByte0(currentWord);
                        switch(OPC)
                        {
                            case "GGR1":
                            {
                                GGR1();
                                break;
                            }
                            case "GGR2":
                            {
                                GGR2();
                                break;
                            }
                            case "GLR1":
                            {
                                GLR1();
                                break;
                            }
                            case "GLR2":
                            {
                                GLR2();
                                break;
                            }
                            case "GNR1":
                            {
                                GNR1();
                                break;
                            }
                            case "GNR2":
                            {
                                GNR2();
                                break;
                            }
                            case "HALT":
                            {
                                HALT();
                                break;
                            }
                            default:
                            {
                                //Opkodas neegzistuoja
                                RealMachine.proc[0].PI.setValue(2);
                                RealMachine.proc[0].mode.SetSupervisor();
                                RealMachine.gui.showMessage("Opcode: " + OPC + " does not exist");

                            }
                        }
                    }
                } 
            }   
        }

        System.out.println(" -- > Code:" + OPC);  
    }   
        
    //Aritmetinės komandos
    public void A1(int xx)
    {
        Word oldValue = R1.getValue();
        R1.setValue(new Word ((oldValue.getIntValue() + getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }
    
    public void A2(int xx)
    {
        Word oldValue = R2.getValue();
        R2.setValue(new Word ((oldValue.getIntValue() + getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R2.getValue());
    }
    
    public void B1(int xx)
    {        
        Word oldValue = R1.getValue();
        R1.setValue(new Word ((oldValue.getIntValue() - getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }   

    public void B2(int xx)
    {
        Word oldValue = R2.getValue();
        R2.setValue(new Word ((oldValue.getIntValue() - getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R2.getValue());
    }   
    
    public void MU(int xx)
    {
        Word oldValue = R1.getValue();
        R1.setValue(new Word ((oldValue.getIntValue() * getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }   
    
    public void DI(int xx)
    {
        R2.setValue(new Word ((R1.getValue().getIntValue() % getWord(xx).getIntValue())));
        
        Word oldValue = R1.getValue();
        R1.setValue(new Word ((oldValue.getIntValue() / getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }
    
    //Loginės komandos
    public void XR (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() ^ getWord(xx).getIntValue())));
        setZfSf(R1.getValue());
    }

    public void AN (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() & getWord(xx).getIntValue())));
        setZfSf(R1.getValue());
    }
    
    public void OR (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() | getWord(xx).getIntValue())));
        setZfSf(R1.getValue());
    }    
    
    //Palyginimo komandos
    public void C1 (int xx)
    {
        Word oldValue = R1.getValue();
        Word tempValue = new Word((oldValue.getIntValue() - getWord(xx).getIntValue()));        
        arithmeticFlagSet(oldValue, getWord(xx), tempValue);
    }
    
    public void C2 (int xx)
    {
        Word oldValue = R2.getValue();
        Word tempValue = new Word ((oldValue.getIntValue() - getWord(xx).getIntValue()));        
        arithmeticFlagSet(oldValue, getWord(xx), tempValue);
    }
    
    //Darbo su duomenimis komandos
    
    public void L1 (int xx)
    {
        R1.setValue(getWord(xx));
    }

    public void L2 (int xx)
    {
        R2.setValue(getWord(xx));
    }
    
    public void S1 (int xx)
    {
        setWord(xx, R1.getValue());
    }
    
    public void S2 (int xx)
    {
        setWord(xx, R2.getValue());
    }
    
    public void LCK (int x)
    {
        if (RealMachine.proc[0].S.isBitSet(x)){
            RealMachine.gui.showMessage(x+" block is already locked.");
            RealMachine.proc[0].PI.setValue(1);
        } else {
            RealMachine.proc[0].SI.setValue(4);
            RealMachine.proc[0].mode.SetSupervisor();
            RealMachine.proc[0].S.setBit(x);         

            RealMachine.gui.updateAll();
            pause(WAIT_TIME);

            RealMachine.proc[0].SI.setValue(0);
            RealMachine.proc[0].mode.setUser(); 

            RealMachine.gui.updateAll();
        }
    }
    
    public void X1 (int xx)
    {
        int x = xx/0x10;
        if (!RealMachine.proc[0].S.isBitSet(x))
        {
            RealMachine.proc[0].PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            R1.setValue(memory.getSharedMemoryWord(xx));
    }
    
    public void X2 (int xx)
    {
        int x = xx/0x10;
        if (!RealMachine.proc[0].S.isBitSet(x))
        {
            RealMachine.proc[0].PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
             R2.setValue(memory.getSharedMemoryWord(xx));
    }
    
    public void Z1 (int xx)
    {
        int x = xx/0x10;
        if (!RealMachine.proc[0].S.isBitSet(x))
        {
            RealMachine.proc[0].PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            memory.setSharedMemoryWord(xx, R1.getValue());
    }

    public void Z2 (int xx)
    {
        int x = xx/0x10;
        if (!RealMachine.proc[0].S.isBitSet(x))
        {
            RealMachine.proc[0].PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            memory.setSharedMemoryWord(xx, R2.getValue());
    }
    
    public void ULC (int x)
    {
        if (!RealMachine.proc[0].S.isBitSet(x)){
            RealMachine.gui.showMessage(x+" block is already unlocked.");
            RealMachine.proc[0].PI.setValue(1);
        } else {
            RealMachine.proc[0].SI.setValue(4);
            RealMachine.proc[0].mode.SetSupervisor(); 
            RealMachine.proc[0].S.unsetBit(x);   
       
            RealMachine.gui.updateAll();
            pause(WAIT_TIME);
       
            RealMachine.proc[0].SI.setValue(0);
            RealMachine.proc[0].mode.setUser();
       
            RealMachine.gui.updateAll();
        }
    }    
    
    //Valdymo perdavimo komandos
    
    public void JP (int xx)
    {
        System.out.println(xx);
        IC.setValue(xx);
    }
    
    public void JE (int xx)
    {   
        if (C.isZeroFlagSet())
            IC.setValue(xx);
    }

    public void JG (int xx)
    {   
        if (C.isZeroFlagSet() && (C.isSignFlagSet() == C.isOverflowFlagSet()))
            IC.setValue(xx);
    }
    
    public void JL (int xx)
    {   
        if (C.isSignFlagSet() != C.isOverflowFlagSet())
            IC.setValue(xx);
    }
    
    public void JX (int xx)
    {   
        IC.setValue(getWord(xx).getIntValue());
    }    
    
    public void LO (int xx)
    {   
        R2.setValue(new Word ((R2.getValue().getIntValue() - 1)));
        
        if (R2.getValue().getIntValue() > 0)
        {
            IC.setValue(xx);
        }    
    }    
    
    //Įvedimo/išvedimo įrenginio komandos
    
    public void DGT (int x)
    {
        RealMachine.proc[0].SI.setValue(1);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH2.setClosed();

        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        RealMachine.proc[0].timer.timePass(2);
        
        String temp = RealMachine.in.get();
        for (int i = 0; i < temp.length() / 4 + 1; i++)
        {
            int end = i*4+4;
            if (end > temp.length())
                end = temp.length();
            
           setWord(x * 0x10 + i, new Word(temp.substring(i*4, end)));
        }
        
        RealMachine.proc[0].CH2.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    public void DPT (int x)
    {
        RealMachine.proc[0].SI.setValue(2);      
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH1.setClosed();
           
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        RealMachine.proc[0].timer.timePass(2);
        
        
        String temp = "";
        
        for (int i = 0; i < 16; i++)
        {
            String s = getWord(x * 0x10 + i).getValue();
            if (!s.equals("0"))
                temp = temp.concat(s);   
        }
        
        RealMachine.out.send(temp);
        
        RealMachine.proc[0].CH1.setOpen();
        RealMachine.proc[0].mode.setUser();
        RealMachine.proc[0].SI.setValue(0);
    }    
    
    //Garsiakalbio komandos
    
    public void GGR1(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = RealMachine.proc[0].R1.getValue().getIntValue();
        RealMachine.speakers[0].setVolume(volume);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();       
    }
    
    public void GGR2(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = RealMachine.proc[0].R2.getValue().getIntValue();
        RealMachine.speakers[1].setVolume(volume);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    public void GLR1(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = RealMachine.proc[0].R1.getValue().getIntValue();
        RealMachine.speakers[0].setLength(volume);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    public void GLR2(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        int volume = RealMachine.proc[0].R2.getValue().getIntValue();
        RealMachine.speakers[1].setLength(volume);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    public void GNR1(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int value = RealMachine.proc[0].R1.getValue().getIntValue();
        RealMachine.speakers[0].play(value);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    public void GNR2(){
        RealMachine.proc[0].SI.setValue(3);
        RealMachine.proc[0].mode.SetSupervisor();
        RealMachine.proc[0].CH4.setClosed();
        
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int value = RealMachine.proc[0].R2.getValue().getIntValue();
        RealMachine.speakers[1].play(value);
        
        RealMachine.proc[0].CH4.setOpen();
        RealMachine.proc[0].SI.setValue(0);
        RealMachine.proc[0].mode.setUser();
    }
    
    //Programos pabaigos komadna
    
    public void HALT ()
    {
        RealMachine.proc[0].SI.setValue(5);
        RealMachine.proc[0].mode.SetSupervisor();
    }
    
    //Pagalbinės (ne virtualios mašinos)
    private void arithmeticFlagSet(Word oldValue, Word operand, Word newValue)
    {       
        setZfSf(newValue);
          
        if (((getSign(oldValue)) == getSign(operand))
            && (getSign(oldValue) != getSign(newValue)))
            
            C.setOverflowFlag();
        else
            C.unsetOverflowFlag();
        
    }
    
    private void setZfSf(Word newValue)
    {
        if (newValue.getIntValue() == 0)
        {
            C.setZeroFlag();
            C.unsetSignFlag();
        }
        else if (getSign(newValue) == 0)
        {
            C.unsetZeroFlag();
            C.unsetSignFlag();
        }
        else 
        {
            C.unsetZeroFlag();
            C.setSignFlag();
        }
    }
    
    private int getSign(Word value)
    {     
        String s = "";
        s += value.getValue().charAt(0);
               
        if (value.getValue().length() < 4)
            return 0;
        
        int i = Integer.parseInt(s, 16);
        
        
        if (i >= 8)
            return  1;
        else
            return 0;
    }
   
    private String encodeBytes3and2(Word word)
    {
        String OPC = "";
        OPC += word.getValue().charAt(0);
        OPC += word.getValue().charAt(1);
        return OPC;
    }
    
    private char encodeByte1(Word word)
    {
        return word.getValue().charAt(2);
    }
    
    private char encodeByte0(Word word)
    {
        return word.getValue().charAt(3);
    }
    
    private int XXAdress(Word word)
    {
        String address = "";
        
        address += word.getValue().charAt(2);
        address += word.getValue().charAt(3);
               
        try 
        {
            return Integer.parseInt(address, 16);
        } 
        catch (NumberFormatException e)
        {
            return 0;
        }
    }  
    
    private int XAdress(Word word)
    {
        String address = "";
        address += encodeByte0(word);
        
        try 
        {
            return Integer.parseInt(address, 16);
        } 
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
    
    public Word getWord(int address)
    {
        return memory.getWord(address);
    }
    
    public void setWord(int address, Word value)
    {
        memory.setWord(address, value);
    }
    
    private void pause(int mils)
    {
         try
         {
            Thread.sleep(mils);
         } 
         catch (InterruptedException ex) {}
    }
}
