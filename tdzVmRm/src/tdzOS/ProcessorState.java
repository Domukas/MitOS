/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import tdzVmRm.Processor;
import tdzVmRm.registers.*;

/**
 *
 * @author Tomas
 */
class ProcessorState {
    public  PLRRegister PLR;
    public  DataRegister R1, R2;
    public  ICRegister IC;
    public  CRegister C;
    public  TimerRegister timer;
    public  ModeRegister mode;
    public  INTRegister PI, SI;
    
    ProcessorState(){
    }
    
    public void SaveProcessorState(Processor processor)
    {
        this.PLR = processor.PLR;
        this.R1 = processor.R1;
        this.R2 = processor.R2;
        this.IC = processor.IC;
        this.C = processor.C;
        this.timer = processor.timer;
        this.mode = processor.mode;
        this.PI = processor.PI;
        this.SI = processor.SI;
    }
    public void LoadProcessorState(Processor processor)
    {
        processor.PLR = this.PLR;
        processor.R1 = this.R1;
        processor.R2 = this.R2;
        processor.IC = this.IC;
        processor.C = this.C;
        processor.timer = this.timer;
        processor.mode = this.mode;
        processor.PI = this.PI;
        processor.SI = this.SI;   
    }
}
