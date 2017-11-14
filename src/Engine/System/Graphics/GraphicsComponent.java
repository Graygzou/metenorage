package Engine.System.Graphics;

import Engine.System.Component.Component;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public interface GraphicsComponent extends Component {
    void initialize();

    void cleanUp();
}
