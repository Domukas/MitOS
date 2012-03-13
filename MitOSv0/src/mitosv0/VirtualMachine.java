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
        
        //System.out.println(C.getValue().getByte(0));
        Word w = new Word();
        w.setByte((byte)255, 0);
        w.setByte((byte)255, 1);
        w.setByte((byte)255, 2);
        w.setByte((byte)127, 3);
        
        Long l = w.getLong();
        System.out.println(l);
        String s = Long.toHexString(l);
        System.out.println(s);
        //2147483647 <-- Max teigiamas 32 bit skaicius
        
        w.setByte((byte)255, 0);
        w.setByte((byte)255, 1);
        w.setByte((byte)255, 2);
        w.setByte((byte)255, 3);
        l = w.getLong();
        System.out.println(l);
        //Max neigiamas
        
    }
    /*
    public Word getCurrentCommand(){
        return memory.getWord((int) IC.getValue());
    }
    
    private void processCommand(){
        Word command = getCurrentCommand();
        
    }
    */
}
