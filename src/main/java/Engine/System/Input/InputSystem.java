package Engine.System.Input;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Window;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class InputSystem extends BaseSystem {
    private Window window;

    private MouseInput mouseInput;

    public InputSystem(Window window) {
        this.window = window;
        this.mouseInput = new MouseInput(window);
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

    public void handleInput() {
        this.mouseInput.handleInput();
    }
}
