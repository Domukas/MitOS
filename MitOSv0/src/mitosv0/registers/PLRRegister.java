/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

/**
 *
 * @author Tomas
 */
public class PLRRegister{

    private int data;
    
    public PLRRegister()
    {
        data = 0;
    }
    
    public void setValue(int value) {
        data = value;
    }

    public int getValue() {
        return data;
    }
    
    public byte getA0()
    {
        return (byte)(data >>> 24);
    }
    
    public void setA0(byte value)
    {
        int intValue = (int) value & 0xff;
        data = (data & 0x00FFFFFF) | (intValue << 24);
    }
    
    public int getA1()
    {
        return (data >>> 16) % 0x100;
    }
    
    public void setA1(byte value)
    {
        int intValue = (int) value & 0xff;
        data = (data & 0xFF00FFFF) | (intValue << 16);
    }
    
    public int getA2()
    {
        return ((data / 0x100) % 0x100);
    }
    
    public void setA2(byte value)
    {
        int intValue = (int) value & 0xff;
        intValue = intValue * 0x100;
        data = ((data & 0xFFFF00FF) | intValue);
    }
    
    public int getA3()
    {
        return data % 0x100;
    }
    
    public void setA3(byte value)
    {
        int intValue = (int) value & 0xff;
        int tempData = (data & 0xFFFFFF00);
        data = (tempData | intValue);
    }
}
