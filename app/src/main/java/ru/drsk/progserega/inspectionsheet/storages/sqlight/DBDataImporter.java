package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTransformator;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionItemDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SpWithRes;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

public class DBDataImporter {
    private InspectionSheetDatabase db;
    private SPDao spDao;
    private ResDao resDao;
    private SpWithResDao spWithResDao;
    private TransformerDao transformerDao;
    private TransformerSubstationDao transformerSubstationDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;
    private SubstationDao substationDao;
    private SubstationEquipmentDao substationEquipmentDao;
    private InspectionDao inspectionDao;
    private InspectionPhotoDao inspectionPhotoDao;
    private InspectionItemDao inspectionItemDao;

    private Map<String, Long> spCache;
    private Map<String, Long> resCache;
    private Set<Long> transfCache;



    public DBDataImporter(InspectionSheetDatabase db) {
        this.db = db;
        spDao = db.spDao();
        resDao = db.resDao();
        spWithResDao = db.spWithResDao();
        transformerDao = db.transformerDao();
        transformerSubstationDao = db.transformerSubstationDao();
        transformerSubstationEquipmentDao = db.transfSubstationEquipmentDao();
        substationDao = db.substationDao();
        substationEquipmentDao = db.substationEquipmentDao();
        inspectionDao = db.inspectionDao();
        inspectionPhotoDao = db.inspectionPhotoDao();

       // inspectionItemDao = db.inspectionItemDao();

        spCache = new HashMap<>();
        resCache = new HashMap<>();
        transfCache = new HashSet<>();



    }

    public void ClearDB(){
        transformerDao.delete();
        transformerSubstationDao.delete();
        transformerSubstationEquipmentDao.delete();
        substationDao.delete();
        substationEquipmentDao.delete();
        inspectionDao.deleteAll();
        inspectionPhotoDao.deleteAll();
    }

    public void loadSteTpModel(List<SteTPModel> tpModels) {

        for (SteTPModel tpModel : tpModels) {

            long spId = getOrCreateSp(tpModel.getSpName());
            long resId = getOrCreateRes(tpModel.getResName(), spId);

            TransformerSubstationModel substationModel = new TransformerSubstationModel(
                    tpModel.getId(),
                    tpModel.getUniqId(),
                    tpModel.getPowerCenterName(),
                    tpModel.getDispName(),
                    spId,
                    resId,
                    tpModel.getLat(),
                    tpModel.getLon());


            long tpId = transformerSubstationDao.insert(substationModel);


            List<SteTransformator> steTransformators = new ArrayList<>();
            if (tpModel.getT1() != null && tpModel.getT1().getDesc() != null) {
                steTransformators.add(tpModel.getT1());
            }
            if (tpModel.getT2() != null && tpModel.getT2().getDesc() != null) {
                steTransformators.add(tpModel.getT2());
            }

            if (tpModel.getT3() != null && tpModel.getT3().getDesc() != null) {
                steTransformators.add(tpModel.getT3());
            }

            int slot = 1;
            for (SteTransformator transformator : steTransformators) {

                TransformerModel transformerModel = new TransformerModel(
                        transformator.getId(),
                        transformator.getTrType(),
                        transformator.getDesc(),
                        "transformer_substation");


                if(!transfCache.contains(transformator.getId())){
                    transformerDao.insert(transformerModel);
                    transfCache.add(transformator.getId());
                }

                TransformerSubstationEuipmentModel transformerSubstationEuipmentModel = new TransformerSubstationEuipmentModel(0, tpId, transformator.getId(), slot );
                transformerSubstationEquipmentDao.insert(transformerSubstationEuipmentModel);
                slot++;
            }
        }


    }

    public void loadGeoSubstations(List<GeoSubstation> substations) {

        Set<String> spNames = spCache.keySet();
        Set<String> resNames = resCache.keySet();

        for (GeoSubstation substation : substations) {

            long spId = 0;
            if(substation.getSp() == null || substation.getSp().isEmpty()){
                spId = 0;

            }else {
                String spName = substation.getSp().get("name");
                spId = findSpIdInCache(spNames, spName);
            }


            long resId = 0;
            if(substation.getRes() == null || substation.getRes().isEmpty()) {
                resId = 0;
            }
            else{
                String resName = substation.getRes().get("name");
                resId = findResIdInCache(resNames, resName);
                if(resId == 0){
                    resId = getOrCreateRes(resName, spId);
                }
            }

            SubstationModel substationModel = new SubstationModel(
                    0,
                    substation.getName(),
                    substation.getVoltage(),
                    substation.getObjectType(),
                    spId,
                    resId,
                    substation.getLat(),
                    substation.getLon()
            );

            long substationId = substationDao.insert(substationModel);
        }

    }

    public void loadSubstationTransformers(List<SubstationTransformerJson> transformers){

        long maxId = transformerDao.getMaxId();
        for(SubstationTransformerJson transformerJson: transformers){

//            String descr = String.format("%s %s MBA, %s кВ, РПН: %s, наличие ПВБ: %s, защита масла: %s",
//                    transformerJson.getType(),
//                    transformerJson.getPower(),
//                    transformerJson.getVoltage(),
//                    transformerJson.getRpnType(),
//                    transformerJson.getIsPVB(),
//                    transformerJson.getOilDefenceType());

            String descr = String.format("%s",transformerJson.getType());

            maxId++;
            TransformerModel transformerModel = new TransformerModel(
                    maxId,
                    transformerJson.getType(),
                    descr,
                    "substation");

            transformerDao.insert(transformerModel);
        }
    }

    private long getOrCreateSp(String spName) {
        Long spId = spCache.get(spName);
        if (spId == null) {
            SP sp = new SP(0, spName, spName);
            spId = spDao.insert(sp);
            spCache.put(spName, spId);
        }
        return spId.longValue();
    }

    private long getOrCreateRes(String resName, long spId) {
        Long resId = resCache.get(resName);
        if (resId == null) {
            Res res = new Res(0, resName, resName, spId);
            resId = resDao.insert(res);
            resCache.put(resName, resId);
        }
        return resId.longValue();
    }


    public void initEnterpriseCache() {
        List<SpWithRes> spWithRes = spWithResDao.loadEnterprises();
        for (SpWithRes sp_res : spWithRes) {

            spCache.put(sp_res.getSp().getName(), sp_res.getSp().getId());
            for (Res res : sp_res.getNetworkAreas()) {
                resCache.put(res.getName(), res.getId());
            }
        }

    }

    private long findSpIdInCache(Set<String> spNames, String spName){
        spName = spName.replace("СП П", "");
        long spId = 0;
        for(String savedSpName: spNames){
            if(savedSpName.equals(spName)){
                spId = spCache.get(savedSpName);
                break;
            }
        }
        return spId;
    }

    private long findResIdInCache(Set<String> ResNames, String ResName){

        long resId = 0;
        for(String savedResName: ResNames){
            if(savedResName.equals(ResName)){
                resId = resCache.get(savedResName);
                break;
            }
        }

        if(resId > 0){
            return resId;
        }

        ResName = ResName.replace("РЭС", "").trim();
        for(String savedResName: ResNames){
            if(savedResName.toLowerCase().contains(ResName.toLowerCase())){
                resId = resCache.get(savedResName);
                break;
            }
        }

        return resId;
    }


    public void loadInspectionItems(Context appContext){
        inspectionItemDao.deleteAll();

        TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
        List<InspectionItem> inspectionItems = null;
        try {
            inspectionItems = inspectionListReader.readInspections(
                    appContext.getResources().openRawResource(R.raw.transormator_inspection_list)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        for(InspectionItem inspectionItem: inspectionItems){
            String result = "";
            if(inspectionItem.getResult().getPossibleResult()!=null){
                result = gson.toJson(inspectionItem.getResult().getPossibleResult());
            }

            String subResult = "";
            if(inspectionItem.getResult().getPossibleSubresult()!=null){
                subResult = gson.toJson(inspectionItem.getResult().getPossibleSubresult());
            }
            InspectionItemModel inspectionItemModel = new InspectionItemModel(inspectionItem, result, subResult);

            inspectionItemDao.insert(inspectionItemModel);
        }

        int a = 0;
    }
}
