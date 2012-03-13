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

    public void setValue(Word value) {
        data = value;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Word getValue() {
        return data;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
