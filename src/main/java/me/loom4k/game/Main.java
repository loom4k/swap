package me.loom4k.game;

import me.loom4k.engine.*;
import me.loom4k.engine.graph.*;
import me.loom4k.engine.scene.*;

import org.joml.*;
import org.joml.Math;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic {
    private Entity cubeEntity;
    private Vector4f displInc = new Vector4f();
    private float rotation;

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
        float[] positions = new float[]{
                // VO
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        };

        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };

        List<Mesh> meshList = new ArrayList<>();
        Mesh mesh = new Mesh(positions, colors, indices);
        meshList.add(mesh);
        String cubeModelId = "cube-model";
        Model model = new Model(cubeModelId, meshList);
        scene.addModel(model);

        cubeEntity = new Entity("cube-entity", cubeModelId);
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        displInc.zero();
        if(window.isKeyPressed(GLFW_KEY_UP)) {
            displInc.y = 1;
        } else if(window.isKeyPressed(GLFW_KEY_DOWN)) {
            displInc.y = -1;
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT)) {
            displInc.x = -1;
        } else if(window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displInc.x = 1;
        }

        if(window.isKeyPressed(GLFW_KEY_A)) {
            displInc.z = -1;
        } else if(window.isKeyPressed(GLFW_KEY_Q)) {
            displInc.z = 1;
        }

        if(window.isKeyPressed(GLFW_KEY_Z)) {
            displInc.w = -1;
        } else if(window.isKeyPressed(GLFW_KEY_X)) {
            displInc.w = 1;
        }

        displInc.mul(diffTimeMillis / 1000.0f);

        Vector3f entityPosition = cubeEntity.getPosition();
        cubeEntity.setPosition(displInc.x + entityPosition.x, displInc.y + entityPosition.y, displInc.z + entityPosition.z);
        cubeEntity.setScale(cubeEntity.getScale() + displInc.w);
        cubeEntity.updateModelMatrix();
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
