package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;

public class OrganizationService {

    private IOrganizationStorage organizationStorage;

    public OrganizationService(IOrganizationStorage organizationStorage) {
        this.organizationStorage = organizationStorage;
    }

    public List<NetworkEnterprise> getAllEnterprices(){
        return organizationStorage.getAllEnterprices();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
