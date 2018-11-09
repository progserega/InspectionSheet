package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;

public interface IOrganizationStorage {

    List<NetworkEnterprise> getAllEnterprices();

}
