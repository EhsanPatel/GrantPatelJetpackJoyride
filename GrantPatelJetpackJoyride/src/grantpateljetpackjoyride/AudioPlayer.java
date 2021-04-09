/*
Colin Grant and Ehsan Patel
April 5, 2021
Audio Player class - creates an audio player to play a .wav file for game sounds
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
    
    //instance variables
    private Clip audioClip;
    private AudioInputStream audioStream;
    
    /**
     * creates audioPlayer with specified attributes
     * @param filepath the filepath of the .wav file
     * @param isRepeating if the file will loop or not
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException 
     */
    public AudioPlayer(String filepath, boolean isRepeating)
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
        if(isRepeating){
            //loops continuously
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        
        //starts playing the audio
        audioClip.start();
    }
    
    public void stop(){
        //stops the clip
        audioClip.stop();
        
        //closes the clip without freezing the game
        Runnable runnable =() -> {audioClip.close();};
        new Thread(runnable).start();
        
        try{
            //closes the stream to free resources
            audioStream.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
