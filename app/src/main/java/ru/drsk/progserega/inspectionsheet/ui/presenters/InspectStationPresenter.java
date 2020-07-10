package ru.drsk.progserega.inspectionsheet.ui.presenters;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
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

       // view.setInspectionPercent(stationInspection.getInspectionPercent());
    }

    @Override
    public void onInspectionsListItemClick(int position) {
        List<InspectionItem> allItems = stationInspection.getStationInspectionItems();
        InspectionItem inspectionItem = allItems.get(position);

        if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
            List<InspectionItem> group = getInspectionGroup(inspectionItem, allItems);
            application.getState().setCurrentInspectionItem(inspectionItem);
            application.getState().setInspectionItemsGroup(group);

            view.startEditInspectionGroupActivity(inspectionItem, group);
        } else {
            application.getState().setCurrentDeffect(inspectionItem.getResult());
            view.startEditInspectionActivity(inspectionItem);
        }
    }


    @Override
    public void onInspectionValueEdited() {

        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        inspectionStorage.saveStationInspection(stationInspection.getStation(), stationInspection.getStationInspectionItems());


        float percent = stationInspection.getInspectionPercent();
        view.setInspectionPercent(percent);

        //TODO сохранить процент осмотра и дату
    }



    @Override
    public void onCommonPhotoTaken(String photoPath) {
        stationInspection.getCommonPhotos().add(new InspectionPhoto(0, photoPath, application));

        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        inspectionStorage.saveStationCommonPhotos(stationInspection.getStation(), stationInspection.getCommonPhotos());
    }

    @Override
    public void onCommonPhotoDeleted(InspectionPhoto photo) {
        //TODO удалять здесь
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
    public void onResume() {
        float percent = stationInspection.getInspectionPercent();
        view.setInspectionPercent(percent);
    }

    @Override
    public void onGotoEquipmentBtnClicked() {

        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        inspectionStorage.saveStationInspection(stationInspection.getStation(), stationInspection.getStationInspectionItems());
        inspectionStorage.saveStationCommonPhotos(stationInspection.getStation(), stationInspection.getCommonPhotos());

        //TODO пересчитать процент

        view.startSelectEquipmentActivity();
    }

    @Override
    public void onDestroy() {

        this.view = null;
    }
}
