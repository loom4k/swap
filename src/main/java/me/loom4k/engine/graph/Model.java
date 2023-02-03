package me.loom4k.engine.graph;

import me.loom4k.engine.scene.Entity;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final String id;
    private List<Entity> entitiesList;
    private List<Mesh> meshList;

    public Model(String id, List<Mesh> meshList) {
        this.id = id;
        this.meshList = meshList;
        entitiesList = new ArrayList<>();
    }

    public void cleanup() {
        meshList.stream().forEach(Mesh::cleanup);
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public String getId() {
        return id;
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }
}
