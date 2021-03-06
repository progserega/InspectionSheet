package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;

public class SubstationStorageStub implements ISubstationStorage {


    private List<Substation> substations;

    public SubstationStorageStub(){
        substations = initSubstations();
    }

    private List<Substation> initSubstations(){
        List<Substation> substations = new ArrayList<>();

        substations.add(new Substation(1,0, "Подстанция 1", new Date(0), 0,0,0));
        substations.add(new Substation(2,0, "Подстанция 2",  new Date(0), 0,0,0));
        substations.add(new Substation(3,0, "Подстанция 3",  new Date(0), 0,0,0));
        substations.add(new Substation(4,0, "Подстанция 4",  new Date(0), 0,0,0));
        substations.add(new Substation(5,0, "Подстанция 5",  new Date(0), 0,0,0));

        return substations;

    }



    @Override
    public List<Substation> getByFilters(Map<String, Object> filters) {

        return substations;
    }

    @Override
    public Substation getById(long id) {
        for (Substation substation: substations){
            if(substation.getId() == id){
                return substation;
            }
        }
        return null;
    }

}
