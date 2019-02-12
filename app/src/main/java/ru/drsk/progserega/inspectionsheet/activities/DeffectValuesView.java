package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.activities.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;

public class DeffectValuesView {

    public interface OnValueChangeListener {
        void valuesChange(List<String> values);
    }

    private OnValueChangeListener valueChangeListener;
    private LinearLayout layout;
    private InspectionItemPossibleResult possibleResult;
    private List<String> selectedValues;

    private Context context;

    private RadioGroup radioGroup;
    private List<CheckBox> checkBoxes;

    public DeffectValuesView(LinearLayout layout,
                             InspectionItemPossibleResult possibleResult,
                             List<String> selectedValues,
                             Context context,
                             OnValueChangeListener valueChangeListener) {

        this.layout = layout;
        this.possibleResult = possibleResult;
        this.context = context;
        this.selectedValues = selectedValues;
        this.valueChangeListener = valueChangeListener;
    }

    public void build() {
        int id = 0;
        //InspectionItemPossibleResult possibleResult = result.getPossibleResult();
        if (possibleResult.getType().equals("radio")) {

            radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            for (String value : possibleResult.getValues()) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setId(id);
                rdbtn.setText(value);
                radioGroup.addView(rdbtn);

                if(!selectedValues.isEmpty() && selectedValues.get(0).equals(value)){
                    rdbtn.setChecked(true);
                }
                id++;
            }

            //set listener to radio button group
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioBtn = (RadioButton) layout.findViewById(checkedRadioButtonId);
                   // Toast.makeText(context, radioBtn.getText(), Toast.LENGTH_SHORT).show();
                   List<String> results = new ArrayList<>();
                    results.add( radioBtn.getText().toString());
                    valueChangeListener.valuesChange(results);
                }
            });

            layout.addView(radioGroup);


        }

        if (possibleResult.getType().equals("checkbox")) {
            checkBoxes = new ArrayList<>();
            for (String value : possibleResult.getValues()) {
                CheckBox cb = new CheckBox(context);
                cb.setText(value);
                cb.setId(id);
                layout.addView(cb);
                checkBoxes.add(cb);

                //Устанавливает галочки если уже были установлены значения
                for(String checkedValue: selectedValues){
                    if(checkedValue.equals(value)){
                        cb.setChecked(true);
                    }
                }

                final int finalId = id;
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       // String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                        //Toast.makeText(context, msg + finalId, Toast.LENGTH_SHORT).show();

                        List<String> results = new ArrayList<>();
                        for(CheckBox cb: checkBoxes){
                            boolean checked = cb.isChecked();
                            if(checked){

                                String value = possibleResult.getValues().get( cb.getId() );
                                results.add(value);
                            }
                        }
                        valueChangeListener.valuesChange(results);

                    }
                });

                id++;
            }


        }

        if (possibleResult.getType().equals("date")) {

        }


        int paddingPx = MetricsUtils.dpToPx(8, context);
        layout.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
    }

    public List<String> getResult() {

        List<String> results = new ArrayList<>();

        if (possibleResult.getType().equals("radio")) {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId >= 0) {
                String value = possibleResult.getValues().get(selectedId);
                results.add(value);
            }
        }

        if (possibleResult.getType().equals("checkbox")) {

            for(CheckBox cb: checkBoxes){
                boolean checked = cb.isChecked();
                if(checked){

                    String value = possibleResult.getValues().get( cb.getId() );
                    results.add(value);
                }
            }
        }

        if (possibleResult.getType().equals("date")) {

        }

        return results;
    }
}
