package me.loom4k.engine.scene;

import me.loom4k.engine.graph.Camera;
import me.loom4k.engine.graph.Model;
import me.loom4k.engine.graph.TextureCache;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private Camera camera;
    private Map<String, Model> modelMap;
    private Projection projection;
    private TextureCache textureCache;

    public Scene(int width, int height) {
        modelMap = new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
        camera = new Camera();
    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
    }

    public void cleanup() {
        modelMap.values().stream().forEach(Model::cleanup);
    }

    public Camera getCamera() {
        return camera;
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void resize(int width, int height) {
        projection.updateProjectionMatrix(width, height);
    }
}
