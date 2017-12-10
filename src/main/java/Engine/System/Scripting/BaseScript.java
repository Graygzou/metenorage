package Engine.System.Scripting;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Grégoire Boiron
 *
 * this method correspond to the Monobehavior method in Unity
 * Every scripts should extends from it to be considered has Component.
 */
public abstract class BaseScript {

    private Entity entity;

    private int scriptID;

    private Map<Integer, Callback> waitingQueue;

    public BaseScript() {
        this.waitingQueue = new HashMap<>();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setScriptID(int ID) {
        this.scriptID = ID;
    }

    // Define those methods here empty to not let the user
    // define them in his script.
    public void awake() {}

    public void start() {}

    public void update() {}

    public void fixedUpdate() {}

    public void onMessage(Message message) {
        try {
            switch (message.getInstruction()) {
                case "return":
                    Object[] returnValues = (Object[])message.getData();
                    Callback callback = this.waitingQueue.remove(message.getSender());
                    callback.call(returnValues[1]);
                    break;
                default:
                    System.out.println(message.getInstruction() + ": Corresponding instruction can't be found");
                    break;

            }
        } catch (ClassCastException exception) {
            System.out.println("Data sent can't be converted into the right type.");
            exception.printStackTrace();
        }
    }

    /**
     * Get a specific ID from a component
     * @return
     */
    protected List<Integer> getComponents(Class<? extends Component> type) {
        List<Integer> results = new ArrayList<>();
        List<Component> l = this.entity.getComponents();
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
        Message<Object> message = new Message<>(this.scriptID, componentID, command, data);
        // Send the message to the messageQueue
        GameEngine.messageQueue.add(message);
    }

    /**
     *
     * @param componentID id of the component we want to play
     */
    protected void callReturnMethodComponent(Integer componentID, String command, Object data, Callback callback) {
        // Method 1 : Hardcore
        // Create the message
        Message<Object> message = new Message<>(this.scriptID, componentID, command, data);
        // Send the message to the messageQueue
        GameEngine.messageQueue.add(message);
        // Register the callback for an answer
        this.waitingQueue.put(componentID, callback);
    }

}
