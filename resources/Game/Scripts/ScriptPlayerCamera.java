package Game.Scripts;

import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.System.Component.Transform;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Scripting.BaseScript;
import Engine.System.Scripting.Callback;
import Engine.System.Sound.Component.Source;
import org.joml.Vector3f;

import java.util.List;

/**
 * @author Noemy Artigouha
 */
public class ScriptPlayerCamera extends BaseScript {

    private Integer entitityPlayer;
    private Integer componentCamera;
    private Integer componentPlayer;
    private float rayon;

    public void awake() {
        this.entitityPlayer = this.getEntitiesWithTag("player").get(0);
        this.componentCamera = this.getComponents(Transform.class).get(0);
        this.componentPlayer = this.getComponentsFromEntity(this.entitityPlayer,Transform.class).get(0);
        rayon = 2.0f;
    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {

        //set camera rotation thanks to player rotation
        Callback callbackRotation = new Callback() {
            @Override
            public void call(Object result) {
                Vector3f rot = (Vector3f) result;
                // Call a specific method on this component with his id.
                callMethodComponent(componentCamera, "setRotation", new Vector3f(rot.x+30, rot.y, rot.z));

                //set camera position thanks to player position
                Callback callbackPosition = new Callback() {
                    @Override
                    public void call(Object result) {
                        Vector3f pos = (Vector3f) result;
                        float angle = (float) (rot.y * (Math.PI / 180));
                        // Call a specific method on this component with his id.
                        callMethodComponent(componentCamera, "setPosition",
                                new Vector3f(pos.x - (float)Math.sin(angle)*rayon,
                                        pos.y + 1.5f,
                                        pos.z + rayon + ( (float)Math.cos(angle)*rayon - rayon) ));
                    }
                };
                //get player position
                callReturnMethodComponent(componentPlayer, "getPosition", null, callbackPosition);
            }
        };
        //get player rotation
        callReturnMethodComponent(componentPlayer, "getRotation", null, callbackRotation);
    }

}
