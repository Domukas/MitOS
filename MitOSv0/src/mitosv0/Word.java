/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class Word {
    
    byte[] data;
    
    public Word()
    {
       data = new byte[4];
    }
    
    public void setValue(byte[] data)
    {
        this.data = data;
    }
    
    public void setByte(byte b, int number)
    {
        data[number] = b;   
    }
    
    public byte[] getData()
    {
        return data;
    }
    
    public byte getByte(int number)
    {
        return data[number];
    }
    
}
