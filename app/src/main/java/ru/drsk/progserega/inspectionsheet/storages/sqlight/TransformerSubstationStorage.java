package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

public class TransformerSubstationStorage implements ITransformerSubstationStorage {

    private InspectionSheetDatabase db;
    private ILocation locationService;
    private TransformerSubstationDao transformerSubstationDao;

    public TransformerSubstationStorage(InspectionSheetDatabase db, ILocation locationService) {
        this.db = db;
        this.locationService = locationService;
        transformerSubstationDao = db.transformerSubstationDao();
    }

    @Override
    public List<TransformerSubstation> getByFilters(Map<String, Object> filters) {

        if (filters == null || filters.isEmpty() || (filters.size() == 1 && filters.containsKey(EquipmentService.FILTER_TYPE))) {

            List<TransformerSubstationModel> substationModels = transformerSubstationDao.loadAllTp();
            return dbModelToEntity(substationModels);
        }

        //---- формируем запрос в зависимости от установленных фильтров ---------
        String queryStr = "SELECT * FROM tp ";
        List<String> filtersParts = new ArrayList<>();
        List<Object> filtersValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.equals(EquipmentService.FILTER_TYPE)) {
                continue;
            }

            if (key.equals(EquipmentService.FILTER_NAME)) {
                filtersParts.add("disp_name_name LIKE '%" + (String) value + "%' ");
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
        List<TransformerSubstationModel> substationModels = transformerSubstationDao.getByFilters(query);

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

    private List<TransformerSubstationModel> filterByPoint(List<TransformerSubstationModel> sourceTP, Point center, float radius) {
        List<TransformerSubstationModel> substationModels = new ArrayList<>();

        for (TransformerSubstationModel tp : sourceTP) {

            if (locationService.distanceBetween(new Point(tp.getLat(), tp.getLon(), 0), center) <= radius) {
                substationModels.add(tp);
            }
        }
        return substationModels;
    }

    private List<TransformerSubstation> dbModelToEntity(List<TransformerSubstationModel> substationModels) {
        List<TransformerSubstation> substations = new ArrayList<>();
        for (TransformerSubstationModel substationModel : substationModels) {
            substations.add(new TransformerSubstation(substationModel.getId(), substationModel.getDispName(), substationModel.getInspectionDate(), substationModel.getInspectionPercent()));
        }

        return substations;
    }

    @Override
    public TransformerSubstation getById(long id) {
        TransformerSubstationModel substationModel = transformerSubstationDao.getById(id);
        return new TransformerSubstation(substationModel.getId(), substationModel.getDispName(), substationModel.getInspectionDate(), substationModel.getInspectionPercent());
    }
}
