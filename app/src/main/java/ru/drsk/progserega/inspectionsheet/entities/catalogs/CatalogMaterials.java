package ru.drsk.progserega.inspectionsheet.entities.catalogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogMaterials {
    private Map<Integer, Material> materials;

    public List<Material> getMaterials(){
        return new ArrayList<Material>(materials.values());
    }

    public Material getById(int id){
        return materials.get(id);
    }

    public CatalogMaterials() {

    }
}
