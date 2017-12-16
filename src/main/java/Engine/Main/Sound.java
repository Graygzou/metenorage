package Engine.Main;

import Engine.System.Sound.WaveData;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

/**
 * @author Noemy Artigouha
 * @author Gregoire Boiron
 */

public class Sound extends Metadata {

    //Path of the audio sound
    private String pathSound;

    private String name;

    public Sound() {
        super();
        this.name = "";
        this.pathSound = "";
    }

    public Sound(String name, String path) {
        super();
        this.name = name;
        this.pathSound = path;
        loadSound();
    }

    /**
     * Load a sound to be register and use by the SoundSystem
     */
    public void loadSound() {
        this.uniqueID = AL10.alGenBuffers();
        File audioFile = new File(this.pathSound);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);
            WaveData wavFile = WaveData.create(audio);
            AL10.alBufferData(this.uniqueID, wavFile.format, wavFile.data, wavFile.samplerate);
            wavFile.dispose();
            System.out.println("Sound Buffer " + this.uniqueID + " create.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPathSound(String pathSound) { this.pathSound = pathSound; }
    public void setName(String name) { this.name = name; }

    public String getPathSound() { return this.pathSound; }
    public String getName() { return this.name; }

}
