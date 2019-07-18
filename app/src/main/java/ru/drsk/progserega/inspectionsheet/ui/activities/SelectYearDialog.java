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
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;

public class SelectYearDialog extends DialogFragment {
    public interface SelectYearListener {
        void onSelectYear(int year);
    }

    private int year;
    private int maxYear;

    private SelectYearDialog.SelectYearListener listener;

    public void setYear(int year) {
        this.year = (year == 0) ? 2000 : year;
        //this.year = year;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public void setListener(SelectYearListener listener) {
        this.listener = listener;
    }

    public static SelectYearDialog newInstance(int year, int maxYear, SelectYearListener listener) {
        SelectYearDialog frag = new SelectYearDialog();
        frag.setYear(year);
        frag.setMaxYear(maxYear);
        frag.setListener(listener);
        frag.setCancelable(false); //Запрет закрытия по клику на пустом месте
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.yeardialog, container, false);

        Button set = (Button) view.findViewById(R.id.button1);
        final TextView year_text = (TextView) view.findViewById(R.id.year_text);
        year_text.setText("" + year);
        final NumberPicker nopicker = (NumberPicker) view.findViewById(R.id.numberPicker1);


        nopicker.setMaxValue(maxYear);
        nopicker.setMinValue(maxYear - 100);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year_text.setText(String.valueOf(nopicker.getValue()));
                listener.onSelectYear(nopicker.getValue());
                getDialog().dismiss();
            }
        });


        TextView textView = (TextView) view.findViewById(R.id.select_year_txtclose);
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
        window.setLayout((int) (size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout((int) (size.x * 0.9), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
