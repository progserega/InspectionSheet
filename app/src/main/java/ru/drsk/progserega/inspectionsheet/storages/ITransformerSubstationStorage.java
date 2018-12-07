package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;

public interface ITransformerSubstationStorage {

    List<TransformerSubstation> getByFilters(Map<String, Object> filters);

    TransformerSubstation getById(long id);
}
