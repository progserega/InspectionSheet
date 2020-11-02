package ru.drsk.progserega.inspectionsheet.storages.files;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.IFileStorage;

public class FileStorage implements IFileStorage {


    private Context context;

    public FileStorage(Context context) {
        this.context = context;
    }

    @Override
    public void removeInspectionsPhotos() {

        removeAllFilesFromDir(Environment.DIRECTORY_PICTURES);
        removeAllFilesFromDir(Environment.DIRECTORY_PICTURES+"/inspections");

    }

    private void removeAllFilesFromDir(String path){
        File storageDir = context.getExternalFilesDir(path);

        if(!storageDir.exists()){
            return;
        }

        List<File> result = new ArrayList<>();

        search(".*\\.*", storageDir, result, false);

        for (File file : result) {
            System.out.println(file.getAbsolutePath());
            file.delete();
        }
    }

    public static void search(final String pattern, final File folder, List<File> result, boolean recursive) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory() && recursive) {
                search(pattern, f, result, recursive);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f);
                }
            }

        }
    }
}
