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

    public int calcInspectionPercent() {

        int total = 0;
        int inspected = 0;
        for (InspectionItem inspectionItem : stationInspectionItems) {
            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }
            if (inspectionItem.getName().equals("Прочее")) {
                continue;
            }
            total++;

            if (inspectionItem.getResult() == null) {
                continue;
            }
            InspectionItemResult inspectionResult = inspectionItem.getResult();
            if (!inspectionResult.getComment().isEmpty() || !inspectionResult.getValues().isEmpty() || !inspectionResult.getSubValues().isEmpty()) {
                inspected++;
            }
        }

        int percent = (int) (inspected / (float) total * 100);
        return percent;
    }
}
