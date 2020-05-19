package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationContract;

public class InspectStationPresenter implements InspectStationContract.Presenter {

    private InspectStationContract.View view;
    private InspectionSheetApplication application;
    private IStationInspection stationInspection;



    public InspectStationPresenter(InspectStationContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }


    @Override
    public void onViewCreated() {
        stationInspection = this.application.getCurrentStationInspection();

        view.setInspection(stationInspection.getStationInspectionItems());

        view.setStationName(stationInspection.getStation().getName());

        view.setCommonPhotos(stationInspection.getCommonPhotos());
    }

    @Override
    public void onInspectionsListItemClick(int position) {
        List<InspectionItem> allItems = stationInspection.getStationInspectionItems();
        InspectionItem inspectionItem = allItems.get(position);

        if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
            List<InspectionItem> group = getInspectionGroup(inspectionItem, allItems);
            view.startEditInspectionGroupActivity(inspectionItem, group);
        } else {
            view.startEditInspectionActivity(inspectionItem);
        }
    }


    @Override
    public void onInspectionValueEdited() {

    }

    @Override
    public void onCommonPhotoTaken(String photoPath) {
        stationInspection.getCommonPhotos().add(new InspectionPhoto(0, photoPath, application));
    }

    @Override
    public void onCommonPhotoClicked(int position) {
        view.showCommonPhotoFullscreen(position, stationInspection.getCommonPhotos());
    }

    @Override
    public void onInspectionPhotoClicked(InspectionItem inspectionItem, int photoPosition) {
        view.showInspectionPhotoFullcreen(photoPosition, inspectionItem.getResult().getPhotos());
    }

    private List<InspectionItem> getInspectionGroup(InspectionItem header, List<InspectionItem> allItems) {
        List<InspectionItem> group = new ArrayList<>();
        for (InspectionItem item : allItems) {
            if (item.getParentId() == header.getValueId()) {
                group.add(item);
            }
        }
        return group;
    }

    @Override
    public void onGotoEquipmentBtnClicked() {
        //TODO SAVE
        //TODO GOTO SELECT EQUIPMENT
        view.startSelectEquipmentActivity();
    }

    @Override
    public void onDestroy() {

        this.view = null;
    }
}
