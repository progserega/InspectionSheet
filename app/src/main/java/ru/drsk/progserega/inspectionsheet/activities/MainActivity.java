package ru.drsk.progserega.inspectionsheet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class MainActivity extends AppCompatActivity {

    private InspectionSheetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (InspectionSheetApplication) this.getApplication();
    }

    /**
     * Called when the user clicks the Send button
     */
    public void addDefect(View view) {
        Log.d("addDefect()", "1");

        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, SelectTypeLine.class);
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("addDefect()", "2");
        startActivity(intent);
        Log.d("addDefect()", "3");
    }

    public void syncData(View view){
       // showGPSPermission();

        application.getApiSTE().getData("api/all-tp").enqueue(new Callback<SteTPResponse>() {
            @Override
            public void onResponse(Call<SteTPResponse> call, Response<SteTPResponse> response) {
                //Данные успешно пришли, но надо проверить response.body() на null
                int a = 0;
            }
            @Override
            public void onFailure(Call<SteTPResponse> call, Throwable t) {
                //Произошла ошибка
                int a = 0;
            }
        });
    }

}
