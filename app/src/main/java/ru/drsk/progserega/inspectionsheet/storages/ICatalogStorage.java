package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;

public interface ICatalogStorage {

    public List<Material> getMaterials();

    public List<TowerType> getTowerTypes();

    public List<InspectionType> getInspectionTypes();

}
