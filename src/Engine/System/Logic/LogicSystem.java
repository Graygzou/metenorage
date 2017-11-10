package Engine.System.Logic;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class LogicSystem extends BaseSystem {
    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return LogicComponent.class;
    }
}
