package ru.drsk.progserega.inspectionsheet.storages.json;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public class LineTowerReader {
    public LineTowerReader() {
    }

    public List<LineTower> readLineTowers(InputStream is) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readLineTowersArray(reader);
        } finally {
            reader.close();
        }
    }


    private List<LineTower> readLineTowersArray(JsonReader reader) throws IOException {
        List<LineTower> lineTowers = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            lineTowers.add(readLineTower(reader));
        }
        reader.endArray();
        return lineTowers;
    }

    private LineTower readLineTower(JsonReader reader) throws IOException {
        long id = -1;
        long lineId = 0;
        long towerId = 0;
        String number = null ;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("number")) {
                number = reader.nextString();
            } else if (name.equals("line_id")) {
                lineId = reader.nextLong();
            } else if (name.equals("tower_id")) {
                towerId = reader.nextLong();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new LineTower( lineId, towerId, number);
    }

}
