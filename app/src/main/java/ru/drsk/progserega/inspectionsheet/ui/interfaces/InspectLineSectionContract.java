package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;

public class InspectLineSectionContract {
    public interface View{

        void setDeffectsList(List<LineSectionDeffect> sectionDeffects);

        void setSectionNumber(String number);

        void gotoNextTowerInspection(long nextTowerUniqId);

        void setMaterialsSpinnerData(List<String> materials, int sel);

        void setComment(String comment);

        String getComment();

        void setInspectionPhotos(List<InspectionPhoto> photos);

        void startDeffectDescriptionActivity();

    }

    public interface Presenter{

        void onViewCreated(long  sectionId);

        void onDeffectSelectionChange(int pos, boolean isSelected);

        void nextButtonPressed();

        void previousButtonPressed();

        void onMaterialSelected(int pos);

        void onImageTaken(String photoPath);

        void onDestroy();

        void onDefectAboutBtnClick(LineSectionDeffect sectionDeffect);

        void finishButtonPressed();

    }
}
