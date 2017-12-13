package Game.Scripts;

import Engine.Main.Material;
import Engine.System.Component.Transform;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Scripting.BaseScript;
import Engine.System.Scripting.Callback;
import org.joml.Vector3f;

import java.util.List;
import java.util.Vector;

/**
 * @author Noemy Artigouha
 */
public class ScriptRotateHealth extends BaseScript {

    private List<Integer> componentIDs;

    public void awake() {
        // Get the Component we're interested in
        this.componentIDs = getComponents(Transform.class);
    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {
        callMethodComponent(this.componentIDs.get(0), "rotate", new Vector3f(0,2f,0));
    }

}
