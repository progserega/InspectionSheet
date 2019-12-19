package ru.drsk.progserega.inspectionsheet.storages.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;

@Deprecated
public class InspectionResultsAsyncUploader extends AsyncTask<Void, Integer, Void> {


    private IApiInspectionSheet apiInspectionSheet;

    private IProgressListener progressListener;
    private IRemoteDataArrivedListener dataArrivedListener;
    List<TransformerInspection> transformerInspections;
    private static final int PAGE_SIZE = 200;

    private Exception ex;

    public InspectionResultsAsyncUploader(IApiInspectionSheet apiInspectionSheet, List<TransformerInspection> transformerInspections, IRemoteDataArrivedListener dataArrivedListener) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dataArrivedListener = dataArrivedListener;
        this.transformerInspections = transformerInspections;
        ex = null;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        for(TransformerInspection transformerInspection: transformerInspections){

//            try {
//                Response response = apiInspectionSheet.uploadInspection(new TransformerInspectionResult("ИМЯ", "ЗНАЧЕНИЕ")).execute();
//                TransformerInspectionResult resp = (TransformerInspectionResult) response.body();
//                int a = 0;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            for(InspectionItem inspectionItem: transformerInspection.getInspectionItems()){

                for(InspectionPhoto inspectionPhoto : inspectionItem.getResult().getPhotos()){
                    uploadPhoto(inspectionPhoto);
                }
            }
            break;

        }
//        int page = 0;
//        int total = 0;
//        int offset = 0;
//        do {
//            offset = page * PAGE_SIZE;
//            SteTPResponse tpResponse = null;
//            try {
//                Response response = apiInspectionSheet.getTPbyRange("api/all-tp-by-range", offset, PAGE_SIZE).execute();
//                if (response.body() == null) {
//                    break;
//                }
//
//                tpResponse = (SteTPResponse) response.body();
//                total = tpResponse.getTotalRecords();
//
//                if (tpResponse.getData() != null && !tpResponse.getData().isEmpty()) {
//                    //передаем данные на обработку
//                    dataArrivedListener.SteTPModelsArrived(tpResponse.getData());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                //break;
//                ex = e;
//                return null;
//            }
//
//            page++;
//
//            //считаем процент загруженных данных
//            int totalPages = (int) Math.ceil((float) total / PAGE_SIZE);
//            publishProgress((int) ((page / (float) totalPages) * 100));
//
//        } while ((offset + PAGE_SIZE) < total);
//
//        publishProgress(0);
//        loadSubstations();
//        publishProgress(100);
//
//        //Загружаем трансформаторы для подстанций
//        loadSubstationTransformers();
        publishProgress(100);
        return null;
   }


    protected void onProgressUpdate(Integer... progress) {
        if (progressListener != null) {
            progressListener.progressUpdate(progress[0].toString());
        }
    }

    protected void onPostExecute(Void result) {
        if (progressListener != null) {
            progressListener.progressComplete();
        }
        if (ex != null) {
            progressListener.progressError(ex);
        }
    }

    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    private void uploadPhoto(InspectionPhoto photo)  {

        File file = new File(photo.getPath());
        Log.d("UPLOAD FILE:", "Filename " + file.getName());
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Response response = null;
        try {
            //Не работает с кирилическими именами файлов
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            response = apiInspectionSheet.uploadInspectionImage(fileToUpload, filename).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }

        if (response.body() == null) {
           return;
        }

        UploadRes uploadImageInfoRes = (UploadRes) response.body();
        Log.d("UPLOAD FILE:", "reult " + uploadImageInfoRes.getStatus());
    }
}
