package ru.drsk.progserega.inspectionsheet.ui.presenters;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;

public class InspectLineTowerPresenter implements InspectLineTowerContract.Presenter {

    private InspectLineTowerContract.View view;
    private InspectionSheetApplication application;


    private Line line;
    private Tower currentTower;
    private List<Tower> towers;
    private List<TowerDeffect> deffects;
    private Set<Integer> changedDeffects;

    private static final int SEARCH_RADIUS = 100; //радиус поиска опоры в метрах

    public InspectLineTowerPresenter(InspectLineTowerContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
        line = null;
        currentTower = null;
        towers = new ArrayList<>();
        changedDeffects = new HashSet<>();
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
        if (currentTower == null) {
            return;
        }

        setViewData();
    }

    private void setViewData() {
        view.setTowerNumber(currentTower.getName().isEmpty() ? "не задан" : currentTower.getName());

        deffects = readDeffects();
        view.setDeffectsList(deffects);
        changedDeffects.clear();

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

        currentTower = towers.get(pos);
        setViewData();
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

    @Override
    public void onDeffectSelectionChange(int pos, boolean isSelected) {
        deffects.get(pos).setValue(isSelected ? 1 : 0);
        changedDeffects.add(pos);
    }

    @Override
    public void nextButtonPressed() {
        saveDeffects();
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

    private List<TowerDeffect> readDeffects() {
        //получаем сохраненные деффекты
        List<TowerDeffect> towerDeffects = application.getLineInspectionStorage().getTowerDeffects(currentTower.getUniqId());
        //конвертнем в хэшмап для быстрого поиска
        Map<Long, TowerDeffect> towerDeffectsMap = new HashMap<>();
        for (TowerDeffect towerDeffect : towerDeffects) {
            towerDeffectsMap.put(towerDeffect.getDeffectType().getId(), towerDeffect);
        }

        //Получаем список возможных вариантов
        List<LineTowerDeffectType> deffectTypes = application.getLineTowerDeffectTypesStorage().load();

        //Формируем список всех деффектов с учетом ранее заполненных
        List<TowerDeffect> allDeffects = new ArrayList<>();
        for (LineTowerDeffectType deffectType : deffectTypes) {
            TowerDeffect savedDeffect = towerDeffectsMap.get(deffectType.getId());

            if (savedDeffect != null) {
                allDeffects.add(savedDeffect);
            } else {
                allDeffects.add(new TowerDeffect(0, currentTower.getUniqId(), deffectType, 0));
            }
        }

        return allDeffects;
    }

    private void saveDeffects() {
        for (Integer pos : changedDeffects) {
            TowerDeffect deffect = deffects.get(pos);
            if (deffect.getId() == 0) {

                if (deffect.getValue() == 1) {

                    long id = application.getLineInspectionStorage().addTowerDeffect(deffect);
                    deffect.setId(id);

                }
            } else {
                application.getLineInspectionStorage().updateTowerDeffect(deffect);
            }
        }


    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
