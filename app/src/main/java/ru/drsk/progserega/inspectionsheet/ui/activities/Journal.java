package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;
import ru.drsk.progserega.inspectionsheet.ui.adapters.EquipmentRCListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.JournalListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.JournalContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.JournalPresenter;

public class Journal extends AppCompatActivity implements JournalContract.View {

    private InspectionSheetApplication application;
    private JournalContract.Presenter presenter;

    private JournalListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new JournalPresenter(this, application);


        setTitle("Журнал событий");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter.onViewCreated();


        List<LogModel> logs = new ArrayList<>();
        logs.add(new LogModel(new Date(), 1, "TAG", "MESSAGE"));
        logs.add(new LogModel(new Date(), 1, "TAG", "MESSAGE"));
        logs.add(new LogModel(new Date(), 1, "TAG", "MESSAGE"));
        logs.add(new LogModel(new Date(), 1, "TAG", "MESSAGE"));

        listAdapter = new JournalListAdapter(logs);
        RecyclerView journalList = (RecyclerView) findViewById(R.id.journal_list);
        journalList.setAdapter(listAdapter);
        journalList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        journalList.addItemDecoration(itemDecoration);
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
        super.onDestroy();
        presenter.onDestroy();
    }
}
