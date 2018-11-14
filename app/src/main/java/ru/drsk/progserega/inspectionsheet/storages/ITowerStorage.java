package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;

public interface ITowerStorage {

    List<Tower> getByLineId(int lineId);
}
