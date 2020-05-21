package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;
import android.icu.text.RelativeDateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.storages.IStationPhotoStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;

public class StationPhotoStorage implements IStationPhotoStorage {
    private InspectionSheetDatabase db;
    private Context context;

    public StationPhotoStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    @Override
    public List<InspectionPhoto> loadStationCommonPhotos(Equipment station) {
        List<EquipmentPhotoModel> equipmentPhotoModels = db.equipmentPhotoDao().getByEquipment( station.getUniqId(), station.getType().getValue());
        return equipmentPhotoModelToInspectionPhoto(equipmentPhotoModels);
    }

    private List<InspectionPhoto> equipmentPhotoModelToInspectionPhoto(List<EquipmentPhotoModel> photoModels){
        List<InspectionPhoto>  photos = new ArrayList<>();
        for(EquipmentPhotoModel equipmentPhotoModel: photoModels){
            photos.add(new InspectionPhoto(equipmentPhotoModel.getId(), equipmentPhotoModel.getPhotoPath(), this.context ));
        }
        return photos;
    }
}
