/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;


import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tdzOS.OS.ResName;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.VirtualMemory;
import tdzVmRm.Word;
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


public class VirtualMachine extends Process
{
    static final int WAIT_TIME = 3000;
    
    public DataRegister R1, R2;
    public ICRegister IC;
    public CRegister C;
    
  
    public VirtualMemory memory;
    
    String OPC = "";
    int commandParameter = 0;
    
    public VirtualMachine (LinkedList inList, int internalID, OS.ProcName externalID, 
           ProcessorState ps, Processor p, LinkedList<ResComponent> or,
           OS.ProcessState state, int priority, Process parent, OS core)
    {
        super(inList, internalID, externalID, ps, p, or, state,
            priority, parent, core);
        
        setPLR();
        
        memory = new VirtualMemory(pd.procesorState.PLR, RealMachine.memory);
        RealMachine.gui.createVMTab(this);
    }

    
    public void step()
    {
        switch (nextInstruction)
        {
            case 1:
                switchToUser();
                break;
                
            case 2:
                stepProgram();
                break;
                
            case 3:
                createInterruptMessage();
                break;
                
            case 4:
                blockForResume();
                break;
        }
    }
    
    //1
    private void switchToUser()
    {
        System.out.println("VirtualMachine perjungia procesorių į vartotojo rėžimą");
        pd.processor.mode.setUser();
        for (ResComponent rc : pd.ownedResources)
        {
            if (rc.parent.rd.externalID == ResName.PratestiVMDarba)
                pd.core.freeResource(this, rc.parent);
        }
        next();
    }
    
    //2
    private void stepProgram()
    {
        //del patogumo
        IC = pd.processor.IC;
        R1 = pd.processor.R1;
        R2 = pd.processor.R2;
        C = pd.processor.C;
        
        System.out.println("VirtualMachine suveikia");
        stepVM();

        
        if (nextInstruction != 3)
            goTo(2);
        
    }
    
    //3
    private void createInterruptMessage()
    {
        System.out.println("VirtualMachine pranešimą apie pertraukimą");
        
        pd.processor.mode.SetSupervisor();
        
        LinkedList<Object> parameters = new LinkedList<>();
        
        parameters.add(pd.processor.PI.getValue());
        parameters.add(pd.processor.SI.getValue());
        parameters.add(OPC); //Paskutines komandos opkodas

        if ((OPC.equals("DGT")) || (OPC.equals("DPT")))
        {
            commandParameter = getRealBlockAdress(commandParameter);
        }
            
        parameters.add(commandParameter);
        
        
        LinkedList<Object> tempList = new LinkedList<>();
        tempList.add(parameters);
        
        pd.core.createResource(this, ResName.PranesimasApiePertraukima, tempList);

        resetInterruptRegisters();
        
        next();
    }
    
    //4
    private void blockForResume()
    {
        System.out.println("VirtualMachine blokuojasi dėl resurso [Pratęsti VM darbą]");
        pd.core.requestResource(this, ResName.PratestiVMDarba, 1);
        goTo(1);
    }
    
    
    //TODO
    public void run()
    {
        do
        {
            step();
            //RealMachine.gui.updateAll();
            //pause(500);
        } while ((pd.processor.SI.getValue() == 0) && (pd.processor.PI.getValue() == 0));
    }
    
    public void stepVM()
    {    
        //if ((pd.processor.SI.getValue() == 0) && (pd.processor.PI.getValue() == 0))
        
        if (pd.processor.timer.getValue() != 0)
        {
            Word currentCommand = getCurrentCommand();
            goToNextCommand();
            processCommand(currentCommand);  
            pd.processor.timer.timePass(1);
            
            System.out.println("Timer: " + pd.processor.timer.getValue());
            
            if ((pd.processor.SI.getValue() + pd.processor.PI.getValue() == 0)
                    && (pd.processor.timer.getValue() == 0))
            {
                timerInterrupt();
            }
        }
        else 
        {   
            timerInterrupt();
        }
        //else goTo(3);
    }
    
    
    public Word getCurrentCommand()
    {
        System.out.println("IC: " + IC.getValue());
        
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
        int xx = 0;
        if (currentWord.getValue().length() == 4)
        {
            OPC = encodeBytes3and2(currentWord);
            xx = XXAdress(currentWord);
// <editor-fold defaultstate="collapsed" desc="Opkodu Switch'as">
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
                                    pd.processor.PI.setValue(2);
                                    goTo(3);
                                }
                            }
                        }
                    } 
                }   
            }// </editor-fold>
        }
        else 
        {
            //Opkodas per trumpas
            pd.processor.PI.setValue(2);
            goTo(3);
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
        pd.processor.SI.setValue(4);
        commandParameter = x;
        goTo(3);
        
        /*
        if (pd.processor.S.isBitSet(x)){
            RealMachine.gui.showMessage(x+" block is already locked.");
            pd.processor.PI.setValue(1);
        } else {
            pd.processor.SI.setValue(4);
            pd.processor.mode.SetSupervisor();
            pd.processor.S.setBit(x);         

            RealMachine.gui.updateAll();
            pause(WAIT_TIME);

            pd.processor.SI.setValue(0);
            pd.processor.mode.setUser(); 

            RealMachine.gui.updateAll();
        }
        * */
    }
    
    public void X1 (int xx)
    {
        pd.processor.SI.setValue(4);
        commandParameter = xx;
        goTo(3);
        
        /*
        int x = xx/0x10;
        if (!pd.processor.S.isBitSet(x))
        {
            pd.processor.PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            R1.setValue(memory.getSharedMemoryWord(xx));
            * 
            */
    }
    
    public void X2 (int xx)
    {
        pd.processor.SI.setValue(4);
        commandParameter = xx;
        goTo(3);
        /*
        int x = xx/0x10;
        if (!pd.processor.S.isBitSet(x))
        {
            pd.processor.PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
             R2.setValue(memory.getSharedMemoryWord(xx));
             * */
    }
    
    public void Z1 (int xx)
    {
        pd.processor.SI.setValue(4);
        commandParameter = xx;
        goTo(3);
        /*
        int x = xx/0x10;
        if (!pd.processor.S.isBitSet(x))
        {
            pd.processor.PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            memory.setSharedMemoryWord(xx, R1.getValue());
            * 
            */
    }

    public void Z2 (int xx)
    {   
        pd.processor.SI.setValue(4);
        commandParameter = xx;
        goTo(3);
        /*
        int x = xx/0x10;
        if (!pd.processor.S.isBitSet(x))
        {
            pd.processor.PI.setValue(1); 
            RealMachine.gui.showMessage("Accessing unavailable memory!");
        }
        else 
            memory.setSharedMemoryWord(xx, R2.getValue());
            * 
            */
    }
    
    public void ULC (int x)
    {
        pd.processor.SI.setValue(4);
        commandParameter = x;
        goTo(3);
        /*
        if (!pd.processor.S.isBitSet(x)){
            RealMachine.gui.showMessage(x+" block is already unlocked.");
            pd.processor.PI.setValue(1);
        } else {
            pd.processor.SI.setValue(4);
            pd.processor.mode.SetSupervisor(); 
            pd.processor.S.unsetBit(x);   
       
            RealMachine.gui.updateAll();
            pause(WAIT_TIME);
       
            pd.processor.SI.setValue(0);
            pd.processor.mode.setUser();
       
            RealMachine.gui.updateAll();
            
        }
        * 
        */
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
        pd.processor.SI.setValue(1);
        commandParameter = x;
        
        pd.processor.timer.timePass(1);
        
        goTo(3);
        
        /*
        pd.processor.CH2.setClosed();
        

        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        pd.processor.timer.timePass(2);
        
        String temp = RealMachine.in.get();
        for (int i = 0; i < temp.length() / 4 + 1; i++)
        {
            int end = i*4+4;
            if (end > temp.length())
                end = temp.length();
            
           setWord(x * 0x10 + i, new Word(temp.substring(i*4, end)));
        }
        
        pd.processor.CH2.setOpen();
        * 
        */
    }
    
    public void DPT (int x)
    {
        pd.processor.SI.setValue(2); 
        commandParameter = x;
        
        pd.processor.timer.timePass(1);
        
        goTo(3);
        
        /*
        pd.processor.CH1.setClosed();
           
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        pd.processor.timer.timePass(2);
        
        
        String temp = "";
        
        for (int i = 0; i < 16; i++)
        {
            String s = getWord(x * 0x10 + i).getValue();
            if (!s.equals("0"))
                temp = temp.concat(s);   
        }
        
        RealMachine.out.send(temp);
        
        pd.processor.CH1.setOpen();
        * 
        */
    }    
    
    //Garsiakalbio komandos
    
    public void GGR1(){
        pd.processor.SI.setValue(3);
        goTo(3);
        /*
        pd.processor.CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = pd.processor.R1.getValue().getIntValue();
        RealMachine.speakers[0].setLength(volume);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    public void GGR2(){
        pd.processor.SI.setValue(3);
        goTo(3);
        /*
        pd.processor.CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = pd.processor.R2.getValue().getIntValue();
        RealMachine.speakers[1].setLength(volume);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    public void GLR1(){
        pd.processor.SI.setValue(3);
        
        /*
        pd.processor.CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int volume = pd.processor.R1.getValue().getIntValue();
        RealMachine.speakers[0].setLength(volume);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    public void GLR2(){
        pd.processor.SI.setValue(3);
        goTo(3);
        
        /*
        pd.processor.CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);

        int volume = pd.processor.R2.getValue().getIntValue();
        RealMachine.speakers[1].setLength(volume);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    public void GNR1(){
        pd.processor.SI.setValue(3);
        goTo(3);
        
        /*
        pd.processor.CH4.setClosed();
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int value = pd.processor.R1.getValue().getIntValue();
        RealMachine.speakers[0].play(value);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    public void GNR2(){
        pd.processor.SI.setValue(3);
        goTo(3);
        /*
        pd.processor.CH4.setClosed();
        
        
        RealMachine.gui.updateAll();
        pause(WAIT_TIME);
        
        int value = pd.processor.R2.getValue().getIntValue();
        RealMachine.speakers[1].play(value);
        
        pd.processor.CH4.setOpen();
        * 
        */
    }
    
    //Programos pabaigos komadna
    
    public void HALT ()
    {
        pd.processor.SI.setValue(5);
        goTo(3);
        
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

    private void setPLR()
    {
        String message = (String)pd.ownedResources.getFirst().value;
        
        int val = Character.digit(message.charAt(0), 16);
        pd.procesorState.PLR.setA2((byte)val);
        
        val = Character.digit(message.charAt(1), 16);
        pd.procesorState.PLR.setA3((byte)val);
        
        val = Integer.parseInt(message.substring(2, message.length()));
        
        if (val >= 0x10)
            pd.procesorState.PLR.setA0((byte) 0);
        else
            pd.procesorState.PLR.setA0((byte) val);
    }
    
    private int getRealBlockAdress(int adress)
    {
        System.out.print("Adresas: " + RealMachine.memory.getBlock(pd.processor.PLR.getA2()*0x10
                + pd.processor.PLR.getA3()).getWord(adress).getIntValue());
        
        return RealMachine.memory.getBlock(pd.processor.PLR.getA2()*0x10
                + pd.processor.PLR.getA3()).getWord(adress).getIntValue();
    }

    private void resetInterruptRegisters()
    {
        
        if (pd.processor != null)
        {
            pd.processor.PI.setValue(0);
            pd.processor.SI.setValue(0);
        }
    }
    
    private void timerInterrupt()
    {
        pd.processor.timer.reset();
        System.out.println(pd.externalID + " #" + pd.internalID + " taimerio pertraukimas");
        pd.core.stopProcess(this);
        decreasePriority();
    }

}
