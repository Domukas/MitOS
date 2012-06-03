/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

/**
 *
 * @author Tomas
 */
public class SemaphoreRegister{

    private short data;
    
    public void setValue(int value) {
        
        data = (short)value;
        
    }

    public int getValue() {
        return data;
    }
    
    public SemaphoreRegister()
    {
        data = 0;
    }
    
    public void setBit(int number)
    {
        short temp = (short)(1 << number);
        data = (short)(data | temp);
        
    }
    
    public void unsetBit(int number)
    {
        short temp  = (short)(1 << number);
        short temp2 = (short)(data ^ temp);
        data = (short)(data & temp2);
    }
    
    public boolean isBitSet(int number)
    {
        short temp  = (short)(1 << number);
        temp  = (short)(temp & data);
        temp  = (short)(temp >> number);
        
        if (temp == 0)
            return false;
        else 
            return true;
    }
}
