package Engine.System.Graphics.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Graphics.GraphicsComponent;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * @author : Matthieu Le Boucher
 */
public class Mesh3D extends BaseComponent implements GraphicsComponent {
    private float[] vertices;

    private FloatBuffer verticesBuffer;

    private String meshURI;

    private int vboId;
    private int vaoId;

    public Mesh3D(Entity entity, String meshURI) {
        super(entity);

        this.meshURI = meshURI;
    }

    @Override
    public void apply() {

    }

    @Override
    public void render() {
        // Bind to the VAO
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        // Draw the vertices
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        // Unbind the VBO.
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Unbind the VAO.
        glBindVertexArray(0);

        if (verticesBuffer != null) {
            MemoryUtil.memFree(verticesBuffer);
        }
    }

    @Override
    public void initialize() {
        // Todo: parse and load mesh data stored in file.

        this.vertices = new float[]{
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        // Create and bind VAO.
        this.vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Create, bind and hydrate VBO.
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        memFree(verticesBuffer);

        // Define data structure.
    }
}
