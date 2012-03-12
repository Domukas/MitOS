/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class BitEdit {
    
    public static byte setBit(byte b, int bitNumber)
    {
        return (byte) (b | (1 << bitNumber));   
    }
    
    public static byte unSetBit(byte b, int bitNumber)
    {
        return (byte) (b & ~(1 << bitNumber));   
    }
    
}
