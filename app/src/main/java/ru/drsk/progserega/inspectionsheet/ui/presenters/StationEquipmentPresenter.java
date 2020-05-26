package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.StationEquipmentContract;

public class StationEquipmentPresenter implements StationEquipmentContract.Presenter {

    private InspectionSheetApplication application;
    private StationEquipmentContract.View view;

    private IStationInspection stationInspection;
    private List< StationEquipmentInspection > stationEquipmentInspections;

    public StationEquipmentPresenter(InspectionSheetApplication application, StationEquipmentContract.View view) {
        this.application = application;
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        stationInspection = this.application.getCurrentStationInspection();

        stationEquipmentInspections = stationInspection.getStationEquipmentInspections();

        List< Equipment > equipments = new ArrayList<>();

        for (StationEquipmentInspection equipmentInspection : stationEquipmentInspections) {
            Equipment equipment = equipmentInspection.getEquipment();
            equipment.setInspectionPercent(equipmentInspection.calcInspectionPercent());
            equipments.add(equipment);
        }

        view.setEquipments(equipments);
    }

    @Override
    public void onEquipmentListItemClick(int position) {
        StationEquipmentInspection   equipmentInspection = stationEquipmentInspections.get(position);
        application.getState().setCurrentStaionEquipmentInspection(equipmentInspection);
        view.startInspectEquipmentActivity();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onFinishBtnPress() {
        //SAVE ALL
        stationInspection = this.application.getCurrentStationInspection();
        stationEquipmentInspections = stationInspection.getStationEquipmentInspections();
        IInspectionStorage inspectionStorage = application.getInspectionStorage();

        Date inspDate = new Date();
        for (StationEquipmentInspection equipmentInspection : stationEquipmentInspections) {
            Equipment equipment = equipmentInspection.getEquipment();
            equipment.setInspectionPercent(equipmentInspection.calcInspectionPercent());
            equipmentInspection.getEquipment().setInspectionDate(inspDate);

            inspectionStorage.saveInspection(equipmentInspection);
        }

        inspectionStorage.saveStationInspection(stationInspection.getStation(), stationInspection.getStationInspectionItems());
        inspectionStorage.saveStationCommonPhotos(stationInspection.getStation(), stationInspection.getCommonPhotos());
        inspectionStorage.updateStationInspectionInfo(stationInspection.getStation(), inspDate, stationInspection.getInspectionPercent());

        view.gotoSearchObjectActivity(stationInspection.getStation().getType());
    }

    @Override
    public void onDestroy() {

    }
}
