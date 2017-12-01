package Engine.Main.Light;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import org.joml.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class DirectionalLight extends Entity {
    /**
     * The color of the directional light.
     */
    private Vector3f color;

    /**
     * The direction in which the light points.
     */
    private Vector3f direction;

    /**
     * The intensity of the light.
     */
    private float intensity;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColor()), new Vector3f(light.getDirection()), light.getIntensity());
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}