package Engine.System;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal
 */

import Engine.Main.Entity;

public interface GameSystem {
    void initialize() throws Exception;

    void cleanUp();

    void addEntity(Entity entity);

    void removeEntity(Entity entity);
}
