package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;

public class CatalogStorageStub implements ICatalogStorage {

    @Override
    public List<Material> getMaterials() {
        List<Material> materials = new ArrayList<>();
        materials.add(new Material(1, "Бетон"));
        materials.add(new Material(2, "Дерево"));
        materials.add(new Material(3, "Металл"));
        materials.add(new Material(4, "Дерево-бетон"));

        return materials;
    }

    @Override
    public List<TowerType> getTowerTypes() {
        List<TowerType> towerTypes = new ArrayList<>();
        towerTypes.add(new TowerType(1, "Тип 1"));
        towerTypes.add(new TowerType(2, "Тип 2"));
        towerTypes.add(new TowerType(3, "Тип 3"));
        towerTypes.add(new TowerType(4, "Тип 4"));
        return towerTypes;
    }

    @Override
    public List<InspectionType> getInspectionTypes() {
        List<InspectionType> inspectionTypes = new ArrayList<>();
        inspectionTypes.add(new InspectionType(1, "InspectionType 1"));
        inspectionTypes.add(new InspectionType(2, "InspectionType 2"));
        inspectionTypes.add(new InspectionType(3, "InspectionType 3"));
        inspectionTypes.add(new InspectionType(4, "InspectionType 4"));
        return inspectionTypes;
    }
}

