package ru.drsk.progserega.inspectionsheet.storages.json;


import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.ILineTowerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineTower;
import ru.drsk.progserega.inspectionsheet.storages.json.models.LineDeffectTypeJson;


public class LineTowerDeffectTypesStorageJson implements ILineTowerDeffectTypesStorage {

    private Context context;

    private  List<LineTowerDeffectType> items;
    private Map<Long, LineTowerDeffectType> itemsMap;
    //List<GeoLineTower> towers = new ArrayList<GeoLineTower>(geoLineTowers.getTowers().values());

    public LineTowerDeffectTypesStorageJson(Context context) {
        this.context = context;
        items = new ArrayList<>();
        itemsMap = new HashMap<>();
    }

    @Override
    public List<LineTowerDeffectType> load() {
        if(!items.isEmpty()){
            return items;
        }

        try {
            readInspections(context.getResources().openRawResource(R.raw.tower_deffect_types));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public LineTowerDeffectType getById(long id) {
        if(items.isEmpty()){
            try {
                readInspections(context.getResources().openRawResource(R.raw.tower_deffect_types));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return itemsMap.get(id);
    }

    private void readInspections(InputStream is) throws IOException {

        String jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        LineDeffectTypeJson[] response = gson.fromJson(jsonString, LineDeffectTypeJson[].class);



        for (LineDeffectTypeJson item : response) {
            LineTowerDeffectType deffectType = new LineTowerDeffectType(
                    item.getId(),
                    item.getOrder(),
                    item.getName()
            );

            items.add(deffectType);
            itemsMap.put(Long.valueOf(item.getId()), deffectType );
        }

    }

}
