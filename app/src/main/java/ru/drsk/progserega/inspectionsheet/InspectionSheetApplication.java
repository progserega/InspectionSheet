package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.EquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.ISubstationInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.services.LocationService;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IRemoteStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.RemoteSorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.OrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.SubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.TowerStorageStub;

public class InspectionSheetApplication extends Application {


    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;

    //Сервис для получения списков СП и РЭС
    private OrganizationService organizationService;

    //Сервис для работы с GPS
    private ILocation locationService;

    //Сервис для работы с sqlight
    private InspectionSheetDatabase db;

    private TowersService towersService;

    private ICatalogStorage catalogStorage;

    private EquipmentInspection equipmentInspection;

    private ISubstationInspection currentSubstationInspection;

    private List<ISubstationInspection> substationInspections;

    private InspectionItemResult currentDeffect;

    private IRemoteStorage remoteStorage;

    ITransformerStorage transformerStorage;

    IInspectionStorage inspectionStorage;

    InspectionService inspectionService;

    public ITransformerStorage getTransformerStorage() {
        return transformerStorage;
    }

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

    public ISubstationInspection getCurrentSubstationInspection() {
        return currentSubstationInspection;
    }

    public void setCurrentSubstationInspection(ISubstationInspection currentSubstationInspection) {
        this.currentSubstationInspection = currentSubstationInspection;
    }

    public List<ISubstationInspection> getSubstationInspections() {
        return substationInspections;
    }

    public InspectionItemResult getCurrentDeffect() {
        return currentDeffect;
    }

    public void setCurrentDeffect(InspectionItemResult currentDeffect) {
        this.currentDeffect = currentDeffect;
    }

    public InspectionSheetDatabase getDb() {
        return db;
    }

    public IRemoteStorage getRemoteStorage() {
        return remoteStorage;
    }

    public IInspectionStorage getInspectionStorage() {
        return inspectionStorage;
    }

    public InspectionService getInspectionService() {
        return inspectionService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(
                getApplicationContext(),
                InspectionSheetDatabase.class,
                "inspection_sheet_db")
                .allowMainThreadQueries() //TODO сделать везде асинхронно и убрать это
                .build();


        // locationService = new LocationServiceStub();
        LocationService location = new LocationService(getApplicationContext());
        locationService = (ILocation) location;

        //ILineStorage lineStorage = new LineStorage();
        LineStorageStub lineStorage = new LineStorageStub(locationService);
        lineStorage.setLines(LineStorageStub.initLinesWithTowersStub());
        //linesService = new LinesService(lineStorage);

        //ILineStorage lineStorage = new LineStorageJSON();


        catalogStorage = new CatalogStorageStub();

        IOrganizationStorage organizationStorage = new OrganizationStorage(db);
        organizationService = new OrganizationService(organizationStorage);


        ITowerStorage towerStorage = new TowerStorageStub();

        towersService = new TowersService(towerStorage, lineStorage);

        transformerStorage = new TransformerStorage(db);
        ISubstationStorage substationStorage = new SubstationStorage(db, locationService);
        //
        ITransformerSubstationStorage transformerSubstationStorage = new TransformerSubstationStorage(db, locationService);

        equipmentService = new EquipmentService(lineStorage, substationStorage, transformerSubstationStorage);


        DBDataImporter dbDataImporter = new DBDataImporter(db);
        remoteStorage = new RemoteSorage(dbDataImporter, getApplicationContext());

        substationInspections = new ArrayList<>();

        inspectionStorage = new InspectionStorage(db, getApplicationContext());

        inspectionService = new InspectionService(db, transformerStorage, inspectionStorage, getApplicationContext());

    }

}
