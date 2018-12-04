package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;

public class DBDataImporter {
    private IOrganizationStorage organizationStorage;
    private ITransformerSubstationStorage transformerSubstationStorage;
    private ITransformerStorage transformerStorage;

    public DBDataImporter(IOrganizationStorage organizationStorage,
                          ITransformerSubstationStorage transformerSubstationStorage,
                          ITransformerStorage transformerStorage) {
        this.organizationStorage = organizationStorage;
        this.transformerSubstationStorage = transformerSubstationStorage;
        this.transformerStorage = transformerStorage;
    }

    public void loadSteTpModel(List<SteTPModel> tpModels){

        for (SteTPModel tpModel: tpModels){
            String spName = tpModel.getSpName();
            String resNmae = tpModel.getResName();

        }
    }
}
