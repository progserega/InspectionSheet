package ru.drsk.progserega.inspectionsheet.storages;

import ru.drsk.progserega.inspectionsheet.entities.Settings;

public interface ISettingsStorage {

    Settings loadSettings();

    void saveSettings(Settings settings);


}
