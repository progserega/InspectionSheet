package ru.drsk.progserega.inspectionsheet.storages.sqlight;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.converters.Converters;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.EquipmentPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionItemDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionDeffectDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineSectionInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.LineTowerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerDeffectDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TowerInspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineTowerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;
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
        InspectionItemModel.class,
        EquipmentPhotoModel.class,
        LineModel.class,
        LineTowerModel.class,
        LineSectionModel.class,
        TowerModel.class,
        TowerDeffectModel.class,
        TowerInspectionModel.class,
        LineSectionDeffectModel.class,
        LineSectionInspectionModel.class,
        LineInspectionModel.class
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

    public abstract InspectionItemDao inspectionItemDao();

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


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.

            //добавляем таблицу inspection_items
            database.execSQL("CREATE TABLE \"inspection_items\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"parent_id\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
                    "\t\"number\"\tTEXT,\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"type\"\tINTEGER NOT NULL,\n" +
                    "\t\"result\"\tTEXT,\n" +
                    "\t\"sub_result\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");");


            //добавляем поле год производства трансформатора для оборудования ТП
            database.execSQL("ALTER TABLE tp_transformers ADD COLUMN manufacture_year INTEGER NOT NULL DEFAULT 0;");

            //добавляем поле год производства трансформатора для оборудования Подстанций
            database.execSQL("ALTER TABLE substation_equipments ADD COLUMN manufacture_year INTEGER NOT NULL DEFAULT 0;");

            //Создаем таблицу equipment_photos для хранения фотографий оборудования подстанций и ТП
            database.execSQL("CREATE TABLE `equipment_photos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `equipment_id` INTEGER NOT NULL, `substation_type` INTEGER NOT NULL, `photo_path` TEXT)");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            //добавляем поле дата осмотра трансформатора
            database.execSQL("ALTER TABLE substation_equipments ADD COLUMN inspection_date INTEGER");

            //добавляем поле дата осмотра трансформатора
            database.execSQL("ALTER TABLE tp_transformers ADD COLUMN inspection_date INTEGER");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE \"lines\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"uniq_id\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"voltage\"\tINTEGER  NOT NULL DEFAULT 0,\n" +
                    "\t\"start_exploitation_year\"\tINTEGER NOT NULL,\n" +
                    "\t\"bbox_top_lat\"\tREAL  NOT NULL DEFAULT 0,\n" +
                    "\t\"bbox_top_lon\"\tREAL  NOT NULL DEFAULT 0,\n" +
                    "\t\"bbox_bottom_lat\"\tREAL  NOT NULL DEFAULT 0,\n" +
                    "\t\"bbox_botttom_lon\"\tREAL  NOT NULL DEFAULT 0,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");");



        }
    };
}