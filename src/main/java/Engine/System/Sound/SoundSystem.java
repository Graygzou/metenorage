package Engine.System.Sound;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.ALC10.*;

/**
 * @author Noemy Artigouha
 */

public class SoundSystem extends BaseSystem {
    private List<Integer> buffers;
    private long device;
    private long context;

    public SoundSystem() {
        buffers = new ArrayList<>();
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return SoundComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        final String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        AL.createCapabilities(ALC.createCapabilities(device));
    }

    /*Select the position of the listener sound*/
    public void setListenerData(final float x, final float y, final float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public int loadSound(final String file) {
        final int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        System.out.println("Sound Buffer " + buffer);
        WaveData wavFile = WaveData.create(file);
        //wavFile = WaveData.create(new URL(file));
        AL10.alBufferData(buffer, wavFile.format, wavFile.data, wavFile.samplerate);
        wavFile.dispose();
        return buffer;
    }

    @Override
    public void cleanUp() {
        for (final int buffer : buffers) {
            alDeleteBuffers(buffer);
        }
        //Terminate OpenAL
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

}
