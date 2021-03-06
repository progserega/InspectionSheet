package ru.drsk.progserega.inspectionsheet.storages.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;

public class SettingsStorageImpl implements ISettingsStorage {
    private Context context;
    SharedPreferences sharedPref;

    public SettingsStorageImpl(Context context) {
        this.context = context;

        sharedPref = context.getSharedPreferences( context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    @Override
    public Settings loadSettings() {

        String fio = sharedPref.getString(Settings.FIO, "");
        String position = sharedPref.getString(Settings.POSITION, "");
        int resId = sharedPref.getInt(Settings.RES_ID, 0);
        int spId = sharedPref.getInt(Settings.SP_ID, 0);

        String serverUrl = sharedPref.getString(Settings.SERVER_URL, Settings.SERVER_URL_VALUE);
        String serverAltUrl = sharedPref.getString(Settings.SERVER_ALT_URL, Settings.SERVER_ALT_URL_VALUE);

        return new Settings(fio, position,spId, resId, serverUrl, serverAltUrl);
    }



    @Override
    public void saveSettings(Settings settings) {
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(Settings.FIO, settings.getFio());
        editor.putString(Settings.POSITION, settings.getPosition());
        editor.putInt(Settings.RES_ID, settings.getResId());
        editor.putInt(Settings.SP_ID, settings.getSpId());
        editor.putString(Settings.SERVER_URL, settings.getServerUrl());
        editor.putString(Settings.SERVER_ALT_URL, settings.getServerAltUrl());

        editor.commit();
    }

}
