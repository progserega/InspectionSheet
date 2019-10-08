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
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineSectionContract;

public class InspectLineSectionPresenter implements InspectLineSectionContract.Presenter {

    private InspectLineSectionContract.View view;
    private InspectionSheetApplication application;


    private Line line;
    private LineSection currentSection;

    private List<LineSectionDeffect> deffects;
    private Set<Integer> changedDeffects;

    private LineSectionInspection inspection;



    public InspectLineSectionPresenter(InspectLineSectionContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
        line = null;
        currentSection = null;
        changedDeffects = new HashSet<>();
    }

    @Override
    public void onViewCreated(long sectionId) {
        if (sectionId == 0) {
            return;
        }

        line = application.getCurrentLineInspection().getLine();
        currentSection = application.getLineSectionStorage().getById(sectionId);

        setViewData();
    }

    private void setViewData() {

        if (currentSection == null) {
            return;
        }

        view.setSectionNumber(currentSection.getName());

        view.setMaterialsSpinnerData(getMaterials(), getMaterialIdx());

        deffects = readDeffects();
        view.setDeffectsList(deffects);
        changedDeffects.clear();

        inspection = application.getLineInspectionStorage().getSectionInspection(currentSection.getId());
        if (inspection == null) {
            inspection = new LineSectionInspection(0, currentSection.getId(), "", new Date());
        }

        view.setComment(inspection.getComment());

        view.setInspectionPhotos(inspection.getPhotos());
    }

    private int getMaterialIdx() {

        if (currentSection.getMaterial() != null && currentSection.getMaterial().getId() != 0) {
            return currentSection.getMaterial().getId();
        }

        if (line.getCachedSectionMaterial() != null) {
            return line.getCachedSectionMaterial().getId();
        }

        return 0;
    }

    @Override
    public void onDeffectSelectionChange(int pos, boolean isSelected) {
        deffects.get(pos).setValue(isSelected ? 1 : 0);
        changedDeffects.add(pos);
    }

    @Override
    public void nextButtonPressed() {
        if (currentSection == null) {
            return;
        }

        saveCurrentSection();

        //определяем следующую опору
        long nextTowerUniqId = currentSection.getTowerToUniqId();

        view.gotoNextTowerInspection(nextTowerUniqId);
    }

    private void saveCurrentSection(){
        //сохраняем выявленные деффекты
        saveDeffects();

        //сохраняем материал
        application.getLineSectionStorage().update(currentSection);

        //сохраняем комментарии и фотографии
        String comment = view.getComment();
        inspection.setComment(comment);
        inspection.setInspectionDate(new Date());
        application.getLineInspectionStorage().saveSectionInspection(inspection);

    }

    @Override
    public void finishButtonPressed() {
        saveCurrentSection();
    }

    @Override
    public void onMaterialSelected(int pos) {
        currentSection.setMaterial(application.getCatalogStorage().getLineSectionMaterials().get(pos));
        line.setCachedSectionMaterial(application.getCatalogStorage().getLineSectionMaterials().get(pos));
    }

    @Override
    public void onImageTaken(String photoPath) {
        inspection.getPhotos().add(new InspectionPhoto(0, photoPath, application.getApplicationContext()));
    }

    private List<String> getMaterials() {
        List<Material> materialList = application.getCatalogStorage().getLineSectionMaterials();

        List<String> materialsStr = new ArrayList<>();
        for (Material material : materialList) {
            materialsStr.add(material.getName());
        }
        return materialsStr;
    }

    private List<LineSectionDeffect> readDeffects() {
        //получаем сохраненные деффекты
        List<LineSectionDeffect> sectionDeffects = application.getLineInspectionStorage().getSectionDeffects(currentSection.getId());
        //конвертнем в хэшмап для быстрого поиска
        Map<Long, LineSectionDeffect> lineSectionDeffectMap = new HashMap<>();
        for (LineSectionDeffect sectionDeffect : sectionDeffects) {
            lineSectionDeffectMap.put(sectionDeffect.getDeffectType().getId(), sectionDeffect);
        }

        //Получаем список возможных вариантов
        List<LineDeffectType> deffectTypes = application.getLineDeffectTypesStorage().loadSectionDeffects();

        //Формируем список всех деффектов с учетом ранее заполненных
        List<LineSectionDeffect> allDeffects = new ArrayList<>();
        for (LineDeffectType deffectType : deffectTypes) {
            LineSectionDeffect savedDeffect = lineSectionDeffectMap.get(deffectType.getId());

            if (savedDeffect != null) {
                allDeffects.add(savedDeffect);
            } else {
                allDeffects.add(new LineSectionDeffect(0, currentSection.getId(),  deffectType, 0));
            }
        }

        return allDeffects;
    }

    private void saveDeffects() {
        for (Integer pos : changedDeffects) {
            LineSectionDeffect deffect = deffects.get(pos);
            if (deffect.getId() == 0) {

                if (deffect.getValue() == 1) {

                    long id = application.getLineInspectionStorage().addSectionDeffect(deffect);
                    deffect.setId(id);

                }
            } else {
                   application.getLineInspectionStorage().updateSectionDeffect(deffect);
            }
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
