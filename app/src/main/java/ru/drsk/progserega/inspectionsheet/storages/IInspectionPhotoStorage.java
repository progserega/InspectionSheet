package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public interface IInspectionPhotoStorage {

    List<InspectionPhoto> loadStationCommonPhotos(Equipment station);



    void deleteInspectionPhotos(List< InspectionPhoto > photos);

    void deleteEquipmentPhotos(List<InspectionPhoto> photos);
}
