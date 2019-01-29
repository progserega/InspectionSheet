package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class TowerDeffectsContract {
    public interface View{
        void showSelectDeffectDialog(List<TowerDeffectType> types);

        void updateDeffectsList(List<TowerDeffect> deffects);
    }

    public interface Presenter{

        void onAddDeffectBtnPress();

        void addDeffect(TowerDeffectType deffectType);
    }


}
