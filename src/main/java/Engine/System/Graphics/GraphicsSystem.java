package Engine.System.Graphics;

import Engine.Main.Entity;
import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Utils;
import Engine.Window;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class GraphicsSystem extends BaseSystem {
    private ShadersHandler shadersHandler;

    private Window window;

    /**
     * Field of View in radians.
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;

    public GraphicsSystem(Window window) {
        this.window = window;
    }

    public void resetProjectionMatrix() throws Exception {
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
        System.out.println("Projection matrix reset with aspect ratio: " + aspectRatio + " to:\n" + projectionMatrix);
        shadersHandler.setUniform("projectionMatrix", projectionMatrix);
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
    }

    @Override
    public void iterate(List<Entity> entities) {
        if (window.isResized()) {
            try {
                resetProjectionMatrix();
            } catch (Exception e) {
                System.out.println("GraphicsEngine: could not reset projection matrix.");
            }
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shadersHandler.bind();

        for (Entity entity : entities) {
            for (Component component : getLocalSystemComponentsFor(entity)) {
                component.initialize();
                component.apply();
            }
        }

        shadersHandler.unbind();

    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return GraphicsComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        shadersHandler = new ShadersHandler();
        shadersHandler.createVertexShader(Utils.readTextResource("Shader/basicShader.vs"));
        shadersHandler.createFragmentShader(Utils.readTextResource("Shader/basicShader.fs"));
        shadersHandler.link();

        shadersHandler.createUniform("projectionMatrix");

        // Define shaders data structure.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }
}
