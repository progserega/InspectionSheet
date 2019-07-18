package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class InspectLineContract {
    public interface View{

        void setLineName(String lineName);

        void setSPName(String spName);

        void setResName(String resName);

        void setInspectionTypesSpinnerData(List<InspectionType> types, int selected);

        void setStartExploitationYear(int year);

        void showNoInspectionTypeAlert();

        void gotoInpectLineTowerActivity(long towerUniqId);
    }

    public interface Presenter{

        void onViewCreate();

        void onInspectionTypeSelect(int position);

        void onDestroy();

        void onStartExploitationYearSelect(int year);

        void onNextButtonClick();
    }
}
