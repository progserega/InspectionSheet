package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;

public class LineStorage implements ILineStorage {

    private InspectionSheetDatabase db;
    private ILocation locationService;
    private LineDao lineDao;

    public LineStorage(InspectionSheetDatabase db, ILocation locationService) {
        this.db = db;
        this.locationService = locationService;
        this.lineDao = db.lineDao();
    }

    @Override
    public List< Line > getByFilters(Map< String, Object > filters) {

        if (filters == null || filters.isEmpty() || (filters.size() == 1 && filters.containsKey(EquipmentService.FILTER_TYPE))) {

            List< LineModel > substationModels = lineDao.loadAll();
            return dbModelToEntity(substationModels);
        }

        //---- формируем запрос в зависимости от установленных фильтров ---------
        String queryStr = "SELECT * FROM lines ";
        List< String > filtersParts = new ArrayList<>();
        List< Object > filtersValues = new ArrayList<>();
        for (Map.Entry< String, Object > entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.equals(EquipmentService.FILTER_TYPE)) {
                continue;
            }

            if (key.equals(EquipmentService.FILTER_NAME)) {
                filtersParts.add("name LIKE '%" + (String) value + "%' ");
            }

            if (key.equals(EquipmentService.FILTER_VOLTAGE)) {
                filtersParts.add("voltage = ? ");
                filtersValues.add(((Voltage) value).getValueVolt());
            }

            if (key.equals(EquipmentService.FILTER_POSITION)) {
                Point center = (Point) value;
                filtersParts.add("bbox_top_lat >= ? ");
                filtersParts.add("bbox_top_lon <= ? ");
                filtersParts.add("bbox_bottom_lat <= ? ");
                filtersParts.add("bbox_botttom_lon >= ? ");

                filtersValues.add(center.getLat());
                filtersValues.add(center.getLon());
                filtersValues.add(center.getLat());
                filtersValues.add(center.getLon());

            }

//            if (key.equals(EquipmentService.FILTER_ENTERPRISE)) {
//                filtersParts.add(" sp_id = ? ");
//                filtersValues.add(value);
//            }
//
//            if (key.equals(EquipmentService.FILTER_AREA)) {
//                filtersParts.add(" res_id = ? ");
//                filtersValues.add(value);
//            }
        }

        if (filtersParts.size() > 0) {
            queryStr = queryStr + " WHERE " + TextUtils.join(" AND ", filtersParts);
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryStr, filtersValues.toArray(new Object[filtersValues.size()]));
        List< LineModel > lineModelList = lineDao.getByFilters(query);

        //если есть фильтрация по локации, то уже выбранные данные фильтруем по локации
//        Point center = (Point) filters.get(EquipmentService.FILTER_POSITION);
//        boolean usePosition = center != null;
//        if (usePosition) {
//            Float radius = (Float) filters.get(EquipmentService.FILTER_POSITION_RADIUS);
//            radius = (radius != null) ? radius : locationService.defaultSearchRadius();
//            lineModelList = filterByPoint(lineModelList, center, radius);
//        }


        return dbModelToEntity(lineModelList);
    }

    @Override
    public Line getById(long id) {
        LineModel line = db.lineDao().getById(id);
        return new Line(line.getId(), line.getUniqId(), line.getName(), Voltage.fromVolt(line.getVoltage()), line.getStartExploitationYear(), new ArrayList< Tower >());
    }

    private List< Line > dbModelToEntity(List< LineModel > lineModels) {
        List< Line > lines = new ArrayList<>();
        for (LineModel lineModel : lineModels) {
            lines.add(new Line(
                    lineModel.getId(),
                    lineModel.getUniqId(),
                    lineModel.getName(),
                    Voltage.fromVolt(lineModel.getVoltage()),
                    lineModel.getStartExploitationYear(),
                    new ArrayList< Tower >())
            );
        }

        return lines;
    }

    @Override
    public void updateStartExploitationYear(long lineId, int year) {
        db.lineDao().updateStartExploitationYesr(lineId, year);
    }

    //    @Override
//    public ArrayList<Line> getLinesByType(Voltage voltage) {
//        return null;
//    }
//
//    @Override
//    public ArrayList<Line> getLinesByTypeAndName(Voltage voltage, String name) {
//        return null;
//    }
}
