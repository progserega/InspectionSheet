package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;

public class InspectLineTowerPresenter implements InspectLineTowerContract.Presenter {

    private InspectLineTowerContract.View view;
    private InspectionSheetApplication application;


    private Line line;
    private Tower currentTower;
    private List< Tower > towers;
    private List< TowerDeffect > deffects;
    private Set< Integer > changedDeffects;

    private TowerInspection inspection;

    private List< LineSection > nextSections = null;

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
    public void onViewCreated(long nextTowerUniqId) {

        line = application.getCurrentLineInspection().getLine();
        towers = line.getTowers();
        nextSections = null;

        if (nextTowerUniqId == 0) {
            currentTower = application.getTowerStorage().getFirstInLine(line.getUniqId());
        } else {
            currentTower = application.getTowerStorage().getByUniqId(nextTowerUniqId);
        }
        if (currentTower == null) {
            return;
        }

        setViewData();
    }

    private void setViewData() {
        view.setTowerNumber(currentTower.getName());

        view.setMaterialsSpinnerData(getMaterials(), getMaterialIdx());
        view.setTowerTypesSpinnerData(getTowerTypes(), getTowerTypeIdx());

        deffects = readDeffects();
        view.setDeffectsList(deffects);
        changedDeffects.clear();

        inspection = application.getLineInspectionStorage().getTowerInspection(currentTower.getUniqId());
        if (inspection == null) {
            inspection = new TowerInspection(0, currentTower.getUniqId(), "", new Date());
        }

        view.setComment(inspection.getComment());

        view.setTowerPhotos(inspection.getPhotos());
    }

    private int getMaterialIdx() {

        if (currentTower.getMaterial() != null && currentTower.getMaterial().getId() != 0) {
            return currentTower.getMaterial().getId();
        }

        if (line.getCachedTowerMaterial() != null) {
            return line.getCachedTowerMaterial().getId();
        }

        return 0;
    }

    private int getTowerTypeIdx() {
        if (currentTower.getTowerType() != null && currentTower.getTowerType().getId() != 0) {
            return currentTower.getTowerType().getId();
        }

        if (line.getCachedTowerType() != null) {
            return line.getCachedTowerType().getId();
        }

        return 0;
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

        long towerUniqId = towers.get(pos).getUniqId();
        currentTower = application.getTowerStorage().getByUniqId(towerUniqId);
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
        if (currentTower == null) {
            return;
        }

        saveCurrentTower();

        //определяем пролет
        nextSections = getNextSections();

        if (nextSections == null || nextSections.isEmpty()) {
            view.showEndOfLineDialog();
            return;
        }

        if (nextSections.size() > 1) {
            String[] sectionNames = new String[nextSections.size()];
            int i = 0;
            for (LineSection section : nextSections) {
                sectionNames[i] = section.getName();
                i++;
            }
            view.showNextSectionSelectorDialog(sectionNames);
        } else {
            view.gotoSectionInspection(nextSections.get(0).getId());
        }
    }

    private void saveCurrentTower() {
        if (currentTower == null) {
            return;
        }

        //сохраняем выявленные деффекты
        saveDeffects();

        //сохраняем материал и тип опоры
        application.getTowerStorage().update(currentTower);

        //сохраняем комментарии и фотографии
        String comment = view.getComment();
        inspection.setComment(comment);
        inspection.setInspectionDate(new Date());
        application.getLineInspectionStorage().saveToweInspection(inspection);
    }

    @Override
    public void onNextSectionSelected(int pos) {
        if (nextSections == null || nextSections.isEmpty()) {
            return;
        }
        view.gotoSectionInspection(nextSections.get(pos).getId());
    }

    @Override
    public void finishButtonPressed() {
        saveCurrentTower();
    }

    @Override
    public void onMaterialSelected(int pos) {
        currentTower.setMaterial(application.getCatalogStorage().getMaterials().get(pos));
        line.setCachedTowerMaterial(application.getCatalogStorage().getMaterials().get(pos));
    }

    @Override
    public void onTowerTypeSelected(int pos) {
        currentTower.setTowerType(application.getCatalogStorage().getTowerTypes().get(pos));
        line.setCachedTowerType(application.getCatalogStorage().getTowerTypes().get(pos));
    }

    @Override
    public void onImageTaken(String photoPath) {
        inspection.getPhotos().add(new InspectionPhoto(0, photoPath, application.getApplicationContext()));
    }

    private List< String > getMaterials() {
        List< Material > materialList = application.getCatalogStorage().getMaterials();

        List< String > materialsStr = new ArrayList<>();
        for (Material material : materialList) {
            materialsStr.add(material.getName());
        }
        return materialsStr;
    }

    private List< String > getTowerTypes() {
        List< TowerType > types = application.getCatalogStorage().getTowerTypes();
        List< String > typesNames = new ArrayList<>();
        for (TowerType type : types) {
            typesNames.add(type.getType());
        }
        return typesNames;
    }

    private void filterTowersByUserLocation() {

        List< Tower > filteredTowers = new ArrayList<>();
        ILocation locationService = application.getLocationService();
        for (Tower tower : towers) {
            double distance = locationService.distanceBetween(tower.getMapPoint(), locationService.getUserPosition());
            if (distance <= SEARCH_RADIUS) {
                filteredTowers.add(tower);
            }
        }
        this.towers = filteredTowers;
    }

    private List< TowerDeffect > readDeffects() {
        //получаем сохраненные деффекты
        List< TowerDeffect > towerDeffects = application.getLineInspectionStorage().getTowerDeffects(currentTower.getUniqId());
        //конвертнем в хэшмап для быстрого поиска
        Map< Long, TowerDeffect > towerDeffectsMap = new HashMap<>();
        for (TowerDeffect towerDeffect : towerDeffects) {
            towerDeffectsMap.put(towerDeffect.getDeffectType().getId(), towerDeffect);
        }

        //Получаем список возможных вариантов
        List< LineDeffectType > deffectTypes = application.getLineDeffectTypesStorage().loadTowerDeffects();

        //Формируем список всех деффектов с учетом ранее заполненных
        List< TowerDeffect > allDeffects = new ArrayList<>();
        for (LineDeffectType deffectType : deffectTypes) {
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

    private List< LineSection > getNextSections() {
        if (currentTower == null) {
            return new ArrayList<>();
        }
        List< LineSection > sectionModels = application.getLineSectionStorage().getByLineStartWithTower(line.getUniqId(), currentTower.getUniqId());

        return sectionModels;
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
