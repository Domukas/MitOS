/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

/**
 *
 * @author Tomas
 */
public class ProcessorDescriptor {
    
    int CPUcount;
    public int number;
    public Process currentProcess;
    
    public ProcessorDescriptor(int CPUcount, int number)
    {
        this.CPUcount = CPUcount;   
        this.number = number;
    }
    
}
