package Engine.System.Graphics.Component;

import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Graphics.GraphicsComponent;
import org.joml.Vector3f;
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
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

/**
 * @author : Matthieu Le Boucher
 * @author : Gregoire Boiron
 */
public class Mesh3D extends BaseComponent implements GraphicsComponent {
    protected float[] vertices;
    protected int[] indices;
    protected String textureName;
    protected float[] textureCoordinates;
    protected float[] normals;

    protected Vector3f meshColor = new Vector3f();

    private FloatBuffer verticesBuffer;
    private IntBuffer indicesBuffer;
    private FloatBuffer textureCoordinatesBuffer;
    private FloatBuffer normalsBuffer;

    private String meshURI;
    private Material material;

    private int vboId;
    private int indexVboId;
    private int textureCoordinatesVboId;
    private int normalsVboId;
    private int vaoId;
    protected int verticesCount;
    protected int indicesCount;
    protected int textureCoordinatesCount;
    protected int normalsCount;

    public Mesh3D(Entity entity) {
        super(entity);
    }

    public Mesh3D(Entity entity, String meshURI) {
        super(entity);

        this.meshURI = meshURI;

        // Todo: implement this logic.
    }

    public Mesh3D(Entity entity, float[] vertices, int[] indices, float[] normals, float[] textureCoordinates) {
        super(entity);

        this.vertices = vertices;
        this.verticesCount = vertices.length;

        this.indices = indices;
        this.indicesCount = indices.length;

        this.normals = normals;
        this.normalsCount = normals.length;

        this.textureCoordinates = textureCoordinates;
        this.textureCoordinatesCount = textureCoordinates.length;
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
        if(material.getTexture() != null) {
            // Activate first texture unit
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, material.getTexture().getId());
        }
        // Bind to the VAO
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the order of the vertices.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexVboId);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, this.indicesCount, GL11.GL_UNSIGNED_INT, 0);

        // Restore state
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);

    }

    @Override
    public void cleanUp() {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        glBindTexture(GL_TEXTURE_2D, 0);

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);

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

        // Create and bind VAO.
        this.vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.vaoId);

        // Create, bind and hydrate VBO.
        vboId = GL15.glGenBuffers();
        verticesBuffer = BufferUtils.createFloatBuffer(verticesCount);
        verticesBuffer.put(vertices).flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        this.vboId = GL15.glGenBuffers();
        // Binds a named buffer object. (target<=specifiedEnum, bufferObjectName)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        // size 4 : BRGA for each vertex. So each vertex need 4 float (the last one for alpha parameter)

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
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
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_DYNAMIC_DRAW);
            GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        }

        if(normals != null) {
            // Vertex normals VBO.
            normalsVboId = GL15.glGenBuffers();
            normalsBuffer = BufferUtils.createFloatBuffer(normalsCount);
            normalsBuffer.put(normals).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsVboId);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        }

        GL30.glBindVertexArray(0);
    }

    public boolean isTextured() {
        return this.material.getTexture() != null;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
