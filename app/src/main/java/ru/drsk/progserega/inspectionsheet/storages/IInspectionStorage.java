package ru.drsk.progserega.inspectionsheet.storages;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IInspectionStorage {

    void saveInspection(TransformerInspection inspection);

    void loadInspections(TransformerInspection inspection);
}
