/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

import mitosv0.TypeConversion;
import mitosv0.Word;

/**
 *
 * @author Tomas
 */
public class DataRegister {

    private Word data;
    
    public DataRegister()
    {
        data = new Word();
    }
    
    public void setValue(Word value)
    {
        data = value;
    }
    
    public Word getValue()
    {
        return data;
    }
    
}
