package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;

public class CatalogStorageStub implements ICatalogStorage {

    private List<Material> materials = null;
    private List<Material> lineSectionMaterials = null;
    private List<TowerType> towerTypes = null;

    @Override
    public List<Material> getMaterials() {
        if(materials != null){
            return materials;
        }
        materials = new ArrayList<>();
        materials.add(new Material(0, "Не задан"));
        materials.add(new Material(1, "Бетон"));
        materials.add(new Material(2, "Дерево"));
        materials.add(new Material(3, "Металл"));
        materials.add(new Material(4, "Дерево-бетон"));

        return materials;
    }

    @Override
    public List<Material> getLineSectionMaterials() {
        if(lineSectionMaterials != null){
            return lineSectionMaterials;
        }
        lineSectionMaterials = new ArrayList<>();
        lineSectionMaterials.add(new Material(0, "Не задан"));
        lineSectionMaterials.add(new Material(1, "Провод АС"));
        lineSectionMaterials.add(new Material(2, "СИП"));
        lineSectionMaterials.add(new Material(3, "Кабель"));

        return lineSectionMaterials;
    }

    @Override
    public List<TowerType> getTowerTypes() {
        if(towerTypes != null){
            return towerTypes;
        }
        towerTypes = new ArrayList<>();
        towerTypes.add(new TowerType(0, "Не задан"));
        towerTypes.add(new TowerType(1, "Промежуточная"));
        towerTypes.add(new TowerType(2, "Анкерная"));
        towerTypes.add(new TowerType(3, "Анкерно-угловая"));
        towerTypes.add(new TowerType(4, "П-образная"));
        return towerTypes;
    }

    @Override
    public List<InspectionType> getInspectionTypes() {
        List<InspectionType> inspectionTypes = new ArrayList<>();
        inspectionTypes.add(new InspectionType(1, "Периодический"));
        inspectionTypes.add(new InspectionType(2, "Внеочередной"));
        inspectionTypes.add(new InspectionType(3, "Выборочный персоналом ИТР"));
        inspectionTypes.add(new InspectionType(4, "Верховой осмотр"));
        return inspectionTypes;
    }

    @Override
    public Material getMaterialById(long id) {

        List<Material> materials = getMaterials();
        for(Material material: materials){
            if(material.getId() == id){
                return material;
            }
        }
        return null;
    }

    @Override
    public TowerType getTowerTypeById(long id) {
        List<TowerType> towerTypes = getTowerTypes();
        for(TowerType type: towerTypes){
            if(type.getId() == id){
                return type;
            }
        }
        return null;
    }

    @Override
    public Material getLineSectionMaterialById(long id) {
        List<Material> materials = getLineSectionMaterials();
        for(Material material: materials){
            if(material.getId() == id){
                return material;
            }
        }
        return null;
    }

    @Override
    public InspectionType getInspectionTypeById(long id) {
        List<InspectionType> inspectionTypes = getInspectionTypes();
        for(InspectionType type: inspectionTypes){
            if(type.getId() == id){
                return type;
            }
        }
        return null;
    }
}

