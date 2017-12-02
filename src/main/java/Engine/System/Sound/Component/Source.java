package Engine.System.Sound.Component;

import Engine.Main.Entity;
import Engine.Main.Sound;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Sound.SoundComponent;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.openal.AL10;

/**
 * @author Noemy Artigouha
 */

public class Source extends BaseComponent implements SoundComponent {

    private int sourceId;

    private float volume;
    private float pitch;
    private boolean isLooping;
    private Vector3f velocity;
    private Vector3f position;

    private Sound sound;

    public Source(Entity entity, Sound sound) {
        super(entity);
        // Initialize parameters
        this.sound = sound;
        this.volume = 100f;
        this.pitch = 0.5f;
        this.isLooping = false;
        this.velocity = new Vector3f(0,0,0);
        this.position = entity.getPosition();
    }

    public void setVelocity(final float x, final float y, final float z) {
        this.velocity = new Vector3f(x, y, z);
    }

    public void setLooping(final boolean loop) {
        this.isLooping = loop;
    }

    public void setVolume(final float volume) {
        this.volume = volume;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public void setPosition(final float x, final float y, final float z) {
        this.position = new Vector3f(x, y, z);
    }

    @Override
    public void apply() {
        // Play the current sound
        play();
    }

    @Override
    public void initialize() {
        sourceId = AL10.alGenSources();

        // Set the looping
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, this.isLooping ? AL10.AL_TRUE : AL10.AL_FALSE);
        // Set the velocity
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, this.velocity.x, this.velocity.y, this.velocity.z);
        // Set the volume
        AL10.alSourcef(sourceId, AL10.AL_GAIN, this.volume);
        // Set the pitch
        AL10.alSourcef(sourceId, AL10.AL_PITCH, this.pitch);
        // Set the position
        AL10.alSource3f(sourceId, AL10.AL_POSITION, this.position.x, this.position.y, this.position.z);
        // Stop all the current songs
        stop();
    }

    @Override
    public void onMessage(Message message) {
        // TODO LATER...
    }

    public void play() {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, this.sound.getId());
        continuePlaying();
    }

    public void delete() {
        stop();
        AL10.alDeleteSources(sourceId);
    }

    public void pause() {
        AL10.alSourcePause(sourceId);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void continuePlaying() {
        AL10.alSourcePlay(sourceId);
    }

    public void stop() {
        AL10.alSourceStop(sourceId);
    }
}