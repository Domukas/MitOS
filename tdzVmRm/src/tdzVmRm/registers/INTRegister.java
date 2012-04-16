/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

/**
 *
 * @author Tomas
 */
public class INTRegister{
    
    private byte data;


    public INTRegister()
    {
        data = 0;
    }
    
    public void setValue(int value)
    {
        data = (byte) value;
    }


    public int getValue()
    {
        return data;
    }
    
    public void clear()
    {
        data = 0;
    }
    
    
}
