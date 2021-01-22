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
    public void removeAllInspectionsPhotos() {

        removeAllFilesFromDir(Environment.DIRECTORY_PICTURES, false);
        removeAllFilesFromDir(Environment.DIRECTORY_PICTURES + "/inspections", true);
        clearSubDir(Environment.DIRECTORY_PICTURES + "/inspections");

    }

    private void removeAllFilesFromDir(String path, boolean recursive) {
        File storageDir = context.getExternalFilesDir(path);

        if (!storageDir.exists()) {
            return;
        }

        List< File > result = new ArrayList<>();

        search(".*\\.*", storageDir, result, recursive);

        for (File file : result) {
            System.out.println(file.getAbsolutePath());
            file.delete();
        }
    }

    private void clearSubDir(String path){
        File folder = context.getExternalFilesDir(path);
        for (final File f : folder.listFiles()) {
            f.delete();
        }
    }

    public static void search(final String pattern, final File folder, List< File > result, boolean recursive) {
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

    @Override
    public void removePhoto(String path) {
        File imageFile = new File(path);

        if (imageFile.exists()) {
            imageFile.delete();
        }
    }
}
