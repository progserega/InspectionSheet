package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SettingsContract;

public class SettingsPresenter implements SettingsContract.Presenter, IProgressListener {

    private SettingsContract.View view;
    private InspectionSheetApplication application;
    private ISettingsStorage settingsStorage;

    private IOrganizationStorage organizationStorage;

    private long spId = 0;
    private long resId = 0;

    private List<String> serverUrls;

    private Boolean selectServerTask = false;
    public SettingsPresenter(SettingsContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
        settingsStorage = application.getSettingsStorage();
        organizationStorage = application.getOrganizationStorage();
        serverUrls = new ArrayList<>();
    }

    @Override
    public void onViewCreated() {

        Settings settings = settingsStorage.loadSettings();
        view.setFio(settings.getFio());
        view.setUserPosition(settings.getPosition());
        this.resId = settings.getResId();

        view.setSpResName("Не выбран (нажмите для выбора)");
        if (resId != 0) {
            ElectricNetworkArea res = organizationStorage.getResById(resId);
            if (res != null) {
                view.setSpResName(res.getNetworkEnterprise().getName() + " / " + res.getName());
            }
        }

        view.setServerUrl(settings.getServerUrl());
        serverUrls.add(settings.getServerUrl());
        view.setServerAltUrl(settings.getServerAltUrl());
        serverUrls.add(settings.getServerAltUrl());

        selectServerTask = false;
    }

    @Override
    public void SaveBtnPressed() {
        String fio = view.getFio();
        String position = view.getUserPosition();
        String serverUrl = view.getServerUrl();
        String serverAltUrl = view.getServerAltUrl();

        settingsStorage.saveSettings(new Settings(fio, position, (int)spId, (int) resId, serverUrl, serverAltUrl));

        serverUrls.clear();
        serverUrls.add(serverUrl);
        serverUrls.add(serverAltUrl);
        application.getRemoteStorage().setServerUrls(serverUrls);
       // view.finishView();
    }

    @Override
    public void SelectResBtnPressed() {
        List<NetworkEnterprise> allEnt = organizationStorage.getAllEnterprices();
        if (allEnt == null || allEnt.size() == 0) {
            //if(true){
            view.showError("Список СП/РЭС пуст", "Нажмите кнопку обновить!");
        } else {
            view.showSelectOrganizationDialog(0, 0);
        }
    }

    @Override
    public void OrganizationSelected(long spId, long resId) {
        ElectricNetworkArea res = organizationStorage.getResById(resId);
        if (res == null) {
            view.showError("Ошибка!", "Не выбран РЭС");
            return;
        }
        view.setSpResName(res.getNetworkEnterprise().getName() + " / " + res.getName());
        this.resId = res.getId();
        this.spId = spId;
    }

    @Override
    public void RefreshOrganizationsBtnPressed() {
        view.ShowProgressBar();
        organizationStorage.ClearOrganizations();

        serverUrls.clear();
        serverUrls.add(view.getServerUrl());
        serverUrls.add(view.getServerAltUrl());

        application.getRemoteStorage().setServerUrls(serverUrls);
        application.getRemoteStorage().setProgressListener(this);
        //application.getRemoteStorage().loadOrganization();
        selectServerTask = true;
        application.getRemoteStorage().selectActiveServer();
    }

    @Override
    public void progressUpdate(String progress) {

    }

    @Override
    public void progressComplete() {
        //  view.showSelectOrganizationDialog(0, 0);

        if(selectServerTask){
            selectServerTask = false;
            application.getRemoteStorage().loadOrganization();
        }
        else {
            view.HideProgressBar();
        }
    }

    @Override
    public void progressError(Exception ex) {
        view.HideProgressBar();
        view.showError("Ошибка загрузки данных", ex.getMessage());
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
