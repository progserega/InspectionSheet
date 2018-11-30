package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.drsk.progserega.inspectionsheet.entities.inspections.EquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.SubstationInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationService;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiSTE;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDBHelper;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.OrganizationStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.SubstationStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.TowerStorageStub;

public class InspectionSheetApplication extends Application {


    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;

    //Сервис для получения списков СП и РЭС
    private OrganizationService organizationService;

    //Сервис для работы с GPS
    private ILocation locationService;

    private TowersService towersService;

    private ICatalogStorage catalogStorage;

    private EquipmentInspection equipmentInspection;

    private SubstationInspection substationInspection;

    private Deffect deffect;

    private SQLiteOpenHelper dbHelper;

    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    public ILocation getLocationService() {
        return locationService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public TowersService getTowersService() {
        return towersService;
    }

    public ICatalogStorage getCatalogStorage() {
        return catalogStorage;
    }

    public EquipmentInspection getEquipmentInspection() {
        return equipmentInspection;
    }

    public void setEquipmentInspection(EquipmentInspection equipmentInspection) {
        this.equipmentInspection = equipmentInspection;
    }

    public SubstationInspection getSubstationInspection() {
        return substationInspection;
    }

    public void setSubstationInspection(SubstationInspection substationInspection) {
        this.substationInspection = substationInspection;
    }

    public Deffect getDeffect() {
        return deffect;
    }

    public void setDeffect(Deffect deffect) {
        this.deffect = deffect;
    }

    private static IApiSTE apiSTE;
    private Retrofit retrofit;

    public SQLiteOpenHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("App onCreate", "Страртуем приложение");

       // locationService = new LocationServiceStub();
        LocationService  location = new LocationService(getApplicationContext());


        locationService = (ILocation) location;

        //ILineStorage lineStorage = new LineStorageSqlight();
        LineStorageStub lineStorage = new LineStorageStub(locationService);
        lineStorage.setLines(LineStorageStub.initLinesWithTowersStub());
        //linesService = new LinesService(lineStorage);

        //ILineStorage lineStorage = new LineStorageJSON();

        ISubstationStorage substationStorage = new SubstationStorageStub();

        catalogStorage = new CatalogStorageStub();

        IOrganizationStorage organizationStorage = new OrganizationStorageStub();
        this.organizationService = new OrganizationService(organizationStorage);

        this.equipmentService = new EquipmentService(lineStorage, substationStorage);

        ITowerStorage towerStorage = new TowerStorageStub();

        towersService = new TowersService(towerStorage, lineStorage);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60*2, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api-ste.rs.int") //Базовая часть адреса
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        apiSTE = retrofit.create(IApiSTE.class); //Создаем объект, при помощи которого будем выполнять запросы

        dbHelper = new InspectionSheetDBHelper(getApplicationContext());

    }


    public IApiSTE getApiSTE() {
        return apiSTE;
    }
}
