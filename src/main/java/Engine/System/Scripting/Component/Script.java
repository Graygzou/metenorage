package Engine.System.Scripting.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Scripting.ScriptingComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * @author Gregoire Boiron
 */
public class Script extends BaseComponent implements ScriptingComponent {

    private Class<?> scriptClass;

    // This component will contains a path to the script we need to execute.
    private String classPath;

    private Method[] methods;

    public Script(Entity entity, String classPath) {
        super(entity);
        this.classPath = classPath;
    }

    @Override
    public void apply() {
        this.update();

        // Should call fixedUpdate() too in a particular case.
    }

    @Override
    public void initialize() {
        try {
            // Get the actual class that contains the script
            this.scriptClass = Class.forName("Game.Scripts.ScriptTest");

            // Get all the methods of the class to be able to retrieve knowns one.
            methods = this.scriptClass.getMethods();

            this.awake();

            if(isActive()) {
                this.start();
            }

        } catch(ClassNotFoundException e) {
            System.out.println("The class located at " + classPath + " cannot be found.");
        }
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void awake() {
        // TODO
    }

    @Override
    public void start() {
        try {
            // Find the start() method
            Method startMethod = this.scriptClass.getMethod("start", null);
            System.out.println("method = " + startMethod.toString());

            // Invoke the start method
            // Not usefull startMethod.setAccessible(true);
            Object o = startMethod.invoke(this.scriptClass.newInstance(), null);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update() {
        //TODO
    }

    @Override
    public void fixedUpdate() {
        //TODO
    }
}
