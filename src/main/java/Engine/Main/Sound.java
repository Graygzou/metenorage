package Engine.Main;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Sound.SoundComponent;
import Engine.System.Sound.WaveData;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class Sound {

    //Path of the audio sound
    private final String pathSound;

    // ID of the sound
    private int id;

    public Sound(String path) {
        this.pathSound = path;
        loadSound();
    }

    /**
     * Load a sound to be register and use by the SoundSystem
     */
    public void loadSound() {
        this.id = AL10.alGenBuffers();
        File audioFile = new File(this.pathSound);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);
            WaveData wavFile = WaveData.create(audio);
            AL10.alBufferData(id, wavFile.format, wavFile.data, wavFile.samplerate);
            wavFile.dispose();
            System.out.println("Sound Buffer " + id + " create.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

}
