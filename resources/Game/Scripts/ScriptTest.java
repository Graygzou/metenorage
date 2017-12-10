package Game.Scripts;

import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Scripting.Component.BaseScript;
import Engine.System.Sound.Component.Source;

import java.util.List;

/*
 * @author Grégoire Boiron
 */
public class ScriptTest extends BaseScript {

    public ScriptTest(Entity entity) {
        super(entity);
    }

    public void awake() {

        // Get the Component we're interested in
        List<Integer> componentIDs = getComponents(Source.class);

        //callMethodComponent(componentIDs.get(0), "setLooping", true);

        // Call a specific method on this component with his id.
        callMethodComponent(componentIDs.get(0), "play", null);

    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {
        //System.out.println("Methode Update called !!");
    }

}
