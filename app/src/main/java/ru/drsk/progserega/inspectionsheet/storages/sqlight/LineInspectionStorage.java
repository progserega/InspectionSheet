package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineTowerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;

public class LineInspectionStorage implements ILineInspectionStorage {

    private InspectionSheetDatabase db;
    private Context context;
    private ILineTowerDeffectTypesStorage deffectTypesStorage;

    public LineInspectionStorage(InspectionSheetDatabase db, Context context, ILineTowerDeffectTypesStorage deffectTypesStorage) {
        this.db = db;
        this.context = context;
        this.deffectTypesStorage = deffectTypesStorage;
    }

    @Override
    public List<TowerDeffect> getTowerDeffects(long towerUniqId) {
        List<TowerDeffectModel> towerDeffectModels =  db.towerDeffectDao().getTowerDeffects(towerUniqId);
        List<TowerDeffect> deffects = new ArrayList<>();
        for(TowerDeffectModel deffectModel: towerDeffectModels){
            deffects.add(new TowerDeffect(
                    deffectModel.getId(),
                    towerUniqId,
                    deffectTypesStorage.getById(deffectModel.getDeffectTypeId()),
                    deffectModel.getDeffectValue()
            ));
        }
        return deffects;
    }

    @Override
    public Long addTowerDeffect(TowerDeffect deffect) {

        TowerDeffectModel towerDeffectModel = new TowerDeffectModel(deffect.getId(),0, deffect.getTowerId(),deffect.getDeffectType().getId(), deffect.getValue());
        return db.towerDeffectDao().addDeffect(towerDeffectModel);
    }

    @Override
    public void updateTowerDeffect(TowerDeffect deffect) {
        TowerDeffectModel towerDeffectModel = new TowerDeffectModel(deffect.getId(),0, deffect.getTowerId(),deffect.getDeffectType().getId(), deffect.getValue());
        db.towerDeffectDao().updateDeffect(towerDeffectModel);
    }
}
