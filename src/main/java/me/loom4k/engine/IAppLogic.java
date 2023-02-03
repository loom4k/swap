package me.loom4k.engine;

import me.loom4k.engine.graph.Render;
import me.loom4k.engine.scene.Scene;

public interface IAppLogic {
    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long diffTimeMillis);

    void update(Window window, Scene scene, long diffTimeMillis);
}
