package Engine.System.Scripting.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;

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

}
