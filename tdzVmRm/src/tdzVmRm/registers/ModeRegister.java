/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm.registers;

/**
 *
 * @author Tomas
 */
public class ModeRegister {
    
    private boolean state;
    //True - Supervizoriaus
    //False - Vartotojo
    
    public ModeRegister()
    {
        state = false;
    }
    
    public void SetSupervisor()
    {
        state = true;
    }
    
    public boolean isSupervisor()
    {
        return state;
    }
    
    public void setUser()
    {
        state = false;
    }
    
    public boolean isUser()
    {
        return !state;
    }
    
}
