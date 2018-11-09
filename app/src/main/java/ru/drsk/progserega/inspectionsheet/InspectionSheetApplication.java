package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.util.Log;

import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationServiceStub;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.OrganizationStorageStub;

public class InspectionSheetApplication extends Application {


    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;

    //Сервис для получения списков СП и РЭС
    private OrganizationService organizationService;

    //Сервис для работы с GPS
    private ILocation locationService;

    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    public ILocation getLocationService() {
        return locationService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("App onCreate", "Страртуем приложение");

        locationService = new LocationServiceStub();

        //ILineStorage lineStorage = new LineStorageSqlight();
        ILineStorage lineStorage = new LineStorageStub(locationService);
        //linesService = new LinesService(lineStorage);

        IOrganizationStorage organizationStorage = new OrganizationStorageStub();
        this.organizationService = new OrganizationService(organizationStorage);

        this.equipmentService = new EquipmentService(lineStorage);

    }


}
