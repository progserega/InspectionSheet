package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;

public class TransformerSubstationStorage extends StationStorage implements ITransformerSubstationStorage {

    public TransformerSubstationStorage(InspectionSheetDatabase db, ILocation locationService) {
        super(db, locationService);
    }

    @Override
    public List< TransformerSubstation > getByFilters(Map< String, Object > filters) {

        List<StationModel> stationModels = super.getByFilters(filters, EquipmentType.TP);

        return dbModelToEntity(stationModels);
    }

    private List< TransformerSubstation > dbModelToEntity(List< StationModel > stationModels) {
        List< TransformerSubstation > transformerSubstations = new ArrayList<>();
        for (StationModel substationModel : stationModels) {
            transformerSubstations.add(buildFromModel(substationModel));
        }

        return transformerSubstations;
    }

    @Override
    public TransformerSubstation getById(long id) {
        StationModel stationModel = super.getByUniqId(id);
        return buildFromModel(stationModel);
    }

    private TransformerSubstation buildFromModel(StationModel stationModel) {
        return new TransformerSubstation(
                stationModel.getUniqId(),
                stationModel.getUniqId(),
                stationModel.getName(),
                stationModel.getInspectionDate(),
                stationModel.getInspectionPercent(),
                stationModel.getLat(),
                stationModel.getLon()
        );
    }
}
