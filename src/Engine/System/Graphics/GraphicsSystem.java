package Engine.System.Graphics;

import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Utils;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class GraphicsSystem extends BaseSystem {
    private ShadersHandler shadersHandler;

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return GraphicsComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        shadersHandler = new ShadersHandler();
        shadersHandler.createVertexShader(Utils.readTextResource("/ShadersHandler/vertex.vs"));
        shadersHandler.createFragmentShader(Utils.readTextResource("/ShadersHandler/fragment.fs"));
        shadersHandler.link();

        // Define shaders data structure.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }


}
