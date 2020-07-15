package ru.drsk.progserega.inspectionsheet.ui.presenters;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.AboutContract;

public class AboutPresenter implements AboutContract.Presenter {

    private AboutContract.View view;
    private InspectionSheetApplication application;

    private int currentVersionCode = 0;

    public AboutPresenter(AboutContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }


    @Override
    public void onViewCreated() {

        PackageInfo pInfo = view.getPackageInfo();
        if (pInfo != null) {
            //get the app version Name for display
            String version = pInfo.versionName;
            //get the app version Code for checking
            currentVersionCode = pInfo.versionCode;
            view.setVersionName(version);
        } else {
            view.setVersionName("ошибка!");
        }
    }

    @Override
    public void onCheckUpdatePressed() {
        Log.d("CHECK UPDATE", "CHECK UPDATE!!!!!!!!!");
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }


}
