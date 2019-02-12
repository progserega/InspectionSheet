package ru.drsk.progserega.inspectionsheet.storages.json;

import android.util.JsonReader;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemJson;

//TODO добавить id в json
public class TransfInspectionListReader {

    public List<InspectionItem> readInspections(InputStream is) throws IOException {

        String  jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        InspectionItemJson[] response = gson.fromJson(jsonString,InspectionItemJson[].class);
        List<InspectionItem> inspectionItems = new ArrayList<>();
        int cnt = 1;
        int parentId = 0;
        int headerId = 0;

        for(InspectionItemJson item: response ){
            if(item.getType().equals(InspectionItemType.HEADER)){
                parentId = 0;
                headerId = cnt;
            }else{
                parentId = headerId;
            }
            inspectionItems.add(new InspectionItem(
                    0,
                    cnt,
                    item.getNumber(),
                    item.getName(),
                    item.getType(),
                    item.getResult(),
                    item.getSubResult(),
                    parentId
            ));
            cnt++;
        }
        return inspectionItems;
    }

}
