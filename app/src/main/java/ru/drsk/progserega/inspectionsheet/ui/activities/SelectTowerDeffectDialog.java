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

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;
import ru.drsk.progserega.inspectionsheet.ui.adapters.TowerDeffectTypesListAdapter;

public class SelectTowerDeffectDialog extends DialogFragment {
    public interface AddTowerDeffectListener {
        void onAddDeffect(TowerDeffectType deffectType);
    }

    private List<TowerDeffectType> types;
    private AddTowerDeffectListener towerDeffectListener;
    private ListView typesList;

    public void setTypes(List<TowerDeffectType> types) {
        this.types = types;
    }

    public SelectTowerDeffectDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SelectTowerDeffectDialog newInstance(List<TowerDeffectType> types) {
        SelectTowerDeffectDialog frag = new SelectTowerDeffectDialog();
        frag.setTypes(types);
        //  frag.setOrganizationService();
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tower_deffects_types, container, false);

        towerDeffectListener = (AddTowerDeffectListener)getActivity();

        typesList = (ListView) view.findViewById(R.id.tower_deffects_types_list);
        TowerDeffectTypesListAdapter deffectTypesListAdapter = new TowerDeffectTypesListAdapter(this.getActivity(), types);
        typesList.setAdapter(deffectTypesListAdapter);
        typesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //onSlectEnterpriseItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button selectButton = (Button) view.findViewById(R.id.select_tower_deffect_btn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Transformer transformer =  (Transformer) transformerList.getSelectedItem();
                int pos = typesList.getCheckedItemPosition();
                TowerDeffectType deffectType  = (TowerDeffectType) typesList.getAdapter().getItem(pos);
                if( deffectType != null) {
                    towerDeffectListener.onAddDeffect(deffectType);
                    getDialog().dismiss();
                }
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.tower_deffects_list_txtclose);
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
