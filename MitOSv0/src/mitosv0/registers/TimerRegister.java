/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

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
    }
    
    public boolean timePass(int value)
    {
        time += (byte)value;
        if (time > interval)
        {
            time -= interval;
            return true;
        }
        else 
            return false;     
    }
    
    public void setValue(int value) {
     
        time = (byte)value;
    }


    public int getValue() {
        
        return time;
    }
    
}
