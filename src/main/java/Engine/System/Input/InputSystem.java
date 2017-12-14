package Engine.System.Input;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;
import Engine.System.Component.Messaging.MessageQueue;
import Engine.System.Input.Component.KeyboardListener;
import Engine.System.Input.Component.MouseListener;
import Engine.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal <florianvidals@gmail.com>
 */

public class InputSystem extends BaseSystem {
    private Window window;

    private MouseInput mouseInput;

    private MessageQueue messageQueue;


    public InputSystem(Window window, MessageQueue messageQueue) {
        super();
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
    public void iterate() {
        this.mouseInput.handleInput();

        checkPendingEntities();

        for(Entity entity : trackedEntities) {
            for(Component component : getLocalSystemComponentsFor(entity)) {
                if(MouseListener.class.isAssignableFrom(component.getClass())) {
                    component.onMessage(new Message(null, component.getID(), "mouseEvent", this.mouseInput));
                }

                if (KeyboardListener.class.isAssignableFrom(component.getClass())) {
                    component.onMessage(new Message(null, component.getID(), "keyboardEvent", window));
                }
            }
        }
    }

    @Override
    protected void checkPendingEntities() {
        for(Entity entity : pendingEntities){
            for(Component component : getLocalSystemComponentsFor(entity)) {
                if (MouseListener.class.isAssignableFrom(component.getClass()) || KeyboardListener.class.isAssignableFrom(component.getClass())) {
                    trackedEntities.add(entity);
                    break;
                }
            }
        }
        pendingEntities.clear();
    }

}
