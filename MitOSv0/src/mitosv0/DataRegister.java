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

    protected int data;
    
    public DataRegister()
    {
        data = 0;
    }
    
    public void setValue(int value) {
        data = value;
    }
    
    public int getValue() {
        return data;
    }
    
}
