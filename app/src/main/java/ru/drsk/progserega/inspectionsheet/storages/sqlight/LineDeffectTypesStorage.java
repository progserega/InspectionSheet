package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SectionDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;

public class LineDeffectTypesStorage implements ILineDeffectTypesStorage {

    private final String VOLTAGE_04_10 = "0.4,10";
    private final String VOLTAGE_35_110 = "35,110";

    private InspectionSheetDatabase db;

    public LineDeffectTypesStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public List< LineDeffectType > loadTowerDeffects(int voltage) {
        String voltageFilter = (voltage >= 35000) ? VOLTAGE_35_110 : VOLTAGE_04_10;
        List< TowerDeffectTypesModel > deffectTypesModels = db.towerDeffectTypesDao().getByVoltage(voltageFilter);

        List<LineDeffectType> lineDeffectTypes = new ArrayList<>();
        for(TowerDeffectTypesModel typesModel:  deffectTypesModels){
            lineDeffectTypes.add(new LineDeffectType(typesModel.getId(), typesModel.getOrder(), typesModel.getName()));
        }

        return lineDeffectTypes;
    }

    @Override
    public LineDeffectType getTowerDeffectById(long id, int voltage) {

        TowerDeffectTypesModel model = db.towerDeffectTypesDao().getById(id);
        if(model == null) {
            return null;
        }

        return new LineDeffectType(model.getId(),model.getOrder(),model.getName());
    }

    @Override
    public List< LineDeffectType > loadSectionDeffects(int voltage) {
        String voltageFilter = (voltage >= 35000) ? VOLTAGE_35_110 : VOLTAGE_04_10;

        List< SectionDeffectTypesModel > deffectTypesModels = db.sectionDeffectTypesDao().getByVoltage(voltageFilter);

        List<LineDeffectType> lineDeffectTypes = new ArrayList<>();
        for(SectionDeffectTypesModel typesModel:  deffectTypesModels){
            lineDeffectTypes.add(new LineDeffectType(typesModel.getId(), typesModel.getOrder(), typesModel.getName()));
        }

        return lineDeffectTypes;
    }

    @Override
    public LineDeffectType getSectionDeffectById(long id, int voltage) {
        SectionDeffectTypesModel model = db.sectionDeffectTypesDao().getById(id);
        if(model == null) {
            return null;
        }

        return new LineDeffectType(model.getId(),model.getOrder(),model.getName());
    }

}
