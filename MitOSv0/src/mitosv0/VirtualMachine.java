/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import javax.sound.sampled.LineUnavailableException;
import mitosv0.registers.ICRegister;
import mitosv0.registers.DataRegister;
import mitosv0.registers.CRegister;

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
    
    private DataRegister R1, R2;
    private ICRegister IC;
    private CRegister C;
    
    private VirtualMemory memory;
    
    public VirtualMachine(DataRegister R1, DataRegister R2, ICRegister IC, CRegister C, VirtualMemory memory)
    {
        this.R1 = R1;
        this.R2 = R2;
        this.IC = IC;
        this.C = C;
        
        this.memory = memory;
   
        R1.setValue(2147483646);
        setWord(0, 501);
    }
    
    public int getCurrentCommand()
    {
        return memory.getWord(IC.getValue());
    }
    
    public int getNextCommand(int currentCommand)
    {
        IC.setValue(++currentCommand);
        return memory.getWord(IC.getValue());
    }
    
    private void processCommand() throws LineUnavailableException
    {
        String OPC="";
        int currentWord;
        int xx;
        currentWord = getCurrentCommand();
        
        if(currentWord>=65 && currentWord<=122)
        {//pirmas baitas?
            OPC=OPC+(char)currentWord;
            int currentIC = IC.getValue();
            IC.setValue(++currentIC);
            currentWord = getCurrentCommand();
            if(currentWord>=65 && currentWord<=122)
            {//antras baitas?
                OPC=OPC+(char)currentWord;
                switch(OPC)
                {
                    case "MU":
                    {
                        xx = XXAdres();
                        MU(xx);
                        break;
                    }
                    case "DI":
                    {
                        xx = XXAdres();
                        DI(xx);
                        break;
                    }
                    case "XR":
                    {
                        xx = XXAdres();
                        XR(xx);
                        break;
                    }
                    case "AN":
                    {
                        xx = XXAdres();
                        AN(xx);
                        break;
                    }
                    case "OR":
                    {
                        xx = XXAdres();
                        OR(xx);
                        break;
                    }
                    case "JP":
                    {
                        xx = XXAdres();
                        JP(xx);
                        break;
                    }
                    case "JE":
                    {
                        xx = XXAdres();
                        JE(xx);
                        break;
                    }
                    case "JG":
                    {
                        xx = XXAdres();
                        JG(xx);
                        break;
                    }
                    case "JL":
                    {
                        xx = XXAdres();
                        JL(xx);
                        break;
                    }
                    case "JX":
                    {
                        xx = XXAdres();
                        JX(xx);
                        break;
                    }
                    case "LO":
                    {
                        xx = XXAdres();
                        LO(xx);
                        break;
                    }
                    default:
                    {
                        currentIC = IC.getValue();
                        IC.setValue(++currentIC);
                        currentWord = getCurrentCommand();  
                        if(currentWord>=65 && currentWord<=122)
                        {//trecias baitas?
                            OPC=OPC+(char)currentWord;
                            int x;
                            switch(OPC)
                            {
                                case "LCK":
                                {
                                    currentIC = IC.getValue();
                                    IC.setValue(++currentIC);
                                    x = getCurrentCommand();
                                    LCK(x);
                                    break;
                                }
                                case "ULC":
                                {
                                    currentIC = IC.getValue();
                                    IC.setValue(++currentIC);
                                    x = getCurrentCommand();
                                    ULC(x);
                                    break;
                                }
                                case "DGT":
                                {
                                    currentIC = IC.getValue();
                                    IC.setValue(++currentIC);
                                    x = getCurrentCommand();
                                    DGT(x);
                                    break;
                                }
                                case "DPT":
                                {
                                    currentIC = IC.getValue();
                                    IC.setValue(++currentIC);
                                    x = getCurrentCommand();
                                    DPT(x);
                                    break;
                                }
                                default:
                                {//ketvirtas baitas?
                                    currentIC = IC.getValue();
                                    IC.setValue(++currentIC);
                                    currentWord = getCurrentCommand();
                                    if(currentWord>=65 && currentWord<=122)
                                    {
                                        OPC=OPC+(char)currentWord;
                                        switch(OPC){
                                            case "HALT":
                                            {
                                                HALT();
                                                break;
                                            }
                                            default:
                                            {
                                                //Kas jei ne komanda??
                                                break;
                                            }
                                        }
                                    }else
                                    {
                                        OPC=OPC+currentWord;
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
                                            default:
                                            {
                                                //Kas jei ne komanda??
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }else
                        {
                            //Kas jei ne komanda??
                        }
                        break;
                    }
                }   
            }else
            {//Jei antras baitas ne
                OPC=OPC+currentWord;
                switch(OPC)
                {
                    case "A1":
                    {
                        xx = XXAdres();
                        A1(xx);
                        break;
                    }
                    case "A2":
                    {
                        xx = XXAdres();
                        A2(xx); 
                        break;
                    }
                    case "B1":
                    {
                        xx = XXAdres();
                        B1(xx);
                        break;
                    }
                    case "B2":
                    {
                        xx = XXAdres();
                        B2(xx); 
                        break;
                    }
                    case "C1":
                    {
                        xx = XXAdres();
                        C1(xx); 
                        break;
                    }
                    case "C2":
                    {
                        xx = XXAdres();
                        C2(xx);  
                        break;
                    }
                    case "L1":
                    {
                        xx = XXAdres();
                        L1(xx);
                        break;
                    }
                    case "L2":
                    {
                        xx = XXAdres();
                        L2(xx);
                        break;
                    }
                    case "S1":
                    {
                        xx = XXAdres();
                        S1(xx);
                        break;
                    }
                    case "S2":
                    {
                        xx = XXAdres();
                        S2(xx);
                        break;
                    }
                    case "X1":
                    {
                        xx = XXAdres();
                        X1(xx);
                        break;
                    }
                    case "X2":
                    {
                        xx = XXAdres();
                        X2(xx);
                        break;
                    }
                    case "Z1":
                    {
                        xx = XXAdres();
                        Z1(xx);
                        break;
                    }
                    case "Z2":
                    {
                        xx = XXAdres();
                        Z2(xx);
                        break;
                    }
                    default:
                    {
                        //Kas jei ne komanda??
                        break;
                    }
                }
            }
        }else
        {
            //Kas jei ne komanda??
        }  
    }   
        
    private int XXAdres(){
        int currentIC = IC.getValue();
        IC.setValue(++currentIC);
        int x1 = getCurrentCommand();
        currentIC = IC.getValue();
        IC.setValue(++currentIC);
        int x2 = getCurrentCommand();
        return getWord(x1*100+x2);
    }  
    
    public int getWord(int address)
    {
        return memory.getWord(address);
    }
    
    public void setWord(int address, int value)
    {
        memory.setWord(address, value);
    }
    
    //Aritmetinės komandos
    public void A1(int xx)
    {
        int oldValue = R1.getValue();
        R1.setValue(oldValue + getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }
    
    public void A2(int xx)
    {
        int oldValue = R2.getValue();
        R2.setValue(oldValue + getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R2.getValue());
    }
    
    public void B1(int xx)
    {
        int oldValue = R1.getValue();
        R1.setValue(oldValue - getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }   

    public void B2(int xx)
    {
        int oldValue = R2.getValue();
        R2.setValue(oldValue - getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R2.getValue());
    }   
    
    public void MU(int xx)
    {
        int oldValue = R1.getValue();
        R1.setValue(oldValue * getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }   
    
    public void DI(int xx)
    {
        R2.setValue(R1.getValue() % getWord(xx));
        
        int oldValue = R1.getValue();
        R1.setValue(R1.getValue() / getWord(xx));
        arithmeticFlagSet(oldValue, getWord(xx), R1.getValue());
    }
    
    //Loginės komandos
    public void XR (int xx)
    {
        R1.setValue(R1.getValue() ^ memory.getWord(xx));
        setZfSf(R1.getValue());
    }

    public void AN (int xx)
    {
        R1.setValue(R1.getValue() & memory.getWord(xx));
        setZfSf(R1.getValue());
    }
    
    public void OR (int xx)
    {
        R1.setValue(R1.getValue() | memory.getWord(xx));
        setZfSf(R1.getValue());
    }    
    
    //Palyginimo komandos
    public void C1 (int xx)
    {
        int oldValue = R1.getValue();
        int tempValue = oldValue - getWord(xx);
        arithmeticFlagSet(oldValue, getWord(xx), tempValue);
    }
    
    public void C2 (int xx)
    {
        int oldValue = R2.getValue();
        int tempValue = oldValue - getWord(xx);
        arithmeticFlagSet(oldValue, getWord(xx), tempValue);
    }
    
    //Darbo su duomenimis komandos
    
    public void L1 (int xx)
    {
        R1.setValue(memory.getWord(xx));
    }

    public void L2 (int xx)
    {
        R2.setValue(memory.getWord(xx));
    }
    
    public void S1 (int xx)
    {
        memory.setWord(xx, R1.getValue());
    }
    
    public void S2 (int xx)
    {
        memory.setWord(xx, R2.getValue());
    }
    
    public void LCK (int x)
    {
       RealMachine.SI.setValue(4);
       RealMachine.mode.SetSupervisor(); //?
       RealMachine.S.setBit(x);         //Šitą vėliau OS'as darys?
    }
    
    public void X1 (int xx)
    {
        //TODO
    }
    
    public void X2 (int xx)
    {
        //TODO
    }
    
    public void Z1 (int xx)
    {
        //TODO (įrašymas į bendrą atmintį)
    }

    public void Z2 (int xx)
    {
        //TODO (įrašymas į bendrą atmintį)
    }
    
    public void ULC (int x)
    {
       RealMachine.SI.setValue(4);
       RealMachine.mode.SetSupervisor(); //?
       RealMachine.S.unsetBit(x);       //Sitą vėliau OS'as darys?
    }    
    
    //Valdymo perdavimo komandos
    
    public void JP (int xx)
    {
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
        IC.setValue(memory.getWord(xx));
    }    
    
    public void LO (int xx)
    {   
        R2.setValue(R2.getValue() - 1);
        
        if (R2.getValue() > 0)
        {
            IC.setValue(xx);
        }    
    }    
    
    //Įvedimo/išvedimo įrenginio komandos
    
    public void DGT (int x)
    {
        //TODO
        RealMachine.SI.setValue(1);
        RealMachine.mode.SetSupervisor();
    }
    
    public void DPT (int x)
    {
        //TODO
        RealMachine.SI.setValue(2);
        RealMachine.mode.SetSupervisor();
    }    
    
    //Garsiakalbio komandos
    
    public void GGR1(){
        int volume = RealMachine.R1.getValue();
        RealMachine.speakers.setVolume(volume);
    }
    
    public void GGR2(){
        int volume = RealMachine.R2.getValue();
        RealMachine.speakers.setVolume(volume);
    }
    
    public void GLR1(){
        int volume = RealMachine.R1.getValue();
        RealMachine.speakers.setLength(volume);
    }
    
    public void GLR2(){
        int volume = RealMachine.R2.getValue();
        RealMachine.speakers.setLength(volume);
    }
    
    public void GNR1() throws LineUnavailableException{
        int value = RealMachine.R1.getValue();
        RealMachine.speakers.play(value);
    }
    
    public void GNR2() throws LineUnavailableException{
        int value = RealMachine.R2.getValue();
        RealMachine.speakers.play(value);
    }
    
    //Programos pabaigos komadna
    
    public void HALT ()
    {
        RealMachine.SI.setValue(4);
    }
    
    //Pagalbinės (ne virtualios mašinos)
    private void arithmeticFlagSet(int oldValue, int operand, int newValue)
    {       
        setZfSf(newValue);
        
        if (((Integer.signum(oldValue)) == Integer.signum(operand))
            && (Integer.signum(oldValue) != Integer.signum(newValue)))
            
            C.setOverflowFlag();
        else
            C.unsetOverflowFlag();
        
    }
    
    private void setZfSf(int newValue)
    {
        if (newValue == 0)
        {
            C.setZeroFlag();
            C.unsetSignFlag();
        }
        else if (newValue > 0)
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
}
