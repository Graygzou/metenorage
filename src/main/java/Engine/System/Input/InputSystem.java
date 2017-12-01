package Engine.System.Input;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Window;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class InputSystem extends BaseSystem {
    private Window window;

    public InputSystem(Window window) {
        this.window = window;
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return null;
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void cleanUp() {

    }
}
