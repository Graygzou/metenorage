package Game.Input;

/*
 * @author Noemy Artigouha
 */


import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Component;
import Engine.System.Component.Messaging.Message;
import Engine.System.Input.Component.KeyboardListener;
import Engine.System.Input.InputComponent;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Physics.Component.RigidBodyComponent;
import Engine.System.Sound.Component.Source;
import Engine.Window;
import com.bulletphysics.linearmath.Transform;
import org.joml.Vector3f;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerKeyboard extends BaseComponent implements KeyboardListener, InputComponent {
    private static float CAMERA_STEP = 0.1f;
    private BoxRigidBodyComponent rigidBody; //Rigidbody MUST BE attached before EntityKeyboard
    private Source sound;
    private static boolean isJumping;

    public PlayerKeyboard(Entity entity) {
        super(entity);
        isJumping = false;
        List<Component> components = this.getEntity().getComponents();
        for(int i = 0; i < components.size(); i++) {
            if(components.get(i) instanceof BoxRigidBodyComponent) {
                this.rigidBody = (BoxRigidBodyComponent) components.get(i);
            } else if(components.get(i) instanceof Source) {
                this.sound = (Source) components.get(i);
            }
        }
    }

    @Override
    public void apply() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void onMessage(Message message) {

        if (message.getInstruction().equals("keyboardEvent")) {
            Window window = (Window) message.getData();
            Vector3f playerPositionOffset = new Vector3f();
            Vector3f playerRotationOffset = new Vector3f();
            //Move the entity
            if (window.isKeyPressed(GLFW_KEY_W)) {
                playerPositionOffset.z = -1;
            } else if (window.isKeyPressed(GLFW_KEY_S)) {
                playerPositionOffset.z = 1;
            }
            if (window.isKeyPressed(GLFW_KEY_A)) {
                playerPositionOffset.x = -1;
            } else if (window.isKeyPressed(GLFW_KEY_D)) {
                playerPositionOffset.x = 1;
            }
            //Rotate the entity
            if(window.isKeyPressed(GLFW_KEY_Q)) {
                playerRotationOffset.y = -50;
            } else if (window.isKeyPressed(GLFW_KEY_E)){
                playerRotationOffset.y = 50;
            }

            if(window.isKeyPressed(GLFW_KEY_SPACE) && !isJumping) {
                playerPositionOffset.y = 10;
                this.rigidBody.getRigidBody().applyCentralImpulse(new javax.vecmath.Vector3f(playerPositionOffset.x * CAMERA_STEP,
                        playerPositionOffset.y * CAMERA_STEP,
                        playerPositionOffset.z * CAMERA_STEP));
                isJumping = true;
                this.sound.play();
            }
            if(window.isKeyRelease(GLFW_KEY_SPACE) && isJumping) {
                isJumping = false;
            }

            getEntity().getTransform().rotate(playerRotationOffset.x * CAMERA_STEP,
                    playerRotationOffset.y * CAMERA_STEP,
                    playerRotationOffset.z * CAMERA_STEP);

            getEntity().getTransform().setPosition(playerPositionOffset.x * CAMERA_STEP,
                    playerPositionOffset.y * CAMERA_STEP,
                    playerPositionOffset.z * CAMERA_STEP);

            javax.vecmath.Vector3f newPosition = new javax.vecmath.Vector3f(getEntity().getTransform().getPosition().x,
                    getEntity().getTransform().getPosition().y,
                    getEntity().getTransform().getPosition().z);

            System.out.println(this.rigidBody.getRigidBody().getActivationState());
            this.rigidBody.getRigidBody().translate(newPosition);
        }
    }
}
