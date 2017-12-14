package Engine.Managers;

import Engine.System.Component.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
     * @param entityComponents
     */
    public void registerComponent(Component... entityComponents) {
        for(Component component : entityComponents) {
            this.componentMap.put(component.getID(), component);
        }
    }

    public List<Component> getComponents() { return new LinkedList<>(this.componentMap.values()); }

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
     * @param entityComponents
     */
    public void removeComponent(Component... entityComponents) {
        for(Component component : entityComponents) {
            this.componentMap.remove(component.getID());
        }
    }

    /**
     * clears all components from the manager
     */
    public void Reset(){
        this.componentMap.clear();
    }

}
