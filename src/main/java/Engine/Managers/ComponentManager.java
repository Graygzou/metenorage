package Engine.Managers;

import Engine.System.Component.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregoire Boiron <gregoire.boiron@gmail.com>
 */
public class ComponentManager {

    private static ComponentManager instance;

    private Map<Integer, Component> componentMap = new HashMap<>();

    public ComponentManager() {
        instance = this;
    }

    /**
     * Use a singleton pattern to represent the component Manager
     * @return the instance of the class
     */
    public static ComponentManager getInstance() {
        if(instance == null)
            instance = new ComponentManager();

        return instance;
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
