package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTransformator;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;
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

    private Map<String, Long> spCache;
    private Map<String, Long> resCache;
    private Set<Long> transfCache;

    private boolean isEnterpriseCacheLoaded = false;
    private boolean isClearTransformerSusbstations = false;
    private boolean isClearSusbstations = false;

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

        spCache = new HashMap<>();
        resCache = new HashMap<>();
        transfCache = new HashSet<>();

        isEnterpriseCacheLoaded = false;
        isClearTransformerSusbstations = false;
        isClearSusbstations = false;

    }
/*
    public void loadSteTpModelWithUpdate(List<SteTPModel> tpModels) {
        if (!isEnterpriseCacheLoaded) {
            initEnterpriseCache();
            isEnterpriseCacheLoaded = true;
        }

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


            TransformerSubstationModel savedSubstationModel = transformerSubstationDao.getByUniqId(tpModel.getUniqId());


            if (savedSubstationModel == null) {
                transformerSubstationDao.insert(substationModel);
            } else {
                transformerSubstationDao.update(substationModel);
            }

            Map<Long, SteTransformator> steTransformatorMap = new HashMap<>();
            List<SteTransformator> steTransformators = new ArrayList<>();
            if (tpModel.getT1() != null && tpModel.getT1().getDesc() != null) {
                steTransformators.add(tpModel.getT1());
                steTransformatorMap.put(tpModel.getT1().getId(), tpModel.getT1());
            }
            if (tpModel.getT2() != null && tpModel.getT2().getDesc() != null) {
                steTransformators.add(tpModel.getT2());
                steTransformatorMap.put(tpModel.getT2().getId(), tpModel.getT2());
            }

            if (tpModel.getT3() != null && tpModel.getT3().getDesc() != null) {
                steTransformators.add(tpModel.getT3());
                steTransformatorMap.put(tpModel.getT3().getId(), tpModel.getT3());
            }

            for (SteTransformator transformator : steTransformators) {

                TransformerModel transformerModel = new TransformerModel(
                        transformator.getId(),
                        transformator.getTrType(),
                        transformator.getDesc());



            }

            //TODO: убить трансформаторы которых уже нет в списке (переместили и всетакое)


        }
    }
*/
    public void loadSteTpModelWithClean(List<SteTPModel> tpModels) {
        if (!isEnterpriseCacheLoaded) {
            initEnterpriseCache();
            isEnterpriseCacheLoaded = true;
        }

        if (!isClearTransformerSusbstations) {

            transformerDao.delete();
            transformerSubstationDao.delete();
            transformerSubstationEquipmentDao.delete();
            inspectionDao.deleteAll();
            inspectionPhotoDao.deleteAll();
            isClearTransformerSusbstations = true;

        }

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

            for (SteTransformator transformator : steTransformators) {

                TransformerModel transformerModel = new TransformerModel(
                        transformator.getId(),
                        transformator.getTrType(),
                        transformator.getDesc());


                if(!transfCache.contains(transformator.getId())){
                    transformerDao.insert(transformerModel);
                    transfCache.add(transformator.getId());
                }

                TransformerSubstationEuipmentModel transformerSubstationEuipmentModel = new TransformerSubstationEuipmentModel(0, tpId, transformator.getId() );
                transformerSubstationEquipmentDao.insert(transformerSubstationEuipmentModel);
            }
        }


    }

    public void loadGeoSubstationsWithClean(List<GeoSubstation> substations) {

        if (!isEnterpriseCacheLoaded) {
            initEnterpriseCache();
            isEnterpriseCacheLoaded = true;
        }

        if (!isClearSusbstations) {
            substationDao.delete();
            substationEquipmentDao.delete();
            inspectionDao.deleteAll();
            inspectionPhotoDao.deleteAll();
            isClearSusbstations = true;
        }

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


    private void initEnterpriseCache() {
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
}
