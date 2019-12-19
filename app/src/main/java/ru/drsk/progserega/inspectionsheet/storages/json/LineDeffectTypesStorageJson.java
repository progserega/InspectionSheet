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

    private List<LineDeffectType> itemsTower_04_10;
    private List<LineDeffectType> itemsTower_35_110;
    private Map<Long, LineDeffectType> itemsTowerMap_04_10;
    private Map<Long, LineDeffectType> itemsTowerMap_35_110;

    private List<LineDeffectType> itemsSection_04_10;
    private Map<Long, LineDeffectType> itemsSectionMap_04_10;

    private List<LineDeffectType> itemsSection_35_110;
    private Map<Long, LineDeffectType> itemsSectionMap_35_110;



    public LineDeffectTypesStorageJson(Context context) {
        this.context = context;
        itemsTower_04_10 = new ArrayList<>();
        itemsTowerMap_04_10 = new HashMap<>();
        itemsTower_35_110 = new ArrayList<>();
        itemsTowerMap_35_110 = new HashMap<>();

        itemsSection_04_10 = new ArrayList<>();
        itemsSectionMap_04_10 = new HashMap<>();
        itemsSection_35_110 = new ArrayList<>();
        itemsSectionMap_35_110 = new HashMap<>();
    }

    @Override
    public List<LineDeffectType> loadTowerDeffects(int voltage) {

        List<LineDeffectType> itemsTower = itemsTower_04_10;
        Map<Long, LineDeffectType> itemsTowerMap = itemsTowerMap_04_10;
        int resourceId = R.raw.tower_deffect_types;
        if(voltage >= 35000){
            itemsTower = itemsTower_35_110;
            itemsTowerMap = itemsTowerMap_35_110;
            resourceId = R.raw.tower_deffect_types_35_110;
        }


        if (!itemsTower.isEmpty()) {
            return itemsTower;
        }

        try {
            List<LineDeffectType> types = readInspections(context.getResources().openRawResource(resourceId));
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
    public LineDeffectType getTowerDeffectById(long id, int voltage) {
        List<LineDeffectType> itemsTower = itemsTower_04_10;
        Map<Long, LineDeffectType> itemsTowerMap = itemsTowerMap_04_10;
        if(voltage >= 35000){
            itemsTower = itemsTower_35_110;
            itemsTowerMap = itemsTowerMap_35_110;
        }

        if (itemsTower.isEmpty()) {
            loadTowerDeffects(voltage);
        }

        return itemsTowerMap.get(id);
    }

    @Override
    public List<LineDeffectType> loadSectionDeffects(int voltage) {
        List<LineDeffectType> itemsSection = itemsSection_04_10;
        Map<Long, LineDeffectType> itemsSectionMap = itemsSectionMap_04_10;
        int resourceId = R.raw.line_section_deffect_types;
        if(voltage >= 35000){
            itemsSection = itemsSection_35_110;
            itemsSectionMap = itemsSectionMap_04_10;
            resourceId = R.raw.line_section_deffect_types_35_110;
        }


        if (!itemsSection.isEmpty()) {
            return itemsSection;
        }

        try {
            List<LineDeffectType> types = readInspections(context.getResources().openRawResource(resourceId));
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
    public LineDeffectType getSectionDeffectById(long id, int voltage) {
        List<LineDeffectType> itemsSection = itemsSection_04_10;
        Map<Long, LineDeffectType> itemsSectionMap = itemsSectionMap_04_10;

        if(voltage >= 35000){
            itemsSection = itemsSection_35_110;
            itemsSectionMap = itemsSectionMap_04_10;

        }
        if (itemsSection.isEmpty()) {
            loadSectionDeffects(voltage);
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
