package Engine.System.Input;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;
import Engine.System.Component.Messaging.MessageQueue;
import Engine.System.Input.Component.KeyboardListener;
import Engine.System.Input.Component.MouseListener;
import Engine.Window;

import java.util.List;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class InputSystem extends BaseSystem {
    private Window window;

    private MouseInput mouseInput;

    private MessageQueue messageQueue;

    public InputSystem(Window window, MessageQueue messageQueue) {
        this.window = window;
        this.messageQueue = messageQueue;
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return InputComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        this.mouseInput = new MouseInput(window);
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void iterate(List<Entity> entities) {
        this.mouseInput.handleInput();

        for(Entity entity : entities) {
            for(Component component : getLocalSystemComponentsFor(entity)) {
                if(MouseListener.class.isAssignableFrom(component.getClass())) {
                    component.onMessage(new Message(null, component, "mouseEvent", this.mouseInput));
                }

                if (KeyboardListener.class.isAssignableFrom(component.getClass())) {
                    component.onMessage(new Message(null, component, "keyboardEvent", window));
                }
            }
        }
    }

}
