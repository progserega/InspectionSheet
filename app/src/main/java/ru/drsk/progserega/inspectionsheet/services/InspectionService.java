package ru.drsk.progserega.inspectionsheet.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

public class InspectionService {

    private InspectionSheetDatabase db;
    private Context context;
    private ITransformerStorage transformerStorage;
    private IInspectionStorage inspectionStorage;
    private ITowerStorage towerStorage;
    private ILineInspectionStorage lineInspectionStorage;
    private ILineSectionStorage sectionStorage;
    private ITransformerDeffectTypesStorage transformerDeffectTypesStorage;

    // private List< InspectionItem > inspectionItemsTemplate = null;

    public InspectionService(InspectionSheetDatabase db,
                             Context context,
                             ITransformerStorage transformerStorage,
                             IInspectionStorage inspectionStorage,
                             ITowerStorage towerStorage,
                             ILineInspectionStorage lineInspectionStorage,
                             ILineSectionStorage sectionStorage,
                             ITransformerDeffectTypesStorage transformerDeffectTypesStorage) {

        this.db = db;
        this.context = context;
        this.transformerStorage = transformerStorage;
        this.inspectionStorage = inspectionStorage;
        this.towerStorage = towerStorage;
        this.lineInspectionStorage = lineInspectionStorage;
        this.sectionStorage = sectionStorage;
        this.transformerDeffectTypesStorage = transformerDeffectTypesStorage;
    }

    //    public InspectionService(InspectionSheetDatabase db, ITransformerStorage transformerStorage, IInspectionStorage inspectionStorage, Context context) {
//        this.db = db;
//        this.context = context;
//        this.transformerStorage = transformerStorage;
//        this.inspectionStorage = inspectionStorage;
//
//        inspectionItemsTemplate = null;
//
//
//    }

    public List< IStationInspection > getInspectionByEquipment(EquipmentType equipmentType, StationInspectionFactory inspectionFactory) {

        if (equipmentType.equals(EquipmentType.SUBSTATION)) {
            return null; //getSubstationInspections();
        }

        if (equipmentType.equals(EquipmentType.TP)) {
            return getTPInspections(inspectionFactory);
        }

        return null;
    }

    private List< TransformerInspection > getSubstationInspections() {

        SubstationDao substationDao = db.substationDao();
        List< SubstationModel > substationModels = substationDao.loadInspected();

        List< TransformerInspection > inspectionList = new ArrayList<>();
        for (SubstationModel substationModel : substationModels) {
            Equipment substation = new Substation(
                    substationModel.getId(),
                    substationModel.getUniqId(),
                    substationModel.getName(),
                    substationModel.getInspectionDate(),
                    substationModel.getInspectionPercent(),
                    substationModel.getLat(),
                    substationModel.getLon()
            );
            inspectionList.addAll(getSubstationTransformersWithInspections(substation));
        }
        return inspectionList;

    }

//    private List< TransformerInspection > getTPInspections() {
//
//
//        List< TransformerSubstationModel > substationModels = db.transformerSubstationDao().loadInspected();
//
//        List< TransformerInspection > inspectionList = new ArrayList<>();
//        for (TransformerSubstationModel substationModel : substationModels) {
//            Equipment substation = new TransformerSubstation(
//                    substationModel.getId(),
//                    substationModel.getUniqId(),
//                    substationModel.getDispName(),
//                    substationModel.getInspectionDate(),
//                    substationModel.getInspectionPercent(),
//                    substationModel.getLat(),
//                    substationModel.getLon()
//            );
//            inspectionList.addAll(getTPTransformersWithInspections(substation));
//        }
//        return inspectionList;
//
//    }

    private List< IStationInspection > getTPInspections(StationInspectionFactory inspectionFactory) {
        List<IStationInspection> inspectedStations = new ArrayList<>();

        List< StationModel > stationModels = db.stationDao().loadInspectedByType(EquipmentType.TP.getValue());
        for(StationModel stationModel: stationModels){
            TransformerSubstation tp = buildTPFromModel(stationModel);
            inspectedStations.add(inspectionFactory.build(tp));
        }

        return inspectedStations;

//        List< TransformerSubstationModel > substationModels = db.transformerSubstationDao().loadInspected();
//
//        List< TransformerInspection > inspectionList = new ArrayList<>();
//        for (TransformerSubstationModel substationModel : substationModels) {
//            Equipment substation = new TransformerSubstation(
//                    substationModel.getId(),
//                    substationModel.getUniqId(),
//                    substationModel.getDispName(),
//                    substationModel.getInspectionDate(),
//                    substationModel.getInspectionPercent(),
//                    substationModel.getLat(),
//                    substationModel.getLon()
//            );
//            inspectionList.addAll(getTPTransformersWithInspections(substation));
//        }
//        return inspectionList;

    }

    private Substation buildSubstationFromModel(StationModel stationModel) {
        return new Substation(
                stationModel.getUniqId(),
                stationModel.getUniqId(),
                stationModel.getName(),
                stationModel.getInspectionDate(),
                stationModel.getInspectionPercent(),
                stationModel.getLat(),
                stationModel.getLon()
        );
    }
    private TransformerSubstation buildTPFromModel(StationModel stationModel) {
        return new TransformerSubstation(
                stationModel.getUniqId(),
                stationModel.getUniqId(),
                stationModel.getName(),
                stationModel.getInspectionDate(),
                stationModel.getInspectionPercent(),
                stationModel.getLat(),
                stationModel.getLon()
        );
    }

    public List< TransformerInspection > getSubstationTransformersWithInspections(Equipment substation) {
        List<Transformer> transformers = transformerStorage.getBySubstantionId(substation.getUniqId(), substation.getType());
        return buildInspectionsList(substation, transformers);
    }

    public List< TransformerInspection > getTPTransformersWithInspections(Equipment substation) {
        List<Transformer> transformers = transformerStorage.getBySubstantionId(substation.getUniqId(), substation.getType());
        return buildInspectionsList(substation, transformers);
    }

    private List< TransformerInspection > buildInspectionsList(Equipment equipment, List<Transformer> transformers) {
        List< TransformerInspection > inspectionList = new ArrayList<>();

        for (Transformer transformer : transformers) {

            TransformerInspection inspection = new TransformerInspection(equipment, transformer);
            inspection.setInspectionItems(loadInspectionTemplates(equipment.getType()));
            inspectionList.add(inspection);

            //Загрузка значений из бд
            inspectionStorage.loadInspections(inspection);
        }

        return inspectionList;
    }

    public List< InspectedLine > getInspectedLines() {
        List< LineInspection > lineInspections = lineInspectionStorage.getAllLineInspections();

        List< InspectedLine > inspectedLines = new ArrayList<>();
        for (LineInspection lineInspection : lineInspections) {
            List< InspectedTower > inspectedTowers = getInspectedTowersByLine(lineInspection.getLine());
            List< InspectedSection > inspectedSections = getInspectedSectionsByLine(lineInspection.getLine());

            List< Tower > towers = towerStorage.getByLineUniqId(lineInspection.getLine().getUniqId());
            List< LineSection > sections = sectionStorage.getByLine(lineInspection.getLine().getUniqId());

            int totalCnt = towers.size() + sections.size();
            int inspectedCnt = inspectedTowers.size() + inspectedSections.size();
            float pecent = 0;
            if (totalCnt != 0) {
                pecent = inspectedCnt * 100.0f / totalCnt;
            }

            lineInspection.setInspectionPercent(pecent);

            inspectedLines.add(new InspectedLine(lineInspection, inspectedTowers, inspectedSections));

        }

        return inspectedLines;
    }

    public List< InspectedTower > getInspectedTowersByLine(Line line) {

        List< TowerInspection > towerInspections = lineInspectionStorage.getTowerInspectionByLine(line.getUniqId());
        Map< Long, TowerInspection > inspectionMap = new HashMap<>();

        for (TowerInspection towerInspection : towerInspections) {
            inspectionMap.put(towerInspection.getTowerUniqId(), towerInspection);
        }

        Set< Long > towersUniqIdsSet = inspectionMap.keySet();
        Long[] towersUniqIds = towersUniqIdsSet.toArray(new Long[towersUniqIdsSet.size()]);

        List< Tower > towers = towerStorage.getByUniqIds(towersUniqIds);

        List< InspectedTower > inspectedTowers = new ArrayList<>();

        for (Tower tower : towers) {
            List< TowerDeffect > deffects = lineInspectionStorage.getTowerDeffects(tower.getUniqId(), line);
            inspectedTowers.add(new InspectedTower(
                    tower,
                    inspectionMap.get(tower.getUniqId()),
                    deffects));
        }

        return inspectedTowers;

    }

    public List< InspectedSection > getInspectedSectionsByLine(Line line) {

        List< LineSectionInspection > sectionInspectionByLine = lineInspectionStorage.getSectionInspectionByLine(line.getUniqId());
        Map< Long, LineSectionInspection > inspectionMap = new HashMap<>();

        for (LineSectionInspection sectionInspection : sectionInspectionByLine) {
            inspectionMap.put(sectionInspection.getSectionId(), sectionInspection);
        }

        Set< Long > sectionsIdsSet = inspectionMap.keySet();
        Long[] sectionIds = sectionsIdsSet.toArray(new Long[sectionsIdsSet.size()]);

        List< LineSection > sections = sectionStorage.getByIds(sectionIds);

        List< InspectedSection > inspectedSections = new ArrayList<>();

        for (LineSection section : sections) {
            List< LineSectionDeffect > deffects = lineInspectionStorage.getSectionDeffects(section.getId(), line);
            inspectedSections.add(new InspectedSection(
                    section,
                    inspectionMap.get(section.getUniqId()),
                    deffects));
        }

        return inspectedSections;

    }

    public List< InspectionItem > loadInspectionTemplates(EquipmentType substationType) {
        List< InspectionItem > template = new ArrayList<>();

        if (substationType == EquipmentType.SUBSTATION) {
            template = transformerDeffectTypesStorage.getSubstationDeffects();
        }

        if (substationType == EquipmentType.TP) {
            template = transformerDeffectTypesStorage.getTPDeffects();
        }

        return template;
    }
}
