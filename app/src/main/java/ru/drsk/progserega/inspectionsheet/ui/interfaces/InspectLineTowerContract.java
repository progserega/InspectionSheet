package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class InspectLineTowerContract {
    public interface View{

        void setDeffectsList(List<TowerDeffect> towerDeffects);

        void showSelectTowerDialog(List<Tower> towers);

        void setTowerNumber(String number);

        void gotoSectionInspection(long nextSectionId);

        void setMaterialsSpinnerData(List<String> materials, int sel);

        void setTowerTypesSpinnerData(List<String> towerTypes, int sel);

        void setComment(String comment);

        String getComment();

        void setTowerPhotos(List<InspectionPhoto> photos);

        void showNextSectionSelectorDialog(String[] selectionItems);

        void showEndOfLineDialog();
    }

    public interface Presenter{

        void onViewCreated(String nextTower);

        void onSelectTowerBtnClick();

        void onTowerSelected(int pos);

        void onGPSSwitchChange(boolean isOn);

        void onGPSLocationChange();

        void onDeffectSelectionChange(int pos, boolean isSelected);

        void nextButtonPressed();

        void onMaterialSelected(int pos);

        void onTowerTypeSelected(int pos);

        void onImageTaken(String photoPath);

        void onDestroy();

        void onNextSectionSelected(int pos);

    }
}