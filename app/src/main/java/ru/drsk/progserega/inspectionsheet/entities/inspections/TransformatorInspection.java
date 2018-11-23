package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;

public class TransformatorInspection {

    private List<InspectionItem> inspectionItems;

    public List<InspectionItem> getInspectionItems() {
        return inspectionItems;
    }

    public TransformatorInspection() {

        inspectionItems = new ArrayList<>();
    }

    public void loadList( TransfInspectionListReader reader){
        try {
            inspectionItems = reader.readLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
