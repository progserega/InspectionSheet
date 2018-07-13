package ru.drsk.progserega.inspectionsheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class searchObject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        // Заполняем список:
        String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
      "Костя", "Игорь", "Анна", "Денис", "Андрей" };
        // находим список
        ListView lvMain = (ListView) findViewById(R.id.drsk_object_search_list);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

    }
}
