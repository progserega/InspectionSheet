package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

public class SubstationStorage implements ISubstationStorage {

    private InspectionSheetDatabase db;
    private ILocation locationService;
    private SubstationDao substationDao;

    public SubstationStorage(InspectionSheetDatabase db, ILocation locationService) {
        this.db = db;
        this.locationService = locationService;
        substationDao = db.substationDao();
    }

    @Override
    public List<Substation> getByFilters(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty() || (filters.size() == 1 && filters.containsKey(EquipmentService.FILTER_TYPE))) {

            List<SubstationModel> substationModels = substationDao.loadAll();
            return dbModelToEntity(substationModels);
        }

        //---- формируем запрос в зависимости от установленных фильтров ---------
        String queryStr = "SELECT * FROM substations ";
        List<String> filtersParts = new ArrayList<>();
        List<Object> filtersValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.equals(EquipmentService.FILTER_TYPE)) {
                continue;
            }

            if (key.equals(EquipmentService.FILTER_NAME)) {
                filtersParts.add("name LIKE '%" + (String) value + "%' ");
            }

            if (key.equals(EquipmentService.FILTER_ENTERPRISE)) {
                filtersParts.add(" sp_id = ? ");
                filtersValues.add(value);
            }

            if (key.equals(EquipmentService.FILTER_AREA)) {
                filtersParts.add(" res_id = ? ");
                filtersValues.add(value);
            }
        }

        if (filtersParts.size() > 0) {
            queryStr = queryStr + " WHERE " + TextUtils.join(" AND ", filtersParts);
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryStr, filtersValues.toArray(new Object[filtersValues.size()]));
        List<SubstationModel> substationModels = substationDao.getByFilters(query);

        //если есть фильтрация по локации, то уже выбранные данные фильтруем по локации
        Point center = (Point) filters.get(EquipmentService.FILTER_POSITION);
        boolean usePosition = center != null;
        if (usePosition) {
            Float radius = (Float) filters.get(EquipmentService.FILTER_POSITION_RADIUS);
            radius = (radius != null) ? radius : locationService.defaultSearchRadius();
            substationModels = filterByPoint(substationModels, center, radius);
        }


        return dbModelToEntity(substationModels);
    }

    @Override
    public Substation getById(long id) {
        SubstationModel substationModel = substationDao.getById(id);
        return new Substation(substationModel.getId(), substationModel.getName());
    }

    private List<Substation> dbModelToEntity(List<SubstationModel> substationModels) {
        List<Substation> substations = new ArrayList<>();
        for (SubstationModel substationModel : substationModels) {
            substations.add(new Substation(substationModel.getId(), substationModel.getName()));
        }

        return substations;
    }

    private List<SubstationModel> filterByPoint(List<SubstationModel> substationModelList, Point center, float radius) {
        List<SubstationModel> substationModels = new ArrayList<>();

        for (SubstationModel substation : substationModelList) {

            if (locationService.distanceBetween(new Point(substation.getLat(), substation.getLon()), center) <= radius) {
                substationModels.add(substation);
            }
        }
        return substationModels;
    }


}
