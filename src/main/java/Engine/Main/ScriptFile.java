package Engine.Main;

import Engine.System.Scripting.Component.BaseScript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Grégoire Boiron
 */
public class ScriptFile {

    // actual script class
    private Class<? extends BaseScript> scriptClass;

    // actual script class
    private BaseScript actualScriptClass;

    // Method of the current script
    private Method[] methods;

    //Path of the script
    private String canonicalNameScript;

    // Entity that contains the script
    private Entity entity;

    public ScriptFile() {
        this.canonicalNameScript = "";
        this.scriptClass = null;
        this.methods = null;
    }

    public ScriptFile(String name) {
        loadScript(name);
    }

    /**
     * Load a script to be use by the ScriptingSystem
     */
    public void loadScript(String name) {
        try {
            this.scriptClass = (Class<? extends BaseScript>)Class.forName("Game.Scripts." + name);
            this.canonicalNameScript = this.scriptClass.getCanonicalName();

            // Get all the methods of the class to be able to retrieve knowns one.
            methods = this.scriptClass.getMethods();

        } catch (ClassNotFoundException e) {
            System.out.println("The class located at Game.Scripts." + name + " cannot be found.");
            e.printStackTrace();
        }
    }

    public void loadClass(Entity entity) {
        this.entity = entity;
        try {
            Constructor ctor = this.scriptClass.getDeclaredConstructor(Entity.class);
            ctor.setAccessible(true);
                this.actualScriptClass = (BaseScript)ctor.newInstance(this.entity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void callSpecificVoidFunction(String name, Class<?>[] parameterTypesArray, List<Object> arguments) {
        try {
            // Find the method
            Method currentMethod = this.scriptClass.getMethod(name, parameterTypesArray);

            // Invoke the method
            if (arguments != null) {
                currentMethod.invoke(this.actualScriptClass, arguments);
            } else {
                currentMethod.invoke(this.actualScriptClass);
            }

        } catch (IllegalAccessException e) {
            System.out.println("Exception : Method" + name +" cannot be access.");
        } catch (InvocationTargetException e) {
            System.out.println("Exception : Method" + name +" cannot be called.");
        } catch (NoSuchMethodException e) {
            System.out.println("Exception : Method" + name +" cannot be found.");
        }
    }
}
