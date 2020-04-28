package ru.drsk.progserega.inspectionsheet.ui.presenters;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationContract;

public class InspectStationPresenter implements InspectStationContract.Presenter {

    private InspectStationContract.View view;

    private InspectionSheetApplication application;

    public InspectStationPresenter(InspectStationContract.View view, InspectionSheetApplication application) {
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
