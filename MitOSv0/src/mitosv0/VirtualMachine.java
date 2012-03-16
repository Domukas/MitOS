/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

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
    private SemaphoreRegister S;
    
    private VirtualMemory memory;
    
    public VirtualMachine()
    {
        R1 = new DataRegister();
        R2 = new DataRegister();
        IC = new ICRegister();
        C = new CRegister();
        S = new SemaphoreRegister();
        
        memory = new VirtualMemory(256);
   
        R1.setValue(2147483646);
        memory.setWord(0, 501);
        
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
    //Logikos komandos
    
    //Palyginimo komandos
    
    //Darbo su duomenimis komandos
    
    //Valdymo perdavimo komandos
    
    //Įvedimo/išvedimo įrenginio komandos
    
    //Garsiakalbio komandos
    
    //Programos pabaigos komadna
    
    //Pagalbinės (ne virtualios mašinos)
    
    
    private void arithmeticFlagSet(int oldValue, int operand, int newValue)
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
        
        if (((Integer.signum(oldValue)) == Integer.signum(operand))
            && (Integer.signum(oldValue) != Integer.signum(newValue)))
            
            C.setOverflowFlag();
        else
            C.unsetOverflowFlag();
        
    }
}
