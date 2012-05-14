/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

import tdzVmRm.RealMachine;
import tdzVmRm.TypeConversion;
import tdzVmRm.Word;

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
            RealMachine.proc[0].PI.setValue(1); //TODO
            RealMachine.proc[0].mode.SetSupervisor();
        }
    }
    
    public Word getValue()
    {
        return data;
    }
    
}
