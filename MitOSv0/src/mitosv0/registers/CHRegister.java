/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0.registers;

/**
 *
 * @author Tomas
 */
public class CHRegister {
    
    private boolean open;
    
    public void setClosed()
    {
        open = false;
    }
    
    public void setOpen()
    {
        open = true;
    }
    
    public boolean isOpen()
    {
        return open;
    }
}
