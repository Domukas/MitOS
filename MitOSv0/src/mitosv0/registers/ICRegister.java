/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

/**
 *
 * @author Tomas
 */
public class ICRegister{

    private short data; 
    
    public ICRegister()
    {
        data = 0;
    }
    
    public void setValue(int value) {
        data = (short)value;
    }
    
    public void setValue(short value) {
        data = value;
    }    

    public int getValue() {
        return data;
    }
    
    public short getValueShort() {
        return data;
    }
    
    
}
