package ru.drsk.progserega.inspectionsheet.storages.json;


import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.LineDeffectTypeJson;


public class LineDeffectTypesStorageJson implements ILineDeffectTypesStorage {

    private Context context;

    private List<LineDeffectType> itemsTower;
    private Map<Long, LineDeffectType> itemsTowerMap;

    private List<LineDeffectType> itemsSection;
    private Map<Long, LineDeffectType> itemsSectionMap;


    public LineDeffectTypesStorageJson(Context context) {
        this.context = context;
        itemsTower = new ArrayList<>();
        itemsTowerMap = new HashMap<>();
        itemsSection = new ArrayList<>();
        itemsSectionMap = new HashMap<>();
    }

    @Override
    public List<LineDeffectType> loadTowerDeffects() {
        if (!itemsTower.isEmpty()) {
            return itemsTower;
        }

        try {
            List<LineDeffectType> types = readInspections(context.getResources().openRawResource(R.raw.tower_deffect_types));
            for (LineDeffectType type : types) {
                itemsTower.add(type);
                itemsTowerMap.put(Long.valueOf(type.getId()), type);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return itemsTower;
    }

    @Override
    public LineDeffectType getTowerDeffectById(long id) {
        if (itemsTower.isEmpty()) {
            loadTowerDeffects();
        }

        return itemsTowerMap.get(id);
    }

    @Override
    public List<LineDeffectType> loadSectionDeffects() {
        if (!itemsSection.isEmpty()) {
            return itemsSection;
        }

        try {
            List<LineDeffectType> types = readInspections(context.getResources().openRawResource(R.raw.line_section_deffect_types));
            for (LineDeffectType type : types) {
                itemsSection.add(type);
                itemsSectionMap.put(Long.valueOf(type.getId()), type);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return itemsSection;
    }

    @Override
    public LineDeffectType getSectionDeffectById(long id) {
        if (itemsSection.isEmpty()) {
            loadSectionDeffects();
        }

        return itemsSectionMap.get(id);
    }

    private List<LineDeffectType> readInspections(InputStream is) throws IOException {

        String jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        LineDeffectTypeJson[] response = gson.fromJson(jsonString, LineDeffectTypeJson[].class);


        List<LineDeffectType> types = new ArrayList<>();
        for (LineDeffectTypeJson item : response) {
            LineDeffectType deffectType = new LineDeffectType(
                    item.getId(),
                    item.getOrder(),
                    item.getName()
            );

            types.add(deffectType);
        }

        return types;

    }

}
