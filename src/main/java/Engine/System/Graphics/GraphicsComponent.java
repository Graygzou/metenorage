package Engine.System.Graphics;

import Engine.System.Component.Component;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public interface GraphicsComponent extends Component {
    void initialize();

    void cleanUp();

    void render();

    void setVertices(float[] vertices);

    void setIndices(int[] indices);

    void setColors(float[] colors);
}
