package Engine.Main;

import Engine.GameEngine;
import Engine.System.Component.Component;
import Engine.System.Component.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
 * @author Gregoire Boiron
 */

public class Entity extends Metadata {

    private String name;

    private String tag;

    private Transform transform;

    //List of components attached to the entity.
    private List<Component> components;


    public Entity(String name) {
        super();
        this.name = name;
        this.components = new ArrayList<Component>();
        this.transform = new Transform(this);
    }

    public Entity() {
        this("");
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        this.components.add(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
    }

    public List<Component> getComponents() {
        return this.components;
    }

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
