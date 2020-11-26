package ru.drsk.progserega.inspectionsheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.services.DBLog;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.services.LocationService;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.services.StationInspectionFactory;
import ru.drsk.progserega.inspectionsheet.services.StationInspectionService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IFileStorage;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.files.FileStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IRemoteStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.RemoteStorageRx;
import ru.drsk.progserega.inspectionsheet.storages.shared_preferences.SettingsStorageImpl;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LogStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.OrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.StationDeffectsTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.StationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.StationPhotoStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.SubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.StationDeffectsTypesStorageStub;

import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_1_2;

//import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_1_2;
//import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_2_3;

public class InspectionSheetApplication extends Application {

    private AppState state = new AppState();

    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;

    //Сервис для получения списков СП и РЭС
    private OrganizationService organizationService;

    //Сервис для работы с GPS
    private ILocation locationService;

    //Сервис для работы с sqlight
    private InspectionSheetDatabase db;

    private ITowerStorage towerStorage;

    private ICatalogStorage catalogStorage;

    private ILineStorage lineStorage;

    private IRemoteStorage remoteStorage;

    private ITransformerStorage transformerStorage;

    private IInspectionStorage inspectionStorage;

    private InspectionService inspectionService;

    private ILineSectionStorage lineSectionStorage;

    private ILineDeffectTypesStorage lineDeffectTypesStorage;

    private PhotoFullscreenManager photoFullscreenManager;

    private ILineInspectionStorage lineInspectionStorage;

    private ISettingsStorage settingsStorage;

    private IOrganizationStorage organizationStorage;

    private ITransformerDeffectTypesStorage transformerDeffectTypesStorage;

    private StationInspectionFactory stationInspectionFactory;

    private ILogStorage logStorage;

    private IStationEquipmentStorage stationEquipmentStorage;

    private IFileStorage fileStorage;

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

    public IOrganizationStorage getOrganizationStorage() {
        return organizationStorage;
    }

    public ICatalogStorage getCatalogStorage() {
        return catalogStorage;
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

    public PhotoFullscreenManager getPhotoFullscreenManager() {
        return photoFullscreenManager;
    }

    public void setPhotoFullscreenManager(PhotoFullscreenManager photoFullscreenManager) {
        this.photoFullscreenManager = photoFullscreenManager;
    }

    public ILineDeffectTypesStorage getLineDeffectTypesStorage() {
        return lineDeffectTypesStorage;
    }

    public ITowerStorage getTowerStorage() {
        return towerStorage;
    }

    public ILineInspectionStorage getLineInspectionStorage() {
        return lineInspectionStorage;
    }

    public ILineSectionStorage getLineSectionStorage() {
        return lineSectionStorage;
    }

    public ILineStorage getLineStorage() {
        return lineStorage;
    }

    public ISettingsStorage getSettingsStorage() {
        return settingsStorage;
    }

    public ITransformerDeffectTypesStorage getTransformerDeffectTypesStorage() {
        return transformerDeffectTypesStorage;
    }

    public ILogStorage getLogStorage() {
        return logStorage;
    }

    public StationInspectionFactory getStationInspectionFactory() {
        return stationInspectionFactory;
    }

    public AppState getState() {
        return state;
    }

    public void setState(AppState state) {
        this.state = state;
    }

    public IStationEquipmentStorage getStationEquipmentStorage() {
        return stationEquipmentStorage;
    }

    public IFileStorage getFileStorage() {
        return fileStorage;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        settingsStorage = new SettingsStorageImpl(getApplicationContext());

        db = Room.databaseBuilder(
                getApplicationContext(),
                InspectionSheetDatabase.class,
                "inspection_sheet_db")
                .allowMainThreadQueries() //TODO сделать везде асинхронно и убрать это
                .addMigrations(MIGRATION_1_2)
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();


        // locationService = new LocationServiceStub();
        LocationService location = new LocationService(getApplicationContext());
        locationService = (ILocation) location;

        lineStorage = new LineStorage(db, locationService);
        // LineStorageStub lineStorage = new LineStorageStub(locationService);
        //lineStorage.setLines(LineStorageStub.initLinesWithTowersStub());
        //linesService = new LinesService(lineStorage);

        //ILineStorage lineStorage = new LineStorageJSON();


        catalogStorage = new CatalogStorageStub();

        organizationStorage = new OrganizationStorage(db);
        organizationService = new OrganizationService(organizationStorage);


        towerStorage = new TowerStorage(db, catalogStorage);

        //ITowerStorage towerStorage = new TowerStorageStub();

        // towersService = new TowersService(towerStorage, lineStorage);

        transformerStorage = new TransformerStorage(db, getApplicationContext());
        ISubstationStorage substationStorage = new SubstationStorage(db, locationService);
        //
        ITransformerSubstationStorage transformerSubstationStorage = new TransformerSubstationStorage(db, locationService);

        equipmentService = new EquipmentService(lineStorage, substationStorage, transformerSubstationStorage);


        DBDataImporter dbDataImporter = new DBDataImporter(db);
        // remoteStorage = new RemoteSorage(dbDataImporter, getApplicationContext());
        remoteStorage = new RemoteStorageRx(dbDataImporter, getApplicationContext(), settingsStorage);

        //substationInspectionsCache = new ArrayList<>();

        inspectionStorage = new InspectionStorage(db, getApplicationContext());

        Fresco.initialize(getApplicationContext());

        photoFullscreenManager = new PhotoFullscreenManager(db);

        //lineDeffectTypesStorage = new LineDeffectTypesStorageJson(getApplicationContext());
        lineDeffectTypesStorage = new LineDeffectTypesStorage(db, getApplicationContext());

        lineInspectionStorage = new LineInspectionStorage(db, getApplicationContext(), lineDeffectTypesStorage, catalogStorage, lineStorage);

        lineSectionStorage = new LineSectionStorage(db, getApplicationContext(), catalogStorage);

        transformerDeffectTypesStorage = new TransformerDeffectTypesStorage(db);

        inspectionService = new InspectionService(
                db,
                getApplicationContext(),
                transformerStorage,
                inspectionStorage,
                towerStorage,
                lineInspectionStorage,
                lineSectionStorage,
                transformerDeffectTypesStorage);

        logStorage = new LogStorage(db);
        DBLog.setLogStorage(logStorage);
//        try{
//            throw new IOException("Test exception");
//        }
//        catch (Exception ex){
//            DBLog.e("APPLICATION", ex);
//        }
//        DBLog.i("APPLICATION", "TEST LOG MSG 3");

        stationEquipmentStorage =  new StationEquipmentStorage(db);
        StationInspectionService stationInspectionService = new StationInspectionService(
                new StationDeffectsTypesStorage(db, getApplicationContext()),
                inspectionStorage,
                stationEquipmentStorage,
                new StationPhotoStorage(db, getApplicationContext())
        );

        stationInspectionFactory = new StationInspectionFactory(
                equipmentService,
                stationInspectionService
        );

        fileStorage = new FileStorage( getApplicationContext());

        this.LockOrientation();
    }

    private void LockOrientation(){
        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


}
