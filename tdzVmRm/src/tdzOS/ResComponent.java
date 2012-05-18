/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

/**
 *
 * @author Tomas
 */
public class ResComponent
{
    public Object value;
    public Resource parent; //Kokio resurso komponentas
    
    public ResComponent(Object value, Resource parent)
    {
        this.value = value;
        this.parent = parent;
    }
}
