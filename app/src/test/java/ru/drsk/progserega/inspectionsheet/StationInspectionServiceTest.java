package ru.drsk.progserega.inspectionsheet;

import org.junit.Test;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.services.IStationInspectionService;
import ru.drsk.progserega.inspectionsheet.services.StationInspectionService;
import ru.drsk.progserega.inspectionsheet.storages.stub.InspectionStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.StationDeffectsTypesStorageStub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StationInspectionServiceTest {

    @Test
    public void should_return_substation_inspection_items(){

        IStationInspectionService stationInspectionService = new StationInspectionService(new StationDeffectsTypesStorageStub(), new InspectionStorageStub());

        Substation substation = new Substation(1, 1, "Name", null, 0, 0, 0);
        List<InspectionItem> items = stationInspectionService.getStationInspectionItems(substation);
        assertFalse(items.isEmpty());
    }

}
