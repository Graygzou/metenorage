package Engine.System.Graphics.Component;

import Engine.Main.Entity;

/**
 * @author : Gregoire Boiron
 */
public class Plane extends Mesh3D {

    public Plane(Entity entity) {
        super(entity);
        vertices = new float[]{
                -0.5f, -0.7f, 0.5f, 1f,     // Left top         ID: 0
                -0.5f, -0.7f, -0.5f, 1f,    // Left bottom      ID: 1
                0.5f, -0.7f, -0.5f, 1f,     // Right bottom     ID: 2
                0.5f, -0.7f, 0.5f, 1f,       // Right left       ID: 3
        };

        indices = new int[]{
                // Left bottom triangle
                0, 1, 2,
                // Right top triangle
                2, 3, 0,
        };

        colors = new float[]{
                0.5f, 0.0f, 0.0f, 1f,
                0.0f, 0.5f, 0.0f, 1f,
                0.0f, 0.0f, 0.5f, 1f,
                0.0f, 0.5f, 0.5f, 1f,
                0.5f, 0.0f, 0.0f, 1f,
                0.0f, 0.5f, 0.0f, 1f,
                0.0f, 0.0f, 0.5f, 1f,
                0.0f, 0.5f, 0.5f, 1f,};

        this.verticesCount = vertices.length;
        this.indicesCount = indices.length;
        this.colorsCount = colors.length;
    }
}
