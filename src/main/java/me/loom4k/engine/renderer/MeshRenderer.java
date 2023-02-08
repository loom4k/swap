package me.loom4k.engine.renderer;

import me.loom4k.engine.graph.Material;
import me.loom4k.engine.graph.Mesh;
import me.loom4k.engine.graph.UniformsMap;
import me.loom4k.engine.scene.Entity;

import java.util.List;

import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRenderer implements IRenderer {
    private Material material;
    private UniformsMap uniformsMap;
    private List<Entity> entities;

    public MeshRenderer(Material material, List<Entity> entities, UniformsMap uniformsMap) {
        this.material = material;
        this.entities = entities;
        this.uniformsMap = uniformsMap;

        render();
    }

    @Override
    public void render() {
        for(Mesh mesh : material.getMeshList()) {
            glBindVertexArray(mesh.getVaoId());

            EntityRenderer entityRenderer = new EntityRenderer(mesh, entities, uniformsMap);
        }
    }
}
