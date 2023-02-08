package me.loom4k.engine.input;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

// TODO: Make mouse input work with the orthographic gameplay (move laterally)
public class MouseInput {
    private Vector2f currentPosition;
    private Vector2f displayVector;
    private Vector2f previousPosition;
    private boolean inWindow;
    private boolean leftButtonPressed;
    private boolean rightButtonPressed;

    public MouseInput(long windowHandle) {
        previousPosition = new Vector2f(-1, -1);
        currentPosition = new Vector2f();
        displayVector = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;

        glfwSetCursorPosCallback(windowHandle, (handle, xpos, ypos) -> {
            currentPosition.x = (float) xpos;
            currentPosition.y = (float) ypos;
        });
        glfwSetCursorEnterCallback(windowHandle, (handle, entered) -> inWindow = entered);
        glfwSetMouseButtonCallback(windowHandle, (handle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getCurrentPosition() {
        return currentPosition;
    }

    public Vector2f getDisplayVector() {
        return displayVector;
    }

    public void input() {
        displayVector.x = 0;
        displayVector.y = 0;
        if(previousPosition.x > 0 && previousPosition.y > 0 && inWindow) {
            double deltax = currentPosition.x - previousPosition.x;
            double deltay = currentPosition.y - previousPosition.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;

            if(rotateX) {
                displayVector.y = (float) -deltax;
            }

            if(rotateY) {
                displayVector.x = (float) -deltay;
            }
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
}
