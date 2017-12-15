package Game.Scripts;

import Engine.System.Component.Transform;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.BaseScript;
import Engine.System.Scripting.Callback;
import Game.Input.PlayerKeyboard;
import org.joml.Vector3f;


/**
 * @author Noemy Artigouha
 */
public class ScriptPlayer extends BaseScript {

    private Integer componentTransform;
    private Integer componentRigidBody;
    private Integer componentEntityKeyboard;
    private Integer entity;
    private Integer lifeBlock;
    static int nbLife;
    static boolean bonusTook;
    static boolean playerWin;


    public void awake() {
        this.componentTransform = this.getComponents(Transform.class).get(0);
        this.componentRigidBody = this.getComponents(BoxRigidBodyComponent.class).get(0);
        this.componentEntityKeyboard = this.getComponents(PlayerKeyboard.class).get(0);
        this.entity = this.getEntitiesWithTag("player").get(0);
        this.lifeBlock = this.getEntitiesWithTag("life").get(0);
        bonusTook = false;
        nbLife = 3;
        playerWin = false;
    }

    public void start() {
        System.out.println("Methode Start called !!");
    }

    public void update() {
        Callback callback = new Callback() {
            @Override
            public void call(Object result) {
                Vector3f pos = (Vector3f) result;
                //DEFEAT of the player
                if(pos.y <= -6f) {
                    callMethodComponent(componentTransform, "setPosition", new Vector3f(1f, -1f, -3.5f));
                    callMethodComponent(componentTransform, "setRotation", new Vector3f(0,0,0));
                    callMethodComponent(componentRigidBody, "reInitialize",null);
                    if(nbLife == 0) {
                        System.out.println("You lost !");
                        removeComponentFromEntitiy(entity,componentEntityKeyboard);
                    } else {
                        nbLife--;
                        bonusTook = false;
                        callMethodComponent(getComponentsFromEntity(lifeBlock,Transform.class).get(0),"setScale",0.2f);
                        callMethodComponent(getComponentsFromEntity(lifeBlock,BoxRigidBodyComponent.class).get(0),"setCollisionShape",new javax.vecmath.Vector3f(0.2f,0.2f,0.2f));
                        System.out.println("You fell ! Remaining life : " + nbLife);
                    }
                } //VICTORY of the player
                else if(pos.x > 3.7 && pos.x < 5.2 && pos.y > -3 && pos.z > 9.5 && pos.z < 11) {
                    if(!playerWin) {
                        System.out.println("You win !");
                    }
                   playerWin = true;
                    removeComponentFromEntitiy(entity,componentEntityKeyboard);
                } //BONUS LIFE
                else if(pos.x > 10 && pos.x < 10.1 && pos.y > 0.3 && pos.z > -9 && pos.z < -8) {
                    if(!bonusTook) {
                        System.out.println("You find a life bonus !");
                        bonusTook = true;
                        nbLife++;
                        callMethodComponent(getComponentsFromEntity(lifeBlock,Transform.class).get(0),"setScale",0.0f);
                        callMethodComponent(getComponentsFromEntity(lifeBlock,BoxRigidBodyComponent.class).get(0),"setCollisionShape",new javax.vecmath.Vector3f(0f,0f,0f));
                    }

                }

            }
        };
        callReturnMethodComponent(componentTransform, "getPosition", null, callback);

    }

}
