package ru.drsk.progserega.inspectionsheet.presenters;


import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.TowerDeffectsContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.TowerDefectsPresenter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TowerDeffectsPresenterTest {


    @Mock
    Context mMockContext;

    @Mock
    TowerDeffectsContract.View view;

    @Mock
    InspectionSheetApplication application;

    @Test
    public void testPresenter() {

        TowerDefectsPresenter presenter = new TowerDefectsPresenter(view, application);

        presenter.onAddDeffectBtnPress();
        verify(view).showSelectDeffectDialog(anyListOf(TowerDeffectType.class));

        assertTrue(true);



    }
}
