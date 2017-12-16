package Engine.Main;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Gregoire Boiron
 */

import Engine.System.Graphics.Texture;
import Engine.Managers.TexturesManager;
import org.joml.Vector4f;

public class Material extends Metadata {
    /**
     * The default color of a material when no further info is provided.
     */
    private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    /**
     * Models light that comes from everywhere. Used to illuminate the areas that are not hit by any light.
     */
    private Vector4f ambientColor;

    /**
     * Models light on the surfaces that are facing the source.
     */
    private Vector4f diffuseColor;

    /**
     * Models how light reflects on polished or metallic surfaces.
     */
    private Vector4f specularColor;

    private float reflectance;

    /**
     * The texture associated to the material. This changes how the final color of fragments is computed.
     */
    private Texture texture;
    private String textureName;

    public Material() {
        super();
        this.ambientColor = DEFAULT_COLOUR;
        this.diffuseColor = DEFAULT_COLOUR;
        this.specularColor = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector4f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(String textureName) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, textureName, 0);
    }

    public Material(String textureName, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, textureName, reflectance);
    }

    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, String textureName, float reflectance) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.textureName = textureName;
        this.reflectance = reflectance;
    }

    public void initialize() {
        if(!TexturesManager.getInstance().getTextures().containsKey(textureName)) {
            try {
                TexturesManager.getInstance().addTexture(textureName, new Texture(textureName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.texture = TexturesManager.getInstance().getTexture(textureName);
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getTextureName() { return this.textureName; }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
