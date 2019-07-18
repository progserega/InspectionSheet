package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineFinishContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineFinishPresenter;

import static ru.drsk.progserega.inspectionsheet.ui.activities.SearchObject.OBJECT_TYPE;

public class InspectLineFinish extends AppCompatActivity implements InspectLineFinishContract.View {

    private InspectionSheetApplication application;
    private InspectLineFinishContract.Presenter presenter;

    private Calendar myCalendar;
    private EditText edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_line_finish);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectLineFinishPresenter(this, application);

        setTitle("Завершение осмотра");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDateEditText();

        presenter.onViewCreate();
    }

    private void initDateEditText() {
        myCalendar = Calendar.getInstance();
        edittext = (EditText) findViewById(R.id.inspect_line_finish_date_edit);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                presenter.onInspectionDateSelected(myCalendar.getTime());
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(InspectLineFinish.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

    @Override
    public void setInspectorName(String name)
    {
        EditText editText = (EditText) findViewById(R.id.inspect_line_finish_inspector_name);
        editText.setText(name);
    }

    @Override
    public String getInspectorName()
    {
        EditText editText = (EditText) findViewById(R.id.inspect_line_finish_inspector_name);
        return editText.getText().toString();
    }

    @Override
    public void setDateInspection(Date dateInspection) {
        if(dateInspection == null){
            edittext.setText("");
        }
        else {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            edittext.setText(sdf.format(dateInspection));
        }
    }

    public void onFinishBtnClick(View view) {
        presenter.onFinishButtonPressed();
    }

    @Override
    public void gotoSearchObjectActivity() {
        Intent intent = new Intent(this, SearchObject.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {

            case android.R.id.home:
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
