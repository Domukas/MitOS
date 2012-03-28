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
    private static int volume;
    private static int length;
    public Speaker(){
        volume = 70;
        length = 1000;
    }
    
    public void play(int xx){
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat((float)44100, 8, 1, true, false);
        SourceDataLine sdl;
        try
        {
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for(int i = 0; i < length*(float)44100/1000; i++) {
                double angle = i/((float)44100/xx)*2.0*Math.PI;
                buf[0] = (byte)(Math.sin(angle)*volume);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e)
        {
            System.out.println("Speaker error!");
        }
    }
        
    public void setVolume(int xx){
        volume = xx;
    }
    
    public void setLength(int xx){
        length = xx;
    }
}