package Engine.System.Graphics;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.System.Graphics.Component.Text2D;
import Engine.Window;

public interface HUD {
    // Todo: make this more general than just text.
    Text2D[] getHUDMeshes();

    default void cleanup() {
        for (Text2D textMesh : getHUDMeshes()) {
            textMesh.cleanUp();
        }
    }

    void updateSize(Window window);
}
