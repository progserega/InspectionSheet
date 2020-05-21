package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Базовый класс для осмотра ТП и ПОдстанций
 */
public class InspectedStation {

    private Date date;
    private boolean done;
    private String inspectorName = "";

    protected List<InspectionItem> stationInspectionItems;

    protected List<InspectionPhoto> commonPhotos = new ArrayList<>();

    public void setCommonPhotos(List<InspectionPhoto> commonPhotos) {
        this.commonPhotos = commonPhotos;
    }
}
