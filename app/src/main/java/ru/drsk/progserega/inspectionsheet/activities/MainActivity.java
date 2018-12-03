package ru.drsk.progserega.inspectionsheet.activities;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class MainActivity extends AppCompatActivity implements IProgressListener {

    private InspectionSheetApplication application;
    private ProgressBar progressBar;
    private TextView progressText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (InspectionSheetApplication) this.getApplication();

        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        progressText = (TextView) findViewById(R.id.loading_progress_text);
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

    public void syncData(View view) {
        // showGPSPermission();

        progressText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        application.getRemoteStorage().setProgressListener(this);
        application.getRemoteStorage().loadTrasformerSubstations();
    }

    @Override
    public void progressUpdate(int progress) {
        Log.d("DOWNLOAD", "DOWNLOAD PERCENT "+ String.valueOf(progress));
        progressText.setText(String.valueOf(progress)+" %");
    }

    @Override
    public void progressComplete() {
        progressText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
