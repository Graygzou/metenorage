package Game.Input;

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

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class CameraFollow extends BaseComponent implements MouseListener, KeyboardListener, InputComponent {
    private static float MOUSE_SENSITIVITY = 0.1f;
    private static float CAMERA_STEP = 0.1f;

    public CameraFollow(Entity entity) {
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
        if (message.getInstruction().equals("mouseEvent")) {
            MouseInput mouseInput = (MouseInput) message.getData();

            // Update camera based on mouse
            if (mouseInput.isRightButtonPressed()) {
                Vector2f rotVec = mouseInput.getDisplayVector();
                getEntity().getTransform().rotate(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            }
        }

        if (message.getInstruction().equals("keyboardEvent")) {
            Window window = (Window) message.getData();
            Vector3f cameraPositionOffset = new Vector3f();

            if (window.isKeyPressed(GLFW_KEY_W)) {
                cameraPositionOffset.z = -1;
            } else if (window.isKeyPressed(GLFW_KEY_S)) {
                cameraPositionOffset.z = 1;
            }
            if (window.isKeyPressed(GLFW_KEY_A)) {
                cameraPositionOffset.x = -1;
            } else if (window.isKeyPressed(GLFW_KEY_D)) {
                cameraPositionOffset.x = 1;
            }
            if (window.isKeyPressed(GLFW_KEY_Z)) {
                cameraPositionOffset.y = -1;
            } else if (window.isKeyPressed(GLFW_KEY_X)) {
                cameraPositionOffset.y = 1;
            }

            getEntity().getTransform().movePosition(cameraPositionOffset.x * CAMERA_STEP,
                    cameraPositionOffset.y * CAMERA_STEP,
                    cameraPositionOffset.z * CAMERA_STEP);

        }
    }
}
