/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

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
        } while ((RealMachine.SI.getValue() != 5) && (RealMachine.PI.getValue() != 2));
    }
    
    public void step()
    {    
        if ((RealMachine.SI.getValue() != 5) && (RealMachine.PI.getValue() != 2))
        {
            Word currentCommand = getCurrentCommand();
            goToNextCommand();
            processCommand(currentCommand);          
        }
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
                                RealMachine.PI.setValue(2);
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
        int oldValue = R1.getValue().getIntValue();
        R1.setValue(new Word ((oldValue + getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R1.getValue().getIntValue());
    }
    
    public void A2(int xx)
    {
        int oldValue = R2.getValue().getIntValue();
        R2.setValue(new Word ((oldValue + getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R2.getValue().getIntValue());
    }
    
    public void B1(int xx)
    {        
        int oldValue = R1.getValue().getIntValue();
        R1.setValue(new Word ((oldValue - getWord(xx).getIntValue())));
        System.out.println(oldValue - getWord(xx).getIntValue());
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R1.getValue().getIntValue());
    }   

    public void B2(int xx)
    {
        int oldValue = R2.getValue().getIntValue();
        R2.setValue(new Word ((oldValue - getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R2.getValue().getIntValue());
    }   
    
    public void MU(int xx)
    {
        int oldValue = R1.getValue().getIntValue();
        R1.setValue(new Word ((oldValue * getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R1.getValue().getIntValue());
    }   
    
    public void DI(int xx)
    {
        R2.setValue(new Word ((R1.getValue().getIntValue() % getWord(xx).getIntValue())));
        
        int oldValue = R1.getValue().getIntValue();
        R1.setValue(new Word ((oldValue / getWord(xx).getIntValue())));
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), R1.getValue().getIntValue());
    }
    
    //Loginės komandos
    public void XR (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() ^ getWord(xx).getIntValue())));
        setZfSf(R1.getValue().getIntValue());
    }

    public void AN (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() & getWord(xx).getIntValue())));
        setZfSf(R1.getValue().getIntValue());
    }
    
    public void OR (int xx)
    {
        R1.setValue(new Word ((R1.getValue().getIntValue() | getWord(xx).getIntValue())));
        setZfSf(R1.getValue().getIntValue());
    }    
    
    //Palyginimo komandos
    public void C1 (int xx)
    {
        int oldValue = R1.getValue().getIntValue();
        int tempValue = (oldValue - getWord(xx).getIntValue());        
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), tempValue);
    }
    
    public void C2 (int xx)
    {
        int oldValue = R2.getValue().getIntValue();
        int tempValue = (oldValue - getWord(xx).getIntValue());        
        arithmeticFlagSet(oldValue, getWord(xx).getIntValue(), tempValue);
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
       RealMachine.SI.setValue(4);
       RealMachine.mode.SetSupervisor(); //?
       RealMachine.S.setBit(x);         //Šitą vėliau OS'as darys?
    }
    
    public void X1 (int xx)
    {
        R1.setValue(memory.getSharedMemoryWord(xx));
    }
    
    public void X2 (int xx)
    {
        R2.setValue(memory.getSharedMemoryWord(xx));
    }
    
    public void Z1 (int xx)
    {
        memory.setSharedMemoryWord(xx, R1.getValue());
    }

    public void Z2 (int xx)
    {
        memory.setSharedMemoryWord(xx, R2.getValue());
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

        RealMachine.SI.setValue(1);
        RealMachine.mode.SetSupervisor();
        
        String temp = RealMachine.in.get();
        for (int i = 0; i < temp.length() / 4 + 1; i++)
        {
            int end = i*4+4;
            if (end > temp.length())
                end = temp.length();
            
           setWord(x * 0x10 + i, new Word(temp.substring(i*4, end)));
        }
    
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    public void DPT (int x)
    {
        RealMachine.SI.setValue(2);  
        RealMachine.mode.SetSupervisor();
        
        
        String temp = "";
        
        for (int i = 0; i < 16; i++)
        {
            String s = getWord(x * 0x10 + i).getValue();
            if (!s.equals("0"))
                temp = temp.concat(s);   
        }
        
        RealMachine.out.send(temp);
        
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
        

    }    
    
    //Garsiakalbio komandos
    
    public void GGR1(){
        RealMachine.SI.setValue(3);
        RealMachine.mode.SetSupervisor();
        
            //reikia kad po puses komandos GUI atvaizduotu kad pasikeitineja modas ir SI
        int volume = RealMachine.R1.getValue().getIntValue();
        RealMachine.speakers.setVolume(volume);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();       
    }
    
    public void GGR2(){
        RealMachine.SI.setValue(3);
        RealMachine.mode.SetSupervisor();
        
            //reikia kad po puses komandos GUI atvaizduotu kad pasikeitineja modas ir SI
        int volume = RealMachine.R2.getValue().getIntValue();
        RealMachine.speakers.setVolume(volume);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    public void GLR1(){
        RealMachine.SI.setValue(3);
        RealMachine.mode.SetSupervisor();
        
            //reikia kad po puses komandos GUI atvaizduotu kad pasikeitineja modas ir SI
        int volume = RealMachine.R1.getValue().getIntValue();
        RealMachine.speakers.setLength(volume);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    public void GLR2(){
        RealMachine.SI.setValue(3);
        RealMachine.mode.SetSupervisor();
        
            //reikia kad po puses komandos GUI atvaizduotu kad pasikeitineja modas ir SI
        int volume = RealMachine.R2.getValue().getIntValue();
        RealMachine.speakers.setLength(volume);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    public void GNR1(){
        RealMachine.SI.setValue(3);
        int value = RealMachine.R1.getValue().getIntValue();
        RealMachine.speakers.play(value);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    public void GNR2(){
        RealMachine.SI.setValue(3);
        RealMachine.mode.SetSupervisor();
        
            //reikia kad po puses komandos GUI atvaizduotu kad pasikeitineja modas ir SI
        int value = RealMachine.R2.getValue().getIntValue();
        RealMachine.speakers.play(value);
        RealMachine.SI.setValue(0);
        RealMachine.mode.setUser();
    }
    
    //Programos pabaigos komadna
    
    public void HALT ()
    {
        RealMachine.SI.setValue(5);
    }
    
    //Pagalbinės (ne virtualios mašinos)
    private void arithmeticFlagSet(int oldValue, int operand, int newValue)
    {       
        setZfSf(newValue);
            
        if (((getSign(oldValue)) == getSign(operand))
            && (getSign(oldValue) != getSign(newValue)))
            
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
    
    private int getSign(int value)
    {
        if (Integer.signum(value) == -1)
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
        
        System.out.println("XXaddress ->" + address);
               
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
}
