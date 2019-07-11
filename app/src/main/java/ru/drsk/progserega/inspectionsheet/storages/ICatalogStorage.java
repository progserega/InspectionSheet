package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;

public interface ICatalogStorage {

    List<Material> getMaterials();

    List<Material> getLineSectionMaterials();

    Material getMaterialById(long id);

    Material getLineSectionMaterialById(long id);

    TowerType getTowerTypeById(long id);

    List<TowerType> getTowerTypes();

    List<InspectionType> getInspectionTypes();


}
