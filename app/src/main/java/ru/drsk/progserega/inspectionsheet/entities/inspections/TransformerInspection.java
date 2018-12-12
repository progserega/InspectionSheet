package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;

public class TransformerInspection {

    private Equipment substation;
    private Transformer transformator;
    private List<InspectionItem> inspectionItems;
    private Date date;
    private boolean done;

    public Equipment getSubstation() {
        return substation;
    }

    public Transformer getTransformator() {
        return transformator;
    }

    public List<InspectionItem> getInspectionItems() {
        return inspectionItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransformerInspection(Equipment substation, Transformer transformator) {

        this.substation = substation;
        this.transformator = transformator;
        inspectionItems = new ArrayList<>();
        done = false;
    }

    public void setInspectionItems(List<InspectionItem> inspectionItems) {
        this.inspectionItems = inspectionItems;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
