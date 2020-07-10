package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.storages.IStationDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;

public class StationDeffectsTypesStorageStub implements IStationDeffectTypesStorage {

    @Override
    public List<InspectionItem> getDeffectTypes(EquipmentType equipmentType) {
        List<InspectionItem> templates = new ArrayList<>();
        InspectionItemPossibleResult result = new InspectionItemPossibleResult("radio", new ArrayList<>( Arrays.asList("Yes", "NO")));
        templates.add(new InspectionItem(1, 1, "1", "Заголовок", InspectionItemType.HEADER, null, null, 0));
        templates.add(new InspectionItem(2, 2, "1.1", "Параметр 1.1.", InspectionItemType.ITEM, result, null, 1));
        templates.add(new InspectionItem(3, 3, "1.2", "Параметр 1.2.", InspectionItemType.ITEM, result, null, 1));

        templates.add(new InspectionItem(4, 4, "2", "Заголовок 2", InspectionItemType.HEADER, null, null, 0));
        templates.add(new InspectionItem(5, 5, "2.1", "Параметр 2.1.", InspectionItemType.ITEM, result, null, 4));
        templates.add(new InspectionItem(6, 6, "2.2", "Параметр 2.2.", InspectionItemType.ITEM, result, null, 4));



        return templates;
    }
}
