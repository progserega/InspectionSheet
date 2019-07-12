package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;

public class TowerStorage implements ITowerStorage {

    private InspectionSheetDatabase db;
    private ICatalogStorage catalogStorage;

    public TowerStorage(InspectionSheetDatabase db, ICatalogStorage catalogStorage) {
        this.db = db;
        this.catalogStorage = catalogStorage;
    }

    @Override
    public List<Tower> getByLineId(int lineId) {
        return null;
    }

    @Override
    public List<Tower> getByLineUniqId(long id) {
        List<TowerModel> towerModels = db.towerDao().getByLineUniqId(id);
        List<Tower> towers = new ArrayList<>();
        for (TowerModel towerModel : towerModels) {
            towers.add(dbModelToEntity(towerModel));
        }
        return towers;
    }

    @Override
    public Tower getFirstInLine(long lineUniqId) {
        TowerModel towerModel = db.towerDao().getFirstTowerInLine(lineUniqId);
        if (towerModel != null) {
            return dbModelToEntity(towerModel);
        }
        return null;
    }

    @Override
    public Tower getByNumberInLine(String number, long lineUniqId) {
        TowerModel towerModel = db.towerDao().getTowerByNameInLine(number, lineUniqId);
        if (towerModel != null) {
            return dbModelToEntity(towerModel);
        }
        return null;
    }

    @Override
    public Tower getByUniqId(long uniqId) {
        TowerModel towerModel = db.towerDao().getByUniqId(uniqId);
        if (towerModel != null) {
            return dbModelToEntity(towerModel);
        }
        return null;
    }

    @Override
    public void update(Tower tower) {
        if(tower == null){
            return;
        }
        TowerModel towerModel = new TowerModel(
                tower.getId(),
                tower.getUniqId(),
                tower.getName(),
                tower.getMaterial().getId(),
                tower.getTowerType().getId(),
                tower.getMapPoint().getEle(),
                tower.getMapPoint().getLat(),
                tower.getMapPoint().getLon());

        db.towerDao().update(towerModel);
    }

    private Tower dbModelToEntity(TowerModel towerModel){
        return new Tower(
                towerModel.getId(),
                towerModel.getUniqId(),
                towerModel.getName(),
                new Point(towerModel.getLat(), towerModel.getLon(), towerModel.getEle()),
                catalogStorage.getMaterialById(towerModel.getMaterial()),
                catalogStorage.getTowerTypeById(towerModel.getType())
        );
    }
}
