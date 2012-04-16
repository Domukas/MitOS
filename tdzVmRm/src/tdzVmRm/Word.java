/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzVmRm;

/**
 *
 * @author Tomas
 */
public class Word {
    
    private String data;
    
    public Word()
    {
        data = "0";
    }
    
    public Word (int s)
    {
        setIntValue(s);
    }
    
    public Word (String s)
    {
        setValue(s);
    }
    
    public int getIntValue()
    {
        try
        {
            return Integer.parseInt(data, 16);
        }
        catch(NumberFormatException e)
        {
            RealMachine.PI.setValue(2);
            RealMachine.gui.updateAll();
            RealMachine.gui.showMessage("'"+data+"' is not an integer.");
            return 0;
        }
        
    }
    
    public String getValue()
    {        
        return data.toUpperCase();
    }
    
    public void setValue(String s)
    {
            
        if (s.length() <= 4)
            data = s;
        else 
            data = s.substring(0, 4);
    }
    
    public void setIntValue(int s)
    {
        data = Integer.toHexString(s);
        if (data.length() > 4)
            data = data.substring(data.length()-4, data.length());
    }
}
