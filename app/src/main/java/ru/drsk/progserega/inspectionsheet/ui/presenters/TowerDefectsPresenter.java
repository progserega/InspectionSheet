package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.TowerDeffectsContract;

@Deprecated
public class TowerDefectsPresenter implements TowerDeffectsContract.Presenter {

    private TowerDeffectsContract.View view;
    private InspectionSheetApplication application;

    private List<TowerDeffect> deffectList;

    public TowerDefectsPresenter(TowerDeffectsContract.View view, InspectionSheetApplication application){
        this.view = view;
        this.application = application;
        deffectList = new ArrayList<>();
    }

    @Override
    public void onAddDeffectBtnPress() {
        List<TowerDeffectType> types = new ArrayList<>();
        types.add(new TowerDeffectType(1, "Деффект 1"));
        types.add(new TowerDeffectType(2, "Деффект 2"));
        types.add(new TowerDeffectType(3, "Деффект 3"));
        types.add(new TowerDeffectType(4, "Деффект 4"));

        view.showSelectDeffectDialog(types);
    }

    @Override
    public void addDeffect(TowerDeffectType deffectType) {
       // deffectList.add(new TowerDeffect(0, 1, deffectType));

        view.updateDeffectsList(deffectList);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
