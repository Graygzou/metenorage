package Engine.System.Graphics;

import Engine.Main.Entity;
import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.TransformationUtils;
import Engine.Utils;
import Engine.Window;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

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

    private static final float Z_NEAR = 1f;

    private static final float Z_FAR = 100.f;

    private Matrix4f projectionMatrix;

    private Matrix4f viewMatrix;

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

        for (Entity entity : entities) {
            shadersHandler.setUniform("worldMatrix", TransformationUtils.getWorldMatrix(
                    entity.getPosition(),
                    entity.getRotation(),
                    entity.getScale()));

            shadersHandler.bind();

            for (Component component : getLocalSystemComponentsFor(entity)) {
                component.initialize();
                component.apply();
            }

            shadersHandler.unbind();
        }
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
        shadersHandler.createUniform("worldMatrix");
        shadersHandler.createUniform("texture_sampler");

        shadersHandler.setUniform("worldMatrix", new Matrix4f());
        shadersHandler.setUniform("texture_sampler", 0);
    }
}
