package ru.drsk.progserega.inspectionsheet.storages.json;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemJson;


public class TransfInspectionListReader {

    public List<InspectionItem> readInspections(InputStream is) throws IOException {

        String jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        InspectionItemJson[] response = gson.fromJson(jsonString, InspectionItemJson[].class);
        List<InspectionItem> inspectionItems = new ArrayList<>();
        //int cnt = 1;
        int parentId = 0;
        int headerId = 0;

        for (InspectionItemJson item : response) {
            if (item.getType().equals(InspectionItemType.HEADER)) {
                parentId = 0;
                headerId = item.getId();
            } else {
                parentId = headerId;
            }
            inspectionItems.add(new InspectionItem(
                    0,
                    item.getId(),
                    item.getNumber(),
                    item.getName(),
                    item.getType(),
                    item.getResult(),
                    item.getSubResult(),
                    parentId
            ));
            //cnt++;
        }
        return inspectionItems;
    }

}
