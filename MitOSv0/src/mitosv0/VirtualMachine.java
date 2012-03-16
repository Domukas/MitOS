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
        System.out.println(getWord(0));
        System.out.println("Memory dump:");
        for (int i = 0; i < 0x100; i++)
        {
            System.out.print(getWord(i)+" ");
            if (i % 0x10 == 0x0f)
                System.out.println();
        }
        
        A1(0);
        System.out.println(R1.getValue());
        System.out.println(C.isZeroFlagSet());
        System.out.println(C.isSignFlagSet());
        System.out.println(C.isOverflowFlagSet());
    
    }
    
    public int getCurrentCommand()
    {
        return memory.getWord(IC.getValue());
    }
    
    private void processCommand()
    {
        int command = getCurrentCommand();    
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
