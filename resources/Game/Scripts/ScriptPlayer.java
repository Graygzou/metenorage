package Game.Scripts;

import Engine.System.Component.Transform;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.BaseScript;
import Engine.System.Scripting.Callback;
import Game.Input.PlayerKeyboard;
import com.bulletphysics.dynamics.RigidBody;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noemy Artigouha
 */
public class ScriptPlayer extends BaseScript {

    private Integer componentTransform;
    private Integer componentRigidBody;
    private Integer componentEntityKeyboard;
    private Integer entity;
    private List<Integer> entitiesFinalBlock;
    private List<Integer> componentsRigidBodyFinalBlock;
    private RigidBody rigidBodyPlayer;
    static int nbHealth;
    static boolean collision;


    public void awake() {
        this.componentTransform = this.getComponents(Transform.class).get(0);
        this.componentRigidBody = this.getComponents(BoxRigidBodyComponent.class).get(0);
        this.componentEntityKeyboard = this.getComponents(PlayerKeyboard.class).get(0);
        this.entitiesFinalBlock = this.getEntitiesWithTag("final block");
        this.entity = this.getEntitiesWithTag("player").get(0);
        this.componentsRigidBodyFinalBlock = new ArrayList<>();
        for(int i = 0; i < this.entitiesFinalBlock.size(); i++) {
            this.componentsRigidBodyFinalBlock.add(
                    this.getComponentsFromEntity(this.entitiesFinalBlock.get(i),
                    BoxRigidBodyComponent.class).get(0));
        }

        Callback callback = new Callback() {
            @Override
            public void call(Object result) {
                rigidBodyPlayer = (RigidBody) result;

            }
        };
        callReturnMethodComponent(componentRigidBody, "getRigidbody", null, callback);
        collision = false;
        nbHealth = 3;
    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {
        Callback callback = new Callback() {
            @Override
            public void call(Object result) {
                Vector3f pos = (Vector3f) result;
                if(pos.y <= -6f) { //DEFEAT of the player
                    callMethodComponent(componentTransform, "setPosition", new Vector3f(1f, -1f, -3.5f));
                    callMethodComponent(componentTransform, "setRotation", new Vector3f(0,0,0));
                    callMethodComponent(componentRigidBody, "initialize",null);
                    if(nbHealth == 0) {
                        System.out.println("You lost !");
                        removeComponentFromEntitiy(entity,componentEntityKeyboard);
                    } else {
                        nbHealth--;
                    }

                }
                for(int i = 0; i < componentsRigidBodyFinalBlock.size(); i++) {
                    Callback callbackCollision = new Callback() {
                        @Override
                        public void call(Object result) {
                            Boolean isCollide = (Boolean) result;
                            if(isCollide) {
                                collision = true;
                            }
                        }
                    };
                    callReturnMethodComponent(componentsRigidBodyFinalBlock.get(i), "detectCollision", rigidBodyPlayer, callbackCollision);
                }

            }
        };
        callReturnMethodComponent(componentTransform, "getPosition", null, callback);

        /*if(collision) { //VICTORY of the player
            System.out.println("You win !");
            removeComponentFromEntitiy(entity,componentEntityKeyboard);
        }*/

    }

}
