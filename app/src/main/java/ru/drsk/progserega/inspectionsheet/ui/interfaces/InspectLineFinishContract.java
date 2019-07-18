package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class InspectLineFinishContract {
    public interface View{

        void setInspectorName(String name);

        String getInspectorName();

        void setDateInspection(Date dateInspection);

        void gotoSearchObjectActivity();

    }

    public interface Presenter{

        void onViewCreate();

        void onInspectionDateSelected(Date date);

        void onFinishButtonPressed();

        void onDestroy();


    }
}
