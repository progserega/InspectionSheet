package ru.drsk.progserega.inspectionsheet.ui.presenters;

import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.GroupAddTransfDeffectContract;

public class GroupAddTransfDeffectPresenter
        implements GroupAddTransfDeffectContract.Presenter {

    private GroupAddTransfDeffectContract.View view;
    private InspectionSheetApplication application;

    public GroupAddTransfDeffectPresenter(GroupAddTransfDeffectContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;
    }

    @Override
    public void initViewData() {
        InspectionItem header = application.getCurrentInspectionItem();

        view.setHeaderNumber(header.getNumber());
        view.setHeaderTitle(header.getName());

        view.initListAdapter(application.getInspectionItemsGroup());
    }


    @Override
    public void onSaveBtnPressed(Map<Integer, List<String>> values,
                                 Map<Integer, List<String>> subValues,
                                 Map<Integer, String> comments,
                                 Map<Integer, List<InspectionPhoto>> photos) {

        List<InspectionItem> inspectionItemsGroup = application.getInspectionItemsGroup();
        for(int i =0; i < inspectionItemsGroup.size(); i++){

            InspectionItemResult deffectResult = inspectionItemsGroup.get(i).getResult();
            deffectResult.setValues(values.get(i));
            deffectResult.setSubValues(subValues.get(i));
            deffectResult.setComment(comments.get(i));
            deffectResult.setPhotos(photos.get(i));
        }

        view.finishView();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
