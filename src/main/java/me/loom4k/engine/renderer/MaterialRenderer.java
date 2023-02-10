package me.loom4k.engine.renderer;

import me.loom4k.engine.graph.*;
import me.loom4k.engine.scene.Entity;

import java.util.List;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class MaterialRenderer implements IRenderer {
    private Model model;
    private UniformsMap uniformsMap;
    private TextureCache textureCache;
    private List<Entity> entities;

    public MaterialRenderer(Model model, UniformsMap uniformsMap, TextureCache textureCache) {
        this.model = model;
        this.uniformsMap = uniformsMap;
        this.textureCache = textureCache;
        this.entities = model.getEntitiesList();

        render();
    }

    @Override
    public void render() {
        for(Material material : model.getMaterialList()) {
            uniformsMap.setUniform("material.diffuse", material.getDiffuseColor());
            Texture texture = textureCache.getTexture(material.getTexturePath());
            glActiveTexture(GL_TEXTURE0);
            texture.bind();

            MeshRenderer meshRenderer = new MeshRenderer(material, entities, uniformsMap);
        }
    }
}
