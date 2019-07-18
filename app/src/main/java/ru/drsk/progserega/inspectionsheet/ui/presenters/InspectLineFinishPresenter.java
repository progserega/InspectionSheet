package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineFinishContract;

public class InspectLineFinishPresenter implements InspectLineFinishContract.Presenter {
    private InspectLineFinishContract.View view;
    private InspectionSheetApplication application;

    private LineInspection lineInspection;

    public InspectLineFinishPresenter(InspectLineFinishContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }

    @Override
    public void onViewCreate() {
        lineInspection = application.getCurrentLineInspection();

        view.setDateInspection(lineInspection.getInspectionDate());

        view.setInspectorName(lineInspection.getInspectorName());
    }

    @Override
    public void onInspectionDateSelected(Date date) {
        lineInspection.setInspectionDate(date);
        view.setDateInspection(date);
    }

    @Override
    public void onFinishButtonPressed() {
        lineInspection.setInspectorName(view.getInspectorName());
        application.getLineInspectionStorage().saveLineInspection(lineInspection);
        view.gotoSearchObjectActivity();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
