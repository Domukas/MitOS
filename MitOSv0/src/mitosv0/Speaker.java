/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mitosv0;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Zory
 */
public class Speaker {
    private int volume;
    public Speaker(){
        
    }
    
    public void play(int xx)throws LineUnavailableException{
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.start();
        }catch(MidiUnavailableException e){
        }
    }
        
    public void setVolume(int xx){
    }
}