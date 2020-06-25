package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationEquipmentContract;

public class InspectStationEquipmentPresenter implements InspectStationEquipmentContract.Presenter {


    private InspectStationEquipmentContract.View view;
    private InspectionSheetApplication application;

    private StationEquipmentInspection equipmentInspection;

    private int maxYear = Calendar.getInstance().get(Calendar.YEAR);

    public InspectStationEquipmentPresenter(InspectStationEquipmentContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }

    @Override
    public void onViewCreated() {
        equipmentInspection = application.getState().getCurrentStaionEquipmentInspection();
        if (equipmentInspection == null) {
            return;
        }

        view.setEquipmentName(equipmentInspection.getEquipment().getName());

        view.setInspection(equipmentInspection.getInspectionItems());

        view.setManufactureYear(equipmentInspection.getEquipment().getYear());
    }

    @Override
    public void onInspectionsListItemClick(int position) {
        List< InspectionItem > allItems = equipmentInspection.getInspectionItems();
        InspectionItem inspectionItem = allItems.get(position);

        if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
            List< InspectionItem > group = getInspectionGroup(inspectionItem, allItems);

            application.getState().setCurrentInspectionItem(inspectionItem);
            application.getState().setInspectionItemsGroup(group);

            view.startEditInspectionGroupActivity(inspectionItem, group);
        } else {
            application.getState().setCurrentDeffect(inspectionItem.getResult());
            view.startEditInspectionActivity(inspectionItem);
        }
    }

    @Override
    public void onManufactureYearClick() {
        view.showSelectYearDialog(equipmentInspection.getEquipment().getYear(), maxYear);
    }

    @Override
    public void onManufactureYearSelected(int year) {
        equipmentInspection.getEquipment().setYear(year);
        view.setManufactureYear(year);

        IStationEquipmentStorage equipmentStorage = application.getStationEquipmentStorage();
        equipmentStorage.updateManufactureYear(year, equipmentInspection.getEquipment().getId());
    }

    @Override
    public void onInspectionValueEdited() {
        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        inspectionStorage.saveInspection(equipmentInspection);

        equipmentInspection.getEquipment().setInspectionPercent(equipmentInspection.calcInspectionPercent());
        Date inspDate = new Date();
        equipmentInspection.getEquipment().setInspectionDate(inspDate);

        IStationEquipmentStorage equipmentStorage = application.getStationEquipmentStorage();
        equipmentStorage.updateInspectionDate(inspDate, equipmentInspection.getEquipment().getId());
    }

    @Override
    public void onInspectionPhotoClicked(InspectionItem inspectionItem, int photoPosition) {
        view.showInspectionPhotoFullcreen(photoPosition, inspectionItem.getResult().getPhotos());
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    private List< InspectionItem > getInspectionGroup(InspectionItem header, List< InspectionItem > allItems) {
        List< InspectionItem > group = new ArrayList<>();
        for (InspectionItem item : allItems) {
            if (item.getParentId() == header.getValueId()) {
                group.add(item);
            }
        }
        return group;
    }
}
