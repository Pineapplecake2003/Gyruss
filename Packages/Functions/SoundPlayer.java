package Packages.Functions;

import javax.sound.sampled.*;
import java.io.*;

public class SoundPlayer {

    private File file;
    private AudioInputStream audio;
    private Clip player;

    public SoundPlayer(String path) {
        setMusic(path);
    }

    public void setMusic(String path) {
        try {
            file = new File(path);
            audio = AudioSystem.getAudioInputStream(file);
            player = AudioSystem.getClip();
            player.open(audio);
        } catch (Exception e) {
        }
    }

    public void PlayMusic() {
        player.setMicrosecondPosition(0);
        player.start();
    }

    public void PlayLoopMusic(){
        player.loop(Clip.LOOP_CONTINUOUSLY);
        player.start();
    }

    public void StopPlay() {
        player.stop();
    }
}
