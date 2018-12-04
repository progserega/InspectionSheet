package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InspectionSheetDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "inspection_sheet"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

    public InspectionSheetDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {

            db.execSQL("CREATE TABLE \"SP\" (" +
                    " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,  " +
                    "\"name\" TEXT, " +
                    "\"name_full\" TEXT )");

            db.execSQL("CREATE TABLE \"RES\" ( " +
                    "\"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                    "\"sp_id\" INTEGER, " +
                    " \"name\" TEXT," +
                    " \"name_full\" TEXT )");


            db.execSQL("CREATE TABLE \"TransformerSubstation\" ( " +
                    " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    " \"uniq_id\" INTEGER, " +
                    " \"power_center_name\" TEXT, " +
                    " \"disp_name\" TEXT, " +
                    " \"sp_id\" INTEGER, " +
                    " \"res_id\" INTEGER, " +
                    " \"lat\" REAL, " +
                    " \"lon\" REAL " +
                    ")");

            db.execSQL("CREATE TABLE \"Transformer\" (" +
                    " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                    " \"tr_type\" TEXT," +
                    " \"substation_uniq_id\" INTEGER," +
                    " \"descr\" TEXT" +
                    ")");
        }
        if (oldVersion < 2) {
            //Код добавления нового столбца
        }
    }

}
