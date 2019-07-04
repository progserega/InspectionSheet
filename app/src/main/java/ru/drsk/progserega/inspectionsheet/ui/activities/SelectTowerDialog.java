package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.ui.adapters.TowersListAdapter;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class SelectTowerDialog extends DialogFragment {

    public interface ISelectTowerListener {
        void onSelectTower(int position);
    }

    private ISelectTowerListener selectTowerListener;


    private TowersListAdapter towersListAdapter;
    private ListView towersListView;
    private List<Tower> towers = new ArrayList<>();

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public SelectTowerDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SelectTowerDialog newInstance() {
        SelectTowerDialog frag = new SelectTowerDialog();
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.select_tower_dialog, container, false);

        selectTowerListener = (ISelectTowerListener)getActivity();

        towersListView = (ListView) view.findViewById(R.id.select_tower_towers_list);
        towersListAdapter = new TowersListAdapter(this.getActivity(), towers);
        towersListView.setAdapter(towersListAdapter);
        towersListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        Button selectButton = (Button) view.findViewById(R.id.select_tower_select_btn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  TransformerType transformerType =  (TransformerType) transformerList.getSelectedItem();
                int pos = towersListView.getCheckedItemPosition();

                if (pos != -1) {
                    selectTowerListener.onSelectTower(pos);
                    getDialog().dismiss();
                }

            }
        });

        TextView textView = (TextView) view.findViewById(R.id.select_tower_list_txtclose);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onResume() {

        //Тут пошевелим размеры окна

        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.9), (int) (size.y * 0.9));
        //window.setLayout((int) (size.x * 0.9), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
