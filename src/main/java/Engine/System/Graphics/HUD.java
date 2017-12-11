package Engine.System.Graphics;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.System.Graphics.Component.Text2D;

public interface HUD {
    // Todo: make this more general than just text.
    Text2D[] hudMeshes();

    default void cleanup() {
        for (Text2D textMesh : hudMeshes()) {
            textMesh.cleanUp();
        }
    }
}
