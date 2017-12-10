package Engine.System.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregoire Boiron <gregoire.boiron@gmail.com>
 */
public class ComponentManager {

    private Map<Integer, Component> componentMap;

    public ComponentManager() {
        this.componentMap = new HashMap<>();
    }

    /**
     * Register a component in the manager
     */
    public void registerComponent(Component component) {
        this.componentMap.put(component.getID(), component);
    }

    /**
     * Return a component based on the id given.
     * @param id id of the component.
     * @return Component if found, or null if not.
     */
    public Component getComponentFromID(int id) {
        return this.componentMap.get(id);
    }

    /**
     * Remove a component from the manager
     * @param component
     */
    public void removeComponent(Component component) {
        this.componentMap.remove(component.getID());
    }

    /**
     * clears all components from the manager
     */
    public void Reset(){
        this.componentMap.clear();
    }

}
