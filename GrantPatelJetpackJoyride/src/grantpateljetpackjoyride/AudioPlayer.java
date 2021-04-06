/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author cogra9807
 */
public class AudioPlayer {
    
    private Clip audioClip;
    private AudioInputStream audioStream;
    
    public AudioPlayer(String filepath)
            throws UnsupportedAudioFileException,
        IOException, LineUnavailableException 
    {
        
        //reads the wav file into a buffered input stream to use (works for both netbeans and jar)
        BufferedInputStream ais = new BufferedInputStream(MainGUI.class.getResourceAsStream(filepath));

        //reads the audio stream from the input stream
        audioStream = AudioSystem.getAudioInputStream(ais);
        
        //gets the format to pass into the dataline object
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        //gets the audio clip to play from the line
        audioClip = (Clip) AudioSystem.getLine(info);

        //the audio clip opens the stream to read from
        audioClip.open(audioStream);
        //loops continuously
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        //starts playing the audio
        audioClip.start();
    }
    
    public void stop(){
        //stops the clip
        audioClip.stop();
        try{
            //closes the stream to free resources
            audioStream.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
