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
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineTowerDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IRemoteStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.RemoteStorageRx;
import ru.drsk.progserega.inspectionsheet.storages.json.LineTowerDeffectTypesStorageJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LineStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.OrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.SubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.CatalogStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.TowerStorageStub;

import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_1_2;
import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_2_3;
import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_3_4;

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

    private LineInspection lineInspection;

    private ISubstationInspection currentSubstationInspection;

    private List<ISubstationInspection> substationInspections;

    private InspectionItemResult currentDeffect;

    private IRemoteStorage remoteStorage;

    ITransformerStorage transformerStorage;

    IInspectionStorage inspectionStorage;

    InspectionService inspectionService;

    InspectionItem currentInspectionItem;
    List<InspectionItem> inspectionItemsGroup;

  //  List<InspectionPhoto> photosForFullscreen;

    private ILineTowerDeffectTypesStorage lineTowerDeffectTypesStorage;

    private PhotoFullscreenManager photoFullscreenManager;

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

//    public TowersService getTowersService() {
//        return towersService;
//    }

    public ICatalogStorage getCatalogStorage() {
        return catalogStorage;
    }

    public LineInspection getLineInspection() {
        return lineInspection;
    }

    public void setLineInspection(LineInspection lineInspection) {
        this.lineInspection = lineInspection;
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

    public InspectionItem getCurrentInspectionItem() {
        return currentInspectionItem;
    }

    public void setCurrentInspectionItem(InspectionItem currentInspectionItem) {
        this.currentInspectionItem = currentInspectionItem;
    }

    public List<InspectionItem> getInspectionItemsGroup() {
        return inspectionItemsGroup;
    }

    public void setInspectionItemsGroup(List<InspectionItem> inspectionItemsGroup) {
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

    public ILineTowerDeffectTypesStorage getLineTowerDeffectTypesStorage() {
        return lineTowerDeffectTypesStorage;
    }

    public ITowerStorage getTowerStorage() {
        return towerStorage;
    }

    @Override
    public void onCreate() {
        super.onCreate();

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

        ILineStorage lineStorage = new LineStorage(db, locationService);
       // LineStorageStub lineStorage = new LineStorageStub(locationService);
        //lineStorage.setLines(LineStorageStub.initLinesWithTowersStub());
        //linesService = new LinesService(lineStorage);

        //ILineStorage lineStorage = new LineStorageJSON();


        catalogStorage = new CatalogStorageStub();

        IOrganizationStorage organizationStorage = new OrganizationStorage(db);
        organizationService = new OrganizationService(organizationStorage);


        towerStorage = new TowerStorage(db);

        //ITowerStorage towerStorage = new TowerStorageStub();

       // towersService = new TowersService(towerStorage, lineStorage);

        transformerStorage = new TransformerStorage(db, getApplicationContext());
        ISubstationStorage substationStorage = new SubstationStorage(db, locationService);
        //
        ITransformerSubstationStorage transformerSubstationStorage = new TransformerSubstationStorage(db, locationService);

        equipmentService = new EquipmentService(lineStorage, substationStorage, transformerSubstationStorage);


        DBDataImporter dbDataImporter = new DBDataImporter(db);
       // remoteStorage = new RemoteSorage(dbDataImporter, getApplicationContext());
        remoteStorage = new RemoteStorageRx(dbDataImporter, getApplicationContext());

        substationInspections = new ArrayList<>();

        inspectionStorage = new InspectionStorage(db, getApplicationContext());

        inspectionService = new InspectionService(db, transformerStorage, inspectionStorage, getApplicationContext());


        Fresco.initialize(getApplicationContext());

        photoFullscreenManager = new PhotoFullscreenManager(db);

        lineTowerDeffectTypesStorage = new LineTowerDeffectTypesStorageJson(getApplicationContext());
    }

}
