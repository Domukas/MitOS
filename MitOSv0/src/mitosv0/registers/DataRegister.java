/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

import mitosv0.RealMachine;
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
        try
        {
            Integer.parseInt(value.getValue(), 16);
            data = value;
        }
        catch (NumberFormatException e)
        {
            RealMachine.gui.showMessage(value.getValue() + " is not a valid number");
            RealMachine.PI.setValue(1);
            RealMachine.mode.SetSupervisor();
        }
    }
    
    public Word getValue()
    {
        return data;
    }
    
}
