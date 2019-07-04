package ru.drsk.progserega.inspectionsheet.storages.json;


import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.ILineTowerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.models.LineDeffectTypeJson;


public class LineTowerDeffectTypesStorageJson implements ILineTowerDeffectTypesStorage {

    private Context context;

    private  List<LineTowerDeffectType> items;

    public LineTowerDeffectTypesStorageJson(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public List<LineTowerDeffectType> load() {
        if(!items.isEmpty()){
            return items;
        }

        try {
            items = readInspections(context.getResources().openRawResource(R.raw.tower_deffect_types));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    private List<LineTowerDeffectType> readInspections(InputStream is) throws IOException {

        String jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        LineDeffectTypeJson[] response = gson.fromJson(jsonString, LineDeffectTypeJson[].class);
        List<LineTowerDeffectType> inspectionItems = new ArrayList<>();


        for (LineDeffectTypeJson item : response) {

            inspectionItems.add(new LineTowerDeffectType(
                    item.getId(),
                    item.getOrder(),
                    item.getName()
                    ));
        }
        return inspectionItems;
    }

}
