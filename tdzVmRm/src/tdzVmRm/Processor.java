/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

import tdzOS.ProcessorDescriptor;
import tdzVmRm.registers.*;

/**
 *
 * @author Tomas
 */
public class Processor {
    public  PLRRegister PLR;
    public  DataRegister R1, R2;
    public  ICRegister IC;
    public  CRegister C;
    public  SemaphoreRegister S;
    public  TimerRegister timer;
    public  ModeRegister mode;
    public  INTRegister PI, SI;
    public  CHRegister CH1, CH2, CH3, CH4;
    
    public ProcessorDescriptor pd;
    
    public Processor()
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
        
        pd = new ProcessorDescriptor(2); //TODO
    }
    
}
