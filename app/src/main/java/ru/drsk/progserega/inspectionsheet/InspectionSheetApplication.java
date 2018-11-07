package ru.drsk.progserega.inspectionsheet;

import android.app.Application;
import android.util.Log;

import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationServiceStub;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.LineStorageStub;

public class InspectionSheetApplication extends Application {


    //Сервис для получения списков оборудования
    private EquipmentService equipmentService;


    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("App onCreate", "Страртуем приложение");

        ILocation location = new LocationServiceStub();

        //ILineStorage lineStorage = new LineStorageSqlight();
        ILineStorage lineStorage = new LineStorageStub(location);
        //linesService = new LinesService(lineStorage);

        this.equipmentService = new EquipmentService(lineStorage, location);

    }


}
