package Engine.System.Scripting;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;
import Engine.System.Component.Transform;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Grégoire Boiron
 *
 * this method correspond to the Monobehavior method in Unity
 * Every scripts should extends from it to be considered has Component.
 */
public abstract class BaseScript {

    private Entity entity;

    private int scriptID;

    private ArrayDeque<Callback> waitingQueue;

        public BaseScript() {
        this.waitingQueue = new ArrayDeque<>();
    }

    private void setEntity(Entity entity) {
        this.entity = entity;
    }

    private void setScriptID(int ID) {
        this.scriptID = ID;
    }

    public void onMessage(Message message) {
        try {
            switch (message.getInstruction()) {
                case "return":
                    Object[] returnValues = (Object[])message.getData();
                    Callback callback = this.waitingQueue.poll();
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
     * Let the user get IDs from Components he's looking for
     * @param type component type we're looking for.
     * @return List of IDs.
     */
    protected List<Integer> getComponents(Class<? extends Component> type) {
        List<Integer> results = new ArrayList<>();
        if(type == Transform.class) {
            results.add(this.entity.getTransform().getID());
        } else {
            List<Component> l = this.entity.getComponents();
            for(Component comp : l) {
                if(comp.getClass().equals(type)) {
                    results.add(comp.getID());
                }
            }
        }

        return results;
    }

    protected List<Integer> getComponentsFromEntity(Integer entityID, Class<? extends Component> type) {
        List<Integer> results = new ArrayList<>();
        Entity test;
        // Find the entity that match the id
        List<Entity> firstResult = GameEngine.metadataManager.getEntities().stream()
                                                        .filter(entity1 -> entity1.getUniqueID() == entityID)
                                                        .collect(Collectors.toList());
        if(!firstResult.isEmpty()) {
            Entity entity = firstResult.get(0);
            if(type == Transform.class) {
                results.add(entity.getTransform().getID());
            } else {
                List<Component> l = entity.getComponents();
                for(Component comp : l) {
                    if(comp.getClass().equals(type)) {
                        results.add(comp.getID());
                    }
                }
            }
            return results;
        } else {
            return null;
        }
    }

    protected List<Integer> getEntities() {
        List<Integer> test = GameEngine.metadataManager.getEntities().stream()
                                    .mapToInt(entity -> entity.getUniqueID())
                                    .boxed()
                                    .collect(Collectors.toList());
        return test;
    }

    protected List<Integer> getEntitiesWithTag(String tag) {
        return GameEngine.metadataManager.getEntities().stream()
                                    .filter(entity1 -> entity1.getTag() == tag)
                                    .mapToInt(entity -> entity.getUniqueID())
                                    .boxed()
                                    .collect(Collectors.toList());
    }

    protected List<Integer> getEntitiesByName(String name) {
        return GameEngine.metadataManager.getEntities().stream()
                                    .filter(entity1 -> entity1.getName() == name)
                                    .mapToInt(entity -> entity.getUniqueID())
                                    .boxed()
                                    .collect(Collectors.toList());
    }

    /**
     * Let the script call a function without return statement
     * @param componentID Component we want to use.
     * @param command function we want to call.
     * @param data arguments sent.
     */
    protected void callMethodComponent(int componentID, String command, Object data) {
        // Create the message
        Message<Object> message = new Message<>(this.scriptID, componentID, command, data);
        // Send the message to the messageQueue
        GameEngine.messageQueue.add(message);
    }

    /**
     * Let the script call a function with a return statement
     * @param componentID Component we want to use.
     * @param command function we want to call.
     * @param data arguments sent.
     * @param callback will be execute once the function return the value.
     */
    protected void callReturnMethodComponent(Integer componentID, String command, Object data, Callback callback) {
        // Method 1 : Hardcore
        // Create the message
        Message<Object> message = new Message<>(this.scriptID, componentID, command, data);
        // Send the message to the messageQueue
        GameEngine.messageQueue.add(message);
        // Register the callback for an answer
        this.waitingQueue.add(callback);
    }

}
