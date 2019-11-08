package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.OrganizationStorage;
import ru.drsk.progserega.inspectionsheet.ui.activities.SelectOrganizationDialogFragment;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SettingsContract;

public class SettingsPresenter implements SettingsContract.Presenter, IProgressListener {

    private SettingsContract.View view;
    private InspectionSheetApplication application;
    private ISettingsStorage settingsStorage;

    private IOrganizationStorage organizationStorage;
    private long resId = 0;

    public SettingsPresenter(SettingsContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
        settingsStorage = application.getSettingsStorage();
        organizationStorage = application.getOrganizationStorage();
    }

    @Override
    public void onViewCreated() {

        Settings settings = settingsStorage.loadSettings();
        view.setFio(settings.getFio());
        view.setUserPosition(settings.getPosition());
        this.resId = settings.getResId();

        if (resId != 0) {

            ElectricNetworkArea res = organizationStorage.getResById(resId);
            view.setSpResName(res.getNetworkEnterprise().getName() + " / " + res.getName());
        } else {
            view.setSpResName("Не выбран (нажмите для выбора)");
        }

    }

    @Override
    public void SaveBtnPressed() {
        String fio = view.getFio();
        String position = view.getUserPosition();

        settingsStorage.saveSettings(new Settings(fio, position, (int) resId));

        view.finishView();
    }

    @Override
    public void SelectResBtnPressed() {
        List< NetworkEnterprise > allEnt = organizationStorage.getAllEnterprices();
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
        view.setSpResName(res.getNetworkEnterprise().getName() + " / " + res.getName());
        this.resId = res.getId();

    }

    @Override
    public void RefreshOrganizationsBtnPressed() {
        view.ShowProgressBar();
        organizationStorage.ClearOrganizations();
        application.getRemoteStorage().setProgressListener(this);
        application.getRemoteStorage().loadOrganization();
    }

    @Override
    public void progressUpdate(String progress) {

    }

    @Override
    public void progressComplete() {
      //  view.showSelectOrganizationDialog(0, 0);
        view.HideProgressBar();
    }

    @Override
    public void progressError(Exception ex) {
        view.HideProgressBar();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
