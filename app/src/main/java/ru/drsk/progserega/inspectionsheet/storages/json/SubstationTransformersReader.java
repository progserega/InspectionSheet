package ru.drsk.progserega.inspectionsheet.storages.json;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;

public class SubstationTransformersReader {

    public List<SubstationTransformerJson> readSubstationTransformers(InputStream is) throws IOException {

        String  jsonString = JsonUtils.readJson(is);

        Gson gson = new Gson();
        SubstationTransformerJson[] response = gson.fromJson(jsonString,SubstationTransformerJson[].class);
        List<SubstationTransformerJson> transformers = new ArrayList<>(Arrays.asList(response));
        return transformers;
    }
}
