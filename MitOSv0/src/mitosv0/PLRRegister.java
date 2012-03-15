/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class PLRRegister extends Register {

    private int data;
    
    public void setValue(int value) {
        data = value;
    }

    public int getValue() {
        return data;
    }
    
}
