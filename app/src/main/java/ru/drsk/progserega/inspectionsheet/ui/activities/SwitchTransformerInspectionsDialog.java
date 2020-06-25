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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;


public class SwitchTransformerInspectionsDialog  extends DialogFragment {

    public interface AcceptListener {
        void onAcceptBtnClick(long sourcePos, long destPos);
    }


    private List<TransformerInspection> transformerInspections;

    private AcceptListener acceptListener;
    private Spinner sourceSpinner;
    private Spinner destSpinner;


    public void setTransformerInspections(List<TransformerInspection> transformerInspections) {
        this.transformerInspections = transformerInspections;
    }

    public void setAcceptListener(AcceptListener acceptListener) {
        this.acceptListener = acceptListener;
    }

    public SwitchTransformerInspectionsDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }


    public static SwitchTransformerInspectionsDialog newInstance(List<TransformerInspection> transformerInspections, AcceptListener acceptListener) {

        SwitchTransformerInspectionsDialog frag = new SwitchTransformerInspectionsDialog();
        frag.setTransformerInspections(transformerInspections);
        frag.setAcceptListener(acceptListener);
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transf_swich_deffects_dialog, container, false);


        List<String> transformersNames = new ArrayList<>();
        for(TransformerInspection inspection: transformerInspections){
            transformersNames.add(String.format("[T%d]  %s",inspection.getTransformator().getSlot(), inspection.getTransformator().getModel().getName()));
        }

        ArrayAdapter<String> dataAdapterSource = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, transformersNames);
        dataAdapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner = (Spinner) view.findViewById(R.id.switch_source_spinner);
        sourceSpinner.setAdapter(dataAdapterSource);


        ArrayAdapter<String> dataAdapterDest = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, transformersNames);
        dataAdapterDest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destSpinner = (Spinner) view.findViewById(R.id.switch_dest_spinner);
        destSpinner.setAdapter(dataAdapterDest);

        Button selectButton = (Button) view.findViewById(R.id.switch_transf_inspections_accept_btn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long posSource =  sourceSpinner.getSelectedItemPosition();
                long posDest   =  destSpinner.getSelectedItemPosition();
                acceptListener.onAcceptBtnClick(posSource, posDest);
                getDialog().dismiss();
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.switch_inspections_txtclose);
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
        window.setLayout((int) (size.x * 0.9),  WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout((int) (size.x * 0.9), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
