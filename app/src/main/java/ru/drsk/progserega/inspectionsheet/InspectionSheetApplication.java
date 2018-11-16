package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.util.Log;

import ru.drsk.progserega.inspectionsheet.entities.Inspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationService;
import ru.drsk.progserega.inspectionsheet.services.LocationServiceStub;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.OrganizationStorageStub;
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

    private Inspection inspection;

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

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
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

        catalogStorage = new CatalogStorageStub();

        IOrganizationStorage organizationStorage = new OrganizationStorageStub();
        this.organizationService = new OrganizationService(organizationStorage);

        this.equipmentService = new EquipmentService(lineStorage);

        ITowerStorage towerStorage = new TowerStorageStub();

        towersService = new TowersService(towerStorage, lineStorage);
    }




}
