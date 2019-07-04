package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerInspectionItem;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;
import ru.drsk.progserega.inspectionsheet.ui.activities.InspectLineTower;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;

public class InspectLineTowerPresenter implements InspectLineTowerContract.Presenter {

    private InspectLineTowerContract.View view;
    private InspectionSheetApplication application;
    private Line line;
    private Tower currentTower;

    private List<Tower> towers;

    private static final int SEARCH_RADIUS = 100; //радиус поиска опоры в метрах

    public InspectLineTowerPresenter(InspectLineTowerContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
        line = null;
        currentTower = null;
        towers = new ArrayList<>();
    }

    @Override
    public void onViewCreated(String nextTower) {

        line = application.getLineInspection().getLine();
        towers = line.getTowers();

        if (nextTower.isEmpty()) {
            currentTower = application.getTowerStorage().getFirstInLine(line.getUniqId());
        } else {
            currentTower = application.getTowerStorage().getByNumberInLine(nextTower, line.getUniqId());
        }
        view.setTowerNumber(currentTower.getName());


        List<LineTowerDeffectType> deffectTypes = application.getLineTowerDeffectTypesStorage().load();
        List<LineTowerInspectionItem> inspectionItems = new ArrayList<>();


        for (LineTowerDeffectType deffectType : deffectTypes) {
            inspectionItems.add(new LineTowerInspectionItem(0, deffectType, true));
        }

        view.setInpectionList(inspectionItems);
    }

    @Override
    public void onSelectTowerBtnClick() {

        view.showSelectTowerDialog(towers);
    }

    @Override
    public void onTowerSelected(int pos) {

        if (pos >= towers.size()) {
            return;
        }

        Tower tower = towers.get(pos);
        view.setTowerNumber(tower.getName());
    }

    @Override
    public void onGPSSwitchChange(boolean isOn) {

        towers = line.getTowers();
        if (!isOn) {
            return;
        }
        filterTowersByUserLocation();

    }

    @Override
    public void onGPSLocationChange() {

        towers = line.getTowers();
        filterTowersByUserLocation();
    }

    private void filterTowersByUserLocation() {

        List<Tower> filteredTowers = new ArrayList<>();
        ILocation locationService = application.getLocationService();
        for (Tower tower : towers) {
            double distance = locationService.distanceBetween(tower.getMapPoint(), locationService.getUserPosition());
            if (distance <= SEARCH_RADIUS) {
                filteredTowers.add(tower);
            }
        }
        this.towers = filteredTowers;
    }


    @Override
    public void onDestroy() {
        this.view = null;
    }
}
