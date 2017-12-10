package Engine.Main;

import Engine.GameEngine;
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

    public String name;

    protected Vector3f position;

    protected Vector3f rotation;

    protected Vector3f scale;

    /**
     * List of components attached to the entity.
     */
    private List<Component> components;

    public Entity(String name) {
        this.name = name;
        this.components = new ArrayList<Component>();
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1f, 1f, 1f);
    }

    public Entity() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f();
        this.components = new ArrayList<Component>();
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

    public void rotate(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float s) {
        this.scale.x = s;
        this.scale.y = s;
        this.scale.z = s;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if (offsetX != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public void movePosition(Vector3f offset) {
        this.movePosition(offset.x, offset.y, offset.z);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name != null ? this.name : super.toString();
    }
}
