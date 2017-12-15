package Engine.System.Scripting.Component;

import Engine.Main.Entity;
import Engine.Main.ScriptFile;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Scripting.ScriptingComponent;

/**
 * @author Gregoire Boiron
 */
public class Script extends BaseComponent implements ScriptingComponent {

    // This component will contains a path to the script we need to execute.
    private ScriptFile script;


    public Script(Entity entity, ScriptFile script) {
        super(entity);
        this.script = script;
    }

    @Override
    public void apply() {
        this.update();
        // TODO call fixedUpdate() too in a particular case.
    }

    @Override
    public void initialize() {
        this.script.loadClass(this.getEntity(), this.getID());

        this.awake();

        if(isActive()) {
            this.start();
        }
    }

    @Override
    public void onMessage(Message message) {
        // relay the message to his scriptFile
        Class<?>[] parameterTypes = {Message.class};
        Object[] arguments = new Object[1];
        arguments[0] = message;
        this.script.callSpecificVoidFunction("onMessage", parameterTypes, arguments);
    }

    @Override
    public void awake() {
        this.script.callSpecificVoidFunction("awake", null, null);
    }

    @Override
    public void start() {
        this.script.callSpecificVoidFunction("start", null, null);
    }

    @Override
    public void update() {
        this.script.callSpecificVoidFunction("update", null, null);
    }

    @Override
    public void fixedUpdate() {
        this.script.callSpecificVoidFunction("fixedUpdate", null, null);
    }

    public void OnGUI() { this.script.callSpecificVoidFunction("OnGUI", null, null); }

    public void OnDisable() { this.script.callSpecificVoidFunction("OnDisable", null, null); }

    public void OnEnable() { this.script.callSpecificVoidFunction("OnEnable", null, null); }

}
