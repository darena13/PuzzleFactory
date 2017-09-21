package darena13.puzzlefactory;

import android.app.Dialog;
import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by darena13 on 21.09.17.
 */

public class WinDialog extends DialogFragment implements OnClickListener {
        private static final String TAG = "WinDialog";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Win");
        View v = inflater.inflate(R.layout.win_dialog, null);
        return v;
    }

    public void onClick(View v) {
        //TODO: НЕ РАБОТАЕТ
        //TODO: сделать две кнопки: share и переход к активити с уровнями
        Log.d(TAG, "dismiss");
        dismiss();
    }
}
