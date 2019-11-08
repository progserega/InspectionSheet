package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;

public class OrganizationStorageStub implements IOrganizationStorage {

    @Override
    public List<NetworkEnterprise> getAllEnterprices() {

        List<NetworkEnterprise> enterprises = new ArrayList<>();
        NetworkEnterprise e1 = new NetworkEnterprise(1, "ent 1");
        enterprises.add(e1);
        NetworkEnterprise e2 = new NetworkEnterprise(2, "ent 2");
        enterprises.add(e2);
        NetworkEnterprise e3 = new NetworkEnterprise(3, "ent 3");
        enterprises.add(e3);
        NetworkEnterprise e4 = new NetworkEnterprise(4, "ent 4");
        enterprises.add(e4);
        NetworkEnterprise e5 = new NetworkEnterprise(5, "ent 5");
        enterprises.add(e5);

        List<ElectricNetworkArea> ent1areas = new ArrayList<>();
        ent1areas.add(new ElectricNetworkArea(1, "e1_a1", e1));
        ent1areas.add(new ElectricNetworkArea(2, "e1_a2", e1));
        ent1areas.add(new ElectricNetworkArea(3, "e1_a3", e1));
        e1.setENAreas(ent1areas);

        List<ElectricNetworkArea> ent2areas = new ArrayList<>();
        ent2areas.add(new ElectricNetworkArea(1, "e2_a1", e1));
        ent2areas.add(new ElectricNetworkArea(2, "e2_a2", e1));
        ent2areas.add(new ElectricNetworkArea(3, "e2_a3", e1));
        e2.setENAreas(ent2areas);

        List<ElectricNetworkArea> ent3areas = new ArrayList<>();
        ent3areas.add(new ElectricNetworkArea(1, "e3_a1", e1));
        ent3areas.add(new ElectricNetworkArea(2, "e3_a2", e1));
        ent3areas.add(new ElectricNetworkArea(3, "e3_a3", e1));
        e3.setENAreas(ent3areas);

        List<ElectricNetworkArea> ent4areas = new ArrayList<>();
        ent4areas.add(new ElectricNetworkArea(1, "e4_a1", e1));
        ent4areas.add(new ElectricNetworkArea(2, "e4_a2", e1));
        ent4areas.add(new ElectricNetworkArea(3, "e4_a3", e1));
        e4.setENAreas(ent4areas);


        return enterprises;

    }

    @Override
    public ElectricNetworkArea getResById(long id) {
        return null;
    }

    @Override
    public void ClearOrganizations() {

    }
}
