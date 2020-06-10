package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.R;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineData;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationTransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerType;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLine;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetail;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineProlet;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineTower;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineTowerNode;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineTowers;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
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
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineTowerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SectionDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SpWithRes;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipment;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentModels;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentsDeffectType;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;
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
    private Map<String, Long> resAltCache;
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
        resAltCache = new HashMap<>();
        transfCache = new HashSet<>();


    }


    public void ClearDB() {
        // db.clearAllTables();
//        spDao.delete();
//        resDao.delete();
        transformerDao.delete();
        transformerSubstationDao.delete();
        transformerSubstationEquipmentDao.delete();
        substationDao.delete();
        substationEquipmentDao.delete();
        inspectionDao.deleteAll();
        inspectionPhotoDao.deleteAll();

        db.lineDao().deleteAll();
        db.lineTowerDao().deleteAll();
        db.towerDao().deleteAll();
        db.lineSectionDao().deleteAll();

        db.lineInspectionDao().deleteAll();

        db.lineSectionDeffectDao().deleteAll();
        db.lineSectionInspectionDao().deleteAll();

        db.towerDeffectDao().deleteAll();
        db.towerInspectionDao().deleteAll();

        db.towerDeffectTypesDao().deleteAll();
        db.sectionDeffectTypesDao().deleteAll();
        db.transformerDeffectTypesDao().deleteAll();

        db.stationDao().deleteAll();
        db.stationEquipmentDao().deleteAll();
        db.stationEquipmentModelsDao().deleteAll();

        db.stationEquipmentsDeffectTypesDao().deleteAll();
    }

//    public void loadSteTpModel(List< SteTPModel > tpModels) {
//
//        for (SteTPModel tpModel : tpModels) {
//
//            long spId = getSpFromCache(tpModel.getSpName());
//            long resId = getResFromCache(tpModel.getResName(), spId);
//
//            TransformerSubstationModel substationModel = new TransformerSubstationModel(
//                    tpModel.getId(),
//                    tpModel.getUniqId(),
//                    tpModel.getPowerCenterName(),
//                    tpModel.getDispName(),
//                    spId,
//                    resId,
//                    tpModel.getLat(),
//                    tpModel.getLon());
//
//
//            long tpId = db.transformerSubstationDao().insert(substationModel);
//
//
//            List< SteTransformator > steTransformators = new ArrayList<>();
//            if (tpModel.getT1() != null && tpModel.getT1().getDesc() != null) {
//                steTransformators.add(tpModel.getT1());
//            }
//            if (tpModel.getT2() != null && tpModel.getT2().getDesc() != null) {
//                steTransformators.add(tpModel.getT2());
//            }
//
//            if (tpModel.getT3() != null && tpModel.getT3().getDesc() != null) {
//                steTransformators.add(tpModel.getT3());
//            }
//
//            int slot = 1;
//            for (SteTransformator transformator : steTransformators) {
//
//                TransformerModel transformerModel = new TransformerModel(
//                        transformator.getId(),
//                        transformator.getTrType(),
//                        transformator.getDesc(),
//                        "transformer_substation");
//
//
//                if (!transfCache.contains(transformator.getId())) {
//                    transformerDao.insert(transformerModel);
//                    transfCache.add(transformator.getId());
//                }
//
//                TransformerSubstationEuipmentModel transformerSubstationEuipmentModel = new TransformerSubstationEuipmentModel(0, tpModel.getUniqId(), transformator.getId(), slot, 0, null);
//                transformerSubstationEquipmentDao.insert(transformerSubstationEuipmentModel);
//                slot++;
//            }
//        }
//
//
//    }

//    public void loadGeoSubstations(List< GeoSubstation > substations) {
//
//        Set< String > spNames = spCache.keySet();
//        Set< String > resNames = resCache.keySet();
//
//        for (GeoSubstation substation : substations) {
//
//            long spId = 0;
//            if (substation.getSp() == null || substation.getSp().isEmpty()) {
//                spId = 0;
//
//            } else {
//                String spName = substation.getSp().get("name");
//                spId = findSpIdInCache(spNames, spName);
//            }
//
//
//            long resId = 0;
//            if (substation.getRes() == null || substation.getRes().isEmpty()) {
//                resId = 0;
//            } else {
//                String resName = substation.getRes().get("name");
//                resId = findResIdInCache(resNames, resName);
//                if (resId == 0) {
//                    resId = getResFromCache(resName, spId);
//                }
//            }
//
//            SubstationModel substationModel = new SubstationModel(
//                    0,
//                    0,
//                    substation.getName(),
//                    substation.getVoltage(),
//                    substation.getObjectType(),
//                    spId,
//                    resId,
//                    substation.getLat(),
//                    substation.getLon(),
//                    null,
//                    0
//            );
//
//            long substationId = substationDao.insert(substationModel);
//        }
//
//    }

    public void loadSubstationTransformers(List<TransformerType> transformers, String installIn) {


        for (TransformerType type : transformers) {
            TransformerModel transformerModel = new TransformerModel(
                    type.getId(),
                    type.getName(),
                    type.getDescription(),
                    installIn);

            transformerDao.insert(transformerModel);
        }
    }

    public void importStationEquipmentModels(List<TransformerType> transformers, EquipmentType equipmentType) {
        for (TransformerType type : transformers) {

            StationEquipmentModels model = new StationEquipmentModels(
                    0,
                    type.getId(),
                    equipmentType.getValue(),
                    type.getName(),
                    type.getDescription()
            );

            db.stationEquipmentModelsDao().insert(model);
        }
    }

    public void loadSubstations(List<ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationModel> substations) {

        for (ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationModel substation : substations) {
            SubstationModel substationModel = new SubstationModel(
                    substation.getId(),
                    substation.getUniqId(),
                    substation.getName(),
                    null,
                    "substation",
                    substation.getSpId(),
                    substation.getResId(),
                    substation.getLat(),
                    substation.getLon(),
                    null,
                    0
            );

            long substationId = substationDao.insert(substationModel);

            StationModel stationModel = new StationModel(

                    substation.getUniqId() + 10000000,
                    substation.getName(),
                    null,
                    EquipmentType.SUBSTATION.getValue(),
                    substation.getSpId(),
                    substation.getResId(),
                    substation.getLat(),
                    substation.getLon(),
                    null,
                    0
            );

            db.stationDao().insert(stationModel);

            if (substation.getEquipmnents() == null) {
                continue;
            }
            for (SubstationTransformerModel transformer : substation.getEquipmnents()) {
                SubstationEquipmentModel equipmentModel = new SubstationEquipmentModel(
                        0,
                        substation.getUniqId(),
                        transformer.getTransformerTypeid(),
                        transformer.getSlot(),
                        transformer.getManufactureYear(),
                        null
                );
                substationEquipmentDao.insert(equipmentModel);

                StationEquipment stationEquipment = new StationEquipment(
                        0,
                        0,
                        substation.getUniqId() + 10000000,
                        EquipmentType.SUBSTATION_TRANSFORMER.getValue(),
                        transformer.getTransformerTypeid(),
                        "T" + transformer.getSlot(),
                        transformer.getManufactureYear(),
                        null
                );
                db.stationEquipmentDao().insert(stationEquipment);
            }
        }
    }

    public void loadTps(List<ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationModel> substations) {

        for (ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationModel substation : substations) {
            TransformerSubstationModel substationModel = new TransformerSubstationModel(
                    substation.getId(),
                    substation.getUniqId(),
                    "",
                    substation.getName(),
                    substation.getSpId(),
                    substation.getResId(),
                    substation.getLat(),
                    substation.getLon()

            );

            long substationId = transformerSubstationDao.insert(substationModel);

            StationModel stationModel = new StationModel(

                    substation.getUniqId(),
                    substation.getName(),
                    null,
                    EquipmentType.TP.getValue(),
                    substation.getSpId(),
                    substation.getResId(),
                    substation.getLat(),
                    substation.getLon(),
                    null,
                    0
            );

            try {
                long stationId = db.stationDao().insert(stationModel);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (substation.getEquipmnents() == null) {
                continue;
            }

            for (SubstationTransformerModel transformer : substation.getEquipmnents()) {
                TransformerSubstationEuipmentModel equipmentModel = new TransformerSubstationEuipmentModel(
                        0,
                        substation.getUniqId(),
                        transformer.getTransformerTypeid(),
                        transformer.getSlot(),
                        transformer.getManufactureYear(),
                        null
                );
                transformerSubstationEquipmentDao.insert(equipmentModel);

                StationEquipment stationEquipment = new StationEquipment(
                        0,
                        0,
                        substation.getUniqId(),
                        EquipmentType.TP_TRANSFORMER.getValue(),
                        transformer.getTransformerTypeid(),
                        "T" + transformer.getSlot(),
                        transformer.getManufactureYear(),
                        null
                );
                db.stationEquipmentDao().insert(stationEquipment);
            }
        }
    }

    public void addSp(long id, String name) {
        SP sp = new SP(id, name, name);
        spDao.insert(sp);
    }

    public void addRes(long id, long spId, String name, String altNmae) {
        Res res = new Res(id, name, altNmae, spId);
        resDao.insert(res);
    }


    private long getSpFromCache(String spName) {
        Long spId = spCache.get(spName);
        if (spId == null) {
            spId = 0l;
        }
        return spId.longValue();
    }


    private long getResFromCache(String resName, long spId) {
        Long resId = resCache.get(resName);
        if (resId == null) {
            Set<String> keys = resAltCache.keySet();
            for (String key : keys) {
                if (key.contains((resName))) {
                    resId = resAltCache.get(key);
                }
            }
            if (resId == null) {
                resId = 0l;
            }
        }
        return resId.longValue();
    }


    public void initEnterpriseCache() {
        List<SpWithRes> spWithRes = spWithResDao.loadEnterprises();
        for (SpWithRes sp_res : spWithRes) {

            spCache.put(sp_res.getSp().getName(), sp_res.getSp().getId());
            for (Res res : sp_res.getNetworkAreas()) {
                resCache.put(res.getName(), res.getId());
                resAltCache.put(res.getFullName(), res.getId());
            }
        }

    }

    private long findSpIdInCache(Set<String> spNames, String spName) {
        spName = spName.replace("СП П", "");
        long spId = 0;
        for (String savedSpName : spNames) {
            if (savedSpName.equals(spName)) {
                spId = spCache.get(savedSpName);
                break;
            }
        }
        return spId;
    }

    private long findResIdInCache(Set<String> ResNames, String ResName) {

        long resId = 0;
        for (String savedResName : ResNames) {
            if (savedResName.equals(ResName)) {
                resId = resCache.get(savedResName);
                break;
            }
        }

        if (resId > 0) {
            return resId;
        }

        ResName = ResName.replace("РЭС", "").trim();
        for (String savedResName : ResNames) {
            if (savedResName.toLowerCase().contains(ResName.toLowerCase())) {
                resId = resCache.get(savedResName);
                break;
            }
        }

        return resId;
    }

    public void loadInspectionItems(Context appContext) {
        inspectionItemDao.deleteAll();

        TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
        List<InspectionItem> inspectionItems = null;
        try {
            inspectionItems = inspectionListReader.readInspections(
                    appContext.getResources().openRawResource(R.raw.substation_transormer_deffect_types)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        for (InspectionItem inspectionItem : inspectionItems) {
            String result = "";
            if (inspectionItem.getResult().getPossibleResult() != null) {
                result = gson.toJson(inspectionItem.getResult().getPossibleResult());
            }

            String subResult = "";
            if (inspectionItem.getResult().getPossibleSubresult() != null) {
                subResult = gson.toJson(inspectionItem.getResult().getPossibleSubresult());
            }
            InspectionItemModel inspectionItemModel = new InspectionItemModel(inspectionItem, result, subResult);

            inspectionItemDao.insert(inspectionItemModel);
        }

        int a = 0;
    }

    public void loadLines(List<GeoLine> geoLines) {

        List<LineModel> sqlLines = new ArrayList<>();
        int id = 1;
        for (GeoLine geoLine : geoLines) {
            sqlLines.add(new LineModel(
                    id,
                    id,
                    geoLine.getName(),
                    geoLine.getVoltage(),
                    0,
                    0, 0, 0, 0
            ));
            id++;
        }
        db.lineDao().insertAll(sqlLines);
    }

    public void loadISLines(List<LineData> lines) {


        for (LineData line : lines) {

            LineModel lineModel = new LineModel(
                    line.getLineInfo().getId(),
                    line.getLineInfo().getUniqId(),
                    line.getLineInfo().getName(),
                    line.getLineInfo().getVoltage(),
                    0,
                    0, 0, 0, 0
            );

            db.lineDao().insert(lineModel);

            List<TowerModel> towerModels = new ArrayList<>();
            List<LineTowerModel> lineTowerModels = new ArrayList<>();

            int cnt = 1;
            for (TowerJson towerJson : line.getTowers()) {
                towerModels.add(new TowerModel(
                        towerJson.getUniqId(),
                        towerJson.getUniqId(),
                        towerJson.getName(),
                        towerJson.getMaterial(),
                        towerJson.getType(),
                        towerJson.getEle(),
                        towerJson.getLat(),
                        towerJson.getLon()));

                lineTowerModels.add(new LineTowerModel(0, line.getLineInfo().getUniqId(), towerJson.getUniqId(), cnt));
                cnt++;
            }
            this.db.lineTowerDao().insertAll(lineTowerModels);
            this.db.towerDao().insertAll(towerModels);
            updateLineBoundingBox(towerModels, line.getLineInfo().getUniqId());

            List<LineSectionModel> sectionModels = new ArrayList<>();
            for (SectionJson sectionJson : line.getSections()) {
                sectionModels.add(new LineSectionModel(
                        0,
                        sectionJson.getLineUniqId(),
                        sectionJson.getFromTowerUniqId(),
                        sectionJson.getToTowerUniqId(),
                        sectionJson.getName(),
                        sectionJson.getMaterial()
                ));
            }
            this.db.lineSectionDao().insertAll(sectionModels);

        }
    }

    public void loadLineDetail(GeoLine line, GeoLineDetail lineDetail) {

        long lineUniqId = line.getUniqId();

        Map<Long, TowerModel> towersMap = new LinkedHashMap<>();
        List<LineSectionModel> sectionModels = new ArrayList<>();

        buildLineDetailsModels(lineDetail.getWays(), towersMap, sectionModels, lineUniqId);

        List<TowerModel> towers = new ArrayList<TowerModel>(towersMap.values());
        List<LineTowerModel> lineTowerModels = new ArrayList<>();

        int cnt = 1;
        for (TowerModel tower : towers) {
            lineTowerModels.add(new LineTowerModel(0, lineUniqId, tower.getUniqId(), cnt));
            cnt++;
        }

        this.db.lineTowerDao().insertAll(lineTowerModels);
        this.db.towerDao().insertAll(towers);
        this.db.lineSectionDao().insertAll(sectionModels);

        updateLineBoundingBox(towers, lineUniqId);
    }

    private void buildLineDetailsModels(GeoLineTowers geoLineTowers, Map<Long, TowerModel> towersMap, List<LineSectionModel> sectionModels, long lineUniqId) {
        List<GeoLineTower> towers = new ArrayList<GeoLineTower>(geoLineTowers.getTowers().values());
        for (GeoLineTower tower : towers) {
            GeoLineTowerNode towerNode = tower.getNode();
            TowerModel towerModel = new TowerModel(
                    towerNode.getNodeId(),
                    towerNode.getNodeId(),
                    towerNode.getName(),
                    0,
                    0,
                    towerNode.getEle(),
                    towerNode.getLat(),
                    towerNode.getLon());

            towersMap.put(towerNode.getNodeId(), towerModel);

            if (tower.getProlet() != null) {
                GeoLineProlet prolet = tower.getProlet();
                LineSectionModel sectionModel = new LineSectionModel(0, lineUniqId, prolet.getTower1().getNodeId(), prolet.getTower2().getNodeId(), prolet.getName(), 0);
                sectionModels.add(sectionModel);
            }

            if (tower.getOtpaiki() != null) {
                List<GeoLineTowers> otpaikiList = new ArrayList<GeoLineTowers>(tower.getOtpaiki().values());

                for (GeoLineTowers otpaika : otpaikiList) {
                    buildLineDetailsModels(otpaika, towersMap, sectionModels, lineUniqId);
                }
            }
        }
    }

    private void updateLineBoundingBox(List<TowerModel> towers, long lineUniqId) {
        if (towers.isEmpty()) {
            return;
        }
        double latMin = towers.get(0).getLat();
        double latMax = towers.get(0).getLat();

        double lonMin = towers.get(0).getLon();
        double lonMax = towers.get(0).getLon();

        for (TowerModel tower : towers) {
            latMin = Math.min(latMin, tower.getLat());
            latMax = Math.max(latMax, tower.getLat());

            lonMin = Math.min(lonMin, tower.getLon());
            lonMax = Math.max(lonMax, tower.getLon());
        }
        this.db.lineDao().updateBoundingBox(latMax + 0.000150, lonMin - 0.000150, latMin - 0.000150, lonMax + 0.000150, lineUniqId); //Немного расширим прямоугольник
    }

    public void loadTowerDeffectTypes(List<TowerDeffectTypesJson> deffectTypes) {

        List<TowerDeffectTypesModel> deffectTypesModels = new ArrayList<>();
        for (TowerDeffectTypesJson deffectType : deffectTypes) {

            deffectTypesModels.add(new TowerDeffectTypesModel(
                    deffectType.getId(),
                    deffectType.getOrder(),
                    deffectType.getName(),
                    deffectType.getVoltage()
            ));
        }

        db.towerDeffectTypesDao().insertAll(deffectTypesModels);
    }

    public void loadSectionDeffectTypes(List<SectionDeffectTypesJson> deffectTypes) {

        List<SectionDeffectTypesModel> deffectTypesModels = new ArrayList<>();
        for (SectionDeffectTypesJson deffectType : deffectTypes) {

            deffectTypesModels.add(new SectionDeffectTypesModel(
                    deffectType.getId(),
                    deffectType.getOrder(),
                    deffectType.getName(),
                    deffectType.getVoltage()
            ));
        }

        db.sectionDeffectTypesDao().insertAll(deffectTypesModels);
    }

    public void loadTransformersDeffectTypes(List< StationDeffectTypesJson > deffectTypes) {

        List<TransformerDeffectTypesModel> deffectTypesModels = new ArrayList<>();
        for (StationDeffectTypesJson deffectType : deffectTypes) {

            deffectTypesModels.add(new TransformerDeffectTypesModel(
                    deffectType.getId(),
                    deffectType.getOrder(),
                    deffectType.getNumber(),
                    deffectType.getName(),
                    deffectType.getType(),
                    deffectType.getResult(),
                    deffectType.getSubResult(),
                    deffectType.getEquipmentType()
            ));
        }

        db.transformerDeffectTypesDao().insertAll(deffectTypesModels);
    }

    public void loadStationsDeffectTypes(List< StationDeffectTypesJson > deffectTypes) {

        List< StationEquipmentsDeffectType > deffectTypesModels = new ArrayList<>();
        for (StationDeffectTypesJson deffectType : deffectTypes) {

            deffectTypesModels.add(new StationEquipmentsDeffectType(
                    deffectType.getId(),
                    deffectType.getOrder(),
                    deffectType.getNumber(),
                    deffectType.getName(),
                    deffectType.getType(),
                    deffectType.getResult(),
                    deffectType.getSubResult(),
                    EquipmentType.getByName(deffectType.getEquipmentType()).getValue()
            ));
        }

        db.stationEquipmentsDeffectTypesDao().insertAll(deffectTypesModels);
    }
}
