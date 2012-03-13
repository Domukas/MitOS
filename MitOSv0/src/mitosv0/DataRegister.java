/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class DataRegister extends Register {

    protected Word data;
    
    public DataRegister()
    {
        data = new Word();
    }
    
    public void setValue(Word value) {
        data = value;
    }
    
    public Word getValue() {
        return data;
    }
    
}
