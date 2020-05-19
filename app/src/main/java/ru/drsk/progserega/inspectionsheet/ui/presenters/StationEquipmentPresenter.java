package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.StationEquipmentContract;

public class StationEquipmentPresenter implements StationEquipmentContract.Presenter {

    private InspectionSheetApplication application;
    private StationEquipmentContract.View view;

    private IStationInspection stationInspection;

    public StationEquipmentPresenter(InspectionSheetApplication application, StationEquipmentContract.View view) {
        this.application = application;
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        stationInspection = this.application.getCurrentStationInspection();

        List<StationEquipmentInspection> stationEquipmentInspections = stationInspection.getStationEquipmentInspections();

        List<Equipment> equipments = new ArrayList<>();

        for (StationEquipmentInspection equipmentInspection:  stationEquipmentInspections ) {

            equipments.add(equipmentInspection.getEquipment());

        }

        view.setEquipments(equipments);
    }

    @Override
    public void onEquipmentListItemClick(int position) {

        view.startInspectEquipmentActivity();
    }

    @Override
    public void onDestroy() {

    }
}
