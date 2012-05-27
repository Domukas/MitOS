/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

/**
 *
 * @author Tomas
 */
public class TimerRegister{
   
    private byte interval;
    private byte time;
    
    public TimerRegister(int interval)
    {
        this.interval = (byte)interval;
        this.time = this.interval;
    }
    
    public void timePass(int value)
    {
        time -= (byte)value;
        if (time < 0)
            time = 0;
    }
    
    public void setValue(int value) {
     
        time = (byte)value;
    }
    
    public void reset()
    {
        time = interval;
    }


    public int getValue() {
        
        return time;
    }
    
    public int getInterval()
    {
        return interval;
    }
    
}
