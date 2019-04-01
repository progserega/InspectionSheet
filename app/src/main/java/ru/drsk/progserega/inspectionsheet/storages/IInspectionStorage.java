package ru.drsk.progserega.inspectionsheet.storages;

import android.content.Context;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IInspectionStorage {

    void saveInspection(TransformerInspection inspection);

    void updateSubstationInspectionInfo(TransformerInspection inspection);

    void loadInspections(TransformerInspection inspection);
}
