package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

public class GroupAddTransfDeffectContract {

    public interface View {
        void setHeaderNumber(String number);

        void setHeaderTitle(String title);

        void initListAdapter(List<InspectionItem> inspectionItems);

        void showSelectPhotoDialog();

        void refreshDeffectsList();

        void finishView();
    }

    public interface Presenter {
        void initViewData();


        void onSaveBtnPressed(
                Map<Integer, List<String>> values,
                Map<Integer, List<String>> subValues,
                Map<Integer, String> comments,
                Map<Integer, List<DeffectPhoto>> photos);

        void onDestroy();
    }
}
