package Engine.Main;

import Engine.System.Component.Component;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class Entity {
    /**
     * A unique ID used to identify the entity and its components.
     */
    public int uniqueID;

    /**
     * List of components attached to the entity.
     */
    private List<Component> components;

    public Entity() {
        this.components = new ArrayList<Component>();
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        this.components.add(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
    }

    public List<Component> getComponents() {
        return components;
    }
}
