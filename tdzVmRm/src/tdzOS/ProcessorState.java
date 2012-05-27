/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

import java.awt.color.ICC_ColorSpace;
import tdzVmRm.Processor;
import tdzVmRm.RealMachine;
import tdzVmRm.Word;
import tdzVmRm.registers.*;

/**
 *
 * @author Tomas
 */
public class ProcessorState {
    public  PLRRegister PLR;
    public  DataRegister R1, R2;
    public  ICRegister IC;
    public  CRegister C;
    public  TimerRegister timer;
    public  ModeRegister mode;
    public  INTRegister PI, SI;
    
    public ProcessorState()
    {
        PLR = new PLRRegister();
        R1 = new DataRegister();
        R2 = new DataRegister();
        IC = new ICRegister();
        C = new CRegister();
        timer = new TimerRegister(RealMachine.proc[0].timer.getInterval());
        mode = new ModeRegister();
        PI = new INTRegister();
        SI = new INTRegister();
    }
    
    public void saveProcessorState(Processor processor)
    {
        this.PLR.setValue(processor.PLR.getIntValue());
        this.R1.setValue(processor.R1.getValue());
        this.R2.setValue(processor.R2.getValue());
        this.IC.setValue(processor.IC.getValue());
        this.C.setValue(processor.C.getValue());
        this.timer.setValue(processor.timer.getValue());
        if(processor.mode.isSupervisor())
        {
            this.mode.SetSupervisor();
        }
        else
        {
            this.mode.setUser();
        }
        this.PI.setValue(processor.PI.getValue());
        this.SI.setValue(processor.SI.getValue());
        
        //Kad issaugojus procesoriaus busena, jame neliktu „siuksiu“, registrus nunulinam
        
        processor.PLR.setValue(0);
        processor.R1.setValue(new Word(0));
        processor.R2.setValue(new Word(0));
        processor.IC.setValue(0);
        processor.C.setValue(0);
        processor.timer.setValue(RealMachine.proc[0].timer.getInterval());
        processor.mode.SetSupervisor();
        processor.PI.setValue(0);
        processor.SI.setValue(0);
        
        
    }
    public void loadProcessorState(Processor processor)
    {
        processor.PLR.setValue(this.PLR.getIntValue());
        processor.R1.setValue(this.R1.getValue());
        processor.R2.setValue(this.R2.getValue());
        processor.IC.setValue(this.IC.getValue());
        processor.C.setValue(this.C.getValue());
        processor.timer.setValue(this.timer.getValue());
        if(this.mode.isSupervisor())
        {
            processor.mode.SetSupervisor();
        }
        else
        {
            processor.mode.setUser();
        }
        processor.PI.setValue(this.PI.getValue());
        processor.SI.setValue(this.SI.getValue());   
    }
}
