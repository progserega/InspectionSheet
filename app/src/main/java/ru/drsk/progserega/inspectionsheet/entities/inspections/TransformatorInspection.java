package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Transformator;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;

public class TransformatorInspection {

    private Substation substation;
    private Transformator transformator;
    private List<InspectionItem> inspectionItems;
    private Date date;

    public List<InspectionItem> getInspectionItems() {
        return inspectionItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransformatorInspection(Substation substation, Transformator transformator) {

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
