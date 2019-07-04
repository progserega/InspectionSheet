package ru.drsk.progserega.inspectionsheet.utility;

import android.widget.ImageButton;

import ru.drsk.progserega.inspectionsheet.R;

public class ButtonUtils {

    public static void initSaveBtnImg( ImageButton imageButton){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setImageResource(R.drawable.ic_baseline_save_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            imageButton.setImageResource(R.drawable.ic_save_balack_png);
        }
        imageButton.invalidate();
    }
}
