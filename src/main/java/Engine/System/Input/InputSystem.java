package Engine.System.Input;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Window;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class InputSystem extends BaseSystem {
    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return null;
    }

    @Override
    public void initialize(Window window) throws Exception {

    }

    @Override
    public void cleanUp() {

    }
}
