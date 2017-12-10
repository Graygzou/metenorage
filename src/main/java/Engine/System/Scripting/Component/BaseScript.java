package Engine.System.Scripting.Component;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Grégoire Boiron
 */
public abstract class BaseScript extends BaseComponent {

    /**
     * this method correspond to the Monobehavior method in Unity
     * Every scripts should extends from it to be considered has Component.
     */
    public BaseScript(Entity entity) {
        super(entity);
    }

    public void awake() {}

    public void start() {}

    public void update() {}

    public void fixedUpdate() {}

    // Define those methods here empty to not let the user
    // define them in his script.
    @Override
    public void apply() {}

    @Override
    public void initialize() { }

    @Override
    public void onMessage(Message message) {}

    /**
     * Get a specific ID from a component
     * @return
     */
    protected List<Integer> getComponents(Class<? extends Component> type) {
        List<Integer> results = new ArrayList<>();
        List<Component> l = this.getEntity().getComponents();
        for(Component comp : l) {
            if(comp.getClass().equals(type)) {
                results.add(comp.getID());
            }
        }
        return results;
    }

    /**
     *
     * @param componentID id of the component we want to play
     */
    protected void callMethodComponent(int componentID, String command, Object data) {
        // Create the message
        Message<Object> message = new Message<>(this.getID(), componentID, command, data);
        // Send the message to the messageQueue
        GameEngine.messageQueue.add(message);
    }

    protected void stopSound() {

    }

}
