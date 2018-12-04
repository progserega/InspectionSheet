package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;

public class OrganizationStorage implements IOrganizationStorage {

    private SQLiteOpenHelper dbHelper;

    public OrganizationStorage(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public List<NetworkEnterprise> getAllEnterprices() {


        return null;
    }
}
