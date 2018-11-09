package ru.drsk.progserega.inspectionsheet.storages;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;

public class OrganizationStorageStub implements IOrganizationStorage {

    @Override
    public List<NetworkEnterprise> getAllEnterprices() {

        List<NetworkEnterprise> enterprises = new ArrayList<>();
        enterprises.add(new NetworkEnterprise(1, "ent 1"));
        enterprises.add(new NetworkEnterprise(2, "ent 2"));
        enterprises.add(new NetworkEnterprise(3, "ent 3"));
        enterprises.add(new NetworkEnterprise(4, "ent 4"));
        enterprises.add(new NetworkEnterprise(5, "ent 5"));

        return enterprises;

    }
}
