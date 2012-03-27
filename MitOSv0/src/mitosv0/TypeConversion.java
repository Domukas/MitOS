/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public abstract class TypeConversion {
    
    public static byte[] intToByteArray(int value)
    {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
    
    public static int byteArrayToInt(byte[] buf)
    {
        int newInt = 0;
        
        for (int i = 3; i >= 0; i--)
        {
            newInt += ((int)buf[3-i]) << 8*i;
        }
        return newInt;  
    }
    
    public static byte[] intArrayToByteArray(int[] array)
    {
        byte[] rez = new byte[64];
        int m = 0;
        
        for (int i = 0; i < 16; i++)
        {
            byte[] temp = TypeConversion.intToByteArray(array[i]);
            for (int n = 0; n < 4; n++)
            {
                rez[m] = temp[n];
                m++;
            }
        }
        return rez;
    }
    
    
    public static int[] byteArrayToIntArray(byte[] buf)
    {
        int[] newInt = new int [16];
        byte[] bytebuf;
        
        for (int i = 0; i < 8; i++)
        {
            bytebuf = new byte[4];
            int z = 0;
            for (int m = i*4; m < i*4 + 4; m++)
            {
             bytebuf[z] = buf[m];
             z++;
            }
            
            for (int n = 3; n >= 0; n--)
            {
                newInt[i] += ((int)bytebuf[3-n]) << 8*n;
            }
        }
        return newInt;  
    }
    
    public static String byteArrayToString(byte[] array)
    {
        String temp = "";
        
        for (int i = 0; i < 4; i++)
        {
            temp += (char)array[i];
        }
        
        
        return temp;
    }
    
    public static byte[] stringToByteArray(String s)
    {
        byte[] array = new byte[4];
        
        for (int i=0; i < 4; i++)
        {
            array[i] = (byte)s.charAt(i);
            System.out.println(array[i]);
        }
        
        return array;
    }
    
}
