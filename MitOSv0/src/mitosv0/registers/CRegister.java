/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

/**
 *
 * @author Tomas
 */
public class CRegister extends Register {

    private byte data;
    
    public CRegister()
    {
        data = 0;
    }
    
    public void setValue(int value)
    {
        data = (byte)value;
    }

    public int getValue()
    {
        return data;
    }
    
    public void setZeroFlag()
    {
        data = (byte) (data | 1); 
    }
    
    public boolean isZeroFlagSet()
    {
        if ((data & 1) == 1)
            return true;
        else 
            return false;
    }
    
    public void unsetZeroFlag()
    {
        data = (byte) (data & ~1); 
    }
    
    public void setSignFlag()
    {
        data = (byte) (data | (1 << 1)); 
    }
    
    public boolean isSignFlagSet()
    {
        if (((data >> 1) & 1) == 1)
            return true;
        else 
            return false;
    }
    
    public void unsetSignFlag()
    {
        data = (byte) (data & ~(1 << 1)); 
    }

    public void setOverflowFlag()
    {
        data = (byte) (data | (1 << 2)); 
    }
    
    public boolean isOverflowFlagSet()
    {
        if (((data >> 2) & 1) == 1)
            return true;
        else 
            return false;
    }
    
    public void unsetOverflowFlag()
    {
        data = (byte) (data & ~(1 << 2)); 
    }
    
}
