/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

/**
 *
 * @author Tomas
 */
public class RealMachine {
    private PLRRegister PLR;
    private DataRegister R1, R2;
    private ICRegister IC;
    private CRegister C;
    private SemaphoreRegister S;
    private TimerRegister timer; //Timer ir mode gal kazkaip kitaip pervadint
    private ModeRegister mode;
    private INTRegister PI, SI;
    private CHRegister CH1, CH2, CH3, CH4;
 
    public RealMachine()
    {
        PLR = new PLRRegister();
        R1 = new DataRegister();
        R2 = new DataRegister();
        IC = new ICRegister();
        C = new CRegister();
        S = new SemaphoreRegister();
        timer = new TimerRegister(3); //Skaicius???
        mode  = new ModeRegister();
        PI = new INTRegister();
        SI = new INTRegister();
        CH1 = new CHRegister();
        CH2 = new CHRegister();
        CH3 = new CHRegister();
        CH4 = new CHRegister();
    }
    
}
