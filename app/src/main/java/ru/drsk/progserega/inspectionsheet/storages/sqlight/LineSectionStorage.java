package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineSectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;

public class LineSectionStorage implements ILineSectionStorage {

    private InspectionSheetDatabase db;
    private Context context;
    private ICatalogStorage catalogStorage;

    public LineSectionStorage(InspectionSheetDatabase db, Context context, ICatalogStorage catalogStorage) {
        this.db = db;
        this.context = context;
        this.catalogStorage = catalogStorage;
    }

    @Override
    public List<LineSection> getByLineStartWithTower(long lineUniqId, long towerUniqId) {
        List<LineSectionModel> sectionModels = db.lineSectionDao().getByLineTower(lineUniqId, towerUniqId);
        List<LineSection> sections = new ArrayList<>();
        for (LineSectionModel sectionModel : sectionModels) {
            sections.add(ModelToEntity(sectionModel));
        }
        return sections;
    }

    @Override
    public LineSection getById(long id) {
        LineSectionModel sectionModel = db.lineSectionDao().getById(id);
        return ModelToEntity(sectionModel);
    }

    private LineSection ModelToEntity(LineSectionModel sectionModel) {
        if (sectionModel == null) {
            return null;
        }
        return new LineSection(
                sectionModel.getId(),
                sectionModel.getName(),
                sectionModel.getLineUniqId(),
                sectionModel.getFromTowerUniqId(),
                sectionModel.getToTowerUniqId(),
                catalogStorage.getMaterialById(sectionModel.getMaterial())
        );
    }

    @Override
    public void update(LineSection lineSection) {
        if(lineSection == null){
            return;
        }
        LineSectionModel sectionModel = new LineSectionModel(
                lineSection.getId(),
                lineSection.getLineUniqId(),
                lineSection.getTowerFromUniqId(),
                lineSection.getTowerToUniqId(),
                lineSection.getName(),
                lineSection.getMaterial().getId()
        );
        db.lineSectionDao().update(sectionModel);
    }
}
