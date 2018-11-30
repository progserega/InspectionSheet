package ru.drsk.progserega.inspectionsheet.storages.http;

import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class SteLoader {

    IApiSTE apiSTE;
    IOrganizationStorage organizationStorage;
    ITransformerStorage transformatorStorage;


    public SteLoader(IApiSTE apiSTE) {
        this.apiSTE = apiSTE;
    }

    public void LoadTransformatorSubstantions(){

    }
}
