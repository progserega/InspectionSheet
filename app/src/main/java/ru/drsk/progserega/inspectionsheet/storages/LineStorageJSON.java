package ru.drsk.progserega.inspectionsheet.storages;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.json.LineReader;

public class LineStorageJSON implements ILineStorage {

    private List<Line> lines;
    private Context context;
    private LineReader lineReader;

    public List<Line> getLines() {
        return lines;
    }

    public LineStorageJSON(Context context) {
        this.context = context;
        lineReader = new LineReader();

        try {
           this.lines = lineReader.readLines(context.getResources().openRawResource(R.raw.lines));
        }
        catch (IOException ex){

        }

    }

    @Override
    public List<Line> getByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Line getById(long id) {
        return null;
    }




}
