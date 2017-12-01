package Engine.System.Graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class Texture {

    private final int id;

    public Texture(String fileName) throws Exception {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    private static int loadTexture(String fileName) {
        // Load Texture file
        ByteBuffer buf = null;
        PNGDecoder decoder = null;
        try {
            decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
            // Load texture contents into a byte buffer
            buf = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Buffer flipped.");

        // Create a new OpenGL texture
        int textureId = glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                GL_RGBA, GL_UNSIGNED_BYTE, buf);
        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);
        return textureId;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }
}
