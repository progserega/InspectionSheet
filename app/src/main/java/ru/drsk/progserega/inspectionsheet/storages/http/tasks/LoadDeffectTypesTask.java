package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.services.DBLog;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.DeffectDescriptionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadDeffectTypesTask implements ObservableOnSubscribe< String > {

    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    private DBDataImporter dbDataImporter;

    private  Context context;

    public LoadDeffectTypesTask(IApiInspectionSheet apiInspectionSheet, DBDataImporter dbDataImporter,  Context context) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dbDataImporter = dbDataImporter;
        this.context = context;
    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        try {

            loadTowerDeffectTypes(emitter);

            loadSectionDeffectTypes(emitter);

            loadTransformersDeffectTypes(emitter);

            loadStationsDeffectTypes(emitter);

            List< DeffectDescriptionJson > descriptionsJson = loadDeffectDescriptions(emitter);

            descriptionsJson =  dbDataImporter.getDescriptionsFilesForDownload(descriptionsJson);

            loadDeffectDescriptionsPhotos(emitter, descriptionsJson);

            emitter.onComplete();
        } catch (Exception e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }

    private void loadTowerDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getTowerDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при Загрузке типов деффектов опор. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< TowerDeffectTypesJson > deffectTypesJson = (List< TowerDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadTowerDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов опор загружены");
        }


    }

    private void loadSectionDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getSectionDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов пролетов. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< SectionDeffectTypesJson > deffectTypesJson = (List< SectionDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadSectionDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов пролетов загружены");
        }


    }

    private void loadTransformersDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getTransformersDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов подстанций и ктп. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< StationDeffectTypesJson > deffectTypesJson = (List< StationDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadTransformersDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов  подстанций и ктп загружены");
        }


    }

    private void loadStationsDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getStationDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов подстанций и ктп. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< StationDeffectTypesJson > deffectTypesJson = (List< StationDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            //dbDataImporter.loadTransformersDeffectTypes(deffectTypesJson);

            dbDataImporter.loadStationsDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов  подстанций и ктп загружены");
        }


    }

    private  List< DeffectDescriptionJson > loadDeffectDescriptions(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;
        DBLog.d("IMPORT_DEFECTS_DESCRIPTIONS", "START");
        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getDeffectDescriptions().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке описаний дефектов. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return new ArrayList<>();
        }

        List< DeffectDescriptionJson > descriptionsJson = (List< DeffectDescriptionJson >) response.body();


        if (descriptionsJson.isEmpty()) {
            return new ArrayList<>();
        }
            //передаем данные на обработку
        dbDataImporter.loadDefectDescriptions(descriptionsJson);
        emitter.onNext("Оописания дефектов загружены");
        return descriptionsJson;

    }


    private  void loadDeffectDescriptionsPhotos(ObservableEmitter< String > emitter, List< DeffectDescriptionJson > descriptionJsons) throws Exception {
        DBLog.d("IMPORT_DEFECTS_DESCRIPTIONS", "load photos");

       for(DeffectDescriptionJson descriptionJson: descriptionJsons) {

           Response response = null;
           int attempt = 1;
           while (attempt < ATTEMPT_COUNT) {
               try {
                   response = apiInspectionSheet.downloadFileWithDynamicUrl(descriptionJson.getImageUrl()).execute();
                   break;
               } catch (java.net.ProtocolException ex) {
                   if (attempt == ATTEMPT_COUNT - 1) {
                       throw ex;
                   }
               } catch (Exception ex) {
                   if (attempt == ATTEMPT_COUNT - 1) {
                       throw ex;
                   }
               }
               emitter.onNext("Ошибка при загрузке описаний дефектов. Попытка " + String.valueOf(attempt));
               attempt++;

           }

           File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
           String targetpath = storageDir.getAbsolutePath() + "/" + descriptionJson.getFileName();

           writeResponseBodyToDisk((ResponseBody) response.body(), targetpath);

           dbDataImporter.loadDefectDescriptionPhotoFile(descriptionJson, targetpath);

       }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String path) {

        try {

            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    DBLog.d("IMPORT_DEFECTS_DESCRIPTIONS_FILE", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
