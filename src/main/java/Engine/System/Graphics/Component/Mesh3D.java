package Engine.System.Graphics.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Graphics.GraphicsComponent;
import Engine.System.Graphics.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

/**
 * @author : Matthieu Le Boucher
 * @author : Gregoire Boiron
 */
public class Mesh3D extends BaseComponent implements GraphicsComponent {

    protected float[] vertices;
    protected int[] indices;
    protected float[] colors;
    private float[] textureCoordinates;

    private FloatBuffer verticesBuffer;
    private IntBuffer indicesBuffer;
    private FloatBuffer colorsBuffer;
    private FloatBuffer textureCoordinatesBuffer;

    private String meshURI;
    private Texture texture;
  
    private int vboId;
    private int indexVboId;
    private int colorsVboId;
    private int textureCoordinatesVboId;
    private int vaoId;
    protected int verticesCount;
    protected int indicesCount;
    protected int colorsCount;
    private int textureCoordinatesCount;

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


    public Mesh3D(Entity entity, float[] vertices, int[] indices, float[] textureCoordinates, Texture texture) {
        this(entity, vertices, indices);

        this.textureCoordinates = textureCoordinates;
        this.textureCoordinatesCount = textureCoordinates.length;
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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

        if(texture != null) {
            // Activate first texture unit
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, texture.getId());
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
        GL30.glBindVertexArray(this.vaoId);

        // Create, bind and hydrate VBO.
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        this.vboId = GL15.glGenBuffers();
        // Binds a named buffer object. (target<=specifiedEnum, bufferObjectName)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_DYNAMIC_DRAW);
        // size 4 : BRGA for each vertex. So each vertex need 4 float (the last one for alpha parameter)

        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        this.indexVboId = GL15.glGenBuffers();
        indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
        indicesBuffer.put(indices).flip();
      
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVboId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        if(textureCoordinates != null) {
            textureCoordinatesVboId = GL15.glGenBuffers();
            textureCoordinatesBuffer = BufferUtils.createFloatBuffer(textureCoordinatesCount);
            textureCoordinatesBuffer.put(textureCoordinates).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureCoordinatesVboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        } else {
            colorsVboId = GL15.glGenBuffers();
            colorsBuffer = BufferUtils.createFloatBuffer(colorsCount);
            colorsBuffer.put(colors).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorsVboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        }

        GL30.glBindVertexArray(0);
    }
}
