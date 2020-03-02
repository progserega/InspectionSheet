package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SearchObjectsContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SettingsContract;

public class SearchObjectPresenter implements SearchObjectsContract.Presenter {

    private SearchObjectsContract.View view;
    private InspectionSheetApplication application;


    public SearchObjectPresenter(SearchObjectsContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;

    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

}
