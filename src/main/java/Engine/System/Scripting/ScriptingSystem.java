package Engine.System.Scripting;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.LinkedList;
import java.util.List;

public class ScriptingSystem extends BaseSystem {

    private List<Entity> notStartedEntities;
    private List<Entity> startedEntities;

    public ScriptingSystem() {

    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {

        return ScriptingComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        this.notStartedEntities = new LinkedList<>();
        this.startedEntities = new LinkedList<>();
    }

    @Override
    public void iterate(List<Entity> entities) {
        // initialize the first time list
        if(this.notStartedEntities.isEmpty() && this.startedEntities.isEmpty()) {
            this.notStartedEntities = new LinkedList<>(entities);

            // Call start method for each component script
            for(Entity entity : this.notStartedEntities) {
                // For the script components
                for (Component component : getLocalSystemComponentsFor(entity)) {
                    // Active them
                    component.initialize();
                }

                // Change the current entity
                this.startedEntities.add(entity);
            }
            this.notStartedEntities.removeAll(this.notStartedEntities);
        }

        // For all the entity in the game
        for(Entity entity : this.startedEntities) {
            // For the audio components
            for (Component component : getLocalSystemComponentsFor(entity)) {
                component.apply();
            }
        }
    }

    @Override
    public void cleanUp() { }
}
