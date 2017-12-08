package Engine.System.Scripting.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Scripting.ScriptingComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
        // TODO call fixedUpdate() too in a particular case.
    }

    @Override
    public void initialize() {
        try {
            // Get the actual class that contains the
            ClassLoader classLoader = Script.class.getClassLoader();

            //this.scriptClass = Class.forName("Game.Scripts.ScriptTest");
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
        this.callSpecificVoidFunction("awake", null, null);
    }

    @Override
    public void start() {
        this.callSpecificVoidFunction("start", null, null);
    }

    @Override
    public void update() {
        this.callSpecificVoidFunction("update", null, null);
    }

    @Override
    public void fixedUpdate() {
        this.callSpecificVoidFunction("fixedUpdate", null, null);
    }

    private void callSpecificVoidFunction(String name, Class<?>[] parameterTypesArray, List<Object> arguments) {
        try {
            // Find the method
            Method currentMethod = this.scriptClass.getMethod(name, parameterTypesArray);

            // Invoke the method
            if (arguments != null) {
                currentMethod.invoke(this.scriptClass.newInstance(), arguments);
            } else {
                currentMethod.invoke(this.scriptClass.newInstance());
            }

        } catch (InstantiationException e) {
            System.out.println("Exception : Method" + name +" cannot be instantiate.");
        } catch (IllegalAccessException e) {
            System.out.println("Exception : Method" + name +" cannot be access.");
        } catch (InvocationTargetException e) {
            System.out.println("Exception : Method" + name +" cannot be called.");
        } catch (NoSuchMethodException e) {
            System.out.println("Exception : Method" + name +" cannot be found.");
        }
    }

    // TODO make a template for non-void method ?
}
