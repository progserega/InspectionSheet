package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemJson;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;

public class TransformerDeffectTypesStorage implements ITransformerDeffectTypesStorage {

    private InspectionSheetDatabase db;

    public TransformerDeffectTypesStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public List< InspectionItem > getSubstationDeffects() {

        List< TransformerDeffectTypesModel > deffectTypesModel = db.transformerDeffectTypesDao().getSubstationDeffectTypes();
        return convertModelToItem(deffectTypesModel);
    }

    @Override
    public List< InspectionItem > getTPDeffects() {
        List< TransformerDeffectTypesModel > deffectTypesModel = db.transformerDeffectTypesDao().getTPDeffectTypes();
        return convertModelToItem(deffectTypesModel);
    }

    private List< InspectionItem > convertModelToItem(List< TransformerDeffectTypesModel > deffectTypesModels) {

        List< InspectionItem > inspectionItems = new ArrayList<>();

        if (deffectTypesModels == null || deffectTypesModels.isEmpty()) {
            return inspectionItems;
        }


        int parentId = 0;
        int headerId = 0;
        Gson gson = new Gson();
        for (TransformerDeffectTypesModel type : deffectTypesModels) {

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
