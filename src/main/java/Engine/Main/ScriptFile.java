package Engine.Main;

import Engine.System.Scripting.BaseScript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Grégoire Boiron
 */
public class ScriptFile extends Metadata {

    // actual script class
    private Class<BaseScript> scriptClass;

    // actual script class
    private BaseScript actualScriptClass;

    // Name of the script
    private String name;

    public ScriptFile() {
        this("");
    }

    public ScriptFile(String name) {
        super();
        this.name = name;
    }

    /**
     * Load a script to be use by the ScriptingSystem
     */
    public void loadScript() {
        try {
            this.scriptClass = (Class<BaseScript>)Class.forName("Game.Scripts." + this.name);
        } catch (ClassNotFoundException e) {
            System.out.println("The class located at Game.Scripts." + name + " cannot be found.");
            e.printStackTrace();
        }
    }

    public void loadClass(Entity entity, int scriptID) {
        try {
            // Create new instance of the BaseScript
            Constructor ctor = this.scriptClass.getDeclaredConstructor();
            ctor.setAccessible(true);
            this.actualScriptClass = (BaseScript) ctor.newInstance();

            // Set the entity of the BaseScript
            Method setEntityMethod = this.scriptClass.getSuperclass().getDeclaredMethod("setEntity", Entity.class);
            setEntityMethod.setAccessible(true);
            setEntityMethod.invoke(this.actualScriptClass, entity);

            // Do the same for the ID
            Method setScriptIDMethod = this.scriptClass.getSuperclass().getDeclaredMethod("setScriptID", int.class);
            setScriptIDMethod.setAccessible(true);
            setScriptIDMethod.invoke(this.actualScriptClass, scriptID);
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

    public void callSpecificVoidFunction(String name, Class<?>[] parameterTypesArray, Object[] arguments) {
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
            System.out.println("Exception : Method " + name +" cannot be access.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Exception : Method " + name +" cannot be called.");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("Exception : Method " + name +" cannot be found.");
            e.printStackTrace();
        }
    }
}
