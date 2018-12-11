package ru.drsk.progserega.inspectionsheet.storages.json;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;

//TODO переделать на GSON
public class TransfInspectionListReader {

    private int id = 0;
    InputStream is;
    public TransfInspectionListReader(InputStream is) {
        this.id = 0;
        this.is = is;
    }

    public List<InspectionItem> readInspections() throws IOException {

        Map<Long, InspectionItem> inspectionsMap = readInspectionsMap();
        return new ArrayList<InspectionItem>(inspectionsMap.values());
    }

    public Map<Long, InspectionItem> readInspectionsMap() throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return  readInspectionsArray(reader);
        } finally {
            reader.close();
        }
    }


    private Map<Long, InspectionItem> readInspectionsArray(JsonReader reader ) throws IOException {
        Map<Long, InspectionItem> inspectionItemMap = new LinkedHashMap<>();
        reader.beginArray();
        while (reader.hasNext()) {
            InspectionItem inspection = readInspection(reader);
            inspectionItemMap.put((long)inspection.getValueId(), inspection);
        }
        reader.endArray();
        return inspectionItemMap;
    }

    private InspectionItem readInspection(JsonReader reader) throws IOException {
        id++;
        String number = null;
        String nameItem = null;
        //String voltage = null;
        InspectionItemType type = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("number")) {
                number = reader.nextString();
            } else if (name.equals("name")) {
                nameItem = reader.nextString();
            } else if (name.equals("type")) {
                type = InspectionItemType.valueOf(reader.nextString());
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new InspectionItem(0, id, number, nameItem, type);
    }

}
