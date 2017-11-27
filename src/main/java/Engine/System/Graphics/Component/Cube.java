package Engine.System.Graphics.Component;

import Engine.Main.Entity;

/**
 * @author : Gregoire Boiron
 * @author : Matthieu Le Boucher
 */
public class Cube extends Mesh3D {

    public Cube(Entity entity) {
        super(entity);

        vertices = new float[]{
                // VO
                -0.5f, 0.5f, 0.5f, 1f,
                // V1
                -0.5f, -0.5f, 0.5f, 1f,
                // V2
                0.5f, -0.5f, 0.5f, 1f,
                // V3
                0.5f, 0.5f, 0.5f, 1f,
                // V4
                -0.5f, 0.5f, -0.5f, 1f,
                // V5
                0.5f, 0.5f, -0.5f, 1f,
                // V6
                -0.5f, -0.5f, -0.5f, 1f,
                // V7
                0.5f, -0.5f, -0.5f, 1f,
        };

        indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        colors = new float[]{
                0.5f, 0.0f, 0.0f, 1f,
                0.0f, 0.5f, 0.0f, 1f,
                0.0f, 0.0f, 0.5f, 1f,
                0.0f, 0.5f, 0.5f, 1f,
                0.5f, 0.0f, 0.0f, 1f,
                0.0f, 0.5f, 0.0f, 1f,
                0.0f, 0.0f, 0.5f, 1f,
                0.0f, 0.5f, 0.5f, 1f,
        };

        this.verticesCount = vertices.length;
        this.indicesCount = indices.length;
        this.colorsCount = colors.length;
    }
}
