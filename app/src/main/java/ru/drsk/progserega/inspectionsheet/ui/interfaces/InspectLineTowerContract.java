package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class InspectLineTowerContract {
    public interface View{

        void setDeffectsList(List<TowerDeffect> towerDeffects);

        void showSelectTowerDialog(List<Tower> towers);

        void setTowerNumber(String number, List<String> allNumbers);

        void gotoSectionInspection(long nextSectionId);

        void setMaterialsSpinnerData(List<String> materials, int sel);

        void setTowerTypesSpinnerData(List<String> towerTypes, int sel);

        void setComment(String comment);

        String getComment();

        void showGetPhotoDialog(long equipmentId);

        void setTowerPhotos(List<InspectionPhoto> photos);

        void showNextSectionSelectorDialog(String[] selectionItems);

        void showEndOfLineDialog();

        void startDeffectDescriptionActivity();

        void showEmpyInspectionWarningDialog(String action, String title);

        void gotoFinishActivity();

        void hideUI();

        void showUI();

        void disableTowerNumEvents();

        void enableTowerNumEvents();
    }

    public interface Presenter{

        void onViewCreated(long nextTowerUniqId);

        void onSelectTowerBtnClick();

        void onTowerSelected(int pos);

        void onGPSSwitchChange(boolean isOn);

        void onGPSLocationChange();

        void onDeffectSelectionChange(int pos, boolean isSelected);

        void nextButtonPressed();

        void previousButtonPressed();

        void onMaterialSelected(int pos);

        void onTowerTypeSelected(int pos);

        void onImageTaken(String photoPath);

        void onDestroy();

        void onNextSectionSelected(int pos);

        void onDefectAboutBtnClick(TowerDeffect towerDeffect);

        void finishButtonPressed();

        void onCurrentTowerNameChange(String name);

        void onAddTowerPhotoBtnClick();

        void onEmptyInspectionWarningResult(boolean result, String action);


    }
}
