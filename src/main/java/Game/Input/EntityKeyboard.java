package Game.Input;

/*
 * @author Noemy Artigouha
 */


import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Input.Component.KeyboardListener;
import Engine.System.Input.Component.MouseListener;
import Engine.System.Input.InputComponent;
import Engine.System.Input.MouseInput;
import Engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class EntityKeyboard extends BaseComponent implements KeyboardListener, InputComponent {
    private static float CAMERA_STEP = 0.1f;

    public EntityKeyboard(Entity entity) {
        super(entity);
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

            getEntity().getTransform().movePosition(playerPositionOffset.x * CAMERA_STEP,
                    playerPositionOffset.y * CAMERA_STEP,
                    playerPositionOffset.z * CAMERA_STEP);


            getEntity().getTransform().rotate(playerRotationOffset.x * CAMERA_STEP,
                    playerRotationOffset.y * CAMERA_STEP,
                    playerRotationOffset.z * CAMERA_STEP);
        }
    }
}
