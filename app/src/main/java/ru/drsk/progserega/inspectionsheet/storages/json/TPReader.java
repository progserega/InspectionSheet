package ru.drsk.progserega.inspectionsheet.storages.json;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class TPReader {

    public SteTPResponse readTP(InputStream is) throws IOException {

        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();

        Gson gson = new Gson();
        SteTPResponse response = gson.fromJson(jsonString, SteTPResponse.class);
        return response;
    }
}
