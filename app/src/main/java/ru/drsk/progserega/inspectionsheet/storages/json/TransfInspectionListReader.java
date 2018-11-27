package ru.drsk.progserega.inspectionsheet.storages.json;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;

public class TransfInspectionListReader {

    private int id = 0;
    InputStream is;
    public TransfInspectionListReader(InputStream is) {
        this.id = 0;
        this.is = is;
    }

    public List<InspectionItem> readLines() throws IOException {

        Map<Integer, InspectionItem> linesMap = readLinesMap();
        return new ArrayList<InspectionItem>(linesMap.values());
    }

    public Map<Integer, InspectionItem> readLinesMap() throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return  readLinesArray(reader);
        } finally {
            reader.close();
        }
    }


    private Map<Integer, InspectionItem> readLinesArray(JsonReader reader ) throws IOException {
        Map<Integer, InspectionItem> linesMap = new LinkedHashMap<>();
        reader.beginArray();
        while (reader.hasNext()) {
            InspectionItem line = readLine(reader);
            linesMap.put(line.getId(), line);
        }
        reader.endArray();
        return linesMap;
    }

    private InspectionItem readLine(JsonReader reader) throws IOException {
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
        return new InspectionItem(id, number, nameItem, type);
    }

}
