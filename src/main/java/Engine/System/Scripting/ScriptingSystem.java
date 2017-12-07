package Engine.System.Scripting;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.List;

public class ScriptingSystem extends BaseSystem {

    public ScriptingSystem() {

    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {

        return ScriptingComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        // nothing yet ?
    }

    @Override
    public void iterate(List<Entity> entities) {
        // For all the entity in the game
        for(Entity entity : entities) {
            // For the audio components
            for (Component component : getLocalSystemComponentsFor(entity)) {
                // Active them
                component.initialize();
                component.apply();
            }
        }
        /*
        TODO do it again with 2 lists (beginning + update)
        for(Entity entity : entities) {
            // For the audio components
            for (Component component : getLocalSystemComponentsFor(entity)) {
                // Active them
                component.initialize();
                component.apply();
            }
        }*/
    }

    @Override
    public void cleanUp() { }
}
