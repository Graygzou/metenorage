package Game.Scripts;

import Engine.System.Component.Transform;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.BaseScript;
import Engine.System.Scripting.Callback;
import Game.Input.PlayerKeyboard;
import org.joml.Vector3f;

import java.util.List;

/**
 * @author Noemy Artigouha
 */
public class ScriptLostPlayer extends BaseScript {

    private Integer componentsTransform;
    private Integer components;
    private Integer componentsEntityKeyboard;
    private Integer entity;
    static int nbHealth;


    public void awake() {
        this.componentsTransform = this.getComponents(Transform.class).get(0);
        this.components = this.getComponents(BoxRigidBodyComponent.class).get(0);
        this.componentsEntityKeyboard = this.getComponents(PlayerKeyboard.class).get(0);
        this.entity = this.getEntitiesWithTag("player").get(0);
        nbHealth = 3;
    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {

        //set camera rotation thanks to player rotation
        Callback callback = new Callback() {
            @Override
            public void call(Object result) {
                Vector3f pos = (Vector3f) result;
                if(pos.y <= -4f) {
                    callMethodComponent(componentsTransform, "setPosition", new Vector3f(1f, -1f, -3.5f));
                    callMethodComponent(components, "initialize",null);
                    if(nbHealth == 0) {
                        System.out.println("You lost !");

                    } else {
                        nbHealth--;
                    }

                }

            }
        };
        //get player rotation
        callReturnMethodComponent(componentsTransform, "getPosition", null, callback);
    }

}
