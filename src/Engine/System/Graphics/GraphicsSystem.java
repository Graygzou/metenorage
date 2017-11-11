package Engine.System.Graphics;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class GraphicsSystem extends BaseSystem {
    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return GraphicsComponent.class;
    }
}
