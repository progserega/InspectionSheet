package ru.drsk.progserega.inspectionsheet.entities.organization;

/**
 * Район Электрических Сетей
 */
public class ElectricNetworkArea {
    private long id;
    private String name;
    private NetworkEnterprise networkEnterprise;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NetworkEnterprise getNetworkEnterprise() {
        return networkEnterprise;
    }

    public ElectricNetworkArea(long id, String name, NetworkEnterprise networkEnterprise) {
        this.id = id;
        this.name = name;
        this.networkEnterprise = networkEnterprise;
    }
}
