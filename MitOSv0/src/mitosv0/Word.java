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
    
    public long getLong()
    {
        long rez = 0;
        int pow = 0;
        int val;
        
        for (int i = 0; i < 3; i++)
        {
            for (int n = 0; n < 8; n++)
            {
                val = data[i]>>(n) & 0x0001;
                if (val == 1)
                    rez = rez += ((int) Math.pow(2, pow));
                
                pow++;
            }
        }   
        
        for (int n = 0; n < 7; n++)
            {
                val = data[3]>>(n) & 0x0001;
                if (val == 1)
                    rez = rez += ((int) Math.pow(2, pow));
                pow++;
            }
        
        val = data[3]>>(8) & 0x0001;
        if (val == 1)
            rez *= -1;
        
        return rez;
    }
    
}
