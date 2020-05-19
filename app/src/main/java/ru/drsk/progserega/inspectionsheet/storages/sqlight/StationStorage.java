package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;

public class StationStorage {

    protected InspectionSheetDatabase db;
    protected ILocation locationService;

    public StationStorage(InspectionSheetDatabase db, ILocation locationService) {
        this.db = db;
        this.locationService = locationService;
    }

    public List<StationModel> getByFilters(Map< String, Object > filters, EquipmentType equipmentType) {
        if (filters == null || filters.isEmpty() || (filters.size() == 1 && filters.containsKey(EquipmentService.FILTER_TYPE))) {
            List<StationModel> stationModels = db.stationDao().loadAllByType(equipmentType.getValue());
            return stationModels;
        }

        //---- формируем запрос в зависимости от установленных фильтров ---------
        String queryStr = "SELECT * FROM stations ";
        List< String > filtersParts = new ArrayList<>();
        List< Object > filtersValues = new ArrayList<>();

        filtersParts.add(" type_id = ? ");
        filtersValues.add(equipmentType.getValue());

        for (Map.Entry< String, Object > entry : filters.entrySet()) {
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
        List<StationModel> stationModels = db.stationDao().getByFilters(query);

        //если есть фильтрация по локации, то уже выбранные данные фильтруем по локации
        Point center = (Point) filters.get(EquipmentService.FILTER_POSITION);
        boolean usePosition = center != null;
        if (usePosition) {
            Float radius = (Float) filters.get(EquipmentService.FILTER_POSITION_RADIUS);
            radius = (radius != null) ? radius : locationService.defaultSearchRadius();
            stationModels = filterByPoint(stationModels,center, radius);
        }


        return stationModels;
    }


    public StationModel getByUniqId(long uniqId) {
        return db.stationDao().getById(uniqId);
    }

    private List< StationModel > filterByPoint(List< StationModel > stationModels, Point center, float radius) {
        List< StationModel > filteredStationModels = new ArrayList<>();

        for (StationModel substation : stationModels) {

            if (locationService.distanceBetween(new Point(substation.getLat(), substation.getLon(), 0), center) <= radius) {
                filteredStationModels.add(substation);
            }
        }
        return filteredStationModels;
    }

}
