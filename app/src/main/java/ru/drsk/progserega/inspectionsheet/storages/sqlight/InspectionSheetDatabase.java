package ru.drsk.progserega.inspectionsheet.storages.sqlight;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.converters.Converters;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.EquipmentPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionDeffectDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineTowerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LogDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SectionDeffectTypesDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.StationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.StationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.StationEquipmentModelsDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerDeffectDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerDeffectTypesDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDeffectTypesDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineTowerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SectionDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipment;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentModels;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

@Database(entities = {
        SP.class,
        Res.class,
        TransformerModel.class,
        TransformerSubstationModel.class,
        TransformerSubstationEuipmentModel.class,
        SubstationModel.class,
        SubstationEquipmentModel.class,
        InspectionModel.class,
        InspectionPhotoModel.class,
        EquipmentPhotoModel.class,
        LineModel.class,
        LineTowerModel.class,
        LineSectionModel.class,
        TowerModel.class,
        TowerDeffectModel.class,
        TowerInspectionModel.class,
        LineSectionDeffectModel.class,
        LineSectionInspectionModel.class,
        LineInspectionModel.class,
        TransformerDeffectTypesModel.class,
        TowerDeffectTypesModel.class,
        SectionDeffectTypesModel.class,
        StationModel.class,
        StationEquipment.class,
        StationEquipmentModels.class,
        LogModel.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class InspectionSheetDatabase extends RoomDatabase {

    private static InspectionSheetDatabase INSTANCE;

    public abstract SPDao spDao();

    public abstract ResDao resDao();

    public abstract SpWithResDao spWithResDao();

    public abstract TransformerSubstationDao transformerSubstationDao();

    public abstract TransformerDao transformerDao();

    public abstract TransformerSubstationEquipmentDao transfSubstationEquipmentDao();

    public abstract SubstationDao substationDao();

    public abstract SubstationEquipmentDao substationEquipmentDao();

    public abstract InspectionDao inspectionDao();

    public abstract InspectionPhotoDao inspectionPhotoDao();

    //public abstract InspectionItemDao inspectionItemDao();

    public abstract EquipmentPhotoDao equipmentPhotoDao();

    public abstract LineDao lineDao();

    public abstract LineTowerDao lineTowerDao();

    public abstract LineSectionDao lineSectionDao();

    public abstract TowerDao towerDao();

    public abstract TowerDeffectDao towerDeffectDao();

    public abstract TowerInspectionDao towerInspectionDao();

    public abstract LineSectionDeffectDao lineSectionDeffectDao();

    public abstract LineSectionInspectionDao lineSectionInspectionDao();

    public abstract LineInspectionDao lineInspectionDao();

    public abstract TowerDeffectTypesDao towerDeffectTypesDao();

    public abstract SectionDeffectTypesDao sectionDeffectTypesDao();

    public abstract TransformerDeffectTypesDao transformerDeffectTypesDao();

    public abstract StationDao stationDao();

    public abstract StationEquipmentDao stationEquipmentDao();

    public abstract StationEquipmentModelsDao stationEquipmentModelsDao();

    public abstract LogDao logDao();

//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//
//            //добавляем таблицу transformer_deffect_types
//            database.execSQL("CREATE TABLE \"transformer_deffect_types\" (\n" +
//                    "\t\"id\"\tINTEGER NOT NULL,\n" +
//                    "\t\"order\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
//                    "\t\"number\"\tTEXT,\n" +
//                    "\t\"name\"\tTEXT,\n" +
//                    "\t\"type\"\tINTEGER NOT NULL,\n" +
//                    "\t\"result\"\tTEXT,\n" +
//                    "\t\"sub_result\"\tTEXT,\n" +
//                    "\t\"equipment_type\"\tTEXT,\n" +
//                    "\tPRIMARY KEY(\"id\")\n" +
//                    ");");
//
//            //добавляем таблицу tower_deffect_types
//            database.execSQL("CREATE TABLE \"tower_deffect_types\" (\n" +
//                    "\t\"id\"\tINTEGER NOT NULL,\n" +
//                    "\t\"order\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
//                    "\t\"name\"\tTEXT,\n" +
//                    "\t\"voltage\"\tTEXT,\n" +
//                    "\tPRIMARY KEY(\"id\")\n" +
//                    ");");
//
//            //добавляем таблицу section_deffect_types
//            database.execSQL("CREATE TABLE \"section_deffect_types\" (\n" +
//                    "\t\"id\"\tINTEGER NOT NULL,\n" +
//                    "\t\"order\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
//                    "\t\"name\"\tTEXT,\n" +
//                    "\t\"voltage\"\tTEXT,\n" +
//                    "\tPRIMARY KEY(\"id\")\n" +
//                    ");");
//
//
//            //удаляем inspection_items
//            database.execSQL("DROP TABLE IF EXISTS inspection_items;");
//        }
//    };
//
//    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("CREATE TABLE \"log_storage\" (\n" +
//                    "                    \"date\"\tINTEGER,\n" +
//                    "                    \"level\"\tINTEGER NOT NULL,\n" +
//                    "                    \"tag\"\tTEXT,\n" +
//                    "                    \"message\"\tTEXT, " +
//                    "\tPRIMARY KEY(\"date\")\t)");
//        }
//    };

}