package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineContract;

public class InspectLinePresenter implements InspectLineContract.Presenter {
    private InspectLineContract.View view;
    private InspectionSheetApplication application;
    private LineInspection lineInspection;
    private Line line;

    public InspectLinePresenter(InspectLineContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }

    @Override
    public void onViewCreate() {
        lineInspection = application.getState().getCurrentLineInspection();
        line = lineInspection.getLine();
        line.setTowers(application.getTowerStorage().getByLineUniqId(line.getUniqId()));

        view.setLineName(line.getName());

        long resId = application.getSettingsStorage().loadSettings().getResId();
        ElectricNetworkArea res = application.getOrganizationStorage().getResById(resId);
        //берем СП и РЭС из настроек приложения
        view.setSPName(res.getNetworkEnterprise().getName());
        view.setResName(res.getName());

        view.setInspectionTypesSpinnerData(application.getCatalogStorage().getInspectionTypes(),
                lineInspection.getInspectionType() != null ? lineInspection.getInspectionType().getId() : 0);


        view.setStartExploitationYear(line.getStartExploitationYear());
    }

    @Override
    public void onInspectionTypeSelect(int position) {
        List< InspectionType > inspectionTypes = application.getCatalogStorage().getInspectionTypes();
        if (position >= inspectionTypes.size()) {
            return;
        }
        lineInspection.setInspectionType(inspectionTypes.get(position));
    }

    @Override
    public void onStartExploitationYearSelect(int year) {
        line.setStartExploitationYear(year);
        view.setStartExploitationYear(year);
    }

    @Override
    public void onNextButtonClick() {
        InspectionType inspectionType = lineInspection.getInspectionType();
        if (inspectionType == null) {
            view.showNoInspectionTypeAlert();
            return;
        }

        application.getLineStorage().updateStartExploitationYear(line.getId(), line.getStartExploitationYear());


        long id = application.getLineInspectionStorage().saveLineInspection(lineInspection);
        lineInspection.setId(id);


        view.gotoInpectLineTowerActivity(0l);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
