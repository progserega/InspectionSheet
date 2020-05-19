package ru.drsk.progserega.inspectionsheet.ui.presenters;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationEquipmentContract;

public class InspectStationEquipmentPresenter implements InspectStationEquipmentContract.Presenter {


    private InspectStationEquipmentContract.View view;
    private InspectionSheetApplication application;


    public InspectStationEquipmentPresenter(InspectStationEquipmentContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }
    @Override
    public void onViewCreated() {

    }

    @Override
    public void onDestroy() {

    }
}
