package ru.drsk.progserega.inspectionsheet.entities.organization;


import java.util.ArrayList;
import java.util.List;

/**
 * Сетевое Предприятие
 */
public class NetworkEnterprise {
    private long id;
    private String name;
    private List<ElectricNetworkArea> ENAreas;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ElectricNetworkArea> getENAreas() {
        return ENAreas;
    }

    public void setENAreas(List<ElectricNetworkArea> ENAreas) {
        this.ENAreas = ENAreas;
    }

    public NetworkEnterprise(long id, String name) {
        this.id = id;
        this.name = name;
        this.ENAreas = new ArrayList<>();
    }

    public NetworkEnterprise(long id, String name, List<ElectricNetworkArea> ENAreas) {
        this.id = id;
        this.name = name;
        this.ENAreas = ENAreas;
    }
}
