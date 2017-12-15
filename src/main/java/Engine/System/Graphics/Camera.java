package Engine.System.Graphics;

import Engine.Main.Entity;
import org.joml.Vector3f;

/**
 * @author : Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class Camera extends Entity {
    public Camera() {
        this.getTransform().setPosition(new Vector3f(0, 0, 0));
        this.getTransform().setRotation(new Vector3f(0, 0, 0));
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.getTransform().setPosition(position);
        this.getTransform().setRotation(rotation);
    }

}