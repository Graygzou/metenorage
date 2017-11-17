package Engine.Main;

import Engine.System.Component.Component;
import org.joml.Vector3f;

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

    private Vector3f position;

    private Vector3f rotation;

    private Vector3f scale;

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

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
