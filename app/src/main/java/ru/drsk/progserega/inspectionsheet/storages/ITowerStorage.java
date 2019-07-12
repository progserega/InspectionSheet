package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;

public interface ITowerStorage {

    List<Tower> getByLineId(int lineId);

    List<Tower> getByLineUniqId(long id);

    Tower getFirstInLine(long lineUniqId);

    Tower getByNumberInLine(String number, long lineUniqId);

    Tower getByUniqId( long uniqId);

    void update(Tower tower);
}
