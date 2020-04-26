package ru.drsk.progserega.inspectionsheet.ui.interfaces;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

public class JournalContract {
    public interface View{

        void setLogMessages(List<LogModel> messages);

        boolean requestStoragePermissions();

        void sendFileWiaEmail(String fileName);


    }

    public interface Presenter{

        void onViewCreated();

        void onDestroy();

        void sendJournalMenuSelected();

        void onStorageAccessGaranted();

    }
}
