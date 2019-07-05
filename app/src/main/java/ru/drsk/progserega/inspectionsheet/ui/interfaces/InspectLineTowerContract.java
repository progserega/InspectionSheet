package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerInspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class InspectLineTowerContract {
    public interface View{

        void setDeffectsList(List<TowerDeffect> towerDeffects);

        void showSelectTowerDialog(List<Tower> towers);

        void setTowerNumber(String number);
    }

    public interface Presenter{

        void onViewCreated(String nextTower);

        void onSelectTowerBtnClick();

        void onTowerSelected(int pos);

        void onGPSSwitchChange(boolean isOn);

        void onGPSLocationChange();

        void onDeffectSelectionChange(int pos, boolean isSelected);

        void nextButtonPressed();

        void onDestroy();


    }
}
