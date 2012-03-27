/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

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
        return Integer.parseInt(data, 16);
    }
    
    public String getValue()
    {        
        return data;
    }
    
    public void setValue(String s)
    {
        if (s.length() <= 4)
            data = s;
    }
    
    public void setIntValue(int s)
    {
        data = Integer.toHexString(s);
    }
}
