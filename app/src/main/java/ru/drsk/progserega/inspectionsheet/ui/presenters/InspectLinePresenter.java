package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineContract;

public class InspectLinePresenter implements InspectLineContract.Presenter {
    private InspectLineContract.View view;
    private InspectionSheetApplication application;

    public InspectLinePresenter(InspectLineContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }

    @Override
    public void onViewCreate() {
        Line line = application.getLineInspection().getLine();
        line.setTowers(application.getTowerStorage().getByLineUniqId(line.getUniqId()));

        view.setLineName(line.getName());

        //TODO определить СП и РЭС
        view.setSPName("не задан");
        view.setResName("не задан");

        view.setInspectionTypesSpinnerData(application.getCatalogStorage().getInspectionTypes(), 0);


    }

    @Override
    public void onInspectionTypeSelect(int position) {
        List<InspectionType> inspectionTypes = application.getCatalogStorage().getInspectionTypes();
        if(position >= inspectionTypes.size()){
            return;
        }
        application.getLineInspection().setInspectionType(inspectionTypes.get(position));
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
