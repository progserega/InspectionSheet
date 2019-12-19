package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
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

        String inspectorName = lineInspection.getInspectorName();
        if (inspectorName == null || inspectorName.isEmpty()) {
            inspectorName = application.getSettingsStorage().loadSettings().getFio();
        }

        view.setInspectorName(inspectorName);
    }

    @Override
    public void onInspectionDateSelected(Date date) {
        lineInspection.setInspectionDate(date);
        view.setDateInspection(date);
    }

    @Override
    public void onFinishButtonPressed() {

//        long lineUniqId = lineInspection.getLine().getUniqId();
//
//        List< Tower > towers = application.getTowerStorage().getByLineUniqId(lineUniqId);
//        List< LineSection > sections = application.getLineSectionStorage().getByLine(lineUniqId);
//
//        List< TowerInspection > towerInspections = application.getLineInspectionStorage().getTowerInspectionByLine(lineUniqId);
//        List< LineSectionInspection > sectionInspections = application.getLineInspectionStorage().getSectionInspectionByLine(lineUniqId);


        lineInspection.setInspectorName(view.getInspectorName());
        application.getLineInspectionStorage().saveLineInspection(lineInspection);
        view.gotoSearchObjectActivity();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
