package ru.drsk.progserega.inspectionsheet.services;

import java.io.File;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.EquipmentPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;


public class PhotoFullscreenManager {

    public interface DeletePhotoCompleteListener{
        void onPhotoDeleted();
    }

    public static final int TRANSFORMER_PHOTO = 1;
    public static final int INSPECTION_ITEM_PHOTO = 2;
    public static final int LINE_INSPECTION_PHOTO = 3;


    private List<InspectionPhoto> photos;
    private int photoOwner;

    private InspectionPhotoDao inspectionPhotoDao;
    private EquipmentPhotoDao equipmentPhotoDao;
    private InspectionSheetDatabase db;

    private DeletePhotoCompleteListener deletePhotoCompleteListener;

    public void setDeletePhotoCompleteListener(DeletePhotoCompleteListener deletePhotoCompleteListener) {
        this.deletePhotoCompleteListener = deletePhotoCompleteListener;
    }

    public PhotoFullscreenManager(InspectionSheetDatabase db) {
        this.photos = null;
        this.photoOwner = 0;

        this.db = db;
        inspectionPhotoDao = db.inspectionPhotoDao();
        equipmentPhotoDao = db.equipmentPhotoDao();

    }

    public List<InspectionPhoto> getPhotos() {
        return photos;
    }

    public int getPhotoOwner() {
        return photoOwner;
    }

    public void setPhotos(List<InspectionPhoto> photos) {
        this.photos = photos;
    }

    public void setPhotoOwner(int photoOwner) {
        this.photoOwner = photoOwner;
    }

    public void deletePhoto(int position) {

        if (position < 0 || position >= photos.size()) {
            return;
        }
        InspectionPhoto photo = photos.get(position);
        removePhotoFromStorage(photo);
        removePhotoFromSqlight(photo);

        photos.remove(position);

        if(deletePhotoCompleteListener != null){
            deletePhotoCompleteListener.onPhotoDeleted();
        }
    }

    private void removePhotoFromStorage(InspectionPhoto photo){
        if(photo == null || photo.getPath() == null){
            return;
        }
        File imageFile = new File(photo.getPath());
        imageFile.delete();
    }

    private void removePhotoFromSqlight(InspectionPhoto photo){
        if(photo.getId() == 0){
            return;
        }

        if(photoOwner == TRANSFORMER_PHOTO){
            equipmentPhotoDao.deleteById(photo.getId());
        }

        if(photoOwner == INSPECTION_ITEM_PHOTO){
            inspectionPhotoDao.deleteById(photo.getId());
        }

        if(photoOwner == LINE_INSPECTION_PHOTO){
            db.inspectionPhotoDao().deleteById(photo.getId());
        }

    }
}
