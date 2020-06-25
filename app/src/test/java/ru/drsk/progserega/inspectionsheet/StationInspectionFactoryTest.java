package ru.drsk.progserega.inspectionsheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.IStationInspectionService;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.services.StationInspectionFactory;
import ru.drsk.progserega.inspectionsheet.services.StationInspectionService;
import ru.drsk.progserega.inspectionsheet.storages.stub.InspectionStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.StationDeffectsTypesStorageStub;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StationInspectionFactoryTest {

    @Mock
    EquipmentService equipmentService;

    @Mock
    InspectionService inspectionService;

   // @Mock
   // StationInspectionService stationInspectionService;

    @Test
    public void New_StationInspection_Should_Have_StationInspectionItems() {

        StationInspectionService stationInspectionService = new StationInspectionService(new StationDeffectsTypesStorageStub(), new InspectionStorageStub());
        StationInspectionFactory inspectionFactory = new StationInspectionFactory(equipmentService, inspectionService, stationInspectionService);

        Substation substation = new Substation(1, 1, "Name", null, 0, 0, 0);
        IStationInspection stationInspection = inspectionFactory.build(substation);
//        verify(inspectionService, times(1)).getSubstationTransformersWithInspections(substation);
        assertTrue(!stationInspection.getStationInspectionItems().isEmpty());
    }
}
