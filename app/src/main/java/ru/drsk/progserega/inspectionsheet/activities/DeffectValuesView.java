package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.activities.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemResultValues;

public class DeffectValuesView {

    private LinearLayout layout;
    private InspectionItemResultValues values;
    private List<String> selectedValues;

    private Context context;

    private RadioGroup radioGroup;
    private List<CheckBox> checkBoxes;

    public DeffectValuesView(LinearLayout layout, InspectionItemResultValues values, List<String> selectedValues, Context context) {
        this.layout = layout;
        this.values = values;
        this.context = context;
        this.selectedValues = selectedValues;
    }

    public void build() {
        int id = 0;
        //InspectionItemResultValues values = result.getResultValues();
        if (values.getType().equals("radio")) {

            radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            for (String value : values.getPossibleValues()) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setId(id);
                rdbtn.setText(value);
                radioGroup.addView(rdbtn);

                if(!selectedValues.isEmpty() && selectedValues.get(0).equals(value)){

                    rdbtn.setChecked(true);
                }
                id++;
            }

            layout.addView(radioGroup);


        }

        if (values.getType().equals("checkbox")) {
            checkBoxes = new ArrayList<>();
            for (String value : values.getPossibleValues()) {
                CheckBox cb = new CheckBox(context);
                cb.setText(value);
                cb.setId(id);
                layout.addView(cb);
                id++;
                checkBoxes.add(cb);

                //Устанавливает галочки если уже были установлены значения
                for(String checkedValue: selectedValues){
                    if(checkedValue.equals(value)){
                        cb.setChecked(true);
                    }
                }
            }
        }

        if (values.getType().equals("date")) {

        }


        int paddingPx = MetricsUtils.dpToPx(8, context);
        layout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
    }

    public List<String> getResult() {

        List<String> results = new ArrayList<>();

        if (values.getType().equals("radio")) {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId >= 0) {
                String value = values.getPossibleValues().get(selectedId);
                results.add(value);
            }
        }

        if (values.getType().equals("checkbox")) {

            for(CheckBox cb: checkBoxes){
                boolean checked = cb.isChecked();
                if(checked){

                    String value = values.getPossibleValues().get( cb.getId() );
                    results.add(value);
                }
            }
        }

        if (values.getType().equals("date")) {

        }

        return results;
    }
}
