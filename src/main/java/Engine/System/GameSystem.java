package Engine.System;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Window;

public interface GameSystem {
    void initialize(Window window) throws Exception;

    void cleanUp();
}
