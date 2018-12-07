package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SpWithRes;

public class OrganizationStorage implements IOrganizationStorage {

    private InspectionSheetDatabase db;

    public OrganizationStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public List<NetworkEnterprise> getAllEnterprices() {

        SpWithResDao spWithResDao = db.spWithResDao();
        List<SpWithRes> organizations = spWithResDao.loadEnterprises();

        List<NetworkEnterprise> enterprises = new ArrayList<>();
        for(SpWithRes sp: organizations){
            NetworkEnterprise enterprise = new NetworkEnterprise(sp.getSp().getId(), sp.getSp().getName() );
            List<Res> reses = sp.getNetworkAreas();

            List<ElectricNetworkArea> areaList = new ArrayList<>();
            for(Res res: reses){
                areaList.add(new ElectricNetworkArea(res.getId(), res.getName(), enterprise));
            }

            enterprise.setENAreas(areaList);
            enterprises.add(enterprise);
        }

        return enterprises;
    }
}
