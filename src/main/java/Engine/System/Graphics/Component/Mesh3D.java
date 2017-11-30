package Engine.System.Graphics.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Graphics.GraphicsComponent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author : Matthieu Le Boucher
 * @author : Gregoire Boiron
 */
public class Mesh3D extends BaseComponent implements GraphicsComponent {
    protected float[] vertices;
    protected int[] indices;
    protected float[] colors;

    protected FloatBuffer verticesBuffer;
    protected IntBuffer indicesBuffer;
    protected FloatBuffer colorsBuffer;

    private String meshURI;

    protected int vboId;
    protected int indexVboId;
    protected int colorsVboId;
    protected int vaoId;
    protected int verticesCount;
    protected int indicesCount;
    protected int colorsCount;

    public Mesh3D(Entity entity) {
        super(entity);
    }

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

    public Mesh3D(Entity entity, float[] vertices, int[] indices) {
        super(entity);

        this.vertices = vertices;
        this.verticesCount = vertices.length;

        this.indices = indices;
        this.indicesCount = indices.length;
    }

    public Mesh3D(Entity entity, float[] vertices, int[] indices, float[] colors) {
        this(entity, vertices, indices);

        this.colors = colors;
        this.colorsCount = colors.length;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public void setColors(float[] colors) {
        this.colors = colors;
    }

    @Override
    public void apply() {
        this.render();
    }

    @Override
    public void onMessage(Message message) {
    }

    @Override
    public void render() {
        // Bind to the VAO
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // Bind to the index VBO that has all the information about the order of the vertices.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVboId);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, this.indicesCount, GL11.GL_UNSIGNED_INT, 0);

        // Restore state
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);

        // Delete the color VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(colorsVboId);

        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(indexVboId);

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }

    @Override
    public void initialize() {
        // Todo: parse and load mesh data stored in file.

        verticesBuffer = BufferUtils.createFloatBuffer(verticesCount);
        verticesBuffer.put(vertices).flip();

        // Create and bind VAO.
        this.vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create, bind and hydrate VBO.
        vboId = GL15.glGenBuffers();
        // Binds a named buffer object. (target<=specifiedEnum, bufferObjectName)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        // Creates and initializes a buffer object's data store. (target, data, hint<=how data store will be accessed)
        // ~data : pointer to data
        // ~hint : how data store will be accessed (depend of frequency and nature)
        // frequency
        //      STREAM - The data store contents will be modified once and used at most a few times.
        //      STATIC - The data store contents will be modified once and used many times.
        //      DYNAMIC - The data store contents will be modified repeatedly and used many times.
        // nature
        //      DRAW - The data store contents are modified by the application, and used as the source for GL drawing and image specification commands.
        //      READ - // modified by reading data from the GL, and used to return that data when queried by the application.
        //      COPY - // modified by reading data from the GL, and used as the source for GL drawing and image specification commands.
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // size 4 : BRGA for each vertex. So each vertex need 4 float (the last one for alpha parameter)
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        indexVboId = GL15.glGenBuffers();
        indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
        indicesBuffer.put(indices).flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        colorsVboId = GL15.glGenBuffers();
        colorsBuffer = BufferUtils.createFloatBuffer(colorsCount);
        colorsBuffer.put(colors).flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorsVboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }
}
