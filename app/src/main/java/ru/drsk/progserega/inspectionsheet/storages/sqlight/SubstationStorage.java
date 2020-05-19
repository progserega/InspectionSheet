package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;

public class SubstationStorage extends StationStorage implements ISubstationStorage {



    public SubstationStorage(InspectionSheetDatabase db, ILocation locationService) {
        super(db, locationService);
    }

    @Override
    public List< Substation > getByFilters(Map< String, Object > filters) {

        List<StationModel> stationModels = super.getByFilters(filters, EquipmentType.SUBSTATION);
        return dbModelToEntity(stationModels);
    }

    @Override
    public Substation getById(long id) {
        StationModel stationModel = super.getByUniqId(id);
        return buildFromStationModel(stationModel);

    }

    private List< Substation > dbModelToEntity(List< StationModel > stationModels) {
        List< Substation > substations = new ArrayList<>();
        for (StationModel substationModel : stationModels) {
            substations.add(buildFromStationModel(substationModel));
        }

        return substations;
    }


    private Substation buildFromStationModel(StationModel stationModel) {
        return new Substation(
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
