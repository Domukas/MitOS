/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Zory
 */
public class Speaker {
    
    public Speaker(){
        
    }
    
    public void play(int xx)throws LineUnavailableException{//fiksuotas kol kas viskas. nlb skiriasi dazniai reikia kazkokiu kitokiu budu daryti ta garsa, ieskau
        final AudioFormat af = new AudioFormat(16 * 1024, 8, 1, true, true);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
            line.open(af, 16 * 1024);
            line.start();
            
            int length = 16 * 1024 * Math.min(500, 2 * 1000) / 1000;
            byte[] sin = new byte[2 * 16 * 1024];
            
            sin[0] = (byte)(Math.sin(xx) * 127f);
            line.write(sin, 0, length);
            
            line.drain();
        }catch(Exception e){       
        }
    }
        
    public void volume(int xx){
    }
}