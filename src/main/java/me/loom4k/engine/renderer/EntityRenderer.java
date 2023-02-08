package me.loom4k.engine.renderer;

import me.loom4k.engine.graph.Mesh;
import me.loom4k.engine.graph.UniformsMap;
import me.loom4k.engine.scene.Entity;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer implements IRenderer {
    private Mesh mesh;
    private UniformsMap uniformsMap;
    private List<Entity> entities;

    public EntityRenderer(Mesh mesh, List<Entity> entities, UniformsMap uniformsMap) {
        this.mesh = mesh;
        this.entities = entities;
        this.uniformsMap = uniformsMap;

        render();
    }

    @Override
    public void render() {
        for (Entity entity : entities) {
            uniformsMap.setUniform("modelMatrix", entity.getModelMatrix());
            glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        }
    }
}
