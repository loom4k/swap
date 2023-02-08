package me.loom4k.engine.graph;

import me.loom4k.engine.scene.Entity;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final String id;
    private List<Entity> entitiesList;
    private List<Material> materialList;

    public Model(String id, List<Material> materialList) {
        this.id = id;
        this.materialList = materialList;
        entitiesList = new ArrayList<>();
    }

    public void cleanup() {
        materialList.stream().forEach(Material::cleanup);
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public String getId() {
        return id;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }
}
