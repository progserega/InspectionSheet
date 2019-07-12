package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;

public class InspectLineSectionContract {
    public interface View{

        void setDeffectsList(List<LineSectionDeffect> sectionDeffects);

        void setSectionNumber(String number);

        void gotoNextTowerInspection(long nextTowerUniqId);

        void setMaterialsSpinnerData(List<String> materials, int sel);

        void setComment(String comment);

        String getComment();

        void setInspectionPhotos(List<InspectionPhoto> photos);

    }

    public interface Presenter{

        void onViewCreated(long  sectionId);

        void onDeffectSelectionChange(int pos, boolean isSelected);

        void nextButtonPressed();

        void onMaterialSelected(int pos);

        void onImageTaken(String photoPath);

        void onDestroy();

        void finishButtonPressed();

    }
}
