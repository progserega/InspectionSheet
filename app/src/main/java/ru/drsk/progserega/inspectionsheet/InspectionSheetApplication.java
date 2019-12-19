package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.ISubstationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.services.LocationService;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IRemoteStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.RemoteStorageRx;
import ru.drsk.progserega.inspectionsheet.storages.json.LineDeffectTypesStorageJson;
import ru.drsk.progserega.inspectionsheet.storages.shared_preferences.SettingsStorageImpl;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.OrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.SubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;

public class InspectionSheetApplication extends Application {


    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;

    //Сервис для получения списков СП и РЭС
    private OrganizationService organizationService;

    //Сервис для работы с GPS
    private ILocation locationService;

    //Сервис для работы с sqlight
    private InspectionSheetDatabase db;

    //private TowersService towersService;

    private ITowerStorage towerStorage;

    private ICatalogStorage catalogStorage;

    private ILineStorage lineStorage;

    private LineInspection currentLineInspection;

    private ISubstationInspection currentSubstationInspection;

    private List< ISubstationInspection > substationInspections;

    private InspectionItemResult currentDeffect;

    private IRemoteStorage remoteStorage;

    ITransformerStorage transformerStorage;

    IInspectionStorage inspectionStorage;

    InspectionService inspectionService;

    InspectionItem currentInspectionItem;
    List< InspectionItem > inspectionItemsGroup;

    ILineSectionStorage lineSectionStorage;

    //  List<InspectionPhoto> photosForFullscreen;

    private ILineDeffectTypesStorage lineDeffectTypesStorage;

    private PhotoFullscreenManager photoFullscreenManager;

    private ILineInspectionStorage lineInspectionStorage;


    private ISettingsStorage settingsStorage;

    private IOrganizationStorage organizationStorage;


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

    //    public TowersService getTowersService() {
//        return towersService;
//    }

    public ICatalogStorage getCatalogStorage() {
        return catalogStorage;
    }

    public LineInspection getCurrentLineInspection() {
        return currentLineInspection;
    }

    public void setCurrentLineInspection(LineInspection currentLineInspection) {
        this.currentLineInspection = currentLineInspection;
    }

    public ISubstationInspection getCurrentSubstationInspection() {
        return currentSubstationInspection;
    }

    public void setCurrentSubstationInspection(ISubstationInspection currentSubstationInspection) {
        this.currentSubstationInspection = currentSubstationInspection;
    }

    public List< ISubstationInspection > getSubstationInspections() {
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

    public InspectionItem getCurrentInspectionItem() {
        return currentInspectionItem;
    }

    public void setCurrentInspectionItem(InspectionItem currentInspectionItem) {
        this.currentInspectionItem = currentInspectionItem;
    }

    public List< InspectionItem > getInspectionItemsGroup() {
        return inspectionItemsGroup;
    }

    public void setInspectionItemsGroup(List< InspectionItem > inspectionItemsGroup) {
        this.inspectionItemsGroup = inspectionItemsGroup;
    }

//    public List<InspectionPhoto> getPhotosForFullscreen() {
//        return photosForFullscreen;
//    }
//
//    public void setPhotosForFullscreen(List<InspectionPhoto> photosForFullscreen) {
//        this.photosForFullscreen = photosForFullscreen;
//    }

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

    @Override
    public void onCreate() {
        super.onCreate();
        settingsStorage = new SettingsStorageImpl(getApplicationContext());

        db = Room.databaseBuilder(
                getApplicationContext(),
                InspectionSheetDatabase.class,
                "inspection_sheet_db")
                .allowMainThreadQueries() //TODO сделать везде асинхронно и убрать это
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
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

        substationInspections = new ArrayList<>();

        inspectionStorage = new InspectionStorage(db, getApplicationContext());

        Fresco.initialize(getApplicationContext());

        photoFullscreenManager = new PhotoFullscreenManager(db);

        lineDeffectTypesStorage = new LineDeffectTypesStorageJson(getApplicationContext());

        lineInspectionStorage = new LineInspectionStorage(db, getApplicationContext(), lineDeffectTypesStorage, catalogStorage, lineStorage);

        lineSectionStorage = new LineSectionStorage(db, getApplicationContext(), catalogStorage);

        inspectionService = new InspectionService(
                db,
                getApplicationContext(),
                transformerStorage,
                inspectionStorage,
                towerStorage,
                lineInspectionStorage,
                lineSectionStorage);


    }

}
