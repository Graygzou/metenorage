package Engine.System.Sound;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;

import java.util.List;

import static org.lwjgl.openal.ALC10.*;

/**
 * @author Noemy Artigouha
 */

public class SoundSystem extends BaseSystem {

    private long device;
    private long context;

    public SoundSystem() {
        final String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        AL.createCapabilities(ALC.createCapabilities(device));
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return SoundComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        this.setListenerData(0,0,0);
    }

    /*Select the position of the listener sound*/
    public void setListenerData(final float x, final float y, final float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    @Override
    public void iterate(List<Entity> entities) {
        // For all the entity in the game
        for(Entity entity : entities) {
            // For the audio components
            for (Component component : getLocalSystemComponentsFor(entity)) {
                // Active them
                component.initialize();
                if(component.isActive()) {
                    component.apply();
                }
            }
        }
    }

    @Override
    public void cleanUp() {
        //Terminate OpenAL
        alcMakeContextCurrent(0);
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

}
