package ru.drsk.progserega.inspectionsheet.services;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
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
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;

public class InspectionService {

    private InspectionSheetDatabase db;
    private Context context;
    private ITransformerStorage transformerStorage;
    private IInspectionStorage inspectionStorage;
    private ITowerStorage towerStorage;
    private ILineInspectionStorage lineInspectionStorage;
    private ILineSectionStorage sectionStorage;
   // private List< InspectionItem > inspectionItemsTemplate = null;

    public InspectionService(InspectionSheetDatabase db,
                             Context context,
                             ITransformerStorage transformerStorage,
                             IInspectionStorage inspectionStorage,
                             ITowerStorage towerStorage,
                             ILineInspectionStorage lineInspectionStorage,
                             ILineSectionStorage sectionStorage) {

        this.db = db;
        this.context = context;
        this.transformerStorage = transformerStorage;
        this.inspectionStorage = inspectionStorage;
        this.towerStorage = towerStorage;
        this.lineInspectionStorage = lineInspectionStorage;
        this.sectionStorage = sectionStorage;
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

    public List< TransformerInspection > getInspectionByEquipment(EquipmentType equipmentType) {

        if (equipmentType.equals(EquipmentType.SUBSTATION)) {
            return getSubstationInspections();
        }

        return null;
    }

    private List< TransformerInspection > getSubstationInspections() {

        SubstationDao substationDao = db.substationDao();
        List< SubstationModel > substationModels = substationDao.loadInspected();

        List< TransformerInspection > inspectionList = new ArrayList<>();
        for (SubstationModel substationModel : substationModels) {
            Equipment substation = new Substation(substationModel.getId(), substationModel.getUniqId(), substationModel.getName(), substationModel.getInspectionDate(), substationModel.getInspectionPercent());
            inspectionList.addAll(getSubstationTransformersWithInspections(substation));
        }
        return inspectionList;

    }

    public List< TransformerInspection > getSubstationTransformersWithInspections(Equipment substation) {
        List< TransformerInSlot > transformers = transformerStorage.getBySubstantionId(substation.getUniqId(), substation.getType());
        return buildInspectionsList(substation, transformers);
    }

    private List< TransformerInspection > buildInspectionsList(Equipment equipment, List< TransformerInSlot > transformers) {
        List< TransformerInspection > inspectionList = new ArrayList<>();

        for (TransformerInSlot transformer : transformers) {

            TransformerInspection inspection = new TransformerInspection(equipment, transformer);
            inspection.setInspectionItems(loadInspectionTemplates());
            inspectionList.add(inspection);

            //Загрузка значений из бд
            inspectionStorage.loadInspections(inspection);
        }

        return inspectionList;
    }

    public List< InspectedLine > getInspectedLines(){
        List< LineInspection > lineInspections = lineInspectionStorage.getAllLineInspections();

        List<InspectedLine> inspectedLines = new ArrayList<>();
        for(LineInspection lineInspection : lineInspections){
            List<InspectedTower> inspectedTowers = getInspectedTowersByLine(lineInspection.getLine().getUniqId());
            List<InspectedSection> inspectedSections = getInspectedSectionsByLine(lineInspection.getLine().getUniqId());
            inspectedLines.add(new InspectedLine(lineInspection, inspectedTowers, inspectedSections));

        }

        return inspectedLines;
    }

    public List< InspectedTower > getInspectedTowersByLine(long lineUniqId) {

        List<TowerInspection> towerInspections =  lineInspectionStorage.getTowerInspectionByLine(lineUniqId);
        Map<Long, TowerInspection > inspectionMap = new HashMap<>();

        for(TowerInspection towerInspection: towerInspections){
            inspectionMap.put(towerInspection.getTowerUniqId(), towerInspection );
        }

        Set<Long> towersUniqIdsSet = inspectionMap.keySet();
        Long[] towersUniqIds = towersUniqIdsSet.toArray(new Long[towersUniqIdsSet.size()]);

        List< Tower> towers = towerStorage.getByUniqIds(towersUniqIds);

        List<InspectedTower> inspectedTowers = new ArrayList<>();

        for(Tower tower: towers){
            List< TowerDeffect > deffects = lineInspectionStorage.getTowerDeffects(tower.getUniqId());
            inspectedTowers.add(new InspectedTower(
                    tower,
                    inspectionMap.get(tower.getUniqId()),
                    deffects));
        }

        return inspectedTowers;

    }

    public List< InspectedSection > getInspectedSectionsByLine(long lineUniqId) {

        List< LineSectionInspection > sectionInspectionByLine =  lineInspectionStorage.getSectionInspectionByLine(lineUniqId);
        Map<Long, LineSectionInspection > inspectionMap = new HashMap<>();

        for(LineSectionInspection sectionInspection: sectionInspectionByLine){
            inspectionMap.put(sectionInspection.getSectionId(), sectionInspection );
        }

        Set<Long> sectionsIdsSet = inspectionMap.keySet();
        Long[] sectionIds = sectionsIdsSet.toArray(new Long[sectionsIdsSet.size()]);

        List< LineSection > sections = sectionStorage.getByIds(sectionIds);

        List<InspectedSection> inspectedSections = new ArrayList<>();

        for(LineSection section: sections){
            List< LineSectionDeffect > deffects = lineInspectionStorage.getSectionDeffects(section.getId());
            inspectedSections.add(new InspectedSection(
                    section,
                    inspectionMap.get(section.getUniqId()),
                    deffects));
        }

        return inspectedSections;

    }


    //TODO закэшировать значения чтобы не читать постоянно
    public List< InspectionItem > loadInspectionTemplates() {
        List< InspectionItem > template = new ArrayList<>();
        try {
            TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
            template = inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.transormator_inspection_list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }
}
