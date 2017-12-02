package Engine.System.Sound;

import org.lwjgl.openal.AL10;

/**
 * @author Noemy Artigouha
 */

public class Source {
    private int sourceId;

    public Source() {
        sourceId = AL10.alGenSources();
    }

    public void play(final int buffer) {
        stop();
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        continuePlaying();
    }

    public void delete() {
        stop();
        AL10.alDeleteSources(sourceId);
    }

    public void pause() {
        AL10.alSourcePause(sourceId);
    }

    public void continuePlaying() {
        AL10.alSourcePlay(sourceId);
    }

    public void stop() {
        AL10.alSourceStop(sourceId);
    }

    public void setVelocity(final float x, final float y, final float z) {
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
    }

    public void setLooping(final boolean loop) {
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVolume(final float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    public void setPitch(final float pitch) {
        AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
    }

    public void setPosition(final float x, final float y, final float z) {
        AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
    }
}