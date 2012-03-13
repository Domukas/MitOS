/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class ICRegister extends Register {

    private byte[] data; 
    
    public ICRegister()
    {
        data = new byte[2];
        data[0] = 0;
        data[1] = 0;
    }
    
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
    
    
}
