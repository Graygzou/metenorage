package Engine.System.Graphics;

import Engine.Main.Entity;
import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Utils;
import Engine.Window;

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

    public GraphicsSystem(Window window) {
        this.window = window;
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
    }

    @Override
    public void iterate(List<Entity> entities) {
        if (window.isResized()) {
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
        entities.forEach(entity -> getLocalSystemComponentsFor(entity).forEach(this::applyComponent));

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

        // Define shaders data structure.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }
}
