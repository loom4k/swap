package me.loom4k.game;

import me.loom4k.engine.*;
import me.loom4k.engine.graph.*;
import me.loom4k.engine.input.MouseInput;
import me.loom4k.engine.loader.ModelLoader;
import me.loom4k.engine.renderer.Render;
import me.loom4k.engine.scene.*;

import org.joml.*;
import org.joml.Math;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic {
    private Entity cubeEntity;
    private float rotation;

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;

    public static void main(String[] args) {
        Main main = new Main();
        Engine gameEngine = new Engine("SWAP", new Window.WindowOptions(), main);
        gameEngine.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done here yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        Model cubeModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);
    }

    // TODO: Move KeyboardInput to it's own class
    // TODO: Make this input work with the wanted orthographic views and gameplay
    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();

        if(window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if(window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }

        if(window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if(window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }

        if(window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(move);
        } else if(window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if(mouseInput.isRightButtonPressed()) {
            Vector2f displayVector = mouseInput.getDisplayVector();
            camera.addRotation(
                    (float) Math.toRadians(-displayVector.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displayVector.y * MOUSE_SENSITIVITY)
            );
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 1.5;
        if (rotation > 360) {
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cubeEntity.updateModelMatrix();
    }
}
