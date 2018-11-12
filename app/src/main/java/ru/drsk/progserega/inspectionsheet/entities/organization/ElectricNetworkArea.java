package ru.drsk.progserega.inspectionsheet.entities.organization;

/**
 * Район Электрических Сетей
 */
public class ElectricNetworkArea {
    private int id;
    private String name;
    private NetworkEnterprise networkEnterprise;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NetworkEnterprise getNetworkEnterprise() {
        return networkEnterprise;
    }

    public ElectricNetworkArea(int id, String name, NetworkEnterprise networkEnterprise) {
        this.id = id;
        this.name = name;
        this.networkEnterprise = networkEnterprise;
    }
}
