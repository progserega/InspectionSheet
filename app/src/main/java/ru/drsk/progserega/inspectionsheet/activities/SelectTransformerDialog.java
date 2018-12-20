package ru.drsk.progserega.inspectionsheet.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformerSpinnerAdapter;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformersListAdapter;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class SelectTransformerDialog  extends DialogFragment {

    public interface AddTransformerListener{
        void onAddTransformer(long transformerTypeId, int slot);
    }

    private AddTransformerListener addTransformerListener;
    private  ListView transformerList;

    private int slot;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public SelectTransformerDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SelectTransformerDialog newInstance() {
        SelectTransformerDialog frag = new SelectTransformerDialog();
      //  frag.setOrganizationService();
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transformers_list, container, false);

        addTransformerListener = (AddTransformerListener)getActivity();

        InspectionSheetApplication application = (InspectionSheetApplication) getActivity().getApplication();

        ITransformerStorage transformerStorage = application.getTransformerStorage();
        List<Transformer> transformers = transformerStorage.getAll();


        transformerList = (ListView) view.findViewById(R.id.transformers_list);
        TransformersListAdapter transformersListAdapter = new TransformersListAdapter(this.getActivity(), transformers);
        transformerList.setAdapter(transformersListAdapter);
        transformerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //onSlectEnterpriseItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        // selectOrganizationListener = (ISelectOrganizationListener) this.getActivity();



        Button selectButton = (Button) view.findViewById(R.id.add_transformator_btn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Transformer transformer =  (Transformer) transformerList.getSelectedItem();
                int pos = transformerList.getCheckedItemPosition();
                Transformer transformer  = (Transformer) transformerList.getAdapter().getItem(pos);
                if( transformer != null) {
                    addTransformerListener.onAddTransformer(transformer.getId(), slot);
                    getDialog().dismiss();
                }
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.transformers_list_txtclose);
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
