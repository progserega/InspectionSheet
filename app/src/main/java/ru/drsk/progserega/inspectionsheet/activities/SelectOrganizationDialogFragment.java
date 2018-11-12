package ru.drsk.progserega.inspectionsheet.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;

/**
 * Диалог выбора СП и РЭС
 *
 * TODO Есть баг, при повторном вызове окна диалога не отображает выбранный в предыдущий раз РЭС
 */
public class SelectOrganizationDialogFragment extends DialogFragment {

    public interface ISelectOrganizationListener {
        void onSelectOrganization(int enterpriseId, int areaId);
    }

    private OrganizationService organizationService;
    private List<NetworkEnterprise> enterprisesList;
    private ArrayAdapter<String> enterpriseAdapter;
    private ArrayAdapter<String> areaAdapter;
    private ISelectOrganizationListener selectOrganizationListener;

    private Spinner enterpriseSpinner;
//    private int enterpriseSpinnerSelection = 0;

    private Spinner areaSpinner;
    private int areaSpinnerSelection = 0;

    private NetworkEnterprise selectedEnterprise = null;
    private ElectricNetworkArea selectedArea = null;

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
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.select_organization_dialog, container, false);

        enterprisesList = organizationService.getAllEnterprices();
        List<String> enterpriseSpinnerItems = new ArrayList<>();
        enterpriseSpinnerItems.add("Все СП");
        for (NetworkEnterprise enterprise : enterprisesList) {
            enterpriseSpinnerItems.add(enterprise.getName());
        }


        selectOrganizationListener = (ISelectOrganizationListener) this.getActivity();

        enterpriseSpinner = (Spinner) view.findViewById(R.id.enterprise_spinner);
        enterpriseAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, enterpriseSpinnerItems);
        enterpriseAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        enterpriseSpinner.setAdapter(enterpriseAdapter);
       // enterpriseSpinner.setSelection(enterpriseSpinnerSelection);

        enterpriseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSlectEnterpriseItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        areaSpinner = (Spinner) view.findViewById(R.id.area_spinner);
        areaAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, createAreaSpinnerItems(selectedEnterprise));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        areaSpinner.setAdapter(areaAdapter);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectAreaItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        TextView textView = (TextView) view.findViewById(R.id.txtclose);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button selectButton = (Button) view.findViewById(R.id.btnselect);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectOrganizationBtnClick();
            }
        });

        return view;
    }

    private void onSlectEnterpriseItem(int position) {
        areaAdapter.clear();

        if (position == 0) {
            selectedEnterprise = null;
            return;
        }

        selectedEnterprise = enterprisesList.get(position - 1);
        areaAdapter.addAll(createAreaSpinnerItems(selectedEnterprise));
        areaAdapter.notifyDataSetChanged();
        areaSpinner.setSelection(0);

    }

    private List<String> createAreaSpinnerItems(NetworkEnterprise enterprise) {
        List<String> areas = new ArrayList<>();
        if (enterprise == null) {
            return areas;
        }

        List<ElectricNetworkArea> areaList = enterprise.getENAreas();
        areas.add("все РЭС");
        for (ElectricNetworkArea area : areaList) {
            areas.add(area.getName());
        }
        return areas;
    }

    private void onSelectAreaItem(int position) {
        areaSpinnerSelection = position;
        if (position == 0) {
            selectedArea = null;
            return;
        }

        selectedArea = selectedEnterprise.getENAreas().get(position - 1);
    }

    private void onSelectOrganizationBtnClick() {

        int enterpriseId = (selectedEnterprise != null) ? selectedEnterprise.getId() : 0;
        int areaId = (selectedArea != null) ? selectedArea.getId() : 0;
        selectOrganizationListener.onSelectOrganization(enterpriseId, areaId);
        getDialog().dismiss();
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

    @Override
    public void onDetach() {
        super.onDetach();
        selectOrganizationListener = null;
    }

}
