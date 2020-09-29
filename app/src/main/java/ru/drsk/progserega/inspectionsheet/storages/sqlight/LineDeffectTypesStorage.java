package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectDescription;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.DefectDescriptionWithPhoto;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SectionDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;

public class LineDeffectTypesStorage implements ILineDeffectTypesStorage {

    private final String VOLTAGE_04_10 = "0.4,10";
    private final String VOLTAGE_35_110 = "35,110";

    private InspectionSheetDatabase db;
    private Context context;

    public LineDeffectTypesStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    @Override
    public List< LineDeffectType > loadTowerDeffects(int voltage) {
        String voltageFilter = (voltage >= 35000) ? VOLTAGE_35_110 : VOLTAGE_04_10;
        List< TowerDeffectTypesModel > deffectTypesModels = db.towerDeffectTypesDao().getByVoltage(voltageFilter);

        List< LineDeffectType > lineDeffectTypes = new ArrayList<>();
        for (TowerDeffectTypesModel typesModel : deffectTypesModels) {
            lineDeffectTypes.add(new LineDeffectType(typesModel.getId(), typesModel.getOrder(), typesModel.getName()));
        }

        int armObjectId = (voltage >= 35000) ? 2 : 1;
        setDefectDescriptions(lineDeffectTypes, armObjectId);
        return lineDeffectTypes;
    }

    private void setDefectDescriptions(List< LineDeffectType > lineDeffectTypes, int armObjectId){
        List< DefectDescriptionWithPhoto > descriptionList = db.defectDescriptionDao().getByObjectType(armObjectId);
        Map< Long, DefectDescriptionWithPhoto > descriptionMap = new HashMap<>();
        for (DefectDescriptionWithPhoto description : descriptionList) {
            descriptionMap.put(description.getDeffectId(), description);
        }

        for (LineDeffectType lineDeffectType : lineDeffectTypes) {
            DefectDescriptionWithPhoto description = descriptionMap.get(new Long(lineDeffectType.getId()));
            if (description != null) {
                lineDeffectType.setDeffectDescription(
                        new DeffectDescription(
                                lineDeffectType.getId(),
                                lineDeffectType.getName(),
                                new InspectionPhoto(0, description.getPhotoPath(), context),
                                description.getDescription()
                        )
                );
            }
        }
    }

    @Override
    public LineDeffectType getTowerDeffectById(long id, int voltage) {

        TowerDeffectTypesModel model = db.towerDeffectTypesDao().getById(id);
        if (model == null) {
            return null;
        }

        return new LineDeffectType(model.getId(), model.getOrder(), model.getName());
    }

    @Override
    public List< LineDeffectType > loadSectionDeffects(int voltage) {
        String voltageFilter = (voltage >= 35000) ? VOLTAGE_35_110 : VOLTAGE_04_10;

        List< SectionDeffectTypesModel > deffectTypesModels = db.sectionDeffectTypesDao().getByVoltage(voltageFilter);

        List< LineDeffectType > lineDeffectTypes = new ArrayList<>();
        for (SectionDeffectTypesModel typesModel : deffectTypesModels) {
            lineDeffectTypes.add(new LineDeffectType(typesModel.getId(), typesModel.getOrder(), typesModel.getName()));
        }

        int armObjectId = (voltage >= 35000) ? 4 : 3;
        setDefectDescriptions(lineDeffectTypes, armObjectId);
        return lineDeffectTypes;
    }

    @Override
    public LineDeffectType getSectionDeffectById(long id, int voltage) {
        SectionDeffectTypesModel model = db.sectionDeffectTypesDao().getById(id);
        if (model == null) {
            return null;
        }

        return new LineDeffectType(model.getId(), model.getOrder(), model.getName());
    }

}
