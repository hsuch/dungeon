package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.File;
import java.net.URL;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class SoundManager {

    private HashMap soundeffects = new HashMap();

    public void LoadSoundEffect(String id, String filename) {
        soundeffects.put(id, filename);
    }

    public HashMap getsoundeffects() {
        return soundeffects;
    }

    public void PlaySoundEffect(String id) {
        String file = (String)soundeffects.get(id);
        System.out.println(file);
        File sound_file = new File(file);
        try {
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound_file);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
