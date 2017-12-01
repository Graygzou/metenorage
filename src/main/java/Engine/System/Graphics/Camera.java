package Engine.System.Graphics;

import Engine.Main.Entity;
import org.joml.Vector3f;

/**
 * @author : Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class Camera extends Entity {
    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }
}