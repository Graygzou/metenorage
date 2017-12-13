package Engine.System.Sound.Component;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.Main.Sound;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Sound.SoundComponent;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

/**
 * @author Noemy Artigouha
 * @author Gregoire Boiron
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
        super.setActiveState(false);
        // Initialize parameters
        this.sound = sound;
        this.volume = 100f;
        this.pitch = 0.5f;
        this.isLooping = false;
        this.velocity = new Vector3f(0,0,0);
        this.position = entity.getTransform().getPosition();
    }

    public void setVelocity(final Vector3f velocity) {
        this.setVelocity(velocity.x, velocity.y, velocity.z);
    }

    public void setVelocity(final float x, final float y, final float z) {
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
    }

    public void setLooping(final boolean loop) {
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public void setVolume(final float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, this.volume);
    }

    public void setPitch(final float pitch) {
        AL10.alSourcef(sourceId, AL10.AL_PITCH, this.pitch);
    }

    public void setPosition(final Vector3f position) {
        this.setPosition(position.x, position.y, position.z);
    }

    public void setPosition(final float x, final float y, final float z) {
        AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
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
        this.setLooping(this.isLooping);
        // Set the velocity
        this.setVelocity(this.velocity);
        // Set the volume
        this.setVolume(this.volume);
        // Set the pitch
        this.setPitch(this.pitch);
        // Set the position
        this.setPosition(this.position);
        // Stop all the current songs
        stop();
    }

    @Override
    public void onMessage(Message message) {
        try {
            switch (message.getInstruction()) {
                case "play":
                    play();
                    break;
                case "stop":
                    stop();
                    break;
                case "pause":
                    pause();
                    break;
                case "setLooping":
                    setLooping((boolean)message.getData());
                    break;
                case "setVelocity":
                    setLooping((boolean)message.getData());
                    break;
                case "setVolume":
                    setVolume((float)message.getData());
                    break;
                case "setPitch":
                    setPitch((float)message.getData());
                    break;
                case "setPosition":
                    setPosition((Vector3f)message.getData());
                    break;
                case "isPlaying":
                    System.out.println("Received ! Send again !");
                    // Create a new message to send the return value to the scriptingSystem
                    Object[] returnValues = {Boolean.class, isPlaying()};
                    Message<Object[]> returnMessage =
                            new Message<>(getID(), message.getSender(), "return", returnValues);
                    // Send it
                    GameEngine.messageQueue.add(returnMessage);
                    break;
                default:
                    System.out.println(message.getInstruction() + ": Corresponding method can't be found");
                    break;
            }
        } catch (ClassCastException exception) {
            System.out.println("Data sent can't be converted into the right type.");
            exception.printStackTrace();
        }
    }

    public void play() {
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, this.sound.getUniqueID());
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