package Engine.Main;

import Engine.GameEngine;
import Engine.System.Component.Component;
import Engine.System.Component.Transform;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
 * @author Gregoire Boiron
 */

public class Entity {

    private static int currentIDNumber = 0;

    /**
     * A unique ID used to identify the entity and its components.
     */
    private int uniqueID;

    private String name;

    private String tag;

    private Transform transform;

    //List of components attached to the entity.
    private List<Component> components;


    public Entity(String name) {
        this.name = name;
        this.components = new ArrayList<Component>();
        this.transform = new Transform(this);
        // Create a "ramdom" ID for the entity.
        this.uniqueID = Entity.currentIDNumber;
        Entity.currentIDNumber++;
        // Register the component to be able to communicate with him.
        GameEngine.componentManager.registerComponent(this.transform);
    }

    public Entity() {
        this.components = new ArrayList<Component>();
        this.transform = new Transform(this);
        GameEngine.componentManager.registerComponent(this.transform);
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        this.components.add(component);
        // Register the component to access it through messaging queue
        GameEngine.componentManager.registerComponent(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
        GameEngine.componentManager.removeComponent(component);
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public Integer getUniqueID() { return this.uniqueID; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() { return this.tag; }

    public void setTag(String tag) { this.tag = tag; }

    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public String toString() {
        return this.name != null ? this.name : super.toString();
    }
}
