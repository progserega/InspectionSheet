package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.storages.IFileStorage;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionPhotoStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;

public class InspectionPhotoStorage implements IInspectionPhotoStorage {
    private InspectionSheetDatabase db;
    private Context context;
    private IFileStorage fileStorage;

    public InspectionPhotoStorage(InspectionSheetDatabase db, Context context, IFileStorage fileStorage) {
        this.db = db;
        this.context = context;
        this.fileStorage = fileStorage;
    }

    @Override
    public List< InspectionPhoto > loadStationCommonPhotos(Equipment station) {
        List< EquipmentPhotoModel > equipmentPhotoModels = db.equipmentPhotoDao().getByEquipment(station.getUniqId(), station.getType().getValue());
        return equipmentPhotoModelToInspectionPhoto(equipmentPhotoModels);
    }

    private List< InspectionPhoto > equipmentPhotoModelToInspectionPhoto(List< EquipmentPhotoModel > photoModels) {
        List< InspectionPhoto > photos = new ArrayList<>();
        for (EquipmentPhotoModel equipmentPhotoModel : photoModels) {
            photos.add(new InspectionPhoto(equipmentPhotoModel.getId(), equipmentPhotoModel.getPhotoPath(), this.context));
        }
        return photos;
    }

    @Override
    public void deleteInspectionPhotos(List< InspectionPhoto > photos) {

        List< Long > photoIds = new ArrayList<>();
        for (InspectionPhoto photo : photos) {

            if (photo == null || photo.getPath() == null) {
                continue;
            }

            //TODO когда добавится возможность ссылаться на одну и туже фотку в разных дефектах, тут надо будет считать ссылки
            fileStorage.removePhoto(photo.getPath());
            photoIds.add(photo.getId());

        }

        db.inspectionPhotoDao().deleteByIds(photoIds);
    }

    @Override
    public void deleteEquipmentPhotos(List<InspectionPhoto> photos) {

        for(InspectionPhoto photo: photos) {
            if (photo == null) {
                continue;
            }

            if (photo.getPath() != null) {
                fileStorage.removePhoto(photo.getPath());
            }

            db.equipmentPhotoDao().deleteById(photo.getId());
        }
    }
}
