package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class SettingsContract {
    public interface View{

        String getFio();
        String getUserPosition();
        String getServerUrl();
        String getServerAltUrl();

        void setFio(String fio);
        void setUserPosition(String position);
        void setSpResName(String spResName);
        void setServerUrl(String serverUrl);
        void setServerAltUrl(String serverAltUrl);


        void showSelectOrganizationDialog(long enterpriseId, long areaId);

        void showError(String title, String message);

        void ShowProgressBar();

        void HideProgressBar();

        void finishView();

    }

    public interface Presenter{

        void onViewCreated();

        void SaveBtnPressed();

        void SelectResBtnPressed();

        void OrganizationSelected(long spId, long resId);

        void RefreshOrganizationsBtnPressed();

        void onDestroy();
    }
}
