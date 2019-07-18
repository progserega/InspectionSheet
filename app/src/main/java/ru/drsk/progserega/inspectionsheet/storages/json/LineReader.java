package ru.drsk.progserega.inspectionsheet.storages.json;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public class LineReader {

    public LineReader() {
    }

    public List<Line> readLines(InputStream is ) throws IOException {

       Map<Long, Line> linesMap = readLinesMap(is);
       return new ArrayList<Line>(linesMap.values());
    }

    public Map<Long, Line> readLinesMap(InputStream is) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return  readLinesArray(reader);
        } finally {
            reader.close();
        }
    }


    private Map<Long, Line> readLinesArray(JsonReader reader ) throws IOException {
        Map<Long, Line> linesMap = new HashMap<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Line line = readLine(reader);
           linesMap.put(line.getId(), line);
        }
        reader.endArray();
        return linesMap;
    }

    private Line readLine(JsonReader reader) throws IOException {
        long id = -1;
        String lineName = null;
        //String voltage = null;
        Voltage voltage = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("name")) {
                lineName = reader.nextString();
            } else if (name.equals("voltage")) {
                voltage = Voltage.valueOf(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Line(id, id, lineName, voltage, 0, null);
    }

}
