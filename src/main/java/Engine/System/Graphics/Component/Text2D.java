package Engine.System.Graphics.Component;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.System.Component.Messaging.Message;
import Engine.System.Graphics.GraphicsComponent;
import Engine.Utils;
import org.joml.Vector4f;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Text2D extends Mesh3D implements GraphicsComponent {
    private static final float Z_POSITION = 0.0f;

    private static final int VERTICES_PER_QUAD = 4;

    private String fontName;

    private String text;

    private int numColumns = 8;

    private int numRows = 8;

    private float tileWidth;

    private float tileHeight;

    private byte[] chararacters;

    private int charactersAmount;

    public Text2D(Entity entity) {
        super(entity);
    }

    public Text2D(Entity entity, String text, String fontName, int numColumns, int numRows) {
        super(entity);

        this.fontName = fontName;
        this.text = text;
        try {
            Material fontMaterial = new Material(fontName);
            this.setMaterial(fontMaterial);

            this.numColumns = numColumns;
            this.numRows = numRows;

            this.chararacters = text.getBytes(Charset.forName("ISO-8859-1"));
            this.charactersAmount = chararacters.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureMeshInformation() {
        List<Float> positions = new ArrayList<>();
        List<Float> textureCoordinates = new ArrayList<>();
        float[] normals   = new float[0];
        List<Integer> indices   = new ArrayList<>();

        for (int i = 0; i < charactersAmount; i++) {
            byte currChararacter = chararacters[i];
            int currentColumn = currChararacter % numColumns;
            int currentRow = currChararacter / numColumns;

            // Build a character tile made up of two triangles.

            // Left-top vertex.
            // Position:
            positions.add((float) i * tileWidth); // x.
            positions.add(0.0f); // y.
            positions.add(Z_POSITION); // z.
            textureCoordinates.add((float) currentColumn / (float) numColumns);
            textureCoordinates.add((float) currentRow / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD);

            // Left-bottom vertex.
            // Position:
            positions.add((float) i * tileWidth); // x.
            positions.add(tileHeight); // y.
            positions.add(Z_POSITION); // z.
            textureCoordinates.add((float) currentColumn / (float) numColumns);
            textureCoordinates.add((float) (currentRow + 1) / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD + 1);

            // Right-bottom vertex.
            // Position:
            positions.add((float)i*tileWidth + tileWidth); // x.
            positions.add(tileHeight); // y.
            positions.add(Z_POSITION); // z.
            textureCoordinates.add((float) (currentColumn + 1) / (float) numColumns);
            textureCoordinates.add((float) (currentRow + 1) / (float)numRows );
            indices.add(i * VERTICES_PER_QUAD + 2);

            // Right-top vertex.
            // Position:
            positions.add((float) i * tileWidth + tileWidth); // x.
            positions.add(0.0f); // y.
            positions.add(Z_POSITION); // z.
            textureCoordinates.add((float)(currentColumn + 1)/ (float) numColumns);
            textureCoordinates.add((float)currentRow / (float)numRows );
            indices.add(i*VERTICES_PER_QUAD + 3);

            // Add indices for left top and bottom right vertices
            indices.add(i * VERTICES_PER_QUAD);
            indices.add(i * VERTICES_PER_QUAD + 2);
        }

        this.setVertices(Utils.toFloatArray(positions));
        this.setTextureCoordinates(Utils.toFloatArray(textureCoordinates));
        this.setIndices(indices.stream().mapToInt(i->i).toArray());
        this.setNormals(normals);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.configureMeshInformation();
    }

    public void setTextColor(Vector4f color) {
        this.getMaterial().setAmbientColor(color);
    }

    @Override
    public void apply() {
        super.apply();
    }

    @Override
    public void initialize() {
        this.getMaterial().initialize();

        this.tileWidth = (float) this.getMaterial().getTexture().getWidth() / (float) numColumns;
        this.tileHeight = (float) this.getMaterial().getTexture().getHeight() / (float) numRows;

        this.configureMeshInformation();

        super.initialize();
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void render() {
        super.render();
    }

    public String toString() {
        return "Text '" + text + "' at position " + getEntity().getPosition() + ". Characters: " + chararacters + " " +
                "(num: " + charactersAmount + "), texture #" + getMaterial().getTexture().getId() + " " +
                "(" + numRows + "x" + numColumns + "), tiles are " + tileWidth + "x" + tileHeight;
    }
}
