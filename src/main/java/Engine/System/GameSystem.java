package Engine.System;

import Engine.Main.Entity;

import java.util.List;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Gregoire Boiron <gregoire.boiron@gmail.com>
 */

public interface GameSystem {
    void initialize() throws Exception;

    void iterate(List<Entity> entities);

    void cleanUp();

    void setActiveState(boolean state);

    boolean isActive();
}
