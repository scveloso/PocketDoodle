package com.sveloso.pocketdoodle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Veloso on 6/17/2016.
 */
public class SaveDoodleFragment extends DialogFragment {

    private EditText mEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mEditText = new EditText(getActivity());

        return new AlertDialog.Builder(getActivity())
                .setView(mEditText)
                .setTitle(R.string.save_doodle_title)
                .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PocketDoodleFragment parentFragment = (PocketDoodleFragment) getParentFragment();
                        parentFragment.saveCanvas(mEditText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel_text, null)
                .create();
    }
}
