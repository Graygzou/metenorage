package Engine.System.Graphics.Component;

import Engine.Main.Entity;

/**
 * @author : Gregoire Boiron
 * @author : Matthieu Le Boucher
 */
public class Cube extends Mesh3D {

    private Cube(Entity entity) {
        super(entity);

        vertices = new float[]{
                -0.5f, 0.5f, 0.5f, 1f,
                -0.5f, -0.5f, 0.5f, 1f,
                0.5f, -0.5f, 0.5f, 1f,
                0.5f, 0.5f, 0.5f, 1f,
                -0.5f, 0.5f, -0.5f, 1f,
                0.5f, 0.5f, -0.5f, 1f,
                -0.5f, -0.5f, -0.5f, 1f,
                0.5f, -0.5f, -0.5f, 1f,
                // Repeat vertices for texture coordinates.
                -0.5f, 0.5f, -0.5f, 1f,
                0.5f, 0.5f, -0.5f, 1f,
                -0.5f, 0.5f, 0.5f, 1f,
                0.5f, 0.5f, 0.5f, 1f,
                0.5f, 0.5f, 0.5f, 1f,
                0.5f, -0.5f, 0.5f, 1f,
                -0.5f, 0.5f, 0.5f, 1f,
                -0.5f, -0.5f, 0.5f, 1f,
                -0.5f, -0.5f, -0.5f, 1f,
                0.5f, -0.5f, -0.5f, 1f,
                -0.5f, -0.5f, 0.5f, 1f,
                0.5f, -0.5f, 0.5f, 1f
        };

        textureCoordinates = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f
        };

        indices = new int[] {
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7
        };

        this.verticesCount = vertices.length;
        this.indicesCount = indices.length;
        this.textureCoordinatesCount = textureCoordinates.length;
    }

    public Cube(Entity entity, String textureName) {
        this(entity);
        this.textureName = textureName;
    }
}
