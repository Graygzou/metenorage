package Engine.Main.Light;

import Engine.Main.Entity;
import org.joml.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PointLight extends Entity {
    /**
     * The color of the point light.
     */
    private Vector3f color;

    /**
     * The position of the point light.
     */
    private Vector3f position;

    /**
     * The intensity of the point light.
     */
    private float intensity;

    /**
     * The attenuation of the point light.
     */
    private Attenuation attenuation;

    public PointLight(Vector3f color, Vector3f position, float intensity) {
        attenuation = new Attenuation(1, 0, 0);

        this.color = color;
        this.position = position;
        this.intensity = intensity;
    }

    public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
        this(color, position, intensity);
        this.attenuation = attenuation;
    }

    public PointLight(PointLight pointLight) {
        this(new Vector3f(pointLight.getColor()), new Vector3f(pointLight.getPosition()),
                pointLight.getIntensity(), pointLight.getAttenuation());
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    /**
     * Helps simulating light attenuation, which depends on the distant and the type of light. The intensity of light
     * is inversely proportional to the square of its distance. The attenuation factor is multiplied to the final
     * color of each fragment.
     */
    public static class Attenuation {
        /**
         * Constant value of attenuation at any given point.
         */
        private float constant;

        /**
         * Evolution of the attenuation with respect to the distance to the source.
         */
        private float linear;

        /**
         * Evolution of the attenuation with respect to the square of the distance to the source.
         */
        private float exponent;

        public Attenuation(float constant, float linear, float exponent) {
            this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
        }

        public float getConstant() {
            return constant;
        }

        public void setConstant(float constant) {
            this.constant = constant;
        }

        public float getLinear() {
            return linear;
        }

        public void setLinear(float linear) {
            this.linear = linear;
        }

        public float getExponent() {
            return exponent;
        }

        public void setExponent(float exponent) {
            this.exponent = exponent;
        }
    }
}
