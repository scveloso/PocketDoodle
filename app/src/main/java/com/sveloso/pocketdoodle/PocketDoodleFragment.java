package com.sveloso.pocketdoodle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Veloso on 6/15/2016.
 */
public class PocketDoodleFragment extends Fragment {

    private static final String DIALOG_SAVE = "DialogSave";

    private DrawingView mDrawingView;
    private PocketDoodleManager sPocketDoodleManager;

    public static PocketDoodleFragment newInstance() {
        return new PocketDoodleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pocket_doodle, container, false);

        mDrawingView = (DrawingView) v.findViewById(R.id.pocket_doodle_drawing_view);
        mDrawingView.setDrawingCacheEnabled(true);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPocketDoodleManager = PocketDoodleManager.get(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_pocket_doodle, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_color_palette:
                Paint currentPaint = sPocketDoodleManager.getPaint();
                int currentColor = currentPaint.getColor();
                Intent colorIntent = ColorPaletteActivity.newIntent(getActivity(), currentColor);
                startActivity(colorIntent);
                return true;

            case R.id.menu_item_save_doodle:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                final EditText saveEditText = new EditText(getActivity());
                saveEditText.setWidth(300);
                dialog.setView(saveEditText)
                        .setTitle(R.string.save_doodle_title)
                        .setPositiveButton(R.string.save_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveCanvas(saveEditText.getText().toString());
                        }
                    })
                    .setNegativeButton(R.string.cancel_text, null);
                dialog.show();
                return true;

            case R.id.menu_item_box_mode:
                sPocketDoodleManager.setMode("Box");
                return true;

            case R.id.menu_item_line_mode:
                sPocketDoodleManager.setMode("Line");
                return true;

            case R.id.menu_item_eraser_mode:
                sPocketDoodleManager.setMode("Eraser");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveCanvas(String name) {
        if (mDrawingView.saveDoodle(name)) {
            Toast.makeText(getActivity(), "Save successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Save failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
