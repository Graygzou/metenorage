package Engine.System.Graphics.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Graphics.GraphicsComponent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @author : Matthieu Le Boucher
 */
public class Mesh3D extends BaseComponent implements GraphicsComponent {
    private float[] vertices;

    private FloatBuffer verticesBuffer;

    private String meshURI;

    private int vboId;
    private int vaoId;
    private int verticesCount;

    public Mesh3D(Entity entity, String meshURI) {
        super(entity);

        this.meshURI = meshURI;

        // Todo: implement this logic.
    }

    public Mesh3D(Entity entity, float[] vertices) {
        super(entity);

        this.vertices = vertices;
        this.verticesCount = vertices.length;
    }

    @Override
    public void apply() {
        this.render();
    }

    @Override
    public void render() {
        System.out.println("Mesh3D: rendering.");
        // Bind to the VAO
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.verticesCount);

        // Restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }

    @Override
    public void initialize() {
        // Todo: parse and load mesh data stored in file.

        verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        // Create and bind VAO.
        this.vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create, bind and hydrate VBO.
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        // Define data structure.
        System.out.println("Mesh3D initialized.");
    }
}
