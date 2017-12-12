package Engine.Main;

import Engine.GameEngine;
import Engine.System.Component.Component;
import Engine.System.Component.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
 */

public class Entity {
    /**
     * A unique ID used to identify the entity and its components.
     */
    public int uniqueID;

    public String name;

    private Transform transform;

    //List of components attached to the entity.
    private List<Component> components;


    public Entity(String name) {
        this.name = name;
        this.components = new ArrayList<Component>();
        this.transform = new Transform(this);
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
        return components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public String toString() {
        return this.name != null ? this.name : super.toString();
    }
}
