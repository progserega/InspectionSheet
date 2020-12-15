package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectDescription;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.storages.IStationDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.DefectDescriptionWithPhoto;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentsDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;

public class StationDeffectsTypesStorage implements IStationDeffectTypesStorage {

    private InspectionSheetDatabase db;
    private Context context;

    public StationDeffectsTypesStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    @Override
    public List< InspectionItem > getDeffectTypes(EquipmentType equipmentType) {
        List< StationEquipmentsDeffectType > deffectTypes = db.stationEquipmentsDeffectTypesDao().getDeffectByType(equipmentType.getValue());
        List< InspectionItem > inspectionItems = convertModelToItem(deffectTypes);


        List< DefectDescriptionWithPhoto > descriptionList = db.defectDescriptionDao().getByObjectType(equipmentType.getArmObjecTypeId());
        Map< Long, DefectDescriptionWithPhoto > descriptionMap = new HashMap<>();
        for (DefectDescriptionWithPhoto description : descriptionList) {
            descriptionMap.put(description.getDeffectId(), description);
        }

        for (InspectionItem inspectionItem : inspectionItems) {
            DefectDescriptionWithPhoto description = descriptionMap.get(new Long(inspectionItem.getValueId()));
            if (description != null) {
                inspectionItem.setDescription(
                        new DeffectDescription(
                                inspectionItem.getValueId(),
                                inspectionItem.getName(),
                                new InspectionPhoto(0, description.getPhotoPath(), context),
                                description.getDescription()
                        )
                );
            }
        }

        return inspectionItems;
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
                    0,//(int) type.getId(),
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
