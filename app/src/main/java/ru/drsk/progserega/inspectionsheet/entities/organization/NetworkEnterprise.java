package ru.drsk.progserega.inspectionsheet.entities.organization;


import java.util.ArrayList;
import java.util.List;

public class NetworkEnterprise {
    private int id;
    private String name;
    private List<ElectricNetworkArea> ENAreas;

    public int getId() {
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

    public NetworkEnterprise(int id, String name) {
        this.id = id;
        this.name = name;
        this.ENAreas = new ArrayList<>();
    }

    public NetworkEnterprise(int id, String name, List<ElectricNetworkArea> ENAreas) {
        this.id = id;
        this.name = name;
        this.ENAreas = ENAreas;
    }
}
