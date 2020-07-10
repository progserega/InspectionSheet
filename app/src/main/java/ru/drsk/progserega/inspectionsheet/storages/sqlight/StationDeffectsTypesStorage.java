package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.storages.IStationDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentsDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;

public class StationDeffectsTypesStorage implements IStationDeffectTypesStorage {

    private InspectionSheetDatabase db;

    public StationDeffectsTypesStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public List< InspectionItem > getDeffectTypes(EquipmentType equipmentType) {
        List< StationEquipmentsDeffectType > deffectTypes = db.stationEquipmentsDeffectTypesDao().getDeffectByType(equipmentType.getValue());
        return convertModelToItem(deffectTypes);
    }

    private List< InspectionItem > convertModelToItem(List< StationEquipmentsDeffectType > deffectTypesModels) {

        List< InspectionItem > inspectionItems = new ArrayList<>();

        if (deffectTypesModels == null || deffectTypesModels.isEmpty()) {
            return inspectionItems;
        }


        int parentId = 0;
        int headerId = 0;
        Gson gson = new Gson();
        for (StationEquipmentsDeffectType type : deffectTypesModels) {

            if (type.getType() == InspectionItemType.HEADER.getValue()) {
                parentId = 0;
                headerId = (int) type.getId();
            } else {
                parentId = headerId;
            }

            InspectionItemPossibleResult result = null;
            if (type.getResult() != null && !type.getResult().isEmpty()) {
                result = gson.fromJson(type.getResult(), InspectionItemPossibleResult.class);
            }

            InspectionItemPossibleResult subResult = null;
            if (type.getSubResult() != null && !type.getSubResult().isEmpty()) {
                subResult = gson.fromJson(type.getSubResult(), InspectionItemPossibleResult.class);
            }


            inspectionItems.add(new InspectionItem(
                    (int) type.getId(),
                    (int) type.getId(),
                    type.getNumber(),
                    type.getName(),
                    type.getType() == InspectionItemType.HEADER.getValue() ? InspectionItemType.HEADER : InspectionItemType.ITEM,
                    result,
                    subResult,
                    parentId
            ));

        }

        return inspectionItems;

    }
}
