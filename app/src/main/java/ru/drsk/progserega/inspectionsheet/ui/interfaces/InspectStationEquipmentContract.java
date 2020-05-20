package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public class InspectStationEquipmentContract {
    public interface View {

        public void setEquipmentName(String name);

        void setInspection(List<InspectionItem> inspectionItems);
//
//        void startEditInspectionGroupActivity(InspectionItem currentInspectionItem, List<InspectionItem> group);
//
//        void startEditInspectionActivity(InspectionItem currentInspectionItem);
//
//        void setStationName(String name);
//
//        void setCommonPhotos(List<InspectionPhoto> photos);
//
//        void showCommonPhotoFullscreen(int position, List<InspectionPhoto> photos);
//
//        void showInspectionPhotoFullcreen(int position, List<InspectionPhoto> photos);
//
//        void startSelectEquipmentActivity();
    }

    public interface Presenter {

        void onViewCreated();

//        void onInspectionsListItemClick(int position);
//
//        void onInspectionValueEdited();
//
//        void onCommonPhotoTaken(String photoPath);
//
//        void onCommonPhotoClicked(int position);
//
//        void onInspectionPhotoClicked(InspectionItem inspectionItem, int photoPosition);
//
//        void onGotoEquipmentBtnClicked();

        void onDestroy();


    }
}
