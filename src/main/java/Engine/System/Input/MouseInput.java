package Engine.System.Input;

import Engine.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class MouseInput extends Component {

    private boolean cursorInWindow = false;

    private final Vector2d previousPosition;

    private final Vector2d currentPosition;

    private final Vector2f displayVector;

    private boolean leftButtonPressed = false;

    private boolean rightButtonPressed = false;

    public MouseInput(Window window) {
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            cursorInWindow = entered;
        });

        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });

        previousPosition = new Vector2d();
        currentPosition = new Vector2d();
        displayVector = new Vector2f();

        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xPosition, yPosition) -> {
            currentPosition.x = xPosition;
            currentPosition.y = yPosition;
        });
    }

    public void handleInput() {
        if (cursorInWindow && previousPosition.x > 0 && previousPosition.y > 0) {
            double dx = currentPosition.x - previousPosition.x;
            double dy = currentPosition.y - previousPosition.y;

            displayVector.y = dx != 0 ? (float) dx : 0;
            displayVector.x = dy != 0 ? (float) dy : 0;
        } else {
            displayVector.x = 0;
            displayVector.y = 0;
        }

        previousPosition.x = currentPosition.x;
        previousPosition.y = currentPosition.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public boolean isCursorInWindow() {
        return cursorInWindow;
    }

    public Vector2d getPreviousPosition() {
        return previousPosition;
    }

    public Vector2d getCurrentPosition() {
        return currentPosition;
    }

    public Vector2f getDisplayVector() {
        return displayVector;
    }
}
