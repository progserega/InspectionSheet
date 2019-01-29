package ru.drsk.progserega.inspectionsheet.services;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
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
    private List<InspectionItem> inspectionItemsTemplate;
    private ITransformerStorage transformerStorage;
    private IInspectionStorage inspectionStorage;


    public InspectionService(InspectionSheetDatabase db, ITransformerStorage transformerStorage, IInspectionStorage inspectionStorage, Context context) {
        this.db = db;
        this.context = context;
        this.transformerStorage = transformerStorage;
        this.inspectionStorage = inspectionStorage;

        inspectionItemsTemplate = null;


    }

    public List<TransformerInspection> getInspectionByEquipment(EquipmentType equipmentType){

        if(equipmentType.equals(EquipmentType.SUBSTATION))
        {
            return getSubstationInspections();
        }

        return null;
    }

    private List<TransformerInspection> getSubstationInspections(){

        SubstationDao substationDao = db.substationDao();
        List<SubstationModel> substationModels = substationDao.loadInspected();

        List<TransformerInspection> inspectionList = new ArrayList<>();
        for(SubstationModel substationModel: substationModels){
            Equipment substation = new Substation( substationModel.getId(), substationModel.getName(), substationModel.getInspectionDate(), substationModel.getInspectionPercent());
            inspectionList.addAll(getSubstationTransformersWithInspections(substation));
        }
        return inspectionList;

    }



    public List<TransformerInspection> getSubstationTransformersWithInspections(Equipment substation){
        List<TransformerInSlot> transformers = transformerStorage.getBySubstantionId(substation.getId(), substation.getType());
        return buildInspectionsList(substation, transformers);
    }

    private List<TransformerInspection> buildInspectionsList(Equipment equipment, List<TransformerInSlot> transformers) {
        List<TransformerInspection> inspectionList = new ArrayList<>();

        for (TransformerInSlot transformer : transformers) {

            TransformerInspection inspection = new TransformerInspection(equipment, transformer);
            inspection.setInspectionItems(loadInspectionTemplates());
            inspectionList.add(inspection);

            //Загрузка значений из бд
            inspectionStorage.loadInspections(inspection);
        }

        return inspectionList;
    }


    //TODO закэшировать значения чтобы не читать постоянно
    public List<InspectionItem> loadInspectionTemplates(){
        List<InspectionItem> template = new ArrayList<>();
        try {
            TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
            template = inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.transormator_inspection_list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }
}
