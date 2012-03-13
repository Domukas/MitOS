/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class SemaphoreRegister extends Register {

    private byte[] data = new byte[2];
    
    public void setValue(Word value) {
        data[0] = value.getByte(0);
        data[1] = value.getByte(1);
    }

    public Word getValue() {
        Word rez = new Word();
        
        rez.setByte(data[0], 0);
        rez.setByte(data[1], 1);
        
        return rez;   
    }
    
    public SemaphoreRegister() //Pradzioj visi locked ar unlocked?
    {
        data = new byte[2];
        data[0] = 0;
        data[1] = 0;
    }
    
    public void setBit(int number)
    {
        if (number >= 8)
        {
            number -= 8;
            data[1] = (byte) (data[1] | (1 << number)); 
        }
        else 
        {
            data[0] = (byte) (data[0] | (1 << number));
        }   
    }
    
    public void unsetBit(int number)
    {
        if (number >= 8)
        {
            number -= 8;
            data[1] = (byte) (data[1] & ~(1 << number)); 
        }
        else 
        {
            data[0] = (byte) (data[0] & ~(1 << number));
        }   
    }
        
}
