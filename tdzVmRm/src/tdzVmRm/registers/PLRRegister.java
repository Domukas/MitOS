/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

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

    public String getValue() {
        String tmp = Integer.toHexString(data);
        while (tmp.length() < 4)
            tmp = "0"+tmp;
        return tmp;
    }
    
    public byte getA0()
    {
        return (byte)(data >>> 12);
    }
    
    public void setA0(byte value)
    {
        //int intValue = (int) value & 0xff;
        //data = (data & 0x00FFFFFF) | (intValue << 24);
        int intValue = (int) value & 0xf;
        data = (data & 0x0FFF) | (intValue << 12);
    }
    
    public int getA1()
    {
        return (data >>> 8) % 0x10;
    }
    
    public void setA1(byte value)
    {
        //int intValue = (int) value & 0xff;
        //data = (data & 0xFF00FFFF) | (intValue << 16);
        int intValue = (int) value & 0xf;
        data = (data & 0xF0FF) | (intValue << 8);
    }
    
    public int getA2()
    {
        return ((data / 0x10) % 0x10);
    }
    
    public void setA2(byte value)
    {
        //int intValue = (int) value & 0xff;
        //intValue = intValue * 0x100;
        //data = ((data & 0xFFFF00FF) | intValue);
        int intValue = (int) value & 0xf;
        intValue = intValue * 0x10;
        data = ((data & 0xFF0F) | intValue);
    }
    
    public int getA3()
    {
        return data % 0x10;
    }
    
    public void setA3(byte value)
    {
        //int intValue = (int) value & 0xff;
        //int tempData = (data & 0xFFFFFF00);
        //data = (tempData | intValue);
        int intValue = (int) value & 0xf;
        int tempData = (data & 0xFFF0);
        data = (tempData | intValue);
    }
}
