package ru.drsk.progserega.inspectionsheet.storages.json;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public class TowerReader {
    public TowerReader(){

    }

    public List<Tower> readTowers(InputStream is) throws IOException {
        Map<Long, Tower> towersMap = readTowersMap(is);
        return new ArrayList<Tower>(towersMap.values());
    }

    public Map<Long, Tower> readTowersMap(InputStream is) throws IOException{

        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return  readTowersArray(reader);
        } finally {
            reader.close();
        }
    }

    private Map<Long, Tower> readTowersArray(JsonReader reader ) throws IOException {
        Map<Long, Tower> towers = new HashMap<>();

        reader.beginArray();
        while (reader.hasNext()) {
            Tower tower = readTower(reader);
            towers.put(tower.getId(), tower);
        }
        reader.endArray();
        return towers;
    }

    private Tower readTower(JsonReader reader) throws IOException {
        long id = -1;
        Point point = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("location")) {
                point = readPoint(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Tower(id, id, String.valueOf(id), point, null, null);
    }

    private Point readPoint(JsonReader reader) throws IOException {
        double lat = 0;
        double lon = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("lat")) {
                lat = reader.nextDouble();
            } else if (name.equals("lon")) {
                lon = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Point(lat, lon, 0);
    }
}
