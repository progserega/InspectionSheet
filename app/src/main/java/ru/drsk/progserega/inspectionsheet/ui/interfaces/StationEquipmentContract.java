package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public class StationEquipmentContract {
    public interface View {

        void setEquipments(List<Equipment> equipments);
    }

    public interface Presenter {

        void onViewCreated();


        void onDestroy();


    }
}
