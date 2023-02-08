package me.loom4k.engine.renderer;

import me.loom4k.engine.graph.*;
import me.loom4k.engine.scene.Scene;

import java.util.Collection;

public class ModelRenderer implements IRenderer {
    private Collection<Model> models;
    private TextureCache textureCache;
    private UniformsMap uniformsMap;

    public ModelRenderer(Scene scene, UniformsMap uniformsMap) {
        this.models = scene.getModelMap().values();
        this.textureCache = scene.getTextureCache();
        this.uniformsMap = uniformsMap;

        render();
    }

    @Override
    public void render() {
        for(Model model : models) {
            MaterialRenderer materialRenderer = new MaterialRenderer(model, uniformsMap, textureCache);
        }
    }
}
