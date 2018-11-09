package ru.drsk.progserega.inspectionsheet.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;

public class SelectOrganizationDialogFragment extends DialogFragment {

    private OrganizationService organizationService;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public SelectOrganizationDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SelectOrganizationDialogFragment newInstance(OrganizationService organizationService) {
        SelectOrganizationDialogFragment frag = new SelectOrganizationDialogFragment();
        frag.setOrganizationService(organizationService);

//        Bundle args = new Bundle();
//        args.putString("title", "OLOLO!!!");
//        frag.setArguments(args);
       // frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<NetworkEnterprise> enterprisesList = organizationService.getAllEnterprices();
        String[] enterprises = new String[enterprisesList.size()];
        for (int i = 0; i < enterprises.length; i++) {
            enterprises[i] = enterprisesList.get(i).getName();
        }

        View view = inflater.inflate(R.layout.select_organization_dialog, container, false);
        // Получаем экземпляр элемента Spinner
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        // Настраиваем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),  android.R.layout.simple_spinner_item, enterprises);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Get field from view
//      //  mEditText = (EditText) view.findViewById(R.id.txt_your_name);
//        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
//        // Show soft keyboard automatically and request focus to field
//        //mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(  WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        if(view == null){
            return;
        }


        Log.i("TEST TAG", organizationService.toString());

//            TextView title = (TextView) view.findViewById(R.id.textTitle);
//            Workout workout = Workout.workouts[(int) workoutId];
//            title.setText(workout.getName());
//            TextView description = (TextView) view.findViewById(R.id.textDescription);
//            description.setText(workout.getDescription());
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
        window.setLayout((int) (size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout((int) (size.x * 0.9), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

}
