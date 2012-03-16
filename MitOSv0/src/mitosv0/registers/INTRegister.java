/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

/**
 *
 * @author Tomas
 */
public class INTRegister extends Register{
    
    private byte data;
    // 	1 – Jei suveikė atminties apsauga.
    //	2 – Jei operacijos kodas neegzistuoja.


    public INTRegister()
    {
        data = 0;
    }
    
    public void setValue(int value)
    {
        data = (byte) value;
    }


    public int getValue()
    {
        return data;
    }
    
    public void clear()
    {
        data = 0;
    }
    
    
}
