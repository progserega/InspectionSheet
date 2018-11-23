package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Substation;

public interface ISubstationStorage {

    List<Substation> getByFilters(Map<String, Object> filters);

    Substation getById(long id);
}
