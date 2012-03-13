/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

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
    
    public void setValue(Word value) {
        data = value.getByte(0);
    }

    public Word getValue() {
        Word rez = new Word();
        
        rez.setByte(data, 0);
        return rez;
    }
    
    public void setZeroFlag()
    {
        data = (byte) (data | 1); 
    }
    
    public void unsetZeroFlag()
    {
        data = (byte) (data & ~1); 
    }
    
    public void setSignFlag()
    {
        data = (byte) (data | (1 << 1)); 
    }
    
    public void unsetSignFlag()
    {
        data = (byte) (data & ~(1 << 1)); 
    }

    public void setOverflowFlag()
    {
        data = (byte) (data | (1 << 2)); 
    }
    
    public void unsetOverflowFlag()
    {
        data = (byte) (data & ~(1 << 2)); 
    }
    
}
