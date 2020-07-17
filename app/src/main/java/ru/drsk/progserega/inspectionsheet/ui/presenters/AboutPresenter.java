package ru.drsk.progserega.inspectionsheet.ui.presenters;

import android.app.DownloadManager;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.AppVersionJson;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.AboutContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;

import static android.content.Context.DOWNLOAD_SERVICE;

public class AboutPresenter implements AboutContract.Presenter, IProgressListener {

    private AboutContract.View view;
    private InspectionSheetApplication application;

    private int currentVersionCode = 0;

    private AppVersionJson remoteVersion;

    private Boolean selectServerTask = false;


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
        selectServerTask = true;

        application.getRemoteStorage().setProgressListener(this);
        application.getRemoteStorage().selectActiveServer();
    }

    @Override
    public void progressUpdate(String progress) {
        Log.d("CHECK UPDATE", "progress UPDATE!!!!!!!!!");
    }

    @Override
    public void progressComplete() {
        Log.d("CHECK UPDATE", "progress complete!!!!!!!!!");
        view.HideProgressBar();
        if (selectServerTask) {
            selectServerTask = false;
            requestVersionInfo();
        }

    }

    private void requestVersionInfo() {
        view.ShowProgressBar();
        application.getRemoteStorage().getAppVersion(new Observer< AppVersionJson >() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(AppVersionJson remoteVersionInfo) {
                view.HideProgressBar();
                remoteVersion = remoteVersionInfo;
                compareWithCurrentVersion(remoteVersionInfo);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage("Ошибка получения версии", e.getMessage());
                view.HideProgressBar();
            }

            @Override
            public void onComplete() {
                view.HideProgressBar();
            }
        });
    }

    @Override
    public void onDownloadBtnPressed() {

        String serverUrl = application.getSettingsStorage().loadSettings().getServerUrl();
        view.startDownload(serverUrl + "/mobile/" + remoteVersion.getFileName(), remoteVersion.getFileName());

    }

    private void compareWithCurrentVersion(AppVersionJson remoteVersionInfo) {
        if (remoteVersionInfo.getCode() > currentVersionCode) {
            Log.d("CHECK UPDATE", "update need");
            view.showNeedUpdateDialog("Проверка наличия обновления", String.format("Доступна новая версия: %s<br><br>%sЗагрузить?", remoteVersionInfo.getVersionName(), remoteVersionInfo.getDescription()));
        } else {
            Log.d("CHECK UPDATE", "update don`t need");
            view.showMessage("Проверка наличия обновления", "Обновление не требуется, вы используете последнюю версию программы");
        }
    }

    @Override
    public void progressError(Exception ex) {
        Log.d("CHECK UPDATE", "progress  error!!!!!!!!!");
        view.HideProgressBar();
        view.showMessage("Ошибка загрузки данных", ex.getMessage());
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }


}
