package project.monsterinc;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;


public class SfxController {
    private Clip clip;
    public SfxController(String path) {
        try {
            InputStream filepath = SfxController.class.getResource(path).openStream();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(filepath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void play() {
        clip.start();
    }
    public void playTime(int n) {
        clip.loop(n);
    }
    public void playLoop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
    public void adjustVolume(float n) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN) ;
        gainControl.setValue(n);
    }
}