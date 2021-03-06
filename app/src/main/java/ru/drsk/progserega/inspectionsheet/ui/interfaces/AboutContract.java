package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import android.content.pm.PackageInfo;

public class AboutContract {
    public interface View {

        PackageInfo getPackageInfo();

        void setVersionName(String versionName);

        //        String getFio();
//        String getUserPosition();
//        String getServerUrl();
//        String getServerAltUrl();
//
//        void setFio(String fio);
//        void setUserPosition(String position);
//        void setSpResName(String spResName);
//        void setServerUrl(String serverUrl);
//        void setServerAltUrl(String serverAltUrl);
//
//
//        void showSelectOrganizationDialog(long enterpriseId, long areaId);
//
        void showMessage(String title, String message);

        void showNeedUpdateDialog(String title, String message);

        void startDownload(String fileUrl, String fileName);

        void ShowProgressBar();

        void HideProgressBar();
//
//        void finishView();

    }

    public interface Presenter {

        void onViewCreated();

        void onCheckUpdatePressed();

        void onDownloadBtnPressed();
//        void SaveBtnPressed();
//
//        void SelectResBtnPressed();
//
//        void OrganizationSelected(long spId, long resId);
//
//        void RefreshOrganizationsBtnPressed();

        void onDestroy();
    }
}
